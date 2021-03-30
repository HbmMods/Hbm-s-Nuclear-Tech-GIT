package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelM65;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ItemModGasmask extends ItemArmorMod {
	
	private ModelM65 modelM65;
	private ResourceLocation tex = new ResourceLocation("hbm:textures/models/ModelM65.png");
	private ResourceLocation tex_mono = new ResourceLocation("hbm:textures/models/ModelM65Mono.png");

	public ItemModGasmask() {
		super(ArmorModHandler.helmet_only, true, false, false, false);
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.attachment_mask)
			list.add(EnumChatFormatting.GREEN + "Gas protection");
		if(this == ModItems.attachment_mask_mono)
			list.add(EnumChatFormatting.GREEN + "Carbon monoxide protection");
		
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {

		if(this == ModItems.attachment_mask)
			list.add(EnumChatFormatting.GREEN + "  " + stack.getDisplayName() + " (gas protection)");
		if(this == ModItems.attachment_mask_mono)
			list.add(EnumChatFormatting.GREEN + "  " + stack.getDisplayName() + " (carbon monoxide protection)");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.SetArmorModel event, ItemStack armor) {

		if(this.modelM65 == null) {
			this.modelM65 = new ModelM65();
		}
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		modelM65.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;

		if(this == ModItems.attachment_mask)
			Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		if(this == ModItems.attachment_mask_mono)
			Minecraft.getMinecraft().renderEngine.bindTexture(tex_mono);
		
		modelM65.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}

}
