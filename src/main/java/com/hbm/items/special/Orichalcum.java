package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class Orichalcum extends ItemWithSubtypes
{
	public Orichalcum()
	{
		super(7, "ingot_orichalcum", "gem_orichalcum", "ingot_orichalcum_small", "powder_orichalcum_mix", "ingot_orichalcum_small_irr", "plate_orichalcum", "fragment_orichalcum");
	}
}
