package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.block.IToolable;
import api.hbm.energy.IEnergyUser;
import api.hbm.energy.IEnergyConnector.ConnectionPriority;
import api.hbm.energy.IEnergyConnectorBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class CableDiode extends BlockContainer implements IEnergyConnectorBlock, ILookOverlay, IToolable, ITooltipProvider {
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	
	public CableDiode(Material mat) {
		super(mat);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return this == ModBlocks.cable_inverted_diode ? super.getRenderType() : renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return this == ModBlocks.cable_inverted_diode && super.isOpaqueCube();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return this == ModBlocks.cable_inverted_diode && super.renderAsNormalBlock();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":cable_diode");
		if(this == ModBlocks.cable_inverted_diode)
			this.iconFront = iconRegister.registerIcon(RefStrings.MODID + ":hadron_coil_alloy");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(this == ModBlocks.cable_diode)
			return blockIcon;
		return side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal() ? this.iconFront : this.blockIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return this == ModBlocks.cable_diode || super.shouldSideBeRendered(world, x, y, z, side);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return true;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		TileEntityDiode te = (TileEntityDiode)world.getTileEntity(x, y, z);
		
		if(world.isRemote)
			return true;
		
		if(tool == ToolType.SCREWDRIVER) {
			if(te.level < 11)
				te.level++;
			te.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		if(tool == ToolType.HAND_DRILL) {
			if(te.level > 1)
				te.level--;
			te.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		if(tool == ToolType.DEFUSER) {
			int p = te.priority.ordinal() + 1;
			if(p > 2) p = 0;
			te.priority = ConnectionPriority.values()[p];
			te.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Limits throughput and restricts flow direction");
		if(this == ModBlocks.cable_diode)
			list.add(EnumChatFormatting.GOLD + "Accepts several inputs and has one output");
		else
			list.add(EnumChatFormatting.GOLD + "Accepts one input and has several outputs");
		list.add(EnumChatFormatting.YELLOW + "Use screwdriver to increase throughput");
		list.add(EnumChatFormatting.YELLOW + "Use hand drill to decrease throughput");
		list.add(EnumChatFormatting.YELLOW + "Use defuser to change network priority");
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityDiode))
			return;
		
		TileEntityDiode diode = (TileEntityDiode) te;
		
		List<String> text = new ArrayList<>();
		text.add("Max.: " + BobMathUtil.getShortNumber(diode.getMaxPower()) + "HE/t");
		text.add("Priority: " + diode.priority.name());
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDiode(this == ModBlocks.cable_inverted_diode);
	}
	
	public static class TileEntityDiode extends TileEntityLoadedBase implements IEnergyUser {
		private final boolean isInverted;
		private byte currentOutputDir = 0;

		public TileEntityDiode(boolean isInverted) {
			this.isInverted = isInverted;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			level = nbt.getInteger("level");
			priority = ConnectionPriority.values()[nbt.getByte("p")];
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("level", level);
			nbt.setByte("p", (byte) this.priority.ordinal());
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
		
		int level = 1;
		
		private ForgeDirection getDir() {
			return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		}

		private ForgeDirection getNextOutputDir() {
			if(++currentOutputDir >= ForgeDirection.VALID_DIRECTIONS.length) {
				currentOutputDir = 0;
			}
			return ForgeDirection.VALID_DIRECTIONS[currentOutputDir] == getDir() ? getNextOutputDir() : ForgeDirection.VALID_DIRECTIONS[currentOutputDir];
		}

		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					if(dir == getDir() ^ isInverted)
						continue;
					this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
				}
			}
		}

		@Override
		public boolean canConnect(ForgeDirection dir) {
			return dir != getDir() ^ isInverted;
		}
		
		private boolean recursionBrake = false;
		private long subBuffer;
		private long contingent = 0;
		private long lastTransfer = 0;
		private int pulses = 0;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;

		@Override
		public long transferPower(long power) {

			if(recursionBrake)
				return power;
			
			pulses++;
			
			if(lastTransfer != worldObj.getTotalWorldTime()) {
				lastTransfer = worldObj.getTotalWorldTime();
				contingent = getMaxPower();
				pulses = 0;
			}
			
			if(contingent <= 0 || pulses > 10)
				return power;
			
			//this part turns "maxPower" from a glorified transfer weight into an actual transfer cap
			long overShoot = Math.max(0, power - contingent);
			power = Math.min(power, contingent);
			
			recursionBrake = true;
			this.subBuffer = power;
			
			ForgeDirection dir = isInverted ? getNextOutputDir() : getDir();
			this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			long ret = this.subBuffer;
			
			long sent = power - ret;
			contingent -= sent;
			
			this.subBuffer = 0;
			recursionBrake = false;
			
			return ret + overShoot;
		}


		@Override
		public long getMaxPower() {
			// Inverted diode is slower caus it processes only one side at time, multiplying output to avail sides - input side
			return (long) Math.pow(10, level) * (isInverted ? ForgeDirection.VALID_DIRECTIONS.length - 1 : 1);
		}

		@Override
		public long getPower() {
			return subBuffer;
		}
		
		@Override
		public void setPower(long power) {
			this.subBuffer = power;
		}

		@Override
		public ConnectionPriority getPriority() {
			return this.priority;
		}
	}
}
