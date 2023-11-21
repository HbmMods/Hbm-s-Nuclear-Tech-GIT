package com.hbm.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AstronomyUtil
{
	public static final float AUToKm = 149598000F;
	public static final long offset = 0;
	public static final float day = 24000;
	//public static final long offset = 0;
	//https://www.desmos.com/calculator/h2v3nfaopa
	public static final float KerbolRadius=261600;
	//public static final float GillyRadius=13F;
	public static final float MunRadius=200F;
	public static final float MinmusRadius=60F;
	//public static final float IkeRadius=13F;
	public static final float LaytheRadius=13F;
	public static final float VallRadius=300F;
	//public static final float TyloRadius=13F;
	public static final float BopRadius=65F;
	//public static final float PolRadius=13F;*/
	
    public static final float MohoRadius=250;
    public static final float EveRadius=700;
    public static final float KerbinRadius=600F;    
    public static final float DunaRadius=320;
    public static final float IkeRadius=130F; 
    public static final float DresRadius=138;
    public static final float JoolRadius=6000;
    public static final float SarnusRadius=5300;
    public static final float UrlumRadius=25559;
    public static final float NeidonRadius=24766;
    public static final float PlockRadius=1184;
    public static final float TyloRadius=580F;
	
	public static final float MohoAU=5263138.304F/AUToKm;
    public static final float EveAU=9832684.544F/AUToKm;
    public static final float KerbinAU=13599840.256F/AUToKm;    
    public static final float DunaAU=20726155.264F/AUToKm;
    public static final float DresAU=40839348.203F/AUToKm;
    public static final float JoolAU=68773560.320F/AUToKm;
    public static final float SarnusAU=125798522.368F/AUToKm;
    public static final float UrlumAU=19.19F;
    public static final float NeidonAU=30.1F;
    public static final float PlockAU=39.5F;
    
    public static final float MohoP=102.58F;
    public static final float EveP=261.94F;
    public static final float KerbinP=365.25F;    
    public static final float DunaP=801.6389F;
    public static final float DresP=2217.27F;
    public static final float JoolP=4845.4367F;
    public static final float SarnusP=11987.096F;
    public static final float UrlumP=30687;
    public static final float NeidonP=60190;
    public static final float PlockP=90553;
    
    public static final float MunP=6.43F;
    public static final float MinmusP=49.88F;
    public static final float LaytheP=2.45F;
    public static final float VallP=4.91F;
	public static final float TyloP = 9.81F;
    public static final float BopP=25.21F;
    
    public static final float MunKerbinKm=12000;
    public static final float MinmusKerbinKm=47000;
    public static final float MunKerbinAU=MunKerbinKm/AUToKm;
    public static final float MinmusKerbinAU=MinmusKerbinKm/AUToKm;
    public static final float IkeDunaKm=9377.2F-DunaRadius;
    public static final float LaytheJoolKm = 27184F; 
    public static final float VallJoolKm = 43152F; 
    public static final float TyloJoolKm = 58500F;
    public static final float BopJoolKm = 128500F; 

    public static ResourceLocation mohoTexture = new ResourceLocation("hbm:textures/misc/moho.png");
    public static ResourceLocation eveTexture = new ResourceLocation("hbm:textures/misc/eve.png");
    public static ResourceLocation kerbinTexture = new ResourceLocation("hbm:textures/misc/kerbin.png");
    public static ResourceLocation dunaTexture = new ResourceLocation("hbm:textures/misc/duna.png");
    public static ResourceLocation joolTexture = new ResourceLocation("hbm:textures/misc/jool.png");
    public static ResourceLocation sarnusTexture = new ResourceLocation("hbm:textures/misc/sarnus.png");
    public static ResourceLocation urlumTexture = new ResourceLocation("hbm:textures/misc/urlum.png");
    public static ResourceLocation neidonTexture = new ResourceLocation("hbm:textures/misc/neidon.png");
    public static ResourceLocation plockTexture = new ResourceLocation("hbm:textures/misc/plock.png");
    
    /**
     * Calculates the maximum angular distance from the Sun that the planet can appear in the sky. The first planet is the one that you are running the calculation for, and the second planet is the one you're standing on.
     */
    public static double getMaxPlanetaryElongation(World world, Float planet1, Float planet2) {
    	return Math.toDegrees(Math.asin(planet1/planet2));
    }
    
    public static double getInterplanetaryDistance(World world, Float planet1AU, Float planet1Period, Float planet2AU, Float planet2Period) {
    	double PlanetAYear = day*planet1Period;
    	double PlanetBYear = day*planet2Period;
    	
    	double PlanetACos = planet1AU*Math.cos((2*(Math.PI*world.getWorldTime()+offset))/PlanetAYear);
    	double PlanetASin = planet1AU*Math.sin((2*(Math.PI*world.getWorldTime()+offset))/PlanetAYear);
    	
    	double PlanetBCos = planet2AU*Math.cos((2*(Math.PI*world.getWorldTime()+offset))/PlanetBYear);
    	double PlanetBSin = planet2AU*Math.sin((2*(Math.PI*world.getWorldTime()+offset))/PlanetBYear);
    	
		double distance = Math.sqrt(Math.pow(PlanetBCos-PlanetACos, 2)+Math.pow(PlanetBSin-PlanetASin, 2));
		return distance; 
    }
    /**
     * Calculates the visual angle between two planets. The first planet is the one you are on, and the second planet is the one you are observing.
     */
    public static double getInterplanetaryAngle(World world, Float planet1AU, Float planet1Period, Float planet2AU, Float planet2Period) {
    	double PlanetAYear = day*planet1Period;
    	double PlanetBYear = day*planet2Period;
    	
    	double PlanetACos = planet1AU*Math.cos((2*(Math.PI*world.getWorldTime()+offset))/PlanetAYear);
    	double PlanetASin = planet1AU*Math.sin((2*(Math.PI*world.getWorldTime()+offset))/PlanetAYear);
    	
    	double PlanetBCos = planet2AU*Math.cos((2*(Math.PI*world.getWorldTime()+offset))/PlanetBYear);
    	double PlanetBSin = planet2AU*Math.sin((2*(Math.PI*world.getWorldTime()+offset))/PlanetBYear);
    	
    	
		double angle = (360D+(Math.atan2(PlanetBSin-PlanetASin,PlanetBCos-PlanetACos)-Math.atan2(0-PlanetASin,0-PlanetACos))*(180/Math.PI))%360;//Math.sqrt(Math.pow(PlanetBCos-PlanetACos, 2)+Math.pow(PlanetBSin-PlanetASin, 2));
		return angle; 
    }
    
    /**
     * Calculates the synodic period between two planets. The first planet is the one closest to the sun, and the second planet is the more distant one.
     */
    public static float calculateSynodicPeriod(Float planet1Period, Float planet2Period)
    {
    	float subperiod = (1/planet1Period)-(1/planet2Period);
		//float period = 24000*(1/(1/planet1Period)-(1/planet2Period));
    	float period = day*(1/subperiod);
    	//System.out.println("planet1Period: "+planet1Period+" planet2Period: "+planet2Period+" period: "+period);
    	return period;
    }
    /**
     * Calculates the visual angle between two planets. The first planet is the one closest to the sun, and the second planet is the more distant one.
     */
    public static float calculatePlanetAngle(long par1, float par3, float planet1Period, float planet2Period)
    {
    	float synodic = calculateSynodicPeriod(planet1Period, planet2Period);
        par1 = Minecraft.getMinecraft().theWorld.getWorldTime()+offset;
        int j = (int) (par1 % synodic);
        float f1 = (j + par3) / synodic - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
        return f2 + (f1 - f2) / 3.0F;

    }
    public static float calculateStarAngle(long par1, float par3, float planetPeriod)
    {
        par1 = Minecraft.getMinecraft().theWorld.getWorldTime();
        int j = (int) (par1 % (day*planetPeriod));
        float f1 = (j + par3) / (day*planetPeriod) - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
        return f2 + (f1 - f2) / 3.0F;
    }
    public static float calculateMoonAngle(long par1, float par3, float time)
    {    	
        par1 = Minecraft.getMinecraft().theWorld.getWorldTime()+offset;
        int j = (int) (par1 % time);
        float f1 = (j + par3) / time - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
        return f2 + (f1 - f2) / 3.0F;
    }
    
    public static double[] getOuterOrbitObservation(World world, Float innerPlanetAU, Float innerPlanetPeriod, Float outerPlanetAU, Float outerPlanetPeriod) {
        double innerPlanetYear = day * innerPlanetPeriod;
        double outerPlanetYear = day * outerPlanetPeriod;

        double innerPlanetCos = innerPlanetAU * Math.cos((2 * (Math.PI * world.getWorldTime() + offset)) / innerPlanetYear);
        double innerPlanetSin = innerPlanetAU * Math.sin((2 * (Math.PI * world.getWorldTime() + offset)) / innerPlanetYear);

        double outerPlanetCos = outerPlanetAU * Math.cos((2 * (Math.PI * world.getWorldTime() + offset)) / outerPlanetYear);
        double outerPlanetSin = outerPlanetAU * Math.sin((2 * (Math.PI * world.getWorldTime() + offset)) / outerPlanetYear);

        double distance = Math.sqrt(Math.pow(outerPlanetCos - innerPlanetCos, 2) + Math.pow(outerPlanetSin - innerPlanetSin, 2));
        double angle = (360D + (Math.atan2(outerPlanetSin - innerPlanetSin, outerPlanetCos - innerPlanetCos) - Math.atan2(0 - innerPlanetSin, 0 - innerPlanetCos)) * (180 / Math.PI)) % 360;

        return new double[] { distance, angle };
    }
}