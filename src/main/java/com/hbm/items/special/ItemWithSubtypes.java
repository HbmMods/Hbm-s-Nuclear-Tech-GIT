package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
/**
 * Class to handle subtype items easier
 * @author UFFR
 *
 */
public class ItemWithSubtypes extends ItemCustomLore
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] itemIcon;
	protected int setSize;
	protected String[] customNames;
	public boolean hasCustomNames = false;
	public ItemWithSubtypes(int size)
	{
		super();
		setSize = size;
		setHasSubtypes(true);
	}
	@Deprecated
	public ItemWithSubtypes(int size, String... names)
	{
		this(size);
		addCustomNames(names);
	}
	// Why didn't I think of this sooner?
	public ItemWithSubtypes(String...names)
	{
		this(names.length);
		addCustomNames(names);
	}
	/**
	 * Add a set of custom unlocalized names to be based on stack damage/meta tag
	 * @param names - The set of unlocalized names, the "item." prefix is to be omitted of course
	 * @return Itself for constructor convenience
	 */
	public ItemWithSubtypes addCustomNames(String... names)
	{
		assert names.length == setSize : "Given string array must be equal in size to the set!";
		hasCustomNames = true;
		customNames = names;
		return this;
	}
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (customNames != null && customNames.length > 0)
			return "item.".concat(customNames[stack.getItemDamage()]);
		else if (stack.getItemDamage() > setSize)
			return "invalid meta tag";
		else
			return super.getUnlocalizedName(stack);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister registry)
	{
		itemIcon = new IIcon[setSize];
		for (int i = 0; i < itemIcon.length; i++)
			itemIcon[i] = registry.registerIcon(hasCustomNames ? String.format("%s:%s", RefStrings.MODID, customNames[i]) : String.format("%s:%s_%s", RefStrings.MODID, this.getUnlocalizedName().substring(5), i));
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List tabList)
	{
		for (int i = 0; i < setSize; i++)
			tabList.add(new ItemStack(itemIn, 1, i));
	}
	
	@Override
	public IIcon getIconFromDamage(int icon)
	{
		int i = MathHelper.clamp_int(icon, 0, setSize);
		return itemIcon[i];
	}
}
