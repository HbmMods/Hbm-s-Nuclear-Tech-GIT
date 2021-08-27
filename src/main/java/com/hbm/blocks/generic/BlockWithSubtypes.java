package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.interfaces.Untested;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
@Untested
public class BlockWithSubtypes extends BlockGeneric
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	protected int setSize;
	protected String[] customNames;
	public boolean hasCustomNames = false;
	public BlockWithSubtypes(Material mat, int size)
	{
		super(mat);
		setCreativeTab(MainRegistry.blockTab);
		setSize = size;
//		icons = new IIcon[setSize];
	}
	public BlockWithSubtypes(Material mat, int size, String... names)
	{
		this(mat, size);
		addCustomNames(names);
	}
	
	public BlockWithSubtypes addCustomNames(String... names)
	{
		assert names.length == setSize : "Given string array must be equal in size to the set!";
		hasCustomNames = true;
		customNames = names;
		return this;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta % setSize];
	}
	
	@Override
	public int damageDropped(int i)
	{
		return i;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List tabList)
	{
		for (int i = 0; i < setSize; i++)
			tabList.add(new ItemStack(itemIn, 1, i));
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		icons = new IIcon[setSize];
		for (int i = 0; i < setSize; i++)
			icons[i] = register.registerIcon(hasCustomNames ? String.format("%s:%s", RefStrings.MODID, customNames[i]) : String.format("%s:%s_%s", RefStrings.MODID, getUnlocalizedName().substring(5), i));
	}
	
	public int getSetSize()
	{
		return setSize;
	}
	public String[] getNames()
	{
		return customNames.clone();
	}
}
