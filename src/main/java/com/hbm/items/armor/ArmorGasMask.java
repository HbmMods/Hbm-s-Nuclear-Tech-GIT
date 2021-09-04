package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelGasMask;
import com.hbm.render.model.ModelM65;
import com.hbm.util.ArmorRegistry.HazardClass;

import api.hbm.item.IGasMask;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorGasMask extends ItemArmor implements IGasMask {

	@SideOnly(Side.CLIENT)
	private ModelGasMask modelGas;
	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;
	
	private ResourceLocation[] googleBlur = new ResourceLocation[] {
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_0.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_1.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_2.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_3.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_4.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_5.png")
	};
	
	private ResourceLocation[] maskBlur = new ResourceLocation[] {
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_0.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_1.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_2.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_3.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_4.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_5.png")
	};

	public ArmorGasMask() {
		super(ArmorMaterial.IRON, 7, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(this == ModItems.gas_mask) {
			if(armorSlot == 0) {
				if(this.modelGas == null) {
					this.modelGas = new ModelGasMask();
				}
				return this.modelGas;
			}
		}

		if(this == ModItems.gas_mask_m65 || this == ModItems.gas_mask_mono) {
			if(armorSlot == 0) {
				if(this.modelM65 == null) {
					this.modelM65 = new ModelM65();
				}
				return this.modelM65;
			}
		}

		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {

		if(stack.getItem() == ModItems.gas_mask) {
			return "hbm:textures/models/GasMask.png";
		}
		if(stack.getItem() == ModItems.gas_mask_m65) {
			return "hbm:textures/models/ModelM65.png";
		}
		if(stack.getItem() == ModItems.gas_mask_mono) {
			return "hbm:textures/models/ModelM65Mono.png";
		}

		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {

		ResourceLocation tex = null;

		if(this == ModItems.goggles || this == ModItems.gas_mask_m65) {
			int index = (int) ((double) stack.getItemDamage() / (double) stack.getMaxDamage() * 6D);
			tex = this.googleBlur[index];
		}

		if(this == ModItems.gas_mask) {
			int index = (int) ((double) stack.getItemDamage() / (double) stack.getMaxDamage() * 6D);
			tex = this.maskBlur[index];
		}
		
		if(tex == null)
			return;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

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

	@Override
	public List<HazardClass> getBlacklist(ItemStack stack, EntityPlayer player) {
		
		if(this == ModItems.gas_mask_mono) {
			return Arrays.asList(new HazardClass[] {HazardClass.GAS_CHLORINE, HazardClass.BACTERIA});
		}
		
		return new ArrayList();
	}

	@Override
	public ItemStack getFilter(ItemStack stack, EntityPlayer player) {
		
		if(stack == null || !(stack.getItem() instanceof IGasMask) || !stack.hasTagCompound())
			return null;
		
		return null;
	}

	@Override
	public void damageFilter(ItemStack stack, EntityPlayer player) {
		
	}
}
