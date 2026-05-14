package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Vec3NT;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemRenderMK108 extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		
		standardAimingTransform(stack,
				-1F * offset, -1.5F * offset, 2.5F * offset,
				-0.75F, -0.75F, 1.5F);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mk108_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		
		boolean doesCycle = HbmAnimations.getRelevantAnim(0) != null && HbmAnimations.getRelevantAnim(0).animation.getBus("CYCLE") != null;
		boolean reloading = HbmAnimations.getRelevantAnim(0) != null && HbmAnimations.getRelevantAnim(0).animation.getBus("BELT") != null;
		boolean useShellCount = HbmAnimations.getRelevantAnim(0) != null && HbmAnimations.getRelevantAnim(0).animation.getBus("SHELLS") != null;
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] cycle = HbmAnimations.getRelevantTransformation("CYCLE");
		double[] barrel = HbmAnimations.getRelevantTransformation("BARREL");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] lid = HbmAnimations.getRelevantTransformation("LID");
		double[] belt = HbmAnimations.getRelevantTransformation("BELT");
		double[] drum = HbmAnimations.getRelevantTransformation("DRUM");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] shellCount = HbmAnimations.getRelevantTransformation("SHELLS");
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glTranslated(0, -1, -8);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 8);
		
		GL11.glTranslated(0, 1, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, -1, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		ResourceManager.mk108.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, barrel[2] * 2);
		ResourceManager.mk108.renderPart("Barrel");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.6875, -1);
		GL11.glRotated(lid[0], 1, 0, 0);
		GL11.glTranslated(0, -0.6875, 1);
		ResourceManager.mk108.renderPart("Lid");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();

		GL11.glTranslated(drum[0], drum[1], drum[2]);
		ResourceManager.mk108.renderPart("Drum");
		
		double p = 0.0625D;
		double x = p * 22;
		double y = p * -46;
		double angle = 0;
		Vec3NT vec = new Vec3NT(0, 0.53125, 0);

		double[] anglesLoaded = new double[]   {0,   0,  -5,   0,   -5,  60,  45,  -10,   0};
		double[] anglesUnloaded = new double[] {0, -30, -60, -45, -45,   0,   0,   0,   0};
		double[][] shells = new double[anglesLoaded.length][3];
		double reloadProgress = !reloading ? 1D : belt[0];
		double cycleProgress = !doesCycle ? 1 : cycle[0];
		
		for(int i = 0; i < anglesLoaded.length; i++) {
			shells[i][0] = x;
			shells[i][1] = y;
			shells[i][2] = angle - 90;
			double delta = BobMathUtil.interp(anglesUnloaded[i], anglesLoaded[i], reloadProgress);
			angle += delta;
			vec.rotateAroundZDeg(-delta);
			x += vec.xCoord;
			y += vec.yCoord;
		}
		
		int shellAmount = useShellCount ? (int) shellCount[0] : gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null);
		
		// draw belt, interp used for cycling (shells will transform towards the position/rotation of the next shell)
		for(int i = 0; i < shells.length - 1; i++) {
			double[] prevShell = shells[i];
			double[] nextShell = shells[i + 1];
			renderShell(prevShell[0], nextShell[0], prevShell[1], nextShell[1], prevShell[2], nextShell[2], shells.length - i < shellAmount + 2, cycleProgress);
		}
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8.125);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 50, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.0D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(1, -2.5, 4);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 0.5, 0.25);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -9.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0.5, -0.25);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type, Object... data) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mk108_tex);
		ResourceManager.mk108.renderPart("Gun");
		ResourceManager.mk108.renderPart("Barrel");
		ResourceManager.mk108.renderPart("Lid");
		ResourceManager.mk108.renderPart("Drum");
		
		GL11.glPushMatrix();
		
		double p = 0.0625D;
		double x = p * 22;
		double y = p * -46;
		double angle = 0;
		Vec3NT vec = new Vec3NT(0, 0.53125, 0);

		double[] anglesLoaded = new double[] { 0, 0, -5, 0, -5, 60, 45, -10, 0 };
		double[][] shells = new double[anglesLoaded.length][3];
		
		for(int i = 0; i < anglesLoaded.length; i++) {
			shells[i][0] = x;
			shells[i][1] = y;
			shells[i][2] = angle - 90;
			double delta = anglesLoaded[i];
			angle += delta;
			vec.rotateAroundZDeg(-delta);
			x += vec.xCoord;
			y += vec.yCoord;
		}
		
		// draw belt, interp used for cycling (shells will transform towards the position/rotation of the next shell)
		for(int i = 0; i < shells.length - 1; i++) {
			double[] prevShell = shells[i];
			double[] nextShell = shells[i + 1];
			renderShell(prevShell[0], nextShell[0], prevShell[1], nextShell[1], prevShell[2], nextShell[2], true, 0F);
		}
		GL11.glPopMatrix();
		
		if(type == ItemRenderType.EQUIPPED) {
			EntityLivingBase ent = (EntityLivingBase) data[1];
			long shot;
			double shotRand = 0;
			if(ent == Minecraft.getMinecraft().thePlayer) {
				ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
				shot = gun.lastShot[0];
				shotRand = gun.shotRand;
			} else {
				shot = ItemRenderWeaponBase.flashMap.getOrDefault(ent, (long) -1);
				if(shot < 0) return;
			}
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 8.125);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(90 * shotRand, 1, 0, 0);
			this.renderMuzzleFlash(shot, 50, 5);
			GL11.glPopMatrix();
		}
	}
	
	public static void renderShell(double x0, double x1, double y0, double y1, double rot0, double rot1, boolean shell, double interp) {
		renderShell(BobMathUtil.interp(x0, x1, interp), BobMathUtil.interp(y0, y1, interp), BobMathUtil.interp(rot0, rot1, interp), shell);
	}
	
	public static void renderShell(double x, double y, double rot, boolean shell) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glRotated(rot, 0, 0, 1);
		ResourceManager.mk108.renderPart("Belt");
		if(shell) ResourceManager.mk108.renderPart("Grenade");
		GL11.glPopMatrix();
	}
}
