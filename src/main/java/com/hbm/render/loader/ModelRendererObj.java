package com.hbm.render.loader;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.IModelCustom;

public class ModelRendererObj {
	
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    
    String[] parts;
    IModelCustom model;
    
    public ModelRendererObj(IModelCustom model, String... parts) {
    	this.model = model;
    	this.parts = parts;
    }

    @SideOnly(Side.CLIENT)
    public void render(float p_78785_1_) {

        GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
    	
    }

}
