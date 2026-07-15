package com.hbm.blocks.machine.pile;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.pile.TileEntityPileBaseMK2;
import com.hbm.tileentity.machine.pile.TileEntityPileCore;

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
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPile extends BlockContainer implements IBlockCT, IToolable {

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
	
	@SideOnly(Side.CLIENT) protected IIcon iconTop;

	public BlockPile() { super(Material.iron); }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta == META_CORE) return new TileEntityPileCore();
		return new TileEntityPileBaseMK2();
	}

	@Override public int getRenderType() { return CT.renderID; }
	@Override public Item getItemDropped(int i, Random rand, int j)  { return null; }

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recTop;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":pile_block_top");
		this.rec = IBlockCT.primeReceiver(reg, this.blockIcon.getIconName(), this.blockIcon);
		this.recTop = IBlockCT.primeReceiver(reg, this.iconTop.getIconName(), this.iconTop);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 0 || side == 1) return recTop.fragCache;
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
}
