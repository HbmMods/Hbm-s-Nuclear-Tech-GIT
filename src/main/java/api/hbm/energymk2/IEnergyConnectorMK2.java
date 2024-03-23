package api.hbm.energymk2;

import com.hbm.util.CompatEnergyControl;

import api.hbm.energy.ILoadedTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyConnectorMK2 extends ILoadedTile {
	
	/**
	 * Whether the given side can be connected to
	 * dir refers to the side of this block, not the connecting block doing the check
	 * @param dir
	 * @return
	 */
	public default boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
	public long getPower();
	public long getMaxPower();
	
	public static final boolean particleDebug = false;
	
	public default Vec3 getDebugParticlePos() {
		TileEntity te = (TileEntity) this;
		Vec3 vec = Vec3.createVectorHelper(te.xCoord + 0.5, te.yCoord + 1, te.zCoord + 0.5);
		return vec;
	}
	
	public default void provideInfoForEC(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_ENERGY_HE, this.getPower());
		data.setLong(CompatEnergyControl.L_CAPACITY_HE, this.getMaxPower());
	}
}
