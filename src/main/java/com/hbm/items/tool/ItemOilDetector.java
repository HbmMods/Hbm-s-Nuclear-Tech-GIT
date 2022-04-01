package com.hbm.items.tool;

import java.util.List;

import com.LordWeeder.EconomyPlus.compatibility.xradar.nodes.OilResource;
import com.hbm.blocks.ModBlocks;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.tileentity.clowder.TileEntityFlagBig;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
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

		if(ClowderTerritory.territories.containsKey(ClowderTerritory.intsToCode(x/16, z/16))) {
			TerritoryMeta meta =  ClowderTerritory.territories.get(ClowderTerritory.intsToCode(x/16, z/16));
			TileEntityFlagBig flag = (TileEntityFlagBig) world.getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
			direct = (flag != null && flag.nodeResource instanceof OilResource);
		}
		
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z) == ModBlocks.ore_oil)
				direct = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x + 5, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x - 5, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z + 5) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x, i, z - 5) == ModBlocks.ore_oil)
				oil = true;
		
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlock(x + 10, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlock(x - 10, i, z) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlock(x, i, z + 10) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlock(x, i, z - 10) == ModBlocks.ore_oil)
				oil = true;

		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x + 5, i, z + 5) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x - 5, i, z + 5) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x + 5, i, z - 5) == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlock(x - 5, i, z - 5) == ModBlocks.ore_oil)
				oil = true;
		
		if(direct)
			oil = true;
		
		if(!world.isRemote) {
			
			if(direct) {
				player.addChatMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".bullseye").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)));
			} else if(oil) {
				player.addChatMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".detected").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
			} else {
				player.addChatMessage(new ChatComponentTranslation(this.getUnlocalizedName() + ".noOil").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		}

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
		
	}

}
