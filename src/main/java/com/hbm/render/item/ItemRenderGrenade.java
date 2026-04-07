package com.hbm.render.item;

import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.tileentity.RenderArcFurnace;
import com.hbm.util.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderGrenade implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			renderFirstPerson(stack);
		}
		
		if(type == ItemRenderType.INVENTORY) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glTranslated(8, 8, 0);
			GL11.glScaled(-1, -1, -1);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(150, 0, 1, 0);
			GL11.glRotated(15, 1, 0, 0);
			renderGrenade(stack, type);
		}
		
		if(type == ItemRenderType.EQUIPPED) {
			GL11.glScaled(0.125, 0.125, 0.125);
			GL11.glTranslated(3, 1, -0.5);
			renderGrenade(stack, type);
		}
		
		if(type == ItemRenderType.ENTITY) {
			GL11.glScaled(0.125, 0.125, 0.125);
			renderGrenade(stack, type);
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public static void renderFirstPerson(ItemStack stack) {
		EnumGrenadeShell shell = ItemGrenadeUniversal.getShell(stack);
		
		GL11.glScaled(0.125, 0.125, 0.125);
		GL11.glTranslated(3, 1, -3);
		GL11.glRotated(180, 0, -1, 0);
		
		if(shell == EnumGrenadeShell.FRAG) {
			double[] bodyMove = HbmAnimations.getRelevantTransformation("BODYMOVE");
			double[] bodyTurn = HbmAnimations.getRelevantTransformation("BODYTURN");
			double[] ringMove = HbmAnimations.getRelevantTransformation("RINGMOVE");
			double[] ringTurn = HbmAnimations.getRelevantTransformation("RINGTURN");
			double[] renderRing = HbmAnimations.getRelevantTransformation("RENDERRING");
			GL11.glTranslated(bodyMove[0], bodyMove[1], bodyMove[2]);
			GL11.glRotated(bodyTurn[2], 1, 0, 0);
			renderFragBody(stack);
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_frag_tex);
			ResourceManager.grenades.renderPart("FragSpoon");
			if(renderRing[0] != 0) {
				GL11.glTranslated(ringMove[0], ringMove[1], ringMove[2]);
				GL11.glRotated(ringTurn[2], 1, 0, 0);
				ResourceManager.grenades.renderPart("FragRing");
			}
		}
		
		if(shell == EnumGrenadeShell.STICK) {
			double[] bodyMove = HbmAnimations.getRelevantTransformation("BODYMOVE");
			double[] bodyTurn = HbmAnimations.getRelevantTransformation("BODYTURN");
			double[] capMove = HbmAnimations.getRelevantTransformation("CAPMOVE");
			double[] capTurn = HbmAnimations.getRelevantTransformation("CAPTURN");
			double[] renderCap = HbmAnimations.getRelevantTransformation("RENDERCAP");
			GL11.glTranslated(bodyMove[0], bodyMove[1], bodyMove[2]);
			GL11.glRotated(bodyTurn[2], 0, 0, 1);
			renderStickBody(stack);
			if(renderCap[0] != 0) {
				GL11.glTranslated(capMove[0], capMove[1], capMove[2]);
				GL11.glRotated(capTurn[1], 0, 1, 0);
				EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(stack);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				bind(ResourceManager.grenade_stick_tex);
				ResourceManager.grenades.renderPart("StickCap");
				GL11.glColor3f(ColorUtil.fr(filling.bodyColor), ColorUtil.fg(filling.bodyColor), ColorUtil.fb(filling.bodyColor));
				bind(ResourceManager.grenade_stick_body_tex);
				ResourceManager.grenades.renderPart("StickCap");
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		
		if(shell == EnumGrenadeShell.TECH) {
			double[] bodyMove = HbmAnimations.getRelevantTransformation("BODYMOVE");
			double[] bodyTurn = HbmAnimations.getRelevantTransformation("BODYTURN");
			double[] ringMove = HbmAnimations.getRelevantTransformation("RINGMOVE");
			double[] ringTurn = HbmAnimations.getRelevantTransformation("RINGTURN");
			double[] renderRing = HbmAnimations.getRelevantTransformation("RENDERRING");
			GL11.glTranslated(bodyMove[0], bodyMove[1], bodyMove[2]);
			GL11.glRotated(bodyTurn[2], 1, 0, 0);
			renderTechBody(stack);
			if(renderRing[0] != 0) {
				GL11.glTranslated(ringMove[0], ringMove[1], ringMove[2]);
				GL11.glRotated(ringTurn[2], 1, 0, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_tech_tex);
				ResourceManager.grenades.renderPart("TechRing");
			}
		}
		
		if(shell == EnumGrenadeShell.NUKE) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_nuka_tex);
			ResourceManager.grenades.renderPart("Nuka");
			ResourceManager.grenades.renderPart("NukaSpoon");
			ResourceManager.grenades.renderPart("NukaRing");
		}
	}
	
	public static void renderGrenade(ItemStack stack, ItemRenderType renderType) {
		EnumGrenadeShell shell = ItemGrenadeUniversal.getShell(stack);
		
		if(shell == EnumGrenadeShell.FRAG) {
			if(renderType == ItemRenderType.INVENTORY) {
				GL11.glScaled(3, 3, 3);
				GL11.glTranslated(0, -2, 0);
			}
			if(renderType == null) {
				GL11.glTranslated(0, -2, 0);
			}
			renderFragBody(stack);
			if(renderType != null) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_frag_tex);
				ResourceManager.grenades.renderPart("FragSpoon");
				ResourceManager.grenades.renderPart("FragRing");
			}
		}
		
		if(shell == EnumGrenadeShell.STICK) {
			if(renderType == ItemRenderType.INVENTORY) {
				GL11.glScaled(2, 2, 2);
				GL11.glTranslated(0, -4.5, 0);
			}
			if(renderType == ItemRenderType.EQUIPPED) {
				GL11.glTranslated(0, -2, 0);
			}
			if(renderType == null) {
				GL11.glTranslated(0, -2, 0);
			}
			renderStickBody(stack);
			if(renderType != null) {
				EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(stack);
				GL11.glColor3f(ColorUtil.fr(filling.bodyColor), ColorUtil.fg(filling.bodyColor), ColorUtil.fb(filling.bodyColor));
				bind(ResourceManager.grenade_stick_body_tex);
				ResourceManager.grenades.renderPart("StickCap");
				GL11.glColor3f(1F, 1F, 1F);
			}
		}
		
		if(shell == EnumGrenadeShell.TECH) {
			if(renderType == ItemRenderType.INVENTORY) {
				GL11.glScaled(3.5, 3.5, 3.5);
				GL11.glTranslated(0, -1.75, 0);
			}
			if(renderType == ItemRenderType.EQUIPPED) {
				GL11.glScaled(1.5, 1.5, 1.5);
				GL11.glTranslated(0.5, -1, 0.5);
			}
			if(renderType == null) {
				GL11.glScaled(1.5, 1.5, 1.5);
				GL11.glTranslated(0, -1, 0);
			}
			renderTechBody(stack);
			if(renderType != null) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_tech_tex);
				ResourceManager.grenades.renderPart("TechRing");
			}
		}
		
		if(shell == EnumGrenadeShell.NUKE) {
			if(renderType == ItemRenderType.INVENTORY) {
				GL11.glScaled(2.5, 2.5, 2.5);
				GL11.glTranslated(0, -2.75, 0);
			}
			if(renderType == ItemRenderType.EQUIPPED) {
				GL11.glScaled(1.5, 1.5, 1.5);
				GL11.glTranslated(0.5, -3, 0.5);
			}
			if(renderType == null) {
				GL11.glScaled(1.5, 1.5, 1.5);
				GL11.glTranslated(0, -3, 0);
			}
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_nuka_tex);
			ResourceManager.grenades.renderPart("Nuka");
			if(renderType != null) {
				ResourceManager.grenades.renderPart("NukaSpoon");
				ResourceManager.grenades.renderPart("NukaRing");
			}
		}
	}
	
	public static void renderFragBody(ItemStack stack) {

		EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(stack);
		EnumGrenadeFuze fuze = ItemGrenadeUniversal.getFuze(stack);
		
		bind(ResourceManager.grenade_frag_tex);
		ResourceManager.grenades.renderPart("Frag");

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);

		GL11.glColor3f(ColorUtil.fr(filling.bodyColor), ColorUtil.fg(filling.bodyColor), ColorUtil.fb(filling.bodyColor));
		bind(ResourceManager.grenade_frag_body_tex);
		ResourceManager.grenades.renderPart("Frag");

		GL11.glColor3f(ColorUtil.fr(filling.labelColor), ColorUtil.fg(filling.labelColor), ColorUtil.fb(filling.labelColor));
		bind(ResourceManager.grenade_frag_label_tex);
		ResourceManager.grenades.renderPart("Frag");

		GL11.glColor3f(ColorUtil.fr(fuze.bandColor), ColorUtil.fg(fuze.bandColor), ColorUtil.fb(fuze.bandColor));
		bind(ResourceManager.grenade_frag_fuze_tex);
		ResourceManager.grenades.renderPart("Frag");

		GL11.glColor3f(1F, 1F, 1F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void renderStickBody(ItemStack stack) {

		EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(stack);
		EnumGrenadeFuze fuze = ItemGrenadeUniversal.getFuze(stack);
		
		bind(ResourceManager.grenade_stick_tex);
		ResourceManager.grenades.renderPart("Stick");

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);

		GL11.glColor3f(ColorUtil.fr(filling.bodyColor), ColorUtil.fg(filling.bodyColor), ColorUtil.fb(filling.bodyColor));
		bind(ResourceManager.grenade_stick_body_tex);
		ResourceManager.grenades.renderPart("Stick");

		GL11.glColor3f(ColorUtil.fr(filling.labelColor), ColorUtil.fg(filling.labelColor), ColorUtil.fb(filling.labelColor));
		bind(ResourceManager.grenade_stick_label_tex);
		ResourceManager.grenades.renderPart("Stick");

		GL11.glColor3f(ColorUtil.fr(fuze.bandColor), ColorUtil.fg(fuze.bandColor), ColorUtil.fb(fuze.bandColor));
		bind(ResourceManager.grenade_stick_fuze_tex);
		ResourceManager.grenades.renderPart("Stick");

		GL11.glColor3f(1F, 1F, 1F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void renderTechBody(ItemStack stack) {

		EnumGrenadeFilling filling = ItemGrenadeUniversal.getFilling(stack);
		EnumGrenadeFuze fuze = ItemGrenadeUniversal.getFuze(stack);
		
		bind(ResourceManager.grenade_tech_tex);
		ResourceManager.grenades.renderPart("Tech");

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);

		GL11.glColor3f(ColorUtil.fr(filling.bodyColor), ColorUtil.fg(filling.bodyColor), ColorUtil.fb(filling.bodyColor));
		bind(ResourceManager.grenade_tech_body_tex);
		ResourceManager.grenades.renderPart("Tech");

		GL11.glColor3f(ColorUtil.fr(fuze.bandColor), ColorUtil.fg(fuze.bandColor), ColorUtil.fb(fuze.bandColor));
		bind(ResourceManager.grenade_tech_fuze_tex);
		ResourceManager.grenades.renderPart("Tech");

		RenderArcFurnace.fullbright(true);
		GL11.glColor3f(ColorUtil.fr(filling.labelColor), ColorUtil.fg(filling.labelColor), ColorUtil.fb(filling.labelColor));
		bind(ResourceManager.grenade_tech_lights_tex);
		ResourceManager.grenades.renderPart("Tech");
		RenderArcFurnace.fullbright(false);

		GL11.glColor3f(1F, 1F, 1F);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void bind(ResourceLocation res) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
	}
}
