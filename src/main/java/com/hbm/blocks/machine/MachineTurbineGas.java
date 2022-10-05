package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

import com.hbm.blocks.BlockDummyable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTurbineGas extends BlockDummyable{
	
	public MachineTurbineGas(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) 
			return new TileEntityMachineTurbineGas();
		if(meta >= 6) 
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 1, 1, 4, 5 };
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_turbinegas, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		this.makeExtra(world, x - dir.offsetX * 1 - dir.offsetZ * 4, y + 1, z + dir.offsetX * 4 - dir.offsetZ * 1); //power
		this.makeExtra(world, x - dir.offsetZ * 2, y, z + dir.offsetX * 2); //gas in
		this.makeExtra(world, x - dir.offsetX * 2 - dir.offsetZ * 2, y, z + dir.offsetX * 2 - dir.offsetZ * 2); //gas in
		this.makeExtra(world, x + dir.offsetZ * 4, y, z - dir.offsetX * 4); //wa'er in
		this.makeExtra(world, x + dir.offsetZ * 4 - dir.offsetX * 2, y, z - dir.offsetX * 4 - dir.offsetZ * 2); //wa'er in
		this.makeExtra(world, x + dir.offsetZ * 5 - dir.offsetX * 1, y + 1, z - dir.offsetX * 5 - dir.offsetZ * 1); //steam out
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_turbinegas);
	}
}
