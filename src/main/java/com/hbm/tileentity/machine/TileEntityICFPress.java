package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerICFPress;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIICFPress;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityICFPress extends TileEntityMachineBase implements IGUIProvider {
	
	public FluidTank[] tanks;
	public int muon;
	public static final int maxMuon = 16;

	public TileEntityICFPress() {
		super(8);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.DEUTERIUM, 16_000);
		this.tanks[1] = new FluidTank(Fluids.TRITIUM, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineICFPress";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.networkPackNT(15);
		}
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeByte((byte) muon);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		
		this.muon = buf.readByte();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerICFPress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIICFPress(player.inventory, this);
	}
}
