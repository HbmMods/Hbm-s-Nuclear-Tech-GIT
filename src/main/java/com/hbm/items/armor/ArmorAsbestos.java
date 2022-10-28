package com.hbm.items.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ArmorAsbestos extends ItemArmor implements ISpecialArmor {

	private ResourceLocation asbestosBlur = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_asbestos.png");

	public ArmorAsbestos(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.asbestos_helmet) || stack.getItem().equals(ModItems.asbestos_plate) || stack.getItem().equals(ModItems.asbestos_boots)) {
			return(RefStrings.MODID + ":textures/armor/asbestos_1.png");
		}
		if(stack.getItem().equals(ModItems.asbestos_legs)) {
			return(RefStrings.MODID + ":textures/armor/asbestos_2.png");
		}

		else
			return null;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isFireDamage()) {
			return new ArmorProperties(1, 1, MathHelper.floor_double(999999999));
		}
		return new ArmorProperties(0, 0, 0);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if(slot == 0) {
			return 3;
		}
		if(slot == 1) {
			return 8;
		}
		if(slot == 2) {
			return 6;
		}
		if(slot == 3) {
			return 3;
		}
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		stack.damageItem(damage * 1, entity);

	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {

		player.extinguish();
	}

	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {

		if(this != ModItems.asbestos_helmet)
			return;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		Minecraft.getMinecraft().getTextureManager().bindTexture(asbestosBlur);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (double) resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double) resolution.getScaledWidth(), (double) resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double) resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
