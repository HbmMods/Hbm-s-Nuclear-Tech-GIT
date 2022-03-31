package com.hbm.blocks.generic;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMotherOfAllOres extends BlockContainer implements IBlockMultiPass {

	public BlockMotherOfAllOres() {
		super(Material.rock);
		this.blockIcon = Blocks.stone.getIcon(0, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRandomOre();
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRandomOre) {
			return ((TileEntityRandomOre) te).getStack().copy();
		}
		
		return super.getPickBlock(target, world, x, y, z);
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Override
	public int getPasses() {
		return 2;
	}

	private IIcon[] overlays = new IIcon[10];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		for(int i = 0; i < overlays.length; i++) {
			overlays[i] = reg.registerIcon(RefStrings.MODID + ":ore_random_" + (i + 1));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return this.blockIcon;

		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRandomOre) {
			TileEntityRandomOre ore = (TileEntityRandomOre) te;
			ItemStack item = ore.getStack();
			
			if(item != null) {
				ComparableStack stack = new ComparableStack(item);
				int index = stack.hashCode() % overlays.length;
				return overlays[index];
			}
		}
		
		return this.getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return 0xffffff;
		
		return super.colorMultiplier(world, x, y, z);
	}
	
	public static class TileEntityRandomOre extends TileEntity {
		
		public ItemStack getStack() {
			return new ItemStack(Blocks.dirt);
		}

		@Override
		public boolean canUpdate() {
			return false;
		}
	}
}
