package com.hbm.blocks.generic;

import java.util.ArrayList;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHangingVine extends Block implements IShearable {

	public BlockHangingVine(Material mat) {
		super(mat);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.motionX *= 0.5;
		entity.motionY *= 0.5;
		entity.motionZ *= 0.5;
		entity.fallDistance = 0F;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return this.canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y + 1, z);
		return b.isSideSolid(world, x, y + 1, z, ForgeDirection.UP) || b == this;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		this.checkAndDropBlock(world, x, y, z);
	}

	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			world.setBlock(x, y, z, Blocks.air);
		}
	}
	
	@Override protected boolean canSilkHarvest() { return true; }
	
	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this)); //placeholder
		return ret;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT) public IIcon iconItem;
	@SideOnly(Side.CLIENT) public IIcon iconGround; //when touching a solid face below
	@SideOnly(Side.CLIENT) public IIcon iconHang; //when hanging mid-air
	@SideOnly(Side.CLIENT) public IIcon iconGlow; //regular phosphor
	@SideOnly(Side.CLIENT) public IIcon iconHangGlow; //phosphor in different position when hanging for variety
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.iconItem = reg.registerIcon(RefStrings.MODID + ":vine_phosphor_item");
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":vine_phosphor");
		this.iconGround = reg.registerIcon(RefStrings.MODID + ":vine_phosphor_ground");
		this.iconHang = reg.registerIcon(RefStrings.MODID + ":vine_phosphor_hang");
		this.iconGlow = reg.registerIcon(RefStrings.MODID + ":vine_phosphor_spots");
		this.iconHangGlow = reg.registerIcon(RefStrings.MODID + ":vine_phosphor_spots_hang");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, boolean pass) {
		Block b = world.getBlock(x, y - 1, z);
		
		if(!pass)
			return b.isSideSolid(world, x, y, z, ForgeDirection.UP) ? iconGround : b == this ? blockIcon : iconHang;
		else
			return b.isAir(world, x, y, z) ? iconHangGlow : iconGlow;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.iconItem;
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType() {
		return renderID;
	}
}
