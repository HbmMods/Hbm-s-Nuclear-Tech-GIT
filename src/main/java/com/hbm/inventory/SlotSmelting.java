package com.hbm.inventory;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotSmelting extends Slot {
	
	private EntityPlayer thePlayer;
	private int itemCountBuffer;

	public SlotSmelting(EntityPlayer player, IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
		this.thePlayer = player;
	}
	
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	public ItemStack decrStackSize(int amount) {
		
		if(this.getHasStack()) {
			this.itemCountBuffer += Math.min(amount, this.getStack().stackSize);
		}
		
		return super.decrStackSize(amount);
	}

	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		super.onPickupFromSlot(player, stack);
	}
	
	protected void onCrafting(ItemStack stack, int amount) {
		this.itemCountBuffer += amount;
		this.onCrafting(stack);
	}
	
	protected void onCrafting(ItemStack stack) {
		
		stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.itemCountBuffer);

		if(!this.thePlayer.worldObj.isRemote) {
			
			int buffer = this.itemCountBuffer;
			float exp = FurnaceRecipes.smelting().func_151398_b(stack);
			int remainingExp;

			if(exp == 0.0F) {
				buffer = 0;
				
			} else if(exp < 1.0F) {
				remainingExp = MathHelper.floor_float((float) buffer * exp);

				if(remainingExp < MathHelper.ceiling_float_int((float) buffer * exp) && (float) Math.random() < (float) buffer * exp - (float) remainingExp) {
					++remainingExp;
				}

				buffer = remainingExp;
			}

			while(buffer > 0) {
				remainingExp = EntityXPOrb.getXPSplit(buffer);
				buffer -= remainingExp;
				this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, remainingExp));
			}
		}

		this.itemCountBuffer = 0;

		FMLCommonHandler.instance().firePlayerSmeltedEvent(thePlayer, stack);

		if(stack.getItem() == Items.iron_ingot) {
			this.thePlayer.addStat(AchievementList.acquireIron, 1);
		}

		if(stack.getItem() == Items.cooked_fished) {
			this.thePlayer.addStat(AchievementList.cookFish, 1);
		}
	}
}
