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

		public static final int MAX_BUFFER_LENGTH = 256;
		public static final int MAX_STACK_SIZE = 256;

		private String buffer = "";
		private String[] stack = new String[MAX_STACK_SIZE];
		private int stackSize = 0;
		public String splitString = ";";
		public int clockSpeed = 1;
		public int current = 0;
		
		public ParseContext(World world) {
			this.world = world;
			
			for(int i = 0; i < MAX_STACK_SIZE; i++) stack[i] = "";
		}
		
		public String readBuffer() {
			return this.buffer;
		}
		
		/** Sets the buffer and imposes length restrictions. Returns true if successful, and false if truncation has taken place. */
		public boolean writeBuffer(String buffer) {

			if(buffer.length() > MAX_BUFFER_LENGTH) {
				this.buffer = buffer.substring(0, MAX_BUFFER_LENGTH);
				return false;
			}
			
			this.buffer = buffer;
			return true;
		}
		
		public boolean push(String line) {
			if(stackSize >= MAX_STACK_SIZE) return false;
			if(line.length() > MAX_BUFFER_LENGTH) line = line.substring(0, MAX_BUFFER_LENGTH);
			stack[stackSize] = line;
			stackSize++;
			return true;
		}
		
		public String pop() {
			if(stackSize <= 0) return null;
			if(stackSize > MAX_STACK_SIZE) stackSize = MAX_STACK_SIZE;
			String ret = stack[stackSize - 1];
			stack[stackSize - 1] = "";
			stackSize--;
			return ret;
		}
		
		public String peek() {
			if(stackSize <= 0) return null;
			if(stackSize > MAX_STACK_SIZE) stackSize = MAX_STACK_SIZE;
			return stack[stackSize - 1];
		}
		
		public void turnOff() {
			this.clockSpeed = 1;
			this.current = 0;
			this.buffer = "";
			if(!this.variables.hasNoTags()) this.variables = new NBTTagCompound();
		}
		
		// NBT R/W is now in control of the CTX, meaning additions to the AUTOCAL runtime should be a lot easier
		public void readFromNBT(NBTTagCompound nbt, String[] script, IParse parser) {
			current = nbt.getInteger("current");
			clockSpeed = nbt.getInteger("clockSpeed");
			buffer = nbt.getString("buffer");
			splitString = nbt.getString("splitString");
			variables = nbt.getCompoundTag("variables");
			
			stackSize = nbt.getInteger("stackSize");
			for(int i = 0; i < MAX_STACK_SIZE; i++) stack[i] = nbt.getString("st" + i);
			for(int i = 0; i < script.length; i++) parser.generateJumpPoints(this, script[i], i);
		}
		
		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setInteger("current", current);
			nbt.setInteger("clockSpeed", clockSpeed);
			nbt.setString("buffer", buffer);
			nbt.setString("splitString", splitString);
			nbt.setTag("variables", variables);
			
			nbt.setInteger("stackSize", stackSize);
			for(int i = 0; i < MAX_STACK_SIZE; i++) nbt.setString("st" + i, stack[i]);
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
		UNDEFINED,
		/** Stack ran full */
		STACK_EXCEEDED
	}
}
