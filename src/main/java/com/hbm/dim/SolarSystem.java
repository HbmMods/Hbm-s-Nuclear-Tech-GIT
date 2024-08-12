package com.hbm.dim;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Temperature;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.AstronomyUtil;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SolarSystem {
	
	public static CelestialBody kerbol;

	// How much to scale celestial objects when rendering
	public static final double RENDER_SCALE = 180F;
	public static final double SUN_RENDER_SCALE = 4F;


	public static void init() {
		// All values pulled directly from KSP, most values are auto-converted to MC friendly ones
		kerbol = new CelestialBody("kerbol")
			.withMassRadius(1.757e28F, 261_600)
			.withRotationalPeriod(432_000)
			.withTexture("textures/environment/sun.png")
			.withShader(new ResourceLocation(RefStrings.MODID, "shaders/blackhole.frag"), 3) // Only shows when CBT_Destroyed
			.withSatellites(

				new CelestialBody("moho", SpaceConfig.mohoDimension, Body.MOHO)
					.withMassRadius(2.526e21F, 250)
					.withSemiMajorAxis(5_263_138)
					.withRotationalPeriod(210_000)
					.withColor(0.4863F, 0.4F, 0.3456F)
					.withBlockTextures(RefStrings.MODID + ":moho_stone", "", "", "")
					.withAxialTilt(30F)
					.withProcessingLevel(1)
					.withTraits(new CBT_Temperature(200)),

				new CelestialBody("eve", SpaceConfig.eveDimension, Body.EVE)
					.withMassRadius(1.224e23F, 700)
					.withSemiMajorAxis(9_832_684)
					.withRotationalPeriod(80_500)
					.withColor(0.408F, 0.298F, 0.553F)
					.withBlockTextures(RefStrings.MODID + ":eve_stone_2", "", "", "")
					.withProcessingLevel(2)
					.withTraits(new CBT_Atmosphere(Fluids.EVEAIR, 5D), new CBT_Temperature(400))
					.withSatellites(
						
						new CelestialBody("gilly")
							.withMassRadius(1.242e17F, 13)
							.withSemiMajorAxis(31_500)
							.withRotationalPeriod(28_255)
							.withTexture("hbm:textures/misc/space/planet.png")

					),

				new CelestialBody("kerbin", 0, Body.KERBIN) // overworld
					.withMassRadius(5.292e22F, 600)
					.withSemiMajorAxis(13_599_840)
					.withRotationalPeriod(21_549)
					.withColor(0.608F, 0.914F, 1.0F)
					.withTraits(new CBT_Atmosphere(Fluids.AIR, 1D), CelestialBodyTrait.HAS_WATER)
					.withSatellites(

						new CelestialBody("mun", SpaceConfig.moonDimension, Body.MUN)
							.withMassRadius(9.76e20F, 200)
							.withSemiMajorAxis(12_000)
							.withRotationalPeriod(138_984)
							.withTidalLockingTo("kerbin")
							.withBlockTextures(RefStrings.MODID + ":moon_rock", "", "", ""),

						new CelestialBody("minmus", SpaceConfig.minmusDimension, Body.MINMUS)
							.withMassRadius(2.646e19F, 60)
							.withSemiMajorAxis(47_000)
							.withRotationalPeriod(40_400)
							.withBlockTextures(RefStrings.MODID + ":minmus_stone", "", "", "")

					),

				new CelestialBody("duna", SpaceConfig.dunaDimension, Body.DUNA)
					.withMassRadius(4.515e21F, 320)
					.withSemiMajorAxis(20_726_155)
					.withRotationalPeriod(65_518)
					.withTidalLockingTo("ike")
					.withColor(0.6471f, 0.2824f, 0.1608f)
					.withBlockTextures(RefStrings.MODID + ":duna_rock", "", "", "")
					.withProcessingLevel(1)
					.withTraits(new CBT_Atmosphere(Fluids.DUNAAIR, 0.1D))
					.withProcessingLevel(1)
					.withSatellites(

						new CelestialBody("ike", SpaceConfig.ikeDimension, Body.IKE)
							.withMassRadius(2.782e20F, 130)
							.withSemiMajorAxis(3_200)
							.withBlockTextures(RefStrings.MODID + ":ike_stone", "", "", "")
							.withProcessingLevel(1)
							.withRotationalPeriod(65_518)
							.withTidalLockingTo("duna")
							.withProcessingLevel(1)

					),

				new CelestialBody("dres", SpaceConfig.dresDimension, Body.DRES)
					.withMassRadius(3.219e20F, 138)
					.withSemiMajorAxis(40_839_348)
					.withRotationalPeriod(34_800)
					.withBlockTextures(RefStrings.MODID + ":dresbase", "", "", "")
					.withProcessingLevel(2),
					

				new CelestialBody("jool")
					.withMassRadius(4.233e24F, 6_000)
					.withSemiMajorAxis(68_773_560)
					.withRotationalPeriod(36_000)
					.withColor(0.4588f, 0.6784f, 0.3059f)
					.withSatellites(

						new CelestialBody("laythe", SpaceConfig.laytheDimension, Body.LAYTHE)
							.withMassRadius(2.94e22F, 500)
							.withSemiMajorAxis(27_184)
							.withRotationalPeriod(52_981)
							.withTidalLockingTo("jool")
							.withProcessingLevel(3)
							.withTraits(new CBT_Atmosphere(Fluids.AIR, 0.45D).and(Fluids.XENON, 0.15D), CelestialBodyTrait.HAS_WATER),

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

	// Simple enum used for blocks and items
	public enum Body {
		BLANK(""),
		KERBIN("kerbin"),
		MUN("mun"),
		MINMUS("minmus"),
		DUNA("duna"),
		MOHO("moho"),
		DRES("dres"),
		EVE("eve"),
		IKE("ike"),
		LAYTHE("laythe");

		public String name;

		Body(String name) {
			this.name = name;
		}

		// memoising, since ore rendering would be horrendous otherwise
		private CelestialBody body;
		public CelestialBody getBody() {
			if(this == BLANK)
				return null;

			if(body == null)
				body = CelestialBody.getBody(name);

			return body;
		}

		public int getProcessingLevel() {
			if(this == BLANK) return 0;
			return getBody().processingLevel;
		}

		public String getStoneTexture() {
			if(this == BLANK) return null;
			return getBody().stoneTexture;
		}

		public int getDimensionId() {
			if(this == BLANK) return -1;
			return getBody().dimensionId;
		}
	}

	public static class AstroMetric {

		// Convert a solar system into a set of metrics defining their position and size in the sky for a given body

		public double distance;
		public double angle;
		public double apparentSize;
		public double phase;

		protected Vec3 position;

		public CelestialBody body;

		public AstroMetric(CelestialBody body, Vec3 position) {
			this.body = body;
			this.position = position;
		}

	}

	/**
	 * Celestial mechanics
	 */

	// Create an ordered list for rendering all bodies within the system, minus the parent star
	public static List<AstroMetric> calculateMetricsFromBody(World world, float partialTicks, double longitude, CelestialBody body) {
		List<AstroMetric> metrics = new ArrayList<AstroMetric>();

		// You know not the horrors I have suffered through, in order to fix tidal locking
		double offset = (double)body.getRotationalPeriod() * (longitude / 360.0);

		double ticks = ((double)world.getTotalWorldTime() + offset + partialTicks) * (double)AstronomyUtil.TIME_MULTIPLIER;

		// Get our XYZ coordinates of all bodies
		calculatePositionsRecursive(metrics, null, body.getStar(), ticks);

		// Get the metrics from a given body
		calculateMetricsFromBody(metrics, body);

		// Sort by increasing distance
		metrics.sort((a, b) -> {
			return (int)(b.distance - a.distance);
		});

		return metrics;
	}

	// Recursively calculate the XYZ position of all planets from polar coordinates + time
	private static void calculatePositionsRecursive(List<AstroMetric> metrics, AstroMetric parentMetric, CelestialBody body, double ticks) {
		Vec3 parentPosition = parentMetric != null ? parentMetric.position : Vec3.createVectorHelper(0, 0, 0);

		for(CelestialBody satellite : body.satellites) {
			Vec3 position = calculatePosition(satellite, ticks).addVector(parentPosition.xCoord, parentPosition.yCoord, parentPosition.zCoord);
			AstroMetric metric = new AstroMetric(satellite, position);

			metrics.add(metric);

			calculatePositionsRecursive(metrics, metric, satellite, ticks);
		}
	}

	// Calculates the position of the body around its parent
	private static Vec3 calculatePosition(CelestialBody body, double ticks) {
		// Get how far (in radians) a planet has gone around its parent
		double yearTicks = (double)body.getOrbitalPeriod() * (double)AstronomyUtil.TICKS_IN_DAY;
		double angleRadians = 2 * Math.PI * (ticks / yearTicks);

		double x = body.semiMajorAxisKm * Math.cos(angleRadians);
		double y = body.semiMajorAxisKm * Math.sin(angleRadians);

		return Vec3.createVectorHelper(x, y, 0);
	}

	// Calculates the metrics for a given body in the system
	private static void calculateMetricsFromBody(List<AstroMetric> metrics, CelestialBody body) {
		AstroMetric from = null;
		for(AstroMetric metric : metrics) {
			if(metric.body == body) {
				from = metric;
				break;
			}
		}

		for(AstroMetric to : metrics) {
			if(from == to)
				continue;

			// Calculate distance between bodies, for sorting
			to.distance = from.position.distanceTo(to.position);
			
			// Calculate apparent size, for scaling in render
			to.apparentSize = getApparentSize(to.body.radiusKm, to.distance);

			// Get angle in relation to 0, 0 (sun position, origin)
			to.angle = getApparentAngleDegrees(from.position, to.position);

			to.phase = getApparentAngleDegrees(to.position, from.position) / 180.0;
		}
	}

	private static double getApparentSize(double radius, double distance) {
		return 2D * (float)Math.atan((2D * radius) / (2D * distance)) * RENDER_SCALE;
	}

	private static double getApparentAngleDegrees(Vec3 from, Vec3 to) {
		double angleToOrigin = Math.atan2(-from.yCoord, -from.xCoord);
		double angleToTarget = Math.atan2(to.yCoord - from.yCoord, to.xCoord - from.xCoord);

		return MathHelper.wrapAngleTo180_double(Math.toDegrees(angleToOrigin - angleToTarget));
	}

	// Calculates how large to render the sun in the sky from a given vantage point
	public static double calculateSunSize(CelestialBody from) {
		if(from.parent == null) return 0;
		if(from.parent.parent != null) return calculateSunSize(from.parent);
		return getApparentSize(from.parent.radiusKm, from.semiMajorAxisKm) * SUN_RENDER_SCALE;
	}

	// Gets angle for a single planet, good for locking tidal bodies
	public static double calculateSingleAngle(World world, float partialTicks, CelestialBody from, CelestialBody to) {
		List<AstroMetric> metrics = new ArrayList<AstroMetric>();

		double ticks = ((double)world.getTotalWorldTime() + partialTicks) * (double)AstronomyUtil.TIME_MULTIPLIER;

		// Get our XYZ coordinates of all bodies
		calculatePositionsRecursive(metrics, null, from.getStar(), ticks);

		AstroMetric metricFrom = null;
		AstroMetric metricTo = null;

		for(AstroMetric metric : metrics) {
			if(metric.body == from) {
				metricFrom = metric;
			} else if(metric.body == to) {
				metricTo = metric;
			}
		}

		return getApparentAngleDegrees(metricFrom.position, metricTo.position);
	}


	/**
	 * Delta-V Calcs
	 */

	// Get a number of buckets of fuel required to travel somewhere, (halved, since we're assuming bipropellant)
	public static int getCostBetween(CelestialBody from, CelestialBody to, int mass, int thrust, int isp) {
		double fromDrag = getAtmosphericDrag(from.getTrait(CBT_Atmosphere.class));
		double toDrag = getAtmosphericDrag(to.getTrait(CBT_Atmosphere.class));

		double launchDV = SolarSystem.getLiftoffDeltaV(from, mass, thrust, fromDrag);
		double travelDV = SolarSystem.getDeltaVBetween(from, to);
		double landerDV = SolarSystem.getLandingDeltaV(to, mass, thrust, toDrag);
		
		double totalDV = launchDV + travelDV + landerDV;

		double g0 = 9.81;
		double exhaustVelocity = isp * g0;
		double propellantMass = mass * (1 - Math.exp(-(totalDV / exhaustVelocity)));

		// You can do some fuckery here to get the propellant mass into some reasonable number of buckets

		return MathHelper.ceiling_double_int(propellantMass * 5) * 10;
	}

	private static double getAtmosphericDrag(CBT_Atmosphere atmosphere) {
		if(atmosphere == null) return 0;
		double pressure = atmosphere.getPressure();
		return pressure * 0.01F;
	}

	// Provides the deltaV required to get into orbit, ignoring losses due to atmospheric friction
	// Make sure to convert from kN to N (kilonewtons to newtons) before calling these two functions
	public static double getLiftoffDeltaV(CelestialBody body, float craftMassKg, float craftThrustN, double atmosphericDrag) {
		return calculateSurfaceToOrbitDeltaV(body, craftMassKg, craftThrustN, atmosphericDrag, false);
	}

	// Uses aerobraking if an atmosphere is present
	public static double getLandingDeltaV(CelestialBody body, float craftMassKg, float craftThrustN, double atmosphericDrag) {
		return calculateSurfaceToOrbitDeltaV(body, craftMassKg, craftThrustN, atmosphericDrag, atmosphericDrag > 0.0005);
	}

	private static double calculateSurfaceToOrbitDeltaV(CelestialBody body, float craftMassKg, float craftThrustN, double atmosphericDrag, boolean lossesOnly) {
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
			return gravityLosses * (1 - atmosphericDrag); // drag helps on the way down

		return orbitalDeltaV + gravityLosses * (1 + atmosphericDrag); // and hinders on the way up
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

			double firstBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, start.semiMajorAxisKm, end.semiMajorAxisKm, start.massKg, start.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM);
			double secondBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, end.semiMajorAxisKm, start.semiMajorAxisKm, end.massKg, end.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM);

			return firstBurnCost + secondBurnCost;
		} else if (start == end.parent) {
			// Transferring from parent body to moon

			double firstBurnCost = calculateSingleHohmannTransfer(start.massKg, start.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM, end.semiMajorAxisKm);
			double secondBurnCost = calculateSingleHohmannTransfer(start.massKg, end.semiMajorAxisKm, start.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM, end.massKg, end.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM);

			return firstBurnCost + secondBurnCost;
		} else if(start.parent == end) {
			// Transferring from moon to parent body

			double firstBurnCost = calculateSingleHohmannTransfer(end.massKg, start.semiMajorAxisKm, end.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM, start.massKg, start.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM);
			double secondBurnCost = calculateSingleHohmannTransfer(end.massKg, end.radiusKm + AstronomyUtil.DEFAULT_ALTITUDE_KM, start.semiMajorAxisKm);

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
		CelestialBody mun = CelestialBody.getBody("mun");
		CelestialBody minmus = CelestialBody.getBody("minmus");
		CelestialBody ike = CelestialBody.getBody("ike");

		float deltaIVMass = 500_000;
		float RD180RocketThrust = 7_887 * 1_000;

		MainRegistry.logger.info("Kerbin launch cost: " + getLiftoffDeltaV(kerbin, deltaIVMass, RD180RocketThrust, 0));
		MainRegistry.logger.info("Eve launch cost: " + getLiftoffDeltaV(eve, deltaIVMass, RD180RocketThrust, 0));
		MainRegistry.logger.info("Duna launch cost: " + getLiftoffDeltaV(duna, deltaIVMass, RD180RocketThrust, 0));
		MainRegistry.logger.info("Mun launch cost: " + getLiftoffDeltaV(mun, deltaIVMass, RD180RocketThrust, 0));
		MainRegistry.logger.info("Minmus launch cost: " + getLiftoffDeltaV(minmus, deltaIVMass, RD180RocketThrust, 0));
		MainRegistry.logger.info("Ike launch cost: " + getLiftoffDeltaV(ike, deltaIVMass, RD180RocketThrust, 0));

		MainRegistry.logger.info("Kerbin -> Eve cost: " + getDeltaVBetween(kerbin, eve) + " - should be: " + (950+90+80+1330));
		MainRegistry.logger.info("Kerbin -> Duna cost: " + getDeltaVBetween(kerbin, duna) + " - should be: " + (950+130+250+360));
		MainRegistry.logger.info("Kerbin -> Ike cost: " + getDeltaVBetween(kerbin, ike) + " - should be: " + (950+130+250+30+180));
		MainRegistry.logger.info("Eve -> Duna cost: " + getDeltaVBetween(eve, duna));
		MainRegistry.logger.info("Kerbin -> Mun cost: " + getDeltaVBetween(kerbin, mun) + " - should be: " + (860+310));
		MainRegistry.logger.info("Kerbin -> Minmus cost: " + getDeltaVBetween(kerbin, minmus) + " - should be: " + (930+160));
		MainRegistry.logger.info("Mun -> Kerbin cost: " + getDeltaVBetween(mun, kerbin) + " - should be: " + (860+310));
		MainRegistry.logger.info("Minmus -> Kerbin cost: " + getDeltaVBetween(minmus, kerbin) + " - should be: " + (930+160));
		MainRegistry.logger.info("Minmus -> Ike cost: " + getDeltaVBetween(minmus, ike));

		MainRegistry.logger.info("Kerbin orbital period: " + kerbin.getOrbitalPeriod() + " - should be: " + 426);
		MainRegistry.logger.info("Eve orbital period: " + eve.getOrbitalPeriod() + " - should be: " + 261);
		MainRegistry.logger.info("Mun orbital period: " + mun.getOrbitalPeriod() + " - should be: " + 6);
	}

}
