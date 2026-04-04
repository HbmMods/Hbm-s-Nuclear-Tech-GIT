package com.hbm.render.item;

import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
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
		GL11.glRotated(135, 0, -1, 0);
		
		if(shell == EnumGrenadeShell.FRAG) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_frag_tex);
			ResourceManager.grenades.renderPart("Frag");
			ResourceManager.grenades.renderPart("FragSpoon");
			ResourceManager.grenades.renderPart("FragRing");
		}
		
		if(shell == EnumGrenadeShell.STICK) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_stick_tex);
			ResourceManager.grenades.renderPart("Stick");
			ResourceManager.grenades.renderPart("StickCap");
		}
		
		if(shell == EnumGrenadeShell.TECH) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_tech_tex);
			ResourceManager.grenades.renderPart("Tech");
			ResourceManager.grenades.renderPart("TechRing");
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
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_frag_tex);
			ResourceManager.grenades.renderPart("Frag");
			if(renderType != null) {
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
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_stick_tex);
			ResourceManager.grenades.renderPart("Stick");
			if(renderType != null) {
				ResourceManager.grenades.renderPart("StickCap");
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
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_tech_tex);
			ResourceManager.grenades.renderPart("Tech");
			if(renderType != null) {
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
}
