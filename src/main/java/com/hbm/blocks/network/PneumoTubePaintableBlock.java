package com.hbm.blocks.network;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockMultiPass;
import com.hbm.interfaces.ICopiable;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.util.Compat;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class PneumoTubePaintableBlock extends BlockContainer implements IToolable, IBlockMultiPass {

	@SideOnly(Side.CLIENT) public IIcon overlay;
	@SideOnly(Side.CLIENT) public IIcon overlayIn;
	@SideOnly(Side.CLIENT) public IIcon overlayOut;

	public PneumoTubePaintableBlock() {super(Material.iron);}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {return new TileEntityPneumoTubePaintable();}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_paintable");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_paintable_overlay");
		this.overlayIn = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_paintable_overlay_in");
		this.overlayOut = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_paintable_overlay_out");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TileEntityPneumoTubePaintable) {
			TileEntityPneumoTubePaintable tube = (TileEntityPneumoTubePaintable) tile;

			if (RenderBlockMultipass.currentPass == 0) {
				if (tube.block != null) {
					return tube.block.getIcon(side, tube.meta);
				} else {
					return this.blockIcon;
				}
			} else if (tube.ejectionDir.ordinal() == side) {
				return this.overlayIn;
			} else if (tube.insertionDir.ordinal() == side) {
				return this.overlayOut;
			} else {
				return this.overlay;
			}
		}
		return this.blockIcon;
	}

	@Override
	public int getPasses() {return 2;};

	@Override
	public int getRenderType() {return IBlockMultiPass.getRenderType();}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if (tool == ToolType.HAND_DRILL) {

			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof TileEntityPneumoTubePaintable) {
				TileEntityPneumoTubePaintable tube = (TileEntityPneumoTubePaintable) tile;

				if (tube.block != null) {
					tube.block = null;
					world.markBlockForUpdate(x, y, z);
					tube.markDirty();
				}
			}
		} else if (tool == ToolType.SCREWDRIVER) {

			if (world.isRemote) return true;
			TileEntityPneumoTube tube = (TileEntityPneumoTube) world.getTileEntity(x, y, z);

			ForgeDirection rot = player.isSneaking() ? tube.ejectionDir : tube.insertionDir;
			ForgeDirection oth = player.isSneaking() ? tube.insertionDir : tube.ejectionDir;

			for (int i = 0; i < 7; i++) {
				rot = ForgeDirection.getOrientation((rot.ordinal() + 1) % 7);
				if (rot == ForgeDirection.UNKNOWN) break; //unknown is always valid, simply disables this part
				if (rot == oth) continue; //skip if both positions collide
				TileEntity tile = Compat.getTileStandard(world, x + rot.offsetX, y + rot.offsetY, z + rot.offsetZ);
				if (tile instanceof TileEntityPneumoTube) continue;
				if (tile instanceof IInventory) break; //valid if connected to an IInventory
			}

			if(player.isSneaking()) tube.ejectionDir = rot; else tube.insertionDir = rot;

			tube.markDirty();
			if(world instanceof WorldServer) ((WorldServer) world).getPlayerManager().markBlockForUpdate(x, y, z);

			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		ItemStack stack = player.getHeldItem();
		if (stack != null && stack.getItem() instanceof ItemBlock) {
			ItemBlock ib = (ItemBlock) stack.getItem();
			Block block = ib.field_150939_a;

			if (block.renderAsNormalBlock() && block != this) {
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof TileEntityPneumoTubePaintable) {
					TileEntityPneumoTubePaintable tube = (TileEntityPneumoTubePaintable) tile;

					if (tube.block == null) {
						tube.block = block;
						tube.meta = stack.getItemDamage() & 15;
						world.markBlockForUpdate(x, y, z);
						tube.markDirty();
						return true;
					}
				}
			}
		} else if (ToolType.getType(stack) == ToolType.SCREWDRIVER || ToolType.getType(stack) == ToolType.HAND_DRILL) return false;
			if (!player.isSneaking()) {
				TileEntity tile = world.getTileEntity(x, y, z);
				if (tile instanceof TileEntityPneumoTube) {
					TileEntityPneumoTube tube = (TileEntityPneumoTube) tile;
					if (tube.isCompressor()) {
						FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
						return true;
					} else if(tube.isEndpoint()) {
						FMLNetworkHandler.openGui(player, MainRegistry.instance, 1, world, x, y, z);
						return true;
					}
				}
			}
		return false;
	}

	public static class TileEntityPneumoTubePaintable extends TileEntityPneumoTube implements ICopiable {

		private Block block;
		private int meta;
		private Block lastBlock;
		private int lastMeta;

		@Override
		public void updateEntity() {
			super.updateEntity();

			if (worldObj.isRemote && (lastMeta != meta || lastBlock != block )) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				lastBlock = block;
				lastMeta = meta;
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			int id = nbt.getInteger("block");
			this.block = id == 0 ? null : Block.getBlockById(id);
			this.meta = nbt.getInteger("meta");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(block != null) nbt.setInteger("block", Block.getIdFromBlock(block));
			nbt.setInteger("meta", meta);
		}

		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			NBTTagCompound nbt = pkt.func_148857_g();

			int id = nbt.getInteger("block");
			this.block = id == 0 ? null : Block.getBlockById(id);
			this.meta = nbt.getInteger("meta");
			super.onDataPacket(net, pkt);
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();

			if(block != null) nbt.setInteger("block", Block.getIdFromBlock(block));
			nbt.setInteger("meta", meta);
			nbt.setByte("insertionDir", (byte) insertionDir.ordinal());
			nbt.setByte("ejectionDir", (byte) ejectionDir.ordinal());
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		@Override
		public NBTTagCompound getSettings(World world, int x, int y, int z) {
			NBTTagCompound nbt = new NBTTagCompound();
			if(block != null) {
				nbt.setInteger("paintblock", Block.getIdFromBlock(block));
				nbt.setInteger("paintmeta", meta);
			}
			return nbt;
		}

		@Override
		public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
			if(nbt.hasKey("paintblock")) {
				this.block = Block.getBlockById(nbt.getInteger("paintblock"));
				this.meta = nbt.getInteger("paintmeta");
			}
		}
	}
}
