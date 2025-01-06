package com.hbm.entity.item;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemDrone.EnumDroneType;
import com.hbm.tileentity.network.TileEntityDroneDock;
import com.hbm.tileentity.network.TileEntityDroneProvider;
import com.hbm.tileentity.network.TileEntityDroneRequester;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRequestDrone extends EntityDroneBase {
	
	public ItemStack heldItem;
	public List program = new ArrayList();
	int nextActionTimer = 0;
	
	public static enum DroneProgram {
		UNLOAD, DOCK
	}

	@Override
	public void setTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y + 1;
		this.targetZ = z;
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer && !worldObj.isRemote) {
			this.setDead();
			if(heldItem != null)
				this.entityDropItem(heldItem, 1F);
			this.entityDropItem(new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal()), 1F);
		}

		return false;
	}
	public EntityRequestDrone(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			if(Vec3.createVectorHelper(motionX, motionY, motionZ).lengthVector() < 0.01) {
				
				if(nextActionTimer > 0) {
					nextActionTimer--;
				} else {
					
					if(program.isEmpty()) {
						this.setDead(); //self-destruct if no further operations are pending
						this.entityDropItem(new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal()), 1F);
						return;
					}
					
					Object next = program.get(0);
					program.remove(0);
					
					if(next instanceof BlockPos) {
						BlockPos pos = (BlockPos) next;
						this.setTarget(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					} else if(next instanceof AStack && heldItem == null) {
						
						AStack aStack = (AStack) next;
						//to make DAMN sure this fuckin idiot doesnt miss the dock
						Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
						Vec3 nextPos = Vec3.createVectorHelper(this.posX, this.posY - 4, this.posZ);
						MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

						if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

							TileEntity tile = worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
							if (tile instanceof TileEntityDroneProvider) {
								TileEntityDroneProvider provider = (TileEntityDroneProvider) tile;

								for (int i = 0; i < provider.slots.length; i++) {
									ItemStack stack = provider.slots[i];

									if (stack != null && aStack.matchesRecipe(stack, true)) {
										this.heldItem = stack.copy();
										this.setAppearance(1);
										worldObj.playSoundEffect(posX, posY, posZ, "hbm:item.unpack", 0.5F, 0.75F);
										provider.slots[i] = null;
										provider.markDirty();
										break;
									}
								}
							}
						}
						nextActionTimer = 5;
					} else if(next == DroneProgram.UNLOAD && this.heldItem != null) {
						Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
						Vec3 nextPos = Vec3.createVectorHelper(this.posX, this.posY - 4, this.posZ);
						MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

						if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

							TileEntity tile = worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
							if (tile instanceof TileEntityDroneRequester) {
								TileEntityDroneRequester requester = (TileEntityDroneRequester) tile;

								for (int i = 9; i < 18; i++) {
									ItemStack stack = requester.slots[i];
									if (stack != null && stack.getItem() == heldItem.getItem() && stack.getItemDamage() == heldItem.getItemDamage()) {
										int toTransfer = Math.min(stack.getMaxStackSize() - stack.stackSize, heldItem.stackSize);
										requester.slots[i].stackSize += toTransfer;
										this.heldItem.stackSize -= toTransfer;
									}
								}

								if (this.heldItem.stackSize <= 0) this.heldItem = null;

								if (this.heldItem != null) for (int i = 9; i < 18; i++) {
									if (requester.slots[i] == null) {
										requester.slots[i] = this.heldItem.copy();
										this.heldItem = null;
										break;
									}
								}

								if (this.heldItem == null) {
									this.setAppearance(0);
									worldObj.playSoundEffect(posX, posY, posZ, "hbm:item.unpack", 0.5F, 0.75F);
								}

								requester.markDirty();
							}
						}
						nextActionTimer = 5;
					} else if(next == DroneProgram.DOCK) {
						Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
						Vec3 nextPos = Vec3.createVectorHelper(this.posX, this.posY - 4, this.posZ);
						MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

						if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

							TileEntity tile = worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
							if (tile instanceof TileEntityDroneDock) {
								TileEntityDroneDock dock = (TileEntityDroneDock) tile;
								ItemStack drone = new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal());
								for (int i = 0; i < dock.slots.length; i++) {
									if (dock.slots[i] == null) {
										this.setDead();
										if(heldItem != null){
											if(i != 9 && dock.slots[i + 1] == null){
												dock.slots[i + 1] = heldItem.copy();
											}
										}
										dock.slots[i] = drone.copy();
										this.worldObj.playSoundEffect(dock.xCoord + 0.5, dock.yCoord + 0.5, dock.zCoord + 0.5, "hbm:block.storageClose", 2.0F, 1.0F);
										break;
									} else if (dock.slots[i].isItemEqual(drone) && dock.slots[i].stackSize < 64){
										this.setDead();
										if(heldItem != null){
											if(i != 9 && dock.slots[i + 1] == null){
												dock.slots[i + 1] = heldItem.copy();
											}
										}
										dock.slots[i].stackSize++;
										this.worldObj.playSoundEffect(dock.xCoord + 0.5, dock.yCoord + 0.5, dock.zCoord + 0.5, "hbm:block.storageClose", 2.0F, 1.0F);
										break;
									}
								}
							}
						}
						if (!this.isDead) {
							this.setDead();
							if(heldItem != null)
								this.entityDropItem(heldItem, 1F);
							this.entityDropItem(new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal()), 1F);
						}

					}
				}
			}
		}
	}

	@Override
	public double getSpeed() {
		return 0.625D;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		if(nbt.hasKey("held")) {
			NBTTagCompound stack = nbt.getCompoundTag("held");
			this.heldItem = ItemStack.loadItemStackFromNBT(stack);
		}
		
		nextActionTimer = 5;

		this.dataWatcher.updateObject(10, nbt.getByte("app"));
		
		int size = nbt.getInteger("programSize");
		
		for(int i = 0; i < size; i++) {
			NBTTagCompound data = nbt.getCompoundTag("program" + i);
			String pType = data.getString("type");
			
			if("pos".equals(pType)) {
				int[] pos = data.getIntArray("pos");
				this.program.add(new BlockPos(pos[0], pos[1], pos[2]));
			} else if("unload".equals(pType)) {
				this.program.add(DroneProgram.UNLOAD);
			} else if("dock".equals(pType)) {
				this.program.add(DroneProgram.DOCK);
			} else if("comp".equals(pType)) {
				ComparableStack comp = new ComparableStack(Item.getItemById(nbt.getInteger("id")), 1, nbt.getInteger("meta"));
				this.program.add(comp);
			} else if("dict".equals(pType)) {
				OreDictStack dict = new OreDictStack(nbt.getString("dict"));
				this.program.add(dict);
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		if(heldItem != null) {
			NBTTagCompound stack = new NBTTagCompound();
			this.heldItem.writeToNBT(stack);
			nbt.setTag("held", stack);
		}

		nbt.setByte("app", this.dataWatcher.getWatchableObjectByte(10));
		
		int size = this.program.size();
		nbt.setInteger("programSize", size);
		
		for(int i = 0; i < size; i++) {
			NBTTagCompound data = new NBTTagCompound();
			Object p = this.program.get(i);
			
			if(p instanceof BlockPos) {
				BlockPos pos = (BlockPos) p;
				data.setString("type", "pos");
				data.setIntArray("pos", new int[] {pos.getX(), pos.getY(), pos.getZ()});
			} else if(p instanceof AStack) {
				
				// neither of these wretched fungii works correctly, but so long as the pathing works (which it does), it means that the drone will
				// eventually return to the dock and not got lost, and simply retry the task
				if(p instanceof ComparableStack) {
					ComparableStack comp = (ComparableStack) p;
					data.setString("type", "comp");
					data.setInteger("id", Item.getIdFromItem(comp.item));
					data.setInteger("meta", comp.meta);
				} else {
					OreDictStack dict = (OreDictStack) p;
					data.setString("type", "dict");
					data.setString("dict", dict.name);
				}
				
			} else if(p == DroneProgram.UNLOAD) {
				data.setString("type", "unload");
				
			} else if(p == DroneProgram.DOCK) {
				data.setString("type", "dock");
				
			}
			
			nbt.setTag("program" + i, data);
		}
	}
}
