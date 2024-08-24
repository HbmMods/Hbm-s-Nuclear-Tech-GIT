package com.hbm.handler.rbmkmk2;

import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.items.machine.ItemRBMKRod;
import net.minecraft.util.MathHelper;

import java.util.function.BiFunction;
import java.util.function.Function;


public class ItemRBMKRodFluxCurve extends ItemRBMKRod {

	/** Double 1: Flux ratio in.
	 * Double 2: Depletion value.
	 * Return double: Output flux ratio.
	 **/
	BiFunction<Double, Double, Double> ratioCurve;

	/** Double 1: Flux quantity in. <br>
	 * Double 2: Flux ratio in. <br>
	 * Return double: Output flux quantity.
	 **/
	BiFunction<Double, Double, Double> fluxCurve;

	public ItemRBMKRodFluxCurve(ItemRBMKPellet pellet) {
		super(pellet);
	}

	public ItemRBMKRodFluxCurve(String fullName) {
		super(fullName);
	}

	public ItemRBMKRodFluxCurve setOutputRatioCurve(Function<Double, Double> func) {
		this.ratioCurve = (fluxRatioIn, depletion) -> func.apply(fluxRatioIn) * 1.0D;
		return this;
	}

	public ItemRBMKRodFluxCurve setDepletionOutputRatioCurve(BiFunction<Double, Double, Double> func) {
		this.ratioCurve = func;
		return this;
	}

	public ItemRBMKRodFluxCurve setOutputFluxCurve(BiFunction<Double, Double, Double> func) {
		this.fluxCurve = func;
		return this;
	}

	public double fluxRatioOut(double fluxRatioIn, double depletion) {
		return MathHelper.clamp_double(ratioCurve.apply(fluxRatioIn, depletion), 0, 1);
	}

	public double fluxFromRatio(double quantity, double ratio) {
		return fluxCurve.apply(quantity, ratio);
	}
}
