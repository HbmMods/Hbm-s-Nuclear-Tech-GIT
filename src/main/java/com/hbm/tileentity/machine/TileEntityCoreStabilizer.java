package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerCoreStabilizer;
import com.hbm.inventory.gui.GUICoreStabilizer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreStabilizer extends TileEntityMachineBase implements IEnergyReceiverMK2, SimpleComponent, IGUIProvider, IInfoProviderEC, CompatHandler.OCComponent, IRORValueProvider, IRORInteractive {

	public long power;
	public static final long maxPower = 2500000000L;
	public int watts;
	public int beam;
	
	public static final int range = 15;

	public TileEntityCoreStabilizer() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.dfcStabilizer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			watts = MathHelper.clamp_int(watts, 1, 100);
			int demand = (int) Math.pow(watts, 4);

			beam = 0;
			
			if(power >= demand && slots[0] != null && slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				for(int i = 1; i <= range; i++) {
	
					int x = xCoord + dir.offsetX * i;
					int y = yCoord + dir.offsetY * i;
					int z = zCoord + dir.offsetZ * i;
					
					TileEntity te = worldObj.getTileEntity(x, y, z);
	
					if(te instanceof TileEntityCore) {
						
						TileEntityCore core = (TileEntityCore)te;
						core.field = Math.max(core.field, watts);
						this.power -= demand;
						beam = i;
						
						long dmg = ItemLens.getLensDamage(slots[0]);
						dmg += watts;
						
						if(dmg >= ((ItemLens)ModItems.ams_lens).maxDamage)
							slots[0] = null;
						else
							ItemLens.setLensDamage(slots[0], dmg);
						
						break;
					}
					
					if(!worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z))
						break;
				}
			}

			this.networkPackNT(250);
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeInt(watts);
		buf.writeInt(beam);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		this.power = buf.readLong();
		this.watts = buf.readInt();
		this.beam = buf.readInt();
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (watts * i) / 100;
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
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		watts = nbt.getInteger("watts");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("watts", watts);
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "dfc_stabilizer";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInput(Context context, Arguments args) {
		return new Object[] {watts};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDurability(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
			return new Object[] {ItemLens.getLensDamage(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		Object lens_damage_buf;
		if(slots[0] != null && slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
			lens_damage_buf = ItemLens.getLensDamage(slots[0]);
		} else {
			lens_damage_buf = "N/A";
		}
		return new Object[] {power, maxPower, watts, lens_damage_buf};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setInput(Context context, Arguments args) {
		int newOutput = args.checkInteger(0);
		watts = MathHelper.clamp_int(newOutput, 0, 100);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreStabilizer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreStabilizer(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		int demand = (int) Math.pow(watts, 4);
		long damage = ItemLens.getLensDamage(slots[0]);
		ItemLens lens = (ItemLens) com.hbm.items.ModItems.ams_lens;
		if(getPower() >= demand && slots[0] != null && slots[0].getItem() == lens && damage < 432000000L)
			data.setDouble(CompatEnergyControl.D_CONSUMPTION_HE, demand);
		else
			data.setDouble(CompatEnergyControl.D_CONSUMPTION_HE, 0);
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "durability",
				PREFIX_VALUE + "durabilitypercent",
				PREFIX_FUNCTION + "setpower" + NAME_SEPARATOR + "percent",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "durability").equals(name))			return (slots[0] != null && slots[0].getItem() == ModItems.ams_lens) ? "" + (((ItemLens) slots[0].getItem()).maxDamage - ItemLens.getLensDamage(slots[0])) : "0";
		if((PREFIX_VALUE + "durabilitypercent").equals(name))	return (slots[0] != null && slots[0].getItem() == ModItems.ams_lens) ? "" + ((((ItemLens) slots[0].getItem()).maxDamage - ItemLens.getLensDamage(slots[0])) * 100 / ((ItemLens) slots[0].getItem()).maxDamage) : "0";
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		
		if((PREFIX_FUNCTION + "setpower").equals(name) && params.length > 0) {
			int watts = IRORInteractive.parseInt(params[0], 0, 100);
			this.watts = watts;
			this.markChanged();
			return null;
		}
		
		return null;
	}
}
