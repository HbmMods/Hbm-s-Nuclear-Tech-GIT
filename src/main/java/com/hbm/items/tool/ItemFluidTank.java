package com.hbm.items.tool;

import java.util.List;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFluidTank extends Item {
	
	IIcon overlayIcon;

    public ItemFluidTank()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 1; i < FluidType.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String s1 = ("" + StatCollector.translateToLocal(FluidType.getEnum(stack.getItemDamage()).getUnlocalizedName())).trim();

        if (s1 != null)
        {
            s = s + " " + s1;
        }

        return s;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);

        if(this == ModItems.fluid_tank_full)
        	this.overlayIcon = p_94581_1_.registerIcon("hbm:fluid_tank_overlay");
        if(this == ModItems.fluid_barrel_full)
        	this.overlayIcon = p_94581_1_.registerIcon("hbm:fluid_barrel_overlay");
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
    {
        return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int p_82790_2_)
    {
        if (p_82790_2_ == 0)
        {
            return 16777215;
        }
        else
        {
            int j = FluidType.getEnum(stack.getItemDamage()).getMSAColor();

            if (j < 0)
            {
                j = 16777215;
            }

            return j;
        }
    }

}
