package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.lib.RefStrings;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;

public class WasteEarth extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public WasteEarth(Material mat, boolean tick) {
		super(mat);
		this.setTickRandomly(tick);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_grass_top" : (this == ModBlocks.burning_earth ? ":burning_grass_top" : (this == ModBlocks.waste_mycelium ? ":waste_mycelium_top" : ":frozen_grass_top"))));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_earth_bottom" : (this == ModBlocks.burning_earth ? ":waste_earth_bottom" : (this == ModBlocks.waste_mycelium ? ":waste_earth_bottom" : ":frozen_dirt"))));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_grass_side" : (this == ModBlocks.burning_earth ? ":burning_grass_side" : (this == ModBlocks.waste_mycelium ? ":waste_mycelium_side" : ":frozen_grass_side"))));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		
		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium || this == ModBlocks.burning_earth) {
			return Item.getItemFromBlock(Blocks.dirt);
		}

		if(this == ModBlocks.frozen_grass) {
			return Items.snowball;
		}

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 1;
	}

	@Override
	public void onEntityWalking(World p_149724_1_, int x, int y, int z, Entity entity) {
		
		if(entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(this == ModBlocks.frozen_grass) {
				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
			}
			if(this == ModBlocks.waste_mycelium) {
				living.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 3));
			}
			if(this == ModBlocks.burning_earth) {
				living.setFire(5);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		
		if(this == ModBlocks.waste_mycelium) {
			world.spawnParticle("townaura", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
		if(this == ModBlocks.burning_earth) {
			world.spawnParticle("flame", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(this == ModBlocks.waste_mycelium && GeneralConfig.enableMycelium) {
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						Block b0 = world.getBlock(x + i, y + j, z + k);
						Block b1 = world.getBlock(x + i, y + j + 1, z + k);
						if(!b1.isOpaqueCube() && (b0 == Blocks.dirt || b0 == Blocks.grass || b0 == Blocks.mycelium || b0 == ModBlocks.waste_earth)) {
							world.setBlock(x + i, y + j, z + k, ModBlocks.waste_mycelium);
						}
					}
				}
			}
		}
		
		if(this == ModBlocks.burning_earth) {
			
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						
						if(!world.blockExists(x + i, y + j, z + k)) continue;
						
						Block b0 = world.getBlock(x + i, y + j, z + k);
						Block b1 = world.getBlock(x + i, y + j + 1, z + k);
						
						if(!b1.isOpaqueCube() &&
								((b0 == Blocks.grass || b0 == Blocks.mycelium || b0 == ModBlocks.waste_earth ||
								b0 == ModBlocks.frozen_grass || b0 == ModBlocks.waste_mycelium)
								&& !world.canLightningStrikeAt(x, y, z))) {
							world.setBlock(x + i, y + j, z + k, ModBlocks.burning_earth);
						}
						if((b0 instanceof BlockLeaves || b0 instanceof BlockBush)) {
							world.setBlockToAir(x + i, y + j, z + k);
						}
						if(b0 == ModBlocks.frozen_dirt) {
							world.setBlock(x + i, y + j, z + k, Blocks.dirt);
						}
						if(b1.isFlammable(world, x, y, z, ForgeDirection.UP) && !(b1 instanceof BlockLeaves || b1 instanceof BlockBush) && world.getBlock(x, y + 1, z) == Blocks.air) {
							world.setBlock(x, y + 1, z, Blocks.fire);
						}
					}
				}
			}
			world.setBlock(x, y, z, ModBlocks.impact_dirt);
		}

		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) {
			
			if(RadiationConfig.cleanupDeadDirt || (world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2)) {
				world.setBlock(x, y, z, Blocks.dirt);
				
			}
			
			if(world.getBlock(x, y + 1, z) instanceof BlockMushroom) {
				world.setBlock(x, y + 1, z, ModBlocks.mush);
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(this == ModBlocks.burning_earth) {
			Block b = world.getBlock(x, y + 1, z);
			if(b instanceof BlockLiquid || b instanceof BlockFluidBase || b.isNormalCube()) {
				world.setBlock(x, y, z, Blocks.dirt);
			}
		}
	}
	
	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		
		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) {
			return plantable.getPlantType(world, x, y, z) == EnumPlantType.Cave;
		}
		
		return false;
	}
}
