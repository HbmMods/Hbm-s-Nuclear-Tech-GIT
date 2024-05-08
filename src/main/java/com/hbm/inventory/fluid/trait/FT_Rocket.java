package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Rocket extends FluidTrait {
	
	/** The ISP of the fuel (aka how long it can go) */
	private long isp;
	/**The thrust power of the fuel, (aka how much it can carry) */
	private long twpwr;

	public FT_Rocket() { }
	
	public FT_Rocket(long isp, long twpwr) {
		this.isp = isp;
		this.twpwr = twpwr;

	}
	
	public long getISP() {
		return this.isp;
	}
	public long getThrust() {
		return this.twpwr;
	}
	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		info.add(EnumChatFormatting.LIGHT_PURPLE + "[Rocket Grade]");
		
		if(isp > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(isp) + " ISP " + EnumChatFormatting.YELLOW + "per bucket");
		
		info.add(EnumChatFormatting.RED + "[Thrust power]");

		if(twpwr > 0)
			info.add(EnumChatFormatting.YELLOW + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber(twpwr) + " N " + EnumChatFormatting.YELLOW + "of thrust per bucket");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("isp").value(isp);
		writer.name("thrust").value(twpwr);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.isp = obj.get("energy").getAsLong();
		this.twpwr = obj.get("v").getAsLong();

	}
}
