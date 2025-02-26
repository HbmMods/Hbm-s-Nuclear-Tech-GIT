package com.hbm.items.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.neutron.RBMKNeutronHandler.RBMKNeutronNode;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemRBMKLid extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {

		Block b = world.getBlock(x, y, z);

		if(b instanceof RBMKBase) {
			RBMKBase rbmk = (RBMKBase) b;

			int[] pos = rbmk.findCore(world, x, y, z);

			if(pos == null)
				return false;

			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityRBMKBase))
				return false;

			TileEntityRBMKBase tile = (TileEntityRBMKBase) te;

			if(tile.hasLid())
				return false;

			RBMKNeutronNode node = (RBMKNeutronNode) NeutronNodeWorld.getNode(world, new BlockPos(te));
			if(node != null)
				node.addLid();

			int meta = RBMKBase.DIR_NORMAL_LID.ordinal();

			if(this == ModItems.rbmk_lid_glass) {
				meta = RBMKBase.DIR_GLASS_LID.ordinal();
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Blocks.glass.stepSound.func_150496_b(), (Blocks.glass.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.glass.stepSound.getPitch() * 0.8F);
			} else {
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, ModBlocks.concrete_smooth.stepSound.func_150496_b(), (ModBlocks.concrete_smooth.stepSound.getVolume() + 1.0F) / 2.0F, ModBlocks.concrete_smooth.stepSound.getPitch() * 0.8F);
			}

			world.setBlockMetadataWithNotify(pos[0], pos[1], pos[2], meta + RBMKBase.offset, 3);
			stack.stackSize--;

			return true;
		}

		return false;
	}
}
