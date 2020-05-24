package com.hbm.tileentity.machine;

import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreStabilizer extends TileEntityMachineBase implements IConsumer {

	public long power;
	public static final long maxPower = 2500000000L;
	public int watts;
	public int beam;
	
	public static final int range = 15;

	public TileEntityCoreStabilizer() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.dfcStabilizer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			watts = MathHelper.clamp_int(watts, 1, 100);
			int demand = (int) Math.pow(watts, 4);

			beam = 0;
			
			if(power >= demand && slots[0] != null && slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(slots[0]) < ItemLens.maxDamage) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				for(int i = 1; i <= range; i++) {
	
					int x = xCoord + dir.offsetX * i;
					int y = yCoord + dir.offsetY * i;
					int z = zCoord + dir.offsetZ * i;
					
					TileEntity te = worldObj.getTileEntity(x, y, z);
	
					if(te instanceof TileEntityCore) {
						
						TileEntityCore core = (TileEntityCore)te;
						core.field = watts;
						this.power -= demand;
						beam = i;
						
						long dmg = ItemLens.getLensDamage(slots[0]);
						dmg += watts;
						
						if(dmg >= ItemLens.maxDamage)
							slots[0] = null;
						else
							ItemLens.setLensDamage(slots[0], dmg);
						
						break;
					}
					
					if(worldObj.getBlock(x, y, z) != Blocks.air)
						break;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("watts", watts);
			data.setInteger("beam", beam);
			this.networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		power = data.getLong("power");
		watts = data.getInteger("watts");
		beam = data.getInteger("beam");
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		watts = nbt.getInteger("watts");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("watts", watts);
	}

}
