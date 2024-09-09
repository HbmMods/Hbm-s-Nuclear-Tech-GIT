package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart.FuelType;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;
import com.hbm.render.util.MissilePart;
import com.hbm.util.BufferUtil;
import com.hbm.util.Tuple.Pair;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants;

public class RocketStruct {
	
	public MissilePart capsule;
	public ArrayList<RocketStage> stages = new ArrayList<>();
	public int satFreq = 0;

	public List<String> extraIssues = new ArrayList<>();

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
		if(extraIssues.size() > 0)
			return false;

		if(capsule == null || capsule.type != PartType.WARHEAD)
			return false;

		if(capsule.part.attributes[0] != WarheadType.APOLLO && capsule.part.attributes[0] != WarheadType.SATELLITE)
			return false;
		
		if(stages.size() == 0)
			return false;

		for(RocketStage stage : stages) {
			if(stage.fuselage == null || stage.fuselage.type != PartType.FUSELAGE) return false;
			if(stage.fins != null && stage.fins.type != PartType.FINS) return false;
			if(stage.thruster == null || stage.thruster.type != PartType.THRUSTER) return false;

			if(stage.thrusterCount > stage.fuselageCount || stage.fuselageCount % stage.thrusterCount != 0) return false;

			if(stage.fuselage.part.attributes[0] != FuelType.ANY && stage.fuselage.part.attributes[0] != stage.thruster.part.attributes[0]) return false;
		}
		
