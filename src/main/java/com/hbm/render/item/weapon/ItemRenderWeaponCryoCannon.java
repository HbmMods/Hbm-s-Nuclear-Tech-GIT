package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCryoCannon;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponCryoCannon implements IItemRenderer {

	public static final ResourceLocation[] fill_tex = new ResourceLocation[15];
	public static final ResourceLocation[] pressure_tex = new ResourceLocation[12];
	public static final ResourceLocation[] turbine_tex = new ResourceLocation[9];
	
	public ItemRenderWeaponCryoCannon() {
		for(int i = 0; i < fill_tex.length; i++) fill_tex[i] = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/cryo_cannon/fill_" + i + ".png");
		for(int i = 0; i < pressure_tex.length; i++) pressure_tex[i] = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/cryo_cannon/pressure_" + i + ".png");
		for(int i = 0; i < turbine_tex.length; i++) turbine_tex[i] = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/cryo_cannon/turbine_" + i + ".png");
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
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.cryocannon_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1, 0, -0.3);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			ItemGunBase gun = (ItemGunBase) item.getItem();

			ResourceManager.cryocannon.renderPart("Gun");
			ResourceManager.cryocannon.renderPart("Rotor");
			Minecraft.getMinecraft().renderEngine.bindTexture(fill_tex[MathHelper.clamp_int(ItemGunBase.getMag(item) * fill_tex.length / gun.mainConfig.ammoCap, 0, fill_tex.length - 1)]);
			ResourceManager.cryocannon.renderPart("Fuel");
			Minecraft.getMinecraft().renderEngine.bindTexture(turbine_tex[MathHelper.clamp_int(turbine_tex.length - 1 - ItemCryoCannon.getTurbine(item) * turbine_tex.length / 100, 0, turbine_tex.length - 1)]);
			ResourceManager.cryocannon.renderPart("Spin");
			Minecraft.getMinecraft().renderEngine.bindTexture(pressure_tex[MathHelper.clamp_int(ItemCryoCannon.getPressure(item) * pressure_tex.length / 1000, 0, pressure_tex.length - 1)]);
			ResourceManager.cryocannon.renderPart("Pressure");
			
			break;
			
		case EQUIPPED:

			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.75F, -2.5F, 3.5F);
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslated(0, 0, 3);
			
			break;
			
		case INVENTORY:
			
			double s = 2.5D;
			GL11.glTranslated(1, 6, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) ResourceManager.cryocannon.renderAll();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
