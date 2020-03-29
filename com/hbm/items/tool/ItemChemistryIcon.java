package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemChemistryTemplate.EnumChemistryTemplate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class ItemChemistryIcon extends Item {
	
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemChemistryIcon()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(ModItems.chemistry_template.getUnlocalizedName() + ".name")).trim();
        String s1 = ("" + StatCollector.translateToLocal("chem." + EnumChemistryTemplate.getEnum(stack.getItemDamage()).name())).trim();

        if (s1 != null)
        {
            s = s + " " + s1;
        }

        return s;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < EnumChemistryTemplate.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        this.icons = new IIcon[EnumChemistryTemplate.values().length];

        for (int i = 0; i < icons.length; ++i)
        {
            this.icons[i] = reg.registerIcon("hbm:chem_icon_" + EnumChemistryTemplate.getEnum(i).name());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        int j = MathHelper.clamp_int(i, 0, icons.length - 1);
        return this.icons[j];
    }

}
