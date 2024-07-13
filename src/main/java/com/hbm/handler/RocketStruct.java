package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;
import com.hbm.render.util.MissilePart;
import com.hbm.util.Tuple.Pair;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants;

public class RocketStruct {
	
	public MissilePart capsule;
	public ArrayList<RocketStage> stages = new ArrayList<>();

	public static final int MAX_STAGES = 5;

	public RocketStruct() {}

	public RocketStruct(ItemStack capsule) {
		this.capsule = MissilePart.getPart(capsule);
	}

	public RocketStruct(MissilePart capsule) {
		this.capsule = capsule;
	}

	public void addStage(ItemStack fuselage, ItemStack fins, ItemStack thruster) {
		addStage(MissilePart.getPart(fuselage), MissilePart.getPart(fins), MissilePart.getPart(thruster), fuselage != null ? fuselage.stackSize : 1, thruster != null ? thruster.stackSize : 1);
	}

	public void addStage(MissilePart fuselage, MissilePart fins, MissilePart thruster, int fuselageCount, int thrusterCount) {
		RocketStage stage = new RocketStage();
		stage.fuselage = fuselage;
		stage.fins = fins;
		stage.thruster = thruster;
		stage.fuselageCount = fuselageCount;
		stage.thrusterCount = thrusterCount;
		stages.add(0, stage);
	}

	public boolean validate() {
		if(capsule == null || capsule.type != PartType.WARHEAD || ((ItemCustomMissilePart)capsule.part).attributes[0] != WarheadType.APOLLO)
			return false;
		
		if(stages.size() == 0)
			return false;

		for(RocketStage stage : stages) {
			if(stage.fuselage == null || stage.fuselage.type != PartType.FUSELAGE) return false;
			if(stage.fins != null && stage.fins.type != PartType.FINS) return false;
			if(stage.thruster == null || stage.thruster.type != PartType.THRUSTER) return false;

			if(stage.thrusterCount > stage.fuselageCount || stage.fuselageCount % stage.thrusterCount != 0) return false;
		}
		
		return true;
	}

	// Lists any validation issues so the player can rectify easily
	public List<String> findIssues() {
		List<String> issues = new ArrayList<String>();

		// If we have no parts, we have no worries
		if(capsule == null && stages.size() == 0) return issues;

		return issues;
	}

	// NONE fluid is solid fuel
	public Map<FluidType, Integer> getFillRequirement() {
		Map<FluidType, Integer> tanks = new HashMap<>();

		for(RocketStage stage : stages) {
			if(stage.thruster == null || stage.fuselage == null) continue;

			FluidType fuel = stage.thruster.part.getFuel();
			FluidType oxidizer = stage.thruster.part.getOxidizer();

			if(fuel != null) {
				int amount = stage.fuselage.part.getTankSize() * stage.fuselageCount;
				if(tanks.containsKey(fuel)) amount += tanks.get(fuel);
				tanks.put(fuel, amount);
			}

			if(oxidizer != null) {
				int amount = stage.fuselage.part.getTankSize() * stage.fuselageCount;
				if(tanks.containsKey(oxidizer)) amount += tanks.get(oxidizer);
				tanks.put(oxidizer, amount);
			}
		}

		return tanks;
	}

	public int getDryMass() {
		int mass = 0;

		if(capsule != null) mass += capsule.part.mass;

		for(RocketStage stage : stages) {
			if(stage.fuselage != null) mass += stage.fuselage.part.mass * stage.fuselageCount;
			if(stage.thruster != null) mass += stage.thruster.part.mass * stage.thrusterCount;
		}

		return MathHelper.ceiling_float_int(mass * 0.01F);
	}

	public double getHeight() {
		double height = 0;
		
		if(capsule != null) height += capsule.height;

		boolean isDeployed = true;

		for(RocketStage stage : stages) {
			if(stage.fuselage != null) height += stage.fuselage.height * stage.getStack();
			height += Math.max(stage.thruster != null ? stage.thruster.height : 0, isDeployed && stage.fins != null ? stage.fins.height : 0);
			isDeployed = false;
		}

		return height;
	}

	public double getHeight(int stageNum) {
		double height = 0;

		if(stages.size() > 0) {
			RocketStage stage = stages.get(Math.min(stageNum, stages.size() - 1));
			if(stage.fuselage != null) height += stage.fuselage.height * stage.getStack();
			height += Math.max(stage.thruster != null ? stage.thruster.height : 0, stageNum == 0 && stage.fins != null ? stage.fins.height : 0);
		}

		if(stages.size() == 0 || stageNum == stages.size() - 1) {
			if(capsule != null) height += capsule.height;
		}

		return height;
	}

	public double getOffset(int stageNum) {
		double height = 0;

		for(int i = 0; i < Math.min(stageNum, stages.size() - 1); i++) {
			RocketStage stage = stages.get(i);
			if(stage.fuselage != null) height += stage.fuselage.height * stage.getStack();
			height += Math.max(stage.thruster != null ? stage.thruster.height : 0, i == 0 && stage.fins != null ? stage.fins.height : 0);
		}

		return height;
	}
	
	public void writeToByteBuffer(ByteBuf buf) {
		buf.writeInt(MissilePart.getId(capsule));
		
		buf.writeInt(stages.size());
		for(RocketStage stage : stages) {
			buf.writeInt(MissilePart.getId(stage.fuselage));
			buf.writeInt(MissilePart.getId(stage.fins));
			buf.writeInt(MissilePart.getId(stage.thruster));
			buf.writeByte(stage.fuselageCount);
			buf.writeByte(stage.thrusterCount);
		}
	}
	
