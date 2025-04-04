package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerPneumoTube;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumoTube extends TileEntityMachineBase implements IGUIProvider {

	public ModulePatternMatcher pattern = new ModulePatternMatcher(15);
	public ForgeDirection insertionDir = ForgeDirection.UNKNOWN;
	public ForgeDirection ejectionDir = ForgeDirection.UNKNOWN;
	
	public FluidTank compair;
	
	public TileEntityPneumoTube() {
		super(15);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
	}

	@Override
	public String getName() {
		return "container.pneumoTube";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		pattern.serialize(buf);
		compair.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		pattern.deserialize(buf);
		compair.deserialize(buf);
	}
	
	public void nextMode(int index) {
		this.pattern.nextMode(worldObj, slots[index], index);
	}

	public void initPattern(ItemStack stack, int index) {
		this.pattern.initPatternSmart(worldObj, stack, index);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("insertionDir", (byte) insertionDir.ordinal());
		nbt.setByte("ejectionDir", (byte) ejectionDir.ordinal());
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		this.insertionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("insertionDir"));
		this.ejectionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("ejectionDir"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.insertionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("insertionDir"));
		this.ejectionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("ejectionDir"));
		this.compair.readFromNBT(nbt, "tank");
		this.pattern.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("insertionDir", (byte) insertionDir.ordinal());
		nbt.setByte("ejectionDir", (byte) ejectionDir.ordinal());
		this.compair.writeToNBT(nbt, "tank");
		this.pattern.writeToNBT(nbt);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoTube(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
