package com.hbm.explosion.vanillant.interfaces;

import java.util.HashMap;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IEntityProcessor {

	public HashMap<EntityPlayer, Vec3> process(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}
