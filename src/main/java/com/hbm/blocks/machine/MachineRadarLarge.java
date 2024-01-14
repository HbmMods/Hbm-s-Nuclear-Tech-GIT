package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineRadarLarge;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineRadarLarge extends BlockDummyable {

	public MachineRadarLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineRadarLarge();
		if(meta >= 6) return new TileEntityProxyCombo().power();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(y < TileEntityMachineRadarNT.radarAltitude) {
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("[Radar] Error: Radar altitude not sufficient.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return true;
		}
		
		if(world.isRemote && !player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);
			if(pos == null) return false;
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else if(!player.isSneaking()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int m) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 6) {
			ForgeDirection dir = ForgeDirection.getOrientation(m);
			TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if(tile instanceof TileEntityMachineRadarNT) {
				TileEntityMachineRadarNT entity = (TileEntityMachineRadarNT) tile;
				return entity.getRedPower();
			}
		}
		return 0;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int m) {
		return isProvidingWeakPower(world, x, y, z, m);
	}
}
