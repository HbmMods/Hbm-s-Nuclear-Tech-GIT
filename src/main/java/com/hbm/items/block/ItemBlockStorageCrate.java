package com.hbm.items.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.*;
import com.hbm.inventory.gui.*;
import com.hbm.items.IItemInventory;
import com.hbm.items.tool.ItemKey;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.storage.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemBlockStorageCrate extends ItemBlockBase implements IGUIProvider {

	public ItemBlockStorageCrate(Block block) {
		super(block);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		Block block = Block.getBlockFromItem(player.getHeldItem().getItem());
		if(block == ModBlocks.mass_storage) return stack; // Genuinely can't figure out how to make this part work, so I'm just not gonna mess with it.

		if(!world.isRemote && stack.stackSize == 1) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("lock")) {
				for (ItemStack item : player.inventory.mainInventory) {
					if(item == null) // Skip if no item.
						continue;
					if(!(item.getItem() instanceof ItemKey)) // Skip if item isn't a key.
						continue;
					if(item.stackTagCompound == null) // Skip if there is no NBT (wouldn't open it anyway).
						continue;
					if (item.stackTagCompound.getInteger("pins") == stack.stackTagCompound.getInteger("lock")) { // Check if pins are equal (if it can open it)
						TileEntityCrateBase.spawnSpiders(player, world, stack);
						player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
						break;
					}
				}
				return stack; // Return early if it was locked.
			}
			TileEntityCrateBase.spawnSpiders(player, world, stack);
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0); // If there is no lock then don't bother checking.
		}

		return stack;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Block block = Block.getBlockFromItem(player.getHeldItem().getItem());
		if(block == ModBlocks.crate_iron) return new ContainerCrateIron(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_steel) return new ContainerCrateSteel(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_desh) return new ContainerCrateDesh(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_tungsten) return new ContainerCrateTungsten(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_template) return new ContainerCrateTemplate(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.safe) return new ContainerSafe(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		throw new NullPointerException();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Block block = Block.getBlockFromItem(player.getHeldItem().getItem());
		if(block == ModBlocks.crate_iron) return new GUICrateIron(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_steel) return new GUICrateSteel(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_desh) return new GUICrateDesh(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_tungsten) return new GUICrateTungsten(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.crate_template) return new GUICrateTemplate(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		if(block == ModBlocks.safe) return new GUISafe(player.inventory, new InventoryCrate(player, player.getHeldItem()));
		throw new NullPointerException();
	}

	public static class InventoryCrate extends IItemInventory {

		public InventoryCrate(EntityPlayer player, ItemStack crate) {

			this.player = player;
			this.target = crate;

			slots = new ItemStack[this.getSizeInventory()];
			if(crate.stackTagCompound == null)
				crate.stackTagCompound = new NBTTagCompound();
			else if(!player.worldObj.isRemote) {
				for (int i = 0; i < this.getSizeInventory(); i++)
					this.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(crate.stackTagCompound.getCompoundTag("slot" + i)));
			}
			toMarkDirty = true;
			this.markDirty();
			toMarkDirty = false;
		}

		@Nonnull
		public static TileEntityCrateBase findCrateType(Item crate) {
			Block block = Block.getBlockFromItem(crate);
			if(block == ModBlocks.crate_iron) return new TileEntityCrateIron();
			if(block == ModBlocks.crate_steel) return new TileEntityCrateSteel();
			if(block == ModBlocks.crate_desh) return new TileEntityCrateDesh();
			if(block == ModBlocks.crate_tungsten) return new TileEntityCrateTungsten();
			if(block == ModBlocks.crate_template) return new TileEntityCrateTemplate();
			if(block == ModBlocks.safe) return new TileEntitySafe();
			throw new NullPointerException();
		}

		@Override
		public int getSizeInventory() {
			return findCrateType(target.getItem()).getSizeInventory();
		}

		@Override
		public String getInventoryName() {
			return findCrateType(target.getItem()).getInventoryName();
		}

		@Override
		public boolean hasCustomInventoryName() {
			return target.hasDisplayName();
		}

		@Override
		public void markDirty() { // I HATE THIS SO MUCH

			if(player.getEntityWorld().isRemote || !toMarkDirty) { // go the fuck away
				return;
			}

			NBTTagCompound nbt = new NBTTagCompound();

			int invSize = this.getSizeInventory();

			for(int i = 0; i < invSize; i++) {

				ItemStack stack = this.getStackInSlot(i);
				if(stack == null)
					continue;

				NBTTagCompound slot = new NBTTagCompound();
				stack.writeToNBT(slot);
				nbt.setTag("slot" + i, slot);
			}

			if(target.stackTagCompound != null) { // yes it's a bit jank, but it wants to clear otherwise so...
				if(target.stackTagCompound.hasKey("lock"))
					nbt.setInteger("lock", target.stackTagCompound.getInteger("lock"));
				if(target.stackTagCompound.hasKey("lockMod"))
					nbt.setDouble("lockMod", target.stackTagCompound.getDouble("lockMod"));
				if(target.stackTagCompound.hasKey("spiders"))
					nbt.setBoolean("spiders", target.stackTagCompound.getBoolean("spiders")); // fuck you!!
			}

			target.setTagCompound(checkNBT(nbt));

			player.inventory.setInventorySlotContents(player.inventory.currentItem, target);
		}
	}
}
