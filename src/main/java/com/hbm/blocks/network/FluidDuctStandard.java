package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.test.TestPipe;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class FluidDuctStandard extends FluidDuctBase implements IBlockMulti, ILookOverlay {

	@SideOnly(Side.CLIENT)
	protected IIcon[] icon;
	@SideOnly(Side.CLIENT)
	protected IIcon[] overlay;

	public FluidDuctStandard(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		icon = new IIcon[3];
		overlay = new IIcon[3];

		this.icon[0] = iconRegister.registerIcon(this.getTextureName());
		this.icon[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver");
		this.icon[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored");
		this.overlay[0] = iconRegister.registerIcon(this.getTextureName() + "_overlay");
		this.overlay[1] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_silver_overlay");
		this.overlay[2] = iconRegister.registerIcon(RefStrings.MODID + ":pipe_colored_overlay");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.icon[rectify(metadata)] : this.overlay[rectify(metadata)];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 3; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		return rectify(meta);
	}

	@Override
	public int getRenderType() {
		return TestPipe.renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityPipeBaseNT))
			return;
		
		TileEntityPipeBaseNT duct = (TileEntityPipeBaseNT) te;
		
		List<String> text = new ArrayList();
		text.add("&[" + duct.getType().getColor() + "&]" +I18nUtil.resolveKey(duct.getType().getUnlocalizedName()));
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
