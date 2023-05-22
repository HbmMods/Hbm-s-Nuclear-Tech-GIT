package com.hbm.blocks.machine;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DummyBlockMachine extends DummyOldBase {

	private Block drop;
	float oX = 0;
	float oY = 0;
	float oZ = 0;
	float dX = 1;
	float dY = 1;
	float dZ = 1;

	public DummyBlockMachine(Material p_i45386_1_, boolean port) {
		super(p_i45386_1_, port);
	}

	public DummyBlockMachine(Material mat, Block drop, boolean port) {
		super(mat, port);
		this.drop = drop;
	}
	
	public DummyBlockMachine setBounds(float oX, float oY, float oZ, float dX, float dY, float dZ) {

		this.oX = oX * 0.0625F;
		this.oY = oY * 0.0625F;
		this.oZ = oZ * 0.0625F;
		this.dX = dX * 0.0625F;
		this.dY = dY * 0.0625F;
		this.dZ = dZ * 0.0625F;
		
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(drop);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityDummy) {
				int a = ((TileEntityDummy) te).targetX;
				int b = ((TileEntityDummy) te).targetY;
				int c = ((TileEntityDummy) te).targetZ;

				if(te != null) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, a, b, c);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.setBlockBounds(oX, oY, oZ, dX, dY, dZ);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		this.setBlockBounds(oX, oY, oZ, dX, dY, dZ);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
