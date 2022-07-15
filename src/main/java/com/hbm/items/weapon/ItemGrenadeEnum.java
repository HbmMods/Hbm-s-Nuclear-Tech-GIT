package com.hbm.items.weapon;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.hbm.entity.grenade.EntityGrenadeBouncyBaseNT;
import com.hbm.explosion.*;
import com.hbm.interfaces.IHasLore;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.ItemEnumMulti;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemGrenadeEnum extends ItemEnumMulti
{
	@FunctionalInterface
	public interface GrenadeLogic
	{
		public void explode(EntityLivingBase thrower, ILocationProvider loc, EntityGrenadeBouncyBaseNT grenade);
	}

	private static final EnumMap<AmmoHandGrenade, GrenadeLogic> logicMap = new EnumMap<>(AmmoHandGrenade.class);
	static
	{
		logicMap.put(AmmoHandGrenade.GENERIC, (thrower, loc, nade) -> loc.getWorld().createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 2.0F, true));
		logicMap.put(AmmoHandGrenade.DYNAMITE, (thrower, loc, nade) -> loc.getWorld().newExplosion(nade, loc.getX(), loc.getY() + 0.25D, loc.getZ(), 3F, false, false));
		logicMap.put(AmmoHandGrenade.SMOKE, (thrower, loc, nade) ->
		{
			final int radius = 20;
			final List<Entity> hit = loc.getWorld().getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(loc.getX() - radius, loc.getY() - radius, loc.getZ() - radius, loc.getX() + radius, loc.getY() + radius, loc.getZ() + radius));
			
			for (Entity e : hit) {
				
				if(!Library.isObstructed(loc.getWorld(), loc, ILocationProvider.wrap(e, true))) {
					e.setFire(5);
					
					if (e instanceof EntityLivingBase) {
						
						final PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 120 * 20, 0, true);
						eff.getCurativeItems().clear();
						((EntityLivingBase)e).addPotionEffect(eff);
					}
				}
			}
			
			final NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 200);
			data.setDouble("motion", 0.5);
			
			
			final NBTTagCompound haze = new NBTTagCompound();
			haze.setString("type", "haze");
			for (byte i = 0; i < 5; i++)
			{
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, loc.getX(), loc.getY(), loc.getZ()), Library.easyTargetPoint(loc, 50));
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(haze, loc.getX(), loc.getY(), loc.getZ()), Library.easyTargetPoint(loc, 150));
			}
		});
		logicMap.put(AmmoHandGrenade.STRONG, (thrower, loc, nade) -> ExplosionLarge.explode(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), 5.0F, true, false, false));
		logicMap.put(AmmoHandGrenade.FRAG, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			if(nade.isBurning())
        	{
        		ExplosionChaos.frag(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 100, true, thrower);
                ExplosionChaos.burn(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 5);
                ExplosionChaos.flameDeath(loc.getWorld(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 15);
        		loc.getWorld().playSoundEffect((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        	} else {
        		ExplosionChaos.frag(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 100, false, thrower);
        		loc.getWorld().playSoundEffect((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        	}
		});
		logicMap.put(AmmoHandGrenade.FIRE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            ExplosionChaos.frag(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 100, true, thrower);
            ExplosionChaos.burn(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 5);
            ExplosionChaos.flameDeath(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 15);
            world.playSoundEffect((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		});
		logicMap.put(AmmoHandGrenade.SHRAPNEL, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 2.0F, true);
			for (byte i = 0; i < 5; i++) 
				ExplosionLarge.spawnShrapnels(world, loc.getX(), loc.getY(), loc.getZ(), 5);
		});
		logicMap.put(AmmoHandGrenade.CLUSTER, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            ExplosionChaos.cluster(world, (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), 10, 50);
            world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 1.5F, true);
		});
		logicMap.put(AmmoHandGrenade.FLARE, (thrower, loc, nade) ->
		{
			if (nade.ticksExisted > 250)
				nade.setDead();
		});
		logicMap.put(AmmoHandGrenade.ELECTRIC, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 2.0F, true);
            loc.getWorld().spawnEntityInWorld(new EntityLightningBolt(world, loc.getX(), loc.getY(), loc.getZ()));
		});
		logicMap.put(AmmoHandGrenade.POISON, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 2.0F, true);
			ExplosionNukeGeneric.wasteNoSchrab(world, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), 10);
		});
		logicMap.put(AmmoHandGrenade.GAS, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 0.0F, true);
			ExplosionChaos.spawnChlorine(world, loc.getX(), loc.getY(), loc.getZ(), 50, 1.25, 0);
		});
		logicMap.put(AmmoHandGrenade.PULSE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionChaos.pulse(world, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), 7);
    		world.playSoundEffect((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    		ExplosionLarge.spawnShock(world, loc.getX(), loc.getY(), loc.getZ(), 24, 2);
		});
		logicMap.put(AmmoHandGrenade.PLASMA, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.getX(), loc.getY(), loc.getZ(), 2.0F, true);
			ExplosionChaos.plasma(world, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), 7);
		});
		
		logicMap.put(AmmoHandGrenade.KYIV, (thrower, loc, nade) -> loc.getWorld().newExplosion(null, loc.getX(), loc.getY(), loc.getZ(), 5F, true, true));
	}
	public ItemGrenadeEnum()
	{
		super(AmmoHandGrenade.class, true, true);
	}

	private static String translateFuse(int fuse)
	{
		switch (fuse)
		{
		case -1: return I18nUtil.resolveKey("desc.item.grenade.fuseImpact");
		case 0: return I18nUtil.resolveKey("desc.item.grenade.fuseInstant");
		default: return fuse + "s";
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean b)
	{
		final AmmoHandGrenade nade = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		tooltip.add(I18nUtil.resolveKey("desc.item.grenade.fuse", translateFuse(nade.fuse)));
		if (IHasLore.getHasLore(stack.getUnlocalizedName()))
			tooltip.add("");
		super.addInformation(stack, player, tooltip, b);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		final AmmoHandGrenade nade = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		
		if (!player.capabilities.isCreativeMode)
			stack.stackSize--;
		
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!world.isRemote)
			world.spawnEntityInWorld(new EntityGrenadeBouncyBaseNT(world, player, nade.fuse * 20, nade));
		if (!logicMap.containsKey(nade))
			MainRegistry.logger.warn("Grenade enum type: " + nade + " is not registered! Thrown grenade will default to generic preset!");
		
		return stack;
	}
	
	public static Map<AmmoHandGrenade, GrenadeLogic> getLogicMap()
	{
		return ImmutableMap.copyOf(logicMap);
	}

}
