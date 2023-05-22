package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.IPersistentNBT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block) {
		super(block);
		
		if(block instanceof IBlockMulti) {
			this.setMaxDamage(0);
			this.setHasSubtypes(true);
		}
	}
	
	@Override
	public int getMetadata(int meta) {
		if(field_150939_a instanceof IBlockMulti)
			return meta;
		else
			return super.getMetadata(meta);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if(field_150939_a instanceof BlockMulti) {
			return ((BlockMulti)field_150939_a).getUnlocalizedName(stack);
		} else {
			return super.getUnlocalizedName(stack);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		if(field_150939_a instanceof ITooltipProvider) {
			((ITooltipProvider) field_150939_a).addInformation(stack, player, list, bool);
		}
		
		if(field_150939_a instanceof IPersistentInfoProvider && stack.hasTagCompound() && stack.getTagCompound().hasKey(IPersistentNBT.NBT_PERSISTENT_KEY)) {
			NBTTagCompound data = stack.getTagCompound().getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
			((IPersistentInfoProvider) field_150939_a).addInformation(stack, data, player, list, bool);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.field_150939_a.getIcon(1, meta); //fuck you mojang
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		
		if(field_150939_a instanceof ITooltipProvider) {
			return ((ITooltipProvider) field_150939_a).getRarity(stack);
		}
		
		return EnumRarity.common;
	}
}
