package com.hbm.render.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class Shader {

    private boolean hasLoaded = false;

    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;
    private int timeUniform;
    private int channel1Uniform;
    private int offsetUniform;

    private int previousProgram;

    public Shader(ResourceLocation fragment) {
        this(new ResourceLocation(RefStrings.MODID, "shaders/default.vert"), fragment);
    }

    public Shader(ResourceLocation vertex, ResourceLocation fragment) {
        try {
            vertexShader = loadShader(vertex, GL20.GL_VERTEX_SHADER);
            fragmentShader = loadShader(fragment, GL20.GL_FRAGMENT_SHADER);
    
            shaderProgram = GL20.glCreateProgram();
            GL20.glAttachShader(shaderProgram, vertexShader);
            GL20.glAttachShader(shaderProgram, fragmentShader);
            GL20.glLinkProgram(shaderProgram);
    
            if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
                throw new RuntimeException("Failed to link shader program: " + GL20.glGetProgramInfoLog(shaderProgram, 1024));
            }
    
            timeUniform = GL20.glGetUniformLocation(shaderProgram, "iTime");
            channel1Uniform = GL20.glGetUniformLocation(shaderProgram, "iChannel1");
            offsetUniform = GL20.glGetUniformLocation(shaderProgram, "iOffset");

            hasLoaded = true;
        } catch(RuntimeException ex) {
            MainRegistry.logger.error("Shaders failed to load, falling back to default pipeline");
            MainRegistry.logger.catching(ex);

            hasLoaded = false;
        }
    }

    private int loadShader(ResourceLocation location, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, loadResource(location));
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Failed to compile shader: " + GL20.glGetShaderInfoLog(shader, 1024));
        }

        return shader;
    }

    private String loadResource(ResourceLocation location) {
		try {
			IResource res = Minecraft.getMinecraft().getResourceManager().getResource(location);
			return loadResource(res.getInputStream());
		} catch(IOException e) {
			throw new RuntimeException("IO Exception reading model format", e);
		}
    }

    private String loadResource(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading shader file", e);
        }
    }

    public void use() {
        if(!hasLoaded) return;
        previousProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        GL20.glUseProgram(shaderProgram);
    }

    public void stop() {
        if(!hasLoaded) return;
        GL20.glUseProgram(previousProgram);
    }

    public void cleanup() {
        GL20.glDetachShader(shaderProgram, vertexShader);
        GL20.glDetachShader(shaderProgram, fragmentShader);
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
        GL20.glDeleteProgram(shaderProgram);
    }

    public void setTime(float time) {
        if(!hasLoaded) return;
        GL20.glUniform1f(timeUniform, time);
    }
    
    public void setTextureUnit(int unit) {
        if(!hasLoaded) return;
        GL20.glUniform1i(channel1Uniform, unit);
    }

    public void setOffset(float offset) {
        if(!hasLoaded) return;
        GL20.glUniform1f(offsetUniform, offset);
    }

}