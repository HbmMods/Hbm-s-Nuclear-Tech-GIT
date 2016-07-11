package com.hbm.items;

import java.util.Random;

import com.hbm.lib.ModDamageSource;

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
}
