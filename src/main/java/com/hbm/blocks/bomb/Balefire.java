package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

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

	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
			boolean onNetherrack = world.getBlock(x, y - 1, z).isFireSource(world, x, y - 1, z, UP);

			if(!this.canPlaceBlockAt(world, x, y, z)) {
				world.setBlockToAir(x, y, z);
			}

			int meta = world.getBlockMetadata(x, y, z);

			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(10));

			if(!onNetherrack && !this.canNeighborBurn(world, x, y, z)) {
				if(!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
					world.setBlockToAir(x, y, z);
				}
			} else {
				if(meta < 15) {
					this.tryCatchFire(world, x + 1, y, z, 500, rand, meta, WEST);
					this.tryCatchFire(world, x - 1, y, z, 500, rand, meta, EAST);
					this.tryCatchFire(world, x, y - 1, z, 300, rand, meta, UP);
					this.tryCatchFire(world, x, y + 1, z, 300, rand, meta, DOWN);
					this.tryCatchFire(world, x, y, z - 1, 500, rand, meta, SOUTH);
					this.tryCatchFire(world, x, y, z + 1, 500, rand, meta, NORTH);
				}

				for(int i1 = x - 1; i1 <= x + 1; ++i1) {
					for(int j1 = z - 1; j1 <= z + 1; ++j1) {
						for(int k1 = y - 1; k1 <= y + 4; ++k1) {
							if(i1 != x || k1 != y || j1 != z) {
								int l1 = 100;

								if(k1 > y + 1) {
									l1 += (k1 - (y + 1)) * 100;
								}

								int i2 = this.getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

								if(i2 > 0) {
									int j2 = (i2 + 40 + world.difficultySetting.getDifficultyId() * 7) / (meta + 30);

									if(j2 > 0 && rand.nextInt(l1) <= j2) {
										int k2 = meta + rand.nextInt(5) / 4;

										if(k2 > 15) {
											k2 = 15;
										}

										world.setBlock(i1, k1, j1, this, k2, 3);
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

}
