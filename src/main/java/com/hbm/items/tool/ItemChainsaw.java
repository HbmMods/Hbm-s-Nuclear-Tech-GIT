package com.hbm.items.tool;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.IAnimatedItem;
import com.hbm.items.IHeldSoundProvider;
import com.hbm.render.anim.AnimationEnums.ToolAnimation;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class ItemChainsaw extends ItemToolAbilityFueled implements IHeldSoundProvider, IAnimatedItem<ToolAnimation> {

	public ItemChainsaw(float damage, double movement, ToolMaterial material, EnumToolType type, int maxFuel, int consumption, int fillRate, FluidType... acceptedFuels) {
		super(damage, movement, material, type, maxFuel, consumption, fillRate, acceptedFuels);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		if(!(entityLiving instanceof EntityPlayerMP))
			return false;

		if(stack.getItemDamage() >= stack.getMaxDamage())
			return false;

		playAnimation((EntityPlayer) entityLiving, ToolAnimation.SWING);

		return false;
	}

	@Override
	public BusAnimation getAnimation(ToolAnimation type, ItemStack stack) {
		int forward = 150;
		int sideways = 100;
		int retire = 200;

		if(HbmAnimations.getRelevantAnim() == null) {

			return new BusAnimation()
					.addBus("SWING_ROT", new BusAnimationSequence()
							.addPos(0, 0, 90, forward)
							.addPos(45, 0, 90, sideways)
							.addPos(0, 0, 0, retire))
					.addBus("SWING_TRANS", new BusAnimationSequence()
							.addPos(0, 0, 3, forward)
							.addPos(2, 0, 2, sideways)
							.addPos(0, 0, 0, retire));
		} else {

			double[] rot = HbmAnimations.getRelevantTransformation("SWING_ROT");
			double[] trans = HbmAnimations.getRelevantTransformation("SWING_TRANS");

			if(System.currentTimeMillis() - HbmAnimations.getRelevantAnim().startMillis < 50) return null;

			return new BusAnimation()
					.addBus("SWING_ROT", new BusAnimationSequence()
							.addPos(rot[0], rot[1], rot[2], 0)
							.addPos(0, 0, 90, forward)
							.addPos(45, 0, 90, sideways)
							.addPos(0, 0, 0, retire))
					.addBus("SWING_TRANS", new BusAnimationSequence()
							.addPos(trans[0], trans[1], trans[2], 0)
							.addPos(0, 0, 3, forward)
							.addPos(2, 0, 2, sideways)
							.addPos(0, 0, 0, retire));
		}
	}

	@Override
	public Class<ToolAnimation> getEnum() {
		return ToolAnimation.class;
	}

	@Override
	public boolean shouldPlayerModelAim(ItemStack stack) {
		return false;
	}

}
