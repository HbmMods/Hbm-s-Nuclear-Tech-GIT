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

public class RailStandardCurveWide7 extends RailStandardCurveBase {

	public RailStandardCurveWide7() {
		super();
		this.width = 6;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		dir = dir.getOpposite();

		int dX = dir.offsetX;
		int dZ = dir.offsetZ;
		int rX = rot.offsetX;
		int rZ = rot.offsetZ;
		
		return world.getBlock(x + dX, y, z + dZ).isReplaceable(world, x + dX, y, z + dZ) &&
				world.getBlock(x + rX, y, z + rZ).isReplaceable(world, x + rX, y, z + rZ) &&
				world.getBlock(x + dX + rX, y, z + dZ + rZ).isReplaceable(world, x + dX + rX, y, z + dZ + rZ) &&
				world.getBlock(x + dX + rX * 2, y, z + dZ + rZ * 2).isReplaceable(world, x + dX + rX * 2, y, z + dZ + rZ * 2) &&
				world.getBlock(x + dX * 2 + rX, y, z + dZ * 2 + rZ).isReplaceable(world, x + dX * 2 + rX, y, z + dZ * 2 + rZ) &&
				world.getBlock(x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2).isReplaceable(world, x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2) &&
				world.getBlock(x + dX * 3 + rX, y, z + dZ * 3 + rZ).isReplaceable(world, x + dX * 3 + rX, y, z + dZ * 3 + rZ) &&
				world.getBlock(x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2).isReplaceable(world, x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2) &&
				world.getBlock(x + dX * 2 + rX * 3, y, z + dZ * 2 + rZ * 3).isReplaceable(world, x + dX * 2 + rX * 3, y, z + dZ * 2 + rZ * 3) &&
				world.getBlock(x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3).isReplaceable(world, x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3) &&
				world.getBlock(x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3).isReplaceable(world, x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3) &&
				world.getBlock(x + dX * 3 + rX * 4, y, z + dZ * 3 + rZ * 4).isReplaceable(world, x + dX * 3 + rX * 4, y, z + dZ * 3 + rZ * 4) &&
				world.getBlock(x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4).isReplaceable(world, x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4);
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

		world.setBlock(x + dX, y, z + dZ, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 2, y, z + dZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + rX, y, z + rZ, this, rot.ordinal(), 3);
		world.setBlock(x + dX + rX, y, z + dZ + rZ, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 2 + rX, y, z + dZ * 2 + rZ, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX, y, z + dZ * 3 + rZ, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX, y, z + dZ * 4 + rZ, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 2 + rX * 2, y, z + dZ * 2 + rZ * 2, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX * 2, y, z + dZ * 3 + rZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX * 2, y, z + dZ * 4 + rZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 2, y, z + dZ * 5 + rZ * 2, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 3 + rX * 3, y, z + dZ * 3 + rZ * 3, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX * 3, y, z + dZ * 4 + rZ * 3, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 3, y, z + dZ * 5 + rZ * 3, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 4 + rX * 4, y, z + dZ * 4 + rZ * 4, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 4, y, z + dZ * 5 + rZ * 4, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 6 + rX * 4, y, z + dZ * 6 + rZ * 4, this, dir.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 5, y, z + dZ * 5 + rZ * 5, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 5 + rX * 6, y, z + dZ * 5 + rZ * 6, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 6 + rX * 5, y, z + dZ * 6 + rZ * 5, this, rot.ordinal(), 3);
		world.setBlock(x + dX * 6 + rX * 6, y, z + dZ * 6 + rZ * 6, this, rot.ordinal(), 3);
		
		BlockDummyable.safeRem = false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glScaled(0.225, 0.225, 0.225);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glRotated(60, 1, 0, 0);
		GL11.glTranslated(3, 0, 3);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_curve_wide7, block.getIcon(1, 0), tessellator, 0, false);
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
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_curve_wide7, block.getIcon(1, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
	}
}
