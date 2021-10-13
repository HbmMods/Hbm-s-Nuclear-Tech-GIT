package com.hbm.render.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ObjUtil {

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rot, boolean shadow) {
<<<<<<< HEAD
		for(GroupObject go : model.groupObjects) {
			for(Face f : go.faces) {

				Vertex n = f.faceNormal;
				tes.setNormal(n.x, n.y, n.z);

				if(shadow) {
					float brightness = (n.y + 1) * 0.65F;
=======
		renderWithIcon(model, icon, tes, rot, 0, shadow);
	}

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rot, float pitch, boolean shadow) {
		for(GroupObject go : model.groupObjects) {
			
			for(Face f : go.faces) {

				Vertex n = f.faceNormal;

				Vec3 normal = Vec3.createVectorHelper(n.x, n.y, n.z);
				normal.rotateAroundZ(pitch);
				normal.rotateAroundY(rot);
				tes.setNormal((float)normal.xCoord, (float)normal.yCoord, (float)normal.zCoord);

				if(shadow) {
					float brightness = ((float)normal.yCoord + 0.7F) * 0.9F - (float)Math.abs(normal.xCoord) * 0.1F + (float)Math.abs(normal.zCoord) * 0.1F;
>>>>>>> master

					if(brightness < 0.45F)
						brightness = 0.45F;

					tes.setColorOpaque_F(brightness, brightness, brightness);
				}

				for(int i = 0; i < f.vertices.length; i++) {

					Vertex v = f.vertices[i];

					Vec3 vec = Vec3.createVectorHelper(v.x, v.y, v.z);
<<<<<<< HEAD
=======
					vec.rotateAroundZ(pitch);
>>>>>>> master
					vec.rotateAroundY(rot);

					float x = (float) vec.xCoord;
					float y = (float) vec.yCoord;
					float z = (float) vec.zCoord;

					TextureCoordinate t = f.textureCoordinates[i];
					tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16), icon.getInterpolatedV(t.v * 16));

					// The shoddy way of rendering a tringulated model with a
					// quad tessellator
					if(i % 3 == 2)
						tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16), icon.getInterpolatedV(t.v * 16));
				}
			}
		}
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, IIcon icon, Tessellator tes, float rot, boolean shadow) {
<<<<<<< HEAD
=======
		renderPartWithIcon(model, name, icon, tes, rot, 0, shadow);
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, IIcon icon, Tessellator tes, float rot, float pitch, boolean shadow) {
>>>>>>> master

		GroupObject go = null;

		for(GroupObject obj : model.groupObjects) {
			if(obj.name.equals(name))
				go = obj;
		}

		if(go == null)
			return;

		for(Face f : go.faces) {

			Vertex n = f.faceNormal;

			Vec3 normal = Vec3.createVectorHelper(n.x, n.y, n.z);
			normal.rotateAroundZ(pitch);
			normal.rotateAroundY(rot);
			tes.setNormal((float)normal.xCoord, (float)normal.yCoord, (float)normal.zCoord);

			if(shadow) {
				float brightness = ((float)normal.yCoord + 0.7F) * 0.9F - (float)Math.abs(normal.xCoord) * 0.1F + (float)Math.abs(normal.zCoord) * 0.1F;

				if(brightness < 0.45F)
					brightness = 0.45F;

				tes.setColorOpaque_F(brightness, brightness, brightness);
			}

			for(int i = 0; i < f.vertices.length; i++) {

				Vertex v = f.vertices[i];

				Vec3 vec = Vec3.createVectorHelper(v.x, v.y, v.z);
				vec.rotateAroundZ(pitch);
				vec.rotateAroundY(rot);

				float x = (float) vec.xCoord;
				float y = (float) vec.yCoord;
				float z = (float) vec.zCoord;

				TextureCoordinate t = f.textureCoordinates[i];
				tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16D), icon.getInterpolatedV(t.v * 16D));

				// The shoddy way of rendering a tringulated model with a quad
				// tessellator
				if(i % 3 == 2)
					tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16D), icon.getInterpolatedV(t.v * 16D));
			}
		}
	}
}
