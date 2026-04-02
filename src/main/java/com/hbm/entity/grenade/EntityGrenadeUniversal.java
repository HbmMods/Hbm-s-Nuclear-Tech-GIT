package com.hbm.entity.grenade;

import com.hbm.entity.projectile.EntityThrowableInterp;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.items.weapon.grenade.ItemGrenadeUniversal;
import com.hbm.util.Vec3NT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityGrenadeUniversal extends EntityThrowableInterp {
	
	public static final int DW_GRENADE = 3;

	public EntityGrenadeUniversal(World world) {
		super(world);
	}

	public EntityGrenadeUniversal(World world, EntityPlayer thrower, ItemStack grenade) {
		super(world);
		ItemStack copy = grenade.copy();
		copy.stackSize = 1;
		this.dataWatcher.addObject(DW_GRENADE, copy);
	}
	
	public ItemStack getGrenadeItem() {
		return this.dataWatcher.getWatchableObjectItemStack(DW_GRENADE);
	}

	public EnumGrenadeShell getShell() { return ItemGrenadeUniversal.getShell(getGrenadeItem()); }
	public EnumGrenadeFilling getFilling() { return ItemGrenadeUniversal.getFilling(getGrenadeItem()); }
	public EnumGrenadeFuze getFuze() { return ItemGrenadeUniversal.getFuze(getGrenadeItem()); }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		EnumGrenadeFuze fuze = this.getFuze();
		if(fuze.updateTick != null) fuze.updateTick.accept(this);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		EnumGrenadeFuze fuze = this.getFuze();
		
		if(fuze.onImpact != null) fuze.onImpact.accept(this, mop);
		
		if(this.isDead) return; // we assume the grenade has gone off by this point
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
			EnumGrenadeShell shell = this.getShell();
			Vec3NT vec = new Vec3NT(this.motionX, this.motionY, this.motionZ);
			if(vec.lengthVector() > 0.5) {
				// TODO play bounce sound
			}
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			if(dir.offsetX != 0) this.motionX *= -shell.getBounce();
			if(dir.offsetY != 0) this.motionY *= -shell.getBounce();
			if(dir.offsetZ != 0) this.motionZ *= -shell.getBounce();
		}
	}
}
