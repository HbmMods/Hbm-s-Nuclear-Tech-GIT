package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBombCaller extends Item {
	
	public ItemBombCaller() {
		super();
        this.setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Aim & click to call an airstrike!");

        if(itemstack.getItemDamage() == 0)
    		list.add("Type: Carpet bombing");
        if(itemstack.getItemDamage() == 1)
    		list.add("Type: Napalm");
        if(itemstack.getItemDamage() == 2)
    		list.add("Type: Poison gas");
        if(itemstack.getItemDamage() == 3)
    		list.add("Type: Agent orange");
        if(itemstack.getItemDamage() == 4)
    		list.add("Type: Atomic bomb");
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
	    	player.addChatMessage(new ChatComponentText("Called in airstrike!"));
	        world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);

	        if(stack.getItemDamage() == 0)
	        	world.spawnEntityInWorld(EntityBomber.statFacCarpet(world, x, y, z));
	        if(stack.getItemDamage() == 1)
	        	world.spawnEntityInWorld(EntityBomber.statFacNapalm(world, x, y, z));
	        if(stack.getItemDamage() == 2)
	        	world.spawnEntityInWorld(EntityBomber.statFacChlorine(world, x, y, z));
	        if(stack.getItemDamage() == 3)
	        	world.spawnEntityInWorld(EntityBomber.statFacOrange(world, x, y, z));
	        if(stack.getItemDamage() == 4)
	        	world.spawnEntityInWorld(EntityBomber.statFacABomb(world, x, y, z));
		}
	    
	    stack.stackSize--;
	    
        return stack;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 2));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 3));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 4));
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
        return p_77636_1_.getItemDamage() == 4;
    }
}
