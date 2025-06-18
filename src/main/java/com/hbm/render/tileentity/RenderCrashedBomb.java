package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockCrashedBomb.EnumDudType;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCrashedBomb extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static Random rand = new Random();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		rand.setSeed(BlockPos.getIdentity(tile.xCoord, tile.yCoord, tile.zCoord));
		double yaw = rand.nextDouble() * 360;
		double pitch = rand.nextDouble() * 45 + 45;
		double roll = rand.nextDouble() * 360;
		double offset = rand.nextDouble() * 2 - 1;

		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glRotated(roll, 0, 0, 1);
		GL11.glTranslated(0, 0, -offset);
		
		EnumDudType type = EnumUtil.grabEnumSafely(EnumDudType.class, tile.getBlockMetadata());
		GL11.glShadeModel(GL11.GL_SMOOTH);
		if(type == EnumDudType.BALEFIRE) { bindTexture(ResourceManager.dud_balefire_tex); ResourceManager.dud_balefire.renderAll(); }
		if(type == EnumDudType.CONVENTIONAL) { bindTexture(ResourceManager.dud_conventional_tex); ResourceManager.dud_conventional.renderAll(); }
		if(type == EnumDudType.NUKE) { GL11.glTranslated(0, 0, 1.25); bindTexture(ResourceManager.dud_nuke_tex); ResourceManager.dud_nuke.renderAll(); }
		if(type == EnumDudType.SALTED) { GL11.glTranslated(0, 0, 0.5); bindTexture(ResourceManager.dud_salted_tex); ResourceManager.dud_salted.renderAll(); }
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.crashed_balefire);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, 3, 0);
				GL11.glScaled(2.125, 2.125, 2.125);
				GL11.glRotated(90, 0, 0, 1);
			}
			public void renderCommonWithStack(ItemStack item) {
				EnumDudType type = EnumUtil.grabEnumSafely(EnumDudType.class, item.getItemDamage());
				GL11.glRotated(90, 0, 1, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				if(type == EnumDudType.BALEFIRE) { bindTexture(ResourceManager.dud_balefire_tex); ResourceManager.dud_balefire.renderAll(); }
				if(type == EnumDudType.CONVENTIONAL) { GL11.glTranslated(0, 0, -0.5); bindTexture(ResourceManager.dud_conventional_tex); ResourceManager.dud_conventional.renderAll(); }
				if(type == EnumDudType.NUKE) { GL11.glTranslated(0, 0, 1.25); bindTexture(ResourceManager.dud_nuke_tex); ResourceManager.dud_nuke.renderAll(); }
				if(type == EnumDudType.SALTED) { GL11.glTranslated(0, 0, 0.5); bindTexture(ResourceManager.dud_salted_tex); ResourceManager.dud_salted.renderAll(); }
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
