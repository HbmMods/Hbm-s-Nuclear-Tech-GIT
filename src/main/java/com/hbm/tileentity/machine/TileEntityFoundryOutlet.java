package com.hbm.tileentity.machine;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.util.CrucibleUtil;

import api.hbm.block.ICrucibleAcceptor;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityFoundryOutlet extends TileEntityFoundryBase{

	public NTMMaterial filter = null;
	public NTMMaterial lastFilter = null;
	/* inverts filter behavior, will let everything but the filter material pass */
	public boolean invertFilter = false;
	/** inverts redstone behavior, i.e. when TRUE, the outlet will be blocked by default and only open with redstone */
	public boolean invertRedstone = false;
	public boolean lastClosed = false;

	/** if TRUE, prevents all fluids from flowing through the outlet and renders a small barrier */
	public boolean isClosed() {
		return invertRedstone ^ this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(worldObj.isRemote) {
			boolean isClosed = isClosed();
			if(this.lastClosed != isClosed || this.filter != this.lastFilter) {
				this.lastFilter = this.filter;
				this.lastClosed = isClosed;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}

	@Override public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {

		if(filter != null && (filter != stack.material ^ invertFilter)) return false;
		if(isClosed()) return false;
		if(side != ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite()) return false;

		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 4, z + 0.5);

		MovingObjectPosition[] mop = new MovingObjectPosition[1];
		ICrucibleAcceptor acc = CrucibleUtil.getPouringTarget(world, start, end, mop);

		if(acc == null) {
			return false;
		}

		return acc.canAcceptPartialPour(world, mop[0].blockX, mop[0].blockY, mop[0].blockZ, mop[0].hitVec.xCoord, mop[0].hitVec.yCoord, mop[0].hitVec.zCoord, ForgeDirection.UP, stack);
	}

	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {

		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 4, z + 0.5);

		MovingObjectPosition[] mop = new MovingObjectPosition[1];
		ICrucibleAcceptor acc = CrucibleUtil.getPouringTarget(world, start, end, mop);

		if(acc == null)
			return stack;

		MaterialStack didPour = acc.pour(world, mop[0].blockX, mop[0].blockY, mop[0].blockZ, mop[0].hitVec.xCoord, mop[0].hitVec.yCoord, mop[0].hitVec.zCoord, ForgeDirection.UP, stack);


		if(stack != null) {

			ForgeDirection dir = side.getOpposite();
			double hitY = mop[0].blockY + 1;

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "foundry");
			data.setInteger("color", stack.material.moltenColor);
			data.setByte("dir", (byte) dir.ordinal());
			data.setFloat("off", 0.375F);
			data.setFloat("base", 0F);
			data.setFloat("len", Math.max(1F, yCoord - (float) (Math.ceil(hitY) - 0.875)));
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, xCoord + 0.5D - dir.offsetX * 0.125, yCoord + 0.125, zCoord + 0.5D - dir.offsetZ * 0.125), new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord, zCoord + 0.5, 50));

		}

		return didPour;
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.invertRedstone = nbt.getBoolean("invert");
		this.invertFilter = nbt.getBoolean("invertFilter");
		this.filter = Mats.matById.get((int) nbt.getShort("filter"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("invert", this.invertRedstone);
		nbt.setBoolean("invertFilter", this.invertFilter);
		nbt.setShort("filter", this.filter == null ? -1 : (short) this.filter.id);
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setBoolean("invert", this.invertRedstone);
		nbt.setBoolean("invertFilter", this.invertFilter);
		if(filter != null){
			nbt.setIntArray("matFilter", new int[]{ filter.id });
		}

		return nbt;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {

		if(nbt.hasKey("invert"))   this.invertRedstone = nbt.getBoolean("invert");
		if(nbt.hasKey("invertFilter")) this.invertFilter = nbt.getBoolean("invertFilter");
		if(nbt.hasKey("matFilter")) {
			int[] ids = nbt.getIntArray("matFilter");
			if(ids.length > 0 && index < ids.length)
				this.filter = Mats.matById.get(ids[index]);
		}

	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		ArrayList<String> info = new ArrayList<>();
		info.add("copytool.invertRedstone");
		info.add("copytool.invertFilter");
		if (filter != null) info.add(filter.getUnlocalizedName());
		return info.toArray(new String[0]);
	}
}
