package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GunFolly extends Item implements IHoldableWeapon {

	@Override
	public Crosshair getCrosshair() {
		return Crosshair.L_SPLIT;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		int state = getState(stack);
		
		if(state == 0) {
			
			world.playSoundAtEntity(player, "hbm:weapon.follyOpen", 1.0F, 1.0F);
			setState(stack, 1);
			
		} else if(state == 1) {
			
			if(player.inventory.hasItem(ModItems.ammo_folly)) {

				world.playSoundAtEntity(player, "hbm:weapon.follyReload", 1.0F, 1.0F);
				player.inventory.consumeInventoryItem(ModItems.ammo_folly);
				setState(stack, 2);
			} else {
				
				world.playSoundAtEntity(player, "hbm:weapon.follyClose", 1.0F, 1.0F);
				setState(stack, 0);
			}
			
		} else if(state == 2) {

			world.playSoundAtEntity(player, "hbm:weapon.follyClose", 1.0F, 1.0F);
			setState(stack, 3);
			setTimer(stack, 100);
		} else if(state == 3) {
			
			if(getTimer(stack) == 0) {
				
				setState(stack, 0);
				world.playSoundAtEntity(player, "hbm:weapon.follyFire", 1.0F, 1.0F);

				double mult = 1.75D;
				
				player.motionX -= player.getLookVec().xCoord * mult;
				player.motionY -= player.getLookVec().yCoord * mult;
				player.motionZ -= player.getLookVec().zCoord * mult;

				if (!world.isRemote) {
					EntityBulletBaseNT bullet = new EntityBulletBaseNT(world, BulletConfigSyncingUtil.TEST_CONFIG, player);
					world.spawnEntityInWorld(bullet);
				}
			}
		}
		
		return stack;
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		if(getState(stack) == 3) {
			
			if(isCurrentItem) {
				int timer = getTimer(stack);
				
				if(timer > 0) {
					timer--;
	
					if(timer % 20 == 0 && timer != 0)
						world.playSoundAtEntity(entity, "hbm:weapon.follyBuzzer", 1.0F, 1.0F);
					
					if(timer == 0)
						world.playSoundAtEntity(entity, "hbm:weapon.follyAquired", 1.0F, 1.0F);
					
					setTimer(stack, timer);
				}
			} else {
				setTimer(stack, 100);
			}
		}
	}

	//0: closed, empty,
	//1: open, empty
	//2: open, full
	//3: closed, full
	public static void setState(ItemStack stack, int i) {
		writeNBT(stack, "state", i);
	}
	
	public static int getState(ItemStack stack) {
		return readNBT(stack, "state");
	}
	
	public static void setTimer(ItemStack stack, int i) {
		writeNBT(stack, "timer", i);
	}
	
	public static int getTimer(ItemStack stack) {
		return readNBT(stack, "timer");
	}
	
	private static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(key, value);
	}
	
	private static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger(key);
	}

}
