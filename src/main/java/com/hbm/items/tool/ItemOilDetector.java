package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ServerProxy;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemOilDetector extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(I18n.format(this.getUnlocalizedName() + ".desc1"));
		list.add(I18n.format(this.getUnlocalizedName() + ".desc2"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(!world.isRemote) {
			boolean direct = false;
			int x = MathHelper.floor_double(player.posX);
			int y = MathHelper.floor_double(player.posY);
			int z = MathHelper.floor_double(player.posZ);

			Block reserve;

			if((reserve = searchDirect(world, x, y, z)) != null) {
				direct = true;
			} else {
				reserve = search(world, x, y, z);
			}

			String reserveType = "";
			if(reserve == ModBlocks.ore_gas)
				reserveType = "_gas";
						
			if(direct) {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".bullseye" + reserveType).color(EnumChatFormatting.DARK_GREEN).flush(), ServerProxy.ID_DETONATOR), (EntityPlayerMP) player);
			} else if(reserve != null) {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".detected" + reserveType).color(EnumChatFormatting.GOLD).flush(), ServerProxy.ID_DETONATOR), (EntityPlayerMP) player);
			} else {
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + ".noOil").color(EnumChatFormatting.RED).flush(), ServerProxy.ID_DETONATOR), (EntityPlayerMP) player);
			}
		}

		world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
	}

	private Block search(World world, int x, int y, int z) {
		Block reserve;
		if((reserve = searchDirect(world, x + 5, y, z)) != null) return reserve;
		if((reserve = searchDirect(world, x - 5, y, z)) != null) return reserve;
		if((reserve = searchDirect(world, x, y, z + 5)) != null) return reserve;
		if((reserve = searchDirect(world, x, y, z - 5)) != null) return reserve;
		
		if((reserve = searchDirect(world, x + 10, y, z)) != null) return reserve;
		if((reserve = searchDirect(world, x - 10, y, z)) != null) return reserve;
		if((reserve = searchDirect(world, x, y, z + 10)) != null) return reserve;
		if((reserve = searchDirect(world, x, y, z - 10)) != null) return reserve;
		
		if((reserve = searchDirect(world, x + 5, y, z + 5)) != null) return reserve;
		if((reserve = searchDirect(world, x - 5, y, z + 5)) != null) return reserve;
		if((reserve = searchDirect(world, x + 5, y, z - 5)) != null) return reserve;
		if((reserve = searchDirect(world, x - 5, y, z - 5)) != null) return reserve;

		return null;
	}

	private Block searchDirect(World world, int x, int y, int z) {
		for(int i =  y + 15; i > 5; i--) {
			Block block = world.getBlock(x, i, z);
			if(block == ModBlocks.ore_oil) return block;
			if(block == ModBlocks.ore_gas) return block;
		}

		return null;
	}

}
