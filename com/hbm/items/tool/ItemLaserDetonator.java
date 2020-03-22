package com.hbm.items.tool;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.interfaces.IBomb;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLaserDetonator extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Aim & click to detonate!");
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;
		
		
	    if(!world.isRemote)
		{
	    	if(world.getBlock(x, y, z) instanceof IBomb) {
	    		((IBomb)world.getBlock(x, y, z)).explode(world, x, y, z);

	    		if(MainRegistry.enableExtendedLogging)
	    			MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
	    		
	    		player.addChatMessage(new ChatComponentText("Detonated!"));
	        	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
	    	} else {
	    		player.addChatMessage(new ChatComponentText("Target can not be detonated."));
	        	world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
	    	}
		}
    	
        return stack;
    }
}
