package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSwordMeteorite extends ItemSwordAbility {

	public ItemSwordMeteorite(float damage, double movement, ToolMaterial material) {
		super(damage, movement, material);
		this.setMaxDamage(0);
	}
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
    	super.addInformation(stack, player, list, ext);
    	
    	if(this == ModItems.meteorite_sword) {
    		list.add(EnumChatFormatting.ITALIC + "Forged from a fallen star");
    		list.add(EnumChatFormatting.ITALIC + "Sharper than most terrestrial blades");
    	}
    	
    	if(this == ModItems.meteorite_sword_seared) {
    		list.add(EnumChatFormatting.ITALIC + "Fire strengthens the blade");
    		list.add(EnumChatFormatting.ITALIC + "Making it even more powerful");
    	}
    	
    	if(this == ModItems.meteorite_sword_reforged) {
    		list.add(EnumChatFormatting.ITALIC + "The sword has been reforged");
    		list.add(EnumChatFormatting.ITALIC + "To rectify past imperfections");
    	}
    	
    	if(this == ModItems.meteorite_sword_hardened) {
    		list.add(EnumChatFormatting.ITALIC + "Extremely high pressure has been used");
    		list.add(EnumChatFormatting.ITALIC + "To harden the blade further");
    	}
    	
    	if(this == ModItems.meteorite_sword_alloyed) {
    		list.add(EnumChatFormatting.ITALIC + "Cobalt fills the fissures");
    		list.add(EnumChatFormatting.ITALIC + "Strengthening the sword");
    	}
    	
    	if(this == ModItems.meteorite_sword_machined) {
    		list.add(EnumChatFormatting.ITALIC + "Advanced machinery was used");
    		list.add(EnumChatFormatting.ITALIC + "To refine the blade even more");
    	}
    	
    	if(this == ModItems.meteorite_sword_treated) {
    		list.add(EnumChatFormatting.ITALIC + "Chemicals have been applied");
    		list.add(EnumChatFormatting.ITALIC + "Making the sword more powerful");
    	}
    	
    	if(this == ModItems.meteorite_sword_etched) {
    		list.add(EnumChatFormatting.ITALIC + "Acids clean the material");
    		list.add(EnumChatFormatting.ITALIC + "To make this the perfect sword");
    	}
    }

}
