package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPump;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class FlowControlPump extends BlockDummyable implements ILookOverlay, IGUIProvider {

	public FlowControlPump() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityFlowControlPump();
		return new TileEntityProxyCombo().fluid();
	}

	@Override 	public int[] getDimensions() { return new int[] {0, 0, 0, 0, 1, 1}; }
	@Override public int getOffset() { return 0; }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return false;
		
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(tile instanceof TileEntityFlowControlPump)) return false;
		
		TileEntityFlowControlPump pump = (TileEntityFlowControlPump) tile;
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
			
			IItemFluidIdentifier id = (IItemFluidIdentifier) player.getHeldItem().getItem();
			FluidType type = id.getType(world, x, y, z, player.getHeldItem());
			
			if(!world.isRemote) {
				pump.tank[0].setTankType(type);
				pump.markDirty();
				player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
			}
			return true;
		}

		if(!player.isSneaking()) {
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
		if(!(tile instanceof TileEntityFlowControlPump)) return;
		
		TileEntityFlowControlPump pump = (TileEntityFlowControlPump) tile;

		List<String> text = new ArrayList();
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + pump.tank[0].getTankType().getLocalizedName() + " (" + pump.tank[0].getPressure() + " PU): " + BobMathUtil.format(pump.bufferSize) + "mB/t" + EnumChatFormatting.RED + " ->");
		text.add("Priority: " + EnumChatFormatting.YELLOW + pump.priority.name());
		if(pump.tank[0].getFill() > 0) text.add(BobMathUtil.format(pump.tank[0].getFill()) + "mB buffered");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPump((TileEntityFlowControlPump) world.getTileEntity(x, y, z)); }
	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	public static class TileEntityFlowControlPump extends TileEntityLoadedBase implements IFluidStandardTransceiverMK2, IControlReceiver {

		public int bufferSize = 100;
		public FluidTank[] tank;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;
		public boolean redstone = false;

		public boolean topRail = false;
		public boolean bottomRail = true;
		public boolean isWorking = false;

		public float rotor;
		public float prevRotor;
		public float rotorSpeed;
		public static final float ROTOR_ACCELERATION = 1F;

		public TileEntityFlowControlPump() {
			this.tank = new FluidTank[1];
			this.tank[0] = new FluidTank(Fluids.NONE, bufferSize);
		}

		protected PortDef cachedOutPort;
		protected PortDef cachedInPort;
		
		public PortDef getOutPort() {
			if(cachedOutPort == null) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.DOWN);
				cachedOutPort = PortDef.make(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir);
			}
			return cachedOutPort;
		}
		public PortDef getInPort() {
			if(cachedInPort == null) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.UP);
				cachedInPort = PortDef.make(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir);
			}
			return cachedInPort;
		}
		
		@Override
		public boolean canConnect(FluidType type, ForgeDirection dir) {
			ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.DOWN);
			return dir == rot || dir == rot.getOpposite();
		}

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				this.setupFluidOutPorts(getSendingTanks(), getOutPort());
				this.setupFluidInPorts(getReceivingTanks(), getInPort());
				this.updatePortFIFO();

				// if the capacity were changed directly, any excess buffered fluid would be destroyed
				// when running a closed loop or handling hard to get fluids, that's quite bad
				if(bufferMismatch()) {
					int nextBuffer = Math.max(this.tank[0].getFill(), this.bufferSize);
					this.tank[0].changeTankSize(nextBuffer);
				}

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.DOWN);
				this.redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ||
						worldObj.isBlockIndirectlyGettingPowered(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ) ||
						worldObj.isBlockIndirectlyGettingPowered(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ);
				
				this.networkPackNT(50);
				this.isWorking = false;
			} else {
				
				if(worldObj.getTotalWorldTime() % 20 == 0) {
					topRail = !worldObj.getBlock(xCoord, yCoord + 1, zCoord).isAir(worldObj, xCoord, yCoord + 1, zCoord);
					bottomRail = !worldObj.getBlock(xCoord, yCoord - 1, zCoord).isAir(worldObj, xCoord, yCoord - 1, zCoord);
				}

				if(isWorking) this.rotorSpeed += ROTOR_ACCELERATION;
				else this.rotorSpeed -= ROTOR_ACCELERATION;

				this.rotorSpeed = MathHelper.clamp_float(this.rotorSpeed, 0F, 30);

				this.prevRotor = this.rotor;
				this.rotor += this.rotorSpeed;

				if(this.rotor >= 360F) {
					this.rotor -= 360F;
					this.prevRotor -= 360F;
				}
			}
		}

		@Override
		public void useUpFluid(FluidType type, int pressure, long amount) {
			
			if(amount > 0) {
				if(tank[0].getTankType() == type && tank[0].getPressure() == pressure) {
					int toRem = (int) Math.min(amount, tank[0].getFill());
					tank[0].setFill(tank[0].getFill() - toRem);
					amount -= toRem;
					this.isWorking = true;
				}
			}
		}
		
		public boolean bufferMismatch() {
			return this.bufferSize != this.tank[0].getMaxFill();
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			tank[0].writeToNBT(nbt, "t");
			nbt.setByte("p", (byte) priority.ordinal());
			nbt.setInteger("buffer", bufferSize);
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			tank[0].readFromNBT(nbt, "t");
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, nbt.getByte("p"));
			bufferSize = nbt.getInteger("buffer");
		}

		@Override
		public void serialize(ByteBuf buf) {
			super.serialize(buf);
			tank[0].serialize(buf);
			buf.writeByte((byte) priority.ordinal());
			buf.writeInt(bufferSize);
			buf.writeBoolean(isWorking);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			super.deserialize(buf);
			tank[0].deserialize(buf);
			priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, buf.readByte());
			bufferSize = buf.readInt();
			isWorking = buf.readBoolean();
		}

		@Override public ConnectionPriority getFluidPriority() { return priority; }
		@Override public FluidTank[] getSendingTanks() { return tank; }
		@Override public FluidTank[] getReceivingTanks() { return tank; }
		@Override public FluidTank[] getAllTanks() { return tank; }

		@Override public long getReceiverSpeed(FluidType type, int pressure) { return redstone || bufferMismatch() ?  0 : 1_000_000_000; }
		@Override public long getProviderSpeed(FluidType type, int pressure) { return redstone ?  0 : 1_000_000_000; }

		@Override
		public boolean hasPermission(EntityPlayer player) {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}

		@Override
		public void receiveControl(EntityPlayer player, NBTTagCompound data) {
			if(data.hasKey("capacity")) this.bufferSize = MathHelper.clamp_int(data.getInteger("capacity"), 0, 10_000);
			if(data.hasKey("pressure")) this.tank[0].withPressure(MathHelper.clamp_int(data.getByte("pressure"), 0, 5));
			if(data.hasKey("priority")) priority = EnumUtil.grabEnumSafely(ConnectionPriority.class, data.getByte("priority"));

			this.markDirty();
		}
		
		AxisAlignedBB bb = null;
		
		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 1, zCoord + 2);
			return bb;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}
	}
}
