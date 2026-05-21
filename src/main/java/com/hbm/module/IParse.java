package com.hbm.module;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IParse {

	public EnumStatementReturn eval(ParseContext ctx, String line);
	
	public static class ParseContext {
		public World world;
		public NBTTagCompound variables;
		public HashMap<String, Integer> jmp;

		public Object buffer;
		public int clockSpeed;
		
		public ParseContext(World world, NBTTagCompound variables, HashMap<String, Integer> jmp) {
			this.world = world;
			this.variables = variables;
			this.jmp = jmp;
		}
	}
	
	public static enum EnumStatementReturn {
		OK,
		UNRECOGNIZED_COMMAND,
		PARAMETER_ERROR,
		END_TICK,
		UNDEFINED
	}
}
