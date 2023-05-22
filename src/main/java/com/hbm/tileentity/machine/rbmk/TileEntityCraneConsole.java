package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCraneConsole extends TileEntity implements INBTPacketReceiver, SimpleComponent {
	
	public int centerX;
	public int centerY;
	public int centerZ;
	
	public int spanF;
	public int spanB;
	public int spanL;
	public int spanR;
	
	public int height;
	
	public boolean setUpCrane = false;

	public double lastTiltFront = 0;
	public double lastTiltLeft = 0;
	public double tiltFront = 0;
	public double tiltLeft = 0;

	public double lastPosFront = 0;
	public double lastPosLeft = 0;
	public double posFront = 0;
	public double posLeft = 0;
	private static final double speed = 0.05D;
	
	private boolean goesDown = false;
	public double lastProgress = 1D;
	public double progress = 1D;
	
	private ItemStack loadedItem;
	private boolean hasLoaded = false;
	public double loadedHeat;
	public double loadedEnrichment;

	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			lastTiltFront = tiltFront;
			lastTiltLeft = tiltLeft;
		}
		
		if(goesDown) {
			
			if(progress > 0) {
				progress -= 0.04D;
			} else {
				progress = 0;
				goesDown = false;
				
				if(!worldObj.isRemote && this.canTargetInteract()) {
					if(this.loadedItem != null) {
						getColumnAtPos().load(this.loadedItem);
						this.loadedItem = null;
					} else {
						IRBMKLoadable column = getColumnAtPos();
						this.loadedItem = column.provideNext();
						column.unload();
					}
					
					this.markDirty();
				}
					
			}
		} else if(progress != 1) {
			
			progress += 0.04D;
			
			if(progress > 1D) {
				progress = 1D;
			}
		}

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		double minX = xCoord + 0.5 - side.offsetX * 1.5;
		double maxX = xCoord + 0.5 + side.offsetX * 1.5 + dir.offsetX * 2;
		double minZ = zCoord + 0.5 - side.offsetZ * 1.5;
		double maxZ = zCoord + 0.5 + side.offsetZ * 1.5 + dir.offsetZ * 2;
		
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(
				Math.min(minX, maxX),
				yCoord,
				Math.min(minZ, maxZ),
				Math.max(minX, maxX),
				yCoord + 2,
				Math.max(minZ, maxZ)));
		tiltFront = 0;
		tiltLeft = 0;
		
		if(players.size() > 0 && !isCraneLoading()) {
			EntityPlayer player = players.get(0);
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			boolean up = props.getKeyPressed(EnumKeybind.CRANE_UP);
			boolean down = props.getKeyPressed(EnumKeybind.CRANE_DOWN);
			boolean left = props.getKeyPressed(EnumKeybind.CRANE_LEFT);
			boolean right = props.getKeyPressed(EnumKeybind.CRANE_RIGHT);
			
			if(up && !down) {
				tiltFront = 30;
				if(!worldObj.isRemote) posFront += speed;
			}
			if(!up && down) {
				tiltFront = -30;
				if(!worldObj.isRemote) posFront -= speed;
			}
			if(left && !right) {
				tiltLeft = 30;
				if(!worldObj.isRemote) posLeft += speed;
			}
			if(!left && right) {
				tiltLeft = -30;
				if(!worldObj.isRemote) posLeft -= speed;
			}
			
			if(props.getKeyPressed(EnumKeybind.CRANE_LOAD)) {
				goesDown = true;
			}
		}
		
		posFront = MathHelper.clamp_double(posFront, -spanB, spanF);
		posLeft = MathHelper.clamp_double(posLeft, -spanR, spanL);
		
		if(!worldObj.isRemote) {
			
			if(loadedItem != null && loadedItem.getItem() instanceof ItemRBMKRod) {
				this.loadedHeat = ItemRBMKRod.getHullHeat(loadedItem);
				this.loadedEnrichment = ItemRBMKRod.getEnrichment(loadedItem);
			} else {
				this.loadedHeat = 0;
				this.loadedEnrichment = 0;
			}
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("crane", setUpCrane);
			
			if(setUpCrane) { //no need to send any of this if there's NO FUCKING CRANE THERE
				nbt.setInteger("centerX", centerX);
				nbt.setInteger("centerY", centerY);
				nbt.setInteger("centerZ", centerZ);
				nbt.setInteger("spanF", spanF);
				nbt.setInteger("spanB", spanB);
				nbt.setInteger("spanL", spanL);
				nbt.setInteger("spanR", spanR);
				nbt.setInteger("height", height);
				nbt.setDouble("posFront", posFront);
				nbt.setDouble("posLeft", posLeft);
				nbt.setBoolean("loaded", this.hasItemLoaded());
				nbt.setDouble("loadedHeat", loadedHeat);
				nbt.setDouble("loadedEnrichment", loadedEnrichment);
			}
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
		}
	}
	
	public boolean hasItemLoaded() {
		
		if(!worldObj.isRemote)
			return this.loadedItem != null;
		else
			return this.hasLoaded;
	}
	
	public boolean isCraneLoading() {
		return this.progress != 1D;
	}
	
	public boolean isAboveValidTarget() {
		return getColumnAtPos() != null;
	}
	
	public boolean canTargetInteract() {
		
		IRBMKLoadable column = getColumnAtPos();
		
		if(column == null)
			return false;
		
		if(this.hasItemLoaded()) {
			return column.canLoad(loadedItem);
		} else {
			return column.canUnload();
		}
	}
	
	public IRBMKLoadable getColumnAtPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection left = dir.getRotation(ForgeDirection.DOWN);

		int x = (int)Math.floor(this.centerX - dir.offsetX * this.posFront - left.offsetX * this.posLeft + 0.5D);
		int y = this.centerY - 1;
		int z = (int)Math.floor(this.centerZ - dir.offsetZ * this.posFront - left.offsetZ * this.posLeft + 0.5D);
				
		Block b = worldObj.getBlock(x, y, z);
		
		if(b instanceof RBMKBase) {
			
			int[] pos = ((BlockDummyable)b).findCore(worldObj, x, y, z);
			if(pos != null) {
				TileEntityRBMKBase column = (TileEntityRBMKBase)worldObj.getTileEntity(pos[0], pos[1], pos[2]);
				if(column instanceof IRBMKLoadable) {
					return (IRBMKLoadable) column;
				}
			}
		}
		
		return null;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		lastPosFront = posFront;
		lastPosLeft = posLeft;
		lastProgress = progress;
		
		this.setUpCrane = nbt.getBoolean("crane");
		this.centerX = nbt.getInteger("centerX");
		this.centerY = nbt.getInteger("centerY");
		this.centerZ = nbt.getInteger("centerZ");
		this.spanF = nbt.getInteger("spanF");
		this.spanB = nbt.getInteger("spanB");
		this.spanL = nbt.getInteger("spanL");
		this.spanR = nbt.getInteger("spanR");
		this.height = nbt.getInteger("height");
		this.posFront = nbt.getDouble("posFront");
		this.posLeft = nbt.getDouble("posLeft");
		this.hasLoaded = nbt.getBoolean("loaded");
		this.posLeft = nbt.getDouble("posLeft");
		this.loadedHeat = nbt.getDouble("loadedHeat");
		this.loadedEnrichment = nbt.getDouble("loadedEnrichment");
	}
	
	public void setTarget(int x, int y, int z) {
		this.centerX = x;
		this.centerY = y + RBMKDials.getColumnHeight(worldObj) + 1;
		this.centerZ = z;

		this.spanF = 7;
		this.spanB = 7;
		this.spanL = 7;
		this.spanR = 7;
		
		this.height = 7;
		this.setUpCrane = true;
		
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.setUpCrane = nbt.getBoolean("crane");
		this.centerX = nbt.getInteger("centerX");
		this.centerY = nbt.getInteger("centerY");
		this.centerZ = nbt.getInteger("centerZ");
		this.spanF = nbt.getInteger("spanF");
		this.spanB = nbt.getInteger("spanB");
		this.spanL = nbt.getInteger("spanL");
		this.spanR = nbt.getInteger("spanR");
		this.height = nbt.getInteger("height");
		this.posFront = nbt.getDouble("posFront");
		this.posLeft = nbt.getDouble("posLeft");
		
		NBTTagCompound held = nbt.getCompoundTag("held");
		this.loadedItem = ItemStack.loadItemStackFromNBT(held);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("crane", setUpCrane);
		nbt.setInteger("centerX", centerX);
		nbt.setInteger("centerY", centerY);
		nbt.setInteger("centerZ", centerZ);
		nbt.setInteger("spanF", spanF);
		nbt.setInteger("spanB", spanB);
		nbt.setInteger("spanL", spanL);
		nbt.setInteger("spanR", spanR);
		nbt.setInteger("height", height);
		nbt.setDouble("posFront", posFront);
		nbt.setDouble("posLeft", posLeft);
		
		if(this.loadedItem != null) {
			NBTTagCompound held = new NBTTagCompound();
			this.loadedItem.writeToNBT(held);
			nbt.setTag("held", held);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return this.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "rbmk_crane";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] move(Context context, Arguments args) {
		if(setUpCrane == true) {
			String textbruh = args.checkString(0);
			
			switch(textbruh) {
				case "up":
					tiltFront = 30;
					if(!worldObj.isRemote) posFront += speed;
					break;
				case "down":
					tiltFront = -30;
					if(!worldObj.isRemote) posFront -= speed;
					break;
				case "left":
					tiltLeft = 30;
					if(!worldObj.isRemote) posLeft += speed;
					break;
				case "right":
					tiltLeft = -30;
					if(!worldObj.isRemote) posLeft -= speed;
					break;
			}
			
			return new Object[] {};
		}
		return new Object[] {"Crane not found"};
	}
	
	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] load(Context context, Arguments args) {
		if (setUpCrane == true) {
			goesDown = true;
			return new Object[] {};
		}
		return new Object[] {"Crane not found"};
	}
	
	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDepletion(Context context, Arguments args) {
		if(loadedItem != null && loadedItem.getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getEnrichment(loadedItem)};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getXenonPoison(Context context, Arguments args) {
		if(loadedItem != null && loadedItem.getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getPoison(loadedItem)};
		}
		return new Object[] {"N/A"};
	}
}
