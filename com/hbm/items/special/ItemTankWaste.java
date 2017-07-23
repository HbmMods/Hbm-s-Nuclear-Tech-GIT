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

public class ItemTankWaste extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon[] field_150920_d;
    
	public ItemTankWaste() {
		this.hasSubtypes = true;
        this.setMaxDamage(0);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        int j = MathHelper.clamp_int(p_77617_1_, 0, 8);
        return this.field_150920_d[j];
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.field_150920_d = new IIcon[9];

        for (int i = 0; i < field_150920_d.length; ++i)
        {
            this.field_150920_d[i] = p_94581_1_.registerIcon(RefStrings.MODID + ":tank_waste_" + i);
        }
    }
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for (int i = 0; i < 9; ++i)
        {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
        }
    }
    @Override
	public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        int i = MathHelper.clamp_int(p_77667_1_.getItemDamage(), 0, 9);
        return super.getUnlocalizedName() + "_" + i;
    }
}
