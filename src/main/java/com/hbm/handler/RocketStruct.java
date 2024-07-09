package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.render.util.MissilePart;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class RocketStruct {
	
	public MissilePart capsule;
	public ArrayList<RocketStage> stages = new ArrayList<>();

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

		RocketStage stage = stages.get(Math.min(stageNum, stages.size() - 1));
		if(stage.fuselage != null) height += stage.fuselage.height;
		if(stage.thruster != null) height += stage.thruster.height;

		if(stageNum == stages.size() - 1) {
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

	public static class RocketStage {

		public MissilePart fuselage;
		public MissilePart fins;
		public MissilePart thruster;
		
	}

}
