package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTapeRecorder extends ModelBase
{
    ModelRenderer Base;
    ModelRenderer Tape;
    ModelRenderer Part1;
    ModelRenderer Part2;
  
  public ModelTapeRecorder()
  {
    textureWidth = 128;
    textureHeight = 64;
    
    Base = new ModelRenderer(this, 0, 0);
    Base.addBox(0F, 0F, 0F, 16, 16, 12);
    Base.setRotationPoint(-8F, 8F, -4F);
    Base.setTextureSize(128, 64);
    Base.mirror = true;
    setRotation(Base, 0F, 0F, 0F);
    Tape = new ModelRenderer(this, 0, 28);
    Tape.addBox(0F, 0F, 0F, 8, 0, 2);
    Tape.setRotationPoint(-4F, 11F, -6F);
    Tape.setTextureSize(128, 64);
    Tape.mirror = true;
    setRotation(Tape, 0F, 0F, 0F);
    Part1 = new ModelRenderer(this, 9, 42);
    Part1.addBox(0F, 0F, 0F, 6, 6, 2);
    Part1.setRotationPoint(-7F, 11F, -6F);
    Part1.setTextureSize(128, 64);
    Part1.mirror = true;
    setRotation(Part1, 0F, 0F, 0F);
    Part2 = new ModelRenderer(this, 44, 42);
    Part2.addBox(0F, 0F, 0F, 6, 6, 2);
    Part2.setRotationPoint(1F, 11F, -6F);
    Part2.setTextureSize(128, 64);
    Part2.mirror = true;
    setRotation(Part2, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Base.render(f5);
    Tape.render(f5);
    Part1.render(f5);
    Part2.render(f5);
  }
  
  public void renderModel(float f)
  {
	    Base.render(f);
	    Tape.render(f);
	    Part1.render(f);
	    Part2.render(f);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
