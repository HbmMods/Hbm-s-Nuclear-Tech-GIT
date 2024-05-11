package com.hbm.dim;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.inventory.fluid.Fluids;

public class SolarSystem {
    
    public static CelestialBody kerbol;

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
                            .withTraits(new CBT_Atmosphere(Fluids.AIR, 1F), CelestialBodyTrait.BREATHABLE)

                    )

            );
    }

}
