package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.handler.MissileStruct;
import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUISoyuzLauncher;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySoyuzLauncher extends TileEntityMachineBase implements ISidedInventory, IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider, IFluidCopiable {

	public long power;
	public static final long maxPower = 1000000;
	public FluidTank[] tanks;
	//0: sat, 1: cargo
	public byte mode;
	public boolean starting;
	public int countdown;
	public static final int maxCount = 600;
	public byte rocketType = -1;
	
	private AudioWrapper audio;
	
	public MissileStruct load;

	public TileEntitySoyuzLauncher() {
		super(27);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.KEROSENE, 128_000);
		tanks[1] = new FluidTank(Fluids.OXYGEN, 128_000);
	}

	@Override
	public String getName() {
		return "container.soyuzLauncher";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			tanks[0].loadTank(4, 5, slots);
			tanks[1].loadTank(6, 7, slots);
			
			power = Library.chargeTEFromItems(slots, 8, power, maxPower);
			
			if(!starting || !canLaunch()) {
				countdown = maxCount;
				starting = false;
			} else if(countdown > 0) {
				countdown--;
				
				if(countdown % 100 == 0 && countdown > 0)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.hatch", 100F, 1.1F);
				
			} else {
				liftOff();
			}

			networkPackNT(250);
		}
		
		if(worldObj.isRemote) {
			if(!starting || !canLaunch()) {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
				
				countdown = maxCount;
				
			} else if(countdown > 0) {

				if(audio == null) {
					audio = this.createAudioLoop();
					audio.updateVolume(100);
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}
				
				countdown--;
			}
			
			List<EntitySoyuz> entities = worldObj.getEntitiesWithinAABB(EntitySoyuz.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "shockRand");
				data.setInteger("count", 50);
				data.setDouble("strength", worldObj.rand.nextGaussian() * 3 + 6);
				data.setDouble("posX", xCoord + 0.5);
				data.setDouble("posY", yCoord - 3);
				data.setDouble("posZ", zCoord + 0.5);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	protected List<DirPos> conPos;
	protected List<DirPos> getConPos() {
		
		if(conPos != null)
			return conPos;
		
		conPos = new ArrayList();
		
		for(ForgeDirection dir : new ForgeDirection[] {Library.POS_X, Library.POS_Z, Library.NEG_X, Library.NEG_Z}) {
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			for(int i = -6; i <= 6; i++) {
				conPos.add(new DirPos(xCoord + dir.offsetX * 7 + rot.offsetX * i, yCoord + 0, zCoord + dir.offsetZ * 7 + rot.offsetZ * i, dir));
				conPos.add(new DirPos(xCoord + dir.offsetX * 7 + rot.offsetX * i, yCoord - 1, zCoord + dir.offsetZ * 7 + rot.offsetZ * i, dir));
			}
		}
		
		return conPos;
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.soyuzReady", xCoord, yCoord, zCoord, 2.0F, 100F, 1.0F);
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeByte(mode);
		buf.writeBoolean(starting);
		buf.writeByte(this.getType());
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		mode = buf.readByte();
		starting = buf.readBoolean();
		rocketType = buf.readByte();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	public void startCountdown() {
		
		if(canLaunch())
			starting = true;
	}
	
	public void liftOff() {
		
		this.starting = false;
		
		int req = this.getFuelRequired();
		int pow = this.getPowerRequired();
		
		EntitySoyuz soyuz = new EntitySoyuz(worldObj);
		soyuz.setSkin(this.getType());
		soyuz.mode = this.mode;
		soyuz.setLocationAndAngles(xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0);
		worldObj.spawnEntityInWorld(soyuz);

		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:entity.soyuzTakeoff", 100F, 1.1F);

		tanks[0].setFill(tanks[0].getFill() - req);
		tanks[1].setFill(tanks[1].getFill() - req);
		power -= pow;
		
		if(mode == 0) {
			soyuz.setSat(slots[2]);
			
			if(this.orbital() == 2)
				slots[3] = null;
			
			slots[2] = null;
		}
		
		if(mode == 1) {
			List<ItemStack> payload = new ArrayList();
			
			for(int i = 9; i < 27; i++) {
				payload.add(slots[i]);
				slots[i] = null;
			}

			soyuz.targetX = slots[1].stackTagCompound.getInteger("xCoord");
			soyuz.targetZ = slots[1].stackTagCompound.getInteger("zCoord");
			soyuz.setPayload(payload);
		}
		
		slots[0] = null;
	}
	
	public boolean canLaunch() {
		
		return hasRocket() && hasFuel() && hasRocket() && hasPower() && designator() != 1 && orbital() != 1 && satellite() != 1;
	}
	
	public boolean hasFuel() {
		
		return tanks[0].getFill() >= getFuelRequired();
	}
	
	public boolean hasOxy() {

		return tanks[1].getFill() >= getFuelRequired();
	}
	
	public int getFuelRequired() {
		
		if(mode == 1)
			return Math.min(5000 + getDist(), 128_000);
		
		return 128_000;
	}
	
	public int getDist() {
		
		if(designator() == 2) {
			int x = slots[1].stackTagCompound.getInteger("xCoord");
			int z = slots[1].stackTagCompound.getInteger("zCoord");
			
			return (int) Vec3.createVectorHelper(xCoord - x, 0, zCoord - z).lengthVector();
		}
			
		return 0;
	}
	
	public boolean hasPower() {
		
		return power >= getPowerRequired();
	}
	
	public int getPowerRequired() {
		
		return (int) (maxPower * 0.75);
	}
	
	private byte getType() {
		
		if(!hasRocket())
			return -1;
		
		return (byte) slots[0].getItemDamage();
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public boolean hasRocket() {
		return slots[0] != null && slots[0].getItem() == ModItems.missile_soyuz;
	}
	
	//0: designator not required
	//1: designator required but not present
	//2: designator present
	public int designator() {
		
		if(mode == 0) {
			return 0;
		}
		if(slots[1] != null && slots[1].getItem() instanceof IDesignatorItem && ((IDesignatorItem)slots[1].getItem()).isReady(worldObj, slots[1], xCoord, yCoord, zCoord)) {
			return 2;
		}
		
		return 1;
	}
	
	//0: sat not required
	//1: sat required but not present
	//2: sat present
	public int satellite() {
		
		if(mode == 1)
			return 0;
		
		if(slots[2] != null) {
			return 2;
		}
		return 1;
	}

	//0: module not required
	//1: module required but not present
	//2: module present
	public int orbital() {
		
		if(mode == 1)
			return 0;
		
		if(slots[2] != null && (slots[2].getItem() == ModItems.sat_gerald || slots[2].getItem() == ModItems.sat_lunar_miner)) {
			if(slots[3] != null && slots[3].getItem() == ModItems.missile_soyuz_lander)
				return 2;
			return 1;
		}
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		tanks[0].readFromNBT(nbt, "fuel");
		tanks[1].readFromNBT(nbt, "oxidizer");
		power = nbt.getLong("power");
		mode = nbt.getByte("mode");

		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();

		tanks[0].writeToNBT(nbt, "fuel");
		tanks[1].writeToNBT(nbt, "oxidizer");
		nbt.setLong("power", power);
		nbt.setByte("mode", mode);

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return tanks;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSoyuzLauncher(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISoyuzLauncher(player.inventory, this);
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
