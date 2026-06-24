package com.hbm.module;

import java.util.Locale;

import com.hbm.config.ServerConfig;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;
import com.hbm.util.Calculator;

public class ParseMSES1 implements IParse {

	@Override
	public EnumStatementReturn eval(ParseContext ctx, String line) {
		String lower = line.toLowerCase(Locale.US);
		
		// jump point destination or comment, skip
		if(line.isEmpty() || lower.startsWith("dest ") || lower.startsWith("# ")) {
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
				if(speed < 1 || speed > ServerConfig.AUTOCAL_MAX_CLOCK.get()) return EnumStatementReturn.PARAMETER_ERROR;
				ctx.clockSpeed = speed;
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.SKIP;
		}
		
		// sets the script index to the jump point
		if(lower.startsWith("jmp ")) {
			if(line.length() <= 4) return EnumStatementReturn.PARAMETER_ERROR;
			String jmpKey = substitute(ctx, line.substring(4), false);
			if(ctx.jmp.containsKey(jmpKey)) {
				ctx.current = ctx.jmp.get(jmpKey);
				return EnumStatementReturn.OK;
			}
			return EnumStatementReturn.PARAMETER_ERROR;
		}
		
		// sets the script index to the jump point, if the buffer is the string 'true'
		if(lower.startsWith("jmpif ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			if(!ctx.readBuffer().equals("true")) return EnumStatementReturn.OK;
			String jmpKey = substitute(ctx, line.substring(6), false);
			if(ctx.jmp.containsKey(jmpKey)) {
				ctx.current = ctx.jmp.get(jmpKey);
				return EnumStatementReturn.OK;
			}
			return EnumStatementReturn.PARAMETER_ERROR;
		}
		
		// sets the script index to the jump point, if the buffer is the NOT 'true'
		if(lower.startsWith("jmpnot ")) {
			if(line.length() <= 7) return EnumStatementReturn.PARAMETER_ERROR;
			if(ctx.readBuffer().equals("true")) return EnumStatementReturn.OK;
			String jmpKey = substitute(ctx, line.substring(7), false);
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
			ctx.writeBuffer(ctx.variables.getString(line.substring(5)));
			return EnumStatementReturn.OK;
		}
		
		// saves the buffer with the specified name
		if(lower.startsWith("save ")) {
			if(line.length() <= 5 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.variables.setString(line.substring(5), ctx.readBuffer());
			return EnumStatementReturn.OK;
		}
		
		// stores the specified value in the buffer
		if(lower.startsWith("buffer ")) {
			if(line.length() <= 7) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.writeBuffer(line.substring(7));
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation, allows string substitution, saves result to buffer
		if(lower.startsWith("eval ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, line.substring(5), true);
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.writeBuffer("" + result);
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation, allows string substitution, rounds, saves result to buffer,
		if(lower.startsWith("evalr ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, line.substring(6), true);
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.writeBuffer("" + (int) Math.round(result));
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// runs the calculation from the buffer, allows string substitution, saves result to buffer
		if(lower.equals("evalr")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			String statement = substitute(ctx, ctx.readBuffer(), true);
			try {
				double result = Calculator.evaluateExpression(statement);
				ctx.writeBuffer("" + (int) Math.round(result));
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// rounds the buffer down to the nearest integer
		if(lower.equals("rounddown") || lower.equals("floor")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.readBuffer());
				ctx.writeBuffer("" + (int) Math.floor(d));
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}

		// rounds the buffer up to the nearest integer
		if(lower.equals("roundup") || lower.equals("ceil")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.readBuffer());
				ctx.writeBuffer("" + (int) Math.ceil(d));
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}

		// rounds the buffer to the nearest integer (.5 cutoff rule)
		if(lower.equals("round") || lower.equals("nearest")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double d = Double.parseDouble(ctx.readBuffer());
				ctx.writeBuffer("" + (int) Math.round(d));
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// concatenate, same as buffer but evaluates $var$ substitutions
		if(lower.startsWith("concat ")) {
			if(line.length() <= 7 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			String concat = substitute(ctx, line.substring(7), false);
			ctx.writeBuffer(concat);
			return EnumStatementReturn.OK;
		}
		
		// compares the buffer with a value, allows substitutions
		if(lower.startsWith("eq ")) {
			if(line.length() <= 3 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			ctx.writeBuffer(ctx.readBuffer().equals(substitute(ctx, line.substring(3), false)) ? "true" : "false");
			return EnumStatementReturn.OK;
		}
		
		// greater than buffer
		if(lower.startsWith("gtb ")) {
			if(line.length() <= 4 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.readBuffer());
				double val =  Double.parseDouble(substitute(ctx, line.substring(4), false));
				ctx.writeBuffer(val > buffer ? "true" : "false");
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// lower than buffer
		if(lower.startsWith("ltb ")) {
			if(line.length() <= 4 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.readBuffer());
				double val =  Double.parseDouble(substitute(ctx, line.substring(4), false));
				ctx.writeBuffer(val < buffer ? "true" : "false");
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// greater than or equal buffer
		if(lower.startsWith("geb ")) {
			if(line.length() <= 4 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.readBuffer());
				double val =  Double.parseDouble(substitute(ctx, line.substring(4), false));
				ctx.writeBuffer(val >= buffer ? "true" : "false");
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// lower than or equal buffer
		if(lower.startsWith("leb ")) {
			if(line.length() <= 4 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				double buffer = Double.parseDouble(ctx.readBuffer());
				double val =  Double.parseDouble(substitute(ctx, line.substring(4), false));
				ctx.writeBuffer(val <= buffer ? "true" : "false");
			} catch(Exception ex) { return EnumStatementReturn.PARAMETER_ERROR; }
			return EnumStatementReturn.OK;
		}
		
		// sends an RoR signal using the buffer's contents as the message over the supplied channel
		if(lower.startsWith("send ")) {
			if(line.length() <= 5 || ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			RTTYSystem.broadcast(ctx.world, substitute(ctx, line.substring(5), false), ctx.readBuffer());
			return EnumStatementReturn.OK;
		}
		
		// listens to an RoR signal using the supplied channel name and saves it to the buffer
		if(lower.startsWith("listen ")) {
			if(line.length() <= 7) return EnumStatementReturn.PARAMETER_ERROR;
			RTTYChannel chan = RTTYSystem.listen(ctx.world, substitute(ctx, line.substring(7), false));
			if(chan != null) ctx.writeBuffer(chan.signal + "");
			return EnumStatementReturn.OK;
		}
		
		return EnumStatementReturn.UNRECOGNIZED_COMMAND;
	}
	
	public String substitute(ParseContext ctx, String statement, boolean forceNumber) {
		if(!statement.contains("$")) return statement;
		
		StringBuilder joined = new StringBuilder("");
		StringBuilder var = new StringBuilder("");
		boolean readingVar = false;
		
		for(char c : statement.toCharArray()) {
			
			if(c == '$') {
				if(!readingVar) {
					readingVar = true;
				} else {
					if("buffer".equals(var)) {
						joined.append(ctx.readBuffer());
					} else {
						String variable = ctx.variables.getString(var.toString());
						if(forceNumber && variable.isEmpty()) variable = "0";
						joined.append(variable);
						var.delete(0, var.length());
						readingVar = false;
					}
				}
			} else {
				if(readingVar) {
					var.append(c);
				} else {
					joined.append(c);
				}
			}
		}
		
		return joined.toString();
	}

	@Override
	public void generateJumpPoints(ParseContext ctx, String line, int index) {
		if(line.startsWith("dest ") && line.length() > 5) {
			ctx.jmp.put(line.substring(5), index);
		}
	}
}
