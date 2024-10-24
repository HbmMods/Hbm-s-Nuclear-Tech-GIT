package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityChemical;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemCryoCannon extends ItemGunBase {

	public ItemCryoCannon(GunConfiguration config) {
		super(config);
	}

	@Override
	protected void fire(ItemStack stack, World world, EntityPlayer player) {
		
		if(getPressure(stack) >= 1000) return;
		if(getTurbine(stack) < 100) return;

		BulletConfiguration config = null;
		
		if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
			config = getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}

			if(player instanceof EntityPlayerMP)
				PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
			useUpAmmo(player, stack, true);
			player.inventoryContainer.detectAndSendChanges();
			
			int wear = (int) Math.ceil(config.wear / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)));
			setItemWear(stack, getItemWear(stack) + wear);
		}
		
		world.playSoundAtEntity(player, mainConfig.firingSound, mainConfig.firingVolume, mainConfig.firingPitch);
		
		if(mainConfig.ejector != null && !mainConfig.ejector.getAfterReload())
			queueCasing(player, mainConfig.ejector, config, stack);
	}

	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		EntityChemical chem = new EntityChemical(world, player, 0, 0, 0);
		chem.setFluid(Fluids.OXYGEN);
		world.spawnEntityInWorld(chem);

		int pressure = getPressure(stack);
		pressure += 5;
		pressure = MathHelper.clamp_int(pressure, 0, 1000);
		setPressure(stack, pressure);
	}

	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		int turbine = getTurbine(stack);
		int pressure = getPressure(stack);
		
		if(getIsMouseDown(stack)) {
			turbine += 10;
		} else {
			turbine -= 5;
			pressure -= 5;
		}

		turbine = MathHelper.clamp_int(turbine, 0, 100);
		pressure = MathHelper.clamp_int(pressure, 0, 1000);
		setTurbine(stack, turbine);
		setPressure(stack, pressure);
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
	}
	
	public static void setTurbine(ItemStack stack, int i) {
		writeNBT(stack, "turbine", i);
	}
	
	public static int getTurbine(ItemStack stack) {
		return readNBT(stack, "turbine");
	}
	
	public static void setPressure(ItemStack stack, int i) {
		writeNBT(stack, "pressure", i);
	}
	
	public static int getPressure(ItemStack stack) {
		return readNBT(stack, "pressure");
	}
}
