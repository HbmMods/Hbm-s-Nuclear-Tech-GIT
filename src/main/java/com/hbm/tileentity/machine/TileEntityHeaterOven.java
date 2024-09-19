package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.container.ContainerFirebox;
import com.hbm.inventory.gui.GUIFirebox;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IConfigurableMachine;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityHeaterOven extends TileEntityFireboxBase implements IConfigurableMachine {

	public static int baseHeat = 500;
	public static double timeMult = 0.125D;
	public static int maxHeatEnergy = 500_000;
	public static double heatEff = 0.5D;
	public static ModuleBurnTime burnModule = new ModuleBurnTime()
			.setLigniteTimeMod(1.25)
			.setCoalTimeMod(1.25)
			.setCokeTimeMod(1.25)
			.setSolidTimeMod(1.5)
			.setRocketTimeMod(1.5)
			.setBalefireTimeMod(0.5)

			.setLigniteHeatMod(2)
			.setCoalHeatMod(2)
			.setCokeHeatMod(2)
			.setSolidHeatMod(3)
			.setRocketHeatMod(5)
			.setBalefireHeatMod(15);

	public TileEntityHeaterOven() {
		super();
	}

	@Override
	public String getName() {
		return "container.heaterOven";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.tryPullHeat();
		}
		
		super.updateEntity();
	}
	
	protected void tryPullHeat() {
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int toPull = Math.max(Math.min(source.getHeatStored(), this.getMaxHeat() - this.heatEnergy), 0);
			this.heatEnergy += toPull * heatEff;
			source.useUpHeat(toPull);
		}
	}

	@Override
	public ModuleBurnTime getModule() {
		return burnModule;
	}

	@Override
	public int getBaseHeat() {
		return baseHeat;
	}

	@Override
	public double getTimeMult() {
		return timeMult;
	}

	@Override
	public int getMaxHeat() {
		return maxHeatEnergy;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFirebox(player.inventory, this);
	}

	@SideOnly(Side.CLIENT) private ResourceLocation texture;
	
	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(texture == null) texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heating_oven.png");
		return new GUIFirebox(player.inventory, this, texture);
	}

	@Override
	public String getConfigName() {
		return "heatingoven";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		baseHeat = IConfigurableMachine.grab(obj, "I:baseHeat", baseHeat);
		timeMult = IConfigurableMachine.grab(obj, "D:burnTimeMult", timeMult);
		heatEff = IConfigurableMachine.grab(obj, "D:heatPullEff", heatEff);
		maxHeatEnergy = IConfigurableMachine.grab(obj, "I:heatCap", maxHeatEnergy);
		if(obj.has("burnModule")) {
			burnModule.readIfPresent(obj.get("M:burnModule").getAsJsonObject());
		}
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:baseHeat").value(baseHeat);
		writer.name("D:burnTimeMult").value(timeMult);
		writer.name("D:heatPullEff").value(heatEff);
		writer.name("I:heatCap").value(maxHeatEnergy);
		writer.name("M:burnModule").beginObject();
		burnModule.writeConfig(writer);
		writer.endObject();
	}
}
