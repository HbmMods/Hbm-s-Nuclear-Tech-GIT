package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ArmorUtil;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAABattery extends TileEntityMachineBase implements IEnergyUser {
	
	public long power;
	public static final long maxPower = 100000;
	
	public static int range = 50;
	public static double offset = 1.75;
	
	public List<double[]> targets = new ArrayList();

	public TileEntityAABattery() {
		super(0);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			this.targets.clear();

			
			if(power >= 8000) {
				power -= 8000;

				double dx = xCoord + 0.5;
				double dy = yCoord + offset;
				double dz = zCoord + 0.5;
				
				this.targets = zap(worldObj, dx, dy, dz, range, null);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("length", (short)targets.size());
			int i = 0;
			for(double[] d : this.targets) {
				data.setDouble("x" + i, d[0]);
				data.setDouble("y" + i, d[1]);
				data.setDouble("z" + i, d[2]);
				i++;
			}
			data.setLong("power", power);
			
			this.networkPack(data, 100);
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}
	
	public static List<double[]> zap(World worldObj, double x, double y, double z, double radius, Entity source) {

		List<double[]> ret = new ArrayList();
		
		List<Entity> targets = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for(Entity e : targets) {
			
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - x, e.posY + e.height / 2 - y, e.posZ - z);
			
			if(vec.lengthVector() > range)
				continue;

			if(Library.isObstructed(worldObj, x, y, z, e.posX, e.posY + e.height / 2, e.posZ))
				continue;
			
			if(e instanceof EntityArtilleryShell) {
				ret.add(new double[] {e.posX, e.posY + 1.25, e.posZ});
				e.setDead();
				worldObj.playSoundEffect(x, y, z, "hbm:turret.battery_fire", 6.0F, 1.0F);
				continue;
			}

		
		}
		
		return ret;
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		int s = data.getShort("length");
		this.power = data.getLong("power");
		this.targets.clear();
		
		for(int i = 0; i < s; i++)
			this.targets.add(new double[] {
					data.getDouble("x" + i),
					data.getDouble("y" + i),
					data.getDouble("z" + i)
			});
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
