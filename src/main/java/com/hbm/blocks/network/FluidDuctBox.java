package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidDuctBox extends FluidDuctBase implements IBlockMulti, ILookOverlay {

	@SideOnly(Side.CLIENT) protected IIcon[] iconStraight;
	@SideOnly(Side.CLIENT) protected IIcon[] iconEnd;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveTL;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveTR;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveBL;
	@SideOnly(Side.CLIENT) protected IIcon[] iconCurveBR;
	@SideOnly(Side.CLIENT) protected IIcon[] iconJunction;
	
	private static final String[] materials = new String[] { "silver", "copper", "white" };

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
		for(int i = 0; i < 15; ++i) {
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		
		List<AxisAlignedBB> bbs = new ArrayList();

		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			FluidType type = pipe.getType();

			double lower = 0.125D;
			double upper = 0.875D;
			double jLower = 0.0625D;
			double jUpper = 0.9375D; //TODO
			int meta = world.getBlockMetadata(x, y, z);
			
			for(int i = 2; i < 13; i += 3) {
				
				if(meta > i) {
					lower += 0.0625D;
					upper -= 0.0625D;
					jLower += 0.0625D;
					jUpper -= 0.0625D;
				}
			}

			boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, type);
			boolean pX = canConnectTo(world, x, y, z, Library.POS_X, type);
			boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, type);
			boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, type);
			boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, type);
			boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, type);
			int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
			
			if(mask == 0) {
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.6875D, y + 0.3125D, z + 0.3125D, x + 1.0D, y + 0.6875D, z + 0.6875D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + 0.3125D, z + 0.3125D, x + 0.3125D, y + 0.6875D, z + 0.6875D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.6875D, z + 0.3125D, x + 0.6875D, y + 1.0D, z + 0.6875D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.0D, z + 0.3125D, x + 0.6875D, y + 0.3125D, z + 0.6875D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.6875D, x + 0.6875D, y + 0.6875D, z + 1.0D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.0D, x + 0.6875D, y + 0.6875D, z + 0.3125D));
			} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + 0.3125D, z + 0.3125D, x + 1.0D, y + 0.6875D, z + 0.6875D));
			} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.0D, z + 0.3125D, x + 0.6875D, y + 1.0D, z + 0.6875D));
			} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.0D, x + 0.6875D, y + 0.6875D, z + 1.0D));
			} else {
				
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.3125D, x + 0.6875D, y + 0.6875D, z + 0.6875D));
		
				if(pX) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.6875D, y + 0.3125D, z + 0.3125D, x + 1.0D, y + 0.6875D, z + 0.6875D));
				if(nX) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + 0.3125D, z + 0.3125D, x + 0.3125D, y + 0.6875D, z + 0.6875D));
				if(pY) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.6875D, z + 0.3125D, x + 0.6875D, y + 1.0D, z + 0.6875D));
				if(nY) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.0D, z + 0.3125D, x + 0.6875D, y + 0.3125D, z + 0.6875D));
				if(pZ) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.6875D, x + 0.6875D, y + 0.6875D, z + 1.0D));
				if(nZ) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.3125D, y + 0.3125D, z + 0.0D, x + 0.6875D, y + 0.6875D, z + 0.3125D));
			}
		}
		
		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			FluidType type = pipe.getType();

			boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, type);
			boolean pX = canConnectTo(world, x, y, z, Library.POS_X, type);
			boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, type);
			boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, type);
			boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, type);
			boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, type);
			int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
			
			if(mask == 0) {
				this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
			} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
				this.setBlockBounds(0F, 0.3125F, 0.3125F, 1F, 0.6875F, 0.6875F);
			} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
				this.setBlockBounds(0.3125F, 0F, 0.3125F, 0.6875F, 1F, 0.6875F);
			} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
				this.setBlockBounds(0.3125F, 0.3125F, 0F, 0.6875F, 0.6875F, 1F);
			} else {
				
				this.setBlockBounds(
						nX ? 0F : 0.3125F,
						nY ? 0F : 0.3125F,
						nZ ? 0F : 0.3125F,
						pX ? 1F : 0.6875F,
						pY ? 1F : 0.6875F,
						pZ ? 1F : 0.6875F);
			}
		}
	}
	
	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir, FluidType type) {
		return Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, type);
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
	
	public static int cachedColor = 0xffffff;
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return cachedColor;
	}
}
