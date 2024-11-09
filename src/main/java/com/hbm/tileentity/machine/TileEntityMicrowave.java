package com.hbm.tileentity.machine;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.ICopiable;
import com.hbm.inventory.container.ContainerMicrowave;
import com.hbm.inventory.gui.GUIMicrowave;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMicrowave extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider, SimpleComponent, CompatHandler.OCComponent, ICopiable {
	
	public long power;
	public static final long maxPower = 50000;
	public static final int consumption = 50;
	public static final int maxTime = 300;
	public int time;
	public int speed;
	public static final int maxSpeed = 5;

	public TileEntityMicrowave() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.microwave";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			
			this.power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			
			if(canProcess()) {
				
				if(speed >= maxSpeed) {
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					ExplosionVNT vnt = new ExplosionVNT(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5);
					vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, 50));
					vnt.setPlayerProcessor(new PlayerProcessorStandard());
					vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
					vnt.explode();
					return;
				}
				
				if(time >= maxTime) {
					process();
					time = 0;
				}
				
				if(canProcess()) {
					power -= consumption;
					time += speed * 2;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("time", time);
			data.setInteger("speed", speed);
			networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		
		power = data.getLong("power");
		time = data.getInteger("time");
		speed = data.getInteger("speed");
	}
	
	public void handleButtonPacket(int value, int meta) {
		
		if(value == 0)
			speed++;
		
		if(value == 1)
			speed--;
		
		if(speed < 0)
			speed = 0;
		
		if(speed > maxSpeed)
			speed = maxSpeed;
	}
	
	private void process() {
		
		ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(slots[0]).copy();
		
		if(slots[1] == null) {
			slots[1] = stack;
		} else {
			slots[1].stackSize += stack.stackSize;
		}
		
		this.decrStackSize(0, 1);
		
		this.markDirty();
	}
	
	private boolean canProcess() {
		
		if(speed  == 0)
			return false;
		
		if(power < consumption)
			return false;
		
		if(slots[0] != null && FurnaceRecipes.smelting().getSmeltingResult(slots[0]) != null) {
			
			ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(slots[0]);
			
			if(!(slots[0].getItem() instanceof ItemFood) && !(stack.getItem() instanceof ItemFood)) return false;
			if(slots[1] == null) return true;
			if(!stack.isItemEqual(slots[1])) return false;
			
			return stack.stackSize + slots[1].stackSize <= stack.getMaxStackSize();
		}
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[] { 1 } : new int[] { 0 };
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (time * i) / maxTime;
	}
	
	public int getSpeedScaled(int i) {
		return (speed * i) / maxSpeed;
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
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		speed = nbt.getInteger("speed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		nbt.setInteger("speed", speed);
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "microwave";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] test(Context context, Arguments args) {
		return new Object[] {"This is a testing device for everything OC."};
	}

	@Callback(direct = true, getter = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] variableget(Context context, Arguments args) {
		return new Object[] {speed, "test of the `getter` callback function"};
	}

	@Callback(direct = true, setter = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] variableset(Context context, Arguments args) {
		speed = MathHelper.clamp_int(args.checkInteger(0), 0, 5);
		return new Object[] {"test of the `setter` callback function"};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMicrowave(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMicrowave(player.inventory, this);
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("microSpeed", speed);
		return null;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		if(nbt.hasKey("microSpeed")) speed = nbt.getInteger("microSpeed");
	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		return new String[]{ "copyTool.speed"};
	}
}
