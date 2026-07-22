package com.hbm.blocks.machine.pile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.pile.TileEntityPileBaseMK2;
import com.hbm.tileentity.machine.pile.TileEntityPileCore;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPile extends BlockContainer implements IBlockCT, IToolable, ILookOverlay {

	/** Blank dummy, the pile is mostly composed of that */
	public static final int META_DUMMY		= 0;
	/** The core, gets its own TE and runs the simulation, only one per pile */
	public static final int META_CORE		= 1;
	/** Channel mid segment for channel intersect checks */
	public static final int META_CHANNEL	= 2;
	/** Startpoint of the channel */
	public static final int META_FUEL_IN	= 3;
	/** Endpoint of the fuel channel */
	public static final int META_FUEL_OUT	= 4;
	/** Startpoint of the ventilation channel */
	public static final int META_AIR_IN		= 5;
	/** Endpoint of the ventilation channel */
	public static final int META_AIR_OUT	= 6;
	/** Control rod channel */
	public static final int META_CONTROL	= 7;
	/** Edge of our pile "cube" to prevent channels from being drilled there */
	public static final int META_EDGE		= 8;

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recTop;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recChanIn;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recChanOut;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recCon;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recCore;
	
	@SideOnly(Side.CLIENT) protected IIcon iconTop;

	public BlockPile() { super(Material.iron); }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta == META_CORE) return new TileEntityPileCore();
		return new TileEntityPileBaseMK2();
	}

	@Override public int getRenderType() { return CT.renderID; }
	@Override public Item getItemDropped(int i, Random rand, int j)  { return null; }

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":pile_block_top");
		
		this.rec = IBlockCT.primeReceiver(reg, this.blockIcon.getIconName(), this.blockIcon);
		this.recTop = IBlockCT.primeReceiver(reg, this.iconTop.getIconName(), this.iconTop);
		this.recChanIn = IBlockCT.primeReceiver(reg, RefStrings.MODID + ":pile_block_input", this.blockIcon);
		this.recChanOut = IBlockCT.primeReceiver(reg, RefStrings.MODID + ":pile_block_output", this.blockIcon);
		this.recCon = IBlockCT.primeReceiver(reg, RefStrings.MODID + ":pile_block_control_top", this.iconTop);
		this.recCore = IBlockCT.primeReceiver(reg, RefStrings.MODID + ":pile_block_core", this.iconTop);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		if(side == 0 || side == 1) return meta == META_CONTROL ? recCon.fragCache : recTop.fragCache;
		if(meta == META_FUEL_IN || meta == META_AIR_IN) return recChanIn.fragCache;
		if(meta == META_FUEL_OUT || meta == META_AIR_OUT) return recChanOut.fragCache;
		if(meta == META_CORE) return recCore.fragCache;
		return rec.fragCache;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityPileBaseMK2) {
			TileEntityPileBaseMK2 pile = (TileEntityPileBaseMK2) tile;
			world.removeTileEntity(x, y, z);
			if(pile.coreY >= 0) world.setBlock(x, y, z, ModBlocks.pile_brick);
			
			TileEntityPileCore core = pile.getCore();
			if(core != null && !core.isInvalid()) core.destroy();
			
		} else {
			world.removeTileEntity(x, y, z);
			world.setBlock(x, y, z, ModBlocks.pile_brick);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool == tool.HAND_DRILL) {
			
			TileEntity tile = world.getTileEntity(x, y, z);
			
			if(tile instanceof TileEntityPileCore || world.getBlockMetadata(x, y, z) == META_CORE) {
				MachinePWRController.sendError(world, x, y, z, "Cannot intersect core", player);
				return false;
			}
			
			if(tile instanceof TileEntityPileBaseMK2) {
				if(world.isRemote) return true;
				TileEntityPileCore core = ((TileEntityPileBaseMK2) tile).getCore();
				if(core != null) {
					ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
					return core.drillChannel(x, y, z, dir, player);
				}
			}
			
			MachinePWRController.sendError(world, x, y, z, "No core found", player);
		}
		
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		List<String> text = new ArrayList();
		if(meta == META_FUEL_IN) text.add("Fuel Loading Port");
		if(meta == META_FUEL_OUT) text.add("Fuel Ejection Port");
		if(meta == META_AIR_IN) text.add("Air Inlet");
		if(meta == META_AIR_OUT) text.add("Air Outlet");
		if(meta == META_CONTROL) text.add("Control Rod Channel");
		if(!text.isEmpty()) ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
