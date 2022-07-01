package com.hbm.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimatedModel;
import com.hbm.animloader.Animation;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.WavefrontObjDisplayList;
import com.hbm.util.BobMathUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

public abstract class DoorDecl {
	
public static final DoorDecl TRANSITION_SEAL = new DoorDecl(){
		
		@Override
		public String getOpenSoundStart() {
			return "hbm:door.TransitionSealOpen";
		};
		
		@Override
		public float getSoundVolume(){
			return 6;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(!partName.equals("base")){
				set(trans, 0, 3.5F*getNormTime(openTicks), 0);
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		};
		
		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0, 0, 0.5);
		};
		
		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return super.getClippingPlanes();
		};
		
		@Override
		public int timeToOpen() {
			return 480;
		};
		
		@Override
		public int[][] getDoorOpenRanges(){
			//3 is tall
			//4 is wide
			return new int[][]{{-9, 2, 0, 20, 20, 1}};
		}

		@Override
		public int[] getDimensions(){
			return new int[]{23, 0, 0, 0, 13, 12};
		}
		
		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			return super.getBlockBound(x, y, z, open);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName){
			return ResourceManager.transition_seal_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Animation getAnim() {
			return ResourceManager.transition_seal_anim;
		};
		
		@Override
		@SideOnly(Side.CLIENT)
		public AnimatedModel getAnimatedModel() {
			return ResourceManager.transition_seal;
		};
		
		@Override
		@SideOnly(Side.CLIENT)
		public WavefrontObjDisplayList getModel(){
			return null;
		}
	};

	//Format: x, y, z, tangent amount 1 (how long the door would be if it moved up), tangent amount 2 (door places blocks in this direction), axis (0-x, 1-y, 2-z)
	public abstract int[][] getDoorOpenRanges();
	
	public abstract int[] getDimensions();
	
	public float getDoorRangeOpenTime(int ticks, int idx){
		return getNormTime(ticks);
	}
	
	public int timeToOpen(){
		return 20;
	}
	
	public float getNormTime(float time){
		return getNormTime(time, 0, timeToOpen());
	}
	
	public float getNormTime(float time, float min, float max){
		return BobMathUtil.remap01_clamp(time, min, max);
	}
	
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTextureForPart(String partName);
	
	@SideOnly(Side.CLIENT)
	public abstract WavefrontObjDisplayList getModel();
	
	@SideOnly(Side.CLIENT)
	public AnimatedModel getAnimatedModel(){
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public Animation getAnim(){
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public void getTranslation(String partName, float openTicks, boolean child, float[] trans){
		set(trans, 0, 0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void getRotation(String partName, float openTicks, float[] rot){
		set(rot, 0, 0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void getOrigin(String partName, float[] orig){
		set(orig, 0, 0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean doesRender(String partName, boolean child){
		return true;
	}
	
	private static final String[] nothing = new String[]{};
	
	@SideOnly(Side.CLIENT)
	public String[] getChildren(String partName){
		return nothing;
	}
	
	@SideOnly(Side.CLIENT)
	public double[][] getClippingPlanes(){
		return new double[][]{};
	}
	
	@SideOnly(Side.CLIENT)
	public void doOffsetTransform(){
	}
	
	public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open){
		return open ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}
	
	public boolean isLadder(boolean open){
		return false;
	}
	
	public String getOpenSoundLoop(){
		return null;
	}
	
	//Hack
	public String getSoundLoop2(){
		return null;
	}
	
	public String getCloseSoundLoop(){
		return getOpenSoundLoop();
	}
	
	public String getOpenSoundStart(){
		return null;
	}
	
	public String getCloseSoundStart(){
		return getOpenSoundStart();
	}
	
	public String getOpenSoundEnd(){
		return null;
	}
	
	public String getCloseSoundEnd(){
		return getOpenSoundEnd();
	}
	
	public float getSoundVolume(){
		return 1;
	}
	
	public float[] set(float[] f, float x, float y, float z){
		f[0] = x;
		f[1] = y;
		f[2] = z;
		return f;
	};
}
