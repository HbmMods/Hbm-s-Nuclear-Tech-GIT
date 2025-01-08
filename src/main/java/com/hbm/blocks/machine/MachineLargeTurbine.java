package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineLargeTurbine;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class MachineLargeTurbine extends BlockDummyable implements ITooltipProvider {

	public MachineLargeTurbine(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachineLargeTurbine();

		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 1, 0, 3, 1, 1, 1 };
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);

		if(world.isRemote)
			return;

		int k = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		ForgeDirection dir = ForgeDirection.NORTH;

		if(k == 0)
			dir = ForgeDirection.getOrientation(2);
		if(k == 1)
			dir = ForgeDirection.getOrientation(5);
		if(k == 2)
			dir = ForgeDirection.getOrientation(3);
		if(k == 3)
			dir = ForgeDirection.getOrientation(4);

		ForgeDirection dir2 = dir.getRotation(ForgeDirection.UP);

		//back connector
		this.makeExtra(world, x + dir.offsetX * -4, y, z + dir.offsetZ * -4);
		//front connector
		this.makeExtra(world, x, y, z);

		int xc = x - dir.offsetX;
		int zc = z - dir.offsetZ;

		//side connectors
		this.makeExtra(world, xc + dir2.offsetX, y, zc + dir2.offsetZ);
		this.makeExtra(world, xc - dir2.offsetX, y, zc - dir2.offsetZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
