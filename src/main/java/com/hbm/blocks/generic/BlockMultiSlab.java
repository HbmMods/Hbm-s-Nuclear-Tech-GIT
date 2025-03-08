package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.IStepTickReceiver;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMultiSlab extends BlockSlab implements IStepTickReceiver {
	
	public static List<Object[]> recipeGen = new ArrayList();
	
	public Block[] slabMaterials;
	public Block single;

	public BlockMultiSlab(Block single, Material mat, Block... slabMaterials) {
		super(single != null, mat);
		this.single = single;
		this.slabMaterials = slabMaterials;
		this.useNeighborBrightness = true;
		
		if(single == null) {
			for(int i = 0; i < slabMaterials.length; i++) {
				recipeGen.add(new Object[] {slabMaterials[i], this, i});
			}
		}
		
		this.setBlockTextureName(RefStrings.MODID + ":concrete");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		return block.getIcon(side, meta);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(single != null ? single : this);
	}
	
	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(Item.getItemFromBlock(single != null ? single : this), 2, (meta & 7) % slabMaterials.length);
	}
	
    @SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(single != null ? single : this);
	}

	@Override
	public String func_150002_b(int meta) {
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		return super.getUnlocalizedName() + "." + block.getUnlocalizedName().substring(5);
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return (super.getDamageValue(world, x, y, z) & 7) % slabMaterials.length;
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		if(single == null) {
			for(int i = 0; i < slabMaterials.length; ++i) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		return block.getExplosionResistance(entity);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		return block.getBlockHardness(world, x, y, z); //relies on block not assuming that they are at that position
	}

	@Override
	public void onPlayerStep(World world, int x, int y, int z, EntityPlayer player) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		if(!world.isRemote || !(block instanceof BlockSpeedy))
			return;

		if(player.moveForward != 0 || player.moveStrafing != 0) {
			player.motionX *= 1.5;
			player.motionZ *= 1.5;
		}
	}
}
