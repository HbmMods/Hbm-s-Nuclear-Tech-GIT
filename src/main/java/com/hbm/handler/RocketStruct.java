package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;
import com.hbm.render.util.MissilePart;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		addStage(MissilePart.getPart(fuselage), MissilePart.getPart(fins), MissilePart.getPart(thruster));
	}

	public void addStage(MissilePart fuselage, MissilePart fins, MissilePart thruster) {
		RocketStage stage = new RocketStage();
		stage.fuselage = fuselage;
		stage.fins = fins;
		stage.thruster = thruster;
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
		}
		
		return true;
	}

	public int getMass() {
		return 2000;
	}

	public double getHeight() {
		double height = 0;
		
		if(capsule != null) height += capsule.height;

		for(RocketStage stage : stages) {
			if(stage.fuselage != null) height += stage.fuselage.height;
			if(stage.thruster != null) height += stage.thruster.height;
		}

		return height;
	}

	public double getHeight(int stageNum) {
		double height = 0;

		if(stages.size() > 0) {
			RocketStage stage = stages.get(Math.min(stageNum, stages.size() - 1));
			if(stage.fuselage != null) height += stage.fuselage.height;
			if(stage.thruster != null) height += stage.thruster.height;
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
			if(stage.fuselage != null) height += stage.fuselage.height;
			if(stage.thruster != null) height += stage.thruster.height;
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
			rocket.stages.add(stage);
		}

		return rocket;
	}

	// Sets up a DataWatcher to accept serialization
	public static void setupDataWatcher(DataWatcher watcher, int start) {
		watcher.addObject(start, 0);
		watcher.addObject(start + 1, 0);
		for(int i = 0; i < MAX_STAGES; i++) {
			watcher.addObject(start + i * 3 + 2, 0);
			watcher.addObject(start + i * 3 + 3, 0);
			watcher.addObject(start + i * 3 + 4, 0);
		}
	}

	// Serializes into an entity DataWatcher
	public void writeToDataWatcher(DataWatcher watcher, int start) {
		watcher.updateObject(start, MissilePart.getId(capsule));
		watcher.updateObject(start + 1, stages.size());
		for(int i = 0; i < stages.size(); i++) {
			RocketStage stage = stages.get(i);
			watcher.updateObject(start + i * 3 + 2, MissilePart.getId(stage.fuselage));
			watcher.updateObject(start + i * 3 + 3, MissilePart.getId(stage.fins));
			watcher.updateObject(start + i * 3 + 4, MissilePart.getId(stage.thruster));
		}
	}

	public static RocketStruct readFromDataWatcher(DataWatcher watcher, int start) {
		RocketStruct rocket = new RocketStruct();

		rocket.capsule = MissilePart.getPart(watcher.getWatchableObjectInt(start));

		int count = watcher.getWatchableObjectInt(start + 1);
		for(int i = 0; i < count; i++) {
			RocketStage stage = new RocketStage();
			stage.fuselage = MissilePart.getPart(watcher.getWatchableObjectInt(start + i * 3 + 2));
			stage.fins = MissilePart.getPart(watcher.getWatchableObjectInt(start + i * 3 + 3));
			stage.thruster = MissilePart.getPart(watcher.getWatchableObjectInt(start + i * 3 + 4));
			rocket.stages.add(stage);
		}

		return rocket;
	}

	public static class RocketStage {

		public MissilePart fuselage;
		public MissilePart fins;
		public MissilePart thruster;
		
	}

}
