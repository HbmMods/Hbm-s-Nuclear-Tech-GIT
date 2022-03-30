package com.hbm.explosion.vanillant;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IEntityProcessor {

	public HashMap<EntityPlayer, Vec3> process(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}