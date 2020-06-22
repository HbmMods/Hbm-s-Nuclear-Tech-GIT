package com.hbm.render.loader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.Vertex;

@SideOnly(Side.CLIENT)
public abstract class S_ModelCustom implements IModelCustom {
	
	public float min = 100000.0F;
	public float minX = 100000.0F;
	public float minY = 100000.0F;
	public float minZ = 100000.0F;
	public float max = -100000.0F;
	public float maxX = -100000.0F;
	public float maxY = -100000.0F;
	public float maxZ = -100000.0F;
	public float size = 0.0F;
	public float sizeX = 0.0F;
	public float sizeY = 0.0F;
	public float sizeZ = 0.0F;

	public void checkMinMax(Vertex v) {
		if (v.x < this.minX) {
			this.minX = v.x;
		}
		if (v.y < this.minY) {
			this.minY = v.y;
		}
		if (v.z < this.minZ) {
			this.minZ = v.z;
		}
		if (v.x > this.maxX) {
			this.maxX = v.x;
		}
		if (v.y > this.maxY) {
			this.maxY = v.y;
		}
		if (v.z > this.maxZ) {
			this.maxZ = v.z;
		}
	}

	public void checkMinMaxFinal() {
		if (this.minX < this.min) {
			this.min = this.minX;
		}
		if (this.minY < this.min) {
			this.min = this.minY;
		}
		if (this.minZ < this.min) {
			this.min = this.minZ;
		}
		if (this.maxX > this.max) {
			this.max = this.maxX;
		}
		if (this.maxY > this.max) {
			this.max = this.maxY;
		}
		if (this.maxZ > this.max) {
			this.max = this.maxZ;
		}
		this.sizeX = (this.maxX - this.minX);
		this.sizeY = (this.maxY - this.minY);
		this.sizeZ = (this.maxZ - this.minZ);
		this.size = (this.max - this.min);
	}

	public abstract boolean containsPart(String paramString);

	public abstract void renderAll(int paramInt1, int paramInt2);

	public abstract void renderAllLine(int paramInt1, int paramInt2);

	public abstract int getVertexNum();

	public abstract int getFaceNum();
}
