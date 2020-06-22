package com.hbm.entity.logic;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityEnvirEffect extends Entity {
	
	public int maxAge = 100;
	public int blockRadius = 7;
	public int entityRadius = 7;
	public int chance = 10;
	public boolean hasBlockEffect = true;
	public boolean hasEntityEffect = true;

	public EntityEnvirEffect(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("lifetime");
		this.maxAge = nbt.getInteger("lifecap");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("lifetime", this.ticksExisted);
		nbt.setInteger("lifecap", this.maxAge);
	}
	
    public void onUpdate() {

    	if(hasBlockEffect && rand.nextInt(chance) == 0)
    		applyBlockEffect();
    	
    	if(hasEntityEffect && rand.nextInt(chance) == 0)
    		applyEntityEffect();
    }

    private void applyBlockEffect() { };
    private void applyEntityEffect() { };

}
