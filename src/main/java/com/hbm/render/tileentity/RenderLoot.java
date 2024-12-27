package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorTrenchmaster;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.main.ResourceManager;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class RenderLoot extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.enableStandardItemLighting();
		
		TileEntityLoot loot = (TileEntityLoot) te;
		
		for(Quartet<ItemStack, Double, Double, Double> item : loot.items) {
			
			ItemStack stack = item.getW();

			GL11.glPushMatrix();
			GL11.glTranslated(item.getX(), item.getY(), item.getZ());
			
			if(stack.getItem() == ModItems.ammo_standard && stack.getItemDamage() >= EnumAmmo.NUKE_STANDARD.ordinal() && stack.getItemDamage() <= EnumAmmo.NUKE_HIVE.ordinal()) {
				renderNuke();
				
			} else if(stack.getItem() == ModItems.gun_maresleg) {
				renderShotgun();
				
			} else if(stack.getItem() instanceof ArmorTrenchmaster) {
				renderTrenchmaster(stack);
			} else {
				renderStandardItem(item.getW());
			}
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	private void renderTrenchmaster(ItemStack stack) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 1.5, 0.5);
		GL11.glScaled(0.0625, 0.0625, 0.0625);
		GL11.glRotated(180, 1, 0, 0);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(stack.getItem() == ModItems.trenchmaster_helmet) {
			bindTexture(ResourceManager.trenchmaster_helmet);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			ResourceManager.armor_trenchmaster.renderPart("Helmet");
			GL11.glDisable(GL11.GL_BLEND);
			float lastX = OpenGlHelper.lastBrightnessX;
			float lastY = OpenGlHelper.lastBrightnessY;
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glDisable(GL11.GL_LIGHTING);
			ResourceManager.armor_trenchmaster.renderPart("Light");
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
		}
		if(stack.getItem() == ModItems.trenchmaster_plate) {
			bindTexture(ResourceManager.trenchmaster_chest);
			ResourceManager.armor_trenchmaster.renderPart("Chest");
			bindTexture(ResourceManager.trenchmaster_arm);
			GL11.glPushMatrix();
			GL11.glRotated(-3, 1, 0, 0);
			ResourceManager.armor_trenchmaster.renderPart("LeftArm");
			ResourceManager.armor_trenchmaster.renderPart("RightArm");
			GL11.glPopMatrix();
		}
		if(stack.getItem() == ModItems.trenchmaster_legs) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_leg);
			ResourceManager.armor_trenchmaster.renderPart("LeftLeg");
			GL11.glPushMatrix();
			GL11.glRotated(-0.1, 1, 0, 0);
			ResourceManager.armor_trenchmaster.renderPart("RightLeg");
			GL11.glPopMatrix();
		}
		if(stack.getItem() == ModItems.trenchmaster_boots) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_leg);
			ResourceManager.armor_trenchmaster.renderPart("LeftBoot");
			GL11.glPushMatrix();
			GL11.glRotated(-0.1, 1, 0, 0);
			ResourceManager.armor_trenchmaster.renderPart("RightBoot");
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	private void renderNuke() {
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glTranslated(1, 0.5, 1);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.mini_nuke_tex);
		ResourceManager.projectiles.renderPart("MiniNuke");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	private void renderShotgun() {

		GL11.glScaled(0.125, 0.125, 0.125);
		GL11.glTranslated(3, 0, 0);
		GL11.glRotated(25, 0, 1, 0);
		GL11.glRotated(90, 1, 0, 0);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		bindTexture(ResourceManager.maresleg_tex);
		ResourceManager.maresleg.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
	
	private void renderStandardItem(ItemStack stack) {
		GL11.glTranslated(0.25, 0, 0.25);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 1, 0, 0);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		bindTexture(TextureMap.locationItemsTexture);
		
		for(int i = 0; i < stack.getItem().getRenderPasses(stack.getItemDamage()); i++) {
			
			IIcon icon = stack.getItem().getIcon(stack, i);
			float f14 = icon.getMinU();
			float f15 = icon.getMaxU();
			float f4 = icon.getMinV();
			float f5 = icon.getMaxV();

			int k1 = stack.getItem().getColorFromItemStack(stack, i);
			float f10 = (float) (k1 >> 16 & 255) / 255.0F;
			float f11 = (float) (k1 >> 8 & 255) / 255.0F;
			float f12 = (float) (k1 & 255) / 255.0F;
			GL11.glColor4f(1.0F * f10, 1.0F * f11, 1.0F * f12, 1.0F);

			ItemRenderer.renderItemIn2D(Tessellator.instance, f15, f4, f14, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		}
	}
}
