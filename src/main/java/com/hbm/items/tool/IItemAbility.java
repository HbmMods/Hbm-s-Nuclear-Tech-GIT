package com.hbm.items.tool;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
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
	
	public default void breakBlockSpecial(World world, int x, int y, int z, Block block, int meta, EntityPlayer playerEntity, int fortune, boolean silkTouch) {

		if(world.isAirBlock(x, y, z))
			return;

		if(!(playerEntity instanceof EntityPlayerMP))
			return;
		
		EntityPlayerMP player = (EntityPlayerMP) playerEntity;
		
		if(!ForgeHooks.canHarvestBlock(block, player, meta))
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
				harvestBlock(world, player, x, y, z, block, meta, fortune, silkTouch);
				if(!silkTouch) block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
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
	
	public default void harvestBlock(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, int fortune, boolean silkTouch) {

		player.addStat(StatList.mineBlockStatArray[block.getIdFromBlock(block)], 1);
		player.addExhaustion(0.025F);

		if(block.canSilkHarvest(world, player, x, y, z, meta) && silkTouch) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			//block.createStackedBlock(meta);
			
			/// createStackedBlock ///
			int j = 0;
			Item item = Item.getItemFromBlock(block);
			if(item != null && item.getHasSubtypes()) {
				j = meta;
			}
			ItemStack itemstack = new ItemStack(item, 1, j);
			/// createStackedBlock ///

			if(itemstack != null) {
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, world, block, x, y, z, meta, 0, 1.0f, true, player);
			for(ItemStack is : items) {
				dropBlockAsItem(world, x, y, z, is);
			}
		} else {
			//block.harvesters.set(player);
			block.dropBlockAsItem(world, x, y, z, meta, fortune);
			//block.harvesters.set(null);
		}
	}
	
	public default void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
		if(!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) {
			/*if(captureDrops.get()) {
				capturedDrops.get().add(stack);
				return;
			}*/
			float f = 0.7F;
			double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}
}
