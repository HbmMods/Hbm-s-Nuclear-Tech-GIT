package com.hbm.items.tool;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemFertilizer extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		if(!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		}
		
		world.captureBlockSnapshots = false; //what the fuck is the point of this?!
		
		boolean didSomething = false;
		
		for(int i = x - 1; i <= x + 1; i++) {
			for(int j = y - 1; j <= y + 1; j++) {
				for(int k = z - 1; k <= z + 1; k++) {
					boolean success = fertilize(world, i, j, k, player, i == x && j == y && k == z);
					didSomething = didSomething || success;
					if(success && !world.isRemote) {
						world.playAuxSFX(2005, i, j, k, 0);
					}
				}
			}
		}

		if(didSomething && !player.capabilities.isCreativeMode) {
			stack.stackSize--;
		}

		return false;
	}

	public static boolean useFertillizer(ItemStack stack, World world, int x, int y, int z) {

		if(!(world instanceof WorldServer)) return false;
		EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer)world);
		
		boolean didSomething = false;
		
		for(int i = x - 1; i <= x + 1; i++) {
			for(int j = y - 1; j <= y + 1; j++) {
				for(int k = z - 1; k <= z + 1; k++) {
					boolean success = fertilize(world, i, j, k, player, i == x && j == y && k == z);
					didSomething = didSomething || success;
					if(success && !world.isRemote) {
						world.playAuxSFX(2005, i, j, k, 0);
					}
				}
			}
		}
		
		if(didSomething) stack.stackSize--;
		
		return didSomething;
	}
	
	public static boolean fertilize(World world, int x, int y, int z, EntityPlayer player, boolean force) {
		
		Block b = world.getBlock(x, y, z);

		BonemealEvent event = new BonemealEvent(player, world, b, x, y, z);
		if(MinecraftForge.EVENT_BUS.post(event)) {
			return false;
		}

		if(event.getResult() == Result.ALLOW) {
			return true;
		}

		if(b instanceof IGrowable) {
			IGrowable growable = (IGrowable) b;

			if(growable.func_149851_a(world, x, y, z, world.isRemote)) {
				
				if(!world.isRemote) {
					if(force || growable.func_149852_a(world, world.rand, x, y, z)) {
						growable.func_149853_b(world, world.rand, x, y, z);
					}
				}
				
				return true;
			}
		}
		
		return false;
	}
}
