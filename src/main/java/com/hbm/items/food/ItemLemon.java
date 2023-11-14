package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import com.hbm.util.I18nUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemLemon extends ItemFood {

	public ItemLemon(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
		
		if(this == ModItems.med_ipecac || this == ModItems.med_ptsd) {
			this.setAlwaysEdible();
		}
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.lemon) {
			list.add(I18nUtil.resolveKey("item.lemon.desc"));
		}
		
		if(this == ModItems.definitelyfood) {
			for(String s : I18nUtil.resolveKeyArray("item.definitelyfood.desc"))
			    list.add(s);
		}
		
		if(this == ModItems.med_ipecac) {
			for(String s : I18nUtil.resolveKeyArray("item.med_ipecac.desc"))
				list.add(s);
		}
		
		if(this == ModItems.med_ptsd) {
			for(String s : I18nUtil.resolveKeyArray("item.med_ptsd.desc"))
				list.add(s);
		}
		
		if(this == ModItems.med_schizophrenia) {
			for(String s : I18nUtil.resolveKeyArray("item.med_schiziphrenia.desc"))
				list.add(s);
		}
		
		if(this == ModItems.loops) {
			list.add(I18nUtil.resolveKey("item.loops.desc"));
		}
		
		if(this == ModItems.loop_stew) {
			list.add(I18nUtil.resolveKey("item.loop_stew.desc"));
		}
		
		if(this == ModItems.twinkie) {
			list.add(I18nUtil.resolveKey("item.twinkie.desc"));
		}
		
		if(this == ModItems.pudding) {
			for(String s : I18nUtil.resolveKeyArray("item.pudding.desc"))
				list.add(s);
		}

		if(this == ModItems.ingot_semtex) {
			for(String s : I18nUtil.resolveKeyArray("item.ingot_semtex.desc"))
				list.add(s);
		}

		if(this == ModItems.peas) {
			list.add(I18nUtil.resolveKey("item.peas.desc"));
		}

		if(this == ModItems.quesadilla) {
			list.add(I18nUtil.resolveKey("item.cheese_quesadilla.desc"));
		}
	}


    @Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
		if(this == ModItems.med_ipecac || this == ModItems.med_ptsd) {
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 50, 49));
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "vomit");
			nbt.setInteger("entity", player.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
			
			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:entity.vomit", 1.0F, 1.0F);
		}
		
		if(this == ModItems.med_schizophrenia) {
		}
		
		if(this == ModItems.loop_stew) {
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20, 2));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20 * 20, 2));
		}
    }

    public ItemStack onEaten(ItemStack stack, World worldObj, EntityPlayer player)
    {
        ItemStack sta = super.onEaten(stack, worldObj, player);
        
        if(this == ModItems.loop_stew)
        	return new ItemStack(Items.bowl);
        
        return sta;
        
    }

}
