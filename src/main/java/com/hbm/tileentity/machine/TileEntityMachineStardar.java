package com.hbm.tileentity.machine;

import com.hbm.dim.CelestialBody;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.inventory.gui.GUIMachineStardar;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.ItemVOTVdrive.Destination;
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
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class TileEntityMachineStardar extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {

	private int timeUntilPoint = 0;
	
	// Used to point the dish on the client
	public float dishYaw = 0;
	public float dishPitch = 0;
	public float prevDishYaw = 0;
	public float prevDishPitch = 0;

	// Sent by the server for the client to smoothly lerp to
	public float targetYaw = 0;
	public float targetPitch = 0;

	private float maxSpeedYaw = 0.5F;

	public int[] heightmap;
	public boolean updateHeightmap = false;
	private ItemStack previousStack;

	public TileEntityMachineStardar() {
		super(1);
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			timeUntilPoint--;
			if(timeUntilPoint < 0) {
				timeUntilPoint = worldObj.rand.nextInt(300) + 300;

				targetYaw = MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360);
				targetPitch = worldObj.rand.nextFloat() * 80;
			}

			if(slots[0] != null && slots[0].getItem() == ModItems.full_drive) {
				if(heightmap == null || !slots[0].isItemEqual(previousStack)) {
					previousStack = slots[0];

					Destination destination = ItemVOTVdrive.getApproximateDestination(slots[0]);
					CelestialBody body = destination.body.getBody();
					ChunkCoordIntPair chunk = destination.getChunk();

					if(body != null) {
						heightmap = new int[256*256];
						updateHeightmap = true;

						for(int cx = 0; cx < 16; cx++) {
							for(int cz = 0; cz < 16; cz++) {
								int[] map = body.getHeightmap(chunk.chunkXPos + cx - 8, chunk.chunkZPos + cz - 8);
								int ox = cx * 16;
								int oz = cz * 16;
	
								for(int x = 0; x < 16; x++) {
									for(int z = 0; z < 16; z++) {
										heightmap[(z + oz) * 256 + (x + ox)] = map[z * 16 + x];
									}
								}
							}
						}
					}
				}
			} else {
				if(heightmap != null) {
					heightmap = null;
					updateHeightmap = true;
				}
			}

			networkPackNT(250);
		} else {
			float yawOffset = MathHelper.wrapAngleTo180_float(targetYaw - dishYaw);
			float moveYaw = MathHelper.clamp_float(yawOffset, -maxSpeedYaw, maxSpeedYaw);

			float pitchOffset = targetPitch - dishPitch;
			float pitchSpeed = (moveYaw / yawOffset) * Math.abs(pitchOffset);
			float movePitch = MathHelper.clamp_float(pitchOffset, -pitchSpeed, pitchSpeed);

			prevDishYaw = dishYaw;
			prevDishPitch = dishPitch;
			dishYaw += moveYaw;
			dishPitch += movePitch;
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
		if(!world.isRemote) updateHeightmap = true; // new viewer, send them the heightmap just in case
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
		nbt.setInteger("time", timeUntilPoint);

		nbt.setFloat("yaw", targetYaw);
		nbt.setFloat("pitch", targetPitch);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		timeUntilPoint = nbt.getInteger("time");

		targetYaw = nbt.getFloat("yaw");
		targetPitch = nbt.getFloat("pitch");
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeFloat(targetYaw);
		buf.writeFloat(targetPitch);

		buf.writeBoolean(updateHeightmap);
		if(updateHeightmap) {
			if(heightmap != null) {
				buf.writeInt(heightmap.length);
				for(int h : heightmap) {
					buf.writeByte(h);
				}
			} else {
				buf.writeInt(0);
			}
		}
		updateHeightmap = false;
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		targetYaw = buf.readFloat();
		targetPitch = buf.readFloat();

		if(buf.readBoolean()) {
			updateHeightmap = true;
			int count = buf.readInt();
			if(count > 0) {
				heightmap = new int[count];
				for(int i = 0; i < count; i++) {
					heightmap[i] = buf.readUnsignedByte();
				}
			} else {
				heightmap = null;
			}
		}
	}

	private void processDrive(int targetDimensionId, int ix, int iz) {
		CelestialBody body = CelestialBody.getBodyOrNull(targetDimensionId);
		if(body == null) return;

		if(slots[0] == null || slots[0].getItem() != ModItems.hard_drive) return;

		slots[0] = new ItemStack(ModItems.full_drive, 1, body.getEnum().ordinal());

		if(ix != 0 || iz != 0) {
			slots[0].stackTagCompound = new NBTTagCompound();
			slots[0].stackTagCompound.setInteger("ax", ix);
			slots[0].stackTagCompound.setInteger("az", iz);
			slots[0].stackTagCompound.setBoolean("Processed", true);
		}

		// Now point the dish at the target planet
		timeUntilPoint = worldObj.rand.nextInt(300) + 300;
		targetYaw = MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360);
		targetPitch = worldObj.rand.nextFloat() * 80;

		this.markDirty();
	}

	private void updateDriveCoords(int x, int z) {
		if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) return;

		Destination destination = ItemVOTVdrive.getApproximateDestination(slots[0]);
		ItemVOTVdrive.setCoordinates(slots[0], destination.x + x - 8 * 16, destination.z + z - 8 * 16);

		this.markDirty();
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("pid")) {
			processDrive(data.getInteger("pid"), data.getInteger("ix"), data.getInteger("iz"));
		}

		if(data.hasKey("px") && data.hasKey("pz")) {
			updateDriveCoords(data.getInteger("px"), data.getInteger("pz"));
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

}
