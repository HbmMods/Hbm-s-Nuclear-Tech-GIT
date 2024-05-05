package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IBomb;
import com.hbm.items.special.ItemDoorSkin;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.Rotation;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDoorGeneric extends BlockDummyable implements IBomb {

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
		return type.getBlockOffset();
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		int[] pos1 = findCore(world, x, y, z);
		if(pos1 == null) return BombReturnCode.ERROR_INCOMPATIBLE;
		TileEntityDoorGeneric door = (TileEntityDoorGeneric) world.getTileEntity(pos1[0], pos1[1], pos1[2]);
		if(door != null) {
			DoorDecl decl = door.getDoorType();
			if(!decl.remoteControllable()) return BombReturnCode.ERROR_INCOMPATIBLE;
			if(door.tryToggle(null)) {
				return BombReturnCode.TRIGGERED;
			}
		}
		
		return BombReturnCode.ERROR_INCOMPATIBLE;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote && !playerIn.isSneaking()) {
			int[] pos1 = findCore(world, x, y, z);
			if(pos1 == null)
				return false;
			TileEntityDoorGeneric door = (TileEntityDoorGeneric) world.getTileEntity(pos1[0], pos1[1], pos1[2]);

			if(door != null) {
				if(playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ItemDoorSkin) {
					return door.setSkinIndex((byte) playerIn.getHeldItem().getItemDamage());
				} else {
					return door.tryToggle(playerIn);
				}
			}
		}
		return !playerIn.isSneaking();
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
		AxisAlignedBB box = getBoundingBox(worldIn, x, y ,z);
		box = AxisAlignedBB.getBoundingBox(
				Math.min(box.minX, box.maxX), Math.min(box.minY, box.maxY), Math.min(box.minZ, box.maxZ),
				Math.max(box.minX, box.maxX), Math.max(box.minY, box.maxY), Math.max(box.minZ, box.maxZ));
		
		if(box.minY == y && box.maxY == y) return;
		if(box.minX == box.maxX && box.minY == box.maxY && box.minZ == box.maxZ) return;
		
		if(box != null && box.intersectsWith(entityBox)) {
			collidingBoxes.add(box);
		}
		
		//if(hasExtra(worldIn.getBlockMetadata(x, y, z))) //transition hatch only worked with this, but fire door doesn't
		//	return;
		//super.addCollisionBoxesToList(worldIn, x, y, z, entityBox, collidingBoxes, entityIn);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		AxisAlignedBB aabb = this.getBoundingBox(world, x, y, z);
		if(aabb.minX == aabb.maxX && aabb.minY == aabb.maxY && aabb.minZ == aabb.maxZ) return null;
		return aabb;
	}

	@Override //should fix AI pathfinding
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) { //btw the method name is the exact opposite of that it's doing, check net.minecraft.pathfinding.PathNavigate#512
		return hasExtra(world.getBlockMetadata(x, y, z)); //if it's open
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
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return getBoundingBox(world, x, y, z);
		//return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}
	
	public AxisAlignedBB getBoundingBox(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		int[] core = this.findCore(world, x, y, z);
		boolean open = hasExtra(meta) || (te instanceof TileEntityDoorGeneric && ((TileEntityDoorGeneric)te).state != 0);
		if(core == null){
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
		}
		TileEntity te2 = world.getTileEntity(core[0], core[1], core[2]);
		ForgeDirection dir = ForgeDirection.getOrientation(te2.getBlockMetadata() - BlockDummyable.offset);
		BlockPos pos = new BlockPos(x - core[0], y - core[1], z - core[2]).rotate(Rotation.getBlockRotation(dir).add(Rotation.COUNTERCLOCKWISE_90));
		AxisAlignedBB box = type.getBlockBound(pos.getX(), pos.getY(), pos.getZ(), open);
		
		switch(te2.getBlockMetadata() - offset){
		case 2: return AxisAlignedBB.getBoundingBox(x + 1 - box.minX, y + box.minY, z + 1 - box.minZ, x + 1 - box.maxX, y + box.maxY, z + 1 - box.maxZ);
		case 4: return AxisAlignedBB.getBoundingBox(x + 1 - box.minZ, y + box.minY, z + box.minX, x + 1 - box.maxZ, y + box.maxY, z + box.maxX);
		case 3: return AxisAlignedBB.getBoundingBox(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ);
		case 5: return AxisAlignedBB.getBoundingBox(x + box.minZ, y + box.minY, z + 1 - box.maxX, x + box.maxZ, y + box.maxY, z + 1 - box.minX);
		}
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}
}