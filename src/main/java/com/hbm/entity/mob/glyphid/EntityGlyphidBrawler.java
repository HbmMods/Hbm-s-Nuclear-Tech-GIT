package com.hbm.entity.mob.glyphid;


import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.main.ResourceManager;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class EntityGlyphidBrawler extends EntityGlyphid {

	public EntityGlyphidBrawler(World world) {
		super(world);
		this.setSize(2F, 1.125F);
	}

	public int timer = 0;
	protected Entity lastTarget;
	protected double lastX;
	protected double lastY;
	protected double lastZ;

	@Override
	public void onUpdate(){
		super.onUpdate();
		Entity e = this.getEntityToAttack();
		if (e != null && this.isEntityAlive()) {

			this.lastX = e.posX;
			this.lastY = e.posY;
			this.lastZ = e.posZ;

			if (--timer <= 0) {
				leap();
				timer = 80 + worldObj.rand.nextInt(30);
			}
		}
	}

	/** Mainly composed of repurposed bombardier code**/
	public void leap() {
		if (!worldObj.isRemote && entityToAttack instanceof EntityLivingBase && this.getDistanceToEntity(entityToAttack) < 20) {
			Entity e = this.getEntityToAttack();

			double velX = e.posX - lastX;
			double velY = e.posY - lastY;
			double velZ = e.posZ - lastZ;

			if (this.lastTarget != e) {
				velX = velY = velZ = 0;
			}

			int prediction = 60;
			Vec3 delta = Vec3.createVectorHelper(e.posX - posX + velX * prediction, (e.posY + e.height / 2) - (posY + 1) + velY * prediction, e.posZ - posZ + velZ * prediction);
			double len = delta.lengthVector();
			if (len < 3) return;
			double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);

			double x = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
			double y = delta.yCoord;
			double v0 = 1.5;
			double v02 = v0 * v0;
			double g = 0.01;
			double targetPitch = Math.atan((v02 + Math.sqrt(v02 * v02 - g * (g * x * x + 2 * y * v02)) * 1) / (g * x));
			Vec3 fireVec = null;
			if (!Double.isNaN(targetPitch)) {

				fireVec = Vec3.createVectorHelper(v0, 0, 0);
				fireVec.rotateAroundZ((float) (-targetPitch / 3.5));
				fireVec.rotateAroundY((float) -(targetYaw + Math.PI * 0.5));
			}
			if (fireVec != null)
				this.setThrowableHeading(fireVec.xCoord, fireVec.yCoord, fireVec.zCoord, (float) v0, rand.nextFloat());
		}
	}
	//yeag this is now a motherfucking projectile
	public void setThrowableHeading(double motionX, double motionY, double motionZ, float velocity, float inaccuracy) {
		float throwLen = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX /= (double) throwLen;
		motionY /= (double) throwLen;
		motionZ /= (double) throwLen;
		motionX += this.rand.nextGaussian() * 0.0075D * (double) inaccuracy;
		motionY += this.rand.nextGaussian() * 0.0075D * (double) inaccuracy;
		motionZ += this.rand.nextGaussian() * 0.0075D * (double) inaccuracy;
		motionX *= (double) velocity;
		motionY *= (double) velocity;
		motionZ *= (double) velocity;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		float hyp = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(motionY, (double) hyp) * 180.0D / Math.PI);
	}
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_brawler_tex;
	}

	@Override
	public double getScale() {
		return 1.25D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getBrawler().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getBrawler().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getBrawler().damage);
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsBrawler;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		/*NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "debug");
		data.setInteger("color", 0x0000ff);
		data.setFloat("scale", 2.5F);
		data.setString("text", "" + (int) amount);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 2, posZ), new TargetPoint(dimension, posX, posY + 2, posZ, 50));*/
		//allows brawlers to get no damage on short leaps, but still affected by fall damage on big drops
		if(source == DamageSource.fall && amount <= 10) return false;
		return super.attackEntityFrom(source, amount);
	}
	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.25, 2), 100);
	}
}
