package api.hbm.block;

import net.minecraft.world.World;

public interface IRadioControllable {

	public String[] getVariables(World world, int x, int y, int z);
	public void receiveSignal(World world, int x, int y, int z, String channel, String signal);
}
