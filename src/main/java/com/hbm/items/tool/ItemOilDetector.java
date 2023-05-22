package com.hbm.items.tool;

import java.util.List;

import com.EconomyPlus.compatibility.xradar.dataStructures.TerritoryCluster;
import com.EconomyPlus.compatibility.xradar.dataStructures.TerritoryCluster.ProvinceHandler;
import com.EconomyPlus.compatibility.xradar.nodes.OilResource;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemOilDetector extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18n.format(this.getUnlocalizedName() + ".desc1"));
		list.add(I18n.format(this.getUnlocalizedName() + ".desc2"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		boolean oil = false;
		boolean direct = false;
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;

		ProvinceHandler p = TerritoryCluster.instance().getProvinceFromBlockCoords(x, z, player.dimension);
		direct = p.exists() && p.hasResource() && p.getResource() instanceof OilResource;
		
		if(direct)
			oil = true;
		
		if(!world.isRemote) {
						
			if(direct) {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".bullseye").color(EnumChatFormatting.DARK_GREEN).flush(), MainRegistry.proxy.ID_DETONATOR), (EntityPlayerMP) player);
			} else if(oil) {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".detected").color(EnumChatFormatting.GOLD).flush(), MainRegistry.proxy.ID_DETONATOR), (EntityPlayerMP) player);
			} else {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".noOil").color(EnumChatFormatting.RED).flush(), MainRegistry.proxy.ID_DETONATOR), (EntityPlayerMP) player);
			}
		}

		world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
		
	}

}
