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
		//Universal package for machine gauges and states
		wrapper.registerMessage(AuxGaugePacket.Handler.class, AuxGaugePacket.class, i++, Side.CLIENT);
		//Siren packet for looped sounds
		wrapper.registerMessage(TESirenPacket.Handler.class, TESirenPacket.class, i++, Side.CLIENT);
		//Signals server to change ItemStacks
		wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		wrapper.registerMessage(TERadarPacket.Handler.class, TERadarPacket.class, i++, Side.CLIENT);
		//Siren packet for looped sounds
		wrapper.registerMessage(TERadarDestructorPacket.Handler.class, TERadarDestructorPacket.class, i++, Side.CLIENT);
		//Signals server to perform orbital strike, among other things
		wrapper.registerMessage(SatLaserPacket.Handler.class, SatLaserPacket.class, i++, Side.SERVER);
		//Universal package for sending small info packs back to server
		wrapper.registerMessage(AuxButtonPacket.Handler.class, AuxButtonPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		wrapper.registerMessage(TEVaultPacket.Handler.class, TEVaultPacket.class, i++, Side.CLIENT);
		//Packet to send sat info to players
		wrapper.registerMessage(SatPanelPacket.Handler.class, SatPanelPacket.class, i++, Side.CLIENT);
		//Packet to send block break particles
		wrapper.registerMessage(ParticleBurstPacket.Handler.class, ParticleBurstPacket.class, i++, Side.CLIENT);
		//Packet to send chunk radiation info to individual players
		wrapper.registerMessage(ExtPropPacket.Handler.class, ExtPropPacket.class, i++, Side.CLIENT);
		//Entity sound packet that keeps client and server separated
		wrapper.registerMessage(LoopedEntitySoundPacket.Handler.class, LoopedEntitySoundPacket.class, i++, Side.CLIENT);
		//Entity sound packet that keeps client and server separated
		wrapper.registerMessage(TEFFPacket.Handler.class, TEFFPacket.class, i++, Side.CLIENT);
		//Information packet for the reactor control block
		wrapper.registerMessage(TEControlPacket.Handler.class, TEControlPacket.class, i++, Side.CLIENT);
		//Sends button information for ItemGunBase
		wrapper.registerMessage(GunButtonPacket.Handler.class, GunButtonPacket.class, i++, Side.SERVER);
		//Packet to send block break particles
		wrapper.registerMessage(AuxParticlePacket.Handler.class, AuxParticlePacket.class, i++, Side.CLIENT);
		//Signals server to buy offer from bobmazon
		wrapper.registerMessage(ItemBobmazonPacket.Handler.class, ItemBobmazonPacket.class, i++, Side.SERVER);
		//Packet to send missile multipart information to TEs
		wrapper.registerMessage(TEMissileMultipartPacket.Handler.class, TEMissileMultipartPacket.class, i++, Side.CLIENT);
		//Packet to send NBT data to tile entities
		wrapper.registerMessage(NBTPacket.Handler.class, NBTPacket.class, i++, Side.CLIENT);
		//Aux Particle Packet, New Technology: like the APP but with NBT
		wrapper.registerMessage(AuxParticlePacketNT.Handler.class, AuxParticlePacketNT.class, i++, Side.CLIENT);
		//Signals server to do coord based satellite stuff
		wrapper.registerMessage(SatCoordPacket.Handler.class, SatCoordPacket.class, i++, Side.SERVER);
		//Triggers gun animations of the client
		wrapper.registerMessage(GunAnimationPacket.Handler.class, GunAnimationPacket.class, i++, Side.CLIENT);
		//Sends a funi text to display like a music disc announcement
		wrapper.registerMessage(PlayerInformPacket.Handler.class, PlayerInformPacket.class, i++, Side.CLIENT);
		//Universal keybind packet
		wrapper.registerMessage(KeybindPacket.Handler.class, KeybindPacket.class, i++, Side.SERVER);
		//Packet to send NBT data from clients to serverside TEs
		wrapper.registerMessage(NBTControlPacket.Handler.class, NBTControlPacket.class, i++, Side.SERVER);
		//Packet to send for anvil recipes to be crafted
		wrapper.registerMessage(AnvilCraftPacket.Handler.class, AnvilCraftPacket.class, i++, Side.SERVER);
	}
	
}
