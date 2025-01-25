package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPASource;
import com.hbm.inventory.gui.GUIPASource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPASource extends TileEntityCooledBase implements IGUIProvider {
	
	public TileEntityPASource() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.paSource";
	}

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 5, yCoord, zCoord + rot.offsetZ * 5, rot),
		};
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord - 1,
					zCoord - 4,
					xCoord + 5,
					yCoord + 2,
					zCoord + 6
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPASource(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPASource(player.inventory, this);
	}
	
	public static class Particle {

		public int x;
		public int y;
		public int z;
		public ForgeDirection dir;
		public int momentum;
	}
}
