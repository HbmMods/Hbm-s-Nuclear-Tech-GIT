package com.hbm.tileentity.machine;

import com.hbm.handler.MissileStruct;
import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.inventory.gui.GUIMachineRocketAssembly;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineRocketAssembly extends TileEntityMachineBase implements IGUIProvider {
	
	public MissileStruct rocket;

	public TileEntityMachineRocketAssembly() {
		super(1 + 5 * 3 + 1); // capsule + stages + result
	}

	@Override
	public String getName() {
		return "container.machineRocketAssembly";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			rocket = new MissileStruct(slots[0], slots[1], slots[2], slots[3]);
			networkPackNT(250);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		rocket.writeToByteBuffer(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		rocket = MissileStruct.readFromByteBuffer(buf);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRocketAssembly(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRocketAssembly(player.inventory, this);
	}
	
}
