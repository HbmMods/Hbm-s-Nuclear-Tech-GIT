package com.hbm.saveddata.satellites;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.recipes.SpaceAssemblerRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrive.EnumDriveType;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.util.BobMathUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatelliteScience extends SatelliteBase {
	
	public static final int COOLDOWN = 15 * 60 * 20;
	public long lastScience;
	
	public static final int SENSOR_DURATION = 100 * 60 * 60 * 20; // 100 fucking hour recipe
	public int sensorProgress;
	public int sensorCount;
	
	public int assemblerCount;
	public double assemblerProgress;
	
	// FIFO
	public List<AssemblerTask> assemblerTasks = new ArrayList();

	@Override
	public String getType() {
		return "SCIENCE_PROBE";
	}
	
	@Override
	public boolean hasData(World world) {
		if(super.hasData(world)) return true;
		
		if(world.getTotalWorldTime() > this.lastScience + COOLDOWN) {
			this.produceData(EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_FLIGHTDATA);
			this.lastScience = world.getTotalWorldTime();
			this.markDirty();
		}
		
		return super.hasData(world);
	}

	@Override
	public void onPartDelivered(World world, ItemStack part) {
		
		if(part.getItem() == ModItems.satellite) {

			if(part.getItemDamage() == EnumSatType.SCIENCE_SENSOR.ordinal()) this.sensorCount++;
			if(part.getItemDamage() == EnumSatType.SCIENCE_ASSEMBLER.ordinal()) this.assemblerCount++;
			this.markDirty();
			return;
		}
		
		GenericRecipe recipe = SpaceAssemblerRecipes.INSTANCE.getRecipe(part);
		
		if(recipe != null) {
			this.assemblerTasks.add(new AssemblerTask(recipe));
			this.markDirty();
		}
	}

	@Override
	public void onUpdateTick(World world) {
		
		// sensor relay crap
		if(this.sensorProgress < SENSOR_DURATION) {
			if(this.sensorCount > 0) {
				this.sensorProgress += this.sensorCount;
				this.markDirty();
			}
		} else {
			this.sensorProgress = 0;
			this.produceData(EnumDriveType.DISK_EMPTY, EnumDriveType.DISK_ORBITDATA);
			this.markDirty();
		}
		
		// space assembler
		if(this.assemblerCount > 0 && this.requestableSlots.length <= 0 && this.assemblerTasks.size() > 0) {
			
			AssemblerTask task = this.assemblerTasks.get(0);
			this.assemblerProgress += (double) this.assemblerCount / task.duration;
			
			if(this.assemblerProgress >= 1) {
				
				GenericRecipe recipe = SpaceAssemblerRecipes.INSTANCE.recipeNameMap.get(task.recipe);
				
				if(recipe != null) {
					this.requestableSlots = new ItemStack[recipe.outputItem.length];
					for(int i = 0; i < recipe.outputItem.length; i++) {
						this.requestableSlots[i] = recipe.outputItem[i].collapse();
					}
				}

				this.assemblerProgress = 0;
				this.markDirty();
			}
		}
	}
	
	@Override
	public IChatComponent[] getInfo(World world) {
		
		int cooldown = (int) ((lastScience + COOLDOWN) - world.getTotalWorldTime());
		int seconds = cooldown / 20;
		
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE.ordinal())) + ".name"),
				cooldown <= 0 ? new ChatComponentTranslation("satellite.ready") : new ChatComponentTranslation("satellite.cooldown", (seconds / 60) + "m" + (seconds % 60) + "s"),
						
				this.sensorCount > 0 ? new ChatComponentTranslation("satellite.sensors", this.sensorCount) : null,
				this.sensorCount > 0 ? new ChatComponentTranslation("satellite.pending", BobMathUtil.format(SENSOR_DURATION - sensorProgress)) : null,
				this.driveOutput == EnumDriveType.DISK_ORBITDATA ? new ChatComponentTranslation("satellite.data") : null,

				this.assemblerCount > 0 ? new ChatComponentTranslation("satellite.assemblers", this.assemblerCount) : null,
				this.assemblerCount > 0 ? new ChatComponentTranslation("satellite.progress", (int) Math.round(this.assemblerProgress * 100) + "%") : null,
				this.assemblerCount > 0 ? new ChatComponentTranslation("satellite.queue", this.assemblerTasks.size()) : null,
		};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("lastScience", lastScience);
		nbt.setInteger("sensorProgress", sensorProgress);
		nbt.setInteger("sensorCount", sensorCount);
		nbt.setInteger("assemblerCount", assemblerCount);
		nbt.setDouble("assemblerProgress", assemblerProgress);
		
		nbt.setInteger("taskCount", this.assemblerTasks.size());
		for(int i = 0; i < this.assemblerTasks.size(); i++) {
			AssemblerTask task = this.assemblerTasks.get(i);
			nbt.setString("task" + i, task.recipe);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		lastScience = nbt.getLong("lastScience");
		sensorProgress = nbt.getInteger("sensorProgress");
		sensorCount = nbt.getInteger("sensorCount");
		assemblerCount = nbt.getInteger("assemblerCount");

		this.assemblerTasks.clear();
		int taskCount = nbt.getInteger("taskCount");
		
		for(int i = 0; i < taskCount; i++) {
			this.assemblerTasks.add(new AssemblerTask(nbt.getString("task" + i)));
		}
	}
	
	public class AssemblerTask {
		
		public String recipe;
		public int duration;
		public ItemStack icon;
		
		public AssemblerTask(GenericRecipe recipe) {
			
			if(recipe != null) {
				this.recipe = recipe.getInternalName();
				this.duration = recipe.duration;
				this.icon = recipe.getIcon();
			} else {
				this.recipe = "null";
				this.duration = 1;
				this.icon = new ItemStack(ModItems.nothing);
			}
		}
		
		public AssemblerTask(String name) {
			this(SpaceAssemblerRecipes.INSTANCE.recipeNameMap.get(name));
		}
	}
}
