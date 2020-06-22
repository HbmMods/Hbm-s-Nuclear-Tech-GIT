package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTeleLink extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_,
			float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (player.isSneaking()) {
			TileEntity te = world.getTileEntity(x, y, z);

			if (te != null && te instanceof TileEntityMachineTeleporter
					&& world.getBlock(x, y, z) == ModBlocks.machine_teleporter) {

				if (stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();

					stack.stackTagCompound.setInteger("x", x);
					stack.stackTagCompound.setInteger("y", y);
					stack.stackTagCompound.setInteger("z", z);

					if (world.isRemote)
						player.addChatMessage(new ChatComponentText(
								"[TeleLink] Set teleporter exit to " + x + ", " + y + ", " + z + "."));
				} else {
					int x1 = stack.stackTagCompound.getInteger("x");
					int y1 = stack.stackTagCompound.getInteger("y");
					int z1 = stack.stackTagCompound.getInteger("z");

					if (world.getBlock(x1, y1, z1) == ModBlocks.machine_teleporter
							&& world.getTileEntity(x1, y1, z1) != null
							&& world.getTileEntity(x1, y1, z1) instanceof TileEntityMachineTeleporter) {

						((TileEntityMachineTeleporter) te).mode = true;
						((TileEntityMachineTeleporter) te).targetX = x1;
						((TileEntityMachineTeleporter) te).targetY = y1;
						((TileEntityMachineTeleporter) te).targetZ = z1;
						((TileEntityMachineTeleporter) te).linked = true;
						((TileEntityMachineTeleporter) world.getTileEntity(x1, y1, z1)).linked = true;

						if (world.isRemote)
							player.addChatMessage(
									new ChatComponentText("[TeleLink] Teleporters have been successfully linked."));

						stack.stackTagCompound = null;
					} else {
						if (world.isRemote)
							player.addChatMessage(new ChatComponentText(
									"[TeleLink] Warning: Exit teleporter has been destroyed while linking. Values have been reset."));
						stack.stackTagCompound = null;
					}
				}

				player.swingItem();
				return true;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (itemstack.stackTagCompound != null) {
			list.add("Teleporter Exit x: " + itemstack.stackTagCompound.getInteger("x"));
			list.add("Teleporter Exit y: " + itemstack.stackTagCompound.getInteger("y"));
			list.add("Teleporter Exit z: " + itemstack.stackTagCompound.getInteger("z"));
		} else {
			list.add("Select teleporter exit first!");
			list.add("Right-click teleporter while sneaking.");
		}
	}

}
