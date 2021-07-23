package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.generic.BlockGeneric;

import net.minecraft.block.material.Material;

public class Lightbulb extends BlockGeneric
{
	/** Estimated lifespan **/
	private long lifespan;
	/** Level of potential variance between the estimated lifespan and the actual lifespan **/
	private int modifier;
	/** Actual lifespan, not displayed, may be different between bulbs **/
	private long lifespanActual;
	/** Power/tick consumed **/
	private int powerConsumption;
	/** Sound the bulb makes when burning out **/
	public String burnOutSound = null;
	/** Sound of the bulb when idle, played randomly **/
	public String idleSound = null;
	/** Random number provider **/
	private Random rand = new Random();
	/**
	 * Construct a new lightbulb type
	 * @param mat - Material type
	 * @param life - Estimated lifespan
	 * @param mod - Level of potential variance between the estimated lifespan and the actual lifespan
	 * @param light - Amount of light produced
	 * @param power - Power/tick consumed
	 */
	public Lightbulb(Material mat, long life, int mod, float light, int power)
	{
		super(mat);
		lifespan = life;
		modifier = mod;
//		setLightLevel(light);
		setLightOpacity(2);
//		lifespanActual = life * mod * (rand.nextBoolean() ? -1 : 1);
	}

}
