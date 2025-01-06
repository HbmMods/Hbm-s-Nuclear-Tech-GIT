package com.hbm.render.loader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;

public class WavefrontObjVBO implements IModelCustomNamed {

	class VBOBufferData {

		String name;
		int vertices = 0;
		int vertexHandle;
		int uvHandle;
		int normalHandle;

	}

	List<VBOBufferData> groups = new ArrayList<VBOBufferData>();

	static int VERTEX_SIZE = 3;
	static int UV_SIZE = 3;
	
	public WavefrontObjVBO(HFRWavefrontObject obj) {
		for(S_GroupObject g : obj.groupObjects) {
			VBOBufferData data = new VBOBufferData();
			data.name = g.name;

			FloatBuffer vertexData = BufferUtils.createFloatBuffer(g.faces.size() * 3 * VERTEX_SIZE);
			FloatBuffer uvData = BufferUtils.createFloatBuffer(g.faces.size() * 3 * UV_SIZE);
			FloatBuffer normalData = BufferUtils.createFloatBuffer(g.faces.size() * 3 * VERTEX_SIZE);

			for(S_Face face : g.faces) {
				for(int i = 0; i < face.vertices.length; i++) {
					Vertex vert = face.vertices[i];
					TextureCoordinate tex = new TextureCoordinate(0, 0);
					Vertex normal = face.vertexNormals[i];

					if(face.textureCoordinates != null && face.textureCoordinates.length > 0) {
						tex = face.textureCoordinates[i];
					}

					data.vertices++;
					vertexData.put(new float[] { vert.x, vert.y, vert.z });
					uvData.put(new float[] { tex.u, tex.v, tex.w });
					normalData.put(new float[] { normal.x, normal.y, normal.z });
				}
			}
			vertexData.flip();
			uvData.flip();
			normalData.flip();
	
			data.vertexHandle = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.vertexHandle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			data.uvHandle = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.uvHandle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvData, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			data.normalHandle = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.normalHandle);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalData, GL15.GL_STATIC_DRAW);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			groups.add(data);
		}
	}

	@Override
	public String getType() {
		return "obj_vbo";
	}

	private void renderVBO(VBOBufferData data) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.vertexHandle);
		GL11.glVertexPointer(VERTEX_SIZE, GL11.GL_FLOAT, 0, 0l);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.uvHandle);
		GL11.glTexCoordPointer(UV_SIZE, GL11.GL_FLOAT, 0, 0l);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, data.normalHandle);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0l);

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, data.vertices);
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void renderAll() {
		for(VBOBufferData data : groups) {
			renderVBO(data);
		}
	}

	@Override
	public void renderOnly(String... groupNames) {
		for(VBOBufferData data : groups) {
			for(String name : groupNames) {
				if(data.name.equalsIgnoreCase(name)) {
					renderVBO(data);
				}
			}
		}
	}

	@Override
	public void renderPart(String partName) {
		for(VBOBufferData data : groups) {
			if(data.name.equalsIgnoreCase(partName)) {
				renderVBO(data);
			}
		}
	}

	@Override
	public void renderAllExcept(String... excludedGroupNames) {
		for(VBOBufferData data : groups) {
			boolean skip = false;
			for(String name : excludedGroupNames) {
				if(data.name.equalsIgnoreCase(name)) {
					skip = true;
					break;
				}
			}
			if(!skip) {
				renderVBO(data);
			}
		}
	}

	@Override
	public List<String> getPartNames() {
		List<String> names = new ArrayList<String>();
		for(VBOBufferData data : groups) {
			names.add(data.name);
		}
		return names;
	}

}