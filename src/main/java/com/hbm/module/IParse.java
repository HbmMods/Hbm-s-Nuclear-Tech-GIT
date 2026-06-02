package com.hbm.module;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IParse {

	public EnumStatementReturn eval(ParseContext ctx, String line);
	public void generateJumpPoints(ParseContext ctx, String line, int index);
	
	public static class ParseContext {
		public World world;
		public NBTTagCompound variables = new NBTTagCompound();
		public HashMap<String, Integer> jmp = new HashMap();

		public String buffer = "";
		public int clockSpeed = 1;
		public int current = 0;
		
		public ParseContext(World world) {
			this.world = world;
		}
		
		public void turnOff() {
			this.clockSpeed = 1;
			this.current = 0;
			this.buffer = "";
			if(!this.variables.hasNoTags()) this.variables = new NBTTagCompound();
		}
	}
	
	public static enum EnumStatementReturn {
		/** The command executed correctly (more or less) */
		OK,
		/** The command hasn't been recognized */
		UNRECOGNIZED_COMMAND,
		/** The expected parameters aren't present, or the parameters couldn't be parsed (i.e. using an undefined jump point) */
		PARAMETER_ERROR,
		/** Requests the AUTOCAL unit to end the tick, regardless of how many clock cycles are left */
		END_TICK,
		/** Requests an AUTOCAL shutdown */
		SHUTDOWN,
		/** Skips the instruction, doesn't use up a clock cycle */
		SKIP,
		/** General undefined behavior */
		UNDEFINED
	}
}
