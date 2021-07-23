package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.guncfg.GunEnergyFactory;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTurretTsukuyomi extends TileEntityTurretBaseNT
{
	private int ammoCount = 0;
	BulletConfiguration bulletConf;
	static List<Integer> configs = new ArrayList<Integer>();
	static
	{
		configs.add(BulletConfigSyncingUtil.TWR_RAY);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_LARGE);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_SUPERHEATED);
		configs.add(BulletConfigSyncingUtil.TWR_RAW_COUNTER_RESONANT);
	}
	
	@Override
	public double getDecetorRange()
	{
		return 100D;
	}
	@Override
	public double getDecetorGrace()
	{
		return 12D;
	}
	@Override
	public double getTurretDepression()
	{
		return 45D;
	}
	@Override
	public double getBarrelLength()
	{
		return 4.25D;
	}
	@Override
	public double getTurretElevation()
	{
		return 80D;
	}
	@Override
	public long getMaxPower()
	{
		return 100000000;
	}
	@Override
	public long getConsumption()
	{
		return 200000;
	}
	int timer;
	int cooldown;
	@Override
	public void updateFiringTick()
	{
		timer++;
		if (ammoCount > 0 && tPos != null && timer % 2 == 0)
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.zomgShoot", 5.0F, 1.0F * worldObj.rand.nextFloat());
			ammoCount--;
			cooldown = 10;
			EntityDamageUtil.attackEntityFromIgnoreIFrame(target, ModDamageSource.twr, worldObj.rand.nextInt(bulletConf.bulletsMax) + bulletConf.dmgMin);
		}
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote && ammoCount <= 0)
		{
			bulletConf = getFirstConfigLoaded();
			if (cooldown > 0)
				cooldown--;
			
			if (bulletConf != null && cooldown <= 1)
			{
				conusmeAmmo(bulletConf.ammo);
				if (bulletConf.ammoCount > 1)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.b92Reload", 4.0F, 1.0F);
				ammoCount = bulletConf.ammoCount;
			}
		}
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		if (value == 0 && !isOn)
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 6.0F, 2.0F);
		super.handleButtonPacket(value, meta);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		ammoCount = nbt.getInteger("ammoCount");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("ammoCount", ammoCount);
		
	}
	@Override
	protected List<Integer> getAmmoList()
	{
		return configs;
	}

	@Override
	public String getName()
	{
		return "container.turretTWR";
	}

}
