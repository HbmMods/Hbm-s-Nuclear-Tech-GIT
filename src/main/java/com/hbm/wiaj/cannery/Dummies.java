package com.hbm.wiaj.cannery;


import api.hbm.energymk2.IEnergyConnectorMK2;
import api.hbm.fluid.IFluidConnector;
import net.minecraft.tileentity.TileEntity;

public class Dummies {

	public static class JarDummyConnector extends TileEntity implements IEnergyConnectorMK2, IFluidConnector { }
}
