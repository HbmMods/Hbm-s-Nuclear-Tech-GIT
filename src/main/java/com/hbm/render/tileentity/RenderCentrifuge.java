package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCentrifuge extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(tileEntity instanceof TileEntityMachineCentrifuge) {
			bindTexture(ResourceManager.centrifuge_tex);
			ResourceManager.centrifuge.renderAll();
		}

		if(tileEntity instanceof TileEntityMachineGasCent) {
			GL11.glRotatef(180, 0F, 1F, 0F);
			bindTexture(ResourceManager.gascent_tex);
			ResourceManager.gascent.renderPart("Centrifuge");
			ResourceManager.gascent.renderPart("Flag");
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_centrifuge);
	}

	@Override
	public Item[] getItemsForRenderer() {
		return new Item[] {
				Item.getItemFromBlock(ModBlocks.machine_centrifuge),
				Item.getItemFromBlock(ModBlocks.machine_gascent)
		};
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				if(item.getItem() == Item.getItemFromBlock(ModBlocks.machine_gascent)) {
					bindTexture(ResourceManager.gascent_tex); ResourceManager.gascent.renderPart("Centrifuge");
				} else {
					bindTexture(ResourceManager.centrifuge_tex); ResourceManager.centrifuge.renderAll();
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}
}
