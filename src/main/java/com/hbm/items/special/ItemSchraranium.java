package com.hbm.items.special;

import com.hbm.config.GeneralConfig;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSchraranium extends Item {
	
	IIcon nikonium;

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(this.getIconString());
        this.nikonium = reg.registerIcon("hbm:ingot_nikonium");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
    	
    	if(GeneralConfig.enableBabyMode)
    		return this.nikonium;
		return this.itemIcon;
    }

    public String getItemStackDisplayName(ItemStack stack) {

    	if(GeneralConfig.enableBabyMode)
			return "Nikonium Ingot";
		else
			return super.getItemStackDisplayName(stack);
    }
}
