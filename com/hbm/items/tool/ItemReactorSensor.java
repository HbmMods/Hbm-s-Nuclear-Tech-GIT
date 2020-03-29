package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemReactorSensor extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_,
			float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		
		Block b = world.getBlock(x, y, z);

		if (b == ModBlocks.machine_reactor_small || b == ModBlocks.dummy_block_reactor_small
				|| b == ModBlocks.dummy_port_reactor_small) {

			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();

			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);

			player.swingItem();
			return true;

		}

		if (b == ModBlocks.reactor_hatch || b == ModBlocks.reactor_ejector
				|| b == ModBlocks.reactor_inserter) {
			
			int meta = world.getBlockMetadata(x, y, z);
			
			switch(meta) {
			case 2: z += 2; break;
			case 3: z -= 2; break;
			case 4: x += 2; break;
			case 5: x -= 2; break;
			}
		}
		
		b = world.getBlock(x, y, z);
		
		if (b == ModBlocks.reactor_computer) {

			if (stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();

			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);

			player.swingItem();
			return true;
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (itemstack.stackTagCompound != null) {
			list.add("x: " + itemstack.stackTagCompound.getInteger("x"));
			list.add("y: " + itemstack.stackTagCompound.getInteger("y"));
			list.add("z: " + itemstack.stackTagCompound.getInteger("z"));
		} else {
			list.add("No reactor selected!");
		}
	}
}
