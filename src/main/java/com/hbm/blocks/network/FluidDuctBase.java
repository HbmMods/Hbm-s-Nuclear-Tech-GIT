package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IAnalyzable;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemFluidIDMulti;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidDuctBase extends BlockContainer implements IBlockFluidDuct, IAnalyzable {

	public FluidDuctBase(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPipeBaseNT();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) player.getHeldItem().getItem();
			FluidType type = id.getType(world, x, y, z, player.getHeldItem());
			
			if(!HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.TOOL_CTRL) && !player.isSneaking()) {
				
				TileEntity te = world.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityPipeBaseNT) {
					TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;

					if(HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.TOOL_ALT)) {
						Item item = player.getHeldItem().getItem();
						if (item instanceof ItemFluidIDMulti) {
							if (id.getType(world, x, y, z, player.getHeldItem()) != pipe.getType()) {
								ItemFluidIDMulti.setType(player.getHeldItem(), pipe.getType(), true);
								world.playSoundAtEntity(player, "random.orb", 0.25F, 0.75F);
								return true;
							}
						}
					}

					if(pipe.getType() != type) {
						pipe.setType(type);
						return true;
					}
				}
			} else {
				
				TileEntity te = world.getTileEntity(x, y, z);

				if(te instanceof TileEntityPipeBaseNT) {
					TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;

					if(HbmPlayerProps.getData(player).getKeyPressed(HbmKeybinds.EnumKeybind.TOOL_ALT)) {
						Item item = player.getHeldItem().getItem();
						if (item instanceof ItemFluidIDMulti) {
							if (id.getType(world, x, y, z, player.getHeldItem()) != pipe.getType()) {
								ItemFluidIDMulti.setType(player.getHeldItem(), pipe.getType(), true);
								world.playSoundAtEntity(player, "random.orb", 0.25F, 0.75F);
								return true;
							}
						}
					}

					changeTypeRecursively(world, x, y, z, pipe.getType(), type, 64);
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void changeTypeRecursively(World world, int x, int y, int z, FluidType prevType, FluidType type, int loopsRemaining) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			
			if(pipe.getType() == prevType && pipe.getType() != type) {
				pipe.setType(type);
				
				if(loopsRemaining > 0) {
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						Block b = world.getBlock(x, y, z);
						
						if(b instanceof IBlockFluidDuct) {
							((IBlockFluidDuct) b).changeTypeRecursively(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, prevType, type, loopsRemaining - 1);
						}
					}
				}
			}
		}
	}

	@Override
	public List<String> getDebugInfo(World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityPipeBaseNT) {
			TileEntityPipeBaseNT pipe = (TileEntityPipeBaseNT) te;
			FluidType type = pipe.getType();
			
			if(type != null) {
				
				IPipeNet net = pipe.getPipeNet(type);
				
				if(net instanceof PipeNet) {
					PipeNet pipeNet = (PipeNet) net;
					
					List<String> debug = new ArrayList();
					debug.add("=== DEBUG START ===");
					debug.addAll(pipeNet.debug);
					debug.add("=== DEBUG END ===");
					debug.add("Links: " + pipeNet.getLinks().size());
					debug.add("Subscribers: " + pipeNet.getSubscribers().size());
					debug.add("Transfer: " + pipeNet.getTotalTransfer());
					return debug;
				}
			}
		}
		
		return null;
	}
}
