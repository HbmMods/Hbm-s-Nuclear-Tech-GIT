package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.fusion.TileEntityFusionPlasmaForge;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Clock;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFusionPlasmaForge extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static EntityItem dummy;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glRotatef(90, 0F, 1F, 0F);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityFusionPlasmaForge forge = (TileEntityFusionPlasmaForge) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_plasma_forge_tex);
		ResourceManager.fusion_plasma_forge.renderPart("Body");

		GenericRecipe recipe = PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(forge.plasmaModule.recipe);
		
		renderPlasma(forge);
		renderItem(forge, recipe, interp);
		renderBeam(forge, recipe, interp);
		
		bindTexture(ResourceManager.fusion_plasma_forge_tex);
		
		double[] striker = forge.armStriker.getPositions(interp);
		double[] jet = forge.armJet.getPositions(interp);
		double rotor = forge.prevRing + (forge.ring - forge.prevRing) * interp;
		
		GL11.glPushMatrix(); { // brackets added in post for readability for the common person. i don't have such weaknesses.
			GL11.glRotated(rotor, 0, 1, 0);
			GL11.glPushMatrix(); {
				ResourceManager.fusion_plasma_forge.renderPart("SliderStriker");
				GL11.glTranslated(-2.75, 2.5, 0);
				GL11.glRotated(-striker[0], 0, 0, 1);
				GL11.glTranslated(2.75, -2.5, 0);
				ResourceManager.fusion_plasma_forge.renderPart("ArmLowerStriker");
				GL11.glTranslated(-2.75, 3.75, 0);
				GL11.glRotated(-striker[1], 0, 0, 1);
				GL11.glTranslated(2.75, -3.75, 0);
				ResourceManager.fusion_plasma_forge.renderPart("ArmUpperStriker");
				GL11.glTranslated(-1.5, 3.75, 0);
				GL11.glRotated(-striker[2], 0, 0, 1);
				GL11.glTranslated(1.5, -3.75, 0);
				ResourceManager.fusion_plasma_forge.renderPart("StrikerMount");
				GL11.glPushMatrix(); {
					GL11.glTranslated(0, 3.375, 0.5);
					GL11.glRotated(striker[3], 1, 0, 0);
					GL11.glTranslated(0, -3.375, -0.5);
					ResourceManager.fusion_plasma_forge.renderPart("StrikerRight");
					GL11.glTranslated(0, -striker[4], 0);
					ResourceManager.fusion_plasma_forge.renderPart("PistonRight");
				} GL11.glPopMatrix();
				GL11.glPushMatrix(); {
					GL11.glTranslated(0, 3.375, -0.5);
					GL11.glRotated(-striker[3], 1, 0, 0);
					GL11.glTranslated(0, -3.375, 0.5);
					ResourceManager.fusion_plasma_forge.renderPart("StrikerLeft");
					GL11.glTranslated(0, -striker[5], 0);
					ResourceManager.fusion_plasma_forge.renderPart("PistonLeft");
				} GL11.glPopMatrix();
			} GL11.glPopMatrix();
	
			GL11.glPushMatrix(); {
				ResourceManager.fusion_plasma_forge.renderPart("SliderJet");
				GL11.glTranslated(2.75, 2.5, 0);
				GL11.glRotated(jet[0], 0, 0, 1);
				GL11.glTranslated(-2.75, -2.5, 0);
				ResourceManager.fusion_plasma_forge.renderPart("ArmLowerJet");
				GL11.glTranslated(2.75, 3.75, 0);
				GL11.glRotated(jet[1], 0, 0, 1);
				GL11.glTranslated(-2.75, -3.75, 0);
				ResourceManager.fusion_plasma_forge.renderPart("ArmUpperJet");
				GL11.glTranslated(1.5, 3.75, 0);
				GL11.glRotated(jet[2], 0, 0, 1);
				GL11.glTranslated(-1.5, -3.75, 0);
				ResourceManager.fusion_plasma_forge.renderPart("Jet");
				if(forge.didProcess && forge.armJet.angles[2] == forge.armJet.prevAngles[2]) renderJet(forge);
			} GL11.glPopMatrix();
		} GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	protected void renderJet(TileEntityFusionPlasmaForge forge) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1F, 1F, 1F);
		
		double outerLen = 1 + MainRegistry.proxy.me().getRNG().nextDouble() * 0.125;
		double narrow = 0.01;
		double side = 0.125;
		double near = 1.375;
		double far = 1.625;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, side - narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, side - narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, -side + narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, side - narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, side - narrow);
		
		narrow = 0.0625 * 1.5;
		outerLen *= 1.5;

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, side - narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, side - narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, -side + narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(near, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(near + narrow, 3 - outerLen, side - narrow);

		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 1F); tess.addVertex(far, 3, -side);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, -side + narrow);
		tess.setColorRGBA_F(forge.plasmaRed, forge.plasmaGreen, forge.plasmaBlue, 0F); tess.addVertex(far - narrow, 3 - outerLen, side - narrow);
		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected void renderPlasma(TileEntityFusionPlasmaForge forge) {
		
		if(forge.plasmaEnergySync <= 0) {
			GL11.glColor3f(0F, 0F, 0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1F, 1F, 1F);
		} else {
			RenderArcFurnace.fullbright(true);
			long time = Clock.get_ms() + forge.timeOffset;
			float alpha = 0.5F + (float) (Math.sin(time / 500D) * 0.25F);
			double mainOsc = BobMathUtil.sps(time / 750D) % 1D;
			double glowOsc = Math.sin(time / 1000D) % 1D;
			double glowExtra = time / 10000D % 1D;
			
			GL11.glColor3f(forge.plasmaRed * alpha, forge.plasmaGreen * alpha, forge.plasmaBlue * alpha);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_tex);
			GL11.glTranslated(0, mainOsc, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDepthMask(false);

			GL11.glColor3f(forge.plasmaRed * 2, forge.plasmaGreen * 2, forge.plasmaBlue * 2);
			
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_glow_tex);
			GL11.glTranslated(0, glowOsc + glowExtra, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			glowOsc = Math.sin(time / 600D + 2) % 1D;
			glowExtra = time / 5000D % 1D;
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_glow_tex);
			GL11.glTranslated(0, glowOsc + glowExtra, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
			
			RenderArcFurnace.fullbright(false);
		}
		GL11.glColor3f(1F, 1F, 1F);
	}

	protected void renderItem(TileEntityFusionPlasmaForge forge, GenericRecipe recipe, float interp) {
		if(recipe == null) return;
		if(MainRegistry.proxy.me().getDistanceSq(forge.xCoord + 0.5, forge.yCoord + 1, forge.zCoord + 0.5) > 35 * 35) return;

		GL11.glPushMatrix();
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 1.75, 0);

		ItemStack stack = recipe.getIcon();
		stack.stackSize = 1;

		if(stack.getItemSpriteNumber() == 0 && stack.getItem() instanceof ItemBlock) {
			if(RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType())) {
				GL11.glTranslated(0, -0.0625, 0);
			} else {
				GL11.glScaled(0.5, 0.5, 0.5);
			}
		} else {
			GL11.glRotated(90, 0, 1, 0);
		}

		GL11.glTranslated(0, Math.sin((MainRegistry.proxy.me().ticksExisted + interp) * 0.1) * 0.0625, 0);
		GL11.glScaled(1.5, 1.5, 1.5);

		if(dummy == null || dummy.worldObj != forge.getWorldObj()) dummy = new EntityItem(forge.getWorldObj(), 0, 0, 0, stack);
		dummy.setEntityItemStack(stack);
		dummy.hoverStart = 0.0F;

		RenderItem.renderInFrame = true;
		RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		GL11.glPopMatrix();
	}
	
	// beam renders after the item so the transparency works correctly. beam is also larger, so the LOD sets off a bit later
	public void renderBeam(TileEntityFusionPlasmaForge forge, GenericRecipe recipe, float interp) {
		if(recipe == null) return;
		if(MainRegistry.proxy.me().getDistanceSq(forge.xCoord + 0.5, forge.yCoord + 1, forge.zCoord + 0.5) > 50 * 50) return;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		// i fucking love reusing textures
		this.bindTexture(Fluids.STELLAR_FLUX.getTexture());
		
		double offset = ((MainRegistry.proxy.me().ticksExisted + interp) / 15D) % 1D;
		double in = 0.4375;
		double b = 1;
		double t = 1.5;
		double h = b + t;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(-in, b, in, offset + t, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(-in, h, in, offset + 0, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(-in, h, -in, offset + 0, 1);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(-in, b, -in, offset + t, 1);

		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(in, h, in, offset + 0, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(in, b, in, offset + t, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(in, b, -in, offset + t, 1);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(in, h, -in, offset + 0, 1);

		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(in, b, in, offset + t, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(in, h, in, offset + 0, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(-in, h, in, offset + 0, 1);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(-in, b, in, offset + t, 1);

		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(in, h, -in, offset + 0, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(in, b, -in, offset + t, 0);
		tess.setColorRGBA_F(1F, 1F, 1F, 1F); tess.addVertexWithUV(-in, b, -in, offset + t, 1);
		tess.setColorRGBA_F(1F, 1F, 1F, 0F); tess.addVertexWithUV(-in, h, -in, offset + 0, 1);
		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fusion_plasma_forge);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
				GL11.glRotated(90, 0, 1, 0);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fusion_plasma_forge_tex);
				ResourceManager.fusion_plasma_forge.renderAllExcept("Plasma");
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(0F, 0F, 0F);
				ResourceManager.fusion_plasma_forge.renderPart("Plasma");
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
