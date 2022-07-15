package com.hbm.blocks.turret;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.EnumGUI;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TurretBaseNT extends BlockDummyable {

	private final Class<? extends TileEntityTurretBaseNT> clazz;
	private final EnumGUI gui;
	public TurretBaseNT(Material mat, Class<? extends TileEntityTurretBaseNT> clazz, EnumGUI gui)
	{
		super(mat);
		this.clazz = clazz;
		this.gui = gui;
		setHardness(5);
		setResistance(600);
		setBlockTextureName(RefStrings.MODID + ":block_steel");
		setCreativeTab(MainRegistry.weaponTab);
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 0, 0, 1, 0, 1, 0 };
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;
			
			openGUI(world, player, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}
	
	public void openGUI(World world, EntityPlayer player, int x, int y, int z)
	{
		FMLNetworkHandler.openGui(player, MainRegistry.instance, gui.ordinal(), world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		try
		{
			if (meta >= 12)
				return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return new TileEntityProxyCombo(true, true, false);
	}
}
