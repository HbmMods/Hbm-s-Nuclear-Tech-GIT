package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.awt.Color;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Balefire extends BlockFire {

	private IIcon icon;

	public Balefire() {
		super();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icon = register.registerIcon(this.getTextureName());
	}

	@SideOnly(Side.CLIENT)
	public IIcon getFireIcon(int i) {
		return icon;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.getGameRules().getGameRuleBooleanValue("doFireTick")) {

			if(!this.canPlaceBlockAt(world, x, y, z)) {
				world.setBlockToAir(x, y, z);
			}

			int meta = world.getBlockMetadata(x, y, z);

			if(meta < 15) world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(10));

			if(!this.canNeighborBurn(world, x, y, z) && !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
				world.setBlockToAir(x, y, z);
			} else {
				if(meta < 15) {
					this.tryCatchFire(world, x + 1, y, z, 500, rand, meta, WEST);
					this.tryCatchFire(world, x - 1, y, z, 500, rand, meta, EAST);
					this.tryCatchFire(world, x, y - 1, z, 300, rand, meta, UP);
					this.tryCatchFire(world, x, y + 1, z, 300, rand, meta, DOWN);
					this.tryCatchFire(world, x, y, z - 1, 500, rand, meta, SOUTH);
					this.tryCatchFire(world, x, y, z + 1, 500, rand, meta, NORTH);
					
					int h = 3;

					for(int ix = x - h; ix <= x + h; ++ix) {
						for(int iz = z - h; iz <= z + h; ++iz) {
							for(int iy = y - 1; iy <= y + 4; ++iy) {
								
								if(ix != x || iy != y || iz != z) {
									int fireLimit = 100;

									if(iy > y + 1) {
										fireLimit += (iy - (y + 1)) * 100;
									}
									
									if(world.getBlock(ix, iy, iz) == ModBlocks.balefire && world.getBlockMetadata(ix, iy, iz) > meta + 1) {
										world.setBlock(ix, iy, iz, this, meta + 1, 3);
										continue;
									}

									int neighborFireChance = this.getChanceOfNeighborsEncouragingFire(world, ix, iy, iz);

									if(neighborFireChance > 0) {
										int adjustedFireChance = (neighborFireChance + 40 + world.difficultySetting.getDifficultyId() * 7) / (meta + 30);

										if(adjustedFireChance > 0 && rand.nextInt(fireLimit) <= adjustedFireChance) {
											world.setBlock(ix, iy, iz, this, meta + 1, 3);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void tryCatchFire(World world, int x, int y, int z, int chance, Random rand, int fireMetadata, ForgeDirection face) {
		int flammability = world.getBlock(x, y, z).getFlammability(world, x, y, z, face);

		if(rand.nextInt(chance) < flammability) {
			boolean flag = world.getBlock(x, y, z) == Blocks.tnt;

			world.setBlock(x, y, z, this, fireMetadata + 1, 3);

			if(flag) {
				Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
			}
		}
	}

	private boolean canNeighborBurn(World world, int x, int y, int z) {
		return this.canCatchFire(world, x + 1, y, z, WEST)
				|| this.canCatchFire(world, x - 1, y, z, EAST)
				|| this.canCatchFire(world, x, y - 1, z, UP)
				|| this.canCatchFire(world, x, y + 1, z, DOWN)
				|| this.canCatchFire(world, x, y, z - 1, SOUTH)
				|| this.canCatchFire(world, x, y, z + 1, NORTH);
	}

	private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z) {

		if(!world.isAirBlock(x, y, z)) {
			return 0;
		} else {
			int spread = 0;
			spread = this.getChanceToEncourageFire(world, x + 1, y, z, spread, WEST);
			spread = this.getChanceToEncourageFire(world, x - 1, y, z, spread, EAST);
			spread = this.getChanceToEncourageFire(world, x, y - 1, z, spread, UP);
			spread = this.getChanceToEncourageFire(world, x, y + 1, z, spread, DOWN);
			spread = this.getChanceToEncourageFire(world, x, y, z - 1, spread, SOUTH);
			spread = this.getChanceToEncourageFire(world, x, y, z + 1, spread, NORTH);
			return spread;
		}
	}

	@Override
	public boolean canCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return world.getBlock(x, y, z).isFlammable(world, x, y, z, face);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.setFire(10);

		if(entity instanceof EntityLivingBase) ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 5 * 20, 9));
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return Color.HSBtoRGB(0F, 0F, 1F - meta / 30F);
	}

	@Override
	public int getRenderType() {
		return 1;
	}
}
