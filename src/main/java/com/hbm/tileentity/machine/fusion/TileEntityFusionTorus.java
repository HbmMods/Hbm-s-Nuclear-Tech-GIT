package com.hbm.tileentity.machine.fusion;

import com.hbm.inventory.container.ContainerFusionTorus;
import com.hbm.inventory.gui.GUIFusionTorus;
import com.hbm.module.machine.ModuleMachineFusion;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityCooledBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityFusionTorus extends TileEntityCooledBase implements IGUIProvider {


	public ModuleMachineFusion fusionModule;
	
	public TileEntityFusionTorus() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.fusionTorus";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.fusionModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.fusionModule.deserialize(buf);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}
	
	/** Linearly scales up from 0% to 100% from 0 to 0.5, then stays at 100% */
	public static double getSpeedScaled(double max, double level) {
		if(level >= max * 0.5) return 1D;
		return level / max * 2D;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[0]; // TBI
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 8,
					yCoord,
					zCoord - 8,
					xCoord + 9,
					yCoord + 5,
					zCoord + 9
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
		return new ContainerFusionTorus(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFusionTorus(player.inventory, this);
	}
}
