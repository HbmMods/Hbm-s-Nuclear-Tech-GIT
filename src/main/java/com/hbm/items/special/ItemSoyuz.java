package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemSoyuz extends Item {
	
	IIcon[] icons = new IIcon[3];
	
	public ItemSoyuz() {
        this.setHasSubtypes(true);
	}
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
    	
    	for(int i = 0; i < icons.length; i++)
    		list.add(new ItemStack(item, 1, i));
    }
    
    @Override
	public EnumRarity getRarity(ItemStack stack) {
    	
    	if(stack.getItemDamage() == 0)
    		return EnumRarity.uncommon;
    	
    	if(stack.getItemDamage() == 1)
    		return EnumRarity.rare;
    	
    	if(stack.getItemDamage() == 2)
    		return EnumRarity.epic;
    	
		return EnumRarity.common;
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add("Skin:");
		
		switch(stack.getItemDamage()) {
		case 0: list.add(EnumChatFormatting.GOLD + "Original"); break;
		case 1: list.add(EnumChatFormatting.BLUE + "Luna Space Center"); break;
		case 2: list.add(EnumChatFormatting.GREEN + "Post War"); break;
		}
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
    	for(int i = 0; i < icons.length; i++) {
    		icons[i] = reg.registerIcon(RefStrings.MODID + ":soyuz_" + i);
    	}
    }
	
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, icons.length - 1);
        return icons[j];
    }

}
