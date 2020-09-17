package com.hbm.particle;

public class ParticleDefinition {
	
	//the amount of particle sprites along the height and width of the sheet
	public int sheetWidth = 1;
	public int sheetHeight = 1;
	//the tint of the particle which will be interpolated
	public int startTint = 0xffffff;
	public int endTint = 0xffffff;
	//the scale of the particle which will be interpolated
	public double startScale = 1.0D;
	public double endScale = 1.0D;
	//uniformly distributed random lifetime
	public int minAge = 100;
	public int maxAge = 120;
	
	//whether GL11 blend should be turned on
	public boolean doesBlend = false;

	public ParticleDefinition setSize(int width, int height) {
		
		this.sheetWidth = width;
		this.sheetHeight = height;
		return this;
	}
	
	public ParticleDefinition setTint(int tint) {
		
		return this.setTint(tint, tint);
	}
	
	public ParticleDefinition setTint(int start, int end) {
		
		this.startTint = start;
		this.endTint = end;
		return this;
	}
	
	public ParticleDefinition setScale(double tint) {
		
		return this.setScale(tint, tint);
	}
	
	public ParticleDefinition setScale(double start, double end) {
		
		this.startScale = start;
		this.endScale = end;
		return this;
	}
}
