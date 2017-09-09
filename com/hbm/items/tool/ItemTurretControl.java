package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.blocks.bomb.TurretBase;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.bomb.TileEntityTurretBase;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemTurretControl extends Item {

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			if(player.getHeldItem() != null && stack != null && player.getHeldItem().equals(stack)) {
				if(stack.hasTagCompound()) {
					int x = stack.getTagCompound().getInteger("xCoord");
					int y = stack.getTagCompound().getInteger("yCoord");
					int z = stack.getTagCompound().getInteger("zCoord");
					
					TileEntity te = world.getTileEntity(x, y, z);
					
					if(te != null && te instanceof TileEntityTurretBase) {
						TileEntityTurretBase turret = (TileEntityTurretBase)te;

						turret.rotationYaw = player.rotationYaw;
						turret.rotationPitch = player.rotationPitch;
						if(turret.rotationPitch < -60)
							turret.rotationPitch = -60;
						if(turret.rotationPitch > 30)
							turret.rotationPitch = 30;
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.stackTagCompound != null)
		{
			list.add("Linked to:");
			list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
			list.add("Y: " + String.valueOf(itemstack.stackTagCompound.getInteger("yCoord")));
			list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
		} else {
			list.add("Please select a turret.");
		}
	}
	
	@Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if((p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) instanceof TurretBase))
		{
			if(p_77648_1_.stackTagCompound != null)
			{
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("yCoord", p_77648_5_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			} else {
				p_77648_1_.stackTagCompound = new NBTTagCompound();
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("yCoord", p_77648_5_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			}
	        if(p_77648_3_.isRemote)
			{
	        	p_77648_2_.addChatMessage(new ChatComponentText("Turret Linked!"));
			}

	        p_77648_3_.playSoundAtEntity(p_77648_2_, "hbm:item.techBleep", 1.0F, 1.0F);
        	
	        return true;
		}
    	
        return false;
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {

		int j = this.getMaxItemUseDuration(stack) - i;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);
		
        j = event.charge;
        
		if(stack.hasTagCompound()) {
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");
			
			if(world.getBlock(x, y, z) instanceof TurretBase) {
				
				((TurretBase)world.getBlock(x, y, z)).executeReleaseAction(world, j, player.rotationYaw, player.rotationPitch, x, y, z);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
        
		if(stack.hasTagCompound()) {
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");
			
			if(world.getBlock(x, y, z) instanceof TurretBase) {
				
				((TurretBase)world.getBlock(x, y, z)).executeHoldAction(world, stack.getMaxItemUseDuration() - count, player.rotationYaw, player.rotationPitch, x, y, z);
			}
		}
	}
}
