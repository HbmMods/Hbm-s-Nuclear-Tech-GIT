package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityFBIDrone;
import com.hbm.inventory.container.ContainerRadiobox;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadiobox extends TileEntityLoadedBase implements IEnergyReceiverMK2, IGUIProvider {
	
	long power;
	public static long maxPower = 500000;
	public boolean infinite = false;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote)
			this.updateConnections();

		if(!worldObj.isRemote && this.getBlockMetadata() > 5 && (power >= 25000 || infinite)) {
			
			if(!infinite) {
				power -= 25000;
				this.markDirty();
			}
			
			int range = 15;
			
			List<IMob> entities = worldObj.getEntitiesWithinAABB(IMob.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
			
			for(IMob entity : entities) {
				
				if(entity instanceof EntityFBI || entity instanceof EntityFBIDrone) continue;
				
				((Entity)entity).attackEntityFrom(ModDamageSource.enervation, 20.0F);
			}
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		infinite = nbt.getBoolean("infinite");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setBoolean("infinite", infinite);
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRadiobox(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
