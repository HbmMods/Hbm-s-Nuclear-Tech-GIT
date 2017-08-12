package com.hbm.main;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityTestMissile;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityBaleflare;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.entity.projectile.EntityNightmareBlast;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.items.ModItems;
import com.hbm.render.entity.BSmokeRenderer;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.entity.ElectricityRenderer;
import com.hbm.render.entity.GasFlameRenderer;
import com.hbm.render.entity.GasRenderer;
import com.hbm.render.entity.ModEffectRenderer;
import com.hbm.render.entity.RenderBaleflare;
import com.hbm.render.entity.RenderBeam;
import com.hbm.render.entity.RenderBeam2;
import com.hbm.render.entity.RenderBeam3;
import com.hbm.render.entity.RenderBeam4;
import com.hbm.render.entity.RenderBigNuke;
import com.hbm.render.entity.RenderBlackHole;
import com.hbm.render.entity.RenderBombletSelena;
import com.hbm.render.entity.RenderBombletTheta;
import com.hbm.render.entity.RenderChopperMine;
import com.hbm.render.entity.RenderCloudFleija;
import com.hbm.render.entity.RenderCyberCrab;
import com.hbm.render.entity.RenderEMPBlast;
import com.hbm.render.entity.RenderEmpty;
import com.hbm.render.entity.RenderFallout;
import com.hbm.render.entity.RenderFireball;
import com.hbm.render.entity.RenderFlare;
import com.hbm.render.entity.RenderHunterChopper;
import com.hbm.render.entity.RenderLN2;
import com.hbm.render.entity.RenderMinecartTest;
import com.hbm.render.entity.RenderMiniMIRV;
import com.hbm.render.entity.RenderMiniNuke;
import com.hbm.render.entity.RenderMirv;
import com.hbm.render.entity.RenderMissileDoomsday;
import com.hbm.render.entity.RenderMissileGeneric;
import com.hbm.render.entity.RenderMissileHuge;
import com.hbm.render.entity.RenderMissileMirv;
import com.hbm.render.entity.RenderMissileNuclear;
import com.hbm.render.entity.RenderMissileStrong;
import com.hbm.render.entity.RenderMissileThermo;
import com.hbm.render.entity.RenderNoCloud;
import com.hbm.render.entity.RenderNuclearCreeper;
import com.hbm.render.entity.RenderOminousBullet;
import com.hbm.render.entity.RenderRainbow;
import com.hbm.render.entity.RenderRocket;
import com.hbm.render.entity.RenderRubble;
import com.hbm.render.entity.RenderShrapnel;
import com.hbm.render.entity.RenderSmallNukeAlt;
import com.hbm.render.entity.SSmokeRenderer;
import com.hbm.render.entity.SpillRenderer;
import com.hbm.render.item.ItemRenderBFLauncher;
import com.hbm.render.item.ItemRenderBaleflare;
import com.hbm.render.item.ItemRenderBigSword;
import com.hbm.render.item.ItemRenderCryolator;
import com.hbm.render.item.ItemRenderDecoBlock;
import com.hbm.render.item.ItemRenderEMPRay;
import com.hbm.render.item.ItemRenderFatMan;
import com.hbm.render.item.ItemRenderImmolator;
import com.hbm.render.item.ItemRenderMIRV;
import com.hbm.render.item.ItemRenderMIRVLauncher;
import com.hbm.render.item.ItemRenderMP;
import com.hbm.render.item.ItemRenderMP40;
import com.hbm.render.item.ItemRenderMiniNuke;
import com.hbm.render.item.ItemRenderMultitool;
import com.hbm.render.item.ItemRenderOSIPR;
import com.hbm.render.item.ItemRenderOverkill;
import com.hbm.render.item.ItemRenderPoleTop;
import com.hbm.render.item.ItemRenderRedstoneSword;
import com.hbm.render.item.ItemRenderRevolver;
import com.hbm.render.item.ItemRenderRevolverCursed;
import com.hbm.render.item.ItemRenderRevolverGold;
import com.hbm.render.item.ItemRenderRevolverInverted;
import com.hbm.render.item.ItemRenderRevolverIron;
import com.hbm.render.item.ItemRenderRevolverLead;
import com.hbm.render.item.ItemRenderRevolverNightmare;
import com.hbm.render.item.ItemRenderRevolverSchrabidium;
import com.hbm.render.item.ItemRenderRpg;
import com.hbm.render.item.ItemRenderSatelliteReceiver;
import com.hbm.render.item.ItemRenderSteelPoles;
import com.hbm.render.item.ItemRenderTapeRecorder;
import com.hbm.render.item.ItemRenderTestBombAdvanced;
import com.hbm.render.item.ItemRenderTestContainer;
import com.hbm.render.item.ItemRenderUboinik;
import com.hbm.render.item.ItemRenderXVL1456;
import com.hbm.render.item.ItemRenderZOMG;
import com.hbm.render.tileentity.RenderAssembler;
import com.hbm.render.tileentity.RenderBombMulti;
import com.hbm.render.tileentity.RenderCable;
import com.hbm.render.tileentity.RenderCentrifuge;
import com.hbm.render.tileentity.RenderChemplant;
import com.hbm.render.tileentity.RenderCrashedBomb;
import com.hbm.render.tileentity.RenderCyclotron;
import com.hbm.render.tileentity.RenderDecoBlock;
import com.hbm.render.tileentity.RenderDecoBlockAlt;
import com.hbm.render.tileentity.RenderDerrick;
import com.hbm.render.tileentity.RenderFlamerTurret;
import com.hbm.render.tileentity.RenderFluidDuct;
import com.hbm.render.tileentity.RenderFluidTank;
import com.hbm.render.tileentity.RenderGasDuct;
import com.hbm.render.tileentity.RenderGasFlare;
import com.hbm.render.tileentity.RenderHeavyTurret;
import com.hbm.render.tileentity.RenderIGenerator;
import com.hbm.render.tileentity.RenderLaunchPadTier1;
import com.hbm.render.tileentity.RenderLightTurret;
import com.hbm.render.tileentity.RenderMiningDrill;
import com.hbm.render.tileentity.RenderNukeBoy;
import com.hbm.render.tileentity.RenderNukeCustom;
import com.hbm.render.tileentity.RenderNukeFleija;
import com.hbm.render.tileentity.RenderNukeGadget;
import com.hbm.render.tileentity.RenderNukeMan;
import com.hbm.render.tileentity.RenderNukeMike;
import com.hbm.render.tileentity.RenderNukePrototype;
import com.hbm.render.tileentity.RenderNukeTsar;
import com.hbm.render.tileentity.RenderOilDuct;
import com.hbm.render.tileentity.RenderPoleSatelliteReceiver;
import com.hbm.render.tileentity.RenderPoleTop;
import com.hbm.render.tileentity.RenderPuF6Tank;
import com.hbm.render.tileentity.RenderPylon;
import com.hbm.render.tileentity.RenderRedBarrel;
import com.hbm.render.tileentity.RenderRefinery;
import com.hbm.render.tileentity.RenderRocketTurret;
import com.hbm.render.tileentity.RenderRotationTester;
import com.hbm.render.tileentity.RenderSteelPoles;
import com.hbm.render.tileentity.RenderStructureMaker;
import com.hbm.render.tileentity.RenderTapeRecorder;
import com.hbm.render.tileentity.RenderTauTurret;
import com.hbm.render.tileentity.RenderTestBombAdvanced;
import com.hbm.render.tileentity.RenderTestContainer;
import com.hbm.render.tileentity.RenderTestMissile;
import com.hbm.render.tileentity.RenderTestRender;
import com.hbm.render.tileentity.RenderUF6Tank;
import com.hbm.render.tileentity.RenderYellowBarrel;
import com.hbm.render.tileentity.RendererObjTester;
import com.hbm.tileentity.TileEntityBombMulti;
import com.hbm.tileentity.TileEntityCable;
import com.hbm.tileentity.TileEntityCrashedBomb;
import com.hbm.tileentity.TileEntityDecoBlock;
import com.hbm.tileentity.TileEntityDecoBlockAlt;
import com.hbm.tileentity.TileEntityDecoBlockAltF;
import com.hbm.tileentity.TileEntityDecoBlockAltG;
import com.hbm.tileentity.TileEntityDecoBlockAltW;
import com.hbm.tileentity.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.tileentity.TileEntityDecoPoleTop;
import com.hbm.tileentity.TileEntityDecoSteelPoles;
import com.hbm.tileentity.TileEntityDecoTapeRecorder;
import com.hbm.tileentity.TileEntityFluidDuct;
import com.hbm.tileentity.TileEntityGasDuct;
import com.hbm.tileentity.TileEntityMachineIGenerator;
import com.hbm.tileentity.TileEntityMachineMiningDrill;
import com.hbm.tileentity.TileEntityMachineOilWell;
import com.hbm.tileentity.TileEntityLaunchPad;
import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineCentrifuge;
import com.hbm.tileentity.TileEntityMachineChemplant;
import com.hbm.tileentity.TileEntityMachineCyclotron;
import com.hbm.tileentity.TileEntityMachineFluidTank;
import com.hbm.tileentity.TileEntityMachineGasFlare;
import com.hbm.tileentity.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.TileEntityMachineRefinery;
import com.hbm.tileentity.TileEntityMachineUF6Tank;
import com.hbm.tileentity.TileEntityNukeBoy;
import com.hbm.tileentity.TileEntityNukeCustom;
import com.hbm.tileentity.TileEntityNukeFleija;
import com.hbm.tileentity.TileEntityNukeGadget;
import com.hbm.tileentity.TileEntityNukeMan;
import com.hbm.tileentity.TileEntityNukeMike;
import com.hbm.tileentity.TileEntityNukePrototype;
import com.hbm.tileentity.TileEntityNukeTsar;
import com.hbm.tileentity.TileEntityObjTester;
import com.hbm.tileentity.TileEntityOilDuct;
import com.hbm.tileentity.TileEntityPylonRedWire;
import com.hbm.tileentity.TileEntityRedBarrel;
import com.hbm.tileentity.TileEntityRotationTester;
import com.hbm.tileentity.TileEntityStructureMarker;
import com.hbm.tileentity.TileEntityTestBombAdvanced;
import com.hbm.tileentity.TileEntityTestContainer;
import com.hbm.tileentity.TileEntityTestRender;
import com.hbm.tileentity.TileEntityTurretFlamer;
import com.hbm.tileentity.TileEntityTurretHeavy;
import com.hbm.tileentity.TileEntityTurretLight;
import com.hbm.tileentity.TileEntityTurretRocket;
import com.hbm.tileentity.TileEntityTurretTau;
import com.hbm.tileentity.TileEntityYellowBarrel;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy
{
	@Override
	public void registerRenderInfo()
	{
		MinecraftForge.EVENT_BUS.register(new ModEventHandlerClient());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestRender.class, new RenderTestRender());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestContainer.class, new RenderTestContainer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.test_container), new ItemRenderTestContainer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestBombAdvanced.class, new RenderTestBombAdvanced());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.test_bomb_advanced), new ItemRenderTestBombAdvanced());
		
		MinecraftForgeClient.registerItemRenderer(ModItems.redstone_sword, new ItemRenderRedstoneSword());
		MinecraftForgeClient.registerItemRenderer(ModItems.big_sword, new ItemRenderBigSword());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRotationTester.class, new RenderRotationTester());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObjTester.class, new RendererObjTester());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeGadget.class, new RenderNukeGadget());
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.nuke_gadget), new ItemRenderNukeGadget());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeBoy.class, new RenderNukeBoy());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeCustom.class, new RenderNukeCustom());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCentrifuge.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMan.class, new RenderNukeMan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUF6Tank.class, new RenderUF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePuF6Tank.class, new RenderPuF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineIGenerator.class, new RenderIGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCyclotron.class, new RenderCyclotron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineOilWell.class, new RenderDerrick());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasFlare.class, new RenderGasFlare());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningDrill.class, new RenderMiningDrill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new RenderAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChemplant.class, new RenderChemplant());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFluidTank.class, new RenderFluidTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRefinery.class, new RenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHeavy.class, new RenderHeavyTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretRocket.class, new RenderRocketTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretLight.class, new RenderLightTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFlamer.class, new RenderFlamerTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTau.class, new RenderTauTurret());

		//RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderSnowball(ModItems.man_core));

		MinecraftForgeClient.registerItemRenderer(ModItems.gun_rpg, new ItemRenderRpg());
		//MinecraftForgeClient.registerItemRenderer(ModItems.gun_rpg_ammo, new ItemRenderRocket());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombMulti.class, new RenderBombMulti());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMike.class, new RenderNukeMike());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeTsar.class, new RenderNukeTsar());

		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGeneric.class, new RenderSnowball(ModItems.grenade_generic));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeStrong.class, new RenderSnowball(ModItems.grenade_strong));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFrag.class, new RenderSnowball(ModItems.grenade_frag));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFire.class, new RenderSnowball(ModItems.grenade_fire));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeCluster.class, new RenderSnowball(ModItems.grenade_cluster));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFlare.class, new RenderFlare());
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeElectric.class, new RenderSnowball(ModItems.grenade_electric));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadePoison.class, new RenderSnowball(ModItems.grenade_poison));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGas.class, new RenderSnowball(ModItems.grenade_gas));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSchrabidium.class, new RenderSnowball(ModItems.grenade_schrabidium));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeNuke.class, new RenderSnowball(ModItems.grenade_nuke));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeNuclear.class, new RenderSnowball(ModItems.grenade_nuclear));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadePlasma.class, new RenderSnowball(ModItems.grenade_plasma));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeTau.class, new RenderSnowball(ModItems.grenade_tau));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeLemon.class, new RenderSnowball(ModItems.grenade_lemon));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeMk2.class, new RenderSnowball(ModItems.grenade_mk2));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeZOMG.class, new RenderSnowball(ModItems.grenade_zomg));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeASchrab.class, new RenderSnowball(ModItems.grenade_aschrab));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadePulse.class, new RenderSnowball(ModItems.grenade_pulse));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeShrapnel.class, new RenderSnowball(ModItems.grenade_shrapnel));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBlackHole.class, new RenderSnowball(ModItems.grenade_black_hole));

		RenderingRegistry.registerEntityRenderingHandler(EntitySchrab.class, new RenderFlare());

	    RenderingRegistry.registerEntityRenderingHandler(EntityTestMissile.class, new RenderTestMissile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeFleija.class, new RenderNukeFleija());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrashedBomb.class, new RenderCrashedBomb());
		
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeAlt());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudBig.class, new RenderBigNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleija.class, new RenderCloudFleija());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudNoShroom.class, new RenderNoCloud());
	    RenderingRegistry.registerEntityRenderingHandler(EntityFalloutRain.class, new RenderFallout());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, new RenderBlackHole());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoTapeRecorder.class, new RenderTapeRecorder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoSteelPoles.class, new RenderSteelPoles());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoPoleTop.class, new RenderPoleTop());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoPoleSatelliteReceiver.class, new RenderPoleSatelliteReceiver());
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.tape_recorder), new ItemRenderTapeRecorder());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_poles), new ItemRenderSteelPoles());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.pole_top), new ItemRenderPoleTop());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), new ItemRenderSatelliteReceiver());

		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver, new ItemRenderRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_iron, new ItemRenderRevolverIron());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_gold, new ItemRenderRevolverGold());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_lead, new ItemRenderRevolverLead());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_schrabidium, new ItemRenderRevolverSchrabidium());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_cursed, new ItemRenderRevolverCursed());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare, new ItemRenderRevolverNightmare(ModItems.gun_revolver_nightmare));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare2, new ItemRenderRevolverNightmare(ModItems.gun_revolver_nightmare2));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman, new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mirv, new ItemRenderMIRVLauncher());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bf, new ItemRenderBFLauncher());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_xvl1456, new ItemRenderXVL1456());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_zomg, new ItemRenderZOMG());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_osipr, new ItemRenderOSIPR());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mp, new ItemRenderMP());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_inverted, new ItemRenderRevolverInverted());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mp40, new ItemRenderMP40());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_emp, new ItemRenderEMPRay());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_immolator, new ItemRenderImmolator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_cryolator, new ItemRenderCryolator());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uboinik, new ItemRenderUboinik());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_jack, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_spark, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_hp, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_euthanasia, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_defabricator, new ItemRenderOverkill());

		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_dig, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_silk, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_ext, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_miner, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_hit, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_beam, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_sky, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_mega, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_joule, new ItemRenderMultitool());
		MinecraftForgeClient.registerItemRenderer(ModItems.multitool_decon, new ItemRenderMultitool());

		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, new RenderMiniNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMiniMIRV.class, new RenderMiniMIRV());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBaleflare.class, new RenderBaleflare());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman_ammo, new ItemRenderMiniNuke());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mirv_ammo, new ItemRenderMIRV());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bf_ammo, new ItemRenderBaleflare());
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbow.class, new RenderRainbow());
		RenderingRegistry.registerEntityRenderingHandler(EntityNightmareBlast.class, new RenderOminousBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityFire.class, new RenderFireball(ModItems.energy_ball));
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBeam.class, new RenderBeam());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeam.class, new RenderBeam2());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinerBeam.class, new RenderBeam3());
		RenderingRegistry.registerEntityRenderingHandler(EntitySparkBeam.class, new RenderBeam4());
		RenderingRegistry.registerEntityRenderingHandler(EntityLN2.class, new RenderLN2(ModItems.energy_ball));

		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartTest.class, new RenderMinecartTest());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukePrototype.class, new RenderNukePrototype());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedBarrel.class, new RenderRedBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityYellowBarrel.class, new RenderYellowBarrel());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunchPadTier1());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilDuct.class, new RenderOilDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasDuct.class, new RenderGasDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidDuct.class, new RenderFluidDuct());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylonRedWire.class, new RenderPylon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStructureMarker.class, new RenderStructureMaker());

	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileCluster.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBunkerBuster.class, new RenderMissileGeneric());

	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileClusterStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBusterStrong.class, new RenderMissileStrong());

	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileRain.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileDrill.class, new RenderMissileHuge());

	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileNuclear.class, new RenderMissileNuclear());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileMirv.class, new RenderMissileMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMIRV.class, new RenderMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileDoomsday.class, new RenderMissileDoomsday());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBombletTheta.class, new RenderBombletTheta());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBombletSelena.class, new RenderBombletSelena());

	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileEndo.class, new RenderMissileThermo());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileExo.class, new RenderMissileThermo());
	    
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlock.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAlt.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltG.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltW.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltF.class, new RenderDecoBlockAlt());

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_wall), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_corner), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_roof), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_beam), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_scaffold), new ItemRenderDecoBlock());

	    RenderingRegistry.registerEntityRenderingHandler(EntityNuclearCreeper.class, new RenderNuclearCreeper());
	    RenderingRegistry.registerEntityRenderingHandler(EntityHunterChopper.class, new RenderHunterChopper());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCyberCrab.class, new RenderCyberCrab());

	    RenderingRegistry.registerEntityRenderingHandler(EntityChopperMine.class, new RenderChopperMine());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRubble.class, new RenderRubble());
	    RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel());
	    RenderingRegistry.registerEntityRenderingHandler(EntityOilSpill.class, new RenderEmpty());

	    RenderingRegistry.registerEntityRenderingHandler(EntitySmokeFX.class, new ModEffectRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBSmokeFX.class, new BSmokeRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDSmokeFX.class, new DSmokeRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntitySSmokeFX.class, new SSmokeRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityOilSpillFX.class, new SpillRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGasFX.class, new GasRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGasFlameFX.class, new GasFlameRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityCombineBall.class, new RenderSnowball(ModItems.energy_ball));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDischarge.class, new ElectricityRenderer(ModItems.discharge));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEMPBlast.class, new RenderEMPBlast());
	    
		RenderingRegistry.addNewArmourRendererPrefix("5");
		RenderingRegistry.addNewArmourRendererPrefix("6");
		RenderingRegistry.addNewArmourRendererPrefix("7");
		RenderingRegistry.addNewArmourRendererPrefix("8");
		RenderingRegistry.addNewArmourRendererPrefix("9");
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() {
		
	}
}

