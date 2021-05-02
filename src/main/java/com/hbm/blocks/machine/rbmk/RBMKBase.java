package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class RBMKBase extends BlockDummyable {

	protected RBMKBase() {
		super(Material.iron);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	public boolean openInv(World world, int x, int y, int z, EntityPlayer player, int gui) {
		
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, gui, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		
		float height = 0.0F;
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos != null) {
			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
			
			if(te instanceof TileEntityRBMKBase) {
				
				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
				
				if(rbmk.hasLid()) {
					height += 0.25F;
				}
			}
		}
		
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY + height, z + this.maxZ);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(world), x, y, z, dir);
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(world), this, dir);
	}
	
	public int[] getDimensions(World world) {
		return new int[] {RBMKDials.getColumnHeight(world), 0, 0, 0, 0, 0};
	}

	public static int renderIDRods = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDPassive = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDControl = RenderingRegistry.getNextAvailableRenderId();
}
