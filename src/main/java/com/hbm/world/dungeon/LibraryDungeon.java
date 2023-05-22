//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import com.hbm.config.GeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class LibraryDungeon extends WorldGenerator
{

	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
 {

		Block blockAbove = world.getBlock(x, y  + 8, z);
		Block blockBelow = world.getBlock(x, y - 1, z);
		
		if(blockAbove.getMaterial().isSolid() && blockBelow.getMaterial().isSolid() && y - 1 > 4)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, x, y, z);
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x, y, z) || !LocationIsValidSpawn(world, x + 8, y, z) || !LocationIsValidSpawn(world, x + 8, y, z + 10) || !LocationIsValidSpawn(world, x, y, z + 10))
		{
			return false;
		}

		world.setBlock(x + 0, y + 0, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 0, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 0, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 0, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 1, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 1, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 1, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 1, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 2, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 2, Blocks.air, 0, 3);

		world.setBlock(x + 6, y + 1, z + 2, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(x + 6, y + 1, z + 2);

        if (tileentitymobspawner != null)
        {
            tileentitymobspawner.func_145881_a().setEntityName(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 6) + ", " + (y + 1) + ", " + (z + 2) + ")");
        }
		world.setBlock(x + 7, y + 1, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 3, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 1, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 4, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 5, Blocks.chest, 5, 3);
        TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(x + 1, y + 1, z + 5);

        if (tileentitychest != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 2, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 6, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 6, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 7, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 8, Blocks.chest, 5, 3);
        TileEntityChest tileentitychest1 = (TileEntityChest)world.getTileEntity(x + 1, y + 1, z + 8);

        if (tileentitychest1 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest1, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 2, y + 1, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 8, Blocks.bedrock, 0, 3);

		world.setBlock(x + 6, y + 1, z + 8, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)world.getTileEntity(x + 6, y + 1, z + 8);

        if (tileentitymobspawner1 != null)
        {
            tileentitymobspawner1.func_145881_a().setEntityName(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 6) + ", " + (y + 1) + ", " + (z + 8) + ")");
        }
		world.setBlock(x + 7, y + 1, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 1, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 1, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 1, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 1, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 1, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 1, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 2, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 2, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 2, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 2, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 2, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 3, Blocks.chest, 5, 3);
        TileEntityChest tileentitychest2 = (TileEntityChest)world.getTileEntity(x + 1, y + 2, z + 3);

        if (tileentitychest2 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest2, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 2, y + 2, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 3, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 2, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 4, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 6, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 6, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 7, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 8, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 2, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 2, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 2, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 2, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 2, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 2, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 3, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 3, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 3, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 3, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 3, Blocks.double_wooden_slab, 0, 3);
		world.setBlock(x + 6, y + 3, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 3, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 6, Blocks.double_wooden_slab, 0, 3);
		world.setBlock(x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 7, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 7, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 7, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 7, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 8, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 8, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 8, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 8, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 3, z + 9, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 3, z + 9, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 3, z + 9, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 4, y + 3, z + 9, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 3, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 3, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 3, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 3, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 3, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 4, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 4, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 4, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 4, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 3, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 4, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 5, Blocks.chest, 5, 3);
        TileEntityChest tileentitychest3 = (TileEntityChest)world.getTileEntity(x + 1, y + 4, z + 5);

        if (tileentitychest3 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest3, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 2, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 5, Blocks.bedrock, 0, 3);

		world.setBlock(x + 3, y + 4, z + 5, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner2 = (TileEntityMobSpawner)world.getTileEntity(x + 3, y + 4, z + 5);

        if (tileentitymobspawner2 != null)
        {
            tileentitymobspawner2.func_145881_a().setEntityName(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 3) + ", " + (y + 4) + ", " + (z + 5) + ")");
        }
		world.setBlock(x + 4, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 6, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 6, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 7, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 8, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 4, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 4, z + 9, Blocks.chest, 2, 3);
        TileEntityChest tileentitychest4 = (TileEntityChest)world.getTileEntity(x + 2, y + 4, z + 9);

        if (tileentitychest4 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest4, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 3, y + 4, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 4, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 4, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 4, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 4, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 4, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 4, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 5, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 5, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 5, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 5, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 1, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 5, z + 1, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 1, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 1, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 2, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 5, z + 2, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 2, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 2, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 5, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 3, Blocks.double_wooden_slab, 0, 3);
		world.setBlock(x + 5, y + 5, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 3, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 4, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 5, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 5, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 2, y + 5, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 5, y + 5, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 5, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 6, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 6, Blocks.double_wooden_slab, 0, 3);
		world.setBlock(x + 5, y + 5, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 6, y + 5, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 5, z + 6, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 5, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 7, Blocks.chest, 5, 3);
        TileEntityChest tileentitychest5 = (TileEntityChest)world.getTileEntity(x + 1, y + 5, z + 7);

        if (tileentitychest5 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest5, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 2, y + 5, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 5, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 5, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 5, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 5, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 8, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 5, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 5, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 5, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 2, y + 5, z + 9, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 3, y + 5, z + 9, getShelf(rand), 0, 3);
		world.setBlock(x + 4, y + 5, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 5, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 5, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 5, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 5, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 5, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 5, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 5, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 6, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 6, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 6, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 6, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 6, y + 6, z + 1, Blocks.chest, 3, 3);
        TileEntityChest tileentitychest6 = (TileEntityChest)world.getTileEntity(x + 6, y + 6, z + 1);

        if (tileentitychest6 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest6, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 7, y + 6, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 6, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 2, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 6, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 3, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 6, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 3, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 6, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 4, Blocks.bedrock, 0, 3);

		world.setBlock(x + 5, y + 6, z + 4, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner tileentitymobspawner3 = (TileEntityMobSpawner)world.getTileEntity(x + 5, y + 6, z + 4);

        if (tileentitymobspawner3 != null)
        {
            tileentitymobspawner3.func_145881_a().setEntityName(this.pickMobSpawner(rand));
        }
        else
        {
            System.err.println("Failed to fetch mob spawner entity at (" + (x + 5) + ", " + (y + 6) + ", " + (z + 4) + ")");
        }
		world.setBlock(x + 6, y + 6, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 4, Blocks.chest, 4, 3);
        TileEntityChest tileentitychest7 = (TileEntityChest)world.getTileEntity(x + 7, y + 6, z + 4);

        if (tileentitychest7 != null)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), tileentitychest7, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
		world.setBlock(x + 8, y + 6, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 5, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 6, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 6, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 6, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 6, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 6, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 6, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 6, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 6, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 6, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 6, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 7, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 7, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 7, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 7, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 1, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 6, y + 7, z + 1, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 7, y + 7, z + 1, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 7, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 2, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 7, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 3, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 7, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 3, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 7, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 4, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 4, Blocks.wooden_slab, 8, 3);
		world.setBlock(x + 8, y + 7, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 5, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 5, getShelf(rand), 0, 3);
		world.setBlock(x + 8, y + 7, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 6, Blocks.fence, 0, 3);
		world.setBlock(x + 5, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 7, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 7, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 7, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 8, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 7, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 2, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 6, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 7, y + 7, z + 9, Blocks.air, 0, 3);
		world.setBlock(x + 8, y + 7, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 7, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 7, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 7, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 8, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 0, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 0, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 0, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 1, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 2, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 3, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 4, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 5, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 6, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 7, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 8, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 1, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 9, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 0, y + 8, z + 10, Blocks.stonebrick, 3, 3);
		world.setBlock(x + 1, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 2, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 3, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 4, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 5, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 6, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 7, y + 8, z + 10, Blocks.stonebrick, getBrick(rand), 3);
		world.setBlock(x + 8, y + 8, z + 10, Blocks.stonebrick, 3, 3);

		generate_r02_last(world, rand, x, y, z);
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z)
	{

		/*world.setBlock(x + 4, y + 3, z + 2, Blocks.torch, 4, 3);
		world.setBlock(x + 5, y + 3, z + 3, Blocks.torch, 1, 3);
		world.setBlock(x + 5, y + 3, z + 6, Blocks.torch, 1, 3);
		world.setBlock(x + 3, y + 5, z + 3, Blocks.torch, 2, 3);
		world.setBlock(x + 3, y + 5, z + 6, Blocks.torch, 2, 3);
		world.setBlock(x + 4, y + 5, z + 7, Blocks.torch, 3, 3);*/
		world.setBlock(x + 4, y + 3, z + 2, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 3, Blocks.air, 0, 3);
		world.setBlock(x + 3, y + 5, z + 6, Blocks.air, 0, 3);
		world.setBlock(x + 4, y + 5, z + 7, Blocks.air, 0, 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned library at " + x + " " + y +" " + z + "\n");
		return true;

	}
	public int getBrick(Random rand) {
		return rand.nextInt(3);
		
	}
	public Block getShelf(Random rand) {
		int i = rand.nextInt(2);
		if(i == 0)
		{
			return Blocks.planks;
		}
		return Blocks.bookshelf;
	}
    private String pickMobSpawner(Random p_76543_1_)
    {
        return DungeonHooks.getRandomDungeonMob(p_76543_1_);
    }

}