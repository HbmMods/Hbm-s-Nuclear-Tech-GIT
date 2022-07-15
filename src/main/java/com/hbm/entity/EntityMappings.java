package com.hbm.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.hbm.entity.cart.*;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.*;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.mob.botprime.*;
import com.hbm.entity.mob.siege.*;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.entity.qic.*;
import com.hbm.util.Tuple.Quartet;
import com.hbm.util.Tuple.Triplet;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class EntityMappings
{
	private static final ArrayList<Triplet<Class<? extends Entity>, String, Integer>> technicalMap = new ArrayList<>();
	private static final ArrayList<Quartet<Class<? extends Entity>, String, Integer, Integer>> mobMap = new ArrayList<>();
	static
	{
		add(EntityRocket.class, "entity_rocket", 250);
		add(EntityNukeExplosion.class, "entity_nuke_explosion", 250);
		add(EntityNukeExplosionAdvanced.class, "entity_nuke_explosion_advanced", 250);
		add(EntityGrenadeGeneric.class, "entity_grenade_generic", 250);
		add(EntityGrenadeStrong.class, "entity_grenade_strong", 250);
		add(EntityGrenadeFrag.class, "entity_grenade_frag", 250);
		add(EntityGrenadeFire.class, "entity_grenade_fire", 250);
		add(EntityGrenadeCluster.class, "entity_grenade_cluster", 250);
		add(EntityTestMissile.class, "entity_test_missile", 1000);
		add(EntityNukeCloudSmall.class, "entity_nuke_cloud_small", 10000);
		add(EntityBullet.class, "entity_bullet", 250);
		add(EntityGrenadeFlare.class, "entity_grenade_flare", 500);
		add(EntityGrenadeElectric.class, "entity_grenade_electric", 500);
		add(EntityGrenadePoison.class, "entity_grenade_poison", 500);
		add(EntityGrenadeGas.class, "entity_grenade_gas", 500);
		add(EntityGrenadeSchrabidium.class, "entity_grenade_schrab", 500);
		add(EntityGrenadeNuke.class, "entity_grenade_nuke", 500);
		add(EntitySchrab.class, "entity_schrabnel", 500);
		add(EntityMissileGeneric.class, "entity_missile_generic", 1000);
		add(EntityMissileStrong.class, "entity_missile_strong", 1000);
		add(EntityMissileNuclear.class, "entity_missile_nuclear", 1000);
		add(EntityMissileCluster.class, "entity_missile_cluster", 1000);
		add(EntityMissileIncendiary.class, "entity_missile_incendiary", 1000);
		add(EntityMissileAntiBallistic.class, "entity_missile_anti", 1000);
		add(EntityMissileBunkerBuster.class, "entity_missile_buster", 1000);
		add(EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", 100);
		add(EntityMissileClusterStrong.class, "entity_missile_cluster_strong", 1000);
		add(EntityMissileBusterStrong.class, "entity_missile_buster_strong", 1000);
		add(EntityMissileBurst.class, "entity_missile_burst", 1000);
		add(EntityMissileInferno.class, "entity_missile_inferno", 1000);
		add(EntityMissileRain.class, "entity_missile_rain", 1000);
		add(EntityMissileDrill.class, "entity_missile_drill", 1000);
		add(EntityMissileEndo.class, "entity_missile_endo", 1000);
		add(EntityMissileExo.class, "entity_missile_exo", 1000);
		add(EntityMissileMirv.class, "entity_missile_mirv", 1000);
		add(EntityMIRV.class, "entity_mirvlet", 1000);
		add(EntitySmokeFX.class, "entity_smoke_fx", 1000);
		add(EntityNukeCloudBig.class, "entity_nuke_cloud_big", 1000);
		add(EntityGrenadeNuclear.class, "entity_grenade_nuclear", 1000);
		add(EntityBSmokeFX.class, "entity_b_smoke_fx", 1000);
		add(EntityGrenadePlasma.class, "entity_grenade_plasma", 500);
		add(EntityGrenadeTau.class, "entity_grenade_tau", 500);
		add(EntityChopperMine.class, "entity_chopper_mine", 1000);
		add(EntityCombineBall.class, "entity_combine_ball", 1000);
		add(EntityRainbow.class, "entity_rainbow", 1000);
		add(EntityGrenadeLemon.class, "entity_grenade_lemon", 500);
		add(EntityCloudFleija.class, "entity_cloud_fleija", 500);
		add(EntityGrenadeMk2.class, "entity_grenade_mk2", 500);
		add(EntityGrenadeZOMG.class, "entity_grenade_zomg", 500);
		add(EntityGrenadeASchrab.class, "entity_grenade_aschrab", 500);
		add(EntityNukeCloudNoShroom.class, "entity_nuke_cloud_no", 1000);
		add(EntityFalloutRain.class, "entity_fallout", 1000);
		add(EntityDischarge.class, "entity_emp_discharge", 500);
		add(EntityEMPBlast.class, "entity_emp_blast", 1000);
		add(EntityFire.class, "entity_fire", 1000);
		add(EntityPlasmaBeam.class, "entity_immolator_beam", 1000);
		add(EntityLN2.class, "entity_LN2", 1000);
		add(EntityNightmareBlast.class, "entity_ominous_bullet", 1000);
		add(EntityGrenadePulse.class, "entity_grenade_pulse", 1000);
		add(EntityNukeExplosionPlus.class, "entity_nuke_explosion_advanced", 1000);
		add(EntityLaserBeam.class, "entity_laser_beam", 1000);
		add(EntityMinerBeam.class, "entity_miner_beam", 1000);
		add(EntityRubble.class, "entity_rubble", 1000);
		add(EntityDSmokeFX.class, "entity_d_smoke_fx", 1000);
		add(EntitySSmokeFX.class, "entity_s_smoke_fx", 1000);
		add(EntityShrapnel.class, "entity_shrapnel", 1000);
		add(EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", 250);
		add(EntityBlackHole.class, "entity_black_hole", 250);
		add(EntityGrenadeBlackHole.class, "entity_grenade_black_hole", 250);
		add(EntityOilSpillFX.class, "entity_spill_fx", 1000);
		add(EntityOilSpill.class, "entity_oil_spill", 1000);
		add(EntityGasFX.class, "entity_spill_fx", 1000);
		add(EntityMinecartTest.class, "entity_minecart_test", 1000);
		add(EntitySparkBeam.class, "entity_spark_beam", 1000);
		add(EntityMissileDoomsday.class, "entity_missile_doomsday", 1000);
		add(EntityBombletTheta.class, "entity_theta", 1000);
		add(EntityBombletSelena.class, "entity_selena", 1000);
		add(EntityTSmokeFX.class, "entity_t_smoke_fx", 1000);
		add(EntityNukeExplosionMK3.class, "entity_nuke_mk3", 1000);
		add(EntityVortex.class, "entity_vortex", 250);
		add(EntityMeteor.class, "entity_meteor", 1000);
		add(EntityLaser.class, "entity_laser", 1000);
		add(EntityBoxcar.class, "entity_boxcar", 1000);
		add(EntityMissileTaint.class, "entity_missile_taint", 1000);
		add(EntityGrenadeGascan.class, "entity_grenade_gascan", 1000);
		add(EntityNukeExplosionMK4.class, "entity_nuke_mk4", 1000);
		add(EntityCloudFleijaRainbow.class, "entity_cloud_rainbow", 1000);
		add(EntityExplosiveBeam.class, "entity_beam_bomb", 1000);
		add(EntityAAShell.class, "entity_aa_shell", 1000);
		add(EntityRocketHoming.class, "entity_stinger", 1000);
		add(EntityMissileMicro.class, "entity_missile_micronuclear", 1000);
		add(EntityCloudSolinium.class, "entity_cloud_rainbow", 1000);
		add(EntityRagingVortex.class, "entity_raging_vortex", 250);
		add(EntityCarrier.class, "entity_missile_carrier", 1000);
		add(EntityBooster.class, "entity_missile_booster", 1000);
		add(EntityModBeam.class, "entity_beam_bang", 1000);
		add(EntityMissileBHole.class, "entity_missile_blackhole", 1000);
		add(EntityMissileSchrabidium.class, "entity_missile_schrabidium", 1000);
		add(EntityMissileEMP.class, "entity_missile_emp", 1000);
		add(EntityChlorineFX.class, "entity_chlorine_fx", 1000);
		add(EntityPinkCloudFX.class, "entity_pink_cloud_fx", 1000);
		add(EntityCloudFX.class, "entity_cloud_fx", 1000);
		add(EntityGrenadePC.class, "entity_grenade_pink_cloud", 250);
		add(EntityGrenadeCloud.class, "entity_grenade_cloud", 250);
		add(EntityBomber.class, "entity_bomber", 1000);
		add(EntityBombletZeta.class, "entity_zeta", 1000);
		add(EntityOrangeFX.class, "entity_agent_orange", 1000);
		add(EntityDeathBlast.class, "entity_laser_blast", 1000);
		add(EntityGrenadeSmart.class, "entity_grenade_smart", 250);
		add(EntityGrenadeMIRV.class, "entity_grenade_mirv", 250);
		add(EntityGrenadeBreach.class, "entity_grenade_breach", 250);
		add(EntityGrenadeBurst.class, "entity_grenade_burst", 250);
		add(EntityBurningFOEQ.class, "entity_burning_foeq", 1000);
		add(EntityGrenadeIFGeneric.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFHE.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFBouncy.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFSticky.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFImpact.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFIncendiary.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFToxic.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFConcussion.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFBrimstone.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFMystery.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFSpark.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFHopwire.class, "entity_grenade_ironshod", 250);
		add(EntityGrenadeIFNull.class, "entity_grenade_ironshod", 250);
		add(EntityFallingNuke.class, "entity_falling_bomb", 1000);
		add(EntityBulletBase.class, "entity_bullet_mk2", 250);
		add(EntityMinerRocket.class, "entity_miner_lander", 1000);
		add(EntityFogFX.class, "entity_nuclear_fog", 1000);
		add(EntityDuchessGambit.class, "entity_duchessgambit", 1000);
		add(EntityMissileEMPStrong.class, "entity_missile_emp_strong", 1000);
		add(EntityEMP.class, "entity_emp_logic", 1000);
		add(EntityWaterSplash.class, "entity_water_splash", 1000);
		add(EntityBobmazon.class, "entity_bobmazon_delivery", 1000);
		add(EntityMissileCustom.class, "entity_custom_missile", 1000);
		add(EntityBalefire.class, "entity_balefire", 1000);
		add(EntityTom.class, "entity_tom_the_moonstone", 1000);
		add(EntityTomBlast.class, "entity_tom_bust", 1000);
		add(EntityBuilding.class, "entity_falling_building", 1000);
		add(EntitySoyuz.class, "entity_soyuz", 1000);
		add(EntitySoyuzCapsule.class, "entity_soyuz_capsule", 1000);
		add(EntityMovingItem.class, "entity_c_item", 1000);
		add(EntityCloudTom.class, "entity_moonstone_blast", 1000);
		add(EntityBeamVortex.class, "entity_vortex_beam", 1000);
		add(EntityFireworks.class, "entity_firework_ball", 1000);
		add(EntityWastePearl.class, "entity_waste_pearl", 1000);
		add(EntityBOTPrimeHead.class, "entity_balls_o_tron", 1000);
		add(EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", 1000);
		add(EntityBlockSpider.class, "entity_taintcrawler", 1000);
		add(EntityRBMKDebris.class, "entity_rbmk_debris", 1000);
		add(EntityUFO.class, "entity_ntm_ufo", 1000);
		add(EntityNukeExplosionNT.class, "entity_ntm_explosion_nt", 1000);
		add(EntityQuasar.class, "entity_digamma_quasar", 250);
		add(EntitySpear.class, "entity_digamma_spear", 1000);
		add(EntityMissileVolcano.class, "entity_missile_volcano", 1000);
		add(EntityMissileShuttle.class, "entity_missile_shuttle", 1000);
		add(EntityZirnoxDebris.class, "entity_zirnox_debris", 1000);
//		add(EntityGrenadeLunatic.class, "entity_grenade_lunatic", 250);
//		add(EntityGrenadeStunning.class, "entity_grenade_stunning", 250);
		add(EntityGhost.class, "entity_ntm_ghost", 1000);
		add(EntityGrenadeDynamite.class, "entity_grenade_dynamite", 250);
		add(EntitySiegeLaser.class, "entity_ntm_siege_laser", 1000);
		add(EntitySiegeDropship.class, "entity_ntm_siege_dropship", 1000);
		add(EntityTNTPrimedBase.class, "entity_ntm_tnt_primed", 1000);
		add(EntitySPV.class, "entity_self_propelled_vehicle_mark_1", 1000);
//		add(EntityGrenadeBouncyGeneric.class, "entity_grenade_bouncy_generic", 250);
//		add(EntityGrenadeImpactGeneric.class, "entity_grenade_impact_generic", 250);
		add(EntityMinecartCrate.class, "entity_ntm_cart_crate", 250);
		add(EntityMinecartDestroyer.class, "entity_ntm_cart_crate", 250);
		add(EntityMinecartOre.class, "entity_ntm_cart_ore", 250);
		add(EntityMinecartBogie.class, "entity_ntm_cart_bogie", 250);
		add(EntityMagnusCartus.class, "entity_ntm_cart_chungoid", 250);
		add(EntityMinecartPowder.class, "entity_ntm_cart_powder", 250);
		add(EntityMinecartSemtex.class, "entity_ntm_cart_semtex", 250);
		add(EntityNukeTorex.class, "entity_effect_torex", 250);
		add(EntityArtilleryShell.class, "entity_artillery_shell", 250);
		add(EntitySiegeTunneler.class, "entity_meme_tunneler", 1000);
//		add(EntityTesseract.class, "entity_tesseract", 50);
//		add(EntityGrenadeBouncyBaseNT.class, "entity_grenade_bouncy_nt", 500);
		
		add(EntityNuclearCreeper.class, "entity_mob_nuclear_creeper", 0x204131, 0x75CE00);
		add(EntityTaintedCreeper.class, "entity_mob_tainted_creeper", 0x813b9b, 0xd71fdd);
		add(EntityHunterChopper.class, "entity_mob_hunter_chopper", 0x000020, 0x2D2D72);
		add(EntityCyberCrab.class, "entity_cyber_crab", 0xAAAAAA, 0x444444);
		add(EntityTeslaCrab.class, "entity_tesla_crab", 0xAAAAAA, 0x440000);
		add(EntityTaintCrab.class, "entity_taint_crab", 0xAAAAAA, 0xFF00FF);
		add(EntityMaskMan.class, "entity_mob_mask_man", 0x818572, 0xC7C1B7);
		add(EntityDuck.class, "entity_fucc_a_ducc", 0xd0d0d0, 0xFFBF00);
		add(EntityQuackos.class, "entity_elder_one", 0xd0d0d0, 0xFFBF00);
		add(EntityFBI.class, "entity_ntm_fbi", 0x008000, 0x404040);
		add(EntityRADBeast.class, "entity_ntm_radiation_blaze", 0x303030, 0x008000);
		add(EntitySiegeZombie.class, "entity_meme_zombie", 0x303030, 0x008000);
		add(EntitySiegeSkeleton.class, "entity_meme_skeleton", 0x303030, 0x000080);
		add(EntitySiegeUFO.class, "entity_meme_ufo", 0x303030, 0x800000);
		add(EntitySiegeCraft.class, "entity_meme_craft", 0x303030, 0x808000);

	}
	private static void add(Class<? extends Entity> e, String name, int range)
	{
		technicalMap.add(new Triplet<Class<? extends Entity>, String, Integer>(e, name, range));
	}
	private static void add(Class<? extends Entity> e, String name, int color1, int color2)
	{
		mobMap.add(new Quartet<Class<? extends Entity>, String, Integer, Integer>(e, name, color1, color2));
	}
	private static int i = 0;
	public static void registerEntities(Object mod)
	{
		final Iterator<Triplet<Class<? extends Entity>, String, Integer>> technicalIterator = new ArrayList<>(technicalMap).iterator();
		final Iterator<Quartet<Class<? extends Entity>, String, Integer, Integer>> mobIterator = new ArrayList<>(mobMap).iterator();
		while (technicalIterator.hasNext())
		{
			final Triplet<Class<? extends Entity>, String, Integer> reg = technicalIterator.next();
			EntityRegistry.registerModEntity(reg.getX(), reg.getY(), i++, mod, reg.getZ(), 1, true);
			technicalIterator.remove();
		}
		while (mobIterator.hasNext())
		{
			final Quartet<Class<? extends Entity>, String, Integer, Integer> reg = mobIterator.next();
			EntityRegistry.registerGlobalEntityID(reg.getW(), reg.getX(), EntityRegistry.findGlobalUniqueEntityId(), reg.getY(), reg.getZ());
			mobIterator.remove();
		}
	}
}
