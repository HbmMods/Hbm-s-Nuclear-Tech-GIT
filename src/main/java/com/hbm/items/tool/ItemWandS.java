package com.hbm.items.tool;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.world.gen.nbt.NBTStructure;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemWandS extends Item {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Creative-only item");
		list.add("\"Replication breeds decadence\"");
		list.add("(Saves an area defined by two right-clicks,");
		list.add("adds a block to the blacklist by crouch right-clicking!)");

		if(stack.stackTagCompound != null) {
            int px = stack.stackTagCompound.getInteger("x");
            int py = stack.stackTagCompound.getInteger("y");
            int pz = stack.stackTagCompound.getInteger("z");

			if(px != 0 || py != 0 || pz != 0) {
                list.add(EnumChatFormatting.AQUA + "From: " + px + ", " + py + ", " + pz);
            } else {
                list.add(EnumChatFormatting.AQUA + "No start position set");
            }

            Set<Pair<Block, Integer>> blocks = getBlocks(stack);

            if(blocks.size() > 0) {
                list.add("Blacklist:");
                for(Pair<Block, Integer> block : blocks) {
                    list.add(EnumChatFormatting.RED + "- " + block.key.getUnlocalizedName());
                }
            }
        }
	}

    // why the fuck ye'd leave this whole thing obfuscated is beyond me
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if(player.isSneaking()) {
            Pair<Block, Integer> target = new Pair<Block, Integer>(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
            Set<Pair<Block, Integer>> blocks = getBlocks(stack);

            if(blocks.contains(target)) {
                blocks.remove(target);
                if(world.isRemote) player.addChatMessage(new ChatComponentText("Removed from blacklist " + target.key.getUnlocalizedName()));
            } else {
                blocks.add(target);
                if(world.isRemote) player.addChatMessage(new ChatComponentText("Added to blacklist " + target.key.getUnlocalizedName()));
            }

            setBlocks(stack, blocks);

		} else {
            int px = stack.stackTagCompound.getInteger("x");
            int py = stack.stackTagCompound.getInteger("y");
            int pz = stack.stackTagCompound.getInteger("z");

			if(px == 0 && py == 0 && pz == 0) {
                setPosition(stack, x, y, z);

				if(world.isRemote) player.addChatMessage(new ChatComponentText("First position set!"));
			} else {
                setPosition(stack, 0, 0, 0);

				Set<Pair<Block, Integer>> blocks = getBlocks(stack);
                blocks.add(new Pair<Block, Integer>(Blocks.air, 0));
                blocks.add(new Pair<Block, Integer>(ModBlocks.spotlight_beam, 0));

                String filename = "structure_" + dateFormat.format(new Date()).toString() + ".nbt";

                NBTStructure.saveArea(filename, world, x, y, z, px, py, pz, blocks);

				if(world.isRemote) player.addChatMessage(new ChatComponentText("Structure saved to: .minecraft/structures/" + filename));
			}
		}

        return true;
    }

    private void setPosition(ItemStack stack, int x, int y, int z) {
        stack.stackTagCompound.setInteger("x", x);
        stack.stackTagCompound.setInteger("y", y);
        stack.stackTagCompound.setInteger("z", z);
    }

    private Set<Pair<Block, Integer>> getBlocks(ItemStack stack) {
        if(stack.stackTagCompound == null) {
            return new HashSet<>();
        }

        int[] blockIds = stack.stackTagCompound.getIntArray("blocks");
        int[] metas = stack.stackTagCompound.getIntArray("metas");
        Set<Pair<Block, Integer>> blocks = new HashSet<>(blockIds.length);

        for(int i = 0; i < blockIds.length; i++) {
            blocks.add(new Pair<Block, Integer>(Block.getBlockById(blockIds[i]), metas[i]));
        }

        return blocks;
    }

	@SuppressWarnings("unchecked")
    private void setBlocks(ItemStack stack, Set<Pair<Block, Integer>> blocks) {
        if(stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }

        stack.stackTagCompound.setIntArray("blocks", BobMathUtil.collectionToIntArray(blocks, i -> Block.getIdFromBlock(((Pair<Block, Integer>)i).getKey())));
        stack.stackTagCompound.setIntArray("metas", BobMathUtil.collectionToIntArray(blocks, i -> ((Pair<Block, Integer>)i).getValue()));
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if(player.isSneaking()) {
			stack.stackTagCompound.setIntArray("blocks", new int[0]);
			stack.stackTagCompound.setIntArray("metas", new int[0]);

			if(world.isRemote) {
				player.addChatMessage(new ChatComponentText("Cleared blacklist"));
            }
		}

		return stack;
	}

}