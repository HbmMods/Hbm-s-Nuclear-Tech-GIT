package com.hbm.items.weapon;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemGunZOMG extends ItemGunBase {
	public ItemGunZOMG(GunConfiguration config) {
		super(config);
	}
	
	protected void updateValidation(ItemStack stack, EntityPlayer player) {
		if (player.inventory.hasItem(ModItems.nugget_euphemium) || player.inventory.hasItem(ModItems.ingot_euphemium)) {
			if (player.inventory.hasItem(ModItems.polaroid)) {
				if (getValidation(stack) != 2) {
					player.addChatMessage(new ChatComponentText("[ZOMG] Welcome, superuser!"));
				}
				setValidation(stack, 2);
				setMagType(stack, BulletConfigSyncingUtil.ZOMG_SUPERUSER);
				firingSound = "hbm:weapon.zomgShoot";
			} else {
				if (getValidation(stack) != 1) {
					player.addChatMessage(new ChatComponentText("[ZOMG] Welcome, user!"));
				}
				setValidation(stack, 1);
				setMagType(stack, BulletConfigSyncingUtil.ZOMG_CANNON);
				firingSound = "hbm:weapon.osiprShoot";
			}
		} else {
			if (getValidation(stack) != 0) {
				player.addChatMessage(new ChatComponentText("[ZOMG] Lost validation! External negative gravity well no longer detected!"));
			} else {
				player.addChatMessage(new ChatComponentText("[ZOMG] Validation failed! No external negative gravity well found."));
			}
			setValidation(stack, 0);
		}
		// Cooldown so that validation is not spammed
		setDelay(stack, getDelay(stack) + 10);
	}
	
	protected boolean maintainValidation(ItemStack stack, EntityPlayer player) {
		if (getValidation(stack) >= 1 && player.inventory.hasItem(ModItems.nugget_euphemium) || player.inventory.hasItem(ModItems.ingot_euphemium)) {
			if (getValidation(stack) >= 2) {
				if (!player.inventory.hasItem(ModItems.polaroid)) {
					player.addChatMessage(new ChatComponentText("[ZOMG] Superuser status lost! Welcome, user."));
					setValidation(stack, 1);
					setMagType(stack, BulletConfigSyncingUtil.ZOMG_CANNON);
					firingSound = "hbm:weapon.osiprShoot";
				}
			}
			return true;
		} else {
			setValidation(stack, 0);
			player.addChatMessage(new ChatComponentText("[ZOMG] Lost validation! External negative gravity well no longer detected!"));
			return false;
		}
	}
	
	@Override
	protected boolean canShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		if (main && getValidation(stack) != 0) {
			return maintainValidation(stack, player);
		}
		if (!main) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		updateValidation(stack, player);
	}
	
	
	// NBT Tags
	public static void setValidation(ItemStack stack, int level) {
		writeNBT(stack, "zomg_verification", level);
	}
	
	public static int getValidation(ItemStack stack) {
		return readNBT(stack, "zomg_verification");
	}
}
