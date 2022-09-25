package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats.MaterialStack;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryChannel extends TileEntityFoundryBase {
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.type == null && this.amount != 0) {
				this.amount = 0;
			}
			
			if(worldObj.rand.nextInt(10) == 0 && this.amount > 0 && this.type != null) {
				
				List<Integer> ints = new ArrayList() {{ add(2); add(3); add(4); add(5); }};
				Collections.shuffle(ints);
				
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
							} else {
								this.amount = left.amount;
							}
							return;
						}
					}
				}
				
				for(Integer i : ints) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					TileEntity b = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
					
					if(b instanceof TileEntityFoundryChannel) {
						TileEntityFoundryChannel acc = (TileEntityFoundryChannel) b;
						
						if(acc.type == null || acc.type == this.type) {
							acc.type = this.type;
							
							if(worldObj.rand.nextInt(5) == 0) {
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
		
		super.updateEntity();
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(side != ForgeDirection.UP) return false; //reject from any direction other than the top
		if(this.type != null && this.type != stack.material) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		
		return true; //pour
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(this.type == null) {
			this.type = stack.material;
		}
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN) return false;
		if(this.type != null && this.type != stack.material) return false;
		if(this.amount >= this.getCapacity()) return false;
		
		return true; //pour
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		if(this.type == null) {
			this.type = stack.material;
		}
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	@Override
	public int getCapacity() {
		return MaterialShapes.INGOT.q(1);
	}
}
