package com.hbm.items.machine;

import java.util.List;
import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemAtomicClock extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon[] itemIcon;

	public ItemAtomicClock()
	{
		hasSubtypes = true;
		setMaxDamage(0);
	}
	
	public float getNewInaccuracy(ItemStack stack)
	{
		float inaccuracy;
		Random rand = new Random();

		switch(getDamage(stack))
		{
		case 0:
			inaccuracy = rand.nextInt(10);
			break;
		case 1:
			inaccuracy = rand.nextInt(5);
			break;
		case 2:
			inaccuracy = rand.nextInt(1);
			break;
		default:
		case 3:
			inaccuracy = 0.0F;
			break;
		}
		// Flip to negative
		if (rand.nextBoolean())
			inaccuracy *= -1;
		
		inaccuracy /= 10;
		System.out.println(getDamage(stack));
		System.out.println(inaccuracy);
		return inaccuracy;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn)
	{
		if (!worldIn.isRemote)
			playerIn.addChatComponentMessage(new ChatComponentText(String.format("Current time: ", worldIn.getTotalWorldTime() + getNewInaccuracy(stackIn))));
		return stackIn;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18nUtil.resolveKey("item.atomic_clock.desc." + MathHelper.clamp_int(stack.getItemDamage(), 0, 5)));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		itemIcon = new IIcon[5];
		for (int i = 0; i < itemIcon.length; ++i)
			itemIcon[i] = register.registerIcon(String.format("%s:atomic_clock_%s", RefStrings.MODID, i));
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tabs, List tabList)
	{
		for (int i = 0; i < 5; ++i)
			tabList.add(new ItemStack(itemIn, 1, i));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int icon)
	{
		int i = MathHelper.clamp_int(icon, 0, 5);
		return itemIcon[i];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 5);
		if (i == 4)
			return "item.quantum_clock";
		else
			return super.getUnlocalizedName();
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		switch(MathHelper.clamp_int(stack.getItemDamage(), 0, 5))
		{
		case 2:
			return EnumRarity.uncommon;
		case 3:
			return EnumRarity.rare;
		case 4:
			return EnumRarity.epic;
		default:
			return EnumRarity.common;
		}
	}
}
