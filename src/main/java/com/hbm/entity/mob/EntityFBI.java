package com.hbm.entity.mob;

import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityFBI extends EntityZombie {

	public EntityFBI(World p_i1745_1_) {
		super(p_i1745_1_);
	}
	
    protected void addRandomArmor() {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(ModItems.gun_revolver_nopip));
    }
}
