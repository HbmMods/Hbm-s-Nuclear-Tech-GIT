package com.hbm.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleNT extends EntityFX {
	
	public ParticleNT(World world, double x, double y, double z, ParticleDefinition definition) {
		super(world, x, y, z);
	}

	public ParticleNT(World world, double x, double y, double z, double moX, double moY, double moZ, ParticleDefinition definition) {
		this(world, x, y, z, definition);
		this.setVelocity(moX, moY, moZ);
	}

}
