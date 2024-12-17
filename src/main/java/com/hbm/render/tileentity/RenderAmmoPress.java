package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineAmmoPress;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderAmmoPress extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		TileEntityMachineAmmoPress tile = (TileEntityMachineAmmoPress) tileEntity;

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		float press = tile.prevPress + (tile.press - tile.prevPress) * f;
		float lift = tile.prevLift + (tile.lift - tile.prevLift) * f;

		bindTexture(ResourceManager.ammo_press_tex);
		ResourceManager.ammo_press.renderPart("Frame");
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0, -press * 0.25F, 0);
		ResourceManager.ammo_press.renderPart("Press");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0, lift * 0.5F - 0.5F, 0);
		ResourceManager.ammo_press.renderPart("Shells");
		if(tile.animState == tile.animState.RETRACTING || tile.animState == tile.animState.LOWERING) ResourceManager.ammo_press.renderPart("Bullets");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_ammo_press);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				bindTexture(ResourceManager.ammo_press_tex);
				ResourceManager.ammo_press.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
