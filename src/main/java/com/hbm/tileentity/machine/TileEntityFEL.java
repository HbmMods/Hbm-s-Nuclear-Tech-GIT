package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFEL extends TileEntityMachineBase {
	
	public long power;
	public static final long maxPower = 1000000;
	public int watts;
	public int mode = 0;
	public boolean isOn;
	
	public TileEntityFEL() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.machineFEL";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			int range = 50;
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			int length = 5;
			
			for(int i = 5; i < range; i++) {
				
				length = i;

				int x = xCoord + dir.offsetX * i;
				int y = yCoord + 1;
				int z = zCoord + dir.offsetZ * i;
				
				Block b = worldObj.getBlock(x, y, z);
				
				if(b.getMaterial().isOpaque())
					continue;
				
				if(b == ModBlocks.machine_silex) {
					
					TileEntity te = worldObj.getTileEntity(x + dir.offsetX, yCoord, z + dir.offsetZ);
					
					if(te instanceof TileEntitySILEX) {
						TileEntitySILEX silex = (TileEntitySILEX) te;
						silex.laser += this.watts;
					}
				}
				
				break;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setByte("mode", (byte)mode);
			data.setByte("watts", (byte)watts);
			data.setBoolean("isOn", isOn);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.mode = nbt.getByte("mode");
		this.watts = nbt.getByte("watts");
		this.isOn = nbt.getBoolean("isOn");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0) {
			this.mode = Math.abs(value) % 6;
		}
		
		if(meta == 1){
			this.watts = MathHelper.clamp_int(value, 1, 100);
		}
		
		if(meta == 2){
			this.isOn = !this.isOn;
		}
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		watts = nbt.getInteger("watts");
		mode = nbt.getInteger("mode");
		isOn = nbt.getBoolean("isOn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("watts", watts);
		nbt.setInteger("mode", mode);
		nbt.setBoolean("isOn", isOn);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				xCoord - 4,
				yCoord,
				zCoord - 4,
				xCoord + 5,
				yCoord + 3,
				zCoord + 5
			);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
