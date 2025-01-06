package com.hbm.entity.mob.glyphid;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class EntityGlyphidDigger extends EntityGlyphid {
	protected Entity lastTarget;
	protected double lastX;
	protected double lastY;
	protected double lastZ;

	public EntityGlyphidDigger(World world) {
		super(world);
	}

	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_digger_tex;
	}

	@Override
	public double getScale() {
		return 1.3D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getDigger().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getDigger().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getDigger().damage);
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsDigger;
	}

	public int timer = 0;
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		Entity e = this.getEntityToAttack();
		if (e != null && this.isEntityAlive()) {

			this.lastX = e.posX;
			this.lastY = e.posY;
			this.lastZ = e.posZ;

			if (--timer <= 0) {
				groundSlam();
				timer = 120;
			}
		}
	}

	/** Mainly composed of crusty old power fist code, with some touch ups **/
	public void groundSlam(){
		if (!worldObj.isRemote && entityToAttack instanceof EntityLivingBase && this.getDistanceToEntity(entityToAttack) < 30) {
			Entity e = this.getEntityToAttack();

			boolean topAttack = false;

			int l = 6;
			float part = -1F / 16F;

			int bugX = (int) posX;
			int bugY = (int) posY;
			int bugZ = (int) posZ;

			Vec3 vec0 = getLookVec();

			List<int[]> list = Library.getBlockPosInPath(bugX, bugY, bugZ, l, vec0);

			for (int i = 0; i < 8; i++) {
				vec0.rotateAroundY(part);
				list.addAll(Library.getBlockPosInPath(bugX, bugY - 1, bugZ, l, vec0));
			}

			double velX = e.posX - lastX;
			double velY = e.posY - lastY;
			double velZ = e.posZ - lastZ;

			if(this.lastTarget != e) {
				velX = velY = velZ = 0;
			}

			if (this.getDistanceToEntity(e) > 20) {
				topAttack = true;
			}

			int prediction = 60;
			Vec3 delta = Vec3.createVectorHelper(e.posX - posX + velX * prediction, (e.posY + e.height / 2) - (posY + 1) + velY * prediction, e.posZ - posZ + velZ * prediction);
			double len = delta.lengthVector();
			if(len < 3) return;
			double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);

			double x = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
			double y = delta.yCoord;
			double v0 = 1.2;
			double v02 = v0 * v0;
			double g = 0.03D;
			double upperLower = topAttack ? 1 : -1;
			double targetPitch = Math.atan((v02 + Math.sqrt(v02*v02 - g*(g*x*x + 2*y*v02)) * upperLower) / (g*x));
			Vec3 fireVec = null;
			if(!Double.isNaN(targetPitch)) {

				fireVec = Vec3.createVectorHelper(v0, 0, 0);
				fireVec.rotateAroundZ((float) -targetPitch);
				fireVec.rotateAroundY((float) -(targetYaw + Math.PI * 0.5));
			}

			for (int[] ints : list) {

				int x1 = ints[0];
				int y1 = ints[1];
				int z1 = ints[2];


				Block b = worldObj.getBlock(x1, y1, z1);
				float k = b.getExplosionResistance(this, worldObj, x1, y1, z1, posX, posY, posZ);

				if (k < ModBlocks.concrete.getExplosionResistance(this) && b.isNormalCube() && !(b instanceof BlockDummyable) && worldObj.getTileEntity(x1, y1, z1) == null) {

					EntityRubble rubble = new EntityRubble(worldObj);
					rubble.posX = x1 + 0.5F;
					rubble.posY = y1 + 2;
					rubble.posZ = z1 + 0.5F;

					rubble.setMetaBasedOnBlock(b, worldObj.getBlockMetadata(x1, y1, z1));

					if(fireVec != null)
						rubble.setThrowableHeading(fireVec.xCoord, fireVec.yCoord, fireVec.zCoord, (float) v0, rand.nextFloat());

					worldObj.spawnEntityInWorld(rubble);

					worldObj.setBlock(x1, y1, z1, Blocks.air);
				}
			}
		}
	}
	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.25, 2), 100);
	}

	@Override
	protected boolean canDig() {
		return true;
	}
}
