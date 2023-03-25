package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFan extends BlockContainer {

	public MachineFan() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFan();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
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
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		int meta = world.getBlockMetadata(x, y, z);

		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN) return meta != 0 && meta != 1;
		if(side == ForgeDirection.NORTH || side == ForgeDirection.SOUTH) return meta != 2 && meta != 3;
		if(side == ForgeDirection.EAST || side == ForgeDirection.WEST) return meta != 4 && meta != 5;
		
		return false;
	}
	
	public static class TileEntityFan extends TileEntity {

		public float spin;
		public float prevSpin;

		@Override
		public void updateEntity() {
			
			this.prevSpin = this.spin;
			
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				
				int range = 10;
				int effRange = 0;
				double push = 0.1;
				
				for(int i = 1; i <= range; i++) {
					if(worldObj.getBlock(xCoord + dir.offsetX * i, yCoord + dir.offsetY * i, zCoord + dir.offsetZ * i).isNormalCube()) {
						break;
					}
					
					effRange = i;
				}

				int x = dir.offsetX * effRange;
				int y = dir.offsetY * effRange;
				int z = dir.offsetZ * effRange;
				
				List<Entity> affected = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5 + Math.min(x, 0), yCoord + 0.5 + Math.min(y, 0), zCoord + 0.5 + Math.min(z, 0), xCoord + 0.5 + Math.max(x, 0), yCoord + 0.5 + Math.max(y, 0), zCoord + 0.5 + Math.max(z, 0)).expand(0.5, 0.5, 0.5));
				
				for(Entity e : affected) {
					
					e.motionX += dir.offsetX * push;
					e.motionY += dir.offsetY * push;
					e.motionZ += dir.offsetZ * push;
				}
				
				if(worldObj.isRemote && worldObj.rand.nextInt(30) == 0) {
					double speed = 0.2;
					worldObj.spawnParticle("cloud", xCoord + 0.5 + dir.offsetX * 0.5, yCoord + 0.5 + dir.offsetY * 0.5, zCoord + 0.5 + dir.offsetZ * 0.5, dir.offsetX * speed, dir.offsetY * speed, dir.offsetZ * speed);
				}
				
				this.spin += 30;
			}
			
			if(this.spin >= 360) {
				this.prevSpin -= 360;
				this.spin -= 360;
			}
		}
	}
}
