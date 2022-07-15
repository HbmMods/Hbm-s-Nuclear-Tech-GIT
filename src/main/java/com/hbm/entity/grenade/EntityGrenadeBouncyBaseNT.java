package com.hbm.entity.grenade;

import java.util.Map;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.weapon.ItemGrenadeEnum;
import com.hbm.items.weapon.ItemGrenadeEnum.GrenadeLogic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

@Beta
public class EntityGrenadeBouncyBaseNT extends EntityGrenadeBouncyBase
{
	private static final Map<AmmoHandGrenade, GrenadeLogic> logicMap;
	static
	{
		logicMap = ItemGrenadeEnum.getLogicMap();
	}
	protected final AmmoHandGrenade grenade;
	protected final int maxFuse;
	public EntityGrenadeBouncyBaseNT(World world)
	{
		super(world);
		this.setSize(0.25F, 0.25F);
		this.grenade = null;
		maxFuse = 0;
	}
	public EntityGrenadeBouncyBaseNT(World world, EntityLivingBase thrower, int fuse, AmmoHandGrenade grenade)
	{
		super(world, thrower);
		this.maxFuse = fuse;
		this.grenade = grenade;
	}
	@Override
	protected double getBounceMod()
	{
		return 0.25d;
	}
	@Override
	protected int getMaxTimer()
	{
		return maxFuse;
	}
	@Override
	public void explode()
	{
		if (!worldObj.isRemote)
		{
			logicMap.getOrDefault(grenade, logicMap.get(AmmoHandGrenade.GENERIC)).explode(getThrower(), EasyLocation.constructGeneric(this), this);
			setDead();
		}
	}
}
