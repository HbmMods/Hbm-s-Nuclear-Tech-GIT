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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryTank extends TileEntityFoundryBase {
	
	public int nextUpdate;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.type == null && this.amount != 0) {
				this.amount = 0;
			}
			
			nextUpdate--;
			
			if(nextUpdate <= 0 && this.amount > 0 && this.type != null) {
				
				boolean hasOp = false;
				nextUpdate = worldObj.rand.nextInt(6) + 5;
				
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				
				if(te instanceof TileEntityFoundryTank) {
					TileEntityFoundryTank tank = (TileEntityFoundryTank) te;
					
					if((tank.type == null || tank.type == this.type) && tank.amount < tank.getCapacity()) {
						tank.type = this.type;
						int toFill = Math.min(this.amount, tank.getCapacity() - tank.amount);
						this.amount -= toFill;
						tank.amount += toFill;
						hasOp = true;
					}
				}
				
				List<Integer> ints = new ArrayList() {{ add(2); add(3); add(4); add(5); }};
				Collections.shuffle(ints);
				
				if(!hasOp) {
					
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
								hasOp = true;
								break;
							}
						}
					}
				}
				
				if(!hasOp) {
					for(Integer i : ints) {
						ForgeDirection dir = ForgeDirection.getOrientation(i);
						TileEntity b = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ);
						
						if(b instanceof TileEntityFoundryTank) {
							TileEntityFoundryTank acc = (TileEntityFoundryTank) b;
							
							if(acc.type == null || acc.type == this.type || acc.amount == 0) {
								acc.type = this.type;
								if(worldObj.rand.nextInt(5) == 0) {
									//1:4 chance that the fill states are simply swapped
									//this promotes faster spreading and prevents spread limits
									int buf = this.amount;
									this.amount = acc.amount;
									acc.amount = buf;
									
								} else {
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
		}
		
		super.updateEntity();
	}

	@Override
	public int getCapacity() {
		return MaterialShapes.BLOCK.q(4);
	}
}
