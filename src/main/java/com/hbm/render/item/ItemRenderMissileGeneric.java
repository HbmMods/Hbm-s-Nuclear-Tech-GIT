package com.hbm.render.item;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRenderMissileGeneric implements IItemRenderer {
	
	public static HashMap<ComparableStack, Consumer<TextureManager>> renderers = new HashMap();
	
	protected RenderMissileType type;
	
	public static enum RenderMissileType {
		TYPE_TIER0,
		TYPE_TIER1,
		TYPE_TIER2,
		TYPE_TIER3,
		TYPE_STEALTH,
		TYPE_ABM,
		TYPE_NUCLEAR,
		TYPE_ROBIN
	}
	
	public ItemRenderMissileGeneric(RenderMissileType type) {
		this.type = type;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		Consumer<TextureManager> renderer = renderers.get(new ComparableStack(item).makeSingular());
		if(renderer == null) return;
		
		GL11.glPushMatrix();

		double guiScale = 1;
		double guiOffset = 0;

		switch(this.type) {
		case TYPE_TIER0: guiScale = 3.75D; guiOffset = 10.75D; break;
		case TYPE_TIER1: guiScale = 2.5D; guiOffset = 8.5D; break;
		case TYPE_TIER2: guiScale = 2D; guiOffset = 6.5D; break;
		case TYPE_TIER3: guiScale = 1.25D; guiOffset = 1D; break;
		case TYPE_STEALTH: guiScale = 1.75D; guiOffset = 4.75D; break;
		case TYPE_ABM: guiScale = 2.25D; guiOffset = 7D; break;
		case TYPE_NUCLEAR: guiScale = 1.375D; guiOffset = 1.5D; break;
		case TYPE_ROBIN: guiScale = 1.25D; guiOffset = 2D; break;
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		switch(type) {
		case EQUIPPED:
			double s = 0.15;
			GL11.glTranslated(0.5, -0.25, 0);
			GL11.glScaled(s, s, s);
			break;
		case EQUIPPED_FIRST_PERSON:
			double heldScale = 0.1;
			GL11.glTranslated(0.5, 0.25, 0);
			GL11.glScaled(heldScale, heldScale, heldScale);
			break;
		case ENTITY:
			double s2 = 0.15;
			GL11.glScaled(s2, s2, s2);
			break;
		case INVENTORY:
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glScaled(guiScale, guiScale, guiScale);
			GL11.glRotated(135, 0, 0, 1);
			GL11.glRotatef(System.currentTimeMillis() / 15 % 360, 0, 1, 0);
			GL11.glTranslated(0, -16 + guiOffset, 0);
			break;
		default: break;
		}
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		renderer.accept(Minecraft.getMinecraft().renderEngine);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
	
	public static Consumer<TextureManager> generateStandard(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 1F); }
	public static Consumer<TextureManager> generateLarge(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 1.5F); }
	public static Consumer<TextureManager> generateDouble(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 2F); }
	
	public static Consumer<TextureManager> generateWithScale(ResourceLocation texture, IModelCustom model, float scale) {
		return x -> {
			GL11.glScalef(scale, scale, scale);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			x.bindTexture(texture); model.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		};
	}
	
	public static void init() {

		renderers.put(new ComparableStack(ModItems.missile_test), generateStandard(ResourceManager.missileMicroTest_tex, ResourceManager.missileMicro));
		renderers.put(new ComparableStack(ModItems.missile_taint), generateStandard(ResourceManager.missileMicroTaint_tex, ResourceManager.missileMicro));
		renderers.put(new ComparableStack(ModItems.missile_micro), generateStandard(ResourceManager.missileMicro_tex, ResourceManager.missileMicro));
		renderers.put(new ComparableStack(ModItems.missile_bhole), generateStandard(ResourceManager.missileMicroBHole_tex, ResourceManager.missileMicro));
		renderers.put(new ComparableStack(ModItems.missile_schrabidium), generateStandard(ResourceManager.missileMicroSchrab_tex, ResourceManager.missileMicro));
		renderers.put(new ComparableStack(ModItems.missile_emp), generateStandard(ResourceManager.missileMicroEMP_tex, ResourceManager.missileMicro));
		
		renderers.put(new ComparableStack(ModItems.missile_stealth), x -> {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			x.bindTexture(ResourceManager.missileStealth_tex); ResourceManager.missileStealth.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		});
		
		renderers.put(new ComparableStack(ModItems.missile_generic), generateStandard(ResourceManager.missileV2_HE_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_incendiary), generateStandard(ResourceManager.missileV2_IN_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_cluster), generateStandard(ResourceManager.missileV2_CL_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_buster), generateStandard(ResourceManager.missileV2_BU_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_decoy), generateStandard(ResourceManager.missileV2_decoy_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_anti_ballistic), generateStandard(ResourceManager.missileAA_tex, ResourceManager.missileABM));
		
		renderers.put(new ComparableStack(ModItems.missile_strong), generateLarge(ResourceManager.missileStrong_HE_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_incendiary_strong), generateLarge(ResourceManager.missileStrong_IN_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_cluster_strong), generateLarge(ResourceManager.missileStrong_CL_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_buster_strong), generateLarge(ResourceManager.missileStrong_BU_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_emp_strong), generateLarge(ResourceManager.missileStrong_EMP_tex, ResourceManager.missileStrong));
		
		renderers.put(new ComparableStack(ModItems.missile_burst), generateStandard(ResourceManager.missileHuge_HE_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_inferno), generateStandard(ResourceManager.missileHuge_IN_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_rain), generateStandard(ResourceManager.missileHuge_CL_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_drill), generateStandard(ResourceManager.missileHuge_BU_tex, ResourceManager.missileHuge));

		renderers.put(new ComparableStack(ModItems.missile_nuclear), generateStandard(ResourceManager.missileNuclear_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_nuclear_cluster), generateStandard(ResourceManager.missileThermo_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_volcano), generateStandard(ResourceManager.missileVolcano_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_doomsday), generateStandard(ResourceManager.missileDoomsday_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_doomsday_rusted), generateStandard(ResourceManager.missileDoomsdayRusted_tex, ResourceManager.missileNuclear));

		renderers.put(new ComparableStack(ModItems.missile_shuttle), generateStandard(ResourceManager.missileShuttle_tex, ResourceManager.missileShuttle));
	}
}
