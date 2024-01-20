package com.hbm.render.tileentity;

import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderMissileGeneric;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderLaunchPadTier1 extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		bindTexture(ResourceManager.missile_pad_tex);
		ResourceManager.missile_pad.renderAll();

		GL11.glDisable(GL11.GL_CULL_FACE);

		if(tileEntity instanceof TileEntityLaunchPad) {
			ItemStack toRender = ((TileEntityLaunchPad) tileEntity).toRender;
			
			if(toRender != null) {
				GL11.glTranslated(0, 1, 0);
				Consumer<TextureManager> renderer = ItemRenderMissileGeneric.renderers.get(new ComparableStack(toRender).makeSingular());
				if(renderer != null) renderer.accept(this.field_147501_a.field_147553_e);
			}
		}

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

}
