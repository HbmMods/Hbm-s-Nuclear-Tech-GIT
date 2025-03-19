package com.hbm.items.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.*;
import com.hbm.inventory.gui.*;
import com.hbm.items.tool.ItemKey;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.storage.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Random;

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

	public static class InventoryCrate implements IInventory {

		public final EntityPlayer player;
		public final ItemStack crate;
		public ItemStack[] slots;

		private boolean toMarkDirty = false;

		public InventoryCrate(EntityPlayer player, ItemStack crate) {

			this.player = player;
			this.crate = crate;

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
			return findCrateType(crate.getItem()).getSizeInventory();
		}

		@Override
		public String getInventoryName() {
			return findCrateType(crate.getItem()).getInventoryName();
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return slots[slot];
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			ItemStack stack = getStackInSlot(slot);
			if (stack != null) {
				if (stack.stackSize > amount) {
					stack = stack.splitStack(amount);
				} else {
					setInventorySlotContents(slot, null);
				}
			}
			return stack;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			ItemStack stack = getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return stack;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			if(stack != null) {
				stack.stackSize = Math.min(stack.stackSize, this.getInventoryStackLimit());
			}

			slots[slot] = stack;
		}

		@Override
		public boolean hasCustomInventoryName() {
			return crate.hasDisplayName();
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public void markDirty() { // I HATE THIS SO MUCH

			if(player.worldObj.isRemote) { // go the fuck away
				return;
			}

			if(!toMarkDirty) { // ok fuck you too
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

			if(crate.stackTagCompound != null) { // yes it's a bit jank, but it wants to clear otherwise so...
				if(crate.stackTagCompound.hasKey("lock"))
					nbt.setInteger("lock", crate.stackTagCompound.getInteger("lock"));
				if(crate.stackTagCompound.hasKey("lockMod"))
					nbt.setDouble("lockMod", crate.stackTagCompound.getDouble("lockMod"));
				if(crate.stackTagCompound.hasKey("spiders"))
					nbt.setBoolean("spiders", crate.stackTagCompound.getBoolean("spiders")); // fuck you!!
			}

			if(!nbt.hasNoTags()) {

				Random random = new Random();

				try {
					byte[] abyte = CompressedStreamTools.compress(nbt);

					if(abyte.length > 6000) {
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Warning: Container NBT exceeds 6kB, contents will be ejected!"));
						for(int i1 = 0; i1 < invSize; ++i1) {
							ItemStack itemstack = this.getStackInSlot(i1);

							if(itemstack != null) {
								float f = random.nextFloat() * 0.8F + 0.1F;
								float f1 = random.nextFloat() * 0.8F + 0.1F;
								float f2 = random.nextFloat() * 0.8F + 0.1F;

								while(itemstack.stackSize > 0) {
									int j1 = random.nextInt(21) + 10;

									if(j1 > itemstack.stackSize) {
										j1 = itemstack.stackSize;
									}

									itemstack.stackSize -= j1;
									EntityItem entityitem = new EntityItem(player.worldObj, player.posX + f, player.posY + f1, player.posZ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

									if(itemstack.hasTagCompound()) {
										entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
									}

									float f3 = 0.05F;
									entityitem.motionX = (float) random.nextGaussian() * f3 + player.motionX;
									entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F + player.motionY;
									entityitem.motionZ = (float) random.nextGaussian() * f3 + player.motionZ;
									player.worldObj.spawnEntityInWorld(entityitem);
								}
							}
						}

						crate.setTagCompound(null); // Wipe tag compound to clear crate.
						player.inventory.setInventorySlotContents(player.inventory.currentItem, crate);
						return;
					}
				} catch(IOException ignored) { }
			}

			crate.setTagCompound(nbt);

			player.inventory.setInventorySlotContents(player.inventory.currentItem, crate);
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			return true;
		}

		@Override
		public void openInventory() {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:block.crateOpen", 1.0F, 0.8F);
		}

		@Override
		public void closeInventory() {
			toMarkDirty = true;
			markDirty();
			toMarkDirty = false;
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:block.crateClose", 1.0F, 0.8F);
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return true;
		}
	}
}
