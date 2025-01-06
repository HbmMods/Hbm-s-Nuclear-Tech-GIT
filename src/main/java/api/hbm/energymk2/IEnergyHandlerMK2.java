package api.hbm.energymk2;

import com.hbm.util.CompatEnergyControl;

import api.hbm.tile.ILoadedTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

/** DO NOT USE DIRECTLY! This is simply the common ancestor to providers and receivers, because all this behavior has to be excluded from conductors! */
public interface IEnergyHandlerMK2 extends IEnergyConnectorMK2, ILoadedTile {

	public long getPower();
	public void setPower(long power);
	public long getMaxPower();
	
	public static final boolean particleDebug = false;
	
	public default Vec3 getDebugParticlePosMK2() {
		TileEntity te = (TileEntity) this;
		Vec3 vec = Vec3.createVectorHelper(te.xCoord + 0.5, te.yCoord + 1, te.zCoord + 0.5);
		return vec;
	}
	
	public default void provideInfoForECMK2(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_ENERGY_HE, this.getPower());
		data.setLong(CompatEnergyControl.L_CAPACITY_HE, this.getMaxPower());
	}
}
