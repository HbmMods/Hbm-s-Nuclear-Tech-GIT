package com.hbm.items.special;

import java.util.List;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPolaroid extends Item {
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	if(entity instanceof EntityPlayer)
    		if(((EntityPlayer)entity).getHealth() < 10F) {
    			((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 2));
    		}
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Fate chosen");
		list.add("");
		switch(MainRegistry.polaroidID) {
		case 1: 
			list.add("...");
			break;
		case 2: 
			list.add("Clear as glass.");
			break;
		case 3: 
			list.add("'M");
			break;
		case 4: 
			list.add("It's about time.");
			break;
		case 5: 
			list.add("If you stare long into the abyss, the abyss stares back.");
			break;
		case 6: 
			list.add("public Party celebration = new Party();");
			break;
		case 7: 
			list.add("V urnerq lbh yvxr EBG13!");
			break;
		case 8: 
			list.add("11011100");
			break;
		case 9: 
			list.add("Vg'f nobhg gvzr.");
			break;
		case 10: 
			list.add("Schrabidium dislikes the breeding reactor.");
			break;
		case 11: 
			list.add("yss stares back.6public Party cel");
			break;
		case 12: 
			list.add("Red streaks.");
			break;
		case 13: 
			list.add("Q1");
			break;
		case 14: 
			list.add("Q4");
			break;
		case 15: 
			list.add("Q3");
			break;
		case 16: 
			list.add("Q2");
			break;
		case 17: 
			list.add("Two friends before christmas.");
			break;
		case 18: 
			list.add("Duchess of the boxcars.");
			list.add("");
			list.add("\"P.S.: Thirty-one.\"");
			list.add("\"Huh, what does thirty-one mean?\"");
			break;
		}
	}

}
