package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IGasAcceptor;
import com.hbm.interfaces.IGasSource;
import com.hbm.interfaces.IOilAcceptor;
import com.hbm.interfaces.IOilSource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDrillPacket;
import com.hbm.packet.TEIGeneratorPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineMiningDrill extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];

	public int power;
	public int warning;
	public static final int maxPower = 100000;
	int age = 0;
	int timer = 50;
	int radius = 100;
	int consumption = 100;
	int fortune = 0;
	boolean flag = true;
	public float torque;
	public float rotation;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineMiningDrill() {
		slots = new ItemStack[13];
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.miningDrill";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof ItemBattery)
				return true;
		
		if(i == 1)
			return true;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getInteger("powerTime");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("powerTime", power);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		this.consumption = 100;
		this.timer = 50;
		this.radius = 1;
		this.fortune = 0;
		
		for(int i = 10; i < 13; i++) {
			ItemStack stack = slots[i];
			
			if(stack != null) {
				if(stack.getItem() == ModItems.upgrade_effect_1) {
					this.radius += 1;
					this.consumption += 80;
				}
				if(stack.getItem() == ModItems.upgrade_effect_2) {
					this.radius += 2;
					this.consumption += 160;
				}
				if(stack.getItem() == ModItems.upgrade_effect_3) {
					this.radius += 3;
					this.consumption += 240;
				}
				if(stack.getItem() == ModItems.upgrade_speed_1) {
					this.timer -= 15;
					this.consumption += 300;
				}
				if(stack.getItem() == ModItems.upgrade_speed_2) {
					this.timer -= 30;
					this.consumption += 600;
				}
				if(stack.getItem() == ModItems.upgrade_speed_3) {
					this.timer -= 45;
					this.consumption += 900;
				}
				if(stack.getItem() == ModItems.upgrade_power_1) {
					this.consumption -= 30;
					this.timer += 5;
				}
				if(stack.getItem() == ModItems.upgrade_power_2) {
					this.consumption -= 60;
					this.timer += 10;
				}
				if(stack.getItem() == ModItems.upgrade_power_3) {
					this.consumption -= 90;
					this.timer += 15;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_1) {
					this.fortune += 1;
					this.timer += 15;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_2) {
					this.fortune += 2;
					this.timer += 30;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_3) {
					this.fortune += 3;
					this.timer += 45;
				}
			}
		}
		
		if(timer < 5)
			timer = 5;
		if(consumption < 40)
			consumption = 40;
		if(radius > 4)
			radius = 4;
		if(fortune > 3)
			fortune = 3;
		
		age++;
		if(age >= timer)
			age -= timer;
		
		if(!worldObj.isRemote) {
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(power >= consumption) {
				
				//operation start
				
				if(age == timer - 1) {
					warning = 0;
					
					//warning 0, green: drill is operational
					//warning 1, red: drill is full, has no power or the drill is jammed
					//warning 2, yellow: drill has reached max depth
					
					for(int i = this.yCoord - 1; i > this.yCoord - 1 - 100; i--) {
						
						if(i <= 5) {
							//Code 2: The drilling ended
							warning = 2;
							break;
						}

						Block b = worldObj.getBlock(this.xCoord, i, this.zCoord);
						Block b1 = worldObj.getBlock(this.xCoord, i - 1, this.zCoord);
						int meta = worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
						int meta1 = worldObj.getBlockMetadata(this.xCoord, i - 1, this.zCoord);
						ItemStack stack = new ItemStack(b.getItemDropped(meta, rand, fortune), b.quantityDropped(meta, fortune, rand), b.damageDropped(meta));
						ItemStack stack1 = new ItemStack(b1.getItemDropped(meta1, rand, fortune), b1.quantityDropped(meta1, fortune, rand), b1.damageDropped(meta1));
						
						if(i == this.yCoord - 1 && worldObj.getBlock(this.xCoord, i, this.zCoord) != ModBlocks.drill_pipe) {
							if(this.isOreo(this.xCoord, i, this.zCoord) && this.hasSpace(stack)) {
								//if(stack != null)
									//this.addItemToInventory(stack);
								worldObj.setBlock(this.xCoord, i, this.zCoord, ModBlocks.drill_pipe);
								break;
							} else {
								//Code 2: Drill jammed
								warning = 1;
								break;
							}
						}
						
						if(b1 == ModBlocks.drill_pipe) {
							continue;
						} else {
							
							flag = i != this.yCoord - 1;
							
							if(this.radius == 1)
								if(!this.drill1(this.xCoord, i, this.zCoord)) {
									if(this.isOreo(this.xCoord, i - 1, this.zCoord) && this.hasSpace(stack1)) {
										//if(stack1 != null)
											//this.addItemToInventory(stack1);
										worldObj.setBlock(this.xCoord, i - 1, this.zCoord, ModBlocks.drill_pipe);
									} else {
										//Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 2)
								if(!this.drill2(this.xCoord, i, this.zCoord)) {
									if(this.isOreo(this.xCoord, i - 1, this.zCoord) && this.hasSpace(stack1)) {
										//if(stack1 != null)
											//this.addItemToInventory(stack1);
										worldObj.setBlock(this.xCoord, i - 1, this.zCoord, ModBlocks.drill_pipe);
									} else {
										//Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 3)
								if(!this.drill3(this.xCoord, i, this.zCoord)) {
									if(this.isOreo(this.xCoord, i - 1, this.zCoord) && this.hasSpace(stack1)) {
										//if(stack1 != null)
											//this.addItemToInventory(stack1);
										worldObj.setBlock(this.xCoord, i - 1, this.zCoord, ModBlocks.drill_pipe);
									} else {
										//Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 4)
								if(!this.drill4(this.xCoord, i, this.zCoord)) {
									if(this.isOreo(this.xCoord, i - 1, this.zCoord) && this.hasSpace(stack1)) {
										//if(stack1 != null)
											//this.addItemToInventory(stack1);
										worldObj.setBlock(this.xCoord, i - 1, this.zCoord, ModBlocks.drill_pipe);
									} else {
										//Code 2: Drill jammed
										warning = 1;
									}
								}
							break;
						}
					}
				}
				
				//operation end
				
				power -= consumption;
			} else {
				warning = 1;
			}
			
			int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			TileEntity te = null;
			if(meta == 2) {
				te = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 3) {
				te = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 4) {
				te = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 5) {
				te = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			
			if(te != null && te instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(chest, i))
						break;
			}
			
			if(te != null && te instanceof TileEntityHopper) {
				TileEntityHopper hopper = (TileEntityHopper)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}
			
			if(warning == 0) {
				torque += 0.1;
				if(torque > (100/timer))
					torque = (100/timer);
			} else {
				torque -= 0.1F;
				if(torque < -(100/timer))
					torque = -(100/timer);
			}
			
			if(torque < 0) {
				torque = 0;
			}
			rotation += torque;
			if(rotation >= 360)
				rotation -= 360;

			PacketDispatcher.wrapper.sendToAll(new TEDrillPacket(xCoord, yCoord, zCoord, rotation));
		}
		
	}
	
	public boolean tryFillContainer(IInventory inventory, int slot) {
		
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = slots[slot].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						slots[slot].stackSize--;
						
						if(slots[slot].stackSize <= 0)
							slots[slot] = null;
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.stackSize++;
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(slots[slot] == null)
				return false;
			
			ItemStack sta2 = slots[slot].copy();
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[slot].stackSize--;
				
				if(slots[slot].stackSize <= 0)
					slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isOreo(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		
		if(b == Blocks.air || b == Blocks.grass || b == Blocks.dirt || 
				b == Blocks.stone || b == Blocks.sand || b == Blocks.sandstone || 
				b == Blocks.clay || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
				b == Blocks.gravel || b.isReplaceable(worldObj, x, y, z))
			return true;
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}
		
		return false;
	}
	
	public boolean isMinableOreo(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		
		if(b == Blocks.grass || b == Blocks.dirt || 
				b == Blocks.stone || b == Blocks.sand || b == Blocks.sandstone || 
				b == Blocks.clay || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
				b == Blocks.gravel || b.isReplaceable(worldObj, x, y, z))
			return true;
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}
		
		return false;
	}
	
	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined and the drill is ready to extend
	 * */
	public boolean drill1(int x, int y, int z) {
		
		if(!flag)
			return false;
		
		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x  - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x, y - 1, z))
											return false;
					
		return true;
	}
	
	public boolean drill2(int x, int y, int z) {
		
		if(!flag)
			return false;
		
		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x  - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x, y - 1, z))
																							return false;
					
		return true;
	}
	
	public boolean drill3(int x, int y, int z) {
		
		if(!flag)
			return false;
		
		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x  - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x + 3, y, z))
																							if(!tryDrill(x + 3, y, z + 1))
																								if(!tryDrill(x + 2, y, z + 2))
																									if(!tryDrill(x + 1, y, z + 3))
																										if(!tryDrill(x, y, z + 3))
																											if(!tryDrill(x - 1, y, z + 3))
																												if(!tryDrill(x - 2, y, z + 2))
																													if(!tryDrill(x - 3, y, z + 1))
																														if(!tryDrill(x - 3, y, z))
																															if(!tryDrill(x - 3, y, z - 1))
																																if(!tryDrill(x - 2, y, z - 2))
																																	if(!tryDrill(x - 1, y, z - 3))
																																		if(!tryDrill(x, y, z - 3))
																																			if(!tryDrill(x + 1, y, z - 3))
																																				if(!tryDrill(x + 2, y, z - 2))
																																					if(!tryDrill(x + 3, y, z - 1))

																																						if(!tryDrill(x, y - 1, z))
																																							return false;
					
		return true;
	}
	
	public boolean drill4(int x, int y, int z) {
		
		if(!flag)
			return false;
		
		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x  - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x + 3, y, z))
																							if(!tryDrill(x + 3, y, z + 1))
																								if(!tryDrill(x + 2, y, z + 2))
																									if(!tryDrill(x + 1, y, z + 3))
																										if(!tryDrill(x, y, z + 3))
																											if(!tryDrill(x - 1, y, z + 3))
																												if(!tryDrill(x - 2, y, z + 2))
																													if(!tryDrill(x - 3, y, z + 1))
																														if(!tryDrill(x - 3, y, z))
																															if(!tryDrill(x - 3, y, z - 1))
																																if(!tryDrill(x - 2, y, z - 2))
																																	if(!tryDrill(x - 1, y, z - 3))
																																		if(!tryDrill(x, y, z - 3))
																																			if(!tryDrill(x + 1, y, z - 3))
																																				if(!tryDrill(x + 2, y, z - 2))
																																					if(!tryDrill(x + 3, y, z - 1))

																																						if(!tryDrill(x + 4, y, z))
																																							if(!tryDrill(x + 4, y, z + 1))
																																								if(!tryDrill(x + 4, y, z + 2))
																																									if(!tryDrill(x + 3, y, z + 2))
																																										if(!tryDrill(x + 3, y, z + 3))
																																											if(!tryDrill(x + 2, y, z + 3))
																																												if(!tryDrill(x + 2, y, z + 4))
																																													if(!tryDrill(x + 1, y, z + 4))
																																														if(!tryDrill(x, y, z + 4))
																																															if(!tryDrill(x - 1, y, z + 4))
																																																if(!tryDrill(x - 2, y, z + 4))
																																																	if(!tryDrill(x - 2, y, z + 3))
																																																		if(!tryDrill(x - 3, y, z + 3))
																																																			if(!tryDrill(x - 3, y, z + 2))
																																																				if(!tryDrill(x - 4, y, z + 2))
																																																					if(!tryDrill(x - 4, y, z + 1))
																																																						if(!tryDrill(x - 4, y, z))
																																																							if(!tryDrill(x - 4, y, z - 1))
																																																								if(!tryDrill(x - 4, y, z - 2))
																																																									if(!tryDrill(x - 3, y, z - 2))
																																																										if(!tryDrill(x - 3, y, z - 3))
																																																											if(!tryDrill(x - 2, y, z - 3))
																																																												if(!tryDrill(x - 2, y, z - 4))
																																																													if(!tryDrill(x - 1, y, z - 4))
																																																														if(!tryDrill(x, y, z - 4))
																																																															if(!tryDrill(x + 1, y, z - 4))
																																																																if(!tryDrill(x + 2, y, z - 4))
																																																																	if(!tryDrill(x + 2, y, z - 3))
																																																																		if(!tryDrill(x + 3, y, z - 3))
																																																																			if(!tryDrill(x + 3, y, z - 2))
																																																																				if(!tryDrill(x + 4, y, z - 2))
																																																																					if(!tryDrill(x + 4, y, z - 1))

																																																																						if(!tryDrill(x, y - 1, z))
																																																																							return false;
					
		return true;
	}

	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined, as it is either air or unmineable
	 * */
	public boolean tryDrill(int x, int y, int z) {

		if(worldObj.getBlock(x, y, z) == Blocks.air || !isMinableOreo(x, y, z))
			return false;
		if(worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
			worldObj.func_147480_a(x, y, z, false);
			return false;
		}
		
		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		ItemStack stack = new ItemStack(b.getItemDropped(meta, rand, fortune), b.quantityDropped(meta, fortune, rand), b.damageDropped(meta));
		if(hasSpace(stack)) {
			this.addItemToInventory(stack);
			worldObj.func_147480_a(x, y, z, false);
			return true;
		}
		
		return true;
	}
	
	public boolean hasSpace(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			if(slots[i] == null)
				return true;
		}
		
		int size = st.stackSize;
		st.stackSize = 1;
		
		ItemStack[] fakeArray = slots.clone();
		boolean flag = true;
		for(int i = 0; i < stack.stackSize; i++) {
			if(!canAddItemToArray(st, fakeArray))
				flag = false;
		}
		
		return flag;
	}
	
	public void addItemToInventory(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return;
		
		int size = st.stackSize;
		st.stackSize = 1;
		
		for(int i = 0; i < size; i++)
			canAddItemToArray(st, this.slots);
		
	}
	
	public boolean canAddItemToArray(ItemStack stack, ItemStack[] array) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			
			if(array[i] != null) {
				ItemStack sta = array[i].copy();
			
				if(sta != null && sta.getItem() == st.getItem() && sta.stackSize < st.getMaxStackSize()) {
					array[i].stackSize++;
					return true;
				}
			}
		}
		
		for(int i = 1; i < 10; i++) {
			if(array[i] == null) {
				array[i] = stack.copy();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
