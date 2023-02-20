package com.hbm.blocks.bomb;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.IBomb;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BombFlameWar extends Block implements IBomb {

	public BombFlameWar(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			ExplosionChaos.explode(world, x, y, z, 15);
			ExplosionChaos.spawnExplosion(world, x, y, z, 75);
			ExplosionChaos.flameDeath(world, x, y, z, 100);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			ExplosionChaos.explode(world, x, y, z, 15);
			ExplosionChaos.spawnExplosion(world, x, y, z, 75);
			ExplosionChaos.flameDeath(world, x, y, z, 100);
		}

		return BombReturnCode.DETONATED;
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
	if(!world.isRemote) {
			if(GeneralConfig.enableExtendedLogging) {
			MainRegistry.logger.log(Level.INFO, "[BOMBPL]" + this.getLocalizedName() + " placed at " + x + " / " + y + " / " + z + "! " + "by "+ player.getCommandSenderName());
		}	
	}
	}
}
