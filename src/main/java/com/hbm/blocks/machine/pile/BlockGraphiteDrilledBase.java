package com.hbm.blocks.machine.pile;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockGraphiteDrilledBase extends BlockFlammable {

	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;

	public BlockGraphiteDrilledBase() {
		super(ModBlocks.block_graphite.getMaterial(), ((BlockFlammable) ModBlocks.block_graphite).encouragement, ((BlockFlammable) ModBlocks.block_graphite).flammability);
		
		this.setCreativeTab(null);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		
		if(side == cfg * 2 || side == cfg * 2 + 1)
			return this.blockIcon;
		
		return this.sideIcon;
	}
	
	protected static void ejectItem(World world, int x, int y, int z, ForgeDirection dir, ItemStack stack) {
		
		EntityItem dust = new EntityItem(world, x + 0.5D + dir.offsetX * 0.75D, y + 0.5D + dir.offsetY * 0.75D, z + 0.5D + dir.offsetZ * 0.75D, stack);
		dust.motionX = dir.offsetX * 0.25;
		dust.motionY = dir.offsetY * 0.25;
		dust.motionZ = dir.offsetZ * 0.25;
		world.spawnEntityInWorld(dust);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList();
		drops.add(new ItemStack(ModItems.ingot_graphite, 8));
		return drops;
	}
}
