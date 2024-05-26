package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCircuit extends ItemEnumMulti {

	public ItemCircuit() {
		super(EnumCircuitType.class, true, true);
	}

	public static enum EnumCircuitType {
		VACUUM_TUBE,
		CAPACITOR,
		CAPACITOR_TANTALIUM,
		PCB,
		SILICON,
		CHIP,
		CHIP_BISMOID,
		ANALOG,
		BASIC,
		ADVANCED,
		CAPACITOR_BOARD,
		BISMOID,
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		/*List<String> lines = new ArrayList();
		
		switch(stack.getItemDamage()) {
		case 0: lines.add("We taught this filament how to think."); break;
		case 1: lines.add("3300µF"); break;
		case 2: lines.add("Sorry, we were out of flux capacitors, this is a regular one."); break;
		case 3: lines.add("Laminated Sandwich Structure™"); break;
		case 4: lines.add("Microscopic arcane sigils have given this rock anima."); break;
		case 5: lines.add("Less tasty than it sounds."); break;
		case 6: lines.add("The ALU is probably wired together correctly. Probably."); break;
		case 7: lines.add("One final act of goodwill."); lines.add("If I have to hear the words \"Interplay\" or"); lines.add("\"objectively better\" one more time I'll blow"); lines.add("up Chris Avellone with a bazooka."); break;
		case 8: lines.add("100% lead solder (not RoHS compliant)"); break;
		case 9: lines.add("It's red, that means it's better."); break;
		case 10: lines.add("Uses that exceptionally stanky 90s yellow PCB."); break;
		case 11: lines.add("Can do up to three different things (instead of two)!"); break;
		}
		
		for(String line : lines) {
			list.add(EnumChatFormatting.ITALIC + line);
		}*/
	}
}
