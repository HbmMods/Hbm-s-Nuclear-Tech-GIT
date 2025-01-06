package com.hbm.blocks.rail;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardRamp extends BlockDummyable implements IRailNTM, IRenderBlock {

	public RailStandardRamp() {
		super(Material.iron);
		this.bounding.add(AxisAlignedBB.getBoundingBox(-2.5, 0.0, -1.5, -1.5, 0.1, 0.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-1.5, 0.0, -1.5, -0.5, 0.3, 0.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.5, 0.0, -1.5, 0.5, 0.5, 0.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(0.5, 0.0, -1.5, 1.5, 0.7, 0.5));
		this.bounding.add(AxisAlignedBB.getBoundingBox(1.5, 0.0, -1.5, 2.5, 0.9, 0.5));
		this.maxY = 0.999D; //item bounce prevention
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 2, 2, 1, 0};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, 0, 0, 0, 0, new RailContext());
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info);
	}
	
	/* Very simple function determining the snapping position and adding the motion value to it, if desired. */
	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return Vec3.createVectorHelper(trainX, trainY, trainZ);
		int cX = pos[0];
		int cY = pos[1];
		int cZ = pos[2];
		int meta = world.getBlockMetadata(cX, cY, cZ) - this.offset;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		Vec3 vec = Vec3.createVectorHelper(trainX, trainY, trainZ);
		
		if(speed == 0) {
			//return vec;
		}
		
		if(dir == Library.POS_X || dir == Library.NEG_X) {
			double targetX = trainX;
			if(motionX > 0) {
				targetX += speed;
				info.yaw(-90F);
			} else {
				targetX -= speed;
				info.yaw(90F);
			}
			double dist = (cX + 0.5 - targetX + 2.5) / 5;
			vec.xCoord = MathHelper.clamp_double(targetX, cX - 2, cX + 3);
			vec.yCoord = MathHelper.clamp_double(dir == Library.POS_X ? cY + dist : cY + 1 - dist, cY, cY + 1) + 0.1875;
			vec.zCoord = cZ + 0.5 + rot.offsetZ * 0.5;
			info.dist(Math.abs(targetX - vec.xCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX + (motionX * speed > 0 ? 3 : -3), cY + (motionX * speed > 0 ^ dir == Library.POS_X ? 1 : 0), cZ));
		} else {
			double targetZ = trainZ;
			if(motionZ > 0) {
				targetZ += speed;
				info.yaw(0F);
			} else {
				targetZ -= speed;
				info.yaw(180F);
			}
			double dist = (cZ + 0.5 - targetZ + 2.5) / 5;
			vec.xCoord = cX + 0.5 + rot.offsetX * 0.5;
			vec.yCoord = MathHelper.clamp_double(dir == Library.POS_Z ? cY + dist : cY + 1 - dist, cY, cY + 1) + 0.1875;
			vec.zCoord = MathHelper.clamp_double(targetZ, cZ - 2,cZ + 3);
			info.dist(Math.abs(targetZ - vec.zCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX, cY + (motionZ * speed > 0 ^ dir == Library.POS_Z ? 1 : 0), cZ + (motionZ * speed > 0 ? 3 : -3)));
		}
		
		return vec;
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}

	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {1, -1, 2, 2, 1, 0}, x, y, z, dir);
	}

	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {1, -1, 2, 2, 1, 0}, this, dir);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glTranslated(0, -0.0625, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.3, 0.3, 0.3);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_ramp, block.getIcon(1, 0), tessellator, 0, false);
		tessellator.draw();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z) {
		if(meta < 12) return;
		float rotation = 0;
		if(meta == 15) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 12) rotation = 180F / 180F * (float) Math.PI;
		if(meta == 14) rotation = 270F / 180F * (float) Math.PI;
		if(meta == 12) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, -0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, 0.5F);
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_ramp, block.getIcon(1, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		if(meta == 12) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, 0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, -0.5F);
	}
}
