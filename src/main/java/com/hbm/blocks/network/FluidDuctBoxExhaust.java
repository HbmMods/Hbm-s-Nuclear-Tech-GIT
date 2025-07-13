package com.hbm.blocks.network;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeExhaust;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class FluidDuctBoxExhaust extends FluidDuctBox {

	public FluidDuctBoxExhaust(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPipeExhaust();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);

		iconStraight = new IIcon[1];
		iconEnd = new IIcon[1];
		iconCurveTL = new IIcon[1];
		iconCurveTR = new IIcon[1];
		iconCurveBL = new IIcon[1];
		iconCurveBR = new IIcon[1];
		iconJunction = new IIcon[1][5];

		iconStraight[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_straight");
		iconEnd[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_end");
		iconCurveTL[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_curve_tl");
		iconCurveTR[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_curve_tr");
		iconCurveBL[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_curve_bl");
		iconCurveBR[0] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_curve_br");
		for(int i = 0; i < 5; i++) iconJunction[0][i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_exhaust_junction_" + i);
	}

	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir, TileEntity tile) {
		return Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, Fluids.SMOKE) ||
				Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, Fluids.SMOKE_LEADED) ||
				Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, Fluids.SMOKE_POISON);
	}

	@Override
	public int getSubCount() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 15; i += 3) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		List<String> text = new ArrayList();
		text.add(Fluids.SMOKE.getLocalizedName());
		text.add(Fluids.SMOKE_LEADED.getLocalizedName());
		text.add(Fluids.SMOKE_POISON.getLocalizedName());
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
