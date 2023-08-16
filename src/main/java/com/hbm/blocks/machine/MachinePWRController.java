package com.hbm.blocks.machine;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityPWRController;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachinePWRController extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public MachinePWRController(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPWRController();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":pwr_controller");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.blockIcon);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {

			TileEntityPWRController controller = (TileEntityPWRController) world.getTileEntity(x, y, z);
			
			if(!controller.assembled) {
				assemble(world, x, y, z);
			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			
			return true;
		} else {
			return false;
		}
	}

	private static HashMap<BlockPos, Block> assembly = new HashMap();
	private static HashMap<BlockPos, Block> fuelRods = new HashMap();
	private static boolean errored;
	private static final int maxSize = 1024;
	
	public void assemble(World world, int x, int y, int z) {
		assembly.clear();
		assembly.put(new BlockPos(x, y, z), this);
		
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();
		
		errored = false;
		floodFill(world, x + dir.offsetX, y, z + dir.offsetZ);
		
		if(fuelRods.size() == 0) errored = true;
		
		if(!errored) {
			for(Entry<BlockPos, Block> entry : assembly.entrySet()) {
				
				BlockPos pos = entry.getKey();
				Block block = entry.getValue();
				
				if(block != ModBlocks.pwr_controller) {
					
					if(block == ModBlocks.pwr_port) {
						world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.pwr_block, 1, 3);
					} else {
						world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.pwr_block, 0, 3);
					}
					
					TileEntityBlockPWR pwr = (TileEntityBlockPWR) world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
					pwr.block = block;
					pwr.coreX = x;
					pwr.coreY = y;
					pwr.coreZ = z;
					pwr.markDirty();
				}
			}
		}
		
		TileEntityPWRController controller = (TileEntityPWRController) world.getTileEntity(x, y, z);
		controller.assembled = !errored;
		
		assembly.clear();
	}
	
	private void floodFill(World world, int x, int y, int z) {
		
		BlockPos pos = new BlockPos(x, y, z);
		
		if(assembly.containsKey(pos)) return;
		if(assembly.size() >= maxSize) {
			errored = true;
			return;
		}
		
		Block block = world.getBlock(x, y, z);
		
		if(isValidCasing(block)) {
			assembly.put(pos, block);
			return;
		}
		
		if(isValidCore(block)) {
			assembly.put(pos, block);
			if(block == ModBlocks.pwr_fuel) fuelRods.put(pos, block);
			floodFill(world, x + 1, y, z);
			floodFill(world, x - 1, y, z);
			floodFill(world, x, y + 1, z);
			floodFill(world, x, y - 1, z);
			floodFill(world, x, y, z + 1);
			floodFill(world, x, y, z - 1);
			return;
		}
		
		errored = true;
	}
	
	private boolean isValidCore(Block block) {
		if(block == ModBlocks.pwr_fuel || block == ModBlocks.pwr_control || block == ModBlocks.pwr_channel || block == ModBlocks.pwr_heatex || block == ModBlocks.pwr_neutron_source) return true;
		return false;
	}
	
	private boolean isValidCasing(Block block) {
		if(block == ModBlocks.pwr_casing || block == ModBlocks.pwr_reflector || block == ModBlocks.pwr_port) return true;
		return false;
	}
}
