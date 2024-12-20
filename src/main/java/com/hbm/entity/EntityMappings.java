package com.hbm.entity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.cart.*;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.*;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.entity.missile.EntityMissileTier1.*;
import com.hbm.entity.missile.EntityMissileTier2.*;
import com.hbm.entity.missile.EntityMissileTier3.*;
import com.hbm.entity.missile.EntityMissileTier4.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.mob.botprime.*;
import com.hbm.entity.mob.glyphid.*;
import com.hbm.entity.mob.siege.*;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.entity.train.EntityRailCarBase.BoundingBoxDummyEntity;
import com.hbm.entity.train.EntityRailCarRidable.SeatDummyEntity;
import com.hbm.entity.train.*;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Quartet;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EntityMappings {

	public static List<Quartet<Class<? extends Entity>, String, Integer, Boolean>> entityMappings = new ArrayList();
	public static List<Quartet<Class<? extends Entity>, String, Integer, Integer>> mobMappings = new ArrayList();

	public static void writeMappings() {

		addEntity(EntityRocket.class, "entity_rocket", 250);
		addEntity(EntityGrenadeGeneric.class, "entity_grenade_generic", 250);
		addEntity(EntityGrenadeStrong.class, "entity_grenade_strong", 250);
		addEntity(EntityGrenadeFrag.class, "entity_grenade_frag", 250);
		addEntity(EntityGrenadeFire.class, "entity_grenade_fire", 250);
		addEntity(EntityGrenadeCluster.class, "entity_grenade_cluster", 250);
		addEntity(EntityBullet.class, "entity_bullet", 250);
		addEntity(EntityGrenadeFlare.class, "entity_grenade_flare", 500);
		addEntity(EntityGrenadeElectric.class, "entity_grenade_electric", 500);
		addEntity(EntityGrenadePoison.class, "entity_grenade_poison", 500);
		addEntity(EntityGrenadeGas.class, "entity_grenade_gas", 500);
		addEntity(EntityGrenadeSchrabidium.class, "entity_grenade_schrab", 500);
		addEntity(EntityGrenadeNuke.class, "entity_grenade_nuke", 500);
		addEntity(EntitySchrab.class, "entity_schrabnel", 500);
		addEntity(EntityMissileGeneric.class, "entity_missile_generic", 1000);
		addEntity(EntityMissileDecoy.class, "entity_missile_decoy", 1000);
		addEntity(EntityMissileStrong.class, "entity_missile_strong", 1000);
		addEntity(EntityMissileNuclear.class, "entity_missile_nuclear", 1000);
		addEntity(EntityMissileCluster.class, "entity_missile_cluster", 1000);
		addEntity(EntityMissileIncendiary.class, "entity_missile_incendiary", 1000);
		addEntity(EntityMissileAntiBallistic.class, "entity_missile_anti", 1000);
		addEntity(EntityMissileBunkerBuster.class, "entity_missile_buster", 1000);
		addEntity(EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", 1000);
		addEntity(EntityMissileClusterStrong.class, "entity_missile_cluster_strong", 1000);
		addEntity(EntityMissileBusterStrong.class, "entity_missile_buster_strong", 1000);
		addEntity(EntityMissileBurst.class, "entity_missile_burst", 1000);
		addEntity(EntityMissileInferno.class, "entity_missile_inferno", 1000);
		addEntity(EntityMissileRain.class, "entity_missile_rain", 1000);
		addEntity(EntityMissileDrill.class, "entity_missile_drill", 1000);
		addEntity(EntityMissileMirv.class, "entity_missile_mirv", 1000);
		addEntity(EntityMIRV.class, "entity_mirvlet", 1000);
		addEntity(EntityGrenadeNuclear.class, "entity_grenade_nuclear", 1000);
		addEntity(EntityBSmokeFX.class, "entity_b_smoke_fx", 1000);
		addEntity(EntityGrenadePlasma.class, "entity_grenade_plasma", 500);
		addEntity(EntityGrenadeTau.class, "entity_grenade_tau", 500);
		addEntity(EntityChopperMine.class, "entity_chopper_mine", 1000);
		addEntity(EntityRainbow.class, "entity_rainbow", 1000);
		addEntity(EntityGrenadeLemon.class, "entity_grenade_lemon", 500);
		addEntity(EntityCloudFleija.class, "entity_cloud_fleija", 500);
		addEntity(EntityGrenadeMk2.class, "entity_grenade_mk2", 500);
		addEntity(EntityGrenadeZOMG.class, "entity_grenade_zomg", 500);
		addEntity(EntityGrenadeASchrab.class, "entity_grenade_aschrab", 500);
		addEntity(EntityFalloutRain.class, "entity_fallout", 1000);
		addEntity(EntityEMPBlast.class, "entity_emp_blast", 1000);
		addEntity(EntityLN2.class, "entity_LN2", 1000);
		addEntity(EntityGrenadePulse.class, "entity_grenade_pulse", 1000);
		addEntity(EntityLaserBeam.class, "entity_laser_beam", 1000);
		addEntity(EntityMinerBeam.class, "entity_miner_beam", 1000);
		addEntity(EntityRubble.class, "entity_rubble", 1000);
		addEntity(EntityShrapnel.class, "entity_shrapnel", 1000);
		addEntity(EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", 250);
		addEntity(EntityBlackHole.class, "entity_black_hole", 250);
		addEntity(EntityGrenadeBlackHole.class, "entity_grenade_black_hole", 250);
		addEntity(EntityMinecartTest.class, "entity_minecart_test", 1000);
		addEntity(EntitySparkBeam.class, "entity_spark_beam", 1000);
		addEntity(EntityMissileDoomsday.class, "entity_missile_doomsday", 1000);
		addEntity(EntityMissileDoomsdayRusted.class, "entity_missile_doomsday_rusted", 1000);
		addEntity(EntityNukeExplosionMK3.class, "entity_nuke_mk3", 1000);
		addEntity(EntityVortex.class, "entity_vortex", 250);
		addEntity(EntityMeteor.class, "entity_meteor", 250);
		addEntity(EntityBoxcar.class, "entity_boxcar", 1000);
		addEntity(EntityTorpedo.class, "entity_torpedo", 1000);
		addEntity(EntityMissileTaint.class, "entity_missile_taint", 1000);
		addEntity(EntityGrenadeGascan.class, "entity_grenade_gascan", 1000);
		addEntity(EntityNukeExplosionMK5.class, "entity_nuke_mk5", 1000);
		addEntity(EntityCloudFleijaRainbow.class, "entity_cloud_rainbow", 1000);
		addEntity(EntityExplosiveBeam.class, "entity_beam_bomb", 1000);
		addEntity(EntityAAShell.class, "entity_aa_shell", 1000);
		addEntity(EntityMissileTest.class, "entity_missile_test_mk2", 1000);
		addEntity(EntityMissileMicro.class, "entity_missile_micronuclear", 1000);
		addEntity(EntityCloudSolinium.class, "entity_cloud_rainbow", 1000);
		addEntity(EntityRagingVortex.class, "entity_raging_vortex", 250);
		addEntity(EntityModBeam.class, "entity_beam_bang", 1000);
		addEntity(EntityMissileBHole.class, "entity_missile_blackhole", 1000);
		addEntity(EntityMissileSchrabidium.class, "entity_missile_schrabidium", 1000);
		addEntity(EntityMissileEMP.class, "entity_missile_emp", 1000);
		addEntity(EntityChlorineFX.class, "entity_chlorine_fx", 1000);
		addEntity(EntityPinkCloudFX.class, "entity_pink_cloud_fx", 1000);
		addEntity(EntityCloudFX.class, "entity_cloud_fx", 1000);
		addEntity(EntityGrenadePC.class, "entity_grenade_pink_cloud", 250);
		addEntity(EntityGrenadeCloud.class, "entity_grenade_cloud", 250);
		addEntity(EntityBomber.class, "entity_bomber", 1000);
		addEntity(EntityC130.class, "entity_c130", 1000);
		addEntity(EntityBombletZeta.class, "entity_zeta", 1000);
		addEntity(EntityOrangeFX.class, "entity_agent_orange", 1000);
		addEntity(EntityDeathBlast.class, "entity_laser_blast", 1000);
		addEntity(EntityGrenadeSmart.class, "entity_grenade_smart", 250);
		addEntity(EntityGrenadeMIRV.class, "entity_grenade_mirv", 250);
		addEntity(EntityGrenadeBreach.class, "entity_grenade_breach", 250);
		addEntity(EntityGrenadeBurst.class, "entity_grenade_burst", 250);
		addEntity(EntityBurningFOEQ.class, "entity_burning_foeq", 1000);
		addEntity(EntityGrenadeIFGeneric.class, "entity_grenade_ironshod", 250);
		addEntity(EntityGrenadeIFHE.class, "entity_grenade_ironshod_he", 250);
		addEntity(EntityGrenadeIFBouncy.class, "entity_grenade_ironshod_bouncy", 250);
		addEntity(EntityGrenadeIFSticky.class, "entity_grenade_ironshod_sticky", 250);
		addEntity(EntityGrenadeIFImpact.class, "entity_grenade_ironshod_impact", 250);
		addEntity(EntityGrenadeIFIncendiary.class, "entity_grenade_ironshod_fire", 250);
		addEntity(EntityGrenadeIFToxic.class, "entity_grenade_ironshod_toxic", 250);
		addEntity(EntityGrenadeIFConcussion.class, "entity_grenade_ironshod_con", 250);
		addEntity(EntityGrenadeIFBrimstone.class, "entity_grenade_ironshod_brim", 250);
		addEntity(EntityGrenadeIFMystery.class, "entity_grenade_ironshod_m", 250);
		addEntity(EntityGrenadeIFSpark.class, "entity_grenade_ironshod_s", 250);
		addEntity(EntityGrenadeIFHopwire.class, "entity_grenade_ironshod_hopwire", 250);
		addEntity(EntityGrenadeIFNull.class, "entity_grenade_ironshod_null", 250);
		addEntity(EntityFallingNuke.class, "entity_falling_bomb", 1000);
		addEntity(EntityBulletBaseNT.class, "entity_bullet_mk3", 250, false);
		addEntity(EntityBulletBaseMK4.class, "entity_bullet_mk4", 250, false);
		addEntity(EntityBulletBaseMK4CL.class, "entity_bullet_mk4_cl", 250, false);
		addEntity(EntityBulletBeamBase.class, "entity_beam_mk4", 250, false);
		addEntity(EntityMinerRocket.class, "entity_miner_lander", 1000);
		addEntity(EntityFogFX.class, "entity_nuclear_fog", 1000);
		addEntity(EntityDuchessGambit.class, "entity_duchessgambit", 1000);
		addEntity(EntityMissileEMPStrong.class, "entity_missile_emp_strong", 1000);
		addEntity(EntityEMP.class, "entity_emp_logic", 1000);
		addEntity(EntityWaterSplash.class, "entity_water_splash", 1000);
		addEntity(EntityBobmazon.class, "entity_bobmazon_delivery", 1000);
		addEntity(EntityMissileCustom.class, "entity_custom_missile", 1000);
		addEntity(EntityBalefire.class, "entity_balefire", 1000);
		addEntity(EntityTom.class, "entity_tom_the_moonstone", 1000);
		addEntity(EntityTomBlast.class, "entity_tom_bust", 1000);
		addEntity(EntityBuilding.class, "entity_falling_building", 1000);
		addEntity(EntitySoyuz.class, "entity_soyuz", 1000);
		addEntity(EntitySoyuzCapsule.class, "entity_soyuz_capsule", 1000);
		addEntity(EntityParachuteCrate.class, "entity_parachute_crate", 1000);
		addEntity(EntityMovingItem.class, "entity_c_item", 1000);
		addEntity(EntityMovingPackage.class, "entity_c_package", 1000);
		addEntity(EntityDeliveryDrone.class, "entity_delivery_drone", 250, false);
		addEntity(EntityRequestDrone.class, "entity_request_drone", 250, false);
		addEntity(EntityCloudTom.class, "entity_moonstone_blast", 1000);
		addEntity(EntityBeamVortex.class, "entity_vortex_beam", 1000);
		addEntity(EntityFireworks.class, "entity_firework_ball", 1000);
		addEntity(EntityWastePearl.class, "entity_waste_pearl", 1000);
		addEntity(EntityBOTPrimeHead.class, "entity_balls_o_tron",  1000);
		addEntity(EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", 1000);
		addEntity(EntityBlockSpider.class, "entity_taintcrawler", 1000);
		addEntity(EntityRBMKDebris.class, "entity_rbmk_debris", 1000);
		addEntity(EntityUFO.class, "entity_ntm_ufo", 1000);
		addEntity(EntityQuasar.class, "entity_digamma_quasar", 250);
		addEntity(EntitySpear.class, "entity_digamma_spear", 1000);
		addEntity(EntityMissileVolcano.class, "entity_missile_volcano", 1000);
		addEntity(EntityMissileShuttle.class, "entity_missile_shuttle", 1000);
		addEntity(EntityZirnoxDebris.class, "entity_zirnox_debris", 1000);
		addEntity(EntityGhost.class, "entity_ntm_ghost", 1000);
		addEntity(EntityGrenadeDynamite.class, "entity_grenade_dynamite", 250);
		addEntity(EntitySiegeLaser.class, "entity_ntm_siege_laser", 1000);
		addEntity(EntityTNTPrimedBase.class, "entity_ntm_tnt_primed", 1000);
		addEntity(EntityGrenadeBouncyGeneric.class, "entity_grenade_bouncy_generic", 250);
		addEntity(EntityGrenadeImpactGeneric.class, "entity_grenade_impact_generic", 250);
		addEntity(EntityMinecartCrate.class, "entity_ntm_cart_crate", 250, false);
		addEntity(EntityMinecartDestroyer.class, "entity_ntm_cart_crate", 250, false);
		addEntity(EntityMinecartOre.class, "entity_ntm_cart_ore", 250, false);
		addEntity(EntityMinecartBogie.class, "entity_ntm_cart_bogie", 250, false);
		addEntity(EntityMagnusCartus.class, "entity_ntm_cart_chungoid", 250, false);
		addEntity(EntityMinecartPowder.class, "entity_ntm_cart_powder", 250, false);
		addEntity(EntityMinecartSemtex.class, "entity_ntm_cart_semtex", 250, false);
		addEntity(EntityNukeTorex.class, "entity_effect_torex", 250, false);
		addEntity(EntityArtilleryShell.class, "entity_artillery_shell", 1000);
		addEntity(EntityArtilleryRocket.class, "entity_himars", 1000);
		addEntity(EntitySiegeTunneler.class, "entity_meme_tunneler", 1000);
		addEntity(EntityCog.class, "entity_stray_cog", 1000);
		addEntity(EntitySawblade.class, "entity_stray_saw", 1000);
		addEntity(EntityChemical.class, "entity_chemthrower_splash", 1000);
		addEntity(EntityMist.class, "entity_mist", 250, false);
		addEntity(EntityFireLingering.class, "entity_fire_lingering", 250, false);
		addEntity(EntityAcidBomb.class, "entity_acid_bomb", 1000);
		addEntity(EntityFallingBlockNT.class, "entity_falling_block_nt", 1000);
		addEntity(EntityBoatRubber.class, "entity_rubber_boat", 250, false);
		addEntity(EntityMissileStealth.class, "entity_missile_stealth", 1000);

		addEntity(EntityItemWaste.class, "entity_item_waste", 100);
		addEntity(EntityItemBuoyant.class, "entity_item_buoyant", 100);

		addEntity(SeatDummyEntity.class, "entity_ntm_seat_dummy", 250, false);
		addEntity(BoundingBoxDummyEntity.class, "entity_ntm_bounding_dummy", 250, false);
		addEntity(TrainCargoTram.class, "entity_ntm_cargo_tram", 250, false);
		addEntity(TrainCargoTramTrailer.class, "entity_ntm_cargo_tram_trailer", 250, false);
		addEntity(TrainTunnelBore.class, "entity_ntm_tunnel_bore", 250, false);

		addEntity(EntityDisperserCanister.class, "entity_disperser", 250);
		addEntity(EntityWaypoint.class, "entity_waypoint", 250, false);

		addMob(EntityCreeperNuclear.class, "entity_mob_nuclear_creeper", 0x204131, 0x75CE00);
		addMob(EntityCreeperTainted.class, "entity_mob_tainted_creeper", 0x813b9b, 0xd71fdd);
		addMob(EntityCreeperPhosgene.class, "entity_mob_phosgene_creeper", 0xE3D398, 0xB8A06B);
		addMob(EntityCreeperVolatile.class, "entity_mob_volatile_creeper", 0xC28153, 0x4D382C);
		addMob(EntityCreeperGold.class, "entity_mob_gold_creeper", 0xECC136, 0x9E8B3E);
		addMob(EntityHunterChopper.class, "entity_mob_hunter_chopper", 0x000020, 0x2D2D72);
		addMob(EntityCyberCrab.class, "entity_cyber_crab", 0xAAAAAA, 0x444444);
		addMob(EntityTeslaCrab.class, "entity_tesla_crab", 0xAAAAAA, 0x440000);
		addMob(EntityTaintCrab.class, "entity_taint_crab", 0xAAAAAA, 0xFF00FF);
		addMob(EntityMaskMan.class, "entity_mob_mask_man", 0x818572, 0xC7C1B7);
		addMob(EntityDuck.class, "entity_fucc_a_ducc", 0xd0d0d0, 0xFFBF00);
		addMob(EntityQuackos.class, "entity_elder_one", 0xd0d0d0, 0xFFBF00);
		addMob(EntityPigeon.class, "entity_pigeon", 0xC8C9CD, 0x858894);
		addMob(EntityFBI.class, "entity_ntm_fbi", 0x008000, 0x404040);
		addMob(EntityFBIDrone.class, "entity_ntm_fbi_drone", 0x008000, 0x404040);
		addMob(EntityRADBeast.class, "entity_ntm_radiation_blaze", 0x303030, 0x008000);
		addMob(EntitySiegeZombie.class, "entity_meme_zombie", 0x303030, 0x008000);
		addMob(EntitySiegeSkeleton.class, "entity_meme_skeleton", 0x303030, 0x000080);
		addMob(EntitySiegeUFO.class, "entity_meme_ufo", 0x303030, 0x800000);
		addMob(EntitySiegeCraft.class, "entity_meme_craft", 0x303030, 0x808000);
		addMob(EntityGlyphid.class, "entity_glyphid", 0x724A21, 0xD2BB72);
		addMob(EntityGlyphidBrawler.class, "entity_glyphid_brawler", 0x273038, 0xD2BB72);
		addMob(EntityGlyphidBehemoth.class, "entity_glyphid_behemoth", 0x267F00, 0xD2BB72);
		addMob(EntityGlyphidBrenda.class, "entity_glyphid_brenda", 0x4FC0C0, 0xA0A0A0);
		addMob(EntityGlyphidBombardier.class, "entity_glyphid_bombardier", 0xDDD919, 0xDBB79D);
		addMob(EntityGlyphidBlaster.class, "entity_glyphid_blaster", 0xD83737, 0xDBB79D);
		addMob(EntityGlyphidScout.class, "entity_glyphid_scout", 0x273038, 0xB9E36B);
		addMob(EntityGlyphidNuclear.class, "entity_glyphid_nuclear", 0x267F00, 0xA0A0A0);
		addMob(EntityGlyphidDigger.class, "entity_glyphid_digger", 0x273038, 0x724A21);
		addMob(EntityPlasticBag.class, "entity_plastic_bag", 0xd0d0d0, 0x808080);
		addMob(EntityParasiteMaggot.class, "entity_parasite_maggot", 0xd0d0d0, 0x808080);
		addMob(EntityDummy.class, "entity_ntm_test_dummy", 0xffffff, 0x000000);

		addSpawn(EntityCreeperPhosgene.class, 5, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		addSpawn(EntityCreeperVolatile.class, 10, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		addSpawn(EntityCreeperGold.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		addSpawn(EntityPlasticBag.class, 1, 1, 3, EnumCreatureType.waterCreature, BiomeDictionary.getBiomesForType(Type.OCEAN));
		addSpawn(EntityPigeon.class, 1, 5, 10, EnumCreatureType.creature, BiomeDictionary.getBiomesForType(Type.PLAINS));
		
		int id = 0;
		for(Quartet<Class<? extends Entity>, String, Integer, Boolean> entry : entityMappings) {
			EntityRegistry.registerModEntity(entry.getW(), entry.getX(), id++, MainRegistry.instance, entry.getY(), 1, entry.getZ());
		}
		
		for(Quartet<Class<? extends Entity>, String, Integer, Integer> entry : mobMappings) {
			EntityRegistry.registerGlobalEntityID(entry.getW(), entry.getX(), EntityRegistry.findGlobalUniqueEntityId(), entry.getY(), entry.getZ());
		}
	}
	
	private static void addEntity(Class<? extends Entity> clazz, String name, int trackingRange) {
		addEntity(clazz, name, trackingRange, true);
	}
	
	private static void addEntity(Class<? extends Entity> clazz, String name, int trackingRange, boolean velocityUpdates) {
		entityMappings.add(new Quartet(clazz, name, trackingRange, velocityUpdates));
	}
	
	private static void addMob(Class<? extends Entity> clazz, String name, int color1, int color2) {
		mobMappings.add(new Quartet(clazz, name, color1, color2));
	}

	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
		
		for(BiomeGenBase biome : biomes) {
			
			if(biome == null) continue;
			
			List<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature);

			for(SpawnListEntry entry : spawns) {
				// Adjusting an existing spawn entry
				if(entry.entityClass == entityClass) {
					entry.itemWeight = weightedProb;
					entry.minGroupCount = min;
					entry.maxGroupCount = max;
					break;
				}
			}

			spawns.add(new SpawnListEntry(entityClass, weightedProb, min, max));
		}
	}
}
