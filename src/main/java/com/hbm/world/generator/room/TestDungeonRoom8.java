package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TestDungeonRoom8 extends CellularDungeonRoom {

	public TestDungeonRoom8(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 - 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 + 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 3, y + 1, z + parent.width / 2 + 3, 1, parent.height - 2, 1, ModBlocks.meteor_pillar);
		world.setBlock(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 - 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 - 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 + 3, y + 3, z + parent.width / 2 + 3, ModBlocks.meteor_brick_chiseled, 0, 2);
		world.setBlock(x + parent.width / 2 - 3, y + 3, z + parent.width / 2 + 3, ModBlocks.meteor_brick_chiseled, 0, 2);

		DungeonToolbox.generateBox(world, x + 4, y + 1, z + 4, parent.width - 8, 1, parent.width - 8, ModBlocks.meteor_polished);
		
		int i = world.rand.nextInt(8);
		
		switch(i) {
		case 0: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.meteor_brick_chiseled, 0, 3); break;
		case 1: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.ntm_dirt, 0, 3); break;
		case 2: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.block_starmetal, 0, 3); break;
		case 3: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.statue_elb_f, 0, 3); break;
		case 4: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.crate_red, 0, 3); break;
		case 5: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.balefire, 0, 3); break;
		case 6: world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.block_meteor, 0, 3); break;
		case 7:
			world.setBlock(x + parent.width / 2, y + 2, z + parent.width / 2, ModBlocks.safe, 0, 3);
			if(world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2) instanceof TileEntitySafe) {
				
				if(world.rand.nextInt(10) == 0)
					((TileEntitySafe)world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2)).setInventorySlotContents(7, new ItemStack(ModItems.book_of_));
				else
					((TileEntitySafe)world.getTileEntity(x + parent.width / 2, y + 2, z + parent.width / 2)).setInventorySlotContents(7, new ItemStack(Items.book));
			}
			break;
		}
		
		/*world.setBlock(x + parent.width / 2, y, z + parent.width / 2, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(x + parent.width / 2, y, z + parent.width / 2);

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.func_145881_a().setEntityName("entity_cyber_crab");
        }*/
	}
}
