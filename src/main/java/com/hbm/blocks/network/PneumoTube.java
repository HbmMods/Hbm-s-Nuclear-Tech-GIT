package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.util.Compat;

import api.hbm.block.IToolable;
import api.hbm.fluidmk2.IFluidConnectorBlockMK2;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class PneumoTube extends BlockContainer implements IToolable, IFluidConnectorBlockMK2, ITooltipProvider {

	@SideOnly(Side.CLIENT) public IIcon baseIcon;
	@SideOnly(Side.CLIENT) public IIcon iconIn;
	@SideOnly(Side.CLIENT) public IIcon iconOut;
	@SideOnly(Side.CLIENT) public IIcon iconConnector;
	@SideOnly(Side.CLIENT) public IIcon iconStraight;
	@SideOnly(Side.CLIENT) public IIcon activeIcon;
	
	public boolean[] renderSides = new boolean[] {true, true, true, true, true, true};

	public PneumoTube() {
		super(Material.iron);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoTube();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);

		iconIn = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_in");
		iconOut = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_out");
		iconConnector = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_connector");
		iconStraight = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_straight");
		
		this.activeIcon = this.baseIcon = this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.activeIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return renderSides[side % 6];
	}
	
	public void resetRenderSides() {
		for(int i = 0; i < 6; i++) renderSides[i] = true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem() != null && ToolType.getType(player.getHeldItem()) == ToolType.SCREWDRIVER) return false;
		if(!player.isSneaking()) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityPneumoTube) {
				TileEntityPneumoTube tube = (TileEntityPneumoTube) tile;
				if(tube.isCompressor()) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		if(world.isRemote) return true;
		
		TileEntityPneumoTube tube = (TileEntityPneumoTube) world.getTileEntity(x, y, z);

		ForgeDirection rot = player.isSneaking() ? tube.ejectionDir : tube.insertionDir;
		ForgeDirection oth = player.isSneaking() ? tube.insertionDir : tube.ejectionDir;
		
		for(int i = 0; i < 7; i++) {
			rot = ForgeDirection.getOrientation((rot.ordinal() + 1) % 7);
			if(rot == ForgeDirection.UNKNOWN) break; //unknown is always valid, simply disables this part
			if(rot == oth) continue; //skip if both positions collide
			TileEntity tile = Compat.getTileStandard(world, x + rot.offsetX, y + rot.offsetY, z + rot.offsetZ);
			if(tile instanceof TileEntityPneumoTube) continue;
			if(tile instanceof IInventory) break; //valid if connected to an IInventory
		}
		
		if(player.isSneaking()) tube.ejectionDir = rot; else tube.insertionDir = rot;
		
		tube.markDirty();
		if(world instanceof WorldServer) ((WorldServer) world).getPlayerManager().markBlockForUpdate(x, y, z);
		
		return true;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		List<AxisAlignedBB> bbs = new ArrayList();
		
		double lower = 0.3125D;
		double upper = 0.6875D;
		
		bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + lower, x + upper, y + upper, z + upper));

		if(canConnectTo(world, x, y, z, Library.POS_X) || canConnectToAir(world, x, y, z, Library.POS_X)) bbs.add(AxisAlignedBB.getBoundingBox(x + upper, y + lower, z + lower, x + 1, y + upper, z + upper));
		if(canConnectTo(world, x, y, z, Library.NEG_X) || canConnectToAir(world, x, y, z, Library.NEG_X)) bbs.add(AxisAlignedBB.getBoundingBox(x, y + lower, z + lower, x + lower, y + upper, z + upper));
		if(canConnectTo(world, x, y, z, Library.POS_Y) || canConnectToAir(world, x, y, z, Library.POS_Y)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + upper, z + lower, x + upper, y + 1, z + upper));
		if(canConnectTo(world, x, y, z, Library.NEG_Y) || canConnectToAir(world, x, y, z, Library.NEG_Y)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y, z + lower, x + upper, y + lower, z + upper));
		if(canConnectTo(world, x, y, z, Library.POS_Z) || canConnectToAir(world, x, y, z, Library.POS_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + upper, x + upper, y + upper, z + 1));
		if(canConnectTo(world, x, y, z, Library.NEG_Z) || canConnectToAir(world, x, y, z, Library.NEG_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z, x + upper, y + upper, z + lower));

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

		float lower = 0.3125F;
		float upper = 0.6875F;
		
		TileEntity tile = world.getTileEntity(x, y, z);
		TileEntityPneumoTube tube = tile instanceof TileEntityPneumoTube ? (TileEntityPneumoTube) tile : null;

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X) || canConnectToAir(world, x, y, z, Library.NEG_X);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X) || canConnectToAir(world, x, y, z, Library.POS_X);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y) || canConnectToAir(world, x, y, z, Library.NEG_Y);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y) || canConnectToAir(world, x, y, z, Library.POS_Y);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z) || canConnectToAir(world, x, y, z, Library.NEG_Z);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z) || canConnectToAir(world, x, y, z, Library.POS_Z);
		
		if(tube != null) {
			nX |= tube.insertionDir == Library.NEG_X || tube.ejectionDir == Library.NEG_X;
			pX |= tube.insertionDir == Library.POS_X || tube.ejectionDir == Library.POS_X;
			nY |= tube.insertionDir == Library.NEG_Y || tube.ejectionDir == Library.NEG_Y;
			pY |= tube.insertionDir == Library.POS_Y || tube.ejectionDir == Library.POS_Y;
			nZ |= tube.insertionDir == Library.NEG_Z || tube.ejectionDir == Library.NEG_Z;
			pZ |= tube.insertionDir == Library.POS_Z || tube.ejectionDir == Library.POS_Z;
		}
		
		this.setBlockBounds(
				nX ? 0F : lower,
				nY ? 0F : lower,
				nZ ? 0F : lower,
				pX ? 1F : upper,
				pY ? 1F : upper,
				pZ ? 1F : upper);
	}

	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		TileEntity tile = world instanceof World ? Compat.getTileStandard((World) world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) : world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		return tile instanceof TileEntityPneumoTube;
	}

	public boolean canConnectToAir(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityPneumoTube tube = te instanceof TileEntityPneumoTube ? (TileEntityPneumoTube) te : null;
		if(tube != null) {
			if(!tube.isCompressor()) return false;
			if(tube.ejectionDir == dir || tube.insertionDir == dir) return false;
		}
		TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if(tile instanceof TileEntityPneumoTube) return false;
		return Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, Fluids.AIR);
	}
	
	@Override
	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		TileEntityPneumoTube tube = (TileEntityPneumoTube) world.getTileEntity(x, y, z);
		return tube != null && tube.isCompressor();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
