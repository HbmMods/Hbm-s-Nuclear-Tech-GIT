package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.siege.EntitySiegeTunneler;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemKitCustom;
import com.hbm.lib.Library;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);
		
		if(pos != null) {
			
			/*EntitySiegeTunneler tunneler = new EntitySiegeTunneler(world);
			tunneler.setPosition(pos.blockX, pos.blockY + 1, pos.blockZ);
			tunneler.onSpawnWithEgg(null);
			world.spawnEntityInWorld(tunneler);*/
			
			//CellularDungeonFactory.meteor.generate(world, x, y, z, world.rand);
			
			int r = 5;
			
			int x = pos.blockX;
			int y = pos.blockY;
			int z = pos.blockZ;
			for(int i = x - r; i <= x + r; i++) {
				for(int j = y - r; j <= y + r; j++) {
					for(int k = z - r; k <= z + r; k++) {
						if(world.getBlock(i, j, k) == ModBlocks.concrete_super)
							world.getBlock(i, j, k).updateTick(world, i, j, k, world.rand);
					}
				}
			}
			
			//new Bunker().generate(world, world.rand, x, y, z);
			
			/*EntityBlockSpider spider = new EntityBlockSpider(world);
			spider.setPosition(x + 0.5, y, z + 0.5);
			spider.makeBlock(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
			world.setBlockToAir(x, y, z);
			world.spawnEntityInWorld(spider);*/
			
			
    		/*NBTTagCompound data = new NBTTagCompound();
    		data.setString("type", "rift");
    		data.setDouble("posX", x);
    		data.setDouble("posY", y + 1);
    		data.setDouble("posZ", z);
    		
    		MainRegistry.proxy.effectNT(data);*/
			
			//new Spaceship().generate_r0(world, world.rand, x - 4, y, z - 8);

			//new Ruin001().generate_r0(world, world.rand, x, y - 8, z);

			//CellularDungeonFactory.jungle.generate(world, x, y, z, world.rand);
			//CellularDungeonFactory.jungle.generate(world, x, y + 4, z, world.rand);
			//CellularDungeonFactory.jungle.generate(world, x, y + 8, z, world.rand);
			
			//new AncientTomb().build(world, world.rand, x, y + 10, z);
			
			//new ArcticVault().trySpawn(world, x, y, z);
			
			/*for(int ix = x - 10; ix <= x + 10; ix++) {
				for(int iz = z - 10; iz <= z + 10; iz++) {

					if(ix % 2 == 0 && iz % 2 == 0) {
						for(int iy = y; iy < y + 4; iy++)
							world.setBlock(ix, iy, iz, ModBlocks.brick_dungeon_flat);
						world.setBlock(ix, y + 4, iz, ModBlocks.brick_dungeon_tile);
					} else if(ix % 2 == 1 && iz % 2 == 1) {
						world.setBlock(ix, y, iz, ModBlocks.reinforced_stone);
						world.setBlock(ix, y + 1, iz, ModBlocks.spikes);
					} else if(world.rand.nextInt(3) == 0) {
						for(int iy = y; iy < y + 4; iy++)
							world.setBlock(ix, iy, iz, ModBlocks.brick_dungeon_flat);
						world.setBlock(ix, y + 4, iz, ModBlocks.brick_dungeon_tile);
					} else {
						world.setBlock(ix, y, iz, ModBlocks.reinforced_stone);
						world.setBlock(ix, y + 1, iz, ModBlocks.spikes);
					}
				}
			}*/
		}
		
		return stack;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");
	}
}
