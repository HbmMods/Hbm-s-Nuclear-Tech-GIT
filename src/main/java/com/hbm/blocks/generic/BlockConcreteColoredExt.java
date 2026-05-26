package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockConcreteColoredExt extends BlockEnumMulti implements ITooltipProvider {

	public BlockConcreteColoredExt(Material mat) {
		super(mat, EnumConcreteType.class, true, true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(meta == EnumConcreteType.MACHINE_STRIPE.ordinal() && (side == 0 || side == 1)) {
			return super.getIcon(side, EnumConcreteType.MACHINE.ordinal());
		}
		
		return super.getIcon(side, meta);
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("tile.nospawn"));
	}
	
	public enum EnumConcreteType {
		MACHINE,
		MACHINE_STRIPE,
		INDIGO,
		PURPLE,
		PINK,
		HAZARD,
		SAND,
		BRONZE
	}
}
