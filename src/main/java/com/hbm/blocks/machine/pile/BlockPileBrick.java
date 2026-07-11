package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.lib.RefStrings;

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
	
	public static int MAX_SIZE = 15;

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

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool == tool.HAND_DRILL) {
			if(side == 0 || side == 1) return false;
			if(world.isRemote) return true;
			
			ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();

			int negHeight = 0;
			int posHeight = 0;
			int left = 0;
			int right = 0;
			int depth = 0;

			for(int i = 1; i <= MAX_SIZE; i++) { if(world.getBlock(x, y + i, z) != this) break; posHeight = i; }
			for(int i = 1; i <= MAX_SIZE - posHeight - 1; i++) { if(world.getBlock(x, y - i, z) != this) break; negHeight = i; }
			
			ForgeDirection dirLeft = dir.getRotation(ForgeDirection.DOWN);
			for(int i = 1; i <= MAX_SIZE; i++) { if(world.getBlock(x + dirLeft.offsetX * i, y, z + dirLeft.offsetZ * i) != this) break; left = i; }
			for(int i = 1; i <= MAX_SIZE - left - 1; i++) { if(world.getBlock(x - dirLeft.offsetX * i, y, z - dirLeft.offsetZ * i) != this) break; right = i; }

			for(int i = 1; i <= MAX_SIZE; i++) { if(world.getBlock(x + dir.offsetX * i, y, z + dir.offsetZ * i) != this) break; depth = i; }
			
			if(posHeight + negHeight + 1 < 5) {
				MachinePWRController.sendError(world, x, y + posHeight, z, "Height too low (<5)", player);
				MachinePWRController.sendError(world, x, y - negHeight, z, "Height too low (<5)", player);
				return true;
			}
			
			if(left + right + 1 < 5) {
				MachinePWRController.sendError(world, x + dirLeft.offsetX * left, y, z + dirLeft.offsetZ * right, "Width too low (<5)", player);
				MachinePWRController.sendError(world, x - dirLeft.offsetX * right, y, z - dirLeft.offsetZ * right, "Width too low (<5)", player);
				return true;
			}
			
			if(depth + 1 < 5) {
				MachinePWRController.sendError(world, x + dir.offsetX * depth, y, z + dir.offsetZ * depth, "Depth too low (<5)", player);
				return true;
			}
			
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
			
			for(int h = -negHeight; h <= posHeight; h++) {
				for(int v = -left; v <= right; v++) {
					for(int d = 0; d <= depth; d++) {
						int iX = x - dirLeft.offsetX * v + dir.offsetX * d;
						int iY = y + h;
						int iZ = z - dirLeft.offsetZ * v + dir.offsetZ * d;
						world.setBlock(iX, iY, iZ, ModBlocks.pile_block);
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
}
