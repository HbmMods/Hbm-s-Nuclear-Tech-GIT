package com.hbm.module;

public class ParseMSES1 implements IParse {

	@Override
	public EnumStatementReturn eval(ParseContext ctx, String line) {
		return EnumStatementReturn.OK;
	}
}
