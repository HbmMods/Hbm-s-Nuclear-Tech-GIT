package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDecoModel extends BlockEnumMulti implements INBTBlockTransformable {

	public BlockDecoModel(Material mat, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
		super(mat, theEnum, multiName, multiTexture);
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

	//Did somebody say - pain?
			//Alright fuckers, looks like 2/b010 = North, 3/b011 = South, 4/b100 = West, 5/b101 = East for sides.
			//I'll just opt for something similar (0/b00 North, 1/b01 South, 2/b10 West, 3/b11 East)

	//Assumes meta is using the third and fourth bits.
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		int meta;

		if((i & 1) != 1)
			meta = i >> 1; //For North(b00>b00) and South(b10>b01), shift bits right by one
		else {
			if(i == 3)
				meta = 2; //For West(b11>b10), just set to 2
			else
				meta = 3; //For East(b01>b11), just set to 3
		}

		world.setBlockMetadataWithNotify(x, y, z, (meta << 2) | stack.getItemDamage(), 2);
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

	//These are separate because they have to be constant
	private float mnX = 0.0F; //min
	private float mnY = 0.0F;
	private float mnZ = 0.0F;
	private float mxX = 1.0F; //max
	private float mxY = 1.0F;
	private float mxZ = 1.0F;

	public BlockDecoModel setBlockBoundsTo(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		mnX = minX;
		mnY = minY;
		mnZ = minZ;
		mxX = maxX;
		mxY = maxY;
		mxZ = maxZ;

		return this;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		switch(world.getBlockMetadata(x, y, z) >> 2) {
		case 0://North
			this.setBlockBounds(1 - mxX, mnY, 1 - mxZ, 1 - mnX, mxY, 1 - mnZ);
			break;
		case 1://South
			this.setBlockBounds(mnX, mnY, mnZ, mxX, mxY, mxZ);
			break;
		case 2://West
			this.setBlockBounds(1 - mxZ, mnY, mnX, 1 - mnZ, mxY, mxX);
			break;
		case 3://East
			this.setBlockBounds(mnZ, mnY, 1 - mxX, mxZ, mxY, 1 - mnX);
			break;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		if(coordBaseMode == 0) return meta;
		//N: 0b00, S: 0b01, W: 0b10, E: 0b11
		int rot = meta >> 2;
		int type = meta & 3;

		switch(coordBaseMode) {
		default: //South
			break;
		case 1: //West
			if((rot & 3) < 2) //N & S can just have bits toggled
				rot = rot ^ 3;
			else //W & E can just have first bit set to 0
				rot = rot ^ 2;
			break;
		case 2: //North
			rot = rot ^ 1; //N, W, E & S can just have first bit toggled
			break;
		case 3: //East
			if((rot & 3) < 2)//N & S can just have second bit set to 1
				rot = rot ^ 2;
			else //W & E can just have bits toggled
				rot = rot ^ 3;
			break;
		}
		//genuinely like. why did i do that
		return (rot << 2) | type; //To accommodate for BlockDecoModel's shift in the rotation bits; otherwise, simply bit-shift right and or any non-rotation meta after
	}
}