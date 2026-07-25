package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockForgottenLock extends BlockMulti {
	
	private IIcon iconTop;
	private IIcon iconAlt;
	private IIcon iconAltTop;
	private IIcon iconStone;
	private IIcon iconStoneTop;
	
	public static final int META_DEFAULT = 0;
	public static final int META_BW = 1;
	public static final int META_NULLSTONE = 2;
	public static final int META_THE_BLOCK_THAT_FUCKING_KILLS_YOU = 3;

	public BlockForgottenLock() {
		super(Material.rock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_top");
		this.iconAlt = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw_lock");
		this.iconStone = reg.registerIcon(RefStrings.MODID + ":playground/nullstone_demo_2_wip");
		this.iconStoneTop = reg.registerIcon(RefStrings.MODID + ":playground/nullstone_demo_1_wip");
		this.iconAltTop = reg.registerIcon(RefStrings.MODID + ":brick_forgotten_bw_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(meta == META_BW) {
			if(side == 0 || side == 1) return this.iconAltTop;
			return this.iconAlt;
		}
		
		if(meta == META_NULLSTONE) {
			if(side == 0 || side == 1) return this.iconStoneTop;
			return this.iconStone;
		}

		if(side == 0 || side == 1) return this.iconTop;
		return this.blockIcon;
	}
	
	/*
	 * A red herring is something that misleads or distracts from a relevant or important question.[1]
	 * It may be either a logical fallacy or a literary device that leads readers or audiences toward a
	 * false conclusion. A red herring may be used intentionally, as in mystery fiction or as part of
	 * rhetorical strategies (e.g., in politics), or may be used in argumentation inadvertently.[2]
	 * 
	 * The expression was popularized in 1807 by the English polemicist William Cobbett, who told a
	 * story of having used a strong-smelling smoked herring to divert and distract hounds from
	 * chasing a rabbit.[3]
	 */

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		// placeholder
		if(player.getHeldItem() != null) {
			boolean cracked = player.getHeldItem().getItem() == ModItems.key_red_cracked;
			if((player.getHeldItem().getItem() == ModItems.key_red || cracked) && side != 0 && side != 1) {
				if(cracked) player.getHeldItem().stackSize--;
				if(world.isRemote) return true;
				int meta = world.getBlockMetadata(x, y, z);
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				
				generate(world, x, y, z, meta, dir);
				
				world.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
		}
		
		return false;
	}
	
	public static void generate(World world, int x, int y, int z, int meta, ForgeDirection dir) {
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		int len = 15;
		for(int w = -2; w <= 2; w++) for(int h = -2; h <= 2; h++) for(int d = 0; d < len; d++) {
			Block b = (w == -2 || w == 2 || h == -2 || h == 2 || d == len - 1) ? ModBlocks.brick_forgotten : Blocks.air;
			world.setBlock(x - dir.offsetX * d + rot.offsetX * w, y + h, z - dir.offsetZ * d + rot.offsetZ * w, b);
		}
	}

	@Override public int getSubCount() { return 3; }

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
