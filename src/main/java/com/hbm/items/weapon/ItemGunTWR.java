package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.Untested;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunTWR extends ItemGunBase
{
	public ItemGunTWR(GunConfiguration config)
	{
		super(config);
	}
	
	// TODO I don't know what I'm doing
	@Untested
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config)
	{
		//super.spawnProjectile(world, player, stack, config);
		MovingObjectPosition mop = Library.rayTrace(player, 10000, 1, false, true, false);
		if (mop != null)
		{
			System.out.println(mop.typeOfHit);
			System.out.println(mop.entityHit == null ? "No entity hit" : mop.entityHit);
			if (mop.typeOfHit == MovingObjectType.ENTITY)
				EntityDamageUtil.attackEntityFromIgnoreIFrame(mop.entityHit, ModDamageSource.causeTWRDamage(player, mop.entityHit), 1000);
			else if (mop.typeOfHit == MovingObjectType.BLOCK)
				world.newExplosion(null, mop.blockX, mop.blockY, mop.blockZ, 10.0F, false, false);
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.epic;
	}
}
