package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.main.MainRegistry;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.ChatBuilder;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GunDetonatorFactory {

	public static GunConfiguration getDetonatorConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.DUAL;
		config.durability = 1_000_000_000;
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.firingSound = "hbm:weapon.dartShoot";
		config.reloadSoundEnd = false;
		config.showAmmo = true;
		
		config.name = "laserDet";
		config.manufacturer = EnumGunManufacturer.WESTTEK;
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.DET_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_DU_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_STAR_BOLT);
		config.config.add(BulletConfigSyncingUtil.CHL_R5_BOLT);
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_SLEEK);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		config.config.add(BulletConfigSyncingUtil.NUKE_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_TOTS);
		config.config.add(BulletConfigSyncingUtil.NUKE_PUMPKIN);
		config.config.add(BulletConfigSyncingUtil.NUKE_BARREL);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_HIGH);
		
		return config;
	}
	
	public static BulletConfiguration getLaserConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(Items.redstone);
		bullet.spread = 0.0F;
		bullet.maxAge = 100;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.leadChance = 0;
		bullet.doesRicochet = false;
		bullet.setToBolt(BulletConfiguration.BOLT_LASER);
		
		bullet.bntImpact = (bulletnt, x, y, z) -> {
				
			World world = bulletnt.worldObj;
			if(!world.isRemote && y > 0) {
				Block b = world.getBlock(x, y, z);
				if(b instanceof IBomb) {
					BombReturnCode ret = ((IBomb) b).explode(world, x, y, z);

					if(ret.wasSuccessful() && bulletnt.getThrower() instanceof EntityPlayerMP) {
						EntityPlayerMP player = (EntityPlayerMP) bulletnt.getThrower();
						world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
						PacketDispatcher.wrapper.sendTo(
								new PlayerInformPacket(ChatBuilder.start("").nextTranslation(ret.getUnlocalizedMessage()).color(EnumChatFormatting.YELLOW).flush(), MainRegistry.proxy.ID_DETONATOR),
								(EntityPlayerMP) player);
					}
				}
			}
		};
		
		return bullet;
	}
}
