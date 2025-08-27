package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.container.ContainerRBMKAutoloader;
import com.hbm.inventory.gui.GUIRBMKAutoloader;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityRBMKAutoloader extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, ICopiable {
	
	public double piston;
	public double renderPiston;
	public double lastPiston;
	private double syncPiston;
	private int turnProgress;
	private boolean isRetracting = true;
	private int delay = 0;
	
	public static double speed = 0.005D;
	
	public int cycle = 50;
	
	private AudioWrapper audioLift;

	public TileEntityRBMKAutoloader() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.rbmkAutoloader";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(delay > 0) delay--;
			
			if(delay <= 0 && this.isRetracting && this.piston > 0D) {
				this.piston -= this.speed;
				if(this.piston <= 0) {
					this.piston = 0;
					this.delay = 40;
				}
			}
			
			// check for connected fuel rod and decide whether to begin working
			if(isRetracting && worldObj.getTotalWorldTime() % 20 == 0 && this.hasFuel() && this.hasSpace()) {
				Block below = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
				if(below instanceof RBMKBase) {
					int[] pos = ((RBMKBase) below).findCore(worldObj, xCoord, yCoord - 1, zCoord);
					TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					if(tile instanceof TileEntityRBMKRod) {
						TileEntityRBMKRod rod = (TileEntityRBMKRod) tile;
						if(rod.slots[0] == null || (rod.slots[0] != null && rod.slots[0].getItem() instanceof ItemRBMKRod && ItemRBMKRod.getEnrichment(rod.slots[0]) * 100 < cycle)) {
							this.isRetracting = false;
						}
					}
				}
			}
			
			if(delay <= 0 && !this.isRetracting && this.piston < 1D) {
				this.piston += this.speed;
				if(this.piston >= 1) {
					this.piston = 1;
					this.delay = 40;
				}
			}
			
			// once the piston is fully extended
			if(!isRetracting && this.piston >= 1D) {
				this.piston = 1D;
				
				Block below = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
				if(below instanceof RBMKBase) {
					int[] pos = ((RBMKBase) below).findCore(worldObj, xCoord, yCoord - 1, zCoord);
					TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					if(tile instanceof TileEntityRBMKRod) {
						TileEntityRBMKRod rod = (TileEntityRBMKRod) tile;
						// try to take out the old fuel rod
						if(rod.slots[0] != null && this.hasSpace()) {
							for(int i = 9; i < 18; i++) {
								if(slots[i] == null) {
									slots[i] = rod.slots[0].copy();
									rod.slots[0] = null;
									break;
								}
							}
						}
						// if there's space, try and insert a new fuel rod
						if(rod.slots[0] == null) {
							for(int i = 0; i < 9; i++) {
								ItemStack stack = slots[i];
								if(stack != null && stack.getItem() instanceof ItemRBMKRod && ItemRBMKRod.getEnrichment(stack) * 100 >= cycle) {
									rod.slots[0] = stack.copy();
									slots[i] = null;
									break;
								}
							}
						}
						
						this.isRetracting = true;
						this.delay = 40;
					}
				}
			}
			
			this.networkPackNT(100);
		} else {

			this.lastPiston = this.renderPiston;

			if(this.turnProgress > 0) {
				this.renderPiston = this.renderPiston + ((this.syncPiston - this.renderPiston) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPiston = this.syncPiston;
			}
			
			if(this.renderPiston > 0.01 && this.renderPiston < 0.99) {
				if(this.audioLift == null || !this.audioLift.isPlaying()) {
					this.audioLift = MainRegistry.proxy.getLoopedSound("hbm:door.wgh_start", xCoord, yCoord, zCoord, this.getVolume(0.75F), 25F, 1.0F, 5);
					this.audioLift.startSound();
				}
				this.audioLift.updateVolume(this.getVolume(0.75F));
				this.audioLift.keepAlive();
			} else {
				if(this.audioLift != null) {
					this.audioLift.stopSound();
					this.audioLift = null;
					MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:door.wgh_stop", this.getVolume(2F), 1F);
				}
			}
			
			if(this.renderPiston > 0.99) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0F);
				data.setFloat("base", 0.25F);
				data.setFloat("max", 1.5F);
				data.setInteger("life", 70 + worldObj.rand.nextInt(30));
				data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.125);
				data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.125);
				data.setDouble("posY", yCoord + 0.25);
				data.setBoolean("noWind", true);
				data.setFloat("alphaMod", 2F);
				data.setFloat("strafe", 0.05F);
				for(int i = 0; i < 3; i++) MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	public boolean hasFuel() {
		for(int i = 0; i < 9; i++) {
			ItemStack stack = slots[i];
			if(stack != null && stack.getItem() instanceof ItemRBMKRod && ItemRBMKRod.getEnrichment(stack) * 100 >= cycle) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSpace() {
		for(int i = 9; i < 18; i++) if(slots[i] == null) return true;
		return false;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.piston);
		buf.writeInt(this.cycle);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.syncPiston = buf.readDouble();
		this.cycle = buf.readInt();

		this.turnProgress = 2;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof ItemRBMKRod && ItemRBMKRod.getEnrichment(stack) * 100 >= cycle && i < 9;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return this.piston <= 0 ? new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 } : new int[0];
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 9;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		piston = nbt.getDouble("piston");
		isRetracting = nbt.getBoolean("ret");
		delay = nbt.getInteger("delay");
		cycle = nbt.getInteger("cycle");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("piston", piston);
		nbt.setBoolean("ret", isRetracting);
		nbt.setInteger("delay", delay);
		nbt.setInteger("cycle", cycle);
	}

	protected AxisAlignedBB aabb;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(aabb != null) return aabb;
		aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 9, zCoord + 1);
		return aabb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("minus") && this.cycle > 5) this.cycle -= 5;
		if(data.hasKey("plus") && this.cycle < 95) this.cycle += 5;
		this.cycle = MathHelper.clamp_int(cycle, 5, 95);
		this.markChanged();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKAutoloader(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKAutoloader(player.inventory, this);
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("cycle", cycle);
		return data;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		if(nbt.hasKey("cycle")) {
			this.cycle = MathHelper.clamp_int(nbt.getInteger("cycle"), 5, 95);
		}
	}
}
