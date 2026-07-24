package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockMulti;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockForgottenBrick extends BlockMulti {

	private IIcon iconTop;
	private IIcon iconAlt;
	private IIcon iconAltTop;
	private IIcon iconStone;
	private IIcon iconHole;
	private IIcon iconEmpty;
	private IIcon iconPlanks;
	private IIcon iconBricks;

	public static final int META_DEFAULT = 0;
	public static final int META_BW = 1;
	public static final int META_NULLSTONE = 2;
	public static final int META_HOLE = 3;
	public static final int META_HOLE_EMPTY = 4;
	public static final int META_NULLROOM_WOOD = 5;
	public static final int META_NULLROOM_STONE = 6;

	public BlockForgottenBrick() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_top");
		this.iconAlt = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw");
		this.iconAltTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw_top");
		this.iconStone = reg.registerIcon(RefStrings.MODID + ":playground/nullstone_demo_1_wip");
		this.iconHole = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_hole");
		this.iconEmpty = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_hole_empty");
		this.iconPlanks = reg.registerIcon(RefStrings.MODID + ":nr_planks");
		this.iconBricks = reg.registerIcon(RefStrings.MODID + ":nr_stone");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(meta == META_BW) {
			if(side == 0 || side == 1) return this.iconAltTop;
			return this.iconAlt;
		}
		if(meta == META_NULLSTONE) {
			return this.iconStone;
		}
		if(meta == META_HOLE) {
			if(side == 0 || side == 1) return this.iconTop;
			return this.iconHole;
		}
		if(meta == META_HOLE_EMPTY) {
			if(side == 0 || side == 1) return this.iconTop;
			return this.iconEmpty;
		}
		if(meta == META_NULLROOM_WOOD) {
			return this.iconPlanks;
		}
		if(meta == META_NULLROOM_STONE) {
			return this.iconBricks;
		}

		if(side == 0 || side == 1) return this.iconTop;
		return this.blockIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == META_HOLE) {
			if(player.getHeldItem() == null) {
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(ModItems.coal_eternal);
				world.setBlockMetadataWithNotify(x, y, z, META_HOLE_EMPTY, 3);
				return true;
			}
			return false;
		}
		
		return false;
	}

	@Override public int getSubCount() { return 7; }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
