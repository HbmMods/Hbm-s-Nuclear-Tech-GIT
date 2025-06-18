package com.hbm.blocks.generic;

import org.lwjgl.opengl.GL11;

import com.hbm.render.block.ISBRHUniversal;
import com.hbm.render.util.RenderBlocksNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSandbags extends Block implements ISBRHUniversal {

	public BlockSandbags(Material mat) {
		super(mat);
	}

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		
		float min = 0.25F;
		float max = 0.75F;

		Block nx = world.getBlock(x - 1, y, z);
		Block px = world.getBlock(x + 1, y, z);
		Block nz = world.getBlock(x, y, z - 1);
		Block pz = world.getBlock(x, y, z + 1);

		float minX = (nx.isOpaqueCube() || nx.isNormalCube() || nx == this) ? 0F : min;
		float minZ = (nz.isOpaqueCube() || nz.isNormalCube() || nz == this) ? 0F : min;
		float maxX = (px.isOpaqueCube() || px.isNormalCube() || px == this) ? 1F : max;
		float maxZ = (pz.isOpaqueCube() || pz.isNormalCube() || pz == this) ? 1F : max;
		
		this.setBlockBounds(minX, 0, minZ, maxX, 1, maxZ);
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {

		GL11.glPushMatrix();
		RenderBlocks renderer = (RenderBlocks) renderBlocks;
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		renderer.setRenderBounds(0.125D, 0D, 0.125D, 0.875D, 1D, 0.875D);
		RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {

		RenderBlocksNT renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		renderer.setRenderBoundsFromBlock(block);
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}
}
