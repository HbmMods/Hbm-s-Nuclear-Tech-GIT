package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.XFactory762mm;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.TileEntityTesla;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityTaintCrab extends EntityCyberCrab {
	
	public List<double[]> targets = new ArrayList();

	public EntityTaintCrab(World p_i1733_1_) {
		super(p_i1733_1_);
		this.setSize(1.25F, 1.25F);
		this.ignoreFrustumCheck = true;
	}

	protected EntityAIArrowAttack arrowAI() {
		return new EntityAIArrowAttack(this, 0.5D, 5, 5, 50.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5F);
	}

	public void onLivingUpdate() {

		targets = TileEntityTesla.zap(worldObj, posX, posY + 1.25, posZ, 10, this);

		List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));
		
		for(EntityLivingBase e : targets) {
			if(!(e instanceof EntityCyberCrab)) e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 10, 15));
		}

		super.onLivingUpdate();
	}

	@Override
	protected Item getDropItem() {
		return ModItems.coil_advanced_alloy;
	}

	protected void dropRareDrop(int p_70600_1_) {
		this.dropItem(ModItems.coil_magnetized_tungsten, 1);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float f) {
		
		EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(this, XFactory762mm.r762_fmj, 10F, 0F, 0F, 0F, 0F);
		Vec3 motion = Vec3.createVectorHelper(posX - entity.posX, posY - entity.posZ - entity.height / 2, posZ - entity.posZ);
		motion = motion.normalize();
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "vanilla");
		data.setString("mode", "flame");
		data.setDouble("mX", bullet.motionX * 0.3);
		data.setDouble("mY", bullet.motionY * 0.3);
		data.setDouble("mZ", bullet.motionZ * 0.3);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(this.dimension, posX, posY, posZ, 50));
		this.worldObj.spawnEntityInWorld(bullet);
		this.playSound("hbm:weapon.sawShoot", 1.0F, 0.5F);
	}
}
