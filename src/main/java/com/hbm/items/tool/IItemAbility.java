package com.hbm.items.tool;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public interface IItemAbility {
	
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack);

	public default void breakExtraBlock(World world, int x, int y, int z, EntityPlayer playerEntity, int refX, int refY, int refZ) {

		if(world.isAirBlock(x, y, z))
			return;

		if(!(playerEntity instanceof EntityPlayerMP))
			return;

		EntityPlayerMP player = (EntityPlayerMP) playerEntity;
		ItemStack stack = player.getHeldItem();

		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if(!canHarvestBlock(block, stack) || block == Blocks.bedrock)
			return;

		Block refBlock = world.getBlock(refX, refY, refZ);
		float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
		float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

		if(!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f || refBlock.getBlockHardness(world, refX, refY, refZ) < 0)
			return;

		BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
		if(event.isCanceled())
			return;

		if(player.capabilities.isCreativeMode) {
			block.onBlockHarvested(world, x, y, z, meta, player);
			if(block.removedByPlayer(world, player, x, y, z, false))
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);

			if(!world.isRemote) {
				player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
			}
			return;
		}

		player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

		if(!world.isRemote) {

			block.onBlockHarvested(world, x, y, z, meta, player);

			if(block.removedByPlayer(world, player, x, y, z, true)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
				block.harvestBlock(world, player, x, y, z, meta);
				block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
			}

			player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));

		} else {
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
			if(block.removedByPlayer(world, player, x, y, z, true)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			}
			ItemStack itemstack = player.getCurrentEquippedItem();
			if(itemstack != null) {
				itemstack.func_150999_a(world, block, x, y, z, player);

				if(itemstack.stackSize == 0) {
					player.destroyCurrentEquippedItem();
				}
			}

			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
		}
	}
}
