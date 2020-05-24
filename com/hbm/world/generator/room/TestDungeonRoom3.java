package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class TestDungeonRoom3 extends CellularDungeonRoom {

	public TestDungeonRoom3(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 2, y + 1, z + parent.width / 2 - 2, 5, 4, 5, ModBlocks.deco_lead);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z + parent.width / 2 - 1, 3, 3, 3, ModBlocks.toxic_block);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 4, z + parent.width / 2 - 1, 3, 1, 3, Blocks.air);
		
		world.setBlock(x + parent.width / 2, y + 1, z + parent.width / 2, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(x + parent.width / 2, y + 1, z + parent.width / 2);

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.func_145881_a().setEntityName("entity_cyber_crab");
        }
	}
}
