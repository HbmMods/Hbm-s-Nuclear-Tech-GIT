package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ICustomBlockHighlight;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityCargoElevator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCargoElevator extends BlockDummyable {

	public BlockCargoElevator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityCargoElevator();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		int[] pos = ((BlockDummyable) ModBlocks.cargo_elevator).findCore(world, x, y, z);
		if(pos != null) {
			TileEntityCargoElevator elevator = (TileEntityCargoElevator) world.getTileEntity(pos[0], pos[1], pos[2]);

			x = pos[0];
			y = pos[1];
			z = pos[2];
			
			// due to the collisions being really fucking weird, we have to add custom elevator extension too
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(this)) {
				boolean replacable = true;
				for(int i = x - 1; i < x + 2; i++) for(int j = z - 1; j < z + 2; j++) {
					if(!world.getBlock(i, y + elevator.height + 1, j).isReplaceable(world, i, y + elevator.height + 1, j)) {
						replacable = false;
						break;
					}
				}
				
				if(replacable) {
					for(int i = x - 1; i < x + 2; i++) for(int j = z - 1; j < z + 2; j++) {
						world.setBlock(i, y + elevator.height + 1, j, ModBlocks.cargo_elevator, 1, 3);
					}
					elevator.height++;
					elevator.markDirty();
					if(!player.capabilities.isCreativeMode) player.getHeldItem().stackSize--;
				}
			} else {
				elevator.toggleElevator();
			}
		}
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.999F, 1.0F); //for some fucking reason setting maxY to something that isn't 1 magically fixes item collisions
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;

		x = pos[0];
		y = pos[1];
		z = pos[2];
		
		TileEntityCargoElevator elevator = (TileEntityCargoElevator) world.getTileEntity(x, y, z);
		if(elevator == null) return;

		for(AxisAlignedBB aabb : getAABBs(elevator, x, y, z)) {
			if(entityBounding.intersectsWith(aabb)) list.add(aabb);
		}
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return null;

		TileEntityCargoElevator elevator = (TileEntityCargoElevator) world.getTileEntity(pos[0], pos[1], pos[2]);
		if(elevator == null) return null;

		for(AxisAlignedBB aabb : getAABBs(elevator, pos[0], pos[1], pos[2])) {

			MovingObjectPosition intercept = aabb.calculateIntercept(startVec, endVec);
			if(intercept != null) {
				return new MovingObjectPosition(x, y, z, intercept.sideHit, intercept.hitVec);
			}
		}
		
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(DrawBlockHighlightEvent event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;

		x = pos[0];
		y = pos[1];
		z = pos[2];
		
		TileEntityCargoElevator elevator = (TileEntityCargoElevator) world.getTileEntity(x, y, z);
		if(elevator == null) return;

		EntityPlayer player = event.player;
		float interp = event.partialTicks;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;
		float exp = 0.002F;

		ForgeDirection rot = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - offset).getRotation(ForgeDirection.UP);

		ICustomBlockHighlight.setup();
		for(AxisAlignedBB aabb : getAABBs(elevator, x, y, z)) RenderGlobal.drawOutlinedBoundingBox(aabb.expand(exp, exp, exp).getOffsetBoundingBox(-dX, -dY, -dZ), -1);
		ICustomBlockHighlight.cleanup();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldDrawHighlight(World world, int x, int y, int z) {
		return true;
	}
	
	public AxisAlignedBB[] getGuideAABBs(int x, int y, int z, int height) {
		return new AxisAlignedBB[] {
				AxisAlignedBB.getBoundingBox(x - 1, y, z - 1, x - 0.75, y + height, z - 0.75),
				AxisAlignedBB.getBoundingBox(x - 1, y, z + 1.75, x - 0.75, y + height, z + 2),
				AxisAlignedBB.getBoundingBox(x + 1.75, y, z - 1, x + 2, y + height, z - 0.75),
				AxisAlignedBB.getBoundingBox(x + 1.75, y, z + 1.75, x + 2, y + height, z + 2),
		};
	}
	
	public AxisAlignedBB[] getAABBs(TileEntityCargoElevator elevator, int x, int y, int z) {
		int height = elevator.height + 1;
		return new AxisAlignedBB[] {
				AxisAlignedBB.getBoundingBox(x - 1, y, z - 1, x - 0.75, y + height, z - 0.75),
				AxisAlignedBB.getBoundingBox(x - 1, y, z + 1.75, x - 0.75, y + height, z + 2),
				AxisAlignedBB.getBoundingBox(x + 1.75, y, z - 1, x + 2, y + height, z - 0.75),
				AxisAlignedBB.getBoundingBox(x + 1.75, y, z + 1.75, x + 2, y + height, z + 2),
				AxisAlignedBB.getBoundingBox(x - 1, y + 0.75 + elevator.extension, z - 1, x + 2, y + 1 + elevator.extension, z + 2),
		};
	}
}
