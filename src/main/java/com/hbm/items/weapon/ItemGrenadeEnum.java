package com.hbm.items.weapon;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeBouncyBaseNT;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.explosion.*;
import com.hbm.interfaces.IHasLore;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemGrenadeEnum extends ItemEnumMulti
{
	@FunctionalInterface
	public interface GrenadeLogic
	{
		public void explode(EntityLivingBase thrower, ILocationProvider loc, EntityGrenadeBouncyBaseNT grenade);
	}

	private static final Map<AmmoHandGrenade, GrenadeLogic> LOGIC_MAP;
	static
	{
		final Map<AmmoHandGrenade, GrenadeLogic> tmpMap = new EnumMap<>(AmmoHandGrenade.class);
		tmpMap.put(AmmoHandGrenade.GENERIC, (thrower, loc, nade) -> loc.getWorld().createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 2.0F, true));
		tmpMap.put(AmmoHandGrenade.DYNAMITE, (thrower, loc, nade) -> loc.getWorld().newExplosion(nade, loc.posX(), loc.posY() + 0.25D, loc.posZ(), 3F, false, false));
		tmpMap.put(AmmoHandGrenade.SMOKE, (thrower, loc, nade) ->
		{
			final int radius = 20;
			final List<Entity> hit = loc.getWorld().getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(loc.posX() - radius, loc.posY() - radius, loc.posZ() - radius, loc.posX() + radius, loc.posY() + radius, loc.posZ() + radius));
			
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
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, loc.posX(), loc.posY(), loc.posZ()), Library.easyTargetPoint(loc, 50));
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(haze, loc.posX(), loc.posY(), loc.posZ()), Library.easyTargetPoint(loc, 150));
			}
		});
		tmpMap.put(AmmoHandGrenade.STRONG, (thrower, loc, nade) -> ExplosionLarge.explode(loc.getWorld(), loc.posX(), loc.posY(), loc.posZ(), 5.0F, true, false, false));
		tmpMap.put(AmmoHandGrenade.FRAG, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			if(nade.isBurning())
        	{
        		ExplosionChaos.frag(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 100, true, thrower);
                ExplosionChaos.burn(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 5);
                ExplosionChaos.flameDeath(loc.getWorld(), (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 15);
        		world.playSoundEffect((int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        	} else {
        		ExplosionChaos.frag(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 100, false, thrower);
        		world.playSoundEffect((int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
        	}
		});
		tmpMap.put(AmmoHandGrenade.FIRE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            ExplosionChaos.frag(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 100, true, thrower);
            ExplosionChaos.burn(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 5);
            ExplosionChaos.flameDeath(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 15);
            world.playSoundEffect((int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		});
		tmpMap.put(AmmoHandGrenade.SHRAPNEL, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 2.0F, true);
			for (byte i = 0; i < 5; i++) 
				ExplosionLarge.spawnShrapnels(world, loc.posX(), loc.posY(), loc.posZ(), 5);
		});
		tmpMap.put(AmmoHandGrenade.CLUSTER, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            ExplosionChaos.cluster(world, (int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), 10, 50);
            world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 1.5F, true);
		});
		tmpMap.put(AmmoHandGrenade.FLARE, (thrower, loc, nade) ->
		{
			if (nade.ticksExisted > 250)
				nade.setDead();
		});
		tmpMap.put(AmmoHandGrenade.ELECTRIC, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
            world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 2.0F, true);
            world.spawnEntityInWorld(new EntityLightningBolt(world, loc.posX(), loc.posY(), loc.posZ()));
		});
		tmpMap.put(AmmoHandGrenade.POISON, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 2.0F, true);
			ExplosionNukeGeneric.wasteNoSchrab(world, (int) loc.posX(), (int) loc.posY(), (int) loc.posZ(), 10);
		});
		tmpMap.put(AmmoHandGrenade.GAS, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 0.0F, true);
			ExplosionChaos.spawnChlorine(world, loc.posX(), loc.posY(), loc.posZ(), 50, 1.25, 0);
		});
		tmpMap.put(AmmoHandGrenade.PULSE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionChaos.pulse(world, (int) loc.posX(), (int) loc.posY(), (int) loc.posZ(), 7);
    		world.playSoundEffect((int)loc.posX(), (int)loc.posY(), (int)loc.posZ(), "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
    		ExplosionLarge.spawnShock(world, loc.posX(), loc.posY(), loc.posZ(), 24, 2);
		});
		tmpMap.put(AmmoHandGrenade.PLASMA, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 2.0F, true);
			ExplosionChaos.plasma(world, (int) loc.posX(), (int) loc.posY(), (int) loc.posZ(), 7);
		});
		tmpMap.put(AmmoHandGrenade.KYIV, (thrower, loc, nade) -> loc.getWorld().newExplosion(null, loc.posX(), loc.posY(), loc.posZ(), 5F, true, true));
		tmpMap.put(AmmoHandGrenade.ASCHRAB, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.playSoundEffect(loc.posX(), loc.posY(), loc.posZ(), "random.explode", 100.0F, world.rand.nextFloat() * 0.1F + 0.9F);
			world.spawnEntityInWorld(EntityNukeExplosionMK3.statFacFleija(world, loc.posX(), loc.posY(), loc.posZ(), BombConfig.aSchrabRadius));

			final EntityCloudFleija cloud = new EntityCloudFleija(world, BombConfig.aSchrabRadius);
			cloud.posX = loc.posX();
			cloud.posY = loc.posY();
			cloud.posZ = loc.posZ();
			world.spawnEntityInWorld(cloud);
		});
		tmpMap.put(AmmoHandGrenade.NUKE, (thrower, loc, nade) -> loc.getWorld().createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 30F, true));
		tmpMap.put(AmmoHandGrenade.NUCLEAR, (thrower, loc, nade) -> ExplosionNukeSmall.explode(loc.getWorld(), loc.posX(), loc.posY() + 0.5, loc.posZ(), ExplosionNukeSmall.tots));
		tmpMap.put(AmmoHandGrenade.ZOMG, (thrower, loc, nade) -> ExplosionChaos.zomgMeSinPi(loc.getWorld(), loc.posX(), loc.posY(), loc.posZ(), 20, thrower, nade));
		tmpMap.put(AmmoHandGrenade.BLACK_HOLE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			
			world.createExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 1.5F, true);

        	final EntityBlackHole bl = new EntityBlackHole(world, 1.5F);
        	bl.posX = loc.posX();
        	bl.posY = loc.posY();
        	bl.posZ = loc.posZ();
        	world.spawnEntityInWorld(bl);
		});
		tmpMap.put(AmmoHandGrenade.CLOUD, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.playAuxSFX(2002, (int) Math.round(loc.posX()), (int) Math.round(loc.posY()), (int) Math.round(loc.posZ()), 0);
			ExplosionChaos.spawnChlorine(world, loc.posX(), loc.posY(), loc.posZ(), 250, 1.5, 1);
		});
		tmpMap.put(AmmoHandGrenade.PINK_CLOUD, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.playAuxSFX(2002, (int) Math.round(loc.posX()), (int) Math.round(loc.posY()), (int) Math.round(loc.posZ()), 0);
			ExplosionChaos.spawnChlorine(world, loc.posX(), loc.posY(), loc.posZ(), 500, 2, 2);
		});
		// TODO
