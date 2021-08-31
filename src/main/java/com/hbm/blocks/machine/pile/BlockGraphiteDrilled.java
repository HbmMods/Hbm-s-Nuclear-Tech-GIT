package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGraphiteDrilled extends BlockGraphiteDrilledBase {

	public BlockGraphiteDrilled(Material mat, int en, int flam) {
		super(mat, en, flam);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.getHeldItem() != null) {
			
			int meta = world.getBlockMetadata(x, y, z);

			if(side == meta * 2 || side == meta * 2 + 1) {
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_uranium, ModBlocks.block_graphite_fuel)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_source, ModBlocks.block_graphite_source)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_boron, ModBlocks.block_graphite_rod)) return true;
				if(checkInteraction(world, x, y, z, 0, player, ModItems.ingot_graphite, ModBlocks.block_graphite)) return true;
			}
		}
		
		return false;
	}
	
	private boolean checkInteraction(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, Block block) {
		
		if(player.getHeldItem().getItem() == item) {
			player.getHeldItem().stackSize--;
			world.setBlock(x, y, z, block, meta, 3);

			world.playSoundEffect(x + 0.5, y + 1.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
			
			return true;
		}
		
		return false;
	}
}
