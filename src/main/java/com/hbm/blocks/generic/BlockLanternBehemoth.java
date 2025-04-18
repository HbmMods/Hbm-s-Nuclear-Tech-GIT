package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.deco.TileEntityLanternBehemoth;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockLanternBehemoth extends BlockDummyable implements IToolable, ILookOverlay {

	public BlockLanternBehemoth() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityLanternBehemoth();
		return null;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.TORCH) return false;
		boolean didRepair = IRepairable.tryRepairMultiblock(world, x, y, z, this, player);
		
		if(didRepair) {
			HbmPlayerProps data = HbmPlayerProps.getData(player);
			if(data.reputation < 25) data.reputation++;
		}
		
		return didRepair;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		IRepairable.addGenericOverlay(event, world, x, y, z, this);
	}
}
