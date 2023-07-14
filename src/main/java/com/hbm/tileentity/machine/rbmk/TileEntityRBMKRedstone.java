package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerRBMKGeneric;
import com.hbm.inventory.gui.GUIRBMKRedstone;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.network.RTTYSystem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKRedstone extends TileEntityRBMKSlottedBase implements IControlReceiver, SimpleComponent {


	public int mode = 1;
	public String channel = "";
	public int threshold = 0;
	public boolean active = false;
	public double sflux = 0.0;
	public double fflux = 0.0;
	public double value = 0.0;
	public String trunc_v = "";

	@Override
	public String getName() {
		return "container.rbmkRedstone";
	}

	public TileEntityRBMKRedstone() {
		super(0);
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {

			switch(mode) {
				// mode = 1: Heat
				// mode = 2: Slow Flux
				// mode = 3: Fast Flux
				case (1):
					value = heat;
					active = value > threshold;
				break;
				case (2):
					value = sflux;
					active = value > threshold;
				break;
				case (3):
					value = fflux;
					active = value > threshold;
				break;
			}
			if(!channel.isEmpty()) {
				if(active) {
					RTTYSystem.broadcast(worldObj, channel, 15);
				} else {
					RTTYSystem.broadcast(worldObj, channel, 0);
				}
			}
		}
		super.updateEntity();
	}

	public void processFlux(Double flux, IRBMKFluxReceiver.NType stream) {
		if(stream == IRBMKFluxReceiver.NType.SLOW) {
			sflux = flux;
		} else if (stream == IRBMKFluxReceiver.NType.FAST) {
			fflux = flux;
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {return true;}

	@Override
	public void receiveControl(NBTTagCompound data) {

		if(data.hasKey("Ch_set")) {
			channel = data.getString("Ch_set");
			threshold = data.getInteger("Threshold");
		} else if (data.hasKey("Mode")) {
			int newMode = data.getInteger("Mode") % 4;
			if(newMode <= 0)
				newMode = 1;
			mode = newMode;
		}
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		mode = nbt.getInteger("mode");
		channel = nbt.getString("channel");
		threshold = nbt.getInteger("threshold");
		active = nbt.getBoolean("active");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("mode", mode);
		nbt.setString("channel", channel);
		nbt.setInteger("threshold", threshold);
		nbt.setBoolean("active", active);
	}

	public void getDiagData(NBTTagCompound nbt) {
		super.getDiagData(nbt);
		nbt.setString("mode",  String.valueOf(mode));
		nbt.setString("channel", channel);
		nbt.setString("threshold", String.valueOf(threshold));
		nbt.setString("active", String.valueOf(active));
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();

		if(value >= 100) {
			value = (int) value;
		} else {
			value = ((int) (value * 10)) / 10F;
		}
		data.setInteger("mode", mode);
		data.setString("value", trunc_v);
		data.setInteger("threshold", threshold);
		data.setBoolean("active", active);

		return data;
	}

	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.REDSTONE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String getComponentName() {
		return "rbmk_redstone_rod";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKGeneric(player.inventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKRedstone(player.inventory, this);
	}
}
