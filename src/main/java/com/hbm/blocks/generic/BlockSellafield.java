package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSellafield extends BlockHazard {
	
	//Sellafite blocks should probably be entirely metadata, but removing them now might mess with shit
	//...Ah, fuck it! Noone cares anyway.
	public BlockSellafield(Material mat) {
		super(mat);
		this.setCreativeTab(MainRegistry.blockTab);
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(entity instanceof EntityLivingBase)
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, meta < 5 ? meta : meta * 2));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, this.rad);
		
		int meta = world.getBlockMetadata(x, y, z);
		if(rand.nextInt(meta == 0 ? 30 * 60 : 15 * 60) == 0) {
			if(meta > 0)
				world.setBlockMetadataWithNotify(x, y, z, meta - 1, 2);
			else
				world.setBlock(x, y, z, ModBlocks.sellafield_slaked);
		}
		
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(byte i = 0; i < 6; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		icons =  new IIcon[6];
		
		for(byte i = 0; i < 6; i++)
			icons[i] = iconRegister.registerIcon(RefStrings.MODID + ":sellafield_" + i);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[meta % this.icons.length];
	}
	
	
}
