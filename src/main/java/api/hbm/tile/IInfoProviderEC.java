package api.hbm.tile;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Info providers for ENERGY CONTROL
 * 
 * For EC's implementation, refer to:
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/crossmod/CrossHBM.java
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/items/cards/ItemCardHBM.java
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/utils/DataHelper.java
 * 
 * (keys are from DataHelper.java and CrossHBM.java)
 * 
 *  */
public interface IInfoProviderEC {

	/** Adds any custom data that isn't covered by the standard energy and fluid implementations. */
	public void provideExtraInfo(NBTTagCompound data);
}
