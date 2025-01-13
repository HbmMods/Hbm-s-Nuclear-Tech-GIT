package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.util.ChatBuilder;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyConductorMK2;
import api.hbm.energymk2.Nodespace;
import api.hbm.energymk2.Nodespace.PowerNode;
import api.hbm.energymk2.PowerNetMK2;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemPowerNetTool extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {

		Block b = world.getBlock(x, y, z);

		if(b instanceof BlockDummyable) {
			int[] pos = ((BlockDummyable) b).findCore(world, x, y, z);

			if(pos != null) {
				x = pos[0];
				y = pos[1];
				z = pos[2];
			}
		}

		TileEntity te = world.getTileEntity(x, y, z);

		if(world.isRemote)
			return true;

		if((te instanceof IEnergyConductorMK2)) {
			PowerNode node = Nodespace.getNode(world, x, y, z);

			if(node != null && node.hasValidNet()) {

				PowerNetMK2 net = node.net;
				String id = Integer.toHexString(net.hashCode());
				player.addChatComponentMessage(ChatBuilder.start("Start of diagnostic for network " + id).color(EnumChatFormatting.GOLD).flush());
				player.addChatComponentMessage(ChatBuilder.start("Links: " + net.links.size()).color(EnumChatFormatting.YELLOW).flush());
				player.addChatComponentMessage(ChatBuilder.start("Providers: " + net.providerEntries.size()).color(EnumChatFormatting.YELLOW).flush());
				player.addChatComponentMessage(ChatBuilder.start("Receivers: " + net.receiverEntries.size()).color(EnumChatFormatting.YELLOW).flush());
				player.addChatComponentMessage(ChatBuilder.start("End of diagnostic for network " + id).color(EnumChatFormatting.GOLD).flush());

				for(PowerNode link : net.links) {

					for(BlockPos pos : link.positions) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "debug");
						data.setInteger("color", 0xffff00);
						data.setFloat("scale", 0.5F);
						data.setString("text", id);
						PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5), new TargetPoint(world.provider.dimensionId, pos.getX(), pos.getY(), pos.getZ(), radius));
					}
				}

			} else {
				player.addChatComponentMessage(ChatBuilder.start("Error: No network found!").color(EnumChatFormatting.RED).flush());
			}

			return true;
		}

		return false;
	}

	private static final int radius = 20;

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.RED + "Right-click cable to analyze the power net.");
		list.add(EnumChatFormatting.RED + "Links (cables, poles, etc.) are YELLOW");
		list.add(EnumChatFormatting.RED + "Subscribers (any receiver) are BLUE");
		list.add(EnumChatFormatting.RED + "Links with mismatching network info (BUGGED!) are RED");
		list.add(EnumChatFormatting.RED + "Displays stats such as link and subscriber count");
		list.add(EnumChatFormatting.RED + "Proxies are connection points for multiblock links (e.g. 4 for substations)");
		list.add(EnumChatFormatting.RED + "Particles only spawn in a " + radius + " block radius!");
	}
}
