package com.hbm.items.tool;

import com.hbm.inventory.container.ContainerToolBox;
import com.hbm.inventory.gui.GUIToolBox;
import com.hbm.items.IItemInventory;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.ItemStackUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemToolBox extends Item implements IGUIProvider {

	@SideOnly(Side.CLIENT) protected IIcon iconOpen;
	@SideOnly(Side.CLIENT) protected IIcon iconClosed;

	public ItemToolBox() {
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.iconOpen = iconRegister.registerIcon(RefStrings.MODID + ":kit_toolbox_empty");
		this.iconClosed = iconRegister.registerIcon(RefStrings.MODID + ":kit_toolbox");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {

		if(stack.getTagCompound() != null && stack.getTagCompound().getBoolean("isOpen") && renderPass == 1) return this.iconOpen;
		return renderPass == 1 ? this.iconClosed : getIconFromDamageForRenderPass(stack.getItemDamage(), renderPass);
	}

	// Finds active rows in the toolbox (rows with items inside them).
	public List<Integer> getActiveRows(ItemStack box) {
		ItemStack[] stacks = ItemStackUtil.readStacksFromNBT(box, 24);
		if(stacks == null)
			return new ArrayList<>();
		List<Integer> activeRows = new ArrayList<>();
		for (int row = 0; row < 3; row++) {
			for (int slot = 0; slot < 8; slot++) {
				if(stacks[row * 8 + slot] != null) {
					activeRows.add(row);
					break;
				}
			}
		}
		return activeRows;
	}

	// This function genuinely hurts my soul, but it works...
	public void moveRows(ItemStack box, EntityPlayer player) {

		// Move from hotbar into array in preparation for boxing.
		ItemStack[] endingHotBar = new ItemStack[9];
		ItemStack[] stacksToTransferToBox = new ItemStack[8];

		boolean hasToolbox = false;
		int extraToolboxes = 0;
		for (int i = 0; i < 9; i++) { // Maximum allowed HotBar size is 9.

			ItemStack slot = player.inventory.getStackInSlot(i);

			if(slot != null && slot.getItem() == ModItems.toolbox && i != player.inventory.currentItem) {

				extraToolboxes++;
				player.dropPlayerItemWithRandomChoice(slot, true);
				player.inventory.setInventorySlotContents(i, null);

			} else if(i == player.inventory.currentItem) {
				hasToolbox = true;
				endingHotBar[i] = slot;
			} else {
				stacksToTransferToBox[i - (hasToolbox ? 1 : 0)] = slot;
			}
		}

		if(extraToolboxes > 0) {
			if(extraToolboxes == 1)
				player.addChatComponentMessage(new ChatComponentText("You can't toolbox a toolbox... ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED))); // TODO: tell someone else to do i18n stuff; i don't want to
			else
				player.addChatComponentMessage(new ChatComponentText("You can't toolbox a toolbox... (x" + extraToolboxes + ")").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED))); // TODO: this too :ayo:
		}

		// Move stacks around inside the box, mostly shifts rows to other rows and shifts the top row to the hotbar.
		ItemStack[] stacks = ItemStackUtil.readStacksFromNBT(box, 24);
		ItemStack[] endingStacks = new ItemStack[24];

		int lowestActiveIndex = Integer.MAX_VALUE; // Lowest active index to find which row to move *to* the hotbar.
		int lowestInactiveIndex = Integer.MAX_VALUE; // Lowest *inactive* index to find which row to move the hotbar to.

		if(stacks != null) {
			List<Integer> activeRows = getActiveRows(box);

			{ // despair
				for (int i = 0; i < 3; i++) {
					if(activeRows.contains(i))
						lowestActiveIndex = Math.min(i, lowestActiveIndex);
					else
						lowestInactiveIndex = Math.min(i, lowestInactiveIndex);
				}

				if(lowestInactiveIndex > 2) // No inactive rows...
					lowestInactiveIndex = 2; // Set to the last possible row; the items will be moved out of the way in time.
				else
					lowestInactiveIndex = Math.max(0, lowestInactiveIndex - 1); // A little shittery to make items pop into the row that's *going* to be empty.
			}

			// This entire section sucks, but honestly it's not actually that bad; it works so....
			for (Integer activeRowIndex : activeRows) {

				int activeIndex = 8 * activeRowIndex;

				if (activeRowIndex == lowestActiveIndex) { // Items to "flow" to the hotbar.
					hasToolbox = false;
					for (int i = 0; i < 9; i++) {
						if(i == player.inventory.currentItem) {
							hasToolbox = true;
							continue;
						}
						endingHotBar[i] = stacks[activeIndex + i - (hasToolbox ? 1 : 0)];
					}
					continue;
				}

				int targetIndex = 8 * (activeRowIndex - 1);

				System.arraycopy(stacks, activeIndex, endingStacks, targetIndex, 8);
			}
		}

		// Finally, move all temporary arrays into their respective locations.
		System.arraycopy(stacksToTransferToBox, 0, endingStacks, lowestInactiveIndex * 8, 8);

		for (int i = 0; i < endingHotBar.length; i++) {
			player.inventory.setInventorySlotContents(i, endingHotBar[i]);
		}

		box.setTagCompound(new NBTTagCompound());
		ItemStackUtil.addStacksToNBT(box, endingStacks);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(!world.isRemote) {
			if (player.isSneaking()) {
				moveRows(stack, player);
				player.inventoryContainer.detectAndSendChanges();
			} else {
				if(stack.getTagCompound() == null)
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean("isOpen", true);
				player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
			}
		}
		return stack;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerToolBox(player.inventory, new InventoryToolBox(player, player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIToolBox(player.inventory, new InventoryToolBox(player, player.getHeldItem()));
	}

	public static class InventoryToolBox extends IItemInventory {

		public InventoryToolBox(EntityPlayer player, ItemStack box) {
			this.player = player;
			this.target = box;
			slots = new ItemStack[this.getSizeInventory()];

			if(!box.hasTagCompound())
				box.setTagCompound(new NBTTagCompound());

			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(box, slots.length);

			if(fromNBT != null) {
				System.arraycopy(fromNBT, 0, slots, 0, slots.length);
			}
			toMarkDirty = true;
			this.markDirty();
			toMarkDirty = false;
		}

		@Override
		public int getSizeInventory() {
			return 24;
		}

		@Override
		public String getInventoryName() {
			return "container.toolBox";
		}

		@Override
		public boolean hasCustomInventoryName() {
			return target.hasDisplayName();
		}

		@Override
		public void closeInventory() {
			this.target.getTagCompound().removeTag("isOpen");
			this.player.inventory.setInventorySlotContents(this.player.inventory.currentItem, this.target);
			super.closeInventory();
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return stack.getItem() != ModItems.toolbox;
		}
	}
}
