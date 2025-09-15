package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.hbm.blocks.IBlockSideRotation;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.StructureConfig;
import com.hbm.itempool.ItemPool;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.LootGenerator;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.world.gen.nbt.INBTTileEntityTransformable;
import com.mojang.authlib.GameProfile;

import api.hbm.block.IToolable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class BlockWandLoot extends BlockContainer implements ILookOverlay, IToolable, ITooltipProvider, IBlockSideRotation {

	@SideOnly(Side.CLIENT) protected IIcon iconTop;

	public BlockWandLoot() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":wand_loot");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":wand_loot_top");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return (side <= 1) ? iconTop : blockIcon;
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		if(side == 0) return IBlockSideRotation.topToBottom(world.getBlockMetadata(x, y, z));
		if(side == 1) return world.getBlockMetadata(x, y, z);
		return 0;
	}

	@Override
	public int getRenderType() {
		return IBlockSideRotation.getRenderType();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
        int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 1, 2);

		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof TileEntityWandLoot)) return;
		((TileEntityWandLoot) te).placedRotation = player.rotationYaw;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLoot)) return;

		TileEntityWandLoot loot = (TileEntityWandLoot) te;

		List<String> text = new ArrayList<String>();
		text.add("Will replace with: " + loot.replaceBlock.getUnlocalizedName());
		text.add("   meta: " + loot.replaceMeta);
		text.add("Loot pool: " + loot.poolName);
		if(loot.replaceBlock != ModBlocks.deco_loot) {
			text.add("Minimum items: " + loot.minItems);
			text.add("Maximum items: " + loot.maxItems);
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("Define loot crates/piles in .nbt structures");
		list.add(EnumChatFormatting.GOLD + "Use screwdriver to increase/decrease minimum loot");
		list.add(EnumChatFormatting.GOLD + "Use hand drill to increase/decrease maximum loot");
		list.add(EnumChatFormatting.GOLD + "Use defuser to cycle loot types");
		list.add(EnumChatFormatting.GOLD + "Use container block to set the block that spawns with loot inside");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLoot)) return false;

		TileEntityWandLoot loot = (TileEntityWandLoot) te;

		if(!player.isSneaking()) {

			Block block = getLootableBlock(world, player.getHeldItem());

			if(block != null) {
				loot.replaceBlock = block;
				loot.replaceMeta = player.getHeldItem().getItemDamage();

				List<String> poolNames = loot.getPoolNames(block == ModBlocks.deco_loot);
				if(!poolNames.contains(loot.poolName)) {
					loot.poolName = poolNames.get(0);
				}

				return true;
			}
		}

		return false;
	}

	private Block getLootableBlock(World world, ItemStack stack) {
		if(stack == null) return null;

		if(stack.getItem() instanceof ItemBlock) {
			Block block = ((ItemBlock) stack.getItem()).field_150939_a;

			if(block == ModBlocks.deco_loot) return block;

			if(block instanceof ITileEntityProvider) {
				TileEntity te = ((ITileEntityProvider) block).createNewTileEntity(world, 12);
				if(te instanceof IInventory) return block;
			}
		}

		return null;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityWandLoot)) return false;

		TileEntityWandLoot loot = (TileEntityWandLoot) te;

		switch(tool) {
		case SCREWDRIVER:
			if(player.isSneaking()) {
				loot.minItems--;
				if(loot.minItems < 0) loot.minItems = 0;
			} else {
				loot.minItems++;
				loot.maxItems = Math.max(loot.minItems, loot.maxItems);
			}

			return true;

		case HAND_DRILL:
			if(player.isSneaking()) {
				loot.maxItems--;
				if(loot.maxItems < 0) loot.maxItems = 0;
				loot.minItems = Math.min(loot.minItems, loot.maxItems);
			} else {
				loot.maxItems++;
			}

			return true;

		case DEFUSER:
			List<String> poolNames = loot.getPoolNames(loot.replaceBlock == ModBlocks.deco_loot);
			int index = poolNames.indexOf(loot.poolName);

			index += player.isSneaking() ? -1 : 1;
			index = MathHelper.clamp_int(index, 0, poolNames.size() - 1);

			loot.poolName = poolNames.get(index);

			return true;

		default: return false;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWandLoot();
	}

	public static class TileEntityWandLoot extends TileEntityLoadedBase implements INBTTileEntityTransformable {

		private boolean triggerReplace;

		private Block replaceBlock = ModBlocks.deco_loot;
		private int replaceMeta;

		private String poolName = LootGenerator.LOOT_BOOKLET;
		private int minItems;
		private int maxItems = 1;

		private float placedRotation;

		private static final GameProfile FAKE_PROFILE = new GameProfile(UUID.fromString("839eb18c-50bc-400c-8291-9383f09763e7"), "[NTM]");
		private static FakePlayer fakePlayer;

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				if(triggerReplace) {
					// On the first tick of this TE, replace with intended block and fill with loot
					replace();
				} else {
					networkPackNT(15);
				}
			}
		}

		private void replace() {
			if(!(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockWandLoot)) {
				MainRegistry.logger.warn("Somehow the block at: " + xCoord + ", " + yCoord + ", " + zCoord + " isn't a loot block but we're doing a TE update as if it is, cancelling!");
				return;
			}

			WeightedRandomChestContent[] pool = ItemPool.getPool(poolName);

			worldObj.setBlock(xCoord, yCoord, zCoord, replaceBlock, replaceMeta, 2);

			TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);

			if(te == null || te instanceof TileEntityWandLoot) {
				// Some generator has broken the TE->block relationship, which, honestly, rude.
				// so we're just gonna hop in and force update the TE

				MainRegistry.logger.warn("TE set incorrectly at: " + xCoord + ", " + yCoord + ", " + zCoord + ". If you're using some sort of world generation mod, report it to the author!");

				te = replaceBlock.createTileEntity(worldObj, replaceMeta);
				worldObj.setTileEntity(xCoord, yCoord, zCoord, te);
			}

			if(te instanceof IInventory) {
				int count = minItems;
				if(maxItems - minItems > 0) count += worldObj.rand.nextInt(maxItems - minItems);
				WeightedRandomChestContent.generateChestContents(worldObj.rand, pool, (IInventory) te, count);
			} else if(te instanceof BlockLoot.TileEntityLoot) {
				LootGenerator.applyLoot(worldObj, xCoord, yCoord, zCoord, poolName);
			}

			// Shouldn't happen but let's guard anyway, if it fails we just don't rotate the chest block correctly
			if(!(worldObj instanceof WorldServer)) return;

			try {
				if(fakePlayer == null || fakePlayer.worldObj != worldObj) {
					fakePlayer = FakePlayerFactory.get((WorldServer)worldObj, FAKE_PROFILE);
				}

				fakePlayer.rotationYaw = fakePlayer.rotationYawHead = placedRotation;

				ItemStack fakeStack = new ItemStack(replaceBlock, 1, replaceMeta);

				replaceBlock.onBlockPlacedBy(worldObj, xCoord, yCoord, zCoord, fakePlayer, fakeStack);
			} catch(Exception ex) {
				MainRegistry.logger.warn("Failed to correctly rotate loot block at: " + xCoord + ", " + yCoord + ", " + zCoord);
				MainRegistry.logger.catching(ex);
			}
		}

		private List<String> getPoolNames(boolean loot) {
			if(loot) return Arrays.asList(LootGenerator.getLootNames());

			List<String> names = new ArrayList<>();
			names.addAll(ItemPool.pools.keySet());
			return names;
		}

		@Override
		public void transformTE(World world, int coordBaseMode) {
			triggerReplace = !StructureConfig.debugStructures;
			placedRotation = MathHelper.wrapAngleTo180_float(placedRotation + coordBaseMode * 90);
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			Block writeBlock = replaceBlock == null ? ModBlocks.deco_loot : replaceBlock;
			nbt.setString("block", GameRegistry.findUniqueIdentifierFor(writeBlock).toString());
			nbt.setInteger("meta", replaceMeta);
			nbt.setInteger("min", minItems);
			nbt.setInteger("max", maxItems);
			nbt.setString("pool", poolName);
			nbt.setFloat("rot", placedRotation);

			nbt.setBoolean("trigger", triggerReplace);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			replaceBlock = Block.getBlockFromName(nbt.getString("block"));
			replaceMeta = nbt.getInteger("meta");
			minItems = nbt.getInteger("min");
			maxItems = nbt.getInteger("max");
			poolName = nbt.getString("pool");
			placedRotation = nbt.getFloat("rot");

			if(replaceBlock == null) replaceBlock = ModBlocks.deco_loot;

			triggerReplace = nbt.getBoolean("trigger");
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeInt(Block.getIdFromBlock(replaceBlock));
			buf.writeInt(replaceMeta);
			buf.writeInt(minItems);
			buf.writeInt(maxItems);
			BufferUtil.writeString(buf, poolName);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			replaceBlock = Block.getBlockById(buf.readInt());
			replaceMeta = buf.readInt();
			minItems = buf.readInt();
			maxItems = buf.readInt();
			poolName = BufferUtil.readString(buf);
		}

	}

}