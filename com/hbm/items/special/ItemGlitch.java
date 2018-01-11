package com.hbm.items.special;

import java.util.List;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGlitch extends Item {

    public ItemGlitch()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(1);
    }

	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		stack.damageItem(5, player);
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("It's a gamble!");
		list.add("");
		switch(MainRegistry.polaroidID) {
		case 1: 
			list.add("Click-click-click!");
			break;
		case 2: 
			list.add("Creek!");
			break;
		case 3: 
			list.add("Bzzzt!");
			break;
		case 4: 
			list.add("TS staring off into space.");
			break;
		case 5: 
			list.add("BANG!!");
			break;
		case 6: 
			list.add("Woop!");
			break;
		case 7: 
			list.add("Poow!");
			break;
		case 8: 
			list.add("Pft!");
			break;
		case 9: 
			list.add("GF fgnevat bss vagb fcnpr.");
			break;
		case 10: 
			list.add("Backup memory #8 on 1.44 million bytes.");
			break;
		case 11: 
			list.add("PTANG!");
			break;
		case 12: 
			list.add("Bzzt-zrrt!");
			break;
		case 13: 
			list.add("Clang, click-brrthththrtrtrtrtrtr!");
			break;
		case 14: 
			list.add("KABLAM!");
			break;
		case 15: 
			list.add("PLENG!");
			break;
		case 16: 
			list.add("Wheeeeeeee-");
			break;
		case 17: 
			list.add("Thump.");
			break;
		case 18: 
			list.add("BANG! Choo-chooo! B A N G ! ! !");
			break;
		}
	}

}
