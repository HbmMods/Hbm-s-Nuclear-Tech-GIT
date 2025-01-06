package com.hbm.blocks.rail;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardCurveWide9 extends RailStandardCurveBase {

	public RailStandardCurveWide9() {
		super();
		this.width = 8;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;
		
		int[][] dim = new int[][] {
			{1, 0},
			{2, 0},
			{0, 1},
			{1, 1},
			{2, 1},
			{3, 1},
			{4, 1},
			{2, 2},
			{3, 2},
			{4, 2},
			{5, 2},
			{4, 3},
			{5, 3},
			{5, 4},
			{6, 3},
			{6, 4},
			{7, 4},
			{6, 5},
			{7, 5},
			{6, 6},
			{7, 6},
			{7, 7},
			{7, 8},
			{8, 6},
			{8, 7},
			{8, 8},
		};
		
		for(int[] array : dim) {
			if(!world.getBlock(x + dX * array[0] + rX * array[1], y, z + dZ * array[0] + rZ * array[1]).isReplaceable(world, x + dX * array[0] + rX * array[1], y, z + dZ * array[0] + rZ * array[1])) return false;
		}
		
		return true;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		BlockDummyable.safeRem = true;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;
		
		int d = dir.ordinal();
		int r = rot.ordinal();
		
		int[][] dim = new int[][] {
			{1, 0, d},
			{2, 0, d},
			{0, 1, r},
			{1, 1, d},
			{2, 1, d},
			{3, 1, d},
			{4, 1, d},
			{2, 2, r},
			{3, 2, r},
			{4, 2, r},
			{5, 2, d},
			{4, 3, r},
			{5, 3, r},
			{5, 4, r},
			{6, 3, d},
			{6, 4, d},
			{7, 4, d},
			{6, 5, r},
			{7, 5, r},
			{6, 6, r},
			{7, 6, r},
			{7, 7, r},
			{7, 8, r},
			{8, 6, d},
			{8, 7, d},
			{8, 8, d},
		};
		
		for(int[] array : dim) {
			world.setBlock(x + dX * array[0] + rX * array[1], y, z + dZ * array[0] + rZ * array[1], this, array[2], 3);
		}
		
		BlockDummyable.safeRem = false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glScaled(0.175, 0.175, 0.175);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glRotated(60, 1, 0, 0);
		GL11.glTranslated(4, 0, 4);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_curve_wide9, block.getIcon(1, 0), tessellator, 0, false);
		tessellator.draw();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z) {
		if(meta < 12) return;
		float rotation = 0;
		if(meta == 15) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 12) rotation = 180F / 180F * (float) Math.PI;
		if(meta == 14) rotation = 270F / 180F * (float) Math.PI;
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_curve_wide9, block.getIcon(1, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
	}
}
