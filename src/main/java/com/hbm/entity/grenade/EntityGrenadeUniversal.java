package com.hbm.entity.grenade;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityThrowableInterp;
import com.hbm.items.weapon.grenade.ItemGrenadeExtra.EnumGrenadeExtra;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.main.MainRegistry;
import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;
import com.hbm.util.TrackerUtil;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityGrenadeUniversal extends EntityThrowableInterp {

	public static final int DW_GRENADE = 3;
	public static final int DW_BOUNCES = 4;
	public static final int DW_TRAIL = 5;
	
	public static final int TRAIL_TRIPLET = 1;

	public double prevSpin;
	public double spin;

	public EntityGrenadeUniversal(World world) {
		super(world);
	}

	public EntityGrenadeUniversal(World world, ItemStack grenade) {
		super(world);
		ItemStack copy = grenade.copy();
		copy.stackSize = 1;
		this.dataWatcher.updateObject(DW_GRENADE, copy);
	}

	public EntityGrenadeUniversal(World world, EntityPlayer thrower, ItemStack grenade) {
		super(world);
		this.setThrower(thrower);
		ItemStack copy = grenade.copy();
		copy.stackSize = 1;
		this.dataWatcher.updateObject(DW_GRENADE, copy);
		
		Vec3NT offset = new Vec3NT(0.25, -0.25, 0).rotateAroundYDeg(-thrower.rotationYaw + 180);
		
		this.setPosition(thrower.posX + offset.xCoord, thrower.posY + thrower.getEyeHeight() + offset.yCoord, thrower.posZ + offset.zCoord);
		
		EnumGrenadeShell shell = ItemGrenadeUniversal.getShell(grenade);
		
		Vec3NT yeet = new Vec3NT(thrower.getLookVec()).normalizeSelf();
		this.setThrowableHeading(yeet.xCoord, yeet.yCoord, yeet.zCoord, (float) shell.getYeetForce(), 0);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.dataWatcher.addObjectByDataType(DW_GRENADE, 5);
		this.dataWatcher.addObject(DW_BOUNCES, new Integer(0));
		this.dataWatcher.addObject(DW_TRAIL, new Integer(0));
	}
	
	public EntityGrenadeUniversal setTrail(int trail) {
		this.dataWatcher.updateObject(DW_TRAIL, trail);
		return this;
	}
	
	public ItemStack getGrenadeItem() { return this.dataWatcher.getWatchableObjectItemStack(DW_GRENADE); }
	public int getBounces() { return this.dataWatcher.getWatchableObjectInt(DW_BOUNCES); }
	public int getTrail() { return this.dataWatcher.getWatchableObjectInt(DW_TRAIL); }

	public EnumGrenadeShell getShell() { return ItemGrenadeUniversal.getShell(getGrenadeItem()); }
	public EnumGrenadeFilling getFilling() { return ItemGrenadeUniversal.getFilling(getGrenadeItem()); }
	public EnumGrenadeFuze getFuze() { return ItemGrenadeUniversal.getFuze(getGrenadeItem()); }
	public EnumGrenadeExtra getExtra() { return ItemGrenadeUniversal.getExtra(getGrenadeItem()); }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		EnumGrenadeFuze fuze = this.getFuze();
		EnumGrenadeExtra extra = this.getExtra();
		
		if(fuze.updateTick != null) fuze.updateTick.accept(this);
		if(extra != null && extra.updateTick != null) extra.updateTick.accept(this);
		
		if(worldObj.isRemote) {
			this.prevSpin = this.spin;
			
			if(this.getBounces() <= 0) {
				this.spin += 15;
			} else {
				this.spin += Math.min(15, new Vec3NT(lastTickPosX - posX, 0, lastTickPosZ - posZ).lengthVector() * 50);
			}
			
			if(this.spin >= 360) {
				this.prevSpin -= 360;
				this.spin -= 360;
			}
			
			if(this.getTrail() == TRAIL_TRIPLET) {
				NBTTagCompound data = new NBTTagCompound();
				data.setDouble("posX", posX);
				data.setDouble("posY", posY);
				data.setDouble("posZ", posZ);
				data.setString("type", "vanillaExt");
				data.setString("mode", "flame");
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		EnumGrenadeFuze fuze = this.getFuze();
		EnumGrenadeExtra extra = this.getExtra();
		
		if(fuze.onImpact != null) fuze.onImpact.accept(this, mop);
		if(extra != null && extra.onImpact != null) extra.onImpact.accept(this, mop);
		
		if(this.isDead) return; // we assume the grenade has gone off by this point
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			this.setPosition(mop.hitVec.xCoord + dir.offsetX * 0.05, mop.hitVec.yCoord + dir.offsetY * 0.05, mop.hitVec.zCoord + dir.offsetZ * 0.05);
			EnumGrenadeShell shell = this.getShell();
			Vec3NT vec = new Vec3NT(this.motionX, this.motionY, this.motionZ);
			if(vec.lengthVector() > 0.2) {
				worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.grenadeBounce", 1F, 1F);
			}
			if(dir.offsetX != 0) this.motionX *= -shell.getBounce(); else this.motionX *= 0.8;
			if(dir.offsetY != 0) this.motionY *= -shell.getBounce(); else this.motionY *= 0.8;
			if(dir.offsetZ != 0) this.motionZ *= -shell.getBounce(); else this.motionZ *= 0.8;
			if(worldObj instanceof WorldServer) TrackerUtil.sendTeleport((WorldServer) worldObj, this);
			this.dataWatcher.updateObject(DW_BOUNCES, this.getBounces() + 1);
		}
	}
	
	public void explode() {
		this.setDead();
		EnumGrenadeFilling filling = this.getFilling();
		if(filling.explode != null) filling.explode.accept(this);
		EnumGrenadeExtra extra = this.getExtra();
		if(extra != null && extra.onExplode != null) extra.onExplode.accept(this);
		
		if(GeneralConfig.enableExtendedLogging) {
			String s = "null";
			if(getThrower() != null && getThrower() instanceof EntityPlayer) s = ((EntityPlayer) getThrower()).getDisplayName();
			MainRegistry.logger.log(Level.INFO, "[GREN] Set off grenade at " + ((int) posX) + " / " + ((int) posY) + " / " + ((int) posZ) + " by " + s + "!");
		}
	}
	
	public int getTimer() { return this.ticksInAir + this.ticksInGround; }
	
	@Override protected int groundDespawn() { return 0; }
	@Override public boolean fullBlockCollisions() { return true; }
}
