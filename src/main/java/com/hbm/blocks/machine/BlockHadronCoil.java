package com.hbm.blocks.machine;

import java.util.List;
import java.util.Locale;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockHadronCoil extends Block implements IBlockCT, ITooltipProvider {
	
	public int factor;

	public BlockHadronCoil(Material mat, int factor) {
		super(mat);
		this.factor = factor;
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}

	@SideOnly(Side.CLIENT)
	public CTStitchReceiver rec;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.rec = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		return rec.fragCache;
	}
	
	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return block instanceof BlockHadronCoil;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(I18nUtil.resolveKey("info.coil") + ": " + String.format(Locale.US, "%,d", factor));
	}
}
