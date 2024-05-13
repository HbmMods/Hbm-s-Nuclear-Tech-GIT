package com.hbm.dim;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.NotImplementedException;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.util.AstronomyUtil;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SolarSystem {
	
	public static CelestialBody kerbol;

	// How high a craft will orbit a body. Launch calcs try to get to a circular orbit at this level.
	public static final float PARKING_ORBIT_ALTITUDE_KM = 100F;

	// How much to scale celestial objects when rendering
	public static final float RENDER_SCALE = 180F;
	public static final float SUN_RENDER_SCALE = 4F;


	public static void init() {
		// All values pulled directly from KSP, most values are auto-converted to MC friendly ones
		kerbol = new CelestialBody("kerbol")
			.withMassRadius(1.757e28F, 261_600)
			.withRotationalPeriod(432_000)
			.withTexture("textures/environment/sun.png")
			.withSatellites(

				new CelestialBody("moho", SpaceConfig.mohoDimension)
					.withMassRadius(2.526e21F, 250)
					.withSemiMajorAxis(5_263_138)
					.withRotationalPeriod(1_210_000)
					.withColor(0.4863F, 0.4F, 0.3456F)
					.withAxialTilt(30F)
					.withProcessingLevel(2)
					.withTraits(CelestialBodyTrait.HOT),

				new CelestialBody("eve", SpaceConfig.eveDimension)
					.withMassRadius(1.224e23F, 700)
					.withSemiMajorAxis(9_832_684)
					.withRotationalPeriod(80_500)
					.withColor(0.408F, 0.298F, 0.553F)
					.withProcessingLevel(1)
					.withTraits(new CBT_Atmosphere(Fluids.EVEAIR, 1F), CelestialBodyTrait.HOT)
					.withSatellites(
						
						new CelestialBody("gilly")
							.withMassRadius(1.242e17F, 13)
							.withSemiMajorAxis(31_500)
							.withRotationalPeriod(28_255)
							.withTexture("hbm:textures/misc/space/planet.png")

					),

				new CelestialBody("kerbin", 0) // overworld
					.withMassRadius(5.292e22F, 600)
					.withSemiMajorAxis(13_599_840)
					.withRotationalPeriod(21_549)
					.withTraits(new CBT_Atmosphere(Fluids.AIR, 1F), CelestialBodyTrait.BREATHABLE)
					.withSatellites(

						new CelestialBody("mun", SpaceConfig.moonDimension)
							.withMassRadius(9.76e20F, 200)
							.withSemiMajorAxis(12_000)
							.withRotationalPeriod(138_984),

						new CelestialBody("minmus", SpaceConfig.minmusDimension)
							.withMassRadius(2.646e19F, 60)
							.withSemiMajorAxis(47_000)
							.withRotationalPeriod(40_400)

					),

				new CelestialBody("duna", SpaceConfig.dunaDimension)
					.withMassRadius(4.515e21F, 320)
					.withSemiMajorAxis(20_726_155)
					.withRotationalPeriod(65_518)
					.withColor(0.6471f, 0.2824f, 0.1608f)
					.withProcessingLevel(1)
					.withTraits(new CBT_Atmosphere(Fluids.CARBONDIOXIDE, 0.1F))
					.withSatellites(

						new CelestialBody("ike", SpaceConfig.ikeDimension)
							.withMassRadius(2.782e20F, 130)
							.withSemiMajorAxis(3_200)
							.withProcessingLevel(1)
							.withRotationalPeriod(65_518)

					),

				new CelestialBody("dres", SpaceConfig.dresDimension)
					.withMassRadius(3.219e20F, 138)
					.withSemiMajorAxis(40_839_348)
					.withRotationalPeriod(34_800),

				new CelestialBody("jool")
					.withMassRadius(4.233e24F, 6_000)
					.withSemiMajorAxis(68_773_560)
					.withRotationalPeriod(36_000)
					.withColor(0.4588f, 0.6784f, 0.3059f)
					.withSatellites(

						new CelestialBody("laythe", SpaceConfig.laytheDimension)
							.withMassRadius(2.94e22F, 500)
							.withSemiMajorAxis(27_184)
							.withRotationalPeriod(52_981)
							.withProcessingLevel(3)
							.withTraits(new CBT_Atmosphere(Fluids.AIR, 0.6F), CelestialBodyTrait.BREATHABLE),

						new CelestialBody("vall")
							.withMassRadius(3.109e21F, 300)
							.withSemiMajorAxis(43_152)
							.withRotationalPeriod(105_962),

						new CelestialBody("tylo")
							.withMassRadius(4.233e22F, 600)
							.withSemiMajorAxis(68_500)
							.withRotationalPeriod(211_926),

						new CelestialBody("bop")
							.withMassRadius(3.726e19F, 65)
							.withSemiMajorAxis(128_500)
							.withRotationalPeriod(544_507),

						new CelestialBody("pol")
							.withMassRadius(1.081e19F, 44)
							.withSemiMajorAxis(179_890)
							.withRotationalPeriod(901_902)

					)

			);

		runTests();
	}

	/**
	 * Celestial mechanics
	 */

	// Gets the distance between two planets at the current time in km
	public static double getInterplanetaryDistanceKm(World world, CelestialBody from, CelestialBody to) {
		double fromYearTicks = from.getOrbitalPeriod() * AstronomyUtil.TICKS_IN_DAY;
		double toYearTicks = to.getOrbitalPeriod() * AstronomyUtil.TICKS_IN_DAY;
		
		double fromCos = from.semiMajorAxisKm * Math.cos((2 * (Math.PI * world.getWorldTime())) / fromYearTicks);
		double fromSin = from.semiMajorAxisKm * Math.sin((2 * (Math.PI * world.getWorldTime())) / fromYearTicks);
		
		double toCos = to.semiMajorAxisKm * Math.cos((2 * (Math.PI * world.getWorldTime())) / toYearTicks);
		double toSin = to.semiMajorAxisKm * Math.sin((2 * (Math.PI * world.getWorldTime())) / toYearTicks);
		
		return Math.sqrt(Math.pow(toCos - fromCos, 2) + Math.pow(toSin - fromSin, 2));
	}

	// Gets the distance between two planets at the current time in AU
	public static double getInterplanetaryDistanceAU(World world, CelestialBody from, CelestialBody to) {
		return getInterplanetaryDistanceKm(world, from, to) * AstronomyUtil.KM_IN_AU;
	}

	// Calculates the maximum angular distance from the Sun that the planet can appear in the sky. The first planet is the one that you are running the calculation for, and the second planet is the one you're standing on.
	public static double getMaxPlanetaryElongation(CelestialBody from, CelestialBody to) {
		return Math.toDegrees(Math.asin(from.semiMajorAxisKm / to.semiMajorAxisKm));
	}

	// Calculates the visual angle between two planets. The first planet is the one you are on, and the second planet is the one you are observing.
	public static double getInterplanetaryAngle(World world, CelestialBody from, CelestialBody to) {
		double fromYearTicks = from.getOrbitalPeriod() * AstronomyUtil.TICKS_IN_DAY;
		double toYearTicks = to.getOrbitalPeriod() * AstronomyUtil.TICKS_IN_DAY;
		
		double fromCos = from.semiMajorAxisKm * Math.cos((2 * (Math.PI * world.getWorldTime())) / fromYearTicks);
		double fromSin = from.semiMajorAxisKm * Math.sin((2 * (Math.PI * world.getWorldTime())) / fromYearTicks);
		
		double toCos = to.semiMajorAxisKm * Math.cos((2 * (Math.PI * world.getWorldTime())) / toYearTicks);
		double toSin = to.semiMajorAxisKm * Math.sin((2 * (Math.PI * world.getWorldTime())) / toYearTicks);
		
		return (360D + (Math.atan2(toSin - fromSin, toCos - fromCos) - Math.atan2(0 - fromSin, 0 - fromCos)) * (180 / Math.PI)) % 360;
	}

	// Calculates the synodic period between two planets. The first planet is the one closest to the sun, and the second planet is the more distant one.
	public static float calculateSynodicPeriod(CelestialBody from, CelestialBody to) {
		float subperiod = (1F / (float)from.getOrbitalPeriod()) - (1F / (float)to.getOrbitalPeriod());
		float period = (1F / subperiod) * AstronomyUtil.TICKS_IN_DAY;
		return period;
	}
	
	// Calculates the visual angle between two planets. The first planet is the one closest to the sun, and the second planet is the more distant one.
	// When flipped, automatically limit to maximum elongation
	public static float calculatePlanetAngle(long worldTimeTicks, float partialTicks, CelestialBody from, CelestialBody to) {
		if(from.semiMajorAxisKm > to.semiMajorAxisKm) {
			float synodic = calculateSynodicPeriod(to, from);
			float sine = (float) Math.sin(((Math.PI / 2) / (synodic / 4)) * (worldTimeTicks));
			double elong = getMaxPlanetaryElongation(to, from);
			return (sine * (float)elong) / 360F;
		}
		
		float synodic = calculateSynodicPeriod(from, to);
		int j = (int) (worldTimeTicks % synodic);
		float f1 = (j + partialTicks) / synodic - 0.25F;

		if (f1 < 0.0F) {
			++f1;
		}

		if (f1 > 1.0F) {
			--f1;
		}

		float f2 = f1;
		f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
		return f2 + (f1 - f2) / 3.0F;
	}

	// Calculates how large to render the sun in the sky from a given vantage point
	public static float calculateSunSize(CelestialBody from) {
		return calculateBodySize(kerbol, from) * SUN_RENDER_SCALE;
	}

	// Calculates how large to render an object in the sky from a given vantage point
	// If both arguments are the same, get body size from the parent
	public static float calculateBodySize(CelestialBody body, CelestialBody from) {
		if(from.parent != body && from != body) return calculateBodySize(body, from.parent);
		return 2F * (float)Math.atan((2D * body.radiusKm) / (2D * from.semiMajorAxisKm)) * RENDER_SCALE;
	}


	/**
	 * Delta-V Calcs
	 */

	// Provides the deltaV required to get into orbit, ignoring losses due to atmospheric friction
	// Make sure to convert from kN to N (kilonewtons to newtons) before calling these two functions
	public static double getLiftoffDeltaV(CelestialBody body, float craftMassKg, float craftThrustN) {
		return calculateSurfaceToOrbitDeltaV(body, craftMassKg, craftThrustN, false);
	}

	// Uses aerobraking if an atmosphere is present
	public static double getLandingDeltaV(CelestialBody body, float craftMassKg, float craftThrustN, boolean hasAtmosphere) {
		return calculateSurfaceToOrbitDeltaV(body, craftMassKg, craftThrustN, hasAtmosphere);
	}

	private static double calculateSurfaceToOrbitDeltaV(CelestialBody body, float craftMassKg, float craftThrustN, boolean lossesOnly) {
		double orbitalDeltaV = Math.sqrt((AstronomyUtil.GRAVITATIONAL_CONSTANT * body.massKg) / (body.radiusKm * 1_000));
		double thrustToWeightRatio = craftThrustN / (craftMassKg * body.getSurfaceGravity());

		if(thrustToWeightRatio < 1)
			return Double.MAX_VALUE;

		// We have to find out how long the burn will take to get our "gravity tax"
		// Shorter burns have less gravity losses, meaning higher thrust is desirable
		double acceleration = thrustToWeightRatio * body.getSurfaceGravity();
		double timeToOrbit = orbitalDeltaV / acceleration;
		double gravityLosses = body.getSurfaceGravity() * timeToOrbit;

		if(lossesOnly)
			return gravityLosses;

		return orbitalDeltaV + gravityLosses;
	}

	// Provides the deltaV required to transfer from the orbit of one body to the orbit of another
	// Does not currently support travelling to the main body (Sol)
	// Our structure doesn't currently require this, but if it does, go annoy Mellow to add it lmao
	public static double getDeltaVBetween(CelestialBody start, CelestialBody end) {
		return calculateHohmannTransfer(start, end);
	}

	// This calculates the entire transfer cost, adding together the cost of two burns
	private static double calculateHohmannTransfer(CelestialBody start, CelestialBody end) {
		if(start == end) {
			// Transfer to self, ignore

			return 0;
		} else if(start.parent == null || end.parent == null) {
			throw new NotImplementedException("Transfers to and from solar bodies not supported");
		} else if(start.parent == end.parent) {
			// Intersystem transfer

			double firstBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, start.semiMajorAxisKm, end.semiMajorAxisKm, start.massKg, start.radiusKm + PARKING_ORBIT_ALTITUDE_KM);
			double secondBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, end.semiMajorAxisKm, start.semiMajorAxisKm, end.massKg, end.radiusKm + PARKING_ORBIT_ALTITUDE_KM);

			return firstBurnCost + secondBurnCost;
		} else if (start == end.parent) {
			// Transferring from parent body to moon

			double firstBurnCost = calculateSingleHohmannTransfer(start.massKg, start.radiusKm + PARKING_ORBIT_ALTITUDE_KM, end.semiMajorAxisKm);
			double secondBurnCost = calculateSingleHohmannTransfer(start.massKg, end.semiMajorAxisKm, start.radiusKm + PARKING_ORBIT_ALTITUDE_KM, end.massKg, end.radiusKm + PARKING_ORBIT_ALTITUDE_KM);

			return firstBurnCost + secondBurnCost;
		} else if(start.parent == end) {
			// Transferring from moon to parent body

			double firstBurnCost = calculateSingleHohmannTransfer(end.massKg, start.semiMajorAxisKm, end.radiusKm + PARKING_ORBIT_ALTITUDE_KM, start.massKg, start.radiusKm + PARKING_ORBIT_ALTITUDE_KM);
			double secondBurnCost = calculateSingleHohmannTransfer(end.massKg, end.radiusKm + PARKING_ORBIT_ALTITUDE_KM, start.semiMajorAxisKm);

			return firstBurnCost + secondBurnCost;
		} else {
			// Complex transfer (moon -> moon, moon -> other planet)

			CelestialBody commonParent = getCommonParent(start, end);
			CelestialBody fromBody = start;
			CelestialBody toBody = end;
			float currentFromOrbitRadius = fromBody.semiMajorAxisKm;
			float currentToOrbitRadius = toBody.semiMajorAxisKm;

			double burnCost = 0;

			// Go up the tree from start
			while(fromBody.parent != commonParent) {
				burnCost += calculateSingleHohmannTransfer(fromBody.parent.massKg, fromBody.semiMajorAxisKm, fromBody.semiMajorAxisKm, fromBody.massKg, fromBody.semiMajorAxisKm);
				currentFromOrbitRadius = fromBody.semiMajorAxisKm;
				fromBody = fromBody.parent;
			}

			// Go up the tree from end
			while(toBody.parent != commonParent) {
				burnCost += calculateSingleHohmannTransfer(toBody.parent.massKg, toBody.semiMajorAxisKm, toBody.semiMajorAxisKm, toBody.massKg, toBody.semiMajorAxisKm);
				currentToOrbitRadius = toBody.semiMajorAxisKm;
				toBody = toBody.parent;
			}

			// Transfer interplanetary
			burnCost += calculateSingleHohmannTransfer(commonParent.massKg, fromBody.semiMajorAxisKm, toBody.semiMajorAxisKm, fromBody.massKg, currentFromOrbitRadius);
			burnCost += calculateSingleHohmannTransfer(commonParent.massKg, toBody.semiMajorAxisKm, fromBody.semiMajorAxisKm, toBody.massKg, currentToOrbitRadius);

			return burnCost;
		}
	}

	private static CelestialBody getCommonParent(CelestialBody start, CelestialBody end) {
		CelestialBody startParent = start.parent;
			
		while(startParent != null) {
			CelestialBody endParent = end.parent;
			while(endParent != null) {
				if(startParent == endParent)
					return startParent;

				endParent = endParent.parent;
			}
			startParent = startParent.parent;
		}

		throw new InvalidParameterException("Bodies aren't in the same solar system");
	}

	
	// All transfer math is commutative, injection burn (getting onto the transfer orbit) takes the exact same dV as
	// the insertion burn (entering the target orbit)

	// Calculate orbit to orbit transfer around a parent body, without any need to escape an inner gravity well.
	// This is used to transfer from low orbit to a moon.
	private static double calculateSingleHohmannTransfer(float parentMassKg, float fromRadiusKm, float toRadiusKm) {
		double parentGravitationalParameter = parentMassKg * AstronomyUtil.GRAVITATIONAL_CONSTANT;

		// We're finding the dv to transfer between these circular orbits
		double startOrbitalRadius = fromRadiusKm * 1_000;
		double endOrbitalRadius = toRadiusKm * 1_000;

		// The semimajor axis of the transfer orbit (average distance of orbit)
		double transferSemiMajorAxis = (startOrbitalRadius + endOrbitalRadius) / 2;

		// Our current orbital velocity around the parent (planet orbital velocity)
		double currentOrbitalVelocity = Math.sqrt(parentGravitationalParameter / startOrbitalRadius);

		// The velocity we need to get onto our transfer orbit
		double requiredVelocity = Math.sqrt(parentGravitationalParameter * ((2 / startOrbitalRadius) - (1 / transferSemiMajorAxis)));

		// The true velocity we need to add to get onto our transfer orbit (desired energy minus the energy we already have)
		return Math.abs(requiredVelocity - currentOrbitalVelocity);
	}

	// Calculate orbit to orbit transfer around a parent body, escaping from a well.
	// This is used for interplanetary transfers.
	private static double calculateSingleHohmannTransfer(float parentMassKg, float fromRadiusKm, float toRadiusKm, float fromMassKg, float parkingOrbitRadiusKm) {
		// First we get our required velocity change ignoring the body we're currently orbiting
		double hyperbolicVelocity = calculateSingleHohmannTransfer(parentMassKg, fromRadiusKm, toRadiusKm);

		double fromGravitationalParameter = fromMassKg * AstronomyUtil.GRAVITATIONAL_CONSTANT;

		// Our current orbital radius and velocity around the starting body
		double parkingOrbitRadius = parkingOrbitRadiusKm * 1_000;
		double parkingOrbitVelocity = Math.sqrt(fromGravitationalParameter / parkingOrbitRadius);

		// The amount of energy needed to escape the start body to get onto our transfer orbit
		double escapeVelocity = Math.sqrt((2 * fromGravitationalParameter) / parkingOrbitRadius);
		double escapeHyperVelocity = Math.sqrt(hyperbolicVelocity * hyperbolicVelocity + escapeVelocity * escapeVelocity);

		// The first half of the required dV to get to our destination!
		return escapeHyperVelocity - parkingOrbitVelocity;
	}

	public static void runTests() {
		CelestialBody kerbin = CelestialBody.getBody("kerbin");
		CelestialBody eve = CelestialBody.getBody("eve");
		CelestialBody duna = CelestialBody.getBody("duna");
		CelestialBody moon = CelestialBody.getBody("mun");
		CelestialBody minmus = CelestialBody.getBody("minmus");
		CelestialBody ike = CelestialBody.getBody("ike");

		float deltaIVMass = 500_000;
		float RD180RocketThrust = 7_887 * 1_000;

		MainRegistry.logger.info("Kerbin launch cost: " + getLiftoffDeltaV(kerbin, deltaIVMass, RD180RocketThrust));
		MainRegistry.logger.info("Eve launch cost: " + getLiftoffDeltaV(eve, deltaIVMass, RD180RocketThrust));
		MainRegistry.logger.info("Duna launch cost: " + getLiftoffDeltaV(duna, deltaIVMass, RD180RocketThrust));
		MainRegistry.logger.info("Moon launch cost: " + getLiftoffDeltaV(moon, deltaIVMass, RD180RocketThrust));
		MainRegistry.logger.info("Minmus launch cost: " + getLiftoffDeltaV(minmus, deltaIVMass, RD180RocketThrust));
		MainRegistry.logger.info("Ike launch cost: " + getLiftoffDeltaV(ike, deltaIVMass, RD180RocketThrust));

		MainRegistry.logger.info("Kerbin -> Eve cost: " + getDeltaVBetween(kerbin, eve) + " - should be: " + (950+90+80+1330));
		MainRegistry.logger.info("Kerbin -> Duna cost: " + getDeltaVBetween(kerbin, duna) + " - should be: " + (950+130+250+360));
		MainRegistry.logger.info("Kerbin -> Ike cost: " + getDeltaVBetween(kerbin, ike) + " - should be: " + (950+130+250+30+180));
		MainRegistry.logger.info("Eve -> Duna cost: " + getDeltaVBetween(eve, duna));
		MainRegistry.logger.info("Kerbin -> Moon cost: " + getDeltaVBetween(kerbin, moon) + " - should be: " + (860+310));
		MainRegistry.logger.info("Kerbin -> Minmus cost: " + getDeltaVBetween(kerbin, minmus) + " - should be: " + (930+160));
		MainRegistry.logger.info("Moon -> Kerbin cost: " + getDeltaVBetween(moon, kerbin) + " - should be: " + (860+310));
		MainRegistry.logger.info("Minmus -> Kerbin cost: " + getDeltaVBetween(minmus, kerbin) + " - should be: " + (930+160));
		MainRegistry.logger.info("Minmus -> Ike cost: " + getDeltaVBetween(minmus, ike));

		MainRegistry.logger.info("Kerbin orbital period: " + kerbin.getOrbitalPeriod() + " - should be: " + 426);
		MainRegistry.logger.info("Eve orbital period: " + eve.getOrbitalPeriod() + " - should be: " + 261);
		MainRegistry.logger.info("Moon orbital period: " + moon.getOrbitalPeriod() + " - should be: " + 6);
	}

}
