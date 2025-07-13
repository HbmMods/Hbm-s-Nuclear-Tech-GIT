package com.hbm.render.loader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class ModelRendererObj {

	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float originPointX;
	public float originPointY;
	public float originPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	
	public boolean doRender = true;

	String[] parts;
	IModelCustom model;

	public ModelRendererObj(IModelCustom model, String... parts) {
		this.model = model;
		this.parts = parts;
	}

	public ModelRendererObj setPosition(float x, float y, float z) {
		this.offsetX = x;
		this.offsetY = y;
		this.offsetZ = z;
		return this;
	}

	public ModelRendererObj setRotationPoint(float x, float y, float z) {
		this.originPointX = this.rotationPointX = x;
		this.originPointY = this.rotationPointY = y;
		this.originPointZ = this.rotationPointZ = z;
		return this;
	}

	public void copyTo(ModelRendererObj obj) {

		obj.offsetX = offsetX;
		obj.offsetY = offsetY;
		obj.offsetZ = offsetZ;
		obj.rotateAngleX = rotateAngleX;
		obj.rotateAngleY = rotateAngleY;
		obj.rotateAngleZ = rotateAngleZ;
		obj.rotationPointX = rotationPointX;
		obj.rotationPointY = rotationPointY;
		obj.rotationPointZ = rotationPointZ;
	}

	public void copyRotationFrom(ModelRenderer model) {
		offsetX = model.offsetX;
		offsetY = model.offsetY;
		offsetZ = model.offsetZ;
		rotateAngleX = model.rotateAngleX;
		rotateAngleY = model.rotateAngleY;
		rotateAngleZ = model.rotateAngleZ;
		rotationPointX = model.rotationPointX;
		rotationPointY = model.rotationPointY;
		rotationPointZ = model.rotationPointZ;
	}

	@SideOnly(Side.CLIENT)
	public void render(float scale) {

		if(parts == null)
			return;

		GL11.glPushMatrix();

		GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

		if(this.rotateAngleZ != 0.0F) GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
		if(this.rotateAngleY != 0.0F) GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		if(this.rotateAngleX != 0.0F) GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);

		GL11.glTranslatef(-this.originPointX * scale, -this.originPointY * scale, -this.originPointZ * scale);
		GL11.glTranslatef(-this.offsetX * scale, -this.offsetY * scale, -this.offsetZ * scale);

		GL11.glScalef(scale, scale, scale);

		if(doRender) {
			if(parts.length > 0)
				for(String part : parts)
					model.renderPart(part);
			else
				model.renderAll();
		}

		GL11.glPopMatrix();
	}

	@SideOnly(Side.CLIENT)
	public void postRender(float scale) {

		if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
			if(this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
				GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
			}
		} else {
			GL11.glTranslatef(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

			if(this.rotateAngleZ != 0.0F) GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			if(this.rotateAngleY != 0.0F) GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			if(this.rotateAngleX != 0.0F) GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
		}
	}
}
