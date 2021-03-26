package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHazard extends Block implements IItemHazard {
	
	ItemHazardModule module;
	
	private float radIn = 0.0F;
	private float radMax = 0.0F;
	private ExtDisplayEffect extEffect = null;

	public BlockHazard() {
		this(Material.iron);
	}
	
	public BlockHazard setDisplayEffect(ExtDisplayEffect extEffect) {
		this.extEffect = extEffect;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		if(extEffect == null)
			return;
		
		switch(extEffect) {
		case RADFOG:
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

				if(dir == ForgeDirection.DOWN)
					continue;

				if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air) {

					double ix = x + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
					double iy = y + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
					double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

					if(dir.offsetX != 0)
						ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.25 * dir.offsetX;
					if(dir.offsetY != 0)
						iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * 0.25 * dir.offsetY;
					if(dir.offsetZ != 0)
						iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.25 * dir.offsetZ;

					world.spawnParticle("townura", ix, iy, iz, 0.0, 0.0, 0.0);
				}
			}
			break;
			
		case SCHRAB:
			break;
			
		case SPARKS:
			break;
			
		case FLAMES:
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

				if(dir == ForgeDirection.DOWN)
					continue;

				if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air) {

					double ix = x + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
					double iy = y + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
					double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

					if(dir.offsetX != 0)
						ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.25 * dir.offsetX;
					if(dir.offsetY != 0)
						iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * 0.25 * dir.offsetY;
					if(dir.offsetZ != 0)
						iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.25 * dir.offsetZ;

					world.spawnParticle("flame", ix, iy, iz, 0.0, 0.0, 0.0);
					world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.0, 0.0);
					world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.1, 0.0);
				}
			}
			break;
			
		case LAVAPOP:
			world.spawnParticle("lava", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			break;
			
		default: break;
		}
	}

	public BlockHazard(Material mat) {
		super(mat);
		this.module = new ItemHazardModule();
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public IItemHazard addRadiation(float radiation) {
		this.getModule().addRadiation(radiation);
		this.radIn = radiation;
		this.radMax = radiation * 10;
		return this;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(this.radIn > 0) {
			RadiationSavedData.incrementRad(world, x, z, radIn, radMax);
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
	}

	@Override
	public int tickRate(World world) {

		if(this.radIn > 0)
			return 20;

		return super.tickRate(world);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if(this.radIn > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
	
	public static enum ExtDisplayEffect {
		RADFOG,
		SPARKS,
		SCHRAB,
		FLAMES,
		LAVAPOP
	}
}
