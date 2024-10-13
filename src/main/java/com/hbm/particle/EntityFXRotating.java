package com.hbm.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityFXRotating extends EntityFX {

	protected EntityFXRotating(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	public void renderParticleRotated(Tessellator tess, float interp, float sX, float sY, float sZ, float dX, float dZ, double scale) {

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);
		float rotation = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * interp;

		double x1 = 0 - sX * scale - dX * scale;
		double y1 = 0 - sY * scale;
		double z1 = 0 - sZ * scale - dZ * scale;
		double x2 = 0 - sX * scale + dX * scale;
		double y2 = 0 + sY * scale;
		double z2 = 0 - sZ * scale + dZ * scale;
		double x3 = 0 + sX * scale + dX * scale;
		double y3 = 0 + sY * scale;
		double z3 = 0 + sZ * scale + dZ * scale;
		double x4 = 0 + sX * scale - dX * scale;
		double y4 = 0 - sY * scale;
		double z4 = 0 + sZ * scale - dZ * scale;
		
		double nX = ((y2 - y1) * (z3 - z1)) - ((z2 - z1) * (y3 - y1));
		double nY = ((z2 - z1) * (x3 - x1)) - ((x2 - x1) * (z3 - z1));
		double nZ = ((x2 - x1) * (y3 - y1)) - ((y2 - y1) * (x3 - x1));
		
		Vec3 vec = Vec3.createVectorHelper(nX, nY, nZ).normalize();
		nX = vec.xCoord;
		nY = vec.yCoord;
		nZ = vec.zCoord;

		double cosTh = Math.cos(rotation * Math.PI / 180D);
		double sinTh = Math.sin(rotation * Math.PI / 180D);

		double x01 = x1 * cosTh + (nY * z1 - nZ * y1) * sinTh;
		double y01 = y1 * cosTh + (nZ * x1 - nX * z1) * sinTh;
		double z01 = z1 * cosTh + (nX * y1 - nY * x1) * sinTh;
		double x02 = x2 * cosTh + (nY * z2 - nZ * y2) * sinTh;
		double y02 = y2 * cosTh + (nZ * x2 - nX * z2) * sinTh;
		double z02 = z2 * cosTh + (nX * y2 - nY * x2) * sinTh;
		double x03 = x3 * cosTh + (nY * z3 - nZ * y3) * sinTh;
		double y03 = y3 * cosTh + (nZ * x3 - nX * z3) * sinTh;
		double z03 = z3 * cosTh + (nX * y3 - nY * x3) * sinTh;
		double x04 = x4 * cosTh + (nY * z4 - nZ * y4) * sinTh;
		double y04 = y4 * cosTh + (nZ * x4 - nX * z4) * sinTh;
		double z04 = z4 * cosTh + (nX * y4 - nY * x4) * sinTh;

		tess.addVertexWithUV(pX + x01, pY + y01, pZ + z01, particleIcon.getMaxU(), particleIcon.getMaxV());
		tess.addVertexWithUV(pX + x02, pY + y02, pZ + z02, particleIcon.getMaxU(), particleIcon.getMinV());
		tess.addVertexWithUV(pX + x03, pY + y03, pZ + z03, particleIcon.getMinU(), particleIcon.getMinV());
		tess.addVertexWithUV(pX + x04, pY + y04, pZ + z04, particleIcon.getMinU(), particleIcon.getMaxV());
	}
}
