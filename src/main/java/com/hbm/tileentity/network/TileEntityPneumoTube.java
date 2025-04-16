package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerPneumoTube;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPneumoTube;
import com.hbm.lib.Library;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PneumaticNetwork;
import com.hbm.uninos.networkproviders.PneumaticNetworkProvider;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPneumoTube extends TileEntityMachineBase implements IGUIProvider, IFluidStandardReceiverMK2, IControlReceiverFilter {

	public ModulePatternMatcher pattern = new ModulePatternMatcher(15);
	public ForgeDirection insertionDir = ForgeDirection.UNKNOWN;
	public ForgeDirection ejectionDir = ForgeDirection.UNKNOWN;
	
	public boolean whitelist = false;
	public boolean redstone = false;
	public byte sendOrder = 0;
	public byte receiveOrder = 0;
	public int soundDelay = 0;
	
	public FluidTank compair;
	
	protected PneumaticNode node;
	
	public TileEntityPneumoTube() {
		super(15);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
	}

	@Override
	public String getName() {
		return "container.pneumoTube";
	}
	
	public boolean matchesFilter(ItemStack stack) {
		
		for(int i = 0; i < 15; i++) {
			ItemStack filter = slots[i];
			if(filter != null && this.pattern.isValidForFilter(filter, i, stack)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.soundDelay > 0) this.soundDelay--;
			
			if(this.node == null || this.node.expired) {
				this.node = (PneumaticNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
				
				if(this.node == null || this.node.expired) {
					this.node = (PneumaticNode) new PneumaticNode(new BlockPos(xCoord, yCoord, zCoord)).setConnections(
							new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
							new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
							new DirPos(xCoord, yCoord + 1, zCoord, Library.POS_Y),
							new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
							new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
							new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
							);
					UniNodespace.createNode(worldObj, this.node);
				}
			}
			
			if(this.isCompressor() && (!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ^ this.redstone)) {
				
				int randTime = Math.abs((int) (worldObj.getTotalWorldTime() + this.getIdentifier(xCoord, yCoord, zCoord)));
				
				if(worldObj.getTotalWorldTime() % 10 == 0) for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					if(dir != this.insertionDir && dir != this.ejectionDir) {
						this.trySubscribe(compair.getTankType(), worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
					}
				}
				
				if(randTime % 5 == 0 && this.node != null && !this.node.expired && this.node.net != null && this.compair.getFill() >= 50) {
					TileEntity sendFrom = Compat.getTileStandard(worldObj, xCoord + insertionDir.offsetX, yCoord + insertionDir.offsetY, zCoord + insertionDir.offsetZ);
					
					if(sendFrom instanceof IInventory) {
						PneumaticNetwork net = node.net;
						
						if(net.send((IInventory) sendFrom, this, this.insertionDir.getOpposite(), sendOrder, receiveOrder, getRangeFromPressure(compair.getPressure()))) {
							this.compair.setFill(this.compair.getFill() - 50);
							
							if(this.soundDelay <= 0 && !this.muffled) {
								worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:weapon.reload.tubeFwoomp", 0.25F, 0.9F + worldObj.rand.nextFloat() * 0.2F);
								this.soundDelay = 20;
							}
						}
					}
				}
			}
			
			if(this.isEndpoint() && this.node != null && this.node.net != null && worldObj.getTotalWorldTime() % 10 == 0) {
				TileEntity tile = Compat.getTileStandard(worldObj, xCoord + this.ejectionDir.offsetX, yCoord + this.ejectionDir.offsetY, zCoord + this.ejectionDir.offsetZ);
				if(tile instanceof IInventory) this.node.net.addReceiver((IInventory) tile, this.ejectionDir);
			}

			this.networkPackNT(15);
		}
	}
	
	public static int getRangeFromPressure(int pressure) {
		if(pressure == 0) return 0;
		if(pressure == 1) return 10;
		if(pressure == 2) return 25;
		if(pressure == 3) return 100;
		if(pressure == 4) return 250;
		if(pressure == 5) return 1_000;
		return 0;
	}
	
	// tactfully copy pasted from BlockPos
	public static int getIdentifier(int x, int y, int z) {
		return (y + z * 27644437) * 27644437 + x;
	}

	@Override
	public long getReceiverSpeed(FluidType type, int pressure) {
		return MathHelper.clamp_int((this.compair.getMaxFill() - this.compair.getFill()) / 25, 1, 100);
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != this.insertionDir && dir != this.ejectionDir && type == Fluids.AIR && this.isCompressor();
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.node != null) {
				UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, PneumaticNetworkProvider.THE_PROVIDER);
			}
		}
	}
	
	public boolean isCompressor() { return this.insertionDir != ForgeDirection.UNKNOWN; }
	public boolean isEndpoint() { return this.ejectionDir != ForgeDirection.UNKNOWN; }

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(redstone);
		buf.writeBoolean(whitelist);
		buf.writeByte(sendOrder);
		buf.writeByte(receiveOrder);
		pattern.serialize(buf);
		compair.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.redstone = buf.readBoolean();
		this.whitelist = buf.readBoolean();
		this.sendOrder = buf.readByte();
		this.receiveOrder = buf.readByte();
		pattern.deserialize(buf);
		compair.deserialize(buf);
	}
	
	public void nextMode(int index) {
		this.pattern.nextMode(worldObj, slots[index], index);
	}

	public void initPattern(ItemStack stack, int index) {
		this.pattern.initPatternSmart(worldObj, stack, index);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("insertionDir", (byte) insertionDir.ordinal());
		nbt.setByte("ejectionDir", (byte) ejectionDir.ordinal());
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.func_148857_g();
		this.insertionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("insertionDir"));
		this.ejectionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("ejectionDir"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord); // that's right, we're gonna cheat
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.insertionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("insertionDir"));
		this.ejectionDir = EnumUtil.grabEnumSafely(ForgeDirection.class, nbt.getByte("ejectionDir"));
		this.compair.readFromNBT(nbt, "tank");
		this.pattern.readFromNBT(nbt);

		this.whitelist = nbt.getBoolean("whitelist");
		this.redstone = nbt.getBoolean("redstone");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("insertionDir", (byte) insertionDir.ordinal());
		nbt.setByte("ejectionDir", (byte) ejectionDir.ordinal());
		this.compair.writeToNBT(nbt, "tank");
		this.pattern.writeToNBT(nbt);

		nbt.setBoolean("whitelist", whitelist);
		nbt.setBoolean("redstone", redstone);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoTube(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPneumoTube(player.inventory, this);
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("whitelist")) {
			this.whitelist = !this.whitelist;
		}
		if(data.hasKey("redstone")) {
			this.redstone = !this.redstone;
		}
		if(data.hasKey("pressure")) {
			int pressure = this.compair.getPressure() + 1;
			if(pressure > 5) pressure = 1;
			this.compair.withPressure(pressure);
		}
		if(data.hasKey("send")) {
			this.sendOrder++;
			if(this.sendOrder > 2) this.sendOrder = 0;
		}
		if(data.hasKey("receive")) {
			this.receiveOrder++;
			if(this.receiveOrder > 1) this.receiveOrder = 0;
		}
		if(data.hasKey("slot")){
			setFilterContents(data);
		}
		
		this.markDirty();
	}

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }
	@Override public int[] getFilterSlots() { return new int[] {0, 15}; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }
	
	public static class PneumaticNode extends GenNode<PneumaticNetwork> {

		public PneumaticNode(BlockPos... positions) {
			super(PneumaticNetworkProvider.THE_PROVIDER, positions);
		}
	}
}