		return true;
	}

	public void addIssue(String issue) {
		extraIssues.add(issue);
	}

	// Lists any validation issues so the player can rectify easily
	public List<String> findIssues(int stageNum, CelestialBody from, CelestialBody to, boolean fromOrbit, boolean toOrbit) {
		List<String> issues = new ArrayList<String>();

		// If we have no parts, we have no worries
		if(capsule == null && stages.size() == 0) return issues;

		if(capsule == null || (capsule.part.attributes[0] != WarheadType.APOLLO && capsule.part.attributes[0] != WarheadType.SATELLITE))
			issues.add(EnumChatFormatting.RED + "Invalid Capsule/Satellite");

		// Current stage stats
		if(stageNum < stages.size()) {
			RocketStage stage = stages.get(stageNum);
			issues.add("Dry mass: " + getLaunchMass(stageNum) + "kg");
			issues.add("Wet mass: " + getWetMass(stageNum) + "kg");
			if(stage.thruster != null) {
				issues.add("Thrust: " + getThrust(stage) + "N");
				issues.add("ISP: " + getISP(stage) + "s");
			}
		}

		for(int i = 0; i < stages.size(); i++) {
			RocketStage stage = stages.get(i);
			if(stage.fuselage == null)
				issues.add(EnumChatFormatting.RED + "Stage " + (i + 1) + " missing fuselage");
			if(stage.thruster == null)
				issues.add(EnumChatFormatting.RED + "Stage " + (i + 1) + " missing thruster");
			
			if(stage.fuselage == null || stage.thruster == null)
				continue;

			if(stage.thrusterCount > stage.fuselageCount)
				issues.add(EnumChatFormatting.RED + "Stage " + (i + 1) + " too many thrusters");
			if(stage.fuselageCount % stage.thrusterCount != 0)
				issues.add(EnumChatFormatting.RED + "Stage " + (i + 1) + " uneven thrusters");

			if(stage.fuselage.part.attributes[0] != FuelType.ANY && stage.fuselage.part.attributes[0] != stage.thruster.part.attributes[0])
				issues.add(EnumChatFormatting.RED + "Stage " + (i + 1) + " fuel mismatch");

			if(i > 0 && stage.fins == null)
				issues.add(EnumChatFormatting.YELLOW + "Stage " + (i + 1) + " lacks landing legs");

			// I was gonna add all sorts of realistic restrictions but then realised
			// KSP lets you shit any part onto any part, and that's fun
			// so who am I to kill your creative spirit
			// put that ant engine on your rhino fuselage
		}

		if(from != null && to != null) {
			int fuelRequirement = getFuelRequired(stageNum, from, to, fromOrbit, toOrbit);
			int fuelCapacity = getFuelCapacity(stageNum);

			if(fuelRequirement == Integer.MAX_VALUE) {
				issues.add(EnumChatFormatting.YELLOW + "Insufficient thrust");
			} else if(fuelCapacity < fuelRequirement) {
				issues.add(EnumChatFormatting.YELLOW + "Insufficient fuel: " + fuelCapacity + "/" + fuelRequirement + "mB");
			} else if(fuelCapacity > 0 && fuelRequirement > 0) {
				issues.add(EnumChatFormatting.GREEN + "Trip possible! " + fuelCapacity + "/" + fuelRequirement + "mB");
			}
		}

		for(String issue : extraIssues) issues.add(issue);

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

	public boolean hasSufficientFuel(CelestialBody from, CelestialBody to, boolean fromOrbit, boolean toOrbit) {
		if(capsule.part == ModItems.rp_pod_20) {
			return from == to && (fromOrbit || toOrbit); // Pods can transfer, fall to orbited body, and return to station, but NOT hop on the surface
		}

		if(stages.size() == 0) {
			return from == to && fromOrbit && !toOrbit; // Capsules can return to orbited body from orbit only
		}

		int fuelRequirement = getFuelRequired(0, from, to, fromOrbit, toOrbit);
		int fuelCapacity = getFuelCapacity(0);

		return fuelCapacity >= fuelRequirement;
	}

	private int getFuelCapacity(int stageNum) {
		if(stageNum >= stages.size()) return -1;

		RocketStage stage = stages.get(stageNum);

		if(stage.fuselage == null) return -1;

		return stage.fuselage.part.getTankSize() * stage.fuselageCount;
	}

	private int getFuelRequired(int stageNum, CelestialBody from, CelestialBody to, boolean fromOrbit, boolean toOrbit) {
		if(stageNum >= stages.size()) return -1;

		RocketStage stage = stages.get(stageNum);

		if(stage.fuselage == null || stage.thruster == null) return -1;
		
		int rocketMass = getLaunchMass(stageNum);
		int thrust = getThrust(stage);
		int isp = getISP(stage);

		return SolarSystem.getCostBetween(from, to, rocketMass, thrust, isp, fromOrbit, toOrbit);
	}

	private int getThrust(RocketStage stage) {
		return stage.thruster.part.getThrust() * stage.thrusterCount;
	}
	
	private int getISP(RocketStage stage) {
		return stage.thruster.part.getISP();
	}

	// Gets the dry mass of the active stage + the wet mass of the stages above it
	public int getLaunchMass() {
		return getMass(0, false);
	}

	// Gets the dry mass of the selected stage + the wet mass of the stages above it
	public int getLaunchMass(int stageNum) {
		return getMass(stageNum, false);
	}

	public int getWetMass(int stageNum) {
		return getMass(stageNum, true);
	}

	private int getMass(int stageNum, boolean wet) {
		int mass = 0;

		if(capsule != null) mass += capsule.part.mass;

		for(int i = stageNum; i < stages.size(); i++) {
			RocketStage stage = stages.get(i);
			if(stage.fuselage != null) mass += stage.fuselage.part.mass * stage.fuselageCount;
			if(stage.thruster != null) mass += stage.thruster.part.mass * stage.thrusterCount;

			if(stage.fuselage != null && (i > stageNum || wet)) {
				mass += stage.fuselage.part.getTankSize() * stage.fuselageCount / 4; // Reduced fuel weight to improve multi-stages
			}
		}

		return MathHelper.ceiling_float_int(mass);
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

		buf.writeInt(extraIssues.size());
		for(String issue : extraIssues) {
			BufferUtil.writeString(buf, issue);
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

		count = buf.readInt();
		for(int i = 0; i < count; i++) {
			rocket.extraIssues.add(BufferUtil.readString(buf));
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

		nbt.setInteger("freq", satFreq);
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
