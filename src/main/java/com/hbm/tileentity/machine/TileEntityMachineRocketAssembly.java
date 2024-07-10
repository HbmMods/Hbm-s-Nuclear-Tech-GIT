package com.hbm.tileentity.machine;

import com.hbm.handler.RocketStruct;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineRocketAssembly;
import com.hbm.inventory.gui.GUIMachineRocketAssembly;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineRocketAssembly extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public RocketStruct rocket;

	public TileEntityMachineRocketAssembly() {
		super(1 + RocketStruct.MAX_STAGES * 3 + 1); // capsule + stages + result
	}

	@Override
	public String getName() {
		return "container.machineRocketAssembly";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			rocket = new RocketStruct(slots[0]);
			for(int i = 1; i < RocketStruct.MAX_STAGES * 3; i += 3) {
				if(slots[i] == null && slots[i+1] == null && slots[i+2] == null) break;

				rocket.addStage(slots[i], slots[i+1], slots[i+2]);
			}
			networkPackNT(250);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		rocket.writeToByteBuffer(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		rocket = RocketStruct.readFromByteBuffer(buf);
	}

	public void construct() {
		if(!rocket.validate()) return;

		slots[slots.length - 1] = ItemCustomRocket.build(rocket);

		for(int i = 0; i < slots.length - 1; i++) {
			slots[i] = null;
		}
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

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("construct")) {
			construct();
		}
	}
	
}
