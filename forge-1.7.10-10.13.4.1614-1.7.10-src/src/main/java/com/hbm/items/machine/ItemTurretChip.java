package com.hbm.items.machine;

import java.util.Arrays;
import com.hbm.blocks.bomb.TurretBase;
import com.hbm.tileentity.bomb.TileEntityTurretBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTurretChip extends ItemTurretBiometry {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if((world.getBlock(x, y, z) instanceof TurretBase))
		{
			if(getNames(stack) == null)
				return false;
			
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof TileEntityTurretBase) {
				((TileEntityTurretBase)te).isAI = true;
				((TileEntityTurretBase)te).players = Arrays.asList(getNames(stack));
			}
	        if(world.isRemote)
			{
	        	player.addChatMessage(new ChatComponentText("Transferred turret ownership!"));
			}
			world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
        	
	        return true;
		}
    	
        return false;
    }

}
