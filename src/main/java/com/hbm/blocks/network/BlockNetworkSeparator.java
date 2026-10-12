package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.gui.GUIDiode;
import com.hbm.main.MainRegistry;
import com.hbm.module.portmanager.ModulePortManPower;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.PowerNetMK2;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockNetworkSeparator extends BlockDummyable implements ILookOverlay, IGUIProvider {

	public BlockNetworkSeparator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityNetworkSeparator();
		return new TileEntityProxyCombo().power();
	}

	@Override public int[] getDimensions() { return new int[] {1, 0, 0, 0, 1, 1}; }
	@Override public int getOffset() { return 0; }
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{1, 0, 0, ForgeDirection.EAST.ordinal()},
			{-1, 0, 0, ForgeDirection.WEST.ordinal()}
		}, dir);
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {

		if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);
			if(pos == null) return false;
			TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
			if(!(tile instanceof TileEntityNetworkSeparator)) return false;
			
			if(world.isRemote) FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		}

		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(tile instanceof TileEntityNetworkSeparator)) return;
		
		TileEntityNetworkSeparator separator = (TileEntityNetworkSeparator) tile;

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(separator.deltaTick[19]) + "HE/t" + EnumChatFormatting.RED + " ->");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(separator.deltaSecond) + "HE/s" + EnumChatFormatting.RED + " ->");
		text.add("Configured: " + BobMathUtil.getShortNumber(separator.configuredLimit) + "HE/t");
		text.add("Priority: " + EnumChatFormatting.YELLOW + separator.priority.name());
		if(separator.redstone) text.add(EnumChatFormatting.RED + "Disabled by redstone");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIDiode((TileEntityNetworkSeparator) world.getTileEntity(x, y, z)); }
	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	public static class TileEntityNetworkSeparator extends TileEntityLoadedBase implements IEnergyProviderMK2, IEnergyReceiverMK2, IControlReceiver, IRORValueProvider, IRORInteractive {

		/** Inter-tick tracker for transferred power */
		protected long deltaTracker = 0;
		/** Final power per tick over the past second */
		protected long[] deltaTick = new long[20];
		/** Average of the entire previous log */
		protected long deltaSecond = 0;
		
		public static final long MAX_LIMIT = 10_000_000_000L;
		
		private long power;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;
		public boolean redstone = false;
		public long configuredLimit = 1_000;
		public long actualLimit = configuredLimit;

		protected ModulePortManPower inPort;
		protected ModulePortManPower outPort;
		protected PortDef[] cachedOutPort;
		protected PortDef[] cachedInPort;
		protected ForgeDirection cachedDir;
		
		public TileEntityNetworkSeparator() {
			inPort = new ModulePortManPower(this).onlyInput();
			outPort = new ModulePortManPower(this).onlyOutput();
		}
		
		public ForgeDirection getDir(boolean opposite) {
			if(cachedDir == null) cachedDir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.DOWN);
			return opposite ? cachedDir.getOpposite() : cachedDir;
		}
		
		public PortDef[] getOutPort() { if(cachedOutPort == null) cachedOutPort = new PortDef[] { PortDef.make(xCoord + getDir(false).offsetX, yCoord, zCoord + getDir(false).offsetZ, getDir(false)) }; return cachedOutPort; }
		public PortDef[] getInPort() { if(cachedInPort == null) cachedInPort = new PortDef[] { PortDef.make(xCoord + getDir(true).offsetX, yCoord, zCoord + getDir(true).offsetZ, getDir(true)) }; return cachedInPort; }
		
		@Override
		public boolean canConnect(ForgeDirection dir) {
			ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.DOWN);
			return dir == rot || dir == rot.getOpposite();
		}

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				this.inPort.update(getInPort());
				this.outPort.update(getOutPort());
				
				this.actualLimit = this.configuredLimit;
				
				if(this.outPort.powerPorts[0].getNetwork() instanceof PowerNetMK2) {
					this.surveyOutgoingNetwork((PowerNetMK2) this.outPort.powerPorts[0].getNetwork());
				}
				
				this.actualLimit = Math.max(this.actualLimit, this.power);

				long total = this.deltaTick[0];

				for(int i = 1; i < this.deltaTick.length; i++) {
					total += this.deltaTick[i];
					this.deltaTick[i - 1] = this.deltaTick[i];
				}

				this.deltaTick[19] = deltaTracker;
				this.deltaTracker = 0;
				this.deltaSecond = total;
				
				ForgeDirection dir = this.getDir(false);
				this.redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ||
						worldObj.isBlockIndirectlyGettingPowered(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ) ||
						worldObj.isBlockIndirectlyGettingPowered(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ);
				
				this.networkPackNT(15);
			}
		}
		
		@Override
		public void usePower(long power) {
			this.setPower(this.getPower() - power);
			deltaTracker += power;
		}
		
		protected void surveyOutgoingNetwork(PowerNetMK2 net) {
			
			long demand = 0;
			for(IEnergyReceiverMK2 rec : net.receiverEntries) {
				
				if(rec instanceof TileEntityNetworkSeparator) {
					demand += ((TileEntityNetworkSeparator) rec).actualLimit;
				} else {
					demand += Math.min(rec.getMaxPower() - rec.getPower(), rec.getReceiverSpeed());
				}
			}
			
			//demand *= 1.25; // a small margin of error
			
			if(demand < this.actualLimit) this.actualLimit = demand;
		}

		@Override
		public void onChunkUnload() {
			super.onChunkUnload();
			inPort.destroy();
			outPort.destroy();
		}
		
		@Override
		public void invalidate() {
			super.invalidate();
			inPort.destroy();
			outPort.destroy();
		}

		@Override public long getReceiverSpeed() { return redstone || bufferMismatch() ? 0 : this.getMaxPower(); }
		@Override public long getProviderSpeed() { return redstone ? 0 : this.getMaxPower(); }
		@Override public long getMaxPower() { return actualLimit; }
		@Override public long getPower() { return power; }
		@Override public void setPower(long power) { this.power = power; }
		@Override public ConnectionPriority getPriority() { return this.priority; }
		
		public boolean bufferMismatch() {
			return this.configuredLimit < this.getMaxPower();
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.power = nbt.getLong("power");
			this.configuredLimit = nbt.getLong("configuredLimit");
			this.actualLimit = nbt.getLong("actualLimit");
			this.priority = ConnectionPriority.values()[nbt.getByte("p")];
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setLong("power", power);
			nbt.setLong("configuredLimit", configuredLimit);
			nbt.setLong("actualLimit", actualLimit);
			nbt.setByte("p", (byte) this.priority.ordinal());
		}

		@Override
		public void serialize(ByteBuf buf) {
			super.serialize(buf);
			buf.writeByte((byte) priority.ordinal());
			buf.writeBoolean(redstone);
			buf.writeLong(configuredLimit);
			buf.writeLong(deltaTick[19]);
			buf.writeLong(deltaSecond);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			super.deserialize(buf);
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
			redstone = buf.readBoolean();
			configuredLimit = buf.readLong();
			deltaTick[19] = buf.readLong();
			deltaSecond = buf.readLong();
		}

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}

		@Override
		public void receiveControl(EntityPlayer player, NBTTagCompound data) {
			if(data.hasKey("limit")) this.configuredLimit = data.getLong("limit");
			if(data.hasKey("priority")) this.priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, data.getByte("priority"));
			if(configuredLimit < 0) configuredLimit = 0;
			if(configuredLimit > MAX_LIMIT) configuredLimit = MAX_LIMIT;
			this.markDirty();
		}

		@Override
		public String[] getFunctionInfo() {
			return new String[] {
					PREFIX_VALUE + "deltatick",
					PREFIX_VALUE + "deltasecond",
					PREFIX_FUNCTION + "setpriority" + NAME_SEPARATOR + "priority (0-2)",
					PREFIX_FUNCTION + "setlimit" + NAME_SEPARATOR + "limit",
			};
		}

		@Override
		public String provideRORValue(String name) {
			if((PREFIX_VALUE + "deltatick").equals(name))	return "" + deltaTick[19];
			if((PREFIX_VALUE + "deltasecond").equals(name))	return "" + deltaSecond;
			return null;
		}

		@Override
		public String runRORFunction(String name, String[] params) {

			if((PREFIX_FUNCTION + "setpriority").equals(name) && params.length > 0) {
				int priority = IRORInteractive.parseInt(params[0], 0, 2) + 1;
				ConnectionPriority p = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority);
				this.priority = p;
				this.markChanged();
				return null;
			}

			if((PREFIX_FUNCTION + "setlimit").equals(name) && params.length > 0) {
				this.configuredLimit = IRORInteractive.parseLong(params[0], 0, MAX_LIMIT);
				this.markChanged();
				return null;
			}
			
			return null;
		}
		
		AxisAlignedBB bb = null;
		
		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
			return bb;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}
	}
}
