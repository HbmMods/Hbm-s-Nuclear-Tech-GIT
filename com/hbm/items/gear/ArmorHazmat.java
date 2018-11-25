package com.hbm.items.gear;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorHazmat extends ItemArmor {

	private ResourceLocation hazmatBlur = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_hazmat.png");

	public ArmorHazmat(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.hazmat_helmet) || stack.getItem().equals(ModItems.hazmat_plate) || stack.getItem().equals(ModItems.hazmat_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_helmet) || stack.getItem().equals(ModItems.hazmat_paa_plate) || stack.getItem().equals(ModItems.hazmat_paa_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_2.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_helmet_red) || stack.getItem().equals(ModItems.hazmat_plate_red) || stack.getItem().equals(ModItems.hazmat_boots_red)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1_red.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs_red)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2_red.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_helmet_grey) || stack.getItem().equals(ModItems.hazmat_plate_grey) || stack.getItem().equals(ModItems.hazmat_boots_grey)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1_grey.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs_grey)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2_grey.png");
		}
		
		else return null;
	}
	
    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY){
    	
    	if(this != ModItems.hazmat_helmet && this != ModItems.hazmat_paa_helmet)
    		return;
    	

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().getTextureManager().bindTexture(hazmatBlur);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
