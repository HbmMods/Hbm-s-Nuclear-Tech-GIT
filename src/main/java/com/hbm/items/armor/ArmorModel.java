package com.hbm.items.armor;

import java.util.List;
import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelCloak;
import com.hbm.render.model.ModelGoggles;
import com.hbm.render.model.ModelHat;

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

@Spaghetti("more ctor stuff, less if/else bullshittery")
// turns out you can't actually pass a model in the ctor because ModelBiped is
// clientonly...
public class ArmorModel extends ItemArmor {

	@SideOnly(Side.CLIENT)
	private static final ModelGoggles modelGoggles = new ModelGoggles();
	@SideOnly(Side.CLIENT)
	private static final ModelHat modelHat = new ModelHat(0);
	@SideOnly(Side.CLIENT)
	private static final ModelCloak modelCloak = new ModelCloak();

	@SideOnly(Side.CLIENT)
	private static final ResourceLocation[] gogglesBlurs = IntStream.range(0, 6)
		.mapToObj(i -> new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_" + i + ".png"))
		.toArray(ResourceLocation[]::new);

	public ArmorModel(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, 0, armorType);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if(this == ModItems.goggles) {
			if(armorSlot == 0) {
				return modelGoggles;
			}
		}
		if(this == ModItems.hat) {
			if(armorSlot == 0) {
				return modelHat;
			}
		}
		if(this == ModItems.cape_radiation || this == ModItems.cape_gasmask || this == ModItems.cape_schrabidium || this == ModItems.cape_hidden) {
			if(armorSlot == 1) {
				return modelCloak;
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(stack.getItem() == ModItems.goggles) {
			return "hbm:textures/models/Goggles.png";
		}
		if(stack.getItem() == ModItems.cape_radiation) {
			return "hbm:textures/models/capes/CapeRadiation.png";
		}
		if(stack.getItem() == ModItems.cape_gasmask) {
			return "hbm:textures/models/capes/CapeGasMask.png";
		}
		if(stack.getItem() == ModItems.cape_schrabidium) {
			return "hbm:textures/models/capes/CapeSchrabidium.png";
		}
		if(stack.getItem() == ModItems.cape_hidden) {
			return "hbm:textures/models/capes/CapeHidden.png";
		}

		return "hbm:textures/models/capes/CapeUnknown.png";
	}

	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {

		if(this != ModItems.goggles && this != ModItems.hazmat_helmet_red && this != ModItems.hazmat_helmet_grey)
			return;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

		int blurTextureIndex = (int) ((double) stack.getItemDamage() / (double) stack.getMaxDamage() * 6D);
		if (blurTextureIndex < 0 || blurTextureIndex > 5)
			blurTextureIndex = 5;

		Minecraft.getMinecraft().getTextureManager().bindTexture(gogglesBlurs[blurTextureIndex]);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(resolution.getScaledWidth(), resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.cape_radiation) {
			list.add("Avalible for everyone");
		}
		if(this == ModItems.cape_gasmask) {
			list.add("Avalible for everyone");
		}
		if(this == ModItems.cape_schrabidium) {
			list.add("Avalible for everyone");
		}
	}
}
