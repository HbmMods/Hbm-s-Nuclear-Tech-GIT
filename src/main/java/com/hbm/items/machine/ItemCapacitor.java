package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCapacitor extends Item {
	
	private int dura;

	public ItemCapacitor(int dura) {
		this.dura = dura;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (this == ModItems.redcoil_capacitor) {
			list.add("Right-click a block to negate positive charge.");
			list.add("[Needed for Schrabidium Synthesis]");
			list.add(getDura(itemstack) + "/" + dura);
		}
		if (this == ModItems.titanium_filter || this == ModItems.saturnite_filter || this == ModItems.paa_filter) {
			list.add("[Needed for Watz Reaction]");
			list.add((getDura(itemstack) / 20) + "/" + (dura / 20));
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		
		if (this == ModItems.redcoil_capacitor) {
			
			if (!player.isSneaking()) {
				
				if (getDura(stack) < dura) {
					
					setDura(stack, getDura(stack) + 1);
					
					if (!world.isRemote) {
						world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 2.5F, true);
					}
					world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));

					return true;
				}
			}
		}

		return false;
	}
    
    public boolean showDurabilityBar(ItemStack stack) {
        return getDurabilityForDisplay(stack) > 0;
    }
    
    public static int getDura(ItemStack stack) {
    	
    	if(stack.stackTagCompound == null)
    		return ((ItemCapacitor)stack.getItem()).dura;
    	
    	return stack.stackTagCompound.getInteger("dura");
    }
    
    public static void setDura(ItemStack stack, int dura) {
    	
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	stack.stackTagCompound.setInteger("dura", dura);
    }
    
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1D - (double)getDura(stack) / (double)dura;
    }
    
    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_)
    {
    	if (this == ModItems.euphemium_capacitor)
    		return EnumRarity.epic;
    	
    	if (this == ModItems.saturnite_filter)
    		return EnumRarity.rare;
    	
    	if (this == ModItems.paa_filter)
    		return EnumRarity.uncommon;
    	
    	return EnumRarity.common;
    }
}
