package com.hbm.blocks.machine;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class BlockHadronCooler extends BlockMulti implements ITooltipProvider {
	
	private IIcon[] icons = new IIcon[getSubCount()];

	public BlockHadronCooler(Material mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons[0] = reg.registerIcon(RefStrings.MODID + ":hadron_cooler");
		icons[1] = reg.registerIcon(RefStrings.MODID + ":hadron_cooler_mk2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return icons[this.rectify(metadata)];
	}

	@Override
	public int getSubCount() {
		return 2;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = this.rectify(stack.getItemDamage());
		
		if(meta == 1) return this.getUnlocalizedName() + "_mk2";
		
		return this.getUnlocalizedName();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray(this.getUnlocalizedName(stack) + ".desc")) list.add(EnumChatFormatting.YELLOW + s);
		} else {
			list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
					EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
					EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
	}
}
