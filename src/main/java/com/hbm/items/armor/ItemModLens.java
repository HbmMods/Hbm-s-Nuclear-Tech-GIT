package com.hbm.items.armor;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ISatChip;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteScanner;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemModLens extends ItemArmorMod implements ISatChip {

	public ItemModLens() {
		super(ArmorModHandler.extra, true, false, false, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + "Satellite Frequency: " + this.getFreq(itemstack));
		list.add("");

		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.AQUA + "  " + stack.getDisplayName() + " (Freq: " + getFreq(stack) + ")");
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		World world = entity.worldObj;
		if(world.isRemote) return;
		if(!(entity instanceof EntityPlayerMP)) return;

		EntityPlayerMP player = (EntityPlayerMP) entity;
		ItemStack lens = ArmorModHandler.pryMods(armor)[ArmorModHandler.extra];

		if(lens == null) return;

		int freq = this.getFreq(lens);
		Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(freq);
		if(!(sat instanceof SatelliteScanner)) return;

		int x = (int) Math.floor(player.posX);
		int y = (int) Math.floor(player.posY);
		int z = (int) Math.floor(player.posZ);
		int range = 3;

		int cX = x >> 4;
		int cZ = z >> 4;

		int height = Math.max(Math.min(y + 10, 255), 64);
		int seg = (int) (world.getTotalWorldTime() % height);

		int hits = 0;

		for(int chunkX = cX - range; chunkX <= cX + range; chunkX++) {
			for(int chunkZ = cZ - range; chunkZ <= cZ + range; chunkZ++) {
				Chunk c = world.getChunkFromChunkCoords(chunkX, chunkZ);

				for(int ix = 0; ix < 16; ix++) {
					for(int iz = 0; iz < 16; iz++) {

						Block b = c.getBlock(ix, seg, iz);
						int aX = (chunkX << 4) + ix;
						int aZ = (chunkZ << 4) + iz;

						if(addIf(ModBlocks.ore_alexandrite, b, 1, aX, seg, aZ, "Alexandrite", 0x00ffff, player)) hits++;
						if(addIf(ModBlocks.ore_oil, b, 300, aX, seg, aZ, "Oil", 0xa0a0a0, player)) hits++;
						if(addIf(ModBlocks.ore_bedrock_oil, b, 300, aX, seg, aZ, "Bedrock Oil", 0xa0a0a0, player)) hits++;
						if(addIf(ModBlocks.ore_coltan, b, 5, aX, seg, aZ, "Coltan", 0xa0a000, player)) hits++;
						if(addIf(ModBlocks.stone_gneiss, b, 5000, aX, seg, aZ, "Schist", 0x8080ff, player)) hits++;
						if(addIf(ModBlocks.ore_australium, b, 1000, aX, seg, aZ, "Australium", 0xffff00, player)) hits++;
						if(addIf(Blocks.end_portal_frame, b, 1, aX, seg, aZ, "End Portal", 0x40b080, player)) hits++;
						if(addIf(ModBlocks.volcano_core, b, 1, aX, seg, aZ, "Volcano Core", 0xff4000, player)) hits++;
						if(addIf(ModBlocks.pink_log, b, 1, aX, seg, aZ, "Pink Log", 0xff00ff, player)) hits++;
						if(addIf(ModBlocks.bobblehead, b, 1, aX, seg, aZ, "A Treasure!", 0xff0000, player)) hits++;
						if(addIf(ModBlocks.deco_loot, b, 1, aX, seg, aZ, null, 0x800000, player)) hits++;
						if(addIf(ModBlocks.crate_ammo, b, 1, aX, seg, aZ, null, 0x800000, player)) hits++;
						if(addIf(ModBlocks.crate_can, b, 1, aX, seg, aZ, null, 0x800000, player)) hits++;
						if(addIf(ModBlocks.ore_bedrock, b, 1, aX, seg, aZ, "Bedrock Ore", 0xff0000, player)) hits++;

						if(hits > 100) return;
					}
				}
			}
		}
	}

	private boolean addIf(Block target, Block b, int chance, int x, int y, int z, String label, int color, EntityPlayerMP player) {

		if(target == b && player.getRNG().nextInt(chance) == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "marker");
			data.setInteger("color", color);
			data.setInteger("expires", 15_000);
			data.setDouble("dist", 300D);
			if(label != null) data.setString("label", label);
			PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(data, x, y, z), player);
			return true;
		}

		return false;
	}
}
