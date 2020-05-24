package com.hbm.items.machine;

import java.util.List;
import com.hbm.handler.FluidTypeHandler.FluidType;
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
import net.minecraft.world.World;

public class ItemFluidIdentifier extends Item {
	
	IIcon overlayIcon;

    public ItemFluidIdentifier()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    /*@Override
	public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getItemDamage();
        return super.getUnlocalizedName() + "." + FluidType.getEnum(i).getName();
    }*/
    
    public ItemStack getContainerItem(ItemStack stack) {
    	return stack.copy();
    }
    
    public boolean hasContainerItem() {
    	return true;
    }
    
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return false;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < FluidType.values().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
    	
    	if(!(stack.getItem() instanceof ItemFluidIdentifier))
    		return;

		list.add("[CREATED USING TEMPLATE FOLDER]");
		list.add("");
    	list.add("Universal fluid identifier for:");
    	list.add("   " + I18n.format(FluidType.getEnum(stack.getItemDamage()).getUnlocalizedName()));
	}
	
	public static FluidType getType(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof ItemFluidIdentifier)
			return FluidType.getEnum(stack.getItemDamage());
		else
			return FluidType.NONE;
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f1, float f2, float f3)
    {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityFluidDuct) {
			TileEntityFluidDuct duct = (TileEntityFluidDuct)te;
			
			duct.type = FluidType.getEnum(stack.getItemDamage());
		}
		return false;
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

        this.overlayIcon = p_94581_1_.registerIcon("hbm:fluid_identifier_overlay");
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
