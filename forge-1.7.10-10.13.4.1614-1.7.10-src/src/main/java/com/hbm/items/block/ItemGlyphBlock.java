package com.hbm.items.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemGlyphBlock extends ItemBlock {

	public ItemGlyphBlock(Block block) {
		super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		switch(itemstack.getItemDamage()) {
		case 0: list.add("Hourglass"); break;
		case 1: list.add("Eye"); break;
		case 2: list.add("'Pillar'"); break;
		case 3: list.add("IOI"); break;
		case 4: list.add("Delta"); break;
		case 5: list.add("VTPC"); break;
		case 6: list.add("Cool S"); break;
		case 7: list.add("Trefoil"); break;
		case 8: list.add("Pony"); break;
		case 9: list.add("Sparkle"); break;
		case 10: list.add("PiP"); break;
		case 11: list.add("Triangles"); break;
		case 12: list.add("Linux Mint"); break;
		case 13: list.add("13"); break;
		case 14: list.add("Digamma"); break;
		case 15: list.add("Celestial Altar"); break;
		}
	}
	
    public int getMetadata(int meta)
    {
        return meta;
    }
}
