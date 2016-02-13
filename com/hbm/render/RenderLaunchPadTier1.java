package com.hbm.render;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.TileEntityLaunchPad;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderLaunchPadTier1 extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/LaunchPad.obj");
	private static final ResourceLocation missileGenericModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileV2.obj");
	private static final ResourceLocation missileStrongModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileGeneric.obj");
	private static final ResourceLocation missileHugeModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileHuge.obj");
	private static final ResourceLocation missileNuclearModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileNuke.obj");
	private static final ResourceLocation missileMirvModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileMirv.obj");
	private static final ResourceLocation missileThermoModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileThermal.obj");
	private IModelCustom padModel;
	private IModelCustom missileGeneric;
	private IModelCustom missileStrong;
	private IModelCustom missileHuge;
	private IModelCustom missileNuclear;
	private IModelCustom missileMirv;
	private IModelCustom missileThermo;
    private ResourceLocation padTexture;
    private ResourceLocation missileGenericTexture;
    private ResourceLocation missileIncendiaryTexture;
    private ResourceLocation missileClusterTexture;
    private ResourceLocation missileBusterTexture;
    private ResourceLocation missileStrongTexture;
    private ResourceLocation missileStrongIncendiaryTexture;
    private ResourceLocation missileStrongClusterTexture;
    private ResourceLocation missileStrongBusterTexture;
    private ResourceLocation missileHugeTexture;
    private ResourceLocation missileHugeIncendiaryTexture;
    private ResourceLocation missileHugeClusterTexture;
    private ResourceLocation missileHugeBusterTexture;
    private ResourceLocation missileNuclearTexture;
    private ResourceLocation missileMirvTexture;
    private ResourceLocation missileThermoEndoTexture;
    private ResourceLocation missileThermoExoTexture;
	
	public RenderLaunchPadTier1()
    {
		padModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		missileGeneric = AdvancedModelLoader.loadModel(missileGenericModel);
		missileStrong = AdvancedModelLoader.loadModel(missileStrongModel);
		missileHuge = AdvancedModelLoader.loadModel(missileHugeModel);
		missileNuclear = AdvancedModelLoader.loadModel(missileNuclearModel);
		missileMirv = AdvancedModelLoader.loadModel(missileMirvModel);
		missileThermo = AdvancedModelLoader.loadModel(missileThermoModel);
		padTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
		missileGenericTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileV2.png");
		missileIncendiaryTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileIncendiary.png");
		missileClusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileCluster.png");
		missileBusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileBuster.png");
		missileStrongTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileGeneric.png");
		missileStrongIncendiaryTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileGenericIncendiary.png");
		missileStrongClusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileGenericCluster.png");
		missileStrongBusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileGenericBuster.png");
		missileHugeTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHuge.png");
		missileHugeIncendiaryTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeIncendiary.png");
		missileHugeClusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeCluster.png");
		missileHugeBusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeBuster.png");
		missileNuclearTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileNuke.png");
		missileMirvTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileMirv.png");
		missileThermoEndoTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileThermalEndo.png");
		missileThermoExoTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileThermalExo.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(padTexture);
        padModel.renderAll();
		
		//if(((TileEntityLaunchPad)tileEntity).getThatWorld().isRemote)
		{
	        GL11.glTranslated(0, 0.5, 0);
	        
			if(((TileEntityLaunchPad)tileEntity).state == 1)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(missileGenericTexture);
				missileGeneric.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 2)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileStrongTexture);
				missileStrong.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 3)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(missileClusterTexture);
				missileGeneric.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 4)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileNuclearTexture);
				missileNuclear.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 5)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(missileIncendiaryTexture);
				missileGeneric.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 6)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(missileBusterTexture);
				missileGeneric.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 7)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileStrongIncendiaryTexture);
				missileStrong.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 8)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileStrongClusterTexture);
				missileStrong.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 9)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileStrongBusterTexture);
				missileStrong.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 10)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(missileHugeTexture);
				missileHuge.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 11)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(missileHugeIncendiaryTexture);
				missileHuge.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 12)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(missileHugeClusterTexture);
				missileHuge.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 13)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(missileHugeBusterTexture);
				missileHuge.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 14)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileThermoEndoTexture);
				missileThermo.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 15)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(missileThermoExoTexture);
				missileThermo.renderAll();
			}
			if(((TileEntityLaunchPad)tileEntity).state == 16)
			{
		        GL11.glScalef(2F, 2F, 2F);
				bindTexture(missileMirvTexture);
				missileMirv.renderAll();
			}
		}

        GL11.glPopMatrix();
    }

}
