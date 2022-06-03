package com.hbm.blocks.network;

import com.hbm.entity.item.EntityMovingItem;
import com.hbm.lib.RefStrings;

import api.hbm.conveyor.IConveyorBelt;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyor extends Block implements IConveyorBelt {

	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;

	public BlockConveyor() {
		super(Material.iron);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":conveyor_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {

		if((metadata == 2 || metadata == 3) && (side == 4 || side == 5))
			return this.sideIcon;
		if((metadata == 4 || metadata == 5) && (side == 2 || side == 3))
			return this.sideIcon;
		
		return super.getIcon(side, metadata);
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		/*Vec3 snap =  this.getClosestSnappingPosition(world, x, y, z, itemPos);
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		return Vec3.createVectorHelper(snap.xCoord + dir.offsetX * speed, snap.yCoord, snap.zCoord + dir.offsetZ * speed);*/
		
		Vec3 snap = this.getClosestSnappingPosition(world, x, y, z, itemPos);
		/*double dist = snap.distanceTo(itemPos);
		
		if(dist > speed) {
			
			return Vec3.createVectorHelper(
					itemPos.xCoord + (snap.xCoord - itemPos.xCoord) / dist * speed,
					snap.yCoord,
					itemPos.zCoord + (snap.zCoord - itemPos.zCoord) / dist * speed
					);
		} else {
			ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
			return Vec3.createVectorHelper(snap.xCoord + dir.offsetX * speed, snap.yCoord, snap.zCoord + dir.offsetZ * speed);
		}*/
		
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		return Vec3.createVectorHelper(snap.xCoord - dir.offsetX * speed, snap.yCoord, snap.zCoord - dir.offsetZ * speed);
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {

		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		
		itemPos.xCoord = MathHelper.clamp_double(itemPos.xCoord, x, x + 1);
		itemPos.zCoord = MathHelper.clamp_double(itemPos.zCoord, z, z + 1);
		
		double posX = x + 0.5;
		double posZ = z + 0.5;

		if(dir.offsetX != 0) {
			posX = itemPos.xCoord;
		}
		if(dir.offsetZ != 0) {
			posZ = itemPos.zCoord;
		}
		
		return Vec3.createVectorHelper(posX, y + 0.25, posZ);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

		if(!world.isRemote) {

			if(entity instanceof EntityItem && entity.ticksExisted > 10 && !entity.isDead) {

				EntityMovingItem item = new EntityMovingItem(world);
				item.setItemStack(((EntityItem) entity).getEntityItem());
				item.setPositionAndRotation(x + 0.5, y + 0.25, z + 0.5, 0, 0);
				world.spawnEntityInWorld(item);

				entity.setDead();
			}
		}
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.25, z + 1);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
}
