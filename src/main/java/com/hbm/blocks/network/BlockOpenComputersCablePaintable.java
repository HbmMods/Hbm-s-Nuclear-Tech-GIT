package com.hbm.blocks.network;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockMultiPass;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.TileEntityLoadedBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.network.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
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
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Loader;

public class BlockOpenComputersCablePaintable extends BlockContainer implements IToolable, IBlockMultiPass {

	@SideOnly(Side.CLIENT) protected IIcon overlay;

	public BlockOpenComputersCablePaintable() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityOpenComputersCablePaintable();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":oc_cable_base");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":oc_cable_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityOpenComputersCablePaintable) {
			TileEntityOpenComputersCablePaintable pipe = (TileEntityOpenComputersCablePaintable) tile;

			if(pipe.block != null) {
				if(RenderBlockMultipass.currentPass == 1) {
					return this.overlay;
				} else {
					return pipe.block.getIcon(side, pipe.meta);
				}
			}
		}

		return RenderBlockMultipass.currentPass == 1 ? this.overlay : this.blockIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		ItemStack stack = player.getHeldItem();

		if(stack != null && stack.getItem() instanceof ItemBlock) {
			ItemBlock ib = (ItemBlock) stack.getItem();
			Block block = ib.field_150939_a;

			if(block.renderAsNormalBlock() && block != this) {

				TileEntity tile = world.getTileEntity(x, y, z);

				if(tile instanceof TileEntityOpenComputersCablePaintable) {
					TileEntityOpenComputersCablePaintable pipe = (TileEntityOpenComputersCablePaintable) tile;

					if(pipe.block == null) {
						pipe.block = block;
						pipe.meta = stack.getItemDamage() & 15;
						world.markBlockForUpdate(x, y, z);
						pipe.markDirty();
						return true;
					}
				}
			}
		}

		return super.onBlockActivated(world, x, y, z, player, side, fX, fY, fZ);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		if(tool != ToolType.SCREWDRIVER) return false;

		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityOpenComputersCablePaintable) {
			TileEntityOpenComputersCablePaintable pipe = (TileEntityOpenComputersCablePaintable) tile;

			if(pipe.block != null) {
				pipe.block = null;
				world.markBlockForUpdate(x, y, z);
				pipe.markDirty();
				return true;
			}
		}

		return false;
	}

	@Override
	public int getPasses() {
		return 2;
	}

	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	// WHY the fuck is this not compiling
	@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.Environment", modid = "OpenComputers")})
	public static class TileEntityOpenComputersCablePaintable extends TileEntityLoadedBase implements Environment {

		protected Node node;
		protected boolean addedToNetwork = false;

		private Block block;
		private int meta;
		private Block lastBlock;
		private int lastMeta;

		public TileEntityOpenComputersCablePaintable() {
			node = Network.newNode(this, Visibility.None).create();
		}

		@Override
		public void updateEntity() {
			super.updateEntity();

			if(worldObj.isRemote && (lastBlock != block || lastMeta != meta)) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				lastBlock = block;
				lastMeta = meta;
			}

			if(!this.getWorldObj().isRemote && !addedToNetwork) {
				addedToNetwork = true;
				Network.joinOrCreateNetwork(this);
			}

		}

		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}

		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			int id = nbt.getInteger("block");
			this.block = id == 0 ? null : Block.getBlockById(id);
			this.meta = nbt.getInteger("meta");

			if (node != null && node.host() == this) {
				node.load(nbt.getCompoundTag("oc:node"));
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(block != null) nbt.setInteger("block", Block.getIdFromBlock(block));
			nbt.setInteger("meta", meta);

			if (node != null && node.host() == this) {
				final NBTTagCompound nodeNbt = new NBTTagCompound();
				node.save(nodeNbt);
				nbt.setTag("oc:node", nodeNbt);
			}
		}

		public NBTTagCompound getSettings(World world, int x, int y, int z) {
			NBTTagCompound nbt = new NBTTagCompound();
			if(block != null) {
				nbt.setInteger("paintblock", Block.getIdFromBlock(block));
				nbt.setInteger("paintmeta", meta);
			}
			return nbt;
		}

		public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
			if(nbt.hasKey("paintblock")) {
				this.block = Block.getBlockById(nbt.getInteger("paintblock"));
				this.meta = nbt.getInteger("paintmeta");
			}
		}

		// OC Cable Things
		@Override
		public Node node() {
			return node;
		}

		@Override
		public void onConnect(Node node) {}

		@Override
		public void onDisconnect(Node node) {}

		@Override
		public void onMessage(Message message) {}

		@Override
		public void onChunkUnload() {
			super.onChunkUnload();
			if (node != null) node.remove();
		}

		public void invalidate() {
			super.invalidate();
			if (node != null) node.remove();
		}
	}
}
