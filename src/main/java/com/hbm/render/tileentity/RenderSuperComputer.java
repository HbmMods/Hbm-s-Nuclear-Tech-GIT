package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineSuperComputer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderSuperComputer extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineSuperComputer computer = (TileEntityMachineSuperComputer) tileEntity;
		
		bindTexture(ResourceManager.supercomputer_tex);
		ResourceManager.supercomputer.renderPart("Computer");
		
		if(!computer.didProcess) GL11.glColor3f(0F, 0F, 0F);
		
		float scroll = tileEntity.getWorldObj().getTotalWorldTime() % 20 + interp;
		scroll /= 20F;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		
		bindTexture(ResourceManager.supercomputer_scan_tex);
		GL11.glTranslatef(-scroll, 0, 0);
		ResourceManager.supercomputer.renderPart("Lights");
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glColor3f(1F, 1F, 1F);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_supercomputer);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glTranslated(-2, 0, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.supercomputer_tex);
				ResourceManager.supercomputer.renderPart("Computer");
				bindTexture(ResourceManager.supercomputer_scan_tex);
				ResourceManager.supercomputer.renderPart("Lights");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
