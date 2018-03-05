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
		//Sound packet that keeps client and server separated
		wrapper.registerMessage(LoopedSoundPacket.Handler.class, LoopedSoundPacket.class, i++, Side.CLIENT);
		//Chemplant piston rotation for rendering
		wrapper.registerMessage(TEChemplantPacket.Handler.class, TEChemplantPacket.class, i++, Side.CLIENT);
		//Turret rotation for rendering
		wrapper.registerMessage(TETurretPacket.Handler.class, TETurretPacket.class, i++, Side.CLIENT);
		//Signals server to consume items and create template
		wrapper.registerMessage(ItemFolderPacket.Handler.class, ItemFolderPacket.class, i++, Side.SERVER);
		//Pumpjack rotation for animation rendering
		wrapper.registerMessage(TEPumpjackPacket.Handler.class, TEPumpjackPacket.class, i++, Side.CLIENT);
		//Turbofan spin for rendering
		wrapper.registerMessage(TETurbofanPacket.Handler.class, TETurbofanPacket.class, i++, Side.CLIENT);
		//Press item for rendering
		wrapper.registerMessage(TEPressPacket.Handler.class, TEPressPacket.class, i++, Side.CLIENT);
		//Electricity gauge for GUI rendering
		wrapper.registerMessage(AuxElectricityPacket.Handler.class, AuxElectricityPacket.class, i++, Side.CLIENT);
		//Electricity gauge for GUI rendering
		wrapper.registerMessage(AuxGaugePacket.Handler.class, AuxGaugePacket.class, i++, Side.CLIENT);
		//Siren packet for looped sounds
		wrapper.registerMessage(TESirenPacket.Handler.class, TESirenPacket.class, i++, Side.CLIENT);
		//Signals server to change ItemStack's
		wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		wrapper.registerMessage(TERadarPacket.Handler.class, TERadarPacket.class, i++, Side.CLIENT);
		//Siren packet for looped sounds
		wrapper.registerMessage(TERadarDestructorPacket.Handler.class, TERadarDestructorPacket.class, i++, Side.CLIENT);
	}
	
}
