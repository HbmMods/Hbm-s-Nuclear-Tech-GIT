package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.Library;
import com.hbm.render.block.ISBRHUniversal;
import com.hbm.render.util.RenderBlocksNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBarrier extends Block implements ISBRHUniversal {

	public BlockBarrier(Material mat) {
		super(mat);
	}

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(world.getBlockMetadata(x, y, z) != 0) return;
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side >= 2 && side <= 5 ? side : meta;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		setBlockBounds(0, 0, 0, 1, 1, 1);
		
		if(meta == Library.POS_X.ordinal()) setBlockBounds(0, 0, 0, 0.125F, 1, 1);
		if(meta == Library.POS_Z.ordinal()) setBlockBounds(0, 0, 0, 1, 1, 0.125F);
		if(meta == Library.NEG_X.ordinal()) setBlockBounds(0.875F, 0, 0, 1, 1, 1);
		if(meta == Library.NEG_Z.ordinal()) setBlockBounds(0, 0, 0.875F, 1, 1, 1);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity collider) {

		int meta = world.getBlockMetadata(x, y, z);
		List<AxisAlignedBB> bbs = new ArrayList();
		
		Block nx = world.getBlock(x - 1, y, z);
		Block px = world.getBlock(x + 1, y, z);
		Block nz = world.getBlock(x, y, z - 1);
		Block pz = world.getBlock(x, y, z + 1);

		if(nx.isOpaqueCube() || nx.isNormalCube() || meta == Library.POS_X.ordinal()) bbs.add(AxisAlignedBB.getBoundingBox(x, y, z, x + 0.125, y + 1, z + 1));
		if(nz.isOpaqueCube() || nz.isNormalCube() || meta == Library.POS_Z.ordinal()) bbs.add(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 0.125));
		if(px.isOpaqueCube() || px.isNormalCube() || meta == Library.NEG_X.ordinal()) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.875, y, z, x + 1, y + 1, z + 1));
		if(pz.isOpaqueCube() || pz.isNormalCube() || meta == Library.NEG_Z.ordinal()) bbs.add(AxisAlignedBB.getBoundingBox(x, y, z + 0.875, x + 1, y + 1, z + 1));

		for(AxisAlignedBB bb : bbs) {
			if(aabb.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {

		GL11.glPushMatrix();
		RenderBlocks renderer = (RenderBlocks) renderBlocks;
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		renderer.setRenderBounds(0.4375, 0D, 0.4375D, 0.5625D, 1D, 0.5625D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.5D, 0.0625D, 0D, 0.5625D, 0.4725, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.5D, 0.5625D, 0D, 0.5625D, 0.9375, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {

		RenderBlocksNT renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		int meta = world.getBlockMetadata(x, y, z);
		
		Block nx = world.getBlock(x - 1, y, z);
		Block px = world.getBlock(x + 1, y, z);
		Block nz = world.getBlock(x, y, z - 1);
		Block pz = world.getBlock(x, y, z + 1);
		Block py = world.getBlock(x, y + 1, z);

		boolean negX = nx.isOpaqueCube() || nx.isNormalCube() || meta == Library.POS_X.ordinal();
		boolean negZ = nz.isOpaqueCube() || nz.isNormalCube() || meta == Library.POS_Z.ordinal();
		boolean posX = px.isOpaqueCube() || px.isNormalCube() || meta == Library.NEG_X.ordinal();
		boolean posZ = pz.isOpaqueCube() || pz.isNormalCube() || meta == Library.NEG_Z.ordinal();
		boolean posY = py.isOpaqueCube() || py.isNormalCube();

		if(negX) {
			renderer.setRenderBounds(0D, 0D, 0.4375D, 0.125D, 1D, 0.5625D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.0625D, negZ ? 0.125D : 0D, 0.0625D, 0.4375D, posZ ? 0.875D : 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.5625D, negZ ? 0.125D : 0D, 0.0625D, 0.9375D, posZ ? 0.875D : 1D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(negZ) {
			renderer.setRenderBounds(0.4375D, 0D, 0D, 0.5625D, 1D, 0.125D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(negX ? 0.125D : 0D, 0.0625D, 0D, posX ? 0.875D : 1D, 0.4375D, 0.0625D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(negX ? 0.125D : 0D, 0.5625D, 0D, posX ? 0.875D : 1D, 0.9375D, 0.0625D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(posX) {
			renderer.setRenderBounds(0.875D, 0D, 0.4375D, 1D, 1D, 0.5625D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.9375D, 0.0625D, negZ ? 0.125D : 0D, 1D, 0.4375D, posZ ? 0.875D : 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.9375D, 0.5625D, negZ ? 0.125D : 0D, 1D, 0.9375D, posZ ? 0.875D : 1D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(posZ) {
			renderer.setRenderBounds(0.4375D, 0D, 0.875D, 0.5625D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(negX ? 0.125D : 0D, 0.0625D, 0.9375D, posX ? 0.875D : 1D, 0.4375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(negX ? 0.125D : 0D, 0.5625D, 0.9375D, posX ? 0.875D : 1D, 0.9375D, 1D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(posY) {
			renderer.setRenderBounds(0D, 0.875D, 0D, 0.125D, 0.9375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.875D, 0.875D, 0D, 1D, 0.9375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.9375D, 0.0625D, 1D, 1D, 0.4375D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.9375D, 0.5625D, 1D, 1D, 0.9375D); renderer.renderStandardBlock(block, x, y, z);
		}
		
		return true;
	}
}
