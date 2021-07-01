package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.Spaghetti;
import com.hbm.interfaces.Untested;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunTWR extends ItemGunBase
{
	public ItemGunTWR(GunConfiguration config)
	{
		super(config);
	}
	
	// TODO I don't know what I'm doing
//	@Untested
//	@Spaghetti("wtf")
//	@Override
//	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config)
//	{
//		//super.spawnProjectile(world, player, stack, config);
//		List<EntityLivingBase> targets;
//		MovingObjectPosition ray = Library.rayTrace(player, 500, 1, false, false, false);
//		if (ray == null)
//			ray = Library.rayTrace(player, 500, 1);
//		if (ray != null)
//		{
//			Vec3 origin = Vec3.createVectorHelper(player.posX, player.posY + player.eyeHeight - player.getYOffset(), player.posZ);
//			Vec3 destination = Vec3.createVectorHelper(ray.blockX, ray.blockY, ray.blockZ);
//			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX + 10, player.posY + 10, player.posZ + 10, destination.xCoord + 10, destination.yCoord + 10, destination.zCoord + 10);
//			targets = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
//			for (EntityLivingBase entity : targets)
//				entity.attackEntityFrom(ModDamageSource.causeTWRDamage(entity, player), 50000);
//		}
//	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.rare;
	}
}
