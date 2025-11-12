package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.render.model.ModelArmorBJ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArmorBJ extends ArmorFSBPowered {

	public ArmorBJ(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorBJ[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorBJ[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorBJ(i);
		}
		
		return models[armorSlot];
	}
	
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    	
    	super.onArmorTick(world, player, itemStack);
    	
    	if(this == ModItems.bj_helmet && ArmorFSB.hasFSBArmorIgnoreCharge(player) && !ArmorFSB.hasFSBArmor(player)) {
    		
    		ItemStack helmet = player.inventory.armorInventory[3];
    		
    		if(!player.inventory.addItemStackToInventory(helmet))
    			player.dropPlayerItemWithRandomChoice(helmet, false);
    		
    		player.inventory.armorInventory[3] = null;
    		
    		player.attackEntityFrom(ModDamageSource.lunar, 1000);
    	}
    }
}