//		tmpMap.put(AmmoHandGrenade.LUNATIC, null);
		tmpMap.put(AmmoHandGrenade.SMART, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			if (nade.ticksExisted > 10)
            	ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5.0F, true, false, false);
            else
            	world.spawnEntityInWorld(new EntityItem(world, loc.posX(), loc.posY(), loc.posZ(), ModItems.grenade.stackFromEnum(AmmoHandGrenade.SMART)));
		});
		tmpMap.put(AmmoHandGrenade.MIRV, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			for (int i = 0; i < 8; i++)
			{
    			final EntityGrenadeSmart grenade = new EntityGrenadeSmart(world);
    			grenade.posX = loc.posX();
    			grenade.posY = loc.posY();
    			grenade.posZ = loc.posZ();
    			grenade.motionX = nade.motionX + world.rand.nextGaussian() * 0.25D;
    			grenade.motionY = nade.motionY + world.rand.nextGaussian() * 0.25D;
    			grenade.motionZ = nade.motionZ + world.rand.nextGaussian() * 0.25D;
    			grenade.ticksExisted = 10;
    			
    			world.spawnEntityInWorld(grenade);
    		}
		});
		tmpMap.put(AmmoHandGrenade.BREACH, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			if(world.rand.nextInt(10) == 0)
        		nade.setDead();
			ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 2.5F, false, false, false);
		});
		tmpMap.put(AmmoHandGrenade.WASTE_PEARL, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final int[] rounded = loc.getCoordInt();
			for (int ix = rounded[0] - 3; ix <= rounded[0] + 3; ix++)
			{
				for (int iy = rounded[1] - 3; iy <= rounded[1] + 3; iy++)
				{
					for (int iz = rounded[2] - 3; iz <= rounded[2] + 3; iz++)
					{
						if (world.rand.nextInt(3) == 0 && world.getBlock(ix, iy, iz).isReplaceable(world, ix, iy, iz) && ModBlocks.fallout.canPlaceBlockAt(world, ix, iy, iz))
							world.setBlock(ix, iy, iz, ModBlocks.fallout);
						else if (world.getBlock(ix, iy, iz) == Blocks.air)
							world.setBlock(ix, iy, iz, world.rand.nextBoolean() ? ModBlocks.gas_radon : ModBlocks.gas_radon_dense);
					}
				}
			}
		});
