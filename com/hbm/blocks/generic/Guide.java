package com.hbm.blocks.generic;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Guide extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconFront;
	private IIcon iconBack;
	private IIcon iconLeft;
	private IIcon iconRight;

	public Guide(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":guide_bottom");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":guide_top");
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":guide_front");
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + ":guide_back");
		this.iconLeft = iconRegister.registerIcon(RefStrings.MODID + ":guide_side_left");
		this.iconRight = iconRegister.registerIcon(RefStrings.MODID + ":guide_side_right");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(metadata == 5)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconFront;
			if(side == 3) return iconBack;
			if(side == 4) return iconRight;
			if(side == 5) return iconLeft;
		}
		if(metadata == 3)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconRight;
			if(side == 3) return iconLeft;
			if(side == 4) return iconBack;
			if(side == 5) return iconFront;
		}
		if(metadata == 4)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconBack;
			if(side == 3) return iconFront;
			if(side == 4) return iconLeft;
			if(side == 5) return iconRight;
		}
		if(metadata == 2)
		{
			if(side == 0) return blockIcon;
			if(side == 1) return iconTop;
			if(side == 2) return iconLeft;
			if(side == 3) return iconRight;
			if(side == 4) return iconFront;
			if(side == 5) return iconBack;
		}

		if(side == 0) return blockIcon;
		if(side == 1) return iconTop;
		if(side == 2) return iconRight;
		if(side == 3) return iconLeft;
		if(side == 4) return iconBack;
		if(side == 5) return iconFront;
		
		return null;
	}
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote)
		{
			Block block1 = world.getBlock(x, y, z - 1);
			Block block2 = world.getBlock(x, y, z + 1);
			Block block3 = world.getBlock(x - 1, y, z);
			Block block4 = world.getBlock(x + 1, y, z);
			
			byte b0 = 3;
			
			if(block1.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 3;
			}
			if(block2.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 2;
			}
			if(block3.func_149730_j() && !block4.func_149730_j())
			{
				b0 = 5;
			}
			if(block4.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		/*if(!player.isSneaking())
		{
			
			ItemStack book1 = new ItemStack(Items.written_book);
			book1.stackTagCompound = new NBTTagCompound();
			book1.stackTagCompound.setString("author", "HbMinecraft");
			book1.stackTagCompound.setString("title", "Hbm's Nuclear Tech Mod Part 1: Resources");
			NBTTagList pages1 = new NBTTagList();

			for(String s : Library.book1) {
				pages1.appendTag(new NBTTagString(s));
			}
			
			book1.stackTagCompound.setTag("pages", pages1);
			player.inventory.addItemStackToInventory(book1);
			
			ItemStack book2 = new ItemStack(Items.written_book);
			book2.stackTagCompound = new NBTTagCompound();
			book2.stackTagCompound.setString("author", "HbMinecraft");
			book2.stackTagCompound.setString("title", "Hbm's Nuclear Tech Mod Part 2: Machines");
			NBTTagList pages2 = new NBTTagList();

			for(String s : Library.book2) {
				pages2.appendTag(new NBTTagString(s));
			}
			
			book2.stackTagCompound.setTag("pages", pages2);
			player.inventory.addItemStackToInventory(book2);
			
			ItemStack book3 = new ItemStack(Items.written_book);
			book3.stackTagCompound = new NBTTagCompound();
			book3.stackTagCompound.setString("author", "HbMinecraft");
			book3.stackTagCompound.setString("title", "Hbm's Nuclear Tech Mod Part 3: Bombs");
			NBTTagList pages3 = new NBTTagList();

			for(String s : Library.book3) {
				pages3.appendTag(new NBTTagString(s));
			}
			
			book3.stackTagCompound.setTag("pages", pages3);
			player.inventory.addItemStackToInventory(book3);
			
			ItemStack book4 = new ItemStack(Items.written_book);
			book4.stackTagCompound = new NBTTagCompound();
			book4.stackTagCompound.setString("author", "HbMinecraft");
			book4.stackTagCompound.setString("title", "Hbm's Nuclear Tech Mod Part 4: Missiles");
			NBTTagList pages4 = new NBTTagList();

			for(String s : Library.book4) {
				pages4.appendTag(new NBTTagString(s));
			}
			
			book4.stackTagCompound.setTag("pages", pages4);
			player.inventory.addItemStackToInventory(book4);
			
			ItemStack book5 = new ItemStack(Items.written_book);
			book5.stackTagCompound = new NBTTagCompound();
			book5.stackTagCompound.setString("author", "HbMinecraft");
			book5.stackTagCompound.setString("title", "Hbm's Nuclear Tech Mod Part 5: Misc");
			NBTTagList pages5 = new NBTTagList();

			for(String s : Library.book5) {
				pages5.appendTag(new NBTTagString(s));
			}
			
			book5.stackTagCompound.setTag("pages", pages5);
			player.inventory.addItemStackToInventory(book5);
			
			return true;
		} else {
			return false;
		}*/
		
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

}
