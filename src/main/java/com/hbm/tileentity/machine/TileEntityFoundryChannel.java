package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryChannel extends TileEntityFoundryBase {
	
	public int nextUpdate;
	public int lastFlow = 0;

	protected NTMMaterial neighborType;
	protected boolean hasCheckedNeighbors;
	protected int unpropagateTime;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			// Initialise before allowing pours, so newly added channels will avoid causing clog feeds
			if(!hasCheckedNeighbors) {
				List<TileEntityFoundryChannel> visited = new ArrayList<TileEntityFoundryChannel>();
				visited.add(this);

				neighborType = checkNeighbors(visited);
				hasCheckedNeighbors = true;
			}
			
			if(this.type == null && this.amount != 0) {
				this.amount = 0;
			}
			
			nextUpdate--;
			
			if(nextUpdate <= 0 && this.amount > 0 && this.type != null) {
				
				boolean hasOp = false;
				nextUpdate = 5;
				
				List<Integer> ints = new ArrayList<Integer>() {{ add(2); add(3); add(4); add(5); }};
				Collections.shuffle(ints);
				if(lastFlow > 0) {
					ints.remove((Integer) this.lastFlow);
					ints.add(this.lastFlow);
				}
				
				for(Integer i : ints) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					
					if(b instanceof ICrucibleAcceptor && b != ModBlocks.foundry_channel) {
						ICrucibleAcceptor acc = (ICrucibleAcceptor) b;
						
						if(acc.canAcceptPartialFlow(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir.getOpposite(), new MaterialStack(this.type, this.amount))) {
							MaterialStack left = acc.flow(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir.getOpposite(), new MaterialStack(this.type, this.amount));
							if(left == null) {
								this.type = null;
								this.amount = 0;

								propagateMaterial(null);
							} else {
								this.amount = left.amount;
							}
							hasOp = true;
							break;
						}
					}
				}
				
				if(!hasOp) {
					for(Integer i : ints) {
						ForgeDirection dir = ForgeDirection.getOrientation(i);
						TileEntity b = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						
						if(b instanceof TileEntityFoundryChannel) {
							TileEntityFoundryChannel acc = (TileEntityFoundryChannel) b;
							
							if(acc.type == null || acc.type == this.type || acc.amount == 0) {
								acc.type = this.type;
								
								acc.lastFlow = dir.getOpposite().ordinal();
								
								if(worldObj.rand.nextInt(5) == 0 || this.amount == 1) { //force swap operations with single quanta to keep them moving
									//1:4 chance that the fill states are simply swapped
									//this promotes faster spreading and prevents spread limits
									int buf = this.amount;
									this.amount = acc.amount;
									acc.amount = buf;
									
								} else {
									//otherwise, equalize the neighbors
									int diff = this.amount - acc.amount;
									
									if(diff > 0) {
										diff /= 2;
										this.amount -= diff;
										acc.amount += diff;
									}
								}
							}
						}
					}
				}
			}

			if(neighborType != null && amount == 0) unpropagateTime++;

			// every 5 seconds do a unprop test, will only occur once per contiguous channel per 5 seconds due to the timer getting updated in all channels from the prop
			if(unpropagateTime > 100) {
				propagateMaterial(null);
			}
			
			if(this.amount == 0) {
				this.lastFlow = 0;
				this.nextUpdate = 5;
			} else {
				unpropagateTime = 0;
			}
		}
		
		super.updateEntity();
	}

	@Override
	public int getCapacity() {
		return MaterialShapes.INGOT.q(2);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.lastFlow = nbt.getByte("flow");
		this.neighborType = Mats.matById.get(nbt.getInteger("nType"));
		this.hasCheckedNeighbors = nbt.getBoolean("init");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("flow", (byte) this.lastFlow);
		nbt.setInteger("nType", this.neighborType != null ? this.neighborType.id : -1);
		nbt.setBoolean("init", hasCheckedNeighbors);
	}

	/**
	 * Channels accept pouring as normal, except when neighbor channels already have material.
	 * This prevents a contiguous channel from having multiple different types of material in it, causing clogs.
	 * If you connect two channels that have different materials already in them, god help you (nah jokes it'll just be clogged until you fix manually)
	 */
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(!hasCheckedNeighbors || (neighborType != null && neighborType != stack.material)) return false;
		return super.canAcceptPartialPour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	/** Upon pouring, propagate the current material type along contiguous channels */
	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		propagateMaterial(stack.material);
		return super.pour(world, x, y, z, dX, dY, dZ, side, stack);
	}

	public void propagateMaterial(NTMMaterial propType) {
		if(propType != null && neighborType != null) return; // optimise away any pours that change nothing

		List<TileEntityFoundryChannel> visited = new ArrayList<TileEntityFoundryChannel>();
		visited.add(this);

		boolean hasMaterial = propagateMaterial(propType, visited, false);

		// since we are now fully clear, it's safe to unassign the contiguous channel type
		if(propType == null && !hasMaterial) {
			for(TileEntityFoundryChannel acc : visited) {
				acc.neighborType = null;
			}
		}
	}

	protected boolean propagateMaterial(NTMMaterial propType, List<TileEntityFoundryChannel> visited, boolean hasMaterial) {
		// if emptying, don't mark the channel as ready for a new material until it is entirely clear
		if(propType != null) {
			neighborType = propType;
		} else {
			// and when empty testing, update the last unpropagate time
			unpropagateTime = 0;
		}

		for(ForgeDirection dir : new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
			TileEntity b = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					
			if(b instanceof TileEntityFoundryChannel && !visited.contains(b)) {
				TileEntityFoundryChannel acc = (TileEntityFoundryChannel) b;
				visited.add(acc);

				if(acc.amount > 0) hasMaterial = true;

				hasMaterial = acc.propagateMaterial(propType, visited, hasMaterial);
			}
		}

		return hasMaterial;
	}

	protected NTMMaterial checkNeighbors(List<TileEntityFoundryChannel> visited) {
		if(neighborType != null) return neighborType;

		for(ForgeDirection dir : new ForgeDirection[] { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
			TileEntity b = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
			
			if(b instanceof TileEntityFoundryChannel && !visited.contains(b)) {
				TileEntityFoundryChannel acc = (TileEntityFoundryChannel) b;
				visited.add(acc);

				NTMMaterial neighborMaterial = acc.checkNeighbors(visited);

				// immediately propagate backwards if a material is found
				if(neighborMaterial != null) return neighborMaterial;
			}
		}

		return null;
	}

}
