package com.hbm.blocks.machine.pile;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockGraphiteDrilledBase extends BlockFlammable implements IToolable {

	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon blockIconAluminum; //shrouded in aluminum

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
		this.blockIconAluminum = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_drilled_aluminum");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 3;
		int meta = metadata & 4;
		
		if(side == cfg * 2 || side == cfg * 2 + 1) {
			if(meta == 4)
				return this.blockIconAluminum;
			
			return this.blockIcon;
		}
		
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
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote) {

			int meta = world.getBlockMetadata(x, y, z);
			int cfg = meta & 3;
			
			if(side == cfg * 2 || side == cfg * 2 + 1) {
				world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, meta & 7, 3);
				this.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(getInsertedItem()));
			}
		}
		
		return true;
	}
	
	//Thank god getDrops passes meta
	protected Item getInsertedItem(int meta) {
		return getInsertedItem();
	}
	
	protected Item getInsertedItem() {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList();
		drops.add(new ItemStack(ModItems.ingot_graphite, 8));
		if((meta & 4) == 4)
			drops.add(new ItemStack(ModItems.hull_small_aluminium, 1));
		if(getInsertedItem() != null)
			drops.add(new ItemStack(getInsertedItem(meta), 1));
		return drops;
	}
}
