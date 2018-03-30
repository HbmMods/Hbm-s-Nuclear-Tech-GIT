package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityCelPrime;
import com.hbm.tileentity.bomb.TileEntityCelPrimeBattery;
import com.hbm.tileentity.bomb.TileEntityCelPrimePort;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTanks;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTerminal;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CelPrimePart extends BlockContainer {

	public CelPrimePart(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.cel_prime_terminal)
			return new TileEntityCelPrimeTerminal();
		if(this == ModBlocks.cel_prime_battery)
			return new TileEntityCelPrimeBattery();
		if(this == ModBlocks.cel_prime_port)
			return new TileEntityCelPrimePort();
		if(this == ModBlocks.cel_prime_tanks)
			return new TileEntityCelPrimeTanks();
		
		return null;
	}
	
	@Override
	public int getRenderType(){
		return -1;
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
}
