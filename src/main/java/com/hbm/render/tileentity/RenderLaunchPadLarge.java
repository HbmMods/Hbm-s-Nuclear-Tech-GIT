package com.hbm.render.tileentity;

import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.MissileFormFactor;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.item.ItemRenderMissileGeneric;
import com.hbm.tileentity.bomb.TileEntityLaunchPadLarge;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderLaunchPadLarge extends TileEntitySpecialRenderer implements IItemRendererProvider {

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
		
		TileEntityLaunchPadLarge pad = (TileEntityLaunchPadLarge) tileEntity;

		bindTexture(ResourceManager.missile_erector_tex);
		ResourceManager.missile_erector.renderPart("Pad");
		
		if(pad.formFactor >= 0 && pad.formFactor < ItemMissile.MissileFormFactor.values().length) {
			
			MissileFormFactor formFactor = MissileFormFactor.values()[pad.formFactor];
			String[] parts = null;
			double[] offset = null;
			
			switch(formFactor) {
			case ABM: parts = new String[] {"ABM_Pad", "ABM_Erector", "ABM_Pivot", "ABM_Rope"};
			offset = new double[] {1.5D, 1.25D};
			bindTexture(ResourceManager.missile_erector_abm_tex); break;
			case MICRO: parts = new String[] {"Micro_Pad", "Micro_Erector", "Micro_Pivot", "Micro_Rope"};
			offset = new double[] {1.5D, 1.25D};
			bindTexture(ResourceManager.missile_erector_micro_tex); break;
			case V2: parts = new String[] {"V2_Pad", "V2_Erector", "V2_Pivot", "V2_Rope"};
			offset = new double[] {1.75D, 1.25D};
			bindTexture(ResourceManager.missile_erector_v2_tex); break;
			case STRONG: parts = new String[] {"Strong_Pad", "Strong_Erector", "Strong_Pivot", "Strong_Rope"};
			offset = new double[] {3D, 1.5D};
			bindTexture(ResourceManager.missile_erector_strong_tex); break;
			case HUGE: parts = new String[] {"Huge_Pad", "Huge_Erector", "Huge_Pivot", "Huge_Rope"};
			offset = new double[] {3D, 1.5D};
			bindTexture(ResourceManager.missile_erector_huge_tex); break;
			case ATLAS: parts = new String[] {"Atlas_Pad", "Atlas_Erector", "Atlas_Pivot", "Atlas_Rope"};
			offset = new double[] {4D, 1.5D};
			bindTexture(ResourceManager.missile_erector_atlas_tex); break;
			case OTHER: parts = new String[] {"ABM_Pad", "ABM_Erector", "ABM_Pivot", "ABM_Rope"};
			offset = new double[] {1.5D, 1.25D};
			bindTexture(ResourceManager.missile_erector_abm_tex); break;
			}
			
			float erectorAngle = pad.prevErector + (pad.erector - pad.prevErector) * f;
			float erectorLift = pad.prevLift + (pad.lift - pad.prevLift) * f;

			GL11.glPushMatrix();
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.missile_erector.renderPart(parts[0]);
			if(pad.toRender != null && pad.erected) ResourceManager.missile_erector.renderPart(parts[3]);
			GL11.glTranslated(0, offset[1], -offset[0]);
			GL11.glRotatef(-erectorAngle, 1, 0, 0);
			GL11.glTranslated(0, -offset[1], offset[0]);
			ResourceManager.missile_erector.renderPart(parts[2]);
			GL11.glTranslatef(0, erectorLift, 0);
			ResourceManager.missile_erector.renderPart(parts[1]);
			GL11.glShadeModel(GL11.GL_FLAT);
			
			if(pad.erected) {
				GL11.glPopMatrix();
				GL11.glPushMatrix();
			}
			
			if(pad.toRender != null && (pad.erected || pad.readyToLoad)) {
				GL11.glTranslated(0, 2, 0);
				Consumer<TextureManager> renderer = ItemRenderMissileGeneric.renderers.get(new ComparableStack(pad.toRender).makeSingular());
				if(renderer != null) renderer.accept(this.field_147501_a.field_147553_e);
			}
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.launch_pad_large);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3.75, 0);
				GL11.glScaled(1.625, 1.625, 1.625);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.missile_erector_tex);
				ResourceManager.missile_erector.renderPart("Pad");
				bindTexture(ResourceManager.missile_erector_atlas_tex);
				ResourceManager.missile_erector.renderPart("Atlas_Pad");
				ResourceManager.missile_erector.renderPart("Atlas_Erector");
				ResourceManager.missile_erector.renderPart("Atlas_Pivot");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
