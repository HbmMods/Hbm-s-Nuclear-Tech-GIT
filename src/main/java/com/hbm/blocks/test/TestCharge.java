package com.hbm.blocks.test;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TestCharge extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public TestCharge(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return 16;
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		int l = determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
	}

	public static int determineOrientation(World p_150071_0_, int p_150071_1_, int p_150071_2_, int p_150071_3_, EntityLivingBase p_150071_4_) {
		
		//instead of mirrored piston behavior, we could just scan for nearby cores and adjust it accordingly

		if(MathHelper.abs((float) p_150071_4_.posX - (float) p_150071_1_) < 2.0F && MathHelper.abs((float) p_150071_4_.posZ - (float) p_150071_3_) < 2.0F) {
			double d0 = p_150071_4_.posY + 1.82D - (double) p_150071_4_.yOffset;

			if(d0 - (double) p_150071_2_ > 2.0D) {
				return 0;
			}

			if((double) p_150071_2_ - d0 > 0.0D) {
				return 1;
			}
		}

		int l = MathHelper.floor_double((double) (p_150071_4_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 1)));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":test_charge_side");
		this.topIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":test_charge_top");
		this.bottomIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":test_charge_bottom");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		int k = getPistonOrientation(p_149691_2_);
		return k > 5 ? this.topIcon : (p_149691_1_ == k ? this.topIcon : (p_149691_1_ == Facing.oppositeSide[k] ? this.bottomIcon : this.blockIcon));
	}

	public static int getPistonOrientation(int p_150076_0_) {
		return p_150076_0_ & 7;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		//instead of exploding outright, we schedule an update. this will let redstone lines transmit signals
		//even if they are on top of the charge which would get destroyed, allowing for more compact designs
		if(!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.scheduledUpdatesAreImmediate = false;
			world.scheduleBlockUpdate(x, y, z, this, 1);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(getPistonOrientation(world.getBlockMetadata(x, y, z)));
			
			//is our target a core?
			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == ModBlocks.test_core) {
				
				//increment meta, schedule an update and set to air
				world.setBlock(x, y, z, Blocks.air);
				int core = world.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
				//this should be false by default and only true for special world gen, but some mods tend to leak immediate updates
				world.scheduledUpdatesAreImmediate = false;
				world.setBlockMetadataWithNotify(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, core + 1, 4); //flag 4 causes no block update and no re-render on clients
				world.scheduleBlockUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.test_core, 1); //set delay to 2 if 1 isn't enough
				
			//if not, just explode, who cares
			} else {
				
				world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 5.0F, false, true);
			}
		}
	}
}
