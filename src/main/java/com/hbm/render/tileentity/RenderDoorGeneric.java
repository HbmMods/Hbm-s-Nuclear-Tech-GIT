package com.hbm.render.tileentity;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimatedModel;
import com.hbm.animloader.Animation;
import com.hbm.animloader.AnimationWrapper;
import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.blocks.BlockDummyable;
import com.hbm.render.loader.IModelCustomNamed;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.TileEntityDoorGeneric;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderDoorGeneric extends TileEntitySpecialRenderer {
	
	private static DoubleBuffer buf = null;
	
	private static final float[] tran = new float[3];
	private static final float[] orig = new float[3];
	private static final float[] rot = new float[3];
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks){
		
		TileEntityDoorGeneric te = (TileEntityDoorGeneric) tile;
		
		if(buf == null){
			buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		}
		DoorDecl door = te.getDoorType();
		
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y, z+0.5);
		
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0+90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90+90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180+90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270+90, 0F, 1F, 0F); break;
		}
		door.doOffsetTransform();
		
		double[][] clip = door.getClippingPlanes();
		for(int i = 0; i < clip.length; i ++){
			GL11.glEnable(GL11.GL_CLIP_PLANE0+i);
			buf.put(clip[i]);
			buf.rewind();
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0+i, buf);
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		AnimatedModel animModel = door.getAnimatedModel();
		if(animModel != null){
			Animation anim = door.getAnim();
			bindTexture(door.getTextureForPart(te.getSkinIndex(), ""));
			long time = System.currentTimeMillis();
			long startTime = te.state > 1 ? te.animStartTime : time;
			boolean reverse = te.state == 1 || te.state == 2;
			AnimationWrapper w = new AnimationWrapper(startTime, anim).onEnd(new EndResult(EndType.STAY));
			if(reverse)
				w.reverse();
			animModel.controller.setAnim(w);
			animModel.renderAnimated(System.currentTimeMillis());
		} else {
			IModelCustomNamed model = door.getModel();
			
			long ms = System.currentTimeMillis()-te.animStartTime;
			float openTicks = MathHelper.clamp_float(te.state == 2 || te.state == 0 ? door.timeToOpen()*50-ms : ms, 0, door.timeToOpen()*50)*0.02F;

			for(String partName : model.getPartNames()) {
				if(!door.doesRender(partName, false))
					continue;
				
				GL11.glPushMatrix();
				{
					bindTexture(door.getTextureForPart(te.getSkinIndex(), partName));
					doPartTransform(door, partName, openTicks, false);
					model.renderPart(partName);

					for(String innerPartName : door.getChildren(partName)) {
						if(!door.doesRender(innerPartName, true))
							continue;
						
						GL11.glPushMatrix();
						{
							bindTexture(door.getTextureForPart(te.getSkinIndex(), innerPartName));
							doPartTransform(door, innerPartName, openTicks, true);
							model.renderPart(innerPartName);
						}
						GL11.glPopMatrix();
					}
				}
				GL11.glPopMatrix();
			}
		}
		
		for(int i = 0; i < clip.length; i ++){
			GL11.glDisable(GL11.GL_CLIP_PLANE0+i);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	public void doPartTransform(DoorDecl door, String name, float openTicks, boolean child){
		door.getTranslation(name, openTicks, child, tran);
		door.getOrigin(name, orig);
		door.getRotation(name, openTicks, rot);
		GL11.glTranslated(orig[0], orig[1], orig[2]);
		if(rot[0] != 0)
			GL11.glRotated(rot[0], 1, 0, 0);
		if(rot[1] != 0)
			GL11.glRotated(rot[1], 0, 1, 0);
		if(rot[2] != 0)
			GL11.glRotated(rot[2], 0, 0, 1);
		GL11.glTranslated(-orig[0]+tran[0], -orig[1]+tran[1], -orig[2]+tran[2]);
	}
}
