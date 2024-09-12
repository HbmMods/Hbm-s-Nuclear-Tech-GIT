package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Rocket extends FluidTrait {

	/**
	 * this is only for the transporter pads, and probably won't remain forever
	 * well now it's also used for station engines, but just the ISP value
	 * 
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
	
	// The ISP of the fuel (aka how long it can go)
	private int isp;

	//The thrust of the fuel (aka how much it can carry)
	private long thrust;
	
	public FT_Rocket(int isp, long twr) {
		this.isp = isp;
		this.thrust = twr;
	}
	
	public int getISP() {
		return this.isp;
	}

	public long getThrust() {
		return this.thrust;
	}

	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		info.add(EnumChatFormatting.LIGHT_PURPLE + "[Rocket Grade]");
		
		if(isp > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(isp) + " ISP " + EnumChatFormatting.YELLOW + "per bucket");
		
		info.add(EnumChatFormatting.RED + "[Thrust power]");

		if(thrust > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(thrust) + " N " + EnumChatFormatting.YELLOW + "of thrust per bucket");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("isp").value(isp);
		writer.name("thrust").value(thrust);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.isp = obj.get("isp").getAsInt();
		this.thrust = obj.get("thrust").getAsLong();
	}

}
