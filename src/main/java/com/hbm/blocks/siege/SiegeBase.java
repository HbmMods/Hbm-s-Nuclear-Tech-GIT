package com.hbm.blocks.siege;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class SiegeBase extends BlockBase {

	private IIcon[] icons;

	public SiegeBase(Material material, int icons) {
		super(material);
		this.setTickRandomly(true);
		this.icons = new IIcon[icons];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		int h = x;
		h *= 433 + y;
		h *= 1709 + z;
		h = Math.abs(h);
		
		h = (h >> 6);
		
		return this.getIcon(h % this.icons.length, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[side % this.icons.length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		for(int i = 0; i < icons.length; i++) {
			icons[i] = reg.registerIcon(this.getTextureName() + "_" + i);
		}
	}
	
	protected boolean solidNeighbors(World world, int x, int y, int z) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(b.getMaterial() == Material.air || !b.isNormalCube())
				return false;
		}
		
		return true;
	}
	
	protected boolean shouldReplace(Block b) {
		return b != ModBlocks.siege_circuit && b != ModBlocks.siege_internal && b != ModBlocks.siege_shield;
	}
}
