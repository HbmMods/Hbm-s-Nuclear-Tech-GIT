package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.ChatBuilder;

import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
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
		
		if((te instanceof IEnergyConductor)) {
			
			IEnergyConductor con = (IEnergyConductor) te;
			IPowerNet net = con.getPowerNet();
			
			if(net == null) {
				player.addChatComponentMessage(ChatBuilder.start("Error: No network found! This should be impossible!").color(EnumChatFormatting.RED).flush());
				return true;
			}
			
			if(!(net instanceof PowerNet)) {
				player.addChatComponentMessage(ChatBuilder.start("Error: Cannot print diagnostic for non-standard power net implementation!").color(EnumChatFormatting.RED).flush());
			}
			
			PowerNet network = (PowerNet) net;
			String id = Integer.toHexString(net.hashCode());
	
			player.addChatComponentMessage(ChatBuilder.start("Start of diagnostic for network " + id).color(EnumChatFormatting.GOLD).flush());
			player.addChatComponentMessage(ChatBuilder.start("Links: " + network.getLinks().size()).color(EnumChatFormatting.YELLOW).flush());
			player.addChatComponentMessage(ChatBuilder.start("Proxies: " + network.getProxies().size()).color(EnumChatFormatting.YELLOW).flush());
			player.addChatComponentMessage(ChatBuilder.start("Subscribers: " + network.getSubscribers().size()).color(EnumChatFormatting.YELLOW).flush());
			player.addChatComponentMessage(ChatBuilder.start("End of diagnostic for network " + id).color(EnumChatFormatting.GOLD).flush());
			
			for(IEnergyConductor link : network.getLinks()) {
				Vec3 pos = link.getDebugParticlePos();
				
				boolean errored = link.getPowerNet() != net;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debug");
				data.setInteger("color", errored ? 0xff0000 : 0xffff00);
				data.setFloat("scale", 0.5F);
				data.setString("text", id);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord, pos.yCoord, pos.zCoord), new TargetPoint(world.provider.dimensionId, pos.xCoord, pos.yCoord, pos.zCoord, radius));
			}
			
			for(IEnergyConnector subscriber : network.getSubscribers()) {
				Vec3 pos = subscriber.getDebugParticlePos();
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debug");
				data.setInteger("color", 0x0000ff);
				data.setFloat("scale", 1.5F);
				data.setString("text", id);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord, pos.yCoord, pos.zCoord), new TargetPoint(world.provider.dimensionId, pos.xCoord, pos.yCoord, pos.zCoord, radius));
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
