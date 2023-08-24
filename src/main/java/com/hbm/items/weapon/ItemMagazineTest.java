package com.hbm.items.weapon;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMagazineTest extends Item
{
	// Since that's what it effectively is
	public static final String STACK_KEY = "stack", STACK_POINTER_KEY = "stackPointer", CAPACITY_KEY = "capacity";
	private final ItemGunBase gun;
	public ItemMagazineTest(Item gun)
	{
		this.gun = (ItemGunBase) gun;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack magazine, EntityPlayer player, List desc, boolean extra)
	{
		initNBT(magazine, gun.mainConfig);
		
		desc.add("Capacity: " + gun.mainConfig.ammoCap);
		desc.add("Used: " + getUsedAmount(magazine));
		final int top = peekBullet(magazine, gun.mainConfig);
		desc.add("Top: " + (top < 0 ? "(Empty)" : BulletConfigSyncingUtil.pullConfig(top).ammo.toStack().getDisplayName()));// Inefficient!!!
		
		if (extra)
		{
			desc.add("");
			desc.add(EnumChatFormatting.BLUE + "[EXTENDED INFO]");
			desc.add("Stack: " + Arrays.toString(magazine.stackTagCompound.getIntArray(STACK_KEY)));
			desc.add("Stack pointer: " + getStackPointer(magazine));
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack magazine, World world, EntityPlayer player)
	{
		// Fast exit if the stack is bigger than 1, otherwise reloading won't work right
		if (magazine.stackSize > 1)
			return magazine;
		// If player is sneaking, unload instead
		if (player.isSneaking())
		{
			if (getUsedAmount(magazine) <= 0)
				return magazine;
			final int[] stack = magazine.stackTagCompound.getIntArray(STACK_KEY);
			final int type = peekBullet(magazine, gun.mainConfig);
			int stackPointer = getStackPointer(magazine);
			while (stackPointer >= 0)
			{
				if (stack[stackPointer] != type)
					break;
				final int bulletID = stack[stackPointer];
				final ItemStack bulletStack = BulletConfigSyncingUtil.pullConfig(bulletID).ammo.toStack();
				if (!player.inventory.addItemStackToInventory(bulletStack))
					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bulletStack));
				stack[stackPointer--] = -1;
			}
			setStackPointer(magazine, stackPointer);
//			while (peekBullet(magazine, gun.mainConfig) >= 0)
//			{
//				final int bulletIndex = popBullet(magazine, gun.mainConfig);
//				final ItemStack bulletStack = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(bulletIndex)).ammo.toStack();
//				if (!player.inventory.addItemStackToInventory(bulletStack))
//					world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bulletStack));
//			}
			return magazine;
		} else// Standard load behavior
		{
			// Stack points to current index, so if empty, -1
			// Do this to prevent negative array indexes with fully empty mags
			// Also helps with loading as the index now points to the next available position in the stack
			final int index = getStackPointer(magazine) + 1;
			if (index + 1 >= gun.mainConfig.ammoCap)
				return magazine;
			for (ItemStack stack : player.inventory.mainInventory)
			{
				if (stack == null || stack.getItem() == null)
					continue;
				boolean done = false;
				for (int configIndex = 0; configIndex < gun.mainConfig.config.size(); configIndex++)
				{
					final int bulletID = gun.mainConfig.config.get(configIndex);
					if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(stack, true))
					{
						final int toPush = Math.min(gun.mainConfig.ammoCap - index, stack.stackSize);
						final int[] bullets = magazine.stackTagCompound.getIntArray(STACK_KEY);
						final int newIndex = toPush + index;
						Arrays.fill(bullets, index, newIndex, bulletID);
						magazine.stackTagCompound.setShort(STACK_POINTER_KEY, (short) (newIndex - 1));// To restore normal behavior of pointing to the top of the stack
						stack.stackSize -= toPush;
						done = true;
						break;
					}
				}
				if (done)
					return magazine;
			}
			return magazine;
		}
	}
	
	/**
	 * Gets the used capacity of the magazine.
	 * @param magazine The magazine to check.
	 * @return The stack pointer plus 1.
	 */
	public static int getUsedAmount(ItemStack magazine)
	{
		return getStackPointer(magazine) + 1;
	}
	
	/**
	 * Gets the stack pointer of the magazine.
	 * @param magazine The magazine to check.
	 * @return The stack pointer.
	 */
	public static int getStackPointer(ItemStack magazine)
	{
		return getStackPointer(magazine.stackTagCompound);
	}
	
	/**
	 * Gets the stack pointer of the magazine (NBT version).
	 * @param nbt The magazine to check.
	 * @return The stack pointer.
	 */
	public static int getStackPointer(NBTTagCompound nbt)
	{
		return nbt.getShort(STACK_POINTER_KEY);
	}
	
	/**
	 * Sets the stack pointer of the magazine.
	 * @param magazine The magazine to set.
	 * @param sp The stack pointer.
	 */
	public static void setStackPointer(ItemStack magazine, int sp)
	{
		setStackPointer(magazine.stackTagCompound, sp);
	}
	
	/**
	 * Sets the stack pointer of the magazine (NBT version).
	 * @param nbt The magazine to set.
	 * @param sp The stack pointer.
	 */
	public static void setStackPointer(NBTTagCompound nbt, int sp)
	{
		nbt.setShort(STACK_POINTER_KEY, (short) sp);
	}
	
	/**
	 * Gets the stack of the magazine (NBT version).
	 * @param nbt The magazine to check.
	 * @return A reference to the stack.
	 */
	public static int[] getStack(ItemStack magazine)
	{
		return getStack(magazine.stackTagCompound);
	}
	
	/**
	 * Gets the stack of the magazine (NBT version).
	 * @param nbt The magazine to check.
	 * @return A reference to the stack.
	 */
	public static int[] getStack(NBTTagCompound nbt)
	{
		return nbt.getIntArray(STACK_KEY);
	}
	
	/**
	 * Tries to push a bullet onto the stack, fails if at capacity.
	 * @param magazine The magazine to push onto.
	 * @param bullet The bullet index to push.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return True, if successful, false otherwise.
	 */
	public static boolean pushBullet(ItemStack magazine, int bullet, GunConfiguration config)
	{
		initNBT(magazine, config);
		
		return pushBullet(magazine.stackTagCompound, bullet, config);
	}
	
	/**
	 * Tries to pop off a bullet from the stack, fails if empty.
	 * @param magazine The magazine to pop.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int popBullet(ItemStack magazine, GunConfiguration config)
	{
		initNBT(magazine, config);
		
		return popBullet(magazine.stackTagCompound, config);
	}
	
	/**
	 * Tries to peek the top of the stack, fails if empty.
	 * @param magazine The magazine to peek.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int peekBullet(ItemStack magazine, GunConfiguration config)
	{
		initNBT(magazine, config);
		
		return peekBullet(magazine.stackTagCompound, config);
	}
	
	/**
	 * Tries to push a bullet onto the stack (NBT version), fails if at capacity.
	 * @param magazine The magazine NBT to push onto.
	 * @param bullet The bullet index to push.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return True, if successful, false otherwise.
	 */
	public static boolean pushBullet(NBTTagCompound magazine, int bullet, GunConfiguration config)
	{
		if (!magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, config);
		
		final int[] bulletStack = magazine.getIntArray(STACK_KEY);
		if (magazine.getShort(STACK_POINTER_KEY) < bulletStack.length)
		{
			short sp = magazine.getShort(STACK_POINTER_KEY);
			bulletStack[++sp] = bullet;
//			magazine.setIntArray(STACK_KEY, bulletStack);
			magazine.setShort(STACK_POINTER_KEY, sp);
			return true;
		} else
			return false;
	}
	
	/**
	 * Tries to pop off a bullet from the stack (NBT version), fails if at capacity.
	 * @param magazine The magazine NBT to pop.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int popBullet(NBTTagCompound magazine, @Nullable GunConfiguration config)
	{
		if (config != null && !magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, config);
		
		if (magazine.getShort(STACK_POINTER_KEY) < 0)
			return -1;
		
		final int[] bulletStack = magazine.getIntArray(STACK_KEY);
		short sp = magazine.getShort(STACK_POINTER_KEY);
		final int key = bulletStack[sp];
		bulletStack[sp--] = -1;
//		magazine.setIntArray(STACK_KEY, bulletStack);
		magazine.setShort(STACK_POINTER_KEY, sp);
		
		return key;
	}
	
	/**
	 * Tries to peek the top of the stack (NBT version), fails if at capacity.
	 * @param magazine The magazine NBT to peek.
	 * @param config The gun configuration to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int peekBullet(NBTTagCompound magazine, @Nullable GunConfiguration config)
	{
		if (config != null && !magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, config);
		
		if (magazine.getShort(STACK_POINTER_KEY) < 0)
			return -1;
		
		final int[] bulletStack = magazine.getIntArray(STACK_KEY);
		final short index = magazine.getShort(STACK_POINTER_KEY);
		final int key = bulletStack[index];
		
		return key;
	}
	
	/**
	 * Initialize the NBT of the magazine. Does nothing if already initialized.
	 * @param stack The magazine to initialize.
	 * @param config The configuration to base the initialization on.
	 */
	public static void initNBT(ItemStack stack, GunConfiguration config)
	{
		if (!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
			initNBT(stack.stackTagCompound, config);
		}
	}
	
	/**
	 * Initialize the NBT. Does nothing if already initialized.
	 * @param nbt The magazine NBT to initialize.
	 * @param config The configuration to base the initialization on.
	 */
	public static void initNBT(NBTTagCompound nbt, GunConfiguration config)
	{
		if (!nbt.hasKey(STACK_KEY) || !nbt.hasKey(STACK_POINTER_KEY))
		{
			final int[] arr = new int[config.ammoCap];
			Arrays.fill(arr, -1);
			
			nbt.setIntArray(STACK_KEY, arr);
			nbt.setShort(STACK_POINTER_KEY, (short) -1);
			nbt.setShort(CAPACITY_KEY, (short) config.ammoCap);
		}
	}
	
}
