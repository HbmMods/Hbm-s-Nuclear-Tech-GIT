package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelCalBarrel;
import com.hbm.render.model.ModelCalDualStock;
import com.hbm.render.model.ModelCalStock;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.render.util.TomPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RendererObjTester extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/TestObj.obj");
	//private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Prototype.obj");
	private IModelCustom objTesterModel;
    private ResourceLocation objTesterTexture;
	
	public RendererObjTester()
    {
		objTesterModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		objTesterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TestObj.png");
		//objTesterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Prototype.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
		/*switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}*/

        /*bindTexture(objTesterTexture);
        objTesterModel.renderAll();*/
        
		//GL11.glScaled(5, 5, 5);
		
        /*GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.sat_foeq_burning_tex);
		ResourceManager.sat_foeq_burning.renderAll();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		
		Random rand = new Random(System.currentTimeMillis() / 50);

        GL11.glScaled(1.15, 0.75, 1.15);
        GL11.glTranslated(0, -0.5, 0.3);
        GL11.glDisable(GL11.GL_CULL_FACE);
		for(int i = 0; i < 10; i++) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor3d(1, 0.75, 0.25);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.5, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.25, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.0, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
			
	        GL11.glTranslated(0, -3.8, 0);
	        
	        GL11.glScaled(0.95, 1.2, 0.95);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);*/

        /*ModelCalBarrel barrel = new ModelCalBarrel();
        ModelCalStock stock = new ModelCalStock();
        ModelCalDualStock saddle = new ModelCalDualStock();

        bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/ModelCalDualStock.png"));
        saddle.renderAll(1F/16F);
        
        bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/ModelCalBarrel.png"));
        GL11.glTranslated(0, 0, -0.25);
        barrel.renderAll(1F/16F);
        GL11.glTranslated(0, 0, 0.5);
        barrel.renderAll(1F/16F);
        
        bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/ModelCalStock.png"));*/
        //stock.renderAll(1F/16F);
        
        //SoyuzPronter.prontSoyuz();
        //TomPronter.prontTom();
        BeamPronter.prontHelix(Vec3.createVectorHelper(5, 5, 5), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0xff8000, 0xff8000, (int)tileEntity.getWorldObj().getTotalWorldTime() % 360 * 25, 25, 0.1F, 4, 0.05F);
        BeamPronter.prontHelix(Vec3.createVectorHelper(5, 5, 5), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0xffff00, 0xffff00, (int)tileEntity.getWorldObj().getTotalWorldTime() % 360 * 25, 1, 0F, 4, 0.05F);
        //BeamPronter.prontHelix(Vec3.createVectorHelper(0, 5, 0), 0.5, 0.5, 0.5, EnumWaveType.SPIRAL, EnumBeamType.LINE, 0x0000ff, 0xffff00, (int)tileEntity.getWorldObj().getTotalWorldTime() % 360 * 25 + 180, 25, 0.25F);

        GL11.glPopMatrix();
    }

}