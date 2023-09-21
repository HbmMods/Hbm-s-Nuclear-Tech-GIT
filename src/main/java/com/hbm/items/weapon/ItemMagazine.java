package com.hbm.items.weapon;

import static com.hbm.config.GeneralConfig.magazineMode;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.InventoryUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMagazine extends ItemEnumMulti
{
	// Since that's what it effectively is
	public static final String STACK_KEY = "stack", STACK_POINTER_KEY = "stackPointer", CAPACITY_KEY = "capacity";
	public ItemMagazine()
	{
		super(EnumMagazine.class, true, true);
	}

	@Override
	public void addInformation(ItemStack magazine, EntityPlayer player, @SuppressWarnings("rawtypes") List desc, boolean extra)
	{
		final EnumMagazine mag = EnumUtil.grabEnumSafely(EnumMagazine.class, magazine.getItemDamage());
		initNBT(magazine, mag.capacity);
		
//		desc.add("Capacity: " + mag.capacity);
//		desc.add("Used: " + getUsedAmount(magazine));
		desc.add(String.format("%s / %s rounds", mag.belt ? getUsedAmount(magazine) * magazine.stackSize : getUsedAmount(magazine), mag.belt ? mag.capacity * magazine.stackSize : mag.capacity));
		final int top = peekBullet(magazine, mag.capacity);
		desc.add("Top: " + (top < 0 ? "(Empty)" : BulletConfigSyncingUtil.pullConfig(top).ammo.toStack().getDisplayName()));// Inefficient!!!
		
		if (extra)
		{
			desc.add("");
			desc.add(EnumChatFormatting.BLUE + "[EXTENDED INFO]");
			desc.add("Stack: " + Arrays.toString(getStack(magazine)));
			desc.add("Stack pointer: " + getStackPointer(magazine));
		}
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			desc.add(I18nUtil.resolveKey("desc.misc.lshift", "for types"));
		else
		{
			desc.add(EnumChatFormatting.BOLD + "Types:");
			for (int i = 0; i < mag.bullets.size(); i++)
				desc.add(" - " + BulletConfigSyncingUtil.pullConfig(mag.bullets.get(i)).ammo.toStack().getDisplayName());// Still inefficient!!!
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
	{
		// Init both filled and empty mags
		for (EnumMagazine mag : EnumMagazine.values())
		{
			if (mag.bullets.isEmpty())// If the magazine/belt doesn't have any bullets it can hold, therefore skipped.
				continue;
			ItemStack magStack = new ItemStack(item, 1, mag.ordinal());
			initNBT(magStack, mag.capacity);
			// "Empty" belts make no sense
			if (!mag.belt)
			{
				// Empty
				list.add(magStack);
				
				// Filled
				magStack = magStack.copy();
			}
			setStackPointer(magStack, mag.capacity - 1);
			Arrays.fill(getStack(magStack), mag.bullets.getFirst());// First should be the default type
			list.add(magStack);
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack magazine)
	{
		final EnumMagazine mag = EnumUtil.grabEnumSafely(EnumMagazine.class, magazine.getItemDamage());
		return mag.belt ? String.format(super.getItemStackDisplayName(magazine), getUsedAmount(magazine)) : super.getItemStackDisplayName(magazine);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack magazine, World world, EntityPlayer player)
	{
		final EnumMagazine mag = EnumUtil.grabEnumSafely(EnumMagazine.class, magazine.getItemDamage());
		// Fast exit if the stack is bigger than 1, otherwise reloading won't work right
		// and if the magazine doesn't have any rounds to accept, to save performance
		if (magazine.stackSize > 1 || mag.bullets.isEmpty())
			return magazine;
		// If player is sneaking, unload instead
		if (player.isSneaking())
		{
			// Fast exit if nothing to unload
			if (getUsedAmount(magazine) <= 0)
				return magazine;
			switch (magazineMode)
			{
				case FAST:
				{
					final int[] stack = magazine.stackTagCompound.getIntArray(STACK_KEY);
					final int type = peekBullet(magazine, mag.capacity);
					final ItemStack bulletStack = BulletConfigSyncingUtil.pullConfig(type).ammo.toStack().copy();
					int stackPointer = getStackPointer(magazine);
					while (stackPointer >= 0 && bulletStack.stackSize < 64)// Don't remember what happens if you try to add a stack more than 54
					{
						if (stack[stackPointer] != type)
							break;
						bulletStack.stackSize++;
						stack[stackPointer--] = -1;
					}
					if (!player.inventory.addItemStackToInventory(bulletStack))
						player.entityDropItem(bulletStack, 0);
//						world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bulletStack));
					if (mag.links)
					{
						final ItemStack linkStack = new ItemStack(ModItems.ammo_link, bulletStack.stackSize);
						if (!player.inventory.addItemStackToInventory(linkStack))
							player.entityDropItem(linkStack, 0);
					}
					setStackPointer(magazine, stackPointer);
//					while (peekBullet(magazine, gun.mainConfig) >= 0)
//					{
//						final int bulletIndex = popBullet(magazine, gun.mainConfig);
//						final ItemStack bulletStack = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(bulletIndex)).ammo.toStack();
//						if (!player.inventory.addItemStackToInventory(bulletStack))
//							world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bulletStack));
//					}
					break;
				}
				case HIDEOUS:// Doing a new recipe handler would be pain, besides, removing is easier than adding.
				case IMMERSIVE:
				{
					// Pop off one at a time
					final int bulletID = popBullet(magazine, mag.capacity);
					final ItemStack bulletStack = BulletConfigSyncingUtil.pullConfig(bulletID).ammo.toStack();
					if (!player.inventory.addItemStackToInventory(bulletStack))
						player.entityDropItem(bulletStack, 0);
//						world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, bulletStack));
					if (mag.links)
					{
						final ItemStack linkStack = new ItemStack(ModItems.ammo_link);
						if (!player.inventory.addItemStackToInventory(linkStack))
							player.entityDropItem(linkStack, 0);
//							world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, linkStack));
					}
					break;
				}
				default: break;
			}
			return magazine;
		} else// Standard load behavior
		{
			// Fast exit if already full
			if (getUsedAmount(magazine) >= mag.capacity)
				return magazine;
			switch (magazineMode)
			{
				case FAST:
				{
					for (ItemStack stack : player.inventory.mainInventory)
					{
						if (stack == null || stack.getItem() == null)
							continue;
						// Stack points to current index, so if empty, -1
						// Do this to prevent negative array indexes with fully empty mags
						// Also helps with loading as the index now points to the next available position in the stack
						final int index = getStackPointer(magazine) + 1;
						if (index >= mag.capacity)
							return magazine;
						// If should break out of inventory loop
						boolean done = false;
						for (int configIndex = 0; configIndex < mag.bullets.size(); configIndex++)
						{
							final int bulletID = mag.bullets.get(configIndex);
							if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(stack, true))
							{
								int links = 0;// Amount of links available
								if (mag.links)
									links = countLinks(player.inventory.mainInventory);
								final int toPush = Math.min(mag.capacity - index, mag.links ? Math.min(stack.stackSize, links) : stack.stackSize);
								final int[] bullets = magazine.stackTagCompound.getIntArray(STACK_KEY);
								final int newIndex = toPush + index;
								Arrays.fill(bullets, index, newIndex, bulletID);
								magazine.stackTagCompound.setShort(STACK_POINTER_KEY, (short) (newIndex - 1));// To restore normal behavior of pointing to the top of the stack
								stack.stackSize -= toPush;
								final ComparableStack linkStack = new ComparableStack(ModItems.ammo_link, toPush);
								if (mag.links)
									InventoryUtil.tryConsumeAStack(player.inventory.mainInventory, 0, player.inventory.mainInventory.length - 1, linkStack);
								done = true;
								break;
							}
						}
						// Reloading complete
						if (done)
							return magazine;
					}
					break;
				}
				case IMMERSIVE:
				{
					// One at a time
					if (!mag.links || player.inventory.hasItem(ModItems.ammo_link))// Convert to AStack equivalents if necessary
					{
						for (ItemStack stack : player.inventory.mainInventory)
						{
							if (stack == null)
								continue;
							for (int configIndex = 0; configIndex < mag.bullets.size(); configIndex++)
							{
								final int bulletID = mag.bullets.get(configIndex);
								if (BulletConfigSyncingUtil.pullConfig(bulletID).ammo.matchesRecipe(stack, true))
								{
									pushBullet(magazine, bulletID, mag.capacity);
									stack.stackSize--;
									if (mag.links)
										player.inventory.consumeInventoryItem(ModItems.ammo_link);
									return magazine;
								}
							}
						}
					}
					break;
				}
				default: break;
			}
			return magazine;
		}
	}
	
//	private static ItemStack getFirstLinks(ItemStack[] inventory)
//	{
//		for (ItemStack stack : inventory)
//			if (stack != null && stack.getItem() == ModItems.ammo_link)
//				return stack;
//		return null;
//	}
	
	private static int countLinks(ItemStack[] inventory)
	{
		int count = 0;
		for (ItemStack stack : inventory)
			if (stack != null && stack.getItem() == ModItems.ammo_link)
				count += stack.stackSize;
		return count;
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
	public static boolean pushBullet(ItemStack magazine, int bullet, int capacity)
	{
		initNBT(magazine, capacity);
		
		return pushBullet(magazine.stackTagCompound, bullet, capacity);
	}
	
	/**
	 * Tries to pop off a bullet from the stack, fails if empty.
	 * @param magazine The magazine to pop.
	 * @param capacity The magazine's capacity to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int popBullet(ItemStack magazine, int capacity)
	{
		initNBT(magazine, capacity);
		
		return popBullet(magazine.stackTagCompound, capacity);
	}
	
	/**
	 * Tries to peek the top of the stack, fails if empty.
	 * @param magazine The magazine to peek.
	 * @param capacity The magazine's capacity to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int peekBullet(ItemStack magazine, int capacity)
	{
		initNBT(magazine, capacity);
		
		return peekBullet(magazine.stackTagCompound, capacity);
	}
	
	/**
	 * Tries to push a bullet onto the stack (NBT version), fails if at capacity.
	 * @param magazine The magazine NBT to push onto.
	 * @param bullet The bullet index to push.
	 * @param capacity The magazine's capacity to initialize, if it has not been already.
	 * @return True, if successful, false otherwise.
	 */
	public static boolean pushBullet(NBTTagCompound magazine, int bullet, int capacity)
	{
		if (!magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, capacity);
		
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
	 * Tries to pop off a bullet from the stack (NBT version), fails if empty.
	 * @param magazine The magazine NBT to pop.
	 * @param capacity The magazine's capacity to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int popBullet(NBTTagCompound magazine, int capacity)
	{
		if (capacity >= 0 && !magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, capacity);
		
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
	 * Tries to peek the top of the stack (NBT version), fails if at empty.
	 * @param magazine The magazine NBT to peek.
	 * @param capacity The magazine's capacity to initialize, if it has not been already.
	 * @return The bullet index if successful, otherwise -1.
	 */
	public static int peekBullet(NBTTagCompound magazine, int capacity)
	{
		if (capacity >= 0 && !magazine.hasKey(STACK_KEY) || !magazine.hasKey(STACK_POINTER_KEY))
			initNBT(magazine, capacity);
		
		if (magazine.getShort(STACK_POINTER_KEY) < 0)
			return -1;
		
		final int[] bulletStack = magazine.getIntArray(STACK_KEY);
		final short index = magazine.getShort(STACK_POINTER_KEY);
		final int key = bulletStack[Math.min(index, bulletStack.length - 1)];
		
		return key;
	}
	
	/**
	 * Initialize the NBT of the magazine. Does nothing if already initialized.
	 * @param stack The magazine to initialize.
	 * @param capacity The magazine's capacity.
	 */
	public static void initNBT(ItemStack stack, int capacity)
	{
		if (!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
			initNBT(stack.stackTagCompound, capacity);
		}
	}
	
	/**
	 * Initialize the NBT. Does nothing if already initialized.
	 * @param nbt The magazine NBT to initialize.
	 * @param capacity The magazine's capacity.
	 */
	public static void initNBT(NBTTagCompound nbt, int capacity)
	{
		if (!nbt.hasKey(STACK_KEY) || !nbt.hasKey(STACK_POINTER_KEY))
		{
			final int[] arr = new int[capacity];
			Arrays.fill(arr, -1);
			
			nbt.setIntArray(STACK_KEY, arr);
			nbt.setShort(STACK_POINTER_KEY, (short) -1);
			nbt.setShort(CAPACITY_KEY, (short) capacity);
		}
	}
	
}
