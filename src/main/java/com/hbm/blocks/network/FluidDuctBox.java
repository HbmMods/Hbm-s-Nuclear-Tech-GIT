package com.hbm.blocks.network;

import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import cpw.mods.fml.client.registry.RenderingRegistry;
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

public class FluidDuctBox extends FluidDuctBase implements IBlockMulti {

	@SideOnly(Side.CLIENT) protected IIcon[] iconStraight;
	@SideOnly(Side.CLIENT) protected IIcon[] iconEnd;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveTL;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveTR;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveBL;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveBR;
	@SideOnly(Side.CLIENT) protected IIcon[] iconJunction;
	
	private static final String[] materials = new String[] { "silver", "copper" };

	public FluidDuctBox(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);

		int count = materials.length;
		iconStraight = new IIcon[count];
		iconEnd = new IIcon[count];
		iconCurveTL = new IIcon[count];
		iconCurveTR = new IIcon[count];
		iconCurveBL = new IIcon[count];
		iconCurveBR = new IIcon[count];
		iconJunction = new IIcon[count];

		for(int i = 0; i < count; i++) {
			iconStraight[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_straight");
			iconEnd[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_end");
			iconCurveTL[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_curve_tl");
			iconCurveTR[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_curve_tr");
			iconCurveBL[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_curve_bl");
			iconCurveBR[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_curve_br");
			iconJunction[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + materials[i] + "_junction");
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		FluidType type = Fluids.NONE;
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			type = pipe.getType();
		}
		
		boolean pX = Library.canConnectFluid(world, x + 1, y, z, Library.NEG_X, type);
		boolean nX = Library.canConnectFluid(world, x - 1, y, z, Library.POS_X, type);
		boolean pY = Library.canConnectFluid(world, x, y + 1, z, Library.NEG_Y, type);
		boolean nY = Library.canConnectFluid(world, x, y - 1, z, Library.POS_Y, type);
		boolean pZ = Library.canConnectFluid(world, x, y, z + 1, Library.NEG_Z, type);
		boolean nZ = Library.canConnectFluid(world, x, y, z - 1, Library.POS_Z, type);
		
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);
		
		int m = rectify(world.getBlockMetadata(x, y, z));
		
		if((mask & 0b001111) == 0 && mask > 0) {
			return (side == 4 || side == 5) ? iconEnd[m] : iconStraight[m];
		} else if((mask & 0b111100) == 0 && mask > 0) {
			return (side == 2 || side == 3) ? iconEnd[m] : iconStraight[m];
		} else if((mask & 0b110011) == 0 && mask > 0) {
			return (side == 0 || side == 1) ? iconEnd[m] : iconStraight[m];
		} else if(count == 2) {

			if(side == 0 && nY || side == 1 && pY || side == 2 && nZ || side == 3 && pZ || side == 4 && nX || side == 5 && pX)
				return iconEnd[m];
			if(side == 1 && nY || side == 0 && pY || side == 3 && nZ || side == 2 && pZ || side == 5 && nX || side == 4 && pX)
				return iconStraight[m];

			if(nY && pZ) return side == 4 ? iconCurveBR[m] : iconCurveBL[m];
			if(nY && nZ) return side == 5 ? iconCurveBR[m] : iconCurveBL[m];
			if(nY && pX) return side == 3 ? iconCurveBR[m] : iconCurveBL[m];
			if(nY && nX) return side == 2 ? iconCurveBR[m] : iconCurveBL[m];
			if(pY && pZ) return side == 4 ? iconCurveTR[m] : iconCurveTL[m];
			if(pY && nZ) return side == 5 ? iconCurveTR[m] : iconCurveTL[m];
			if(pY && pX) return side == 3 ? iconCurveTR[m] : iconCurveTL[m];
			if(pY && nX) return side == 2 ? iconCurveTR[m] : iconCurveTL[m];

			if(pX && nZ) return side == 0 ? iconCurveTR[m] : iconCurveTR[m];
			if(pX && pZ) return side == 0 ? iconCurveBR[m] : iconCurveBR[m];
			if(nX && nZ) return side == 0 ? iconCurveTL[m] : iconCurveTL[m];
			if(nX && pZ) return side == 0 ? iconCurveBL[m] : iconCurveBL[m];
			
			return iconJunction[m];
		}
		
		return iconJunction[m];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 2; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	public int damageDropped(int meta) {
		return rectify(meta);
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
		return 2;
	}
}
