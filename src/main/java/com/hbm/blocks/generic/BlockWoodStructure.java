package com.hbm.blocks.generic;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.render.block.ISBRHUniversal;
import com.hbm.render.util.RenderBlocksNT;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodStructure extends BlockEnumMulti implements ISBRHUniversal {

	public BlockWoodStructure(Material mat) {
		super(mat, EnumWoodStructure.class, true, false);
	}
	
	public enum EnumWoodStructure {
		ROOF, SCAFFOLD, CEILING
	}

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		EnumWoodStructure type = EnumUtil.grabEnumSafely(EnumWoodStructure.class, world.getBlockMetadata(x, y, z));
		if(type == type.SCAFFOLD && side == ForgeDirection.UP) return true;
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		EnumWoodStructure type = EnumUtil.grabEnumSafely(EnumWoodStructure.class, world.getBlockMetadata(x, y, z));
		setBlockBounds(0, 0, 0, 1, 1, 1);
		if(type == type.ROOF) setBlockBounds(0F, 0F, 0F, 1F, 0.1875F, 1F);
		if(type == type.SCAFFOLD) setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		if(type == type.CEILING) setBlockBounds(0F, 0.875F, 0F, 1F, 1F, 1F);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity collider) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, collider);
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {

		GL11.glPushMatrix();
		RenderBlocks renderer = (RenderBlocks) renderBlocks;
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		EnumWoodStructure type = EnumUtil.grabEnumSafely(EnumWoodStructure.class, meta);

		if(type == type.ROOF) {
			GL11.glTranslatef(0F, 0.125F, 0F);
			renderer.setRenderBounds(0D, 0D, 0D, 0.125D, 0.125D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 0.125D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.125D, 0.0625D, 1D, 0.1875D, 0.4375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.125D, 0.5625D, 1D, 0.1875D, 0.9375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		}
		if(type == type.SCAFFOLD) {
			renderer.setRenderBounds(0.0625D, 0D, 0.0625D, 0.1875D, 0.875D, 0.1875D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.8125D, 0D, 0.0625D, 0.9375D, 0.875D, 0.1875D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.0625D, 0D, 0.8125D, 0.1875D, 0.875D, 0.9375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.8125D, 0D, 0.8125D, 0.9375D, 0.875D, 0.9375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.125D, 0D, 0.0625D, 0.375D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.9375D, 0.125D, 0D, 1D, 0.375D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.5D, 0D, 1D, 0.75D, 0.0625D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.5D, 0.9375D, 1D, 0.75D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.875D, 0D, 1D, 1D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		}
		if(type == type.CEILING) {
			GL11.glTranslatef(0F, 0.625F, 0F);
			renderer.setRenderBounds(0D, 0.0625D, 0D, 0.125D, 0.125D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0.875D, 0.0625D, 0D, 1D, 0.125D, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.125D, 0.0625D, 1D, 0.1875D, 0.4375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
			renderer.setRenderBounds(0D, 0.125D, 0.5625D, 1D, 0.1875D, 0.9375D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {

		RenderBlocksNT renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		int meta = world.getBlockMetadata(x, y, z);
		EnumWoodStructure type = EnumUtil.grabEnumSafely(EnumWoodStructure.class,meta);

		if(type == type.ROOF) {
			boolean nx = world.getBlock(x - 1, y, z) == this && world.getBlockMetadata(x - 1, y, z) == meta;
			boolean px = world.getBlock(x + 1, y, z) == this && world.getBlockMetadata(x + 1, y, z) == meta;
			renderer.setRenderBounds(0D, 0D, 0D, 0.125D, 0.125D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 0.125D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(nx ? 0D : 0.0625D, 0.125D, 0.0625D, px ? 1D : 0.9375D, 0.1875D, 0.4375D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(nx ? 0D : 0.0625D, 0.125D, 0.5625D, px ? 1D : 0.9375D, 0.1875D, 0.9375D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(type == type.SCAFFOLD) {
			boolean py = world.getBlock(x, y + 1, z) == this && world.getBlockMetadata(x, y + 1, z) == meta;
			boolean nx = world.getBlock(x - 1, y, z) == this && world.getBlockMetadata(x - 1, y, z) == meta;
			boolean nz = world.getBlock(x, y, z - 1) == this && world.getBlockMetadata(x, y, z - 1) == meta;
			boolean px = world.getBlock(x + 1, y, z) == this && world.getBlockMetadata(x + 1, y, z) == meta;
			boolean pz = world.getBlock(x, y, z + 1) == this && world.getBlockMetadata(x, y, z + 1) == meta;
			renderer.setRenderBounds(0.0625D, 0D, 0.0625D, 0.1875D, py ? 1D : 0.875D, 0.1875D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.8125D, 0D, 0.0625D, 0.9375D, py ? 1D : 0.875D, 0.1875D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.0625D, 0D, 0.8125D, 0.1875D, py ? 1D : 0.875D, 0.9375D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.8125D, 0D, 0.8125D, 0.9375D, py ? 1D : 0.875D, 0.9375D); renderer.renderStandardBlock(block, x, y, z);
			
			if(!nx) renderer.setRenderBounds(0D, 0.125D, 0D, 0.0625D, 0.375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			if(!px) renderer.setRenderBounds(0.9375D, 0.125D, 0D, 1D, 0.375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			if(!nz) renderer.setRenderBounds(0D, 0.5D, 0D, 1D, 0.75D, 0.0625D); renderer.renderStandardBlock(block, x, y, z);
			if(!pz) renderer.setRenderBounds(0D, 0.5D, 0.9375D, 1D, 0.75D, 1D); renderer.renderStandardBlock(block, x, y, z);
			
			if(!py) renderer.setRenderBounds(0D, 0.875D, 0D, 1D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
		}
		if(type == type.CEILING) {
			renderer.setRenderBounds(0D, 0.875D, 0D, 0.125D, 0.9375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.875D, 0.875D, 0D, 1D, 0.9375D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.9375D, 0.0625D, 1D, 1D, 0.4375D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0.9375D, 0.5625D, 1D, 1D, 0.9375D); renderer.renderStandardBlock(block, x, y, z);
		}
		
		return true;
	}
}
