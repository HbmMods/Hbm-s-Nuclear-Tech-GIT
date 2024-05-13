package com.hbm.dim;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.NotImplementedException;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.util.AstronomyUtil;

public class SolarSystem {
    
    public static CelestialBody kerbol;

    // How high a craft will orbit a body. Launch calcs try to get to a circular orbit at this level.
    public static final float parkingOrbitAltitudeKm = 100;

    public static void init() {
        // All values pulled directly from KSP, most values are auto-converted to MC friendly ones
        kerbol = new CelestialBody("kerbol")
            .withMassRadius(1.757e28F, 261_600)
            .withRotationalPeriod(432_000)
            .withSatellites(

                new CelestialBody("moho", SpaceConfig.mohoDimension)
                    .withMassRadius(2.526e21F, 250)
                    .withSemiMajorAxis(5_263_138)
                    .withRotationalPeriod(1_210_000)
                    .withProcessingLevel(2)
                    .withTraits(CelestialBodyTrait.HOT),

                new CelestialBody("eve", SpaceConfig.eveDimension)
                    .withMassRadius(1.224e23F, 700)
                    .withSemiMajorAxis(9_832_684)
                    .withRotationalPeriod(80_500)
                    .withProcessingLevel(1)
                    .withTraits(new CBT_Atmosphere(Fluids.EVEAIR, 1F), CelestialBodyTrait.HOT),

                new CelestialBody("kerbin", 0) // overworld
                    .withMassRadius(5.292e22F, 600)
                    .withSemiMajorAxis(13_599_840)
                    .withRotationalPeriod(21_549)
                    .withTraits(new CBT_Atmosphere(Fluids.AIR, 1F), CelestialBodyTrait.BREATHABLE)
                    .withSatellites(

                        new CelestialBody("moon", SpaceConfig.moonDimension)
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
                    .withSatellites(

                        new CelestialBody("laythe", SpaceConfig.laytheDimension)
                            .withMassRadius(2.94e22F, 500)
                            .withSemiMajorAxis(27_184)
                            .withRotationalPeriod(52_981)
                            .withProcessingLevel(3)
                            .withTraits(new CBT_Atmosphere(Fluids.AIR, 0.6F), CelestialBodyTrait.BREATHABLE)

                    )

            );

        runTests();
    }

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

            double firstBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, start.semiMajorAxisKm, end.semiMajorAxisKm, start.massKg, start.radiusKm + parkingOrbitAltitudeKm);
            double secondBurnCost = calculateSingleHohmannTransfer(start.parent.massKg, end.semiMajorAxisKm, start.semiMajorAxisKm, end.massKg, end.radiusKm + parkingOrbitAltitudeKm);

            return firstBurnCost + secondBurnCost;
        } else if (start == end.parent) {
            // Transferring from parent body to moon

            double firstBurnCost = calculateSingleHohmannTransfer(start.massKg, start.radiusKm + parkingOrbitAltitudeKm, end.semiMajorAxisKm);
            double secondBurnCost = calculateSingleHohmannTransfer(start.massKg, end.semiMajorAxisKm, start.radiusKm + parkingOrbitAltitudeKm, end.massKg, end.radiusKm + parkingOrbitAltitudeKm);

            return firstBurnCost + secondBurnCost;
        } else if(start.parent == end) {
            // Transferring from moon to parent body

            double firstBurnCost = calculateSingleHohmannTransfer(end.massKg, start.semiMajorAxisKm, end.radiusKm + parkingOrbitAltitudeKm, start.massKg, start.radiusKm + parkingOrbitAltitudeKm);
            double secondBurnCost = calculateSingleHohmannTransfer(end.massKg, end.radiusKm + parkingOrbitAltitudeKm, start.semiMajorAxisKm);

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
        CelestialBody moon = CelestialBody.getBody("moon");
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
    }

}
