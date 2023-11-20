package com.hbm.dim.minmus;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.util.AstronomyUtil;

import java.util.Random;

import javax.swing.text.Position;

public class SkyProviderMinmus extends IRenderHandler {
	
	private static final ResourceLocation sunTexture = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation kerbin = new ResourceLocation("hbm:textures/misc/space/kerbin.png");
	private static final ResourceLocation kerbinTom = new ResourceLocation("hbm:textures/misc/space/kerbinTom.png");
	private static final ResourceLocation kerbinBurning = new ResourceLocation("hbm:textures/misc/space/kerbinBurning.png");
	private static final ResourceLocation kerbinDust = new ResourceLocation("hbm:textures/misc/space/kerbinDust.png");
	private static final ResourceLocation planet = new ResourceLocation("hbm:textures/misc/space/planet.png");
	private static final ResourceLocation flash = new ResourceLocation("hbm:textures/misc/space/flare.png");
	private static final ResourceLocation night = new ResourceLocation("hbm:textures/misc/space/night.png");
	private static final ResourceLocation digammaStar = new ResourceLocation("hbm:textures/misc/space/star_digamma.png");
	private static final ResourceLocation moonTexture = new ResourceLocation("hbm:textures/misc/space/mun.png");

	public static boolean displayListsInitialized = false;
	public static int starGLCallList;
	public static int glSkyList;
	public static int glSkyList2;

	protected double x;
	protected double y;
	protected double z;

	public SkyProviderMinmus() {
	    if (!displayListsInitialized) {
	        initializeDisplayLists();
	    }
	}

