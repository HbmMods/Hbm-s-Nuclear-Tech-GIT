package com.hbm.packet;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketDispatcher {

	//Mark 1 Packet Sending Device
	public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(RefStrings.MODID);

	public static final void registerPackets()
	{
		//Packet sent for every connected electricity pole, for wire rendering
		wrapper.registerMessage(TEPylonSenderPacket.Handler.class, TEPylonSenderPacket.class, 0, Side.CLIENT);
		//Resets connection list in client-sided pole rendering
		wrapper.registerMessage(TEPylonDestructorPacket.Handler.class, TEPylonDestructorPacket.class, 1, Side.CLIENT);
		//Flywheel rotation for industrial generator rendering
		wrapper.registerMessage(TEIGeneratorPacket.Handler.class, TEIGeneratorPacket.class, 2, Side.CLIENT);
		//Machine type for marker rendering
		wrapper.registerMessage(TEStructurePacket.Handler.class, TEStructurePacket.class, 3, Side.CLIENT);
		//Mingin drill rotation for rendering
		wrapper.registerMessage(TEDrillPacket.Handler.class, TEDrillPacket.class, 4, Side.CLIENT);
	}
	
}
