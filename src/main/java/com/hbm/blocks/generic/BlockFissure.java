package com.hbm.blocks.generic;

import java.util.Random;

import api.hbm.fluid.IFluidStandardSender;
import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.TileEntityLoadedBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFissure extends BlockContainer implements IBlockMultiPass {

	private IIcon overlay;

	public BlockFissure() {
		super(Material.rock);
		this.setBlockTextureName("bedrock");
		this.setBlockUnbreakable();
		this.setResistance(1_000_000);
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		this.blockIcon = reg.registerIcon("bedrock");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":molten_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.bedrock.getIcon(0, 0);
		
		return this.overlay;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.getBlock(x, y + 1, z).isReplaceable(world, x, y + 1, z)) world.setBlock(x, y + 1, z, ModBlocks.volcanic_lava_block);
	}
	
	@Override
	public boolean shouldRenderItemMulti() {
		return true;
	}

	@Override
	public int getPasses() {
		return 2;
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFissure();
	}
	
	public static class TileEntityFissure extends TileEntityLoadedBase implements IFluidStandardSender {

		public FluidTank lava = new FluidTank(Fluids.LAVA, 1_000);
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				lava.setFill(1_000);
				this.sendFluid(lava, worldObj, xCoord, yCoord + 1, zCoord, ForgeDirection.UP);
			}
		}

		@Override
		public boolean canConnect(FluidType type, ForgeDirection dir) {
			return dir == ForgeDirection.DOWN && type == Fluids.LAVA;
		}
		
		@Override public FluidTank[] getAllTanks() { return new FluidTank[] {lava}; }
		@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {lava}; }
	}
}
