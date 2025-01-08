package com.hbm.packet;

import com.hbm.lib.RefStrings;
import com.hbm.main.NetworkHandler;
import com.hbm.packet.toclient.*;
import com.hbm.packet.toserver.*;

import cpw.mods.fml.relauncher.Side;

public class PacketDispatcher {

	//Mark 1.5 Packet Sending Device
	public static final NetworkHandler wrapper = new NetworkHandler(RefStrings.MODID);

	public static void registerPackets() {
		int i = 0;

		//Signals server to consume items and create template
		wrapper.registerMessage(ItemFolderPacket.Handler.class, ItemFolderPacket.class, i++, Side.SERVER);
		//Siren packet for looped sounds
		wrapper.registerMessage(TESirenPacket.Handler.class, TESirenPacket.class, i++, Side.CLIENT);
		//Signals server to change ItemStacks
		wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
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
		//Packet for force fields
		wrapper.registerMessage(TEFFPacket.Handler.class, TEFFPacket.class, i++, Side.CLIENT);
		//Sends button information for ItemGunBase
		wrapper.registerMessage(GunButtonPacket.Handler.class, GunButtonPacket.class, i++, Side.SERVER);
		//Signals server to buy offer from bobmazon
		wrapper.registerMessage(ItemBobmazonPacket.Handler.class, ItemBobmazonPacket.class, i++, Side.SERVER);
		//Packet to send missile multipart information to TEs
		wrapper.registerMessage(TEMissileMultipartPacket.Handler.class, TEMissileMultipartPacket.class, i++, Side.CLIENT);
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
		//Sends a funi text to display like a music disc announcement
		wrapper.registerMessage(TEDoorAnimationPacket.Handler.class, TEDoorAnimationPacket.class, i++, Side.CLIENT);
		//Does ExVNT standard player knockback
		wrapper.registerMessage(ExplosionKnockbackPacket.Handler.class, ExplosionKnockbackPacket.class, i++, Side.CLIENT);
		//just go fuck yourself already
		wrapper.registerMessage(ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket.Handler.class, ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket.class, i++, Side.CLIENT);
		//Packet to send NBT data from clients to the serverside held item
		wrapper.registerMessage(NBTItemControlPacket.Handler.class, NBTItemControlPacket.class, i++, Side.SERVER);
		//General syncing for global values
		wrapper.registerMessage(PermaSyncPacket.Handler.class, PermaSyncPacket.class, i++, Side.CLIENT);
		//Syncs biome information for single positions or entire chunks
		wrapper.registerMessage(BiomeSyncPacket.Handler.class, BiomeSyncPacket.class, i++, Side.CLIENT);
		//The not-so-convenient but not laggy one
		wrapper.registerMessage(BufPacket.Handler.class, BufPacket.class, i++, Side.CLIENT);
	}

}
