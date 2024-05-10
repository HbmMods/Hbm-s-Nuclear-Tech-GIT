package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Rocket extends FluidTrait {

	/**
	 * A rockets effectiveness and efficiency is defined within two flight regimes:
	 * When escaping gravity and entering orbit (To Orbit)
	 * Whilst in "free-fall", navigating to celestial bodies (Transfer)
	 * 
	 * To Orbit costs include gravity losses, and factor in the rocket TWR
	 * Transfer costs only include losses incurred to apply dV, assuming optimal bi-elliptic transfers
	 * 
	 * Balancing a rocket requires having sufficiently high TWR to minimise gravity loss (spending dV that doesn't become orbital energy)
	 * Whilst also having high enough efficiency such that dV per unit of fuel is very high
	 */
	
	/** The ISP of the fuel (aka how long it can go) */
	private long isp;
	/**
	 * The thrust *multiplier* of the fuel (aka how much it can carry)
	 * Thrust itself is a function of nozzle geometry+count multiplied by this
	 * Thrust to weight ratio (TWR) must exceed 1 for a rocket to launch
	 * TWR between 1 - 1.5 incurs an effectiveness debuff (due to gravity losses)
	 */
	private long twrMultiplier;
	
	public FT_Rocket(long isp, long twr) {
		this.isp = isp;
		this.twrMultiplier = twr;

	}
	
	public long getISP() {
		return this.isp;
	}
	public long getThrust() {
		return this.twrMultiplier;
	}

	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		info.add(EnumChatFormatting.LIGHT_PURPLE + "[Rocket Grade]");
		
		if(isp > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(isp) + " ISP " + EnumChatFormatting.YELLOW + "per bucket");
		
		info.add(EnumChatFormatting.RED + "[Thrust power]");

		if(twrMultiplier > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(twrMultiplier) + " N " + EnumChatFormatting.YELLOW + "of thrust per bucket");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("isp").value(isp);
		writer.name("thrust").value(twrMultiplier);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.isp = obj.get("energy").getAsLong();
		this.twrMultiplier = obj.get("v").getAsLong();

	}
}
