package com.hbm.tileentity;

import com.hbm.animloader.AnimatedModel;
import com.hbm.animloader.Animation;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.IModelCustomNamed;
import com.hbm.util.BobMathUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public abstract class DoorDecl {

	public static final DoorDecl TRANSITION_SEAL = new DoorDecl() {

		@Override
		public String getOpenSoundStart() {
			return "hbm:door.TransitionSealOpen";
		}

		@Override
		public float getSoundVolume() {
			return 6;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(!partName.equals("base")) {
				set(trans, 0, 3.5F * getNormTime(openTicks), 0);
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0, 0, 0.5);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return super.getClippingPlanes();
		}

		@Override
		public int timeToOpen() {
			return 480;
		}

		@Override
		public int[][] getDoorOpenRanges() {
			// 3 is tall
			// 4 is wide
			return new int[][] { { -9, 2, 0, 20, 20, 1 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 23, 0, 0, 0, 13, 12 };
		}

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			return super.getBlockBound(x, y, z, open);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.transition_seal_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Animation getAnim() {
			return ResourceManager.transition_seal_anim;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public AnimatedModel getAnimatedModel() {
			return ResourceManager.transition_seal;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return null;
		}
	};

	public static final DoorDecl FIRE_DOOR = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.wghStop";
		}

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.wghStart";
		}

		@Override
		public String getSoundLoop2() {
			return "hbm:door.alarm6";
		}

		@Override
		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(!partName.equals("frame")) {
				set(trans, 0, 3 * getNormTime(openTicks), 0);
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0, 0, 0.5);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { 0, -1, 0, 3.0001 } };
		}

		@Override
		public int timeToOpen() {
			return 160;
		}

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { -1, 0, 0, 3, 4, 1 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 2, 0, 0, 0, 2, 1 };
		}

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open)
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
			if(z == 1) {
				return AxisAlignedBB.getBoundingBox(0.5, 0, 0, 1, 1, 1);
			} else if(z == -2) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 0.5, 1, 1);
			} else if(y > 1) {
				return AxisAlignedBB.getBoundingBox(0, 0.75, 0, 1, 1, 1);
			} else if(y == 0) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.1, 1);
			} else {
				return super.getBlockBound(x, y, z, open);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.fire_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.fire_door;
		}
	};

	public static final DoorDecl SLIDE_DOOR = new DoorDecl() {
		
		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.sliding_door_opened";
		}

		@Override
		public String getCloseSoundEnd() {
			return "hbm:door.sliding_door_shut";
		}

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.sliding_door_opening";
		}

		@Override
		public String getSoundLoop2() {
			return "hbm:door.sliding_door_opening";
		}

		@Override
		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glRotated(-90, 0, 1, 0);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { -1, 0, 0, 3.50001 }, { 1, 0, 0, 3.50001 } };
		}

		@Override
		public int timeToOpen() {
			return 24;
		}

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { -2, 0, 0, 4, 5, 1 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 3, 0, 0, 0, 3, 3 };
		}

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(open) {
				if(y == 3) {
					return AxisAlignedBB.getBoundingBox(0, 0.5, 0, 1, 1, 1);
				} else if(y == 0) {
					return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.08, 1);
				}
			}
			return super.getBlockBound(x, y, z, open);
		}

		@Override
		public boolean hasSkins() {
			return true;
		}

		@Override
		public int getSkinCount() {
			return 3;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return new ResourceLocation[] { ResourceManager.sliding_blast_door_tex, ResourceManager.sliding_blast_door_variant1_tex, ResourceManager.sliding_blast_door_variant2_tex }[skinIndex];
		}

		@Override
		public IModelCustomNamed getModel() {
			return null;
		}

		@Override
		public Animation getAnim() {
			return ResourceManager.sliding_blast_door_anim;
		}

		@Override
		public AnimatedModel getAnimatedModel() {
			return ResourceManager.sliding_blast_door;
		}
	};

	public static final DoorDecl SLIDING_SEAL_DOOR = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.sliding_seal_stop";
		}

		@Override
		public String getOpenSoundStart() {
			return "hbm:door.sliding_seal_open";
		}

		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(partName.startsWith("door")) {
				set(trans, 0, 0, Library.smoothstep(getNormTime(openTicks), 0, 1));
			} else {
				set(trans, 0, 0, 0);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { 0, 0, -1, 0.5001 } };
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0.375, 0, 0);
		};

		@Override
		public int timeToOpen() {
			return 20;
		};

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(open) {
				if(y == 0) return AxisAlignedBB.getBoundingBox(0, 0, 1 - 0.25, 1, 0, 1);
				return AxisAlignedBB.getBoundingBox(0, 0.9375, 1 - 0.25, 1, 1, 1);
			} else {
				return AxisAlignedBB.getBoundingBox(0, 0, 1 - 0.25, 1, 1, 1);
			}
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { 0, 0, 0, 1, 2, 2 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 1, 0, 0, 0, 0, 0 };
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.sliding_seal_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.sliding_seal_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.sliding_seal_door;
		}
	};

	public static final DoorDecl SECURE_ACCESS_DOOR = new DoorDecl() {

		@Override
		public String getCloseSoundLoop() {
			return "hbm:door.garage_move";
		}

		@Override
		public String getCloseSoundEnd() {
			return "hbm:door.garage_stop";
		}

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.garage_stop";
		}

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.garage_move";
		}

		@Override
		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(!partName.equals("base")) {
				set(trans, 0, 3.5F * getNormTime(openTicks), 0);
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glRotated(90, 0, 1, 0);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { 0, -1, 0, 5 } };
		};

		@Override
		public int timeToOpen() {
			return 120;
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { -2, 1, 0, 4, 5, 1 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 4, 0, 0, 0, 2, 2 };
		}

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open) {
				if(y > 0) {
					return AxisAlignedBB.getBoundingBox(0, 0, 0.375, 1, 1, 0.625);
				}
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
			}
			if(y == 1) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.0625, 1);
			} else if(y == 4) {
				return AxisAlignedBB.getBoundingBox(0, 0.5, 0.15, 1, 1, 0.85);
			} else {
				return super.getBlockBound(x, y, z, open);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.secure_access_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.secure_access_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.secure_access_door;
		}
	};

	public static final DoorDecl ROUND_AIRLOCK_DOOR = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.garage_stop";
		}

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.garage_move";
		}

		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if("doorLeft".equals(partName)) {
				set(trans, 0, 0, 1.5F * getNormTime(openTicks));
			} else if("doorRight".equals(partName)) {
				set(trans, 0, 0, -1.5F * getNormTime(openTicks));
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
			return new double[][] { { 0.0, 0.0, 1.0, 2.0001 }, { 0.0, 0.0, -1.0, 2.0001 } };
		};

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open)
				return super.getBlockBound(x, y, z, open);
			if(z == 1) {
				return AxisAlignedBB.getBoundingBox(0.4, 0, 0, 1, 1, 1);
			} else if(z == -2) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 0.6, 1, 1);
			} else if(y == 3) {
				return AxisAlignedBB.getBoundingBox(0, 0.5, 0, 1, 1, 1);
			} else if(y == 0) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.0625, 1);
			}
			return super.getBlockBound(x, y, z, open);
		};

		@Override
		public int timeToOpen() {
			return 60;
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { 0, 0, 0, -2, 4, 2 }, { 0, 0, 0, 3, 4, 2 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 3, 0, 0, 0, 2, 1 };
		};

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.round_airlock_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.round_airlock_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.round_airlock_door;
		}
	};

	public static final DoorDecl QE_SLIDING = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.qe_sliding_opened";
		};

		@Override
		public String getCloseSoundEnd() {
			return "hbm:door.qe_sliding_shut";
		};

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.qe_sliding_opening";
		};

		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(partName.startsWith("left")) {
				set(trans, 0, 0, 0.99F * getNormTime(openTicks));
			} else {
				set(trans, 0, 0, -0.99F * getNormTime(openTicks));
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0.4375, 0, 0.5);
		};

		@Override
		public int timeToOpen() {
			return 10;
		};

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(open) {
				if(z == 0) {
					return AxisAlignedBB.getBoundingBox(1 - 0.125, 0, 1 - 0.125, 1, 1, 1);
				} else {
					return AxisAlignedBB.getBoundingBox(0, 0, 1 - 0.125, 0.125, 1, 1);
				}
			} else {
				return AxisAlignedBB.getBoundingBox(0, 0, 1 - 0.125, 1, 1, 1);
			}
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { 0, 0, 0, 2, 2, 2 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 1, 0, 0, 0, 1, 0 };
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.qe_sliding_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return getTextureForPart(partName);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.qe_sliding_door;
		}

	};

	public static final DoorDecl QE_CONTAINMENT = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.wgh_stop";
		};

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.wgh_start";
		};

		@Override
		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if(!partName.equals("frame")) {
				set(trans, 0, 3 * getNormTime(openTicks), 0);
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0.25, 0, 0);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { 0, -1, 0, 3.0001 } };
		};

		@Override
		public int timeToOpen() {
			return 160;
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { -1, 0, 0, 3, 3, 1 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 2, 0, 0, 0, 1, 1 };
		}

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open)
				return AxisAlignedBB.getBoundingBox(0, 0, 0.5, 1, 1, 1);
			if(y > 1)
				return AxisAlignedBB.getBoundingBox(0, 0.5, 0.5, 1, 1, 1);
			else if(y == 0)
				return AxisAlignedBB.getBoundingBox(0, 0, 0.5, 1, 0.1, 1);
			return super.getBlockBound(x, y, z, open);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			if(partName.equals("decal"))
				return ResourceManager.qe_containment_decal;
			return ResourceManager.qe_containment_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return getTextureForPart(partName);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.qe_containment;
		}

	};

	public static final DoorDecl WATER_DOOR = new DoorDecl() {

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.wgh_big_stop";
		};

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.wgh_big_start";
		};

		@Override
		public String getOpenSoundStart() {
			return "hbm:door.lever";
		};

		@Override
		public String getCloseSoundStart() {
			return null;
		};

		@Override
		public String getCloseSoundEnd() {
			return "hbm:door.lever";
		};

		@Override
		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if("bolt".equals(partName)) {
				set(trans, 0, 0, 0.4F * Library.smoothstep(getNormTime(openTicks, 0, 30), 0, 1));
			} else {
				set(trans, 0, 0, 0);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void doOffsetTransform() {
			GL11.glTranslated(0.375, 0, 0);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void getOrigin(String partName, float[] orig) {
			if("door".equals(partName) || "bolt".equals(partName)) {
				set(orig, 0.125F, 1.5F, 1.18F);
				return;
			} else if("spinny_upper".equals(partName)) {
				set(orig, 0.041499F, 2.43569F, -0.587849F);
				return;
			} else if("spinny_lower".equals(partName)) {
				set(orig, 0.041499F, 0.571054F, -0.587849F);
				return;
			}
			super.getOrigin(partName, orig);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void getRotation(String partName, float openTicks, float[] rot) {
			if(partName.startsWith("spinny")) {
				set(rot, Library.smoothstep(getNormTime(openTicks, 0, 30), 0, 1) * 360, 0, 0);
				return;
			} else if("door".equals(partName) || "bolt".equals(partName)) {
				set(rot, 0, Library.smoothstep(getNormTime(openTicks, 30, 60), 0, 1) * -134, 0);
				return;
			}
			super.getRotation(partName, openTicks, rot);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public boolean doesRender(String partName, boolean child) {
			return child || !partName.startsWith("spinny");
		};

		@Override
		@SideOnly(Side.CLIENT)
		public String[] getChildren(String partName) {
			if("door".equals(partName))
				return new String[] { "spinny_lower", "spinny_upper" };
			return super.getChildren(partName);
		};

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0.75, 1, 1, 1);
			} else if(y > 1) {
				return AxisAlignedBB.getBoundingBox(0, 0.85, 0.75, 1, 1, 1);
			} else if(y == 0) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0.75, 1, 0.15, 1);
			}
			return super.getBlockBound(x, y, z, open);
		};

		@Override
		public int timeToOpen() {
			return 60;
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { 1, 0, 0, -3, 3, 2 } };
		}

		public float getDoorRangeOpenTime(int ticks, int idx) {
			return getNormTime(ticks, 35, 40);
		};

		@Override
		public int[] getDimensions() {
			return new int[] { 2, 0, 0, 0, 1, 1 };
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.water_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.water_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.water_door;
		}

	};

	public static final DoorDecl SILO_HATCH = new DoorDecl() {

		@Override public String getOpenSoundEnd() { return "hbm:door.wgh_big_stop"; };
		@Override public String getOpenSoundLoop() { return "hbm:door.wgh_big_start"; };
		@Override public String getOpenSoundStart() { return null; };
		@Override public String getCloseSoundStart() { return null; };
		@Override public String getCloseSoundEnd() { return "hbm:door.wgh_big_stop"; };
		@Override public float getSoundVolume() { return 2; }
		@Override public boolean remoteControllable() { return true; }

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if("Hatch".equals(partName)) {
				set(trans, 0, 0.25F * Library.smoothstep(getNormTime(openTicks, 0, 10), 0, 1), 0);
			} else {
				set(trans, 0, 0, 0);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void getOrigin(String partName, float[] orig) {
			if("Hatch".equals(partName)) {
				set(orig, 0F, 0.875F, -1.875F);
				return;
			}
			set(orig, 0, 0, 0);
			super.getOrigin(partName, orig);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void getRotation(String partName, float openTicks, float[] rot) {
			if("Hatch".equals(partName)) {
				set(rot, Library.smoothstep(getNormTime(openTicks, 20, 100), 0, 1) * -240, 0, 0);
				return;
			}
			super.getRotation(partName, openTicks, rot);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public boolean doesRender(String partName, boolean child) {
			return true;
		};

		@Override public int timeToOpen() { return 60; };
		@Override public int[][] getDoorOpenRanges() { return new int[][] { { 1, 0, 1, -3, 3, 0 }, { 0, 0, 1, -3, 3, 0 }, { -1, 0, 1, -3, 3, 0 } }; }
		@Override public float getDoorRangeOpenTime(int ticks, int idx) { return getNormTime(ticks, 20, 20); };

		
		@Override public int getBlockOffset() { return 2; }
		@Override public int[] getDimensions() { return new int[] { 0, 0, 2, 2, 2, 2 }; }
		@Override @SideOnly(Side.CLIENT) public ResourceLocation getTextureForPart(String partName) { return ResourceManager.silo_hatch_tex; }
		@Override public ResourceLocation getTextureForPart(int skinIndex, String partName) { return ResourceManager.silo_hatch_tex; }
		@Override @SideOnly(Side.CLIENT) public IModelCustomNamed getModel() { return ResourceManager.silo_hatch; }

	};

	public static final DoorDecl SILO_HATCH_LARGE = new DoorDecl() {

		@Override public String getOpenSoundEnd() { return "hbm:door.wgh_big_stop"; };
		@Override public String getOpenSoundLoop() { return "hbm:door.wgh_big_start"; };
		@Override public String getOpenSoundStart() { return null; };
		@Override public String getCloseSoundStart() { return null; };
		@Override public String getCloseSoundEnd() { return "hbm:door.wgh_big_stop"; };
		@Override public float getSoundVolume() { return 2; }
		@Override public boolean remoteControllable() { return true; }

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if("Hatch".equals(partName)) {
				set(trans, 0, 0.25F * Library.smoothstep(getNormTime(openTicks, 0, 10), 0, 1), 0);
			} else {
				set(trans, 0, 0, 0);
			}
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void getOrigin(String partName, float[] orig) {
			if("Hatch".equals(partName)) {
				set(orig, 0F, 0.875F, -2.875F);
				return;
			}
			set(orig, 0, 0, 0);
			super.getOrigin(partName, orig);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public void getRotation(String partName, float openTicks, float[] rot) {
			if("Hatch".equals(partName)) {
				set(rot, Library.smoothstep(getNormTime(openTicks, 20, 100), 0, 1) * -240, 0, 0);
				return;
			}
			super.getRotation(partName, openTicks, rot);
		};

		@Override
		@SideOnly(Side.CLIENT)
		public boolean doesRender(String partName, boolean child) {
			return true;
		};

		@Override public int timeToOpen() { return 60; };
		@Override public int[][] getDoorOpenRanges() { return new int[][] { { 2, 0, 1, -3, 3, 0 }, { 1, 0, 2, -5, 3, 0 }, { 0, 0, 2, -5, 3, 0 }, { -1, 0, 2, -5, 3, 0 }, { -2, 0, 1, -3, 3, 0 } }; }
		@Override public float getDoorRangeOpenTime(int ticks, int idx) { return getNormTime(ticks, 20, 20); };

		
		@Override public int getBlockOffset() { return 3; }
		@Override public int[] getDimensions() { return new int[] { 0, 0, 3, 3, 3, 3 }; }
		@Override @SideOnly(Side.CLIENT) public ResourceLocation getTextureForPart(String partName) { return ResourceManager.silo_hatch_large_tex; }
		@Override public ResourceLocation getTextureForPart(int skinIndex, String partName) { return ResourceManager.silo_hatch_large_tex; }
		@Override @SideOnly(Side.CLIENT) public IModelCustomNamed getModel() { return ResourceManager.silo_hatch_large; }

	};

	public static final DoorDecl LARGE_VEHICLE_DOOR = new DoorDecl() {

		@Override
		@SideOnly(Side.CLIENT)
		public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
			if("doorLeft".equals(partName)) {
				set(trans, 0, 0, 3 * getNormTime(openTicks));
			} else if("doorRight".equals(partName)) {
				set(trans, 0, 0, -3 * getNormTime(openTicks));
			} else {
				super.getTranslation(partName, openTicks, child, trans);
			}
		};

		@Override
		public String getOpenSoundEnd() {
			return "hbm:door.garage_stop";
		}

		@Override
		public String getOpenSoundLoop() {
			return "hbm:door.garage_move";
		};

		public float getSoundVolume() {
			return 2;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double[][] getClippingPlanes() {
			return new double[][] { { 0.0, 0.0, 1.0, 3.50001 }, { 0.0, 0.0, -1.0, 3.50001 } };
		};

		@Override
		public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
			if(!open)
				return super.getBlockBound(x, y, z, open);
			if(z == 3) {
				return AxisAlignedBB.getBoundingBox(0.4, 0, 0, 1, 1, 1);
			} else if(z == -3) {
				return AxisAlignedBB.getBoundingBox(0, 0, 0, 0.6, 1, 1);
			}
			return super.getBlockBound(x, y, z, open);
		};

		@Override
		public int timeToOpen() {
			return 60;
		};

		@Override
		public int[][] getDoorOpenRanges() {
			return new int[][] { { 0, 0, 0, -4, 6, 2 }, { 0, 0, 0, 4, 6, 2 } };
		}

		@Override
		public int[] getDimensions() {
			return new int[] { 5, 0, 0, 0, 3, 3 };
		};

		@Override
		@SideOnly(Side.CLIENT)
		public ResourceLocation getTextureForPart(String partName) {
			return ResourceManager.large_vehicle_door_tex;
		}

		@Override
		public ResourceLocation getTextureForPart(int skinIndex, String partName) {
			return ResourceManager.large_vehicle_door_tex;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IModelCustomNamed getModel() {
			return ResourceManager.large_vehicle_door;
		}

	};

	// Format: x, y, z, tangent amount 1 (how long the door would be if it moved
	// up), tangent amount 2 (door places blocks in this direction), axis (0-x,
	// 1-y, 2-z)
	public abstract int[][] getDoorOpenRanges();

	public abstract int[] getDimensions();
	
	public int getBlockOffset() {
		return 0;
	}

	public boolean remoteControllable() {
		return false;
	}

	public float getDoorRangeOpenTime(int ticks, int idx) {
		return getNormTime(ticks);
	}

	public int timeToOpen() {
		return 20;
	}

	public float getNormTime(float time) {
		return getNormTime(time, 0, timeToOpen());
	}

	public float getNormTime(float time, float min, float max) {
		return BobMathUtil.remap01_clamp(time, min, max);
	}

	public boolean hasSkins() {
		return false;
	}

	public int getSkinCount() {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getTextureForPart(String partName) {
		return getTextureForPart(0, partName);
	}

	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTextureForPart(int skinIndex, String partName);

	@SideOnly(Side.CLIENT)
	public abstract IModelCustomNamed getModel();

	@SideOnly(Side.CLIENT)
	public AnimatedModel getAnimatedModel() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public Animation getAnim() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public void getTranslation(String partName, float openTicks, boolean child, float[] trans) {
		set(trans, 0, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	public void getRotation(String partName, float openTicks, float[] rot) {
		set(rot, 0, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	public void getOrigin(String partName, float[] orig) {
		set(orig, 0, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean doesRender(String partName, boolean child) {
		return true;
	}

	private static final String[] nothing = new String[] {};

	@SideOnly(Side.CLIENT)
	public String[] getChildren(String partName) {
		return nothing;
	}

	@SideOnly(Side.CLIENT)
	public double[][] getClippingPlanes() {
		return new double[][] {};
	}

	@SideOnly(Side.CLIENT)
	public void doOffsetTransform() {
	}

	public AxisAlignedBB getBlockBound(int x, int y, int z, boolean open) {
		return open ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

	public boolean isLadder(boolean open) {
		return false;
	}

	public String getOpenSoundLoop() {
		return null;
	}

	// Hack
	public String getSoundLoop2() {
		return null;
	}

	public String getCloseSoundLoop() {
		return getOpenSoundLoop();
	}

	public String getOpenSoundStart() {
		return null;
	}

	public String getCloseSoundStart() {
		return getOpenSoundStart();
	}

	public String getOpenSoundEnd() {
		return null;
	}

	public String getCloseSoundEnd() {
		return getOpenSoundEnd();
	}

	public float getSoundVolume() {
		return 1;
	}

	public float[] set(float[] f, float x, float y, float z) {
		f[0] = x;
		f[1] = y;
		f[2] = z;
		return f;
	}
}
