package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.impl.ItemGunDrill;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mags.MagazineLiquidEngine;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.AnimationEnums.GunAnimation;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class XFactoryDrill {

	public static final String D_REACH =	"D_REACH";
	public static final String F_DTNEG =	"F_DTNEG";
	public static final String F_PIERCE =	"F_PIERCE";
	public static final String I_AOE =		"I_AOE";
	public static final String I_HARVEST =	"I_HARVEST";

	public static void init() {

		ModItems.gun_drill = new ItemGunDrill(WeaponQuality.UTILITY, new GunConfig()
				.dura(3_000).draw(10).inspect(55).hideCrosshair(false).crosshair(Crosshair.L_CIRCUMFLEX)
				.rec(new Receiver(0)
						.dmg(10F).delay(20).dry(30).auto(true).jam(0)
						.mag(new MagazineLiquidEngine(0, 4_000, Fluids.GASOLINE, Fluids.GASOLINE_LEADED, Fluids.COALGAS, Fluids.COALGAS_LEADED))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE).fire(LAMBDA_DRILL_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).pr(Lego.LAMBDA_STANDARD_RELOAD).decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_DRILL_ANIMS)
				).setUnlocalizedName("gun_drill");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_DRILL_FIRE = (stack, ctx) -> {
		doStandardFire(stack, ctx, GunAnimation.CYCLE, true);
	};

	public static void doStandardFire(ItemStack stack, LambdaContext ctx, GunAnimation anim, boolean calcWear) {
		EntityPlayer player = ctx.getPlayer();
		int index = ctx.configIndex;
		if(anim != null) ItemGunBaseNT.playAnimation(player, stack, anim, ctx.configIndex);

		Receiver primary = ctx.config.getReceivers(stack)[0];
		IMagazine mag = primary.getMagazine(stack);
		
		MovingObjectPosition mop = EntityDamageUtil.getMouseOver(ctx.getPlayer(), getModdableReach(stack, 5.0D));
		if(mop != null) {
			if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
				float damage = 5.0F;
				if(mop.entityHit instanceof EntityLivingBase) {
					EntityDamageUtil.attackEntityFromNT((EntityLivingBase) mop.entityHit, DamageSource.causePlayerDamage(ctx.getPlayer()), damage, true, true, 0.1F, getModdableDTNegation(stack, 2F), getModdablePiercing(stack, 0.15F));
				} else {
					mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(ctx.getPlayer()), damage);
				}
			}
			if(player != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
				
				int aoe = player.isSneaking() ? 0 : getModdableAoE(stack, 1);
				for(int i = -aoe; i <= aoe; i++) for(int j = -aoe; j <= aoe; j++) for(int k = -aoe; k <= aoe; k++) {
					breakExtraBlock(player.worldObj, mop.blockX + i, mop.blockY + j, mop.blockZ + k, player, mop.blockX, mop.blockY, mop.blockZ);
				}
			}
		}

		mag.useUpAmmo(stack, ctx.inventory, 10);
		if(calcWear) ItemGunBaseNT.setWear(stack, index, Math.min(ItemGunBaseNT.getWear(stack, index), ctx.config.getDurability(stack)));
	}

	public static void breakExtraBlock(World world, int x, int y, int z, EntityPlayer playerEntity, int refX, int refY, int refZ) {
		if(world.isAirBlock(x, y, z)) return;
		if(!(playerEntity instanceof EntityPlayerMP)) return;

		EntityPlayerMP player = (EntityPlayerMP) playerEntity;
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if(!block.canHarvestBlock(player, meta) || (block.getBlockHardness(world, x, y, z) == -1.0F && block.getPlayerRelativeBlockHardness(player, world, x, y, z) == 0.0F) || block == ModBlocks.stone_keyhole) {
			world.playSoundAtEntity(player, "random.break", 0.5F, 0.8F + world.rand.nextFloat() * 0.6F);
			return;
		}
		
		// we are serverside and tryHarvestBlock already invokes the 2001 packet for every player except the user, so we manually send it for the user as well
		player.theItemInWorldManager.tryHarvestBlock(x, y, z);
		
		if(world.getBlock(x, y, z) == Blocks.air) { // only do this when the block was destroyed. if the block doesn't create air when broken, this breaks, but it's no big deal
			player.playerNetServerHandler.sendPacket(new S28PacketEffect(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12), false));
		}
	}
	
	// this system technically doesn't need to be part of the GunCfg or Receiver or anything, we can just do this and it works the exact same
	public static double getModdableReach(ItemStack stack, double base) {		return WeaponModManager.eval(base, stack, D_REACH, ModItems.gun_drill, 0); }
	public static float getModdableDTNegation(ItemStack stack, float base) {	return WeaponModManager.eval(base, stack, F_DTNEG, ModItems.gun_drill, 0); }
	public static float getModdablePiercing(ItemStack stack, float base) {		return WeaponModManager.eval(base, stack, F_PIERCE, ModItems.gun_drill, 0); }
	public static int getModdableAoE(ItemStack stack, int base) {				return WeaponModManager.eval(base, stack, I_AOE, ModItems.gun_drill, 0); }
	public static int getModdableHarvestLevel(ItemStack stack, int base) {		return WeaponModManager.eval(base, stack, I_HARVEST, ModItems.gun_drill, 0); }

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, GunAnimation, BusAnimation> LAMBDA_DRILL_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().setPos(-1, 0, 0).addPos(0, 0, 0, 750, IType.SIN_DOWN));
		case CYCLE:
			double deploy = HbmAnimations.getRelevantTransformation("DEPLOY")[0];
			double spin = HbmAnimations.getRelevantTransformation("SPIN")[2] % 360;
			return new BusAnimation()
					.addBus("DEPLOY", new BusAnimationSequence().setPos(deploy, 0, 0).addPos(1, 0, 0, (int) (500 * (1 - deploy)), IType.SIN_FULL).hold(1000).addPos(0, 0, 0, 500, IType.SIN_FULL))
					.addBus("SPIN", new BusAnimationSequence().setPos(spin, 0, 0).addPos(spin + 360 * 1.5, 0, 0, 1500).addPos(spin + 360 * 2, 0, 0, 750, IType.SIN_DOWN));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("DEPLOY", new BusAnimationSequence().addPos(0.25, 0, 0, 250, IType.SIN_FULL).addPos(0, 0, 0, 250, IType.SIN_FULL))
				.addBus("SPIN", new BusAnimationSequence().addPos(360 * 1, 0, 0, 1500, IType.SIN_DOWN));
		case INSPECT: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-45, 0, 0, 500, IType.SIN_FULL).hold(1000).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		}
		
		return null;
	};
}
