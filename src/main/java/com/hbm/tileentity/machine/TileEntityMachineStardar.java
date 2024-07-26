package com.hbm.tileentity.machine;

import com.hbm.dim.CelestialBody;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.inventory.gui.GUIMachineStardar;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityMachineStardar extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {

	// Used to point the dish on the client
	private CelestialBody lastProcessedBody;

	public float dishYaw = 0;
	public float dishPitch = 0;

	public TileEntityMachineStardar() {
		super(2);
	}

	@Override
	public void updateEntity() {
		dishPitch = 30;
		dishYaw = MathHelper.wrapAngleTo180_float(dishYaw + 1);

		if(!worldObj.isRemote) {
			networkPackNT(250);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerStardar(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineStardar(player.inventory, this);
	}

	@Override
	public String getName() {
		return "container.machineStardar";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(lastProcessedBody != null) {
			nbt.setInteger("pid", lastProcessedBody.dimensionId);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(nbt.hasKey("pid")) {
			lastProcessedBody = CelestialBody.getBodyOrNull(nbt.getInteger("pid"));
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(lastProcessedBody != null ? lastProcessedBody.dimensionId : -1);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		int dimensionId = buf.readInt();
		if(dimensionId >= 0) {
			lastProcessedBody = CelestialBody.getBodyOrNull(dimensionId);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}

	private void processDrive(int targetDimensionId) {
		lastProcessedBody = CelestialBody.getBodyOrNull(targetDimensionId);
		if(lastProcessedBody == null) return;

		if(slots[1] == null || slots[1].getItem() != ModItems.hard_drive) return;

		slots[1] = new ItemStack(ModItems.full_drive, 1, lastProcessedBody.getEnum().ordinal());

		this.markDirty();
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("pid")) {
			processDrive(data.getInteger("pid"));
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

}
