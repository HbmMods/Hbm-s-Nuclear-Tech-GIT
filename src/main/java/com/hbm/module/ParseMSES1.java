package com.hbm.module;

import java.util.Locale;

import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.tileentity.network.TileEntityRadioAUTOCAL;
import com.hbm.util.Calculator;

public class ParseMSES1 implements IParse {

	@Override
	public EnumStatementReturn eval(ParseContext ctx, String line) {
		String lower = line.toLowerCase(Locale.US);
		
		// jump point destination, skip
		if(lower.startsWith("dest ") || lower.startsWith("# ")) {
			return EnumStatementReturn.SKIP;
		}
		
		// no operation, still eats up a clock cycle
		if(lower.equals("nop")) {
			return EnumStatementReturn.OK;
		}
		
		// sets the desired clock speed, then skips the operation
		if(lower.startsWith("clockspeed ")) {
			if(line.length() <= 11) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				int speed = Integer.parseInt(line.substring(11));
				if(speed < 1 || speed > TileEntityRadioAUTOCAL.MAX_CLOCK_SPEED) return EnumStatementReturn.PARAMETER_ERROR;
				ctx.clockSpeed = speed;
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.SKIP;
		}
		
		// sets the script index to the jump point
		if(lower.startsWith("jmp ")) {
			if(line.length() <= 4) return EnumStatementReturn.PARAMETER_ERROR;
			String jmpKey = line.substring(4);
			if(ctx.jmp.containsKey(jmpKey)) {
				ctx.current = ctx.jmp.get(jmpKey);
				return EnumStatementReturn.OK;
			}
			return EnumStatementReturn.PARAMETER_ERROR;
		}
		
		// sets the script index to the jump point, if the buffer is the string 'true'
		if(lower.startsWith("jmpif ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			if(!ctx.buffer.equals("true")) return EnumStatementReturn.OK;
			String jmpKey = line.substring(6);
			if(ctx.jmp.containsKey(jmpKey)) {
				ctx.current = ctx.jmp.get(jmpKey);
				return EnumStatementReturn.OK;
			}
			return EnumStatementReturn.PARAMETER_ERROR;
		}
		
		// sets the script index to the jump point, if the buffer is the NOT 'true'
		if(lower.startsWith("jmpnot ")) {
			if(line.length() <= 7) return EnumStatementReturn.PARAMETER_ERROR;
			if(ctx.buffer.equals("true")) return EnumStatementReturn.OK;
			String jmpKey = line.substring(7);
			if(ctx.jmp.containsKey(jmpKey)) {
				ctx.current = ctx.jmp.get(jmpKey);
				return EnumStatementReturn.OK;
			}
			return EnumStatementReturn.PARAMETER_ERROR;
		}
		
		// ends the tick regardless of remaining clock cycles
		if(lower.equals("endtick")) {
			return EnumStatementReturn.END_TICK;
		}
		
		// requests unit to shut down
		if(lower.equals("shutdown")) {
			return EnumStatementReturn.SHUTDOWN;
		}
		
		// loads the requested variable into the buffer
		if(lower.startsWith("load ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.buffer = ctx.variables.getString(line.substring(5));
			return EnumStatementReturn.OK;
		}
		
		// saves the buffer with the specified name
		if(lower.startsWith("save ")) {
			if(line.length() <= 5 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.variables.setString(line.substring(5), ctx.buffer);
			return EnumStatementReturn.OK;
		}
		
		// stores the specified value in the buffer
		if(lower.startsWith("buffer ")) {
			if(line.length() <= 7) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.buffer = line.substring(7);
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation, allows string substitution, saves result to buffer
		if(lower.startsWith("eval ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, line.substring(5));
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.buffer = "" + result;
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation, allows string substitution, rounds, saves result to buffer,
		if(lower.startsWith("evalr ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, line.substring(6));
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.buffer = "" + (int) Math.round(result);
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation from the buffer, allows string substitution, saves result to buffer
		if(lower.equals("evalr")) {
			if(ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, ctx.buffer);
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.buffer = "" + (int) Math.round(result);
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// rounds the buffer down to the nearest integer
		if(lower.equals("rounddown") || lower.equals("floor")) {
			if(ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.buffer);
				ctx.buffer = "" + (int) Math.floor(d);
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}

		// rounds the buffer up to the nearest integer
		if(lower.equals("roundup") || lower.equals("ceil")) {
			if(ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.buffer);
				ctx.buffer = "" + (int) Math.ceil(d);
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}

		// rounds the buffer to the nearest integer (.5 cutoff rule)
		if(lower.equals("round") || lower.equals("nearest")) {
			if(ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.buffer);
				ctx.buffer = "" + (int) Math.round(d);
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// concatenate, same as buffer but evaluates $var$ substitutions
		if(lower.startsWith("concat ")) {
			if(line.length() <= 7 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			String concat = substitute(ctx, line.substring(5));
			ctx.buffer = concat;
			return EnumStatementReturn.OK;
		}
		
		// compares the buffer with a value, allows substitutions
		if(lower.startsWith("eq ")) {
			if(line.length() <= 3 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.buffer = ctx.buffer.equals(substitute(ctx, line.substring(3))) ? "true" : "false";
			return EnumStatementReturn.OK;
		}
		
		// greater than buffer
		if(lower.startsWith("gtb ")) {
			if(line.length() <= 3 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.buffer);
				double val =  Double.parseDouble(line.substring(3));
				ctx.buffer = val > buffer ? "true" : "false";
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// lower than buffer
		if(lower.startsWith("ltb ")) {
			if(line.length() <= 3 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.buffer);
				double val =  Double.parseDouble(line.substring(3));
				ctx.buffer = val < buffer ? "true" : "false";
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// greater than or equal buffer
		if(lower.startsWith("geb ")) {
			if(line.length() <= 3 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.buffer);
				double val =  Double.parseDouble(line.substring(3));
				ctx.buffer = val >= buffer ? "true" : "false";
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// lower than or equal buffer
		if(lower.startsWith("leb ")) {
			if(line.length() <= 3 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.buffer);
				double val =  Double.parseDouble(line.substring(3));
				ctx.buffer = val <= buffer ? "true" : "false";
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// sends an RoR signal using the buffer's contents as the message over the supplied channel
		if(lower.startsWith("send ")) {
			if(line.length() <= 5 || ctx.buffer.isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			RTTYSystem.broadcast(ctx.world, line.substring(5), ctx.buffer);
			return EnumStatementReturn.OK;
		}
		
		// listnes to an RoR signal using the buffer as the channel, then saves the signal to the buffer
		if(lower.equals("listen")) {
			// this picks up expired signals too, so polling has to be implemented by the user
			RTTYChannel chan = RTTYSystem.listen(ctx.world, ctx.buffer);
			if(chan != null) ctx.buffer = chan.signal + "";
			return EnumStatementReturn.OK;
		}
		
		return EnumStatementReturn.UNRECOGNIZED_COMMAND;
	}
	
	public String substitute(ParseContext ctx, String statement) {
		if(!statement.contains("$")) return statement;
		
		String[] frags = statement.split("\\$");
		if(frags.length % 2 == 0 || frags.length < 3) return statement; 
		
		// since var names are enclosed with $ signs, we assume that every evenly numbered fragment is a var name
		// example: 5 + $val1$ * $val2$ / (-$val3$)
		// equals "5 + ", "val1", "* ", "val2", "/ (-", "val3", ")"
		//         1       2       3     4       5       6       7
		for(int i = 1; i < frags.length; i += 2) {
			// special case, if we try to substitute $buffer$ then read the literal buffer
			if(frags[i].equals("buffer")) {
				frags[i] = ctx.buffer;
			} else {
				frags[i] = ctx.variables.getString(frags[i]);
			}
		}
		
		return String.join("", frags);
	}

	@Override
	public void generateJumpPoints(ParseContext ctx, String line, int index) {
		if(line.startsWith("dest ") && line.length() > 5) {
			ctx.jmp.put(line.substring(5), index);
		}
	}
}
