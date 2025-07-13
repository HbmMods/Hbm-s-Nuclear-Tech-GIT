package com.hbm.tileentity.machine;

import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;

import api.hbm.block.ICrucibleAcceptor;
import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Base class for all foundry channel type blocks - channels, casts, basins, tanks, etc.
 * Foundry type blocks can only hold one type at a time and usually either store or move it around.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryBase extends TileEntityLoadedBase implements ICrucibleAcceptor, ICopiable {

	public NTMMaterial type;
	protected NTMMaterial lastType;
	public int amount;
	protected int lastAmount;

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {

			if(shouldClientReRender() && this.lastType != this.type || this.lastAmount != this.amount) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastType = this.type;
				this.lastAmount = this.amount;
			}
		} else {

			if(this.lastType != this.type || this.lastAmount != this.amount) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastType = this.type;
				this.lastAmount = this.amount;
			}
		}
	}

	/** Recommended FALSE for things that update a whole lot. TRUE if updates only happen once every few ticks. */
	protected boolean shouldClientReRender() {
		return true;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = Mats.matById.get(nbt.getInteger("type"));
		this.amount = nbt.getInteger("amount");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(this.type == null) {
			nbt.setInteger("type", -1);
		} else {
			nbt.setInteger("type", this.type.id);
		}

		nbt.setInteger("amount", this.amount);
	}

	public abstract int getCapacity();

	/**
	 * Standard check for testing if this material stack can be added to the casting block. Checks:<br>
	 * - type matching<br>
	 * - amount being at max<br>
	 */
	public boolean standardCheck(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		if(this.type != null && this.type != stack.material && this.amount > 0) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		return true;
	}

	/**
	 * Standardized adding of material via pouring or flowing. Does:<br>
	 * - sets material to match the input
	 * - adds the amount, not exceeding the maximum
	 * - returns the amount that cannot be added
	 */
	public MaterialStack standardAdd(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		this.type = stack.material;

		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}

		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();

		stack.amount -= required;

		return stack;
	}

	/** Standard check with no additional limitations added */
	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return this.standardCheck(world, x, y, z, side, stack);
	}

	/** Standard flow, no special handling required */
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return standardAdd(world, x, y, z, side, stack);
	}

	/** Standard check, but with the additional limitation that the only valid source direction is UP */
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(side != ForgeDirection.UP) return false;
		return this.standardCheck(world, x, y, z, side, stack);
	}

	/** Standard flow, no special handling required */
	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return standardAdd(world, x, y, z, side, stack);
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound nbt = new NBTTagCompound();
		if(type != null) nbt.setIntArray("matFilter", new int[]{ type.id });
		return nbt;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {

	}
}
