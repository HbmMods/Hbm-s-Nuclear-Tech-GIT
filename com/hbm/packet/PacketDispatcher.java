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
		int i = 0;
		
		//Packet sent for every connected electricity pole, for wire rendering
		wrapper.registerMessage(TEPylonSenderPacket.Handler.class, TEPylonSenderPacket.class, i++, Side.CLIENT);
		//Resets connection list in client-sided pole rendering
		wrapper.registerMessage(TEPylonDestructorPacket.Handler.class, TEPylonDestructorPacket.class, i++, Side.CLIENT);
		//Flywheel rotation for industrial generator rendering
		wrapper.registerMessage(TEIGeneratorPacket.Handler.class, TEIGeneratorPacket.class, i++, Side.CLIENT);
		//Machine type for marker rendering
		wrapper.registerMessage(TEStructurePacket.Handler.class, TEStructurePacket.class, i++, Side.CLIENT);
		//Mining drill rotation for rendering
		wrapper.registerMessage(TEDrillPacket.Handler.class, TEDrillPacket.class, i++, Side.CLIENT);
		//Mining drill torque for sounds
		wrapper.registerMessage(TEDrillSoundPacket.Handler.class, TEDrillSoundPacket.class, i++, Side.CLIENT);
		//Assembler cog rotation for rendering
		wrapper.registerMessage(TEAssemblerPacket.Handler.class, TEAssemblerPacket.class, i++, Side.CLIENT);
		//Fluid type for pipe rendering
		wrapper.registerMessage(TEFluidPipePacket.Handler.class, TEFluidPipePacket.class, i++, Side.CLIENT);
		//Missile type for rendering
		wrapper.registerMessage(TEMissilePacket.Handler.class, TEMissilePacket.class, i++, Side.CLIENT);
		//Fluid packet for GUI
		wrapper.registerMessage(TEFluidPacket.Handler.class, TEFluidPacket.class, i++, Side.CLIENT);
		//Sound packet that keeps client and server seperated
		wrapper.registerMessage(LoopedSoundPacket.Handler.class, LoopedSoundPacket.class, i++, Side.CLIENT);
	}
	
}
