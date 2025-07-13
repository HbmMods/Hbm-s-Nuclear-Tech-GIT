package com.hbm.tileentity;

import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.BobMathUtil;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import api.hbm.fluidmk2.IFluidUserMK2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public interface IFluidCopiable extends ICopiable {
	
	/**
	 * @return First type for the normal paste, second type for the alt paste,
	 *         none if there is no alt paste support
	 */
	default int[] getFluidIDToCopy() {
		IFluidUserMK2 tile = (IFluidUserMK2) this;
		ArrayList<Integer> types = new ArrayList<>();

		for(FluidTank tank : tile.getAllTanks()) {
			if(!tank.getTankType().hasNoID())
				types.add(tank.getTankType().getID());
		}

		return BobMathUtil.intCollectionToArray(types);
	}

	default FluidTank getTankToPaste() {
		TileEntity te = (TileEntity) this;
		if(te instanceof IFluidStandardTransceiverMK2) { // why are we using the transceiver here?
			IFluidStandardTransceiverMK2 tile = (IFluidStandardTransceiverMK2) this;
			return tile.getReceivingTanks() != null ? tile.getReceivingTanks()[0] : null;
		}
		return null;
	}

	@Override
	default NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		if(getFluidIDToCopy().length > 0) tag.setIntArray("fluidID", getFluidIDToCopy());
		return tag;
	}

	@Override
	default void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		if(getTankToPaste() != null) {
			int[] ids = nbt.getIntArray("fluidID");
			if(ids.length > 0 && index < ids.length) {
				int id = ids[index];
				getTankToPaste().setTankType(Fluids.fromID(id));
			}
		}
	}

	@Override
	default String[] infoForDisplay(World world, int x, int y, int z) {
		int[] ids = getFluidIDToCopy();
		String[] names = new String[ids.length];
		for(int i = 0; i < ids.length; i++) {
			names[i] = Fluids.fromID(ids[i]).getUnlocalizedName();
		}
		return names;
	}
}
