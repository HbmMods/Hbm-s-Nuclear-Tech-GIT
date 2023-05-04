package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.HorsePronter;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderCryoDistill extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z); //how did i fuck this up badly
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		
		case 2: GL11.glRotatef(-90, 0F, 1F, 0F); 
		GL11.glTranslatef(+1f, 0, -1f);
		GL11.glTranslatef(+0, 0, +0f);break;
		
		case 4: GL11.glRotatef(-180, 0F, 1F, 0F);
		GL11.glTranslatef(-1f, 0, + 0.0f);break;

		case 3: GL11.glRotatef(-270, 0F, 1F, 0F);
		break; //this is such a horrid mess i could have done this in blender but im out of options at this point....
		
		case 5: GL11.glRotatef(0, 0F, 1F, 0F);
		GL11.glTranslatef(0f, 0, +1f);
		break;
		}
		GL11.glRotated(90, 0, 1, 0);
		
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.cryodistill_tex);
		ResourceManager.cryo_distill.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);


		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_cryo_distill);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.cryodistill_tex);
				ResourceManager.cryo_distill.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
