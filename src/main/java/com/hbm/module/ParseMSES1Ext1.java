package com.hbm.module;

import java.util.Locale;
import java.util.regex.Pattern;

import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

/**
 * MSES1-FEIS: Machine Script, Equestrian Standard - First Extended Instruction Set (v1.1)
 * 
 * Additions compared to v1 Standard:
 * * Support for splitter chars, splitting and split count
 * * Support for the stack, a secondary buffer that can push, pop and peek
 * * Support for getting string length and substring using first and last
 * * Support for listening only to recent RoR signals using poll
 * 
 * @author hbm
 */
public class ParseMSES1Ext1 extends ParseMSES1 {

	@Override
	public EnumStatementReturn eval(ParseContext ctx, String line) {
		String lower = line.toLowerCase(Locale.US);
		
		// sets the splitter char
		if(lower.startsWith("splitter ")) {
			if(line.length() <= 9) return EnumStatementReturn.PARAMETER_ERROR;
			String splitter = substitute(ctx, line.substring(9), false);
			ctx.splitString = splitter;
			return EnumStatementReturn.OK;
		}
		
		// grabs a fragment based on index
		if(lower.startsWith("split ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				int index = Integer.parseInt(line.substring(6));
				if(index < 1) return EnumStatementReturn.PARAMETER_ERROR;
				String[] frags = ctx.readBuffer().split(Pattern.quote(ctx.splitString));
				if(index > frags.length) return EnumStatementReturn.PARAMETER_ERROR;
				ctx.writeBuffer(frags[index - 1]);
				return EnumStatementReturn.OK;
			} catch(Throwable ex) { return EnumStatementReturn.PARAMETER_ERROR; }
		}
		
		// counts the amount of fragments in this string
		if(lower.equals("splitcount")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			String[] frags = ctx.readBuffer().split(Pattern.quote(ctx.splitString));
			ctx.writeBuffer(frags.length + "");
			return EnumStatementReturn.OK;
		}
		
		// pushes the buffer to stack
		if(lower.equals("push")) {
			if(ctx.readBuffer().isEmpty()) return EnumStatementReturn.PARAMETER_ERROR;
			boolean succ = ctx.push(ctx.readBuffer());
			return succ ? EnumStatementReturn.OK : EnumStatementReturn.STACK_EXCEEDED;
		}
		
		// pushes the supplied value (or variable) to stack
		if(lower.startsWith("push ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			boolean succ = ctx.push(substitute(ctx, line.substring(5), false));
			return succ ? EnumStatementReturn.OK : EnumStatementReturn.STACK_EXCEEDED;
		}
		
		// removes the most recent element from stack and writes it to the buffer
		if(lower.equals("pop")) {
			String val = ctx.pop();
			if(val == null) return EnumStatementReturn.UNDEFINED;
			ctx.writeBuffer(val);
			return EnumStatementReturn.OK;
		}
		
		// writes the most recent element to the buffer
		if(lower.equals("peek")) {
			String val = ctx.peek();
			if(val == null) return EnumStatementReturn.UNDEFINED;
			ctx.writeBuffer(val);
			return EnumStatementReturn.OK;
		}
		
		// figures out buffer string's length
		if(lower.equals("length")) {
			ctx.writeBuffer("" + ctx.readBuffer().length());
			return EnumStatementReturn.OK;
		}
		
		// grabs the first x characters from the buffer and writes them back to the buffer
		if(lower.startsWith("first ")) {
			if(line.length() <= 6) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				int length = Integer.parseInt(line.substring(6));
				int max = ctx.readBuffer().length();
				if(length > max) length = max;
				ctx.writeBuffer(ctx.readBuffer().substring(0, length));
				return EnumStatementReturn.OK;
			} catch(Exception x) { return EnumStatementReturn.PARAMETER_ERROR; }
		}

		// grabs the last x characters from the buffer and writes them back to the buffer
		if(lower.startsWith("last ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			try {
				int length = Integer.parseInt(line.substring(5));
				int max = ctx.readBuffer().length();
				if(length > max) length = max;
				ctx.writeBuffer(ctx.readBuffer().substring(max - length, max));
				return EnumStatementReturn.OK;
			} catch(Exception x) { return EnumStatementReturn.PARAMETER_ERROR; }
		}
		
		// listens to an RoR signal using the supplied channel name and saves it to the buffer
		if(lower.startsWith("poll ")) {
			if(line.length() <= 5) return EnumStatementReturn.PARAMETER_ERROR;
			RTTYChannel chan = RTTYSystem.listen(ctx.world, substitute(ctx, line.substring(5), false));
			if(chan != null && chan.timeStamp >= ctx.world.getTotalWorldTime() - 1) ctx.writeBuffer(chan.signal + "");
			return EnumStatementReturn.OK;
		}
		
		// writes the total world time to the buffer
		if(lower.equals("worldtime")) {
			ctx.writeBuffer("" + ctx.world.getTotalWorldTime());
			return EnumStatementReturn.OK;
		}
		
		return super.eval(ctx, line);
	}
}
