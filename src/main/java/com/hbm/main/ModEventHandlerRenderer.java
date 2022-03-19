package com.hbm.main;

import com.hbm.items.armor.IArmorDisableModel;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ModEventHandlerRenderer {
	
	private static boolean[] partsHidden = new boolean[7];

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		
		EntityPlayer player = event.entityPlayer;
		RenderPlayer renderer = event.renderer;
		
		for(int j = 0; j < 7; j++) {
			partsHidden[j] = false;
		}
		
		for(int i = 1; i < 5; i++) {
			ItemStack stack = player.getEquipmentInSlot(i);
			
			if(stack != null && stack.getItem() instanceof IArmorDisableModel) {
				IArmorDisableModel disable = (IArmorDisableModel) stack.getItem();
				
				for(int j = 0; j < 7; j++) {
					EnumPlayerPart type = EnumPlayerPart.values()[j];
					ModelRenderer box = getBoxFromType(renderer, type);
					if(disable.disablesPart(player, stack, type) && !box.isHidden) {
						partsHidden[j] = true;
						box.isHidden = true;
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		
		RenderPlayer renderer = event.renderer;
		
		for(int j = 0; j < 7; j++) {
			EnumPlayerPart type = EnumPlayerPart.values()[j];
			if(partsHidden[j]) {
				getBoxFromType(renderer, type).isHidden = false;
			}
		}
	}
	
	private static ModelRenderer getBoxFromType(RenderPlayer renderer, EnumPlayerPart part) {
		
		switch(part) {
		case BODY:		return renderer.modelBipedMain.bipedBody;
		case HAT:		return renderer.modelBipedMain.bipedHeadwear;
		case HEAD:		return renderer.modelBipedMain.bipedHead;
		case LEFT_ARM:	return renderer.modelBipedMain.bipedLeftArm;
		case LEFT_LEG:	return renderer.modelBipedMain.bipedLeftLeg;
		case RIGHT_ARM:	return renderer.modelBipedMain.bipedRightArm;
		case RIGHT_LEG:	return renderer.modelBipedMain.bipedRightLeg;
		default:		return null;
		}
	}
}
