package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;

import api.hbm.block.IToolable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGraphiteDrilled extends BlockGraphiteDrilledBase implements IToolable {
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(player.getHeldItem() != null) {
			
			int meta = world.getBlockMetadata(x, y, z);
			int cfg = meta & 3;
			
			if(side == cfg * 2 || side == cfg * 2 + 1) {
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_uranium, ModBlocks.block_graphite_fuel)) return true;
				if(checkInteraction(world, x, y, z, meta | 8, player, ModItems.pile_rod_pu239, ModBlocks.block_graphite_fuel)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_plutonium, ModBlocks.block_graphite_plutonium)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_source, ModBlocks.block_graphite_source)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_boron, ModBlocks.block_graphite_rod)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_lithium, ModBlocks.block_graphite_lithium)) return true;
				if(checkInteraction(world, x, y, z, meta, player, ModItems.cell_tritium, ModBlocks.block_graphite_tritium)) return true; //if you want to i guess?
				if(checkInteraction(world, x, y, z, meta, player, ModItems.pile_rod_detector, ModBlocks.block_graphite_detector)) return true;
				if(meta >> 2 != 1) {
					if(checkInteraction(world, x, y, z, meta | 4, player, ModItems.shell, ModBlocks.block_graphite_drilled)) return true;
					if(checkInteraction(world, x, y, z, 0, player, ModItems.ingot_graphite, ModBlocks.block_graphite)) return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkInteraction(World world, int x, int y, int z, int meta, EntityPlayer player, Item item, Block block) {
		
		if(player.getHeldItem().getItem() == item) {
			
			if(item == ModItems.shell && player.getHeldItem().getItemDamage() != Mats.MAT_ALUMINIUM.id) return false; //shitty workaround
			
			player.getHeldItem().stackSize--;
			world.setBlock(x, y, z, block, meta, 3);

			world.playSoundEffect(x + 0.5, y + 1.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		int meta = world.getBlockMetadata(x, y, z);
		int cfg = meta & 3;
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote && (side == cfg * 2 || side == cfg * 2 + 1) && meta >> 2 == 1) {
			world.setBlock(x, y, z, ModBlocks.block_graphite_drilled, cfg, 3);
			world.playSoundEffect(x + 0.5, y + 1.5, z + 0.5, "hbm:item.upgradePlug", 1.0F, 0.85F);

			BlockGraphiteRod.ejectItem(world, x, y, z, ForgeDirection.getOrientation(side), new ItemStack(ModItems.shell, 1, Mats.MAT_ALUMINIUM.id));
		}
		
		return true;
	}
}
