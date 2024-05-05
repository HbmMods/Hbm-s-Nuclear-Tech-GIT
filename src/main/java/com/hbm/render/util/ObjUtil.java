package com.hbm.render.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class ObjUtil {

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rot, boolean shadow) {
		renderWithIcon(model, icon, tes, rot, 0, 0, shadow);
	}

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rot, float pitch, boolean shadow) {
		renderWithIcon(model, icon, tes, rot, pitch, 0, shadow);
	}

	public static void renderWithIcon(WavefrontObject model, IIcon icon, Tessellator tes, float rot, float pitch, float roll, boolean shadow) {
		for(GroupObject go : model.groupObjects) {
			
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
					vec.rotateAroundX(roll);
					vec.rotateAroundZ(pitch);
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
		renderPartWithIcon(model, name, icon, tes, rot, 0, 0, shadow);
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, IIcon icon, Tessellator tes, float rot, float pitch, boolean shadow) {
		renderPartWithIcon(model, name, icon, tes, rot, pitch, 0, shadow);
	}

	public static void renderPartWithIcon(WavefrontObject model, String name, IIcon icon, Tessellator tes, float rot, float pitch, float roll, boolean shadow) {

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

			if(shadow || hasColor) {
				
				float brightness = 1.0F;
				
				if(shadow) {
					brightness = ((float)normal.yCoord * 0.3F + 0.7F) - (float)Math.abs(normal.xCoord) * 0.1F + (float)Math.abs(normal.zCoord) * 0.1F;
	
					if(brightness < 0.45F)
						brightness = 0.45F;
				}

				if(hasColor) {
					tes.setColorOpaque((int)(red * brightness), (int)(green * brightness), (int)(blue * brightness));
				} else {
					tes.setColorOpaque_F(brightness, brightness, brightness);
				}
			}

			for(int i = 0; i < f.vertices.length; i++) {

				Vertex v = f.vertices[i];

				Vec3 vec = Vec3.createVectorHelper(v.x, v.y, v.z);
				vec.rotateAroundX(roll);
				vec.rotateAroundZ(pitch);
				vec.rotateAroundY(rot);

				float x = (float) vec.xCoord;
				float y = (float) vec.yCoord;
				float z = (float) vec.zCoord;

				TextureCoordinate t = f.textureCoordinates[i];
				tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16D), icon.getInterpolatedV(t.v * 16D));

				// The shoddy way of rendering a tringulated model with a quad
				// tessellator
				if(f.vertices.length == 3 && i % 3 == 2)
					tes.addVertexWithUV(x, y, z, icon.getInterpolatedU(t.u * 16D), icon.getInterpolatedV(t.v * 16D));
			}
		}
	}
	
	private static int red;
	private static int green;
	private static int blue;
	private static boolean hasColor = false;
	
	public static void setColor(int color) {
		red = (color & 0xff0000) >> 16;
		green = (color & 0x00ff00) >> 8;
		blue = color & 0x0000ff;
		hasColor = true;
	}
	
	public static void setColor(int r, int g, int b) {
		red = r;
		green = g;
		blue = b;
		hasColor = true;
	}
	
	public static void clearColor() {
		hasColor = false;
	}

	// Both methods assume model is facing towards +X (EAST)
	// Why not +Z (NORTH)? Pitch doesn't rotate as you would expect in that case using the (current) draw methods
	public static float getPitch(ForgeDirection dir) {
		if (dir == ForgeDirection.UP) return (float)Math.PI * -0.5F;
		if (dir == ForgeDirection.DOWN) return (float)Math.PI * 0.5F;
		return 0;
	}

	public static float getYaw(ForgeDirection dir) {
		if (dir == ForgeDirection.NORTH) return (float)Math.PI * 0.5f;;
		if (dir == ForgeDirection.SOUTH) return (float)Math.PI * -0.5f;
		if (dir == ForgeDirection.WEST) return (float)Math.PI;
		return 0;
	}

}
