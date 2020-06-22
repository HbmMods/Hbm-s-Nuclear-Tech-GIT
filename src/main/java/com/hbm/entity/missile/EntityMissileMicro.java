package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileMicro extends EntityMissileBaseAdvanced {

	public EntityMissileMicro(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileMicro(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
        if (!this.worldObj.isRemote)
        {
    		/*EntityNukeExplosionMK3 entity0 = new EntityNukeExplosionMK3(this.worldObj);
    	    entity0.posX = this.posX;
    	    entity0.posY = this.posY;
    	    entity0.posZ = this.posZ;
    	    entity0.destructionRange = MainRegistry.fatmanRadius;
    	    entity0.speed = MainRegistry.blastSpeed;
    	    entity0.coefficient = 10.0F;*/
    	    	
    	    this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, MainRegistry.fatmanRadius, posX, posY, posZ));
    	    
    	    if(MainRegistry.polaroidID == 11)
    	    	if(rand.nextInt(100) >= 0)
    	    	{
    	    		ExplosionParticleB.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	} else {
    	    		ExplosionParticle.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	}
    	    else
    	    	if(rand.nextInt(100) == 0)
    	    	{
    	    		ExplosionParticleB.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	} else {
    	    		ExplosionParticle.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
    	    	}
        }
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(ModItems.ducttape, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.gun_fatman_ammo, 1);
	}

	@Override
	public int getMissileType() {
		return 0;
	}

}
