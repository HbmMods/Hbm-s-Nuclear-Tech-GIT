package com.hbm.items.weapon;

import java.util.HashSet;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.lib.Library;
import com.hbm.util.BobMathUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunTWR extends ItemGunBase
{
	public ItemGunTWR(GunConfiguration config)
	{
		super(config);
	}
	
	@Beta
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config)
	{
		final EasyLocation playerLoc = new EasyLocation(Library.getPosition(1, player)).modifyCoord(0, player.getEyeHeight(), 0);
		final EasyLocation checkCoord = playerLoc.clone().setWorld(world);
		final HashSet<Entity> entities = new HashSet<>();
		BobMathUtil.offsetForHand(player, checkCoord);
		final Vec3 step = BobMathUtil.entityUnitVector(player);
		do
		{
			checkCoord.addCoord(step);
			entities.addAll(world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(checkCoord.posX - 1, checkCoord.posY - 1, checkCoord.posZ - 1, checkCoord.posX + 1, checkCoord.posY + 1, checkCoord.posZ + 1)));
		} while (!checkCoord.getBlock().isBlockNormalCube() && ILocationProvider.distance(checkCoord, playerLoc) < 500);
		for (Entity entity : entities)
		{
			if (entity instanceof EntityLivingBase)
			{
				final EntityLivingBase livingEntity = (EntityLivingBase) entity;
				final DamageSource dmgSrc = new EntityDamageSourceIndirect("twr" + world.rand.nextInt(2) + 2, player, livingEntity).setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
				if (!livingEntity.attackEntityFrom(dmgSrc, 750))
					livingEntity.setHealth(livingEntity.getHealth() - 750);
				livingEntity.setLastAttacker(player);
				livingEntity.onDeath(dmgSrc);
			}
			else
				entity.setDead();
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.epic;
	}
}
