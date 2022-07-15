package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityDummy;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAMSEmitter extends BlockContainer implements IMultiblock {

	public BlockAMSEmitter(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityAMSEmitter();
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
			TileEntityAMSEmitter entity = (TileEntityAMSEmitter) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.AMS_EMITTER.ordinal(), world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.AMSEmitterDimension)) {
			MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.AMSEmitterDimension, ModBlocks.dummy_block_ams_emitter);

			DummyBlockAMSEmitter.safeBreak = true;
			world.setBlock(x, y + 5, z, ModBlocks.dummy_port_ams_emitter);
			TileEntity te = world.getTileEntity(x, y + 5, z);
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x, y + 5, z - 1, ModBlocks.dummy_port_ams_emitter);
			TileEntity te2 = world.getTileEntity(x, y + 5, z - 1);
			if(te instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te2;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x + 1, y + 5, z, ModBlocks.dummy_port_ams_emitter);
			TileEntity te3 = world.getTileEntity(x + 1, y + 5, z);
			if(te3 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te3;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x - 1, y + 5, z, ModBlocks.dummy_port_ams_emitter);
			TileEntity te4 = world.getTileEntity(x - 1, y + 5, z);
			if(te4 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te4;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			world.setBlock(x, y + 5, z + 1, ModBlocks.dummy_port_ams_emitter);
			TileEntity te5 = world.getTileEntity(x, y + 5, z + 1);
			if(te5 instanceof TileEntityDummy) {
				TileEntityDummy dummy = (TileEntityDummy)te5;
				dummy.targetX = x;
				dummy.targetY = y;
				dummy.targetZ = z;
			}
			DummyBlockAMSEmitter.safeBreak = false;
			
		} else
			world.func_147480_a(x, y, z, true);
	}

}
