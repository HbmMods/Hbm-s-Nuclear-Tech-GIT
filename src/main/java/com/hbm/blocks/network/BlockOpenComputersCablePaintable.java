package com.hbm.blocks.network;

import api.hbm.block.IToolable;
import com.hbm.blocks.IBlockMultiPass;
import com.hbm.interfaces.ICopiable;
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
import li.cil.oc.api.network.SidedEnvironment;
import net.minecraftforge.common.util.ForgeDirection;
import li.cil.oc.api.internal.Colored;
import com.hbm.handler.CompatHandler.OCColors;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOpenComputersCablePaintable extends BlockContainer implements IToolable, IBlockMultiPass {

	@SideOnly(Side.CLIENT) protected IIcon overlay;
	@SideOnly(Side.CLIENT) protected IIcon overlayColor;

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
		this.overlayColor = reg.registerIcon(RefStrings.MODID + ":oc_cable_color");
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
				} else if(RenderBlockMultipass.currentPass == 2) {
					return this.overlayColor;
				} else {
					return pipe.block.getIcon(side, pipe.meta);
				}
			}
		}

		return RenderBlockMultipass.currentPass == 1 ? this.overlay : RenderBlockMultipass.currentPass == 2 ? this.overlayColor : this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		if (RenderBlockMultipass.currentPass == 2) {
			TileEntityOpenComputersCablePaintable tile = (TileEntityOpenComputersCablePaintable) world.getTileEntity(x, y, z);
			if (tile == null)
				return 0xffffff;

			return tile.getColor();
		}

		return 0xffffff;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		ItemStack stack = player.getHeldItem();

		if (stack == null)
			return super.onBlockActivated(world, x, y, z, player, side, fX, fY, fZ);

		if (stack.getItem() instanceof ItemBlock) {
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
		} else {
			boolean isDye = false;
			int[] dicts = OreDictionary.getOreIDs(stack);
			for (int dict : dicts) {
				String dictName = OreDictionary.getOreName(dict);
				if (dictName.equals("dye"))
					isDye = true;
			}

			if (isDye) {
				TileEntityOpenComputersCablePaintable tile = (TileEntityOpenComputersCablePaintable) world.getTileEntity(x, y, z);
				tile.setColor(OCColors.fromDye(stack).getColor());
				world.markBlockForUpdate(x, y, z);
				tile.markDirty();
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
		return 3;
	}

	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Optional.InterfaceList({
		@Optional.Interface(iface = "li.cil.oc.api.network.Environment", modid = "OpenComputers"),
		@Optional.Interface(iface = "li.cil.oc.api.network.SidedEnvironment", modid = "OpenComputers"),
		@Optional.Interface(iface = "li.cil.oc.api.network.Colored", modid = "OpenComputers")
	})
	public static class TileEntityOpenComputersCablePaintable extends TileEntityLoadedBase implements Environment, SidedEnvironment, Colored, ICopiable {

		protected Node node;
		protected boolean addedToNetwork = false;

		private Block block;
		private int meta;
		private Block lastBlock;
		private int lastMeta;
		private OCColors color = OCColors.LIGHTGRAY;

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

			this.color = OCColors.fromInt(nbt.getInteger("dyeColor"));

			if (node != null && node.host() == this) {
				node.load(nbt.getCompoundTag("oc:node"));
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(block != null) nbt.setInteger("block", Block.getIdFromBlock(block));
			nbt.setInteger("meta", meta);

			nbt.setInteger("dyeColor", color.getColor());

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
				this.color = OCColors.fromInt(nbt.getInteger("dyeColor"));
			}
		}

		// OC Cable Things
		@Override
		public Node node() {
			return node;
		}

		public Node sidedNode(ForgeDirection side) {
			if (side == ForgeDirection.UNKNOWN)
				return null;

			int neighborX = super.xCoord + side.offsetX;
			int neighborY = super.yCoord + side.offsetY;
			int neighborZ = super.zCoord + side.offsetZ;
			TileEntity neighbor = worldObj.getTileEntity(neighborX, neighborY, neighborZ);

			// If a cable does not support colors but is a valid cable block, allow it to connect regardless of color.
			if (!(neighbor instanceof Colored)) {
				if (neighbor instanceof Environment)
					return node;
				else
					return null;
			}

			Colored cable = (Colored) neighbor;
			if (cable.getColor() == color.getColor())
				return node;
			else
				return null;
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

		public boolean canConnect(net.minecraftforge.common.util.ForgeDirection side) {
			if (side == ForgeDirection.UNKNOWN)
				return false;

			int neighborX = super.xCoord + side.offsetX;
			int neighborY = super.yCoord + side.offsetY;
			int neighborZ = super.zCoord + side.offsetZ;
			TileEntity neighbor = worldObj.getTileEntity(neighborX, neighborY, neighborZ);

			// If a cable does not support colors but is a valid cable block, allow it to connect regardless of color.
			if (!(neighbor instanceof Colored)) {
				return neighbor instanceof Environment;
			}

			Colored cable = (Colored) neighbor;
			return cable.getColor() == color.getColor();
		}

		public void setColor(int newColor) {
			color = OCColors.fromInt(newColor);
		}

		public int getColor() {
			return color.getColor();
		}
	}
}
