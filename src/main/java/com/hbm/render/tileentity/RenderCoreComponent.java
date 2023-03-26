package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderCoreComponent extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public RenderCoreComponent() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(90, 0F, 1F, 0F);
        
		switch(tileEntity.getBlockMetadata()) {
		case 0:
	        GL11.glTranslated(0.0D, 0.5D, -0.5D);
			GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 1:
	        GL11.glTranslated(0.0D, 0.5D, 0.5D);
			GL11.glRotatef(90, -1F, 0F, 0F); break;
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
        GL11.glTranslated(0.0D, 0D, 0.0D);

        if(tileEntity instanceof TileEntityCoreEmitter) {
	        bindTexture(ResourceManager.dfc_emitter_tex);
	        ResourceManager.dfc_emitter.renderAll();
	        GL11.glTranslated(0, 0.5, 0);
	        int range = ((TileEntityCoreEmitter)tileEntity).beam;
	        
	        if(range > 0) {
		        BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x404000, 0x404000, 0, 1, 0F, 2, 0.0625F);
		        BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x401500, 0x401500, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000, range * 2, 0.125F, 4, 0.0625F);
		        BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x401500, 0x401500, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000 + 1, range * 2, 0.125F, 4, 0.0625F);
	        }
        }

        if(tileEntity instanceof TileEntityCoreReceiver) {
	        bindTexture(ResourceManager.dfc_receiver_tex);
	        ResourceManager.dfc_receiver.renderAll();
        }

        if(tileEntity instanceof TileEntityCoreInjector) {
	        bindTexture(ResourceManager.dfc_injector_tex);
	        ResourceManager.dfc_injector.renderAll();
	        
	        GL11.glTranslated(0, 0.5, 0);
	        TileEntityCoreInjector injector = (TileEntityCoreInjector)tileEntity;
	        int range = injector.beam;
	        
	        if(range > 0) {
	        	
	        	if(injector.tanks[0].getFill() > 0)
	        		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.LINE, injector.tanks[0].getTankType().getColor(), 0x808080, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000, range, 0.0625F, 0, 0);
	        	if(injector.tanks[1].getFill() > 0)
	        		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.LINE, injector.tanks[1].getTankType().getColor(), 0x808080, (int)tileEntity.getWorldObj().getTotalWorldTime() % 1000 + 1, range, 0.0625F, 0, 0);
	        }
        }

        if(tileEntity instanceof TileEntityCoreStabilizer) {
	        bindTexture(ResourceManager.dfc_stabilizer_tex);
	        ResourceManager.dfc_injector.renderAll();

	        
	        GL11.glTranslated(0, 0.5, 0);
	        TileEntityCoreStabilizer stabilizer = (TileEntityCoreStabilizer)tileEntity;
	        int range = stabilizer.beam;

	        if(range > 0) {
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)tileEntity.getWorldObj().getTotalWorldTime() * -25 % 360, range * 3, 0.125F, 0, 0);
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)tileEntity.getWorldObj().getTotalWorldTime() * -15 % 360 + 180, range * 3, 0.125F, 0, 0);
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.LINE, 0xffa200, 0xffd000, (int)tileEntity.getWorldObj().getTotalWorldTime() * -5 % 360 + 180, range * 3, 0.125F, 0, 0);
	        }
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

	@Override
	public Item getItemForRenderer() { return null; }

	@Override
	public Item[] getItemsForRenderer() {
		return new Item[] {
				Item.getItemFromBlock(ModBlocks.dfc_emitter),
				Item.getItemFromBlock(ModBlocks.dfc_receiver),
				Item.getItemFromBlock(ModBlocks.dfc_injector),
				Item.getItemFromBlock(ModBlocks.dfc_stabilizer)
		};
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				double scale = 5;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(2, 2, 2);
				GL11.glRotated(90, 0, 1, 0);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.dfc_emitter)) {
					bindTexture(ResourceManager.dfc_emitter_tex);
					ResourceManager.dfc_emitter.renderAll();
				}
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.dfc_receiver)) {
					bindTexture(ResourceManager.dfc_receiver_tex);
					ResourceManager.dfc_receiver.renderAll();
				}
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.dfc_injector)) {
					bindTexture(ResourceManager.dfc_injector_tex);
					ResourceManager.dfc_injector.renderAll();
				}
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.dfc_stabilizer)) {
					bindTexture(ResourceManager.dfc_stabilizer_tex);
					ResourceManager.dfc_injector.renderAll();
				}
			}};
	}
}
