package com.hbm.render.loader;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

public class ModelRendererTest extends ModelRenderer {
	
	//TODO: blow up mojank HQ with a JDAM
    private boolean compiled;
    private int displayList;

	public ModelRendererTest(ModelBase p_i1173_1_) {
		super(p_i1173_1_);
	}

	public ModelRendererTest(ModelBase p_i1174_1_, int p_i1174_2_, int p_i1174_3_) {
		this(p_i1174_1_);
		this.setTextureOffset(p_i1174_2_, p_i1174_3_);
	}

	@SideOnly(Side.CLIENT)
	public void render(float p_78785_1_) {
		if(!this.isHidden) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(p_78785_1_);
				}

				GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
				int i;

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
						GL11.glCallList(this.displayList);

						if(this.childModels != null) {
							for(i = 0; i < this.childModels.size(); ++i) {
								((ModelRenderer) this.childModels.get(i)).render(p_78785_1_);
							}
						}
					} else {
						GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
						GL11.glCallList(this.displayList);

						if(this.childModels != null) {
							for(i = 0; i < this.childModels.size(); ++i) {
								((ModelRenderer) this.childModels.get(i)).render(p_78785_1_);
							}
						}

						GL11.glTranslatef(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
					}
				} else {
					GL11.glPushMatrix();
					GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);

					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					
					GL11.glPushMatrix();
					GL11.glScaled(0.1, 0.1, 0.1);
					ResourceManager.chemplant_body.renderAll();
					GL11.glPopMatrix();

					//GL11.glCallList(this.displayList);

					if(this.childModels != null) {
						for(i = 0; i < this.childModels.size(); ++i) {
							((ModelRenderer) this.childModels.get(i)).render(p_78785_1_);
						}
					}

					GL11.glPopMatrix();
				}

				GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void renderWithRotation(float p_78791_1_) {
		if(!this.isHidden) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(p_78791_1_);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);

				if(this.rotateAngleY != 0.0F) {
					GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if(this.rotateAngleX != 0.0F) {
					GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}

				if(this.rotateAngleZ != 0.0F) {
					GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				GL11.glCallList(this.displayList);
				GL11.glPopMatrix();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void postRender(float p_78794_1_) {
		if(!this.isHidden) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(p_78794_1_);
				}

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
						GL11.glTranslatef(this.rotationPointX * p_78794_1_, this.rotationPointY * p_78794_1_, this.rotationPointZ * p_78794_1_);
					}
				} else {
					GL11.glTranslatef(this.rotationPointX * p_78794_1_, this.rotationPointY * p_78794_1_, this.rotationPointZ * p_78794_1_);

					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private void compileDisplayList(float p_78788_1_) {
		this.displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.instance;

		for(int i = 0; i < this.cubeList.size(); ++i) {
			((ModelBox) this.cubeList.get(i)).render(tessellator, p_78788_1_);
		}

		GL11.glEndList();
		this.compiled = true;
	}
}