	public static RocketStruct readFromByteBuffer(ByteBuf buf) {
		RocketStruct rocket = new RocketStruct();

		rocket.capsule = MissilePart.getPart(buf.readInt());

		int count = buf.readInt();
		for(int i = 0; i < count; i++) {
			RocketStage stage = new RocketStage();
			stage.fuselage = MissilePart.getPart(buf.readInt());
			stage.fins = MissilePart.getPart(buf.readInt());
			stage.thruster = MissilePart.getPart(buf.readInt());
			stage.fuselageCount = buf.readByte();
			stage.thrusterCount = buf.readByte();

			rocket.stages.add(stage);
		}
		
		return rocket;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("capsule", MissilePart.getId(capsule));

		NBTTagList stagesTag = new NBTTagList();
		for(RocketStage stage : stages) {
			NBTTagCompound stageTag = new NBTTagCompound();
			stageTag.setInteger("fuselage", MissilePart.getId(stage.fuselage));
			stageTag.setInteger("fins", MissilePart.getId(stage.fins));
			stageTag.setInteger("thruster", MissilePart.getId(stage.thruster));
			stageTag.setInteger("fc", stage.fuselageCount);
			stageTag.setInteger("tc", stage.thrusterCount);
			stagesTag.appendTag(stageTag);
		}
		nbt.setTag("stages", stagesTag);
	}

	public static RocketStruct readFromNBT(NBTTagCompound nbt) {
		RocketStruct rocket = new RocketStruct();
		rocket.capsule = MissilePart.getPart(nbt.getInteger("capsule"));

		NBTTagList stagesTag = nbt.getTagList("stages", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < stagesTag.tagCount(); i++) {
			NBTTagCompound stageTag = stagesTag.getCompoundTagAt(i);
			RocketStage stage = new RocketStage();
			stage.fuselage = MissilePart.getPart(stageTag.getInteger("fuselage"));
			stage.fins = MissilePart.getPart(stageTag.getInteger("fins"));
			stage.thruster = MissilePart.getPart(stageTag.getInteger("thruster"));
			stage.fuselageCount = Math.max(stageTag.getInteger("fc"), 1);
			stage.thrusterCount = Math.max(stageTag.getInteger("tc"), 1);
			rocket.stages.add(stage);
		}

		return rocket;
	}

	// Sets up a DataWatcher to accept serialization
	public static void setupDataWatcher(DataWatcher watcher, int start) {
		watcher.addObject(start, 0);
		watcher.addObject(start + 1, 0);
		for(int i = 0; i < MAX_STAGES; i++) {
			watcher.addObject(start + i * 2 + 2, 0);
			watcher.addObject(start + i * 2 + 3, 0);
		}
	}

	// Serializes into an entity DataWatcher
	public void writeToDataWatcher(DataWatcher watcher, int start) {
		watcher.updateObject(start, MissilePart.getId(capsule));
		watcher.updateObject(start + 1, stages.size());
		for(int i = 0; i < stages.size(); i++) {
			Pair<Integer, Integer> watchable = stages.get(i).zipWatchable();
			watcher.updateObject(start + i * 2 + 2, watchable.key);
			watcher.updateObject(start + i * 2 + 3, watchable.value);
		}
	}

	public static RocketStruct readFromDataWatcher(DataWatcher watcher, int start) {
		RocketStruct rocket = new RocketStruct();

		rocket.capsule = MissilePart.getPart(watcher.getWatchableObjectInt(start));

		int count = watcher.getWatchableObjectInt(start + 1);
		for(int i = 0; i < count; i++) {
			Pair<Integer, Integer> watchable = new Pair<Integer, Integer>(
				watcher.getWatchableObjectInt(start + i * 2 + 2),
				watcher.getWatchableObjectInt(start + i * 2 + 3));
			rocket.stages.add(RocketStage.unzipWatchable(watchable));
		}

		return rocket;
	}

	public static class RocketStage {

		public MissilePart fuselage;
		public MissilePart fins;
		public MissilePart thruster;

		public int fuselageCount = 1;
		public int thrusterCount = 1;

		// fucking datawatchers and their limit of 32 synced values
		// I'm a crafty bastard though and can utilise the fact our values are always positive shorts and bytes
		public Pair<Integer, Integer> zipWatchable() {
			int first = MissilePart.getId(fuselage) << 16 | MissilePart.getId(fins);
			int second = MissilePart.getId(thruster) << 16 | fuselageCount << 8 | thrusterCount;
			return new Pair<Integer, Integer>(first, second);
		}

		public static RocketStage unzipWatchable(Pair<Integer, Integer> pair) {
			RocketStage stage = new RocketStage();
			stage.fuselage = MissilePart.getPart(pair.key >> 16);
			stage.fins = MissilePart.getPart(pair.key & 0xFFFF);
			stage.thruster = MissilePart.getPart(pair.value >> 16);
			stage.fuselageCount = (pair.value >> 8) & 0xFF;
			stage.thrusterCount = pair.value & 0xFF;
			return stage;
		}

		public int getStack() {
			return Math.max(fuselageCount / thrusterCount, 1);
		}

		public int getCluster() {
			return Math.max(fuselageCount / getStack(), 1);
		}
		
	}

}
