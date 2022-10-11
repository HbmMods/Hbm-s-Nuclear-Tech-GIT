package com.hbm.entity.grenade;

import java.util.Map;

import com.hbm.calc.EasyLocation;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.weapon.ItemGrenadeEnum;
import com.hbm.items.weapon.ItemGrenadeEnum.GrenadeLogic;
import com.hbm.util.EnumUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGrenadeBouncyBaseNT extends EntityGrenadeBouncyBase
{
	private static final Map<AmmoHandGrenade, GrenadeLogic> LOGIC_MAP = ItemGrenadeEnum.getLogicMap();
	protected AmmoHandGrenade grenade;
	protected int maxFuse;
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
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("maxFuse", maxFuse);
		nbt.setInteger("grenade", grenade.ordinal());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		maxFuse = nbt.getInteger("maxFuse");
		grenade = EnumUtil.grabEnumSafely(AmmoHandGrenade.class, nbt.getInteger("grenade"));
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
			LOGIC_MAP.getOrDefault(grenade, LOGIC_MAP.get(AmmoHandGrenade.GENERIC)).explode(getThrower(), EasyLocation.constructGeneric(this), this);
			setDead();
		}
	}
}
