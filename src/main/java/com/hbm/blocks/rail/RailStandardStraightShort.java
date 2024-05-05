package com.hbm.blocks.rail;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
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

public class RailStandardStraightShort extends BlockDummyable implements IRailNTM, IRenderBlock {

	public RailStandardStraightShort() {
		super(Material.iron);
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
		return new int[] {0, 0, 0, 0, 1, 0};
	}

	@Override
	public int getOffset() {
		return 0;
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
		
		if(dir == Library.POS_X || dir == Library.NEG_X) {
			double targetX = trainX;
			if(motionX > 0) {
				targetX += speed;
				info.yaw(-90F);
			} else {
				targetX -= speed;
				info.yaw(90F);
			}
			vec.xCoord = MathHelper.clamp_double(targetX, cX, cX + 1);
			vec.yCoord = y + 0.1875;
			vec.zCoord = cZ + 0.5 + rot.offsetZ * 0.5;
			info.dist(Math.abs(targetX - vec.xCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX + (motionX * speed > 0 ? 1 : -1), y, cZ));
		} else {
			double targetZ = trainZ;
			if(motionZ > 0) {
				targetZ += speed;
				info.yaw(0F);
			} else {
				targetZ -= speed;
				info.yaw(180F);
			}
			vec.xCoord = cX + 0.5 + rot.offsetX * 0.5;
			vec.yCoord = y + 0.1875;
			vec.zCoord = MathHelper.clamp_double(targetZ, cZ,cZ + 1);
			info.dist(Math.abs(targetZ - vec.zCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX, y, cZ + (motionZ * speed > 0 ? 1 : -1)));
		}
		
		return vec;
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventory(Tessellator tessellator, Block block, int metadata) {
		GL11.glTranslated(0, -0.0625, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.7, 0.7, 0.7);
		tessellator.startDrawingQuads();
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_straight_short, block.getIcon(1, 0), tessellator, 0, false);
		tessellator.draw();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderWorld(Tessellator tessellator, Block block, int meta, IBlockAccess world, int x, int y, int z) {
		if(meta < 12) return;
		float rotation = 0;
		if(meta == 14 || meta == 15) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 12) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, -0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, 0.5F);
		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.rail_standard_straight_short, block.getIcon(1, 0), tessellator, rotation, true);
		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);
		if(meta == 12) tessellator.addTranslation(-0.5F, 0F, 0F);
		if(meta == 13) tessellator.addTranslation(0.5F, 0F, 0F);
		if(meta == 14) tessellator.addTranslation(0F, 0F, 0.5F);
		if(meta == 15) tessellator.addTranslation(0F, 0F, -0.5F);
	}
}
