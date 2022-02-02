package com.hbm.inventory.fluid;

import java.util.List;

import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.BobMathUtil;

import net.minecraft.util.EnumChatFormatting;

/** Because updating all the combustion engines and adding values by hand fucking sucks */
public class FluidTypeCombustible extends FluidTypeFlammable {
	
	protected FuelGrade fuelGrade;
	protected double combustionEnergy;
	
	public FluidTypeCombustible(String compat, int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name) {
		this(compat, color, x, y, sheet, p, f, r, symbol, name, 0, new FluidTrait[0]);
	}
	
	public FluidTypeCombustible(String compat, int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, FluidTrait... traits) {
		this(compat, color, x, y, sheet, p, f, r, symbol, name, 0, traits);
	}
	
	public FluidTypeCombustible(String compat, int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, int temperature) {
		this(compat, color, x, y, sheet, p, f, r, symbol, name, temperature, new FluidTrait[0]);
	}
	
	public FluidTypeCombustible(String compat, int color, int x, int y, int sheet, int p, int f, int r, EnumSymbol symbol, String name, int temperature, FluidTrait... traits) {
		super(compat, color, x, y, sheet, p, f, r, symbol, name, temperature, traits);
	}
	
	public FluidTypeCombustible setCombustionEnergy(FuelGrade grade, double energy) {
		this.fuelGrade = grade;
		this.combustionEnergy = energy;
		return this;
	}
	
	@Override
	public void addInfo(List<String> info) {
		super.addInfo(info);
		
		if(combustionEnergy > 0) {
			info.add(EnumChatFormatting.GOLD + "Provides " + EnumChatFormatting.RED + "" + BobMathUtil.getShortNumber((int) energy) + "HE " + EnumChatFormatting.GOLD + "per bucket used in an engine");
			info.add(EnumChatFormatting.GOLD + "Fuel grade: " + EnumChatFormatting.RED + this.fuelGrade.getGrade());
		}
	}
	
	public static enum FuelGrade {
		LOW("Low"),		//heating and industrial oil
		MEDIUM("Medium"),		//petroil
		HIGH("High"),		//diesel, gasoline
		AERO("Aviation");		//kerosene and other light aviation fuels
		
		private String grade;
		
		private FuelGrade(String grade) {
			this.grade = grade;
		}
		
		public String getGrade() {
			return this.grade;
		}
	}
}