//		TODO
//		tmpMap.put(AmmoHandGrenade.STUNNING, null);
		tmpMap.put(AmmoHandGrenade.IF_GENERIC, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 5, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5, true, true, true);
		});
		tmpMap.put(AmmoHandGrenade.IF_HE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 7.5, 300, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 7, true, true, true);
		});
		tmpMap.put(AmmoHandGrenade.IF_BOUNCY, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 5, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5, true, true, true);
		});
		tmpMap.put(AmmoHandGrenade.IF_STICKY, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 5, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5, true, true, true);
		});
		tmpMap.put(AmmoHandGrenade.IF_IMPACT, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 5, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5, true, true, true);
		});
		tmpMap.put(AmmoHandGrenade.IF_INCENDIARY, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final int[] rounded = loc.getCoordInt();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 5, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 5, true, true, true);
    		ExplosionThermo.setEntitiesOnFire(world, rounded[0], rounded[1], rounded[2], 8);
    		ExplosionChaos.flameDeath(world, rounded[0], rounded[1], rounded[2], 15);
    		ExplosionChaos.burn(world, rounded[0], rounded[1], rounded[2], 10);
		});
		tmpMap.put(AmmoHandGrenade.IF_TOXIC, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final int[] rounded = loc.getCoordInt();
			ExplosionLarge.jolt(world, loc.posX(), loc.posY(), loc.posZ(), 3, 200, 0.25);
    		ExplosionLarge.explode(world, loc.posX(), loc.posY(), loc.posZ(), 2, true, true, true);
    		ExplosionChaos.poison(world, rounded[0], rounded[1], rounded[2], 12);
    		ExplosionNukeGeneric.waste(world, rounded[0], rounded[1], rounded[2], 12);
    		ExplosionChaos.spawnChlorine(world, loc.posX(), loc.posY(), loc.posZ(), 50, 1.5, 0);
		});
		tmpMap.put(AmmoHandGrenade.IF_CONCUSSION, (thrower, loc, nade) -> loc.getWorld().newExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 15, false, false));
		tmpMap.put(AmmoHandGrenade.IF_BRIMSTONE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			
			final EntityBullet fragment = new EntityBullet(world, thrower, 3.0F, 35, 45, false, "tauDay");
    		fragment.setDamage(world.rand.nextInt(301) + 100);

    		fragment.motionX = world.rand.nextGaussian();
    		fragment.motionY = world.rand.nextGaussian();
    		fragment.motionZ = world.rand.nextGaussian();
    		fragment.shootingEntity = thrower;

    		fragment.posX = loc.posX();
    		fragment.posY = loc.posY();
    		fragment.posZ = loc.posZ();

    		fragment.setIsCritical(true);

    		world.spawnEntityInWorld(fragment);
		});
		tmpMap.put(AmmoHandGrenade.IF_MYSTERY, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			world.newExplosion(nade, loc.posX(), loc.posY(), loc.posZ(), 10, false, false);
    		ExplosionChaos.spawnVolley(world, loc.posX(), loc.posY(), loc.posZ(), 100, 1.0D);
		});
		tmpMap.put(AmmoHandGrenade.IF_SPARK, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final EntityRagingVortex vortex = new EntityRagingVortex(world, 1.5F);
    		vortex.posX = loc.posX();
    		vortex.posY = loc.posY();
    		vortex.posZ = loc.posZ();
    		world.spawnEntityInWorld(vortex);
		});
		tmpMap.put(AmmoHandGrenade.IF_HOPWIRE, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final EntityVortex vortex = new EntityVortex(world, 0.75F);
    		vortex.posX = loc.posX();
    		vortex.posY = loc.posY();
    		vortex.posZ = loc.posZ();
    		world.spawnEntityInWorld(vortex);
		});
		tmpMap.put(AmmoHandGrenade.IF_NULL, (thrower, loc, nade) ->
		{
			final World world = loc.getWorld();
			final int[] rounded = loc.getCoordInt();
			for(int a = -3; a <= 3; a++)
        		for(int b = -3; b <= 3; b++)
            		for(int c = -3; c <= 3; c++)
            			world.setBlockToAir(rounded[0] + a, rounded[1] + b, rounded[2] + c);
    		
    		final List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(nade, AxisAlignedBB.getBoundingBox(rounded[0] + 0.5 - 3, rounded[1] + 0.5 - 3, rounded[2] + 0.5 - 3, rounded[0] + 0.5 + 3, rounded[1] + 0.5 + 3, rounded[2] + 0.5 + 3));
    		
    		for (Entity entity : list)
    		{
    			if(entity instanceof EntityLivingBase)
    				((EntityLivingBase) entity).setHealth(0);
    			else
    				entity.setDead();
    		}
		});
		
		LOGIC_MAP = ImmutableMap.copyOf(tmpMap);
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
		if (!LOGIC_MAP.containsKey(nade))
			MainRegistry.logger.warn("Grenade enum type: " + nade + " is not registered! Thrown grenade will default to generic preset!");
		
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		if(multiTexture) {
			Enum<?>[] enums = theEnum.getEnumConstants();
			this.icons = new IIcon[enums.length];
			
			for(int i = 0; i < icons.length; i++) {
				Enum<?> num = enums[i];
				this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase());
			}
		} else {
			this.itemIcon = reg.registerIcon(this.getIconString());
		}
	}
	
	public static Map<AmmoHandGrenade, GrenadeLogic> getLogicMap()
	{
		return LOGIC_MAP;
	}

}
