package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.world.gen.nbt.INBTTileEntityTransformable;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockPlushie extends BlockContainer implements IBlockMulti, ITooltipProvider, INBTBlockTransformable {

	public BlockPlushie() {
		super(Material.cloth);
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	@Override public Item getItemDropped(int i, Random rand, int j) { return null; }

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		TileEntityPlushie entity = (TileEntityPlushie) world.getTileEntity(x, y, z);
		if(entity != null) return new ItemStack(this, 1, entity.type.ordinal());
		return super.getPickBlock(target, world, x, y, z, player);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			if(!world.isRemote) {
				TileEntityPlushie entity = (TileEntityPlushie) world.getTileEntity(x, y, z);
				if(entity != null) {
					EntityItem item = new EntityItem(world, x + 0.5, y, z + 0.5, new ItemStack(this, 1, entity.type.ordinal()));
					item.motionX = 0;
					item.motionY = 0;
					item.motionZ = 0;
					world.spawnEntityInWorld(item);
				}
			}
			harvesters.set(null);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 1; i < PlushieType.values().length; i++) list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int meta = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);

		TileEntityPlushie plushie = (TileEntityPlushie) world.getTileEntity(x, y, z);
		plushie.type = PlushieType.values()[Math.abs(stack.getItemDamage()) % PlushieType.values().length];
		plushie.markDirty();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPlushie();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			TileEntityPlushie plushie = (TileEntityPlushie) world.getTileEntity(x, y, z);
			plushie.squishTimer = 11;
			return true;
		} else {
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:block.squeakyToy", 0.25F, 1F);
			return true;
		}
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return (meta + coordBaseMode * 4) % 16;
	}

	public static class TileEntityPlushie extends TileEntity implements INBTTileEntityTransformable {

		public PlushieType type = PlushieType.NONE;
		public int squishTimer;

		@Override
		public void updateEntity() {
			if(squishTimer > 0) squishTimer--;
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.type = PlushieType.values()[Math.abs(nbt.getByte("type")) % PlushieType.values().length];
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setByte("type", (byte) type.ordinal());
		}

		@Override
		public void transformTE(World world, int coordBaseMode) {
			type = PlushieType.values()[world.rand.nextInt(PlushieType.values().length - 1) + 1];
		}
	}

	public static enum PlushieType {
		NONE(		"NONE",				null),
		YOMI(		"Yomi",				"Hi! Can I be your rabbit friend?"),
		NUMBERNINE(	"Number Nine",		"None of y'all deserve coal."),
		POOH(		"Winnie the Pooh",	"Beloved children's character with no malicious intent.");

		public String label;
		public String inscription;

		private PlushieType(String label, String inscription) {
			this.label = label;
			this.inscription = inscription;
		}
	}

	@Override
	public int getSubCount() {
		return PlushieType.values().length;
	}

	@Override
	public String getOverrideDisplayName(ItemStack stack) {
		PlushieType type = PlushieType.values()[Math.abs(stack.getItemDamage()) % PlushieType.values().length];
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedName() + ".name", type == PlushieType.NONE ? "" : type.label).trim();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		PlushieType type = PlushieType.values()[Math.abs(stack.getItemDamage()) % PlushieType.values().length];
		if(type.inscription != null) list.add(type.inscription);
	}
}
