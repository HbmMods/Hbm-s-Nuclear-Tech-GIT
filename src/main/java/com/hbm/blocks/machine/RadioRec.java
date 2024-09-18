package com.hbm.blocks.machine;

import com.hbm.inventory.gui.GUIRadioRec;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.TileEntityRadioRec;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RadioRec extends BlockContainer implements IGUIProvider {

	public RadioRec(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioRec();
	}

	@Override
	public int getRenderType() {
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
		float f = 0.0625F;

		this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
		switch(te) {
		case 4: this.setBlockBounds(4 * f, 0.0F, 1 * f, 12 * f, 10 * f, 15 * f); break;
		case 2: this.setBlockBounds(1 * f, 0.0F, 4 * f, 15 * f, 10 * f, 12 * f); break;
		case 5: this.setBlockBounds(4 * f, 0.0F, 1 * f, 12 * f, 10 * f, 15 * f); break;
		case 3: this.setBlockBounds(1 * f, 0.0F, 4 * f, 15 * f, 10 * f, 12 * f); break;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
		float f = 0.0625F;

		this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
		switch(te) {
		case 4: this.setBlockBounds(4 * f, 0.0F, 1 * f, 12 * f, 10 * f, 15 * f); break;
		case 2: this.setBlockBounds(1 * f, 0.0F, 4 * f, 15 * f, 10 * f, 12 * f); break;
		case 5: this.setBlockBounds(4 * f, 0.0F, 1 * f, 12 * f, 10 * f, 15 * f); break;
		case 3: this.setBlockBounds(1 * f, 0.0F, 4 * f, 15 * f, 10 * f, 12 * f); break;
		}

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote && !player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return !player.isSneaking();
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRadioRec)
			return new GUIRadioRec((TileEntityRadioRec) te);
		
		return null;
	}
}