	private void initializeDisplayLists() {
	    starGLCallList = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
		this.renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		final Tessellator tessellator = Tessellator.instance;
		this.glSkyList = this.starGLCallList + 1;
		GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
		final byte byte2 = 64;
		final int i = 256 / byte2 + 2;
		float f = 16F;

		for(int j = -byte2 * i; j <= byte2 * i; j += byte2) {
			for(int l = -byte2 * i; l <= byte2 * i; l += byte2) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j + 0, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + byte2);
				tessellator.addVertex(j + 0, f, l + byte2);
				tessellator.draw();
			}
		}

		GL11.glEndList();
		this.glSkyList2 = this.starGLCallList + 2;
		GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
		f = -16F;
		tessellator.startDrawingQuads();

		for(int k = -byte2 * i; k <= byte2 * i; k += byte2) {
			for(int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
				tessellator.addVertex(k + byte2, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + byte2);
				tessellator.addVertex(k + byte2, f, i1 + byte2);
			}
		}

		tessellator.draw();
		GL11.glEndList();
		displayListsInitialized = true;
	}
	

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		boolean impact = ImpactWorldHandler.getImpactForClient(world.provider.getProviderForDimension(0).worldObj);
		float fire = ImpactWorldHandler.getFireForClient(world.provider.getProviderForDimension(0).worldObj);
		float atmosphericDust = ImpactWorldHandler.getDustForClient(world.provider.getProviderForDimension(0).worldObj);
		float solar = (AstronomyUtil.KerbolRadius*4/(AstronomyUtil.KerbinAU*AstronomyUtil.AUToKm))*360;
		double MohoKerbin = AstronomyUtil.getInterplanetaryDistance(world, AstronomyUtil.MohoAU, AstronomyUtil.MohoP, AstronomyUtil.KerbinAU, AstronomyUtil.KerbinP);
		double EveKerbin = AstronomyUtil.getInterplanetaryDistance(world, AstronomyUtil.EveAU, AstronomyUtil.EveP, AstronomyUtil.KerbinAU, AstronomyUtil.KerbinP);
        double KerbinDuna = AstronomyUtil.getInterplanetaryDistance(world, AstronomyUtil.KerbinAU, AstronomyUtil.KerbinP, AstronomyUtil.DunaAU, AstronomyUtil.DunaP);
        double KerbinJool = AstronomyUtil.getInterplanetaryDistance(world, AstronomyUtil.KerbinAU, AstronomyUtil.KerbinP, AstronomyUtil.JoolAU, AstronomyUtil.JoolP);
        double MunMinmus = AstronomyUtil.getInterplanetaryDistance(world, AstronomyUtil.MunKerbinKm, AstronomyUtil.MunP, AstronomyUtil.MinmusKerbinKm, AstronomyUtil.MinmusP);
        
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float f1 = (float) vec3.xCoord;
		float f2 = (float) vec3.yCoord;
		float f3 = (float) vec3.zCoord;
		float f6;

		if(mc.gameSettings.anaglyph) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}

		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		float f7;
		float f8;
		float f9;
		double f10;
		float f18 = world.getStarBrightness(partialTicks);

		if(f18 > 0.0F) {
			GL11.glPushMatrix();
	        mc.renderEngine.bindTexture(this.night);
	        GL11.glEnable(3553);
	        GL11.glBlendFunc(770, 1);
	        float starBrightness = world.getStarBrightness(partialTicks) *0.6f;
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightness);
	        float x = 0.0F;
	        float y = 0.0F;
	        float z = 0.0F;
	        GL11.glTranslatef(x, y, z);
	        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
	        
	       // 

	        GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightness);
	        
	        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
	        renderSkyboxSide(tessellator, 4);
	        
	        GL11.glPushMatrix();
	        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
	        renderSkyboxSide(tessellator, 1);
	        GL11.glPopMatrix();
	        
	        GL11.glPushMatrix();
	        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
	        renderSkyboxSide(tessellator, 0);
	        GL11.glPopMatrix();
	        
	        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
	        renderSkyboxSide(tessellator, 5);
	        
	        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
	        renderSkyboxSide(tessellator, 2);
	        
	        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
	        renderSkyboxSide(tessellator, 3);
	        GL11.glDisable(3553);
	        GL11.glPopMatrix();	        
			/*GL11.glPushMatrix();
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-19.0F, 0, 1.0F, 0);
			GL11.glColor4f(f18, f18, f18, f18);
			GL11.glCallList(this.starGLCallList);
			GL11.glPopMatrix();*/
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
		GL11.glPushMatrix();
		f7 = 0.0F;
		f8 = 0.0F;
		f9 = 0.0F;
		GL11.glTranslatef(f7, f8, f9);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);

		// Render sun
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
		// Some blanking to conceal the stars
		f10 = (AstronomyUtil.KerbolRadius*2/(AstronomyUtil.KerbinAU*AstronomyUtil.AUToKm))*360;
		double f11 = f10*2;
		tessellator.startDrawingQuads();
		tessellator.addVertex(-f10, 99.9D, -f10);
		tessellator.addVertex(f10, 99.9D, -f10);
		tessellator.addVertex(f10, 99.9D, f10);
		tessellator.addVertex(-f10, 99.9D, f10);
		tessellator.draw();
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
			mc.renderEngine.bindTexture(this.sunTexture);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
			tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
			tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
			tessellator.draw();
		}
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5f);
			mc.renderEngine.bindTexture(this.flash);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-f11, 100.0D, -f11, 0.0D, 0.0D);
			tessellator.addVertexWithUV(f11, 100.0D, -f11, 1.0D, 0.0D);
			tessellator.addVertexWithUV(f11, 100.0D, f11, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-f11, 100.0D, f11, 0.0D, 1.0D);
			tessellator.draw();
		}
		{
    		GL11.glPushMatrix();
    		//GL11.glDisable(GL11.GL_BLEND);
    		f10 = 0.75f;
    		GL11.glColor4d(0.4588f, 0.6784f, 0.3059f, 1/KerbinJool);
    		GL11.glRotatef(AstronomyUtil.calculatePlanetAngle(world.getWorldTime(), partialTicks, AstronomyUtil.KerbinP, AstronomyUtil.JoolP) * -360.0F, 1.0F, 0.0F, 0.0F);        		
    		GL11.glRotatef(280F, 1.0F, 0.0F, 0.0F);
    		mc.renderEngine.bindTexture(this.planet);
    		tessellator.startDrawingQuads();
    		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, -100.0D, f10, 1.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1.0D, 1.0D);
    		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0.0D, 1.0D);
    		tessellator.draw();
    		//GL11.glEnable(GL11.GL_BLEND);
    		GL11.glPopMatrix();
    	}
		{
    		GL11.glPushMatrix();
    		//GL11.glDisable(GL11.GL_BLEND);
    		f10 = 0.75f;
    		GL11.glColor4d(0.6471f, 0.2824f, 0.1608f, 1/KerbinDuna);
    		GL11.glRotatef(AstronomyUtil.calculatePlanetAngle(world.getWorldTime(), partialTicks, AstronomyUtil.DunaP, AstronomyUtil.KerbinP) * -360.0F, 1.0F, 0.0F, 0.0F);        		
    		GL11.glRotatef(280F, 1.0F, 0.0F, 0.0F);
    		mc.renderEngine.bindTexture(this.planet);
    		tessellator.startDrawingQuads();
    		tessellator.addVertexWithUV(-f10, -100.0D, f10, 0.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, -100.0D, f10, 1.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, -100.0D, -f10, 1.0D, 1.0D);
    		tessellator.addVertexWithUV(-f10, -100.0D, -f10, 0.0D, 1.0D);
    		tessellator.draw();
    		//GL11.glEnable(GL11.GL_BLEND);
    		GL11.glPopMatrix();
    	}
        {
        	GL11.glPushMatrix();        	
        	float EveRad = 0.15F;
        	double EveSyn = AstronomyUtil.calculateSynodicPeriod(AstronomyUtil.EveP, AstronomyUtil.KerbinP);
        		//System.out.println("Venus-Earth distance: "+VenusEarth);
        	float sine = (float) Math.sin(((Math.PI/2)/(EveSyn/4))*(world.getWorldTime()+AstronomyUtil.offset));
        	double elong = AstronomyUtil.getMaxPlanetaryElongation(FMLClientHandler.instance().getClient().theWorld, AstronomyUtil.EveAU, AstronomyUtil.KerbinAU);
        	GL11.glRotatef((float) (sine*elong), 1.0F, 0.0F, 0.0F);
        	GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        	GL11.glEnable(GL11.GL_TEXTURE_2D);
        	GL11.glColor4d(0.408F, 0.298F, 0.553F, 1/EveKerbin);
        	f10 = 0.75f;
        	mc.renderEngine.bindTexture(this.planet);
        	tessellator.startDrawingQuads();
        	tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
        	tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
        	tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
        	tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
        	tessellator.draw();
    		GL11.glPopMatrix();
        }
    	{
    		GL11.glPushMatrix();
        	float MohoRad = 0.15F;
        	double MohoSyn = AstronomyUtil.calculateSynodicPeriod(AstronomyUtil.MohoP, AstronomyUtil.KerbinP);
        	float sine = (float) Math.sin(((Math.PI/2)/(MohoSyn/4))*(world.getWorldTime()+AstronomyUtil.offset));
        	double elong = AstronomyUtil.getMaxPlanetaryElongation(FMLClientHandler.instance().getClient().theWorld, AstronomyUtil.MohoAU, AstronomyUtil.KerbinAU);
        	GL11.glRotatef((float) (sine*elong), 1.0F, 0.0F, 0.0F);
        	GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        	GL11.glEnable(GL11.GL_TEXTURE_2D);
        	GL11.glColor4d(0.4863F, 0.4F, 0.3456, 1/MohoKerbin);
        	f10 = 0.75f;
        	mc.renderEngine.bindTexture(this.planet);
        	tessellator.startDrawingQuads();
        	tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
        	tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
        	tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
        	tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
        	tessellator.draw();
        	GL11.glPopMatrix();
        }
    	
    	{
    		//if(world.getWorldTime()%20==0)
    		//{
    			//System.out.println("MunMinmus: "+(MunMinmus*1)+" MinmusKerbinKm: "+AstronomyUtil.MinmusKerbinKm);	
    		//}    		
    		if(MunMinmus>AstronomyUtil.MinmusKerbinKm)
    		{
    			GL11.glPushMatrix();
    			GL11.glDisable(GL11.GL_BLEND);
    			double MunRad = AstronomyUtil.MunRadius;
    			double MunSyn = AstronomyUtil.calculateSynodicPeriod(AstronomyUtil.MunP, AstronomyUtil.MinmusP);
    			float sine = (float) Math.sin(((Math.PI/2)/(MunSyn/4))*(world.getWorldTime()+AstronomyUtil.offset));
    			double elong = AstronomyUtil.getMaxPlanetaryElongation(FMLClientHandler.instance().getClient().theWorld, AstronomyUtil.MunKerbinKm, AstronomyUtil.MinmusKerbinKm);

    			GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
        		GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
        		GL11.glRotatef((float) (sine*elong), 1.0F, 0.0F, 0.0F);
        		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        		GL11.glEnable(GL11.GL_TEXTURE_2D);
        		GL11.glColor4d(1, 1, 1, 1);
        		f10 = Math.max(0.0F, (float) (MunRad/(MunMinmus*1)))*180;
        		mc.renderEngine.bindTexture(this.moonTexture);
        		tessellator.startDrawingQuads();
        		tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
        		tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
        		tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
        		tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
        		tessellator.draw();
        		GL11.glEnable(GL11.GL_BLEND);
        		GL11.glPopMatrix();
    		}
        }

		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4d(0, 0, 0, 1);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
			//FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbin);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
			tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
			tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_BLEND);

			if(impact)
			{
				if(fire>0)
				{
					if(fire>0.9)
					{
						{
							GL11.glPushMatrix();
							GL11.glColor4d(1, 1, 1, Math.max(0,(1-Math.min(1,(1-fire)*20)*(1-Math.min(1,atmosphericDust*2)))));
							//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
							//GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
							//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
							f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
							FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbin);
							tessellator.startDrawingQuads();
							tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
							tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
							tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
							tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
							tessellator.draw();
							GL11.glPopMatrix();
						}	
					}					
					{
						GL11.glPushMatrix();
						GL11.glColor4d(1, 1, 1, fire);
						//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(-6.0F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						f10 = ((200F/AstronomyUtil.MinmusKerbinKm)*180)*Math.min(fire,(1-fire)*200);
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.flash);
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
						tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
						tessellator.draw();
						GL11.glPopMatrix();
					}
					{
						GL11.glPushMatrix();
						GL11.glColor4d(1, 1, 1, Math.min(1,(1-fire)*20)*(1-Math.min(1,atmosphericDust*2)));
						//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbinBurning);
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
						tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
						tessellator.draw();
						GL11.glPopMatrix();
					}
				}
				if(fire==0)
				{
					{
						GL11.glPushMatrix();
						GL11.glColor4d(1, 1, 1, (1-Math.min(1,atmosphericDust*2)));
						//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbinTom);
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
						tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
						tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
						tessellator.draw();
						GL11.glPopMatrix();
					}
				}
				{
					GL11.glPushMatrix();
					GL11.glColor4d(1, 1, 1, Math.min(1,atmosphericDust*2));
					//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
					//GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
					//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbinDust);
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
					tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
					tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
					tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
					tessellator.draw();
					GL11.glPopMatrix();
				}
			}
			if(!impact)
			{
				GL11.glPushMatrix();
				GL11.glColor4d(1, 1, 1, 1);
				//GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
				//GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
				//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				f10 = (AstronomyUtil.KerbinRadius/AstronomyUtil.MinmusKerbinKm)*180;
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.kerbin);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
				tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
				tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
		if(MunMinmus<=AstronomyUtil.MinmusKerbinKm)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_BLEND);
			double MunRad = AstronomyUtil.MunRadius;
			double MunSyn = AstronomyUtil.calculateSynodicPeriod(AstronomyUtil.MunP, AstronomyUtil.MinmusP);
			float sine = (float) Math.sin(((Math.PI/2)/(MunSyn/4))*(world.getWorldTime()+AstronomyUtil.offset));
			double elong = AstronomyUtil.getMaxPlanetaryElongation(FMLClientHandler.instance().getClient().theWorld, AstronomyUtil.MunKerbinKm, AstronomyUtil.MinmusKerbinKm);

			GL11.glRotatef(world.getCelestialAngle(partialTicks) * -360.0F, 1.0F, 0.0F, 0.0F);
    		GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
    		GL11.glRotatef((float) (sine*elong), 1.0F, 0.0F, 0.0F);
    		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
    		GL11.glEnable(GL11.GL_TEXTURE_2D);
    		GL11.glColor4d(1, 1, 1, 1);
    		f10 = Math.max(0.0F, (float) (MunRad/(MunMinmus*1)))*180;
    		mc.renderEngine.bindTexture(this.moonTexture);
    		tessellator.startDrawingQuads();
    		tessellator.addVertexWithUV(-f10, 100.0D, -f10, 0.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, 100.0D, -f10, 1.0D, 0.0D);
    		tessellator.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
    		tessellator.addVertexWithUV(-f10, 100.0D, f10, 0.0D, 1.0D);
    		tessellator.draw();
    		GL11.glEnable(GL11.GL_BLEND);
    		GL11.glPopMatrix();
		}
		{
			OpenGlHelper.glBlendFunc(770, 1, 1, 0);

			float brightness = (float) Math.sin(world.getCelestialAngle(partialTicks) * Math.PI);
			brightness *= brightness;

			GL11.glPushMatrix();
			GL11.glColor4f(brightness, brightness, brightness, brightness);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);

			FMLClientHandler.instance().getClient().renderEngine.bindTexture(digammaStar);

			float digamma = HbmLivingProps.getDigamma(Minecraft.getMinecraft().thePlayer);
			float var12 = 1F * (1 + digamma * 0.25F);
			double dist = 100D - digamma * 2.5;

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-var12, dist, -var12, 0.0D, 0.0D);
			tessellator.addVertexWithUV(var12, dist, -var12, 0.0D, 1.0D);
			tessellator.addVertexWithUV(var12, dist, var12, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-var12, dist, var12, 1.0D, 0.0D);
			tessellator.draw();
			GL11.glPopMatrix();

		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

		if(d0 < 0.0D) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(this.glSkyList2);
			GL11.glPopMatrix();
			f8 = 1.0F;
			f9 = -((float) (d0 + 65.0D));
			f10 = -f8;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, -f8);
			tessellator.addVertex(-f8, f10, f8);
			tessellator.addVertex(f8, f10, f8);
			tessellator.addVertex(f8, f10, -f8);
			tessellator.draw();
		}

		if(world.provider.isSkyColored()) {
			GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
		} else {
			GL11.glColor3f(f1, f2, f3);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
		GL11.glCallList(this.glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);

	}

	private void renderStars() {
		Random random = new Random(10842L);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		for(int i = 0; i < 1500; ++i) {
			double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;

			if(d4 < 1.0D && d4 > 0.01D) {
				d4 = 1.0D / Math.sqrt(d4);
				d0 *= d4;
				d1 *= d4;
				d2 *= d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * Math.PI * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for(int j = 0; j < 4; ++j) {
					double d17 = 0.0D;
					double d18 = (double) ((j & 2) - 1) * d3;
					double d19 = (double) ((j + 1 & 2) - 1) * d3;
					double d20 = d18 * d16 - d19 * d15;
					double d21 = d19 * d16 + d18 * d15;
					double d22 = d20 * d12 + d17 * d13;
					double d23 = d17 * d12 - d20 * d13;
					double d24 = d23 * d9 - d21 * d10;
					double d25 = d21 * d9 + d23 * d10;
					tessellator.addVertex(d5 + d24, d6 + d22, d7 + d25);
				}
			}
		}
		tessellator.draw();
	}
	
	  private void renderSkyboxSide(Tessellator tessellator, int side)
	  {
	    double u = side % 3 / 3.0D;
	    double v = side / 3 / 2.0D;
	    tessellator.startDrawingQuads();
	    tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, u, v);
	    tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, u, v + 0.5D);
	    tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, u + 0.3333333333333333D, v + 0.5D);
	    tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, u + 0.3333333333333333D, v);
	    tessellator.draw();
	  }

}