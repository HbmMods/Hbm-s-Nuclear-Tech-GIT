package com.hbm.blocks.network;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

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
	/*
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
	    Block block = world.getBlock(x, y, z); // Get the block at the specified coordinates
	    int blockMetadata = world.getBlockMetadata(x, y, z); // Get the metadata of the block at the specified coordinates
	    TileEntity tileEntity = world.getTileEntity(x, y, z); // Get the tile entity at the specified coordinates

	        TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) tileEntity;

	        // Get the metadata (FluidType) from the pipe
	        FluidType fluidType = pipe.getType();
	        int metadata = fluidType.getID();

	        // Create an ItemStack with the item and metadata
	        ItemStack itemStack = new ItemStack(ModItems.fluid_duct, 1, metadata);
	        System.out.println(metadata);
	        System.out.println(itemStack);

	        return new ItemStack(ModItems.fluid_duct, 1, metadata).getItem();
	}
	*/

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) tileEntity;
			FluidType fluidType = pipe.getType();
			int retadata = fluidType.getID(); // florf

			return new ItemStack(ModItems.fluid_duct, 1, retadata);
		}
		return super.getPickBlock(target, world, x, y, z, player);
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
		return 3;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		List<AxisAlignedBB> bbs = new ArrayList();

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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
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
		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
