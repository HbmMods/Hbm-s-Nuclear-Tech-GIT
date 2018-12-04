package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityForceField;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineForceField extends BlockContainer {

	public MachineForceField(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityForceField();
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_forcefield, world, x, y, z);
			return true;
		} else {
			return true;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		TileEntityForceField te = (TileEntityForceField)world.getTileEntity(x, y, z);
		
		if(te.isOn && te.cooldown == 0 && te.power > 0) {
			for(int i = 0; i < 4; i++) {
				float f = x;
				float f1 = y + 2F;
				float f2 = z;
				float f3 = 0.52F;
				float f4 = rand.nextFloat();
				float f5 = rand.nextFloat();
	
				if(te.color == 0xFF0000)
					world.spawnParticle("lava", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
				else
					world.spawnParticle("reddust", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
			}
		} else if(te.cooldown > 0) {
			for(int i = 0; i < 4; i++) {
				float f = x;
				float f1 = y + 2F;
				float f2 = z;
				float f3 = 0.52F;
				float f4 = rand.nextFloat();
				float f5 = rand.nextFloat();
	
				world.spawnParticle("smoke", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
			}
		}
	}

}
