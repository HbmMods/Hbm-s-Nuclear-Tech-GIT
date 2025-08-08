package com.hbm.items.food;

import java.util.List;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ModItems;
import com.hbm.packet.toclient.AuxParticlePacketNT;
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
			list.add("Eh, good enough.");
		}

		if(this == ModItems.med_ipecac) {
			list.add("Bitter juice that will cause your stomach");
			list.add("to forcefully eject its contents.");
		}

		if(this == ModItems.med_ptsd) {
			list.add("This isn't even PTSD mediaction, it's just");
			list.add("Ipecac in a different bottle!");
		}

		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}

		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}

		if(this == ModItems.loops) {
			list.add("Brøther, may I have some lööps?");
		}

		if(this == ModItems.loop_stew) {
			list.add("A very, very healthy breakfast.");
		}

		if(this == ModItems.twinkie) {
			list.add("Expired 600 years ago!");
		}

		if(this == ModItems.pudding) {
			list.add("What if he did?");
			list.add("What if he didn't?");
			list.add("What if the world was made of pudding?");
		}

		if(this == ModItems.ingot_semtex) {
			list.add("Semtex H Plastic Explosive");
			list.add("Performant explosive for many applications.");
			list.add("Edible");
		}

		if(this == ModItems.peas) {
			list.add("He accepts your offering.");
		}

		if(this == ModItems.quesadilla) {
			list.add("That's what a 50 year old yeast infection does to you.");
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
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

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
