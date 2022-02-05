package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDoorGeneric extends BlockDummyable {

	public DoorDecl type;
	
	public BlockDoorGeneric(Material materialIn, DoorDecl type){
		super(materialIn);
		this.type = type;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		if(meta >= 12)
			return new TileEntityDoorGeneric();
		return null;
	}

	@Override
	public int[] getDimensions(){
		return type.getDimensions();
	}

	@Override
	public int getOffset(){
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote && !playerIn.isSneaking()) {
			int[] pos1 = findCore(world, x, y, z);
			if(pos1 == null)
				return false;
			TileEntityDoorGeneric door = (TileEntityDoorGeneric) world.getTileEntity(pos1[0], pos1[1], pos1[2]);

			if(door != null) {
				return door.tryToggle(playerIn);
			}
		}
		if(!playerIn.isSneaking())
			return true;
		return false;
	}
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		TileEntity te = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		boolean open = hasExtra(meta) || (te instanceof TileEntityDoorGeneric && ((TileEntityDoorGeneric)te).shouldUseBB);
		return type.isLadder(open);
	}
	
	@Override
	public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB entityBox, List collidingBoxes, Entity entityIn) {
		AxisAlignedBB box = getCollisionBoundingBoxFromPool(worldIn, x, y, z);
		if(box.minY == 0 && box.maxY == 0)
			return;
		super.addCollisionBoxesToList( worldIn, x, y, z, entityBox, collidingBoxes, entityIn);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block blockIn){
		
		if(!world.isRemote){
			
			int[] corePos = findCore(world, x, y, z);
			if(corePos != null){
				TileEntity core = world.getTileEntity(corePos[0], corePos[1], corePos[2]);
				if(core instanceof TileEntityDoorGeneric){
					TileEntityDoorGeneric door = (TileEntityDoorGeneric)core;
					door.updateRedstonePower(x, y, z);
				}
			}
		}
		super.onNeighborBlockChange( world, x, y, z, blockIn);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World source, int x, int y, int z) {
		int meta = source.getBlockMetadata(x, y, z);
		TileEntity te = source.getTileEntity(x, y, z);
		int[] core = this.findCore(source, x, y, z);
		boolean open = hasExtra(meta) || (te instanceof TileEntityDoorGeneric && ((TileEntityDoorGeneric)te).shouldUseBB);
		if(core == null){
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		}
		TileEntity te2 = source.getTileEntity(core[0], core[1], core[2]);
		ForgeDirection dir = ForgeDirection.getOrientation(te2.getBlockMetadata() - BlockDummyable.offset);
		AxisAlignedBB box = type.getBlockBound(x - core[0], y - core[1], z - core[2], open ); //.rotate(dir.getBlockRotation().add(Rotation.COUNTERCLOCKWISE_90)), open); TODO: add rotation
		//System.out.println(te2.getBlockMetadata()-offset);
		switch(te2.getBlockMetadata()-offset){
		case 2:
			return AxisAlignedBB.getBoundingBox(1-box.minX, box.minY, 1-box.minZ, 1-box.maxX, box.maxY, 1-box.maxZ);
		case 4:
			return AxisAlignedBB.getBoundingBox(1-box.minZ, box.minY, box.minX, 1-box.maxZ, box.maxY, box.maxX);
		case 3:
			return AxisAlignedBB.getBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
		case 5:
			return AxisAlignedBB.getBoundingBox(box.minZ, box.minY, 1-box.minX, box.maxZ, box.maxY, 1-box.maxX);
		}
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

}