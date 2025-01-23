package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPADipole;
import com.hbm.inventory.gui.GUIPADipole;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPADipole extends TileEntityCooledBase implements IGUIProvider {

	public int dirLower;
	public int dirUpper;
	public int dirRedstone;
	public int threshold;
	
	public TileEntityPADipole() {
		super(2);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public String getName() {
		return "container.paDipole";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
		}
		
		super.updateEntity();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(dirLower);
		buf.writeInt(dirUpper);
		buf.writeInt(dirRedstone);
		buf.writeInt(threshold);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		dirLower = buf.readInt();
		dirUpper = buf.readInt();
		dirRedstone = buf.readInt();
		threshold = buf.readInt();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		dirLower = nbt.getInteger("dirLower");
		dirUpper = nbt.getInteger("dirUpper");
		dirRedstone = nbt.getInteger("dirRedstone");
		threshold = nbt.getInteger("threshold");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("dirLower", dirLower);
		nbt.setInteger("dirUpper", dirUpper);
		nbt.setInteger("dirRedstone", dirRedstone);
		nbt.setInteger("threshold", threshold);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord - 1,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord - 1, yCoord + 2, zCoord, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord + 1, Library.POS_Y),
				new DirPos(xCoord, yCoord + 2, zCoord - 1, Library.POS_Y),
				new DirPos(xCoord + 1, yCoord - 2, zCoord, Library.NEG_Y),
				new DirPos(xCoord - 1, yCoord - 2, zCoord, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord + 1, Library.NEG_Y),
				new DirPos(xCoord, yCoord - 2, zCoord - 1, Library.NEG_Y)
		};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPADipole(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPADipole(player.inventory, this);
	}
}
