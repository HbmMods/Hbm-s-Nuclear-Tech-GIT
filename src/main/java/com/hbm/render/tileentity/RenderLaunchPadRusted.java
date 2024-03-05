package com.hbm.render.tileentity;

import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.item.ItemRenderMissileGeneric;
import com.hbm.tileentity.bomb.TileEntityLaunchPadRusted;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderLaunchPadRusted extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.missile_pad_rusted_tex);
		ResourceManager.missile_pad.renderAll();

		if(tileEntity instanceof TileEntityLaunchPadRusted) {
			TileEntityLaunchPadRusted launchpad = (TileEntityLaunchPadRusted) tileEntity;
			if(launchpad.missileLoaded) {
				GL11.glTranslated(0, 1, 0);
				Consumer<TextureManager> renderer = ItemRenderMissileGeneric.renderers.get(new ComparableStack(ModItems.missile_doomsday_rusted).makeSingular());
				if(renderer != null) renderer.accept(this.field_147501_a.field_147553_e);
			}
		}

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.launch_pad_rusted);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			@Override public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(3, 3, 3);
			}
			@Override public void renderCommon() {
				bindTexture(ResourceManager.missile_pad_rusted_tex); ResourceManager.missile_pad.renderAll();
			}};
	}
}
