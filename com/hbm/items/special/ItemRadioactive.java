package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.Library;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemCustomLore {
	
	float radiation;
	boolean fire;
	boolean blinding;
	
	public ItemRadioactive(float radiation) {
		this.radiation = radiation;
	}
	
	public ItemRadioactive(float radiation, boolean fire) {
		this.radiation = radiation;
		this.fire = fire;
	}
	
	public ItemRadioactive(float radiation, boolean fire, boolean blinding) {
		this.radiation = radiation;
		this.fire = fire;
		this.blinding = blinding;
	}
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	doRadiationDamage(entity, stack.stackSize);
    }
    
    public void doRadiationDamage(Entity entity, float mod) {

		if (entity instanceof EntityLivingBase) {
			
			if(this.radiation > 0)
				Library.applyRadData(entity, this.radiation / 20F);
			
			if(this.fire)
				entity.setFire(5);
			
			if(this.blinding)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
		}
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		
		if(radiation > 0) {
			list.add(EnumChatFormatting.GREEN + "[Radioactive]");
			list.add(EnumChatFormatting.YELLOW + (radiation + "RAD/s"));
		}
		
		if(fire)
			list.add(EnumChatFormatting.GOLD + "[Pyrophyric / Hot]");
		
		if(blinding)
			list.add(EnumChatFormatting.DARK_AQUA + "[Blinding]");
	}
}
