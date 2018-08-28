package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSyringe extends Item {
	
	Random rand = new Random();

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(this == ModItems.syringe_antidote)
		{
            if (!world.isRemote)
            {
            	player.clearActivePotions();
            
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.syringe_awesome)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 50 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50 * 20, 24));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50 * 20, 6));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 50 * 20, 4));
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 4));
                
                stack.stackSize--;

                if (stack.stackSize <= 0)
                {
                    return new ItemStack(ModItems.syringe_empty);
                }

                if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
                {
                	player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
                }
            }
		}
		
		if(this == ModItems.syringe_poison)
		{
            if (!world.isRemote)
            {
            	if(rand.nextInt(2) == 0)
            		player.attackEntityFrom(ModDamageSource.euthanizedSelf, 30);
            	else
            		player.attackEntityFrom(ModDamageSource.euthanizedSelf2, 30);
                
                stack.stackSize--;

                if (stack.stackSize <= 0)
                {
                    return new ItemStack(ModItems.syringe_empty);
                }

                if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
                {
                	player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
                }
            }
		}
		
		if(this == ModItems.syringe_metal_stimpak)
		{
            if (!world.isRemote)
            {
            	player.heal(5);
            
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_metal_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_medx)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(Potion.resistance.id, 4 * 60 * 20, 2));
            	
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_metal_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_psycho)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(Potion.resistance.id, 2 * 60 * 20, 0));
            	player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2 * 60 * 20, 0));
            	
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_metal_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_super)
		{
            if (!world.isRemote)
            {
            	player.heal(25);
            	player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 0));
            
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_metal_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.med_bag)
		{
            if (!world.isRemote)
            {
            	player.setHealth(player.getMaxHealth());
            	
        		player.removePotionEffect(Potion.blindness.id);
        		player.removePotionEffect(Potion.confusion.id);
        		player.removePotionEffect(Potion.digSlowdown.id);
        		player.removePotionEffect(Potion.hunger.id);
        		player.removePotionEffect(Potion.moveSlowdown.id);
        		player.removePotionEffect(Potion.poison.id);
        		player.removePotionEffect(Potion.weakness.id);
        		player.removePotionEffect(Potion.wither.id);
        		player.removePotionEffect(HbmPotion.radiation.id);
            
            	stack.stackSize--;
            }
		}
		
		if(this == ModItems.radaway)
		{
            if (!world.isRemote)
            {
        		player.removePotionEffect(HbmPotion.radiation.id);
            
            	stack.stackSize--;
            }
		}
		
		if(this == ModItems.syringe_taint)
		{
            if (!world.isRemote)
            {
                player.addPotionEffect(new PotionEffect(HbmPotion.taint.id, 60 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
            
            	stack.stackSize--;
            }

        	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
        	}

        	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
        	}
		}
		
		return stack;
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
    	if(this == ModItems.syringe_awesome)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
    	if(this == ModItems.syringe_awesome)
    	{
    		return EnumRarity.uncommon;
    	}
    	if(this == ModItems.euphemium_stopper)
    	{
    		return EnumRarity.epic;
    	}
    	
		return EnumRarity.common;
    }
    
    @Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer)
    {
    	World world = entity.worldObj;
    	
		if(this == ModItems.syringe_antidote)
		{
            if (!world.isRemote)
            {
            	entity.clearActivePotions();
            
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_awesome)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 50 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 50 * 20, 0));
            	entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50 * 20, 24));
            	entity.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50 * 20, 6));
            	entity.addPotionEffect(new PotionEffect(Potion.jump.id, 50 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 50 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 50 * 20, 4));
            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 4));
                
                stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_poison)
		{
            if (!world.isRemote)
            {
            	entity.attackEntityFrom(ModDamageSource.euthanized(entityPlayer, entityPlayer), 30);
                
                stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_stimpak)
		{
            if (!world.isRemote)
            {
            	entity.heal(5);
            
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_medx)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 4 * 60 * 20, 2));
            	
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_psycho)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 2 * 60 * 20, 0));
            	entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2 * 60 * 20, 0));
            	
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_super)
		{
            if (!world.isRemote)
            {
            	entity.heal(25);
            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 0));
            
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_taint)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(HbmPotion.taint.id, 60 * 20, 0));
            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
            
            	stack.stackSize--;

            	if(entityPlayer instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)entityPlayer;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty)))
            		{
            			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.euphemium_stopper)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 30 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 30 * 20, 9));
            }
		}
		
        return false;
    }
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.syringe_antidote) {
			list.add("Removes all potion effects");
		}
		if(this == ModItems.syringe_awesome) {
			list.add("Every good effect for 50 seconds");
		}
		if(this == ModItems.syringe_metal_medx) {
			list.add("Resistance III for 4 minutes");
		}
		if(this == ModItems.syringe_metal_psycho) {
			list.add("Resistance I for 2 minutes");
			list.add("Strength I for 2 minutes");
		}
		if(this == ModItems.syringe_metal_stimpak) {
			list.add("Heals 2.5 hearts");
		}
		if(this == ModItems.syringe_metal_super) {
			list.add("Heals 25 hearts");
			list.add("Slowness I for 10 seconds");
		}
		if(this == ModItems.syringe_poison) {
			list.add("Deadly");
		}
		if(this == ModItems.med_bag) {
			list.add("Full heal, regardless of max health");
			list.add("Removes negative effects");
		}
		if(this == ModItems.radaway) {
			list.add("Removes radiation effect");
		}
		if(this == ModItems.syringe_taint) {
			list.add("Tainted I for 60 seconds");
			list.add("Nausea I for 5 seconds");
			list.add("Cloud damage + taint = tainted heart effect");
		}
	}
}
