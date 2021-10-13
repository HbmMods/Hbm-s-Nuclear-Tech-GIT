package com.hbm.render.shader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ShaderManager {

	public static boolean enableShaders = true;

	public static final Uniform LIGHTMAP = shader -> {
		GL20.glUniform1i(GL20.glGetUniformLocation(shader, "lightmap"), 1);
	};

	public static Shader loadShader(ResourceLocation file) {
		
		if(!enableShaders)
			return new Shader(0);
		
		int vertexShader = 0;
		int fragmentShader = 0;
		
		try {
			int program = GL20.glCreateProgram();

			vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			GL20.glShaderSource(vertexShader, readFileToBuf(new ResourceLocation(file.getResourceDomain(), file.getResourcePath() + ".vert")));
			GL20.glCompileShader(vertexShader);
			if(GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetShaderInfoLog(vertexShader, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating vertex shader: " + file);
			}

			fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			GL20.glShaderSource(fragmentShader, readFileToBuf(new ResourceLocation(file.getResourceDomain(), file.getResourcePath() + ".frag")));
			GL20.glCompileShader(fragmentShader);
			if(GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetShaderInfoLog(fragmentShader, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating fragment shader: " + file);
			}

			GL20.glAttachShader(program, vertexShader);
			GL20.glAttachShader(program, fragmentShader);
			GL20.glLinkProgram(program);
			if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetProgramInfoLog(program, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating fragment shader: " + file);
			}

			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);

			return new Shader(program);
		} catch(Exception x) {
			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);
			x.printStackTrace();
		}
		return new Shader(0);
	}

	private static ByteBuffer readFileToBuf(ResourceLocation file) throws IOException {
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(file).getInputStream();
		byte[] bytes = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		ByteBuffer buf = BufferUtils.createByteBuffer(bytes.length);
		buf.put(bytes);
		buf.rewind();
		return buf;
	}
}
