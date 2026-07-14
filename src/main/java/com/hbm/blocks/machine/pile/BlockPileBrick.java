package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.pile.TileEntityPileBaseMK2;
import com.hbm.tileentity.machine.pile.TileEntityPileCore;
import com.hbm.tileentity.machine.pile.TileEntityPileCore.PileOrientation;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPileBrick extends Block implements IToolable {
	
	@SideOnly(Side.CLIENT) protected IIcon iconTop;
	@SideOnly(Side.CLIENT) protected IIcon iconSide;

	public BlockPileBrick() {
		super(Material.rock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":pile_brick_top");
		this.iconSide = reg.registerIcon(RefStrings.MODID + ":pile_brick_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side == 0 || side == 1) return this.iconTop;
		if(side == 4 || side == 5) return this.iconSide;
		return this.blockIcon;
	}


	public static final int MIN_V_SIZE = 5;
	public static final int MIN_H_SIZE = 5;
	public static final int MAX_V_SIZE = 15;
	public static final int MAX_H_SIZE = 15;

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool == tool.HAND_DRILL) {
			if(side == 0 || side == 1) return false;
			if(world.isRemote) return true;
			
			ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
			ForgeDirection dirLeft = dir.getRotation(ForgeDirection.DOWN);

			int negHeight = 0;
			int posHeight = 0;
			int left = 0;
			int right = 0;
			int depth = 0;

			/// PROBE DIMENSIONS ///
			// height
			for(int i = 1; i <= MAX_V_SIZE - 1; i++) {				if(world.getBlock(x, y + i, z) != this) break; posHeight = i; }
			for(int i = 1; i <= MAX_V_SIZE - posHeight - 1; i++) {	if(world.getBlock(x, y - i, z) != this) break; negHeight = i; }
			// side width
			for(int i = 1; i <= MAX_H_SIZE - 1; i++) {				if(world.getBlock(x + dirLeft.offsetX * i, y, z + dirLeft.offsetZ * i) != this) break; left = i; }
			for(int i = 1; i <= MAX_H_SIZE - left - 1; i++) {		if(world.getBlock(x - dirLeft.offsetX * i, y, z - dirLeft.offsetZ * i) != this) break; right = i; }
			// depth
			for(int i = 1; i <= MAX_H_SIZE; i++) {					if(world.getBlock(x + dir.offsetX * i, y, z + dir.offsetZ * i) != this) break; depth = i; }
			
			/// SIZE CHECKS ///
			if(posHeight + negHeight + 1 < MIN_V_SIZE) {
				MachinePWRController.sendError(world, x, y + posHeight, z, "Height too low (<" + MIN_V_SIZE + ")", player);
				MachinePWRController.sendError(world, x, y - negHeight, z, "Height too low (<" + MIN_V_SIZE + ")", player);
				return true;
			}
			
			if(left + right + 1 < MIN_H_SIZE) {
				MachinePWRController.sendError(world, x + dirLeft.offsetX * left, y, z + dirLeft.offsetZ * right, "Width too low (<" + MIN_H_SIZE + ")", player);
				MachinePWRController.sendError(world, x - dirLeft.offsetX * right, y, z - dirLeft.offsetZ * right, "Width too low (<" + MIN_H_SIZE + ")", player);
				return true;
			}
			
			if(depth + 1 < MIN_H_SIZE) {
				MachinePWRController.sendError(world, x + dir.offsetX * depth, y, z + dir.offsetZ * depth, "Depth too low (<" + MIN_H_SIZE + ")", player);
				return true;
			}

			/// CORE EDGE CHECK ///
			if(posHeight == 0 || negHeight == 0 || left == 0 || right == 0) {
				MachinePWRController.sendError(world, x, y, z, "Core cannot be on an edge", player);
				return true;
			}

			/// VOLUME CHECK ///
			for(int h = -negHeight; h <= posHeight; h++) {
				for(int v = -left; v <= right; v++) {
					for(int d = 0; d <= depth; d++) {
						int iX = x - dirLeft.offsetX * v + dir.offsetX * d;
						int iY = y + h;
						int iZ = z - dirLeft.offsetZ * v + dir.offsetZ * d;
						
						if(world.getBlock(iX, iY, iZ) != this) {
							MachinePWRController.sendError(world, iX, iY, iZ, "Graphite block missing", player);
							return true;
						}
					}
				}
			}

			/// BUILD ///
			for(int h = -negHeight; h <= posHeight; h++) {
				for(int v = -left; v <= right; v++) {
					for(int d = 0; d <= depth; d++) {
						int iX = x - dirLeft.offsetX * v + dir.offsetX * d;
						int iY = y + h;
						int iZ = z - dirLeft.offsetZ * v + dir.offsetZ * d;
						
						if(x == iX && y == iY && z == iZ) {
							world.setBlock(iX, iY, iZ, ModBlocks.pile_block, BlockPile.META_CORE, 3);
							TileEntityPileCore core = (TileEntityPileCore) world.getTileEntity(iX, iY, iZ);
							core.orientation = PileOrientation.getOrientation(dir);
						} else {
							world.setBlock(iX, iY, iZ, ModBlocks.pile_block, BlockPile.META_DUMMY, 3);
							TileEntityPileBaseMK2 pile = (TileEntityPileBaseMK2) world.getTileEntity(iX, iY, iZ);
							pile.coreX = x;
							pile.coreY = y;
							pile.coreZ = z;
						}
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
}
