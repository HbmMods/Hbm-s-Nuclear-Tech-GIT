 package com.hbm.main;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.mob.sodtekhnologiyah.EntityBallsOTronSegment;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.items.ModItems;
import com.hbm.particle.*;
import com.hbm.render.block.*;
import com.hbm.render.entity.*;
import com.hbm.render.entity.effect.*;
import com.hbm.render.entity.item.*;
import com.hbm.render.entity.mob.*;
import com.hbm.render.entity.projectile.*;
import com.hbm.render.entity.rocket.*;
import com.hbm.render.item.*;
import com.hbm.render.item.block.*;
import com.hbm.render.item.weapon.*;
import com.hbm.render.loader.HmfModelLoader;
import com.hbm.render.tileentity.*;
import com.hbm.render.util.MissilePart;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.AudioWrapperClient;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void registerTileEntitySpecialRenderer() {
		//test crap
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestRender.class, new RenderTestRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestBombAdvanced.class, new RenderTestBombAdvanced());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRotationTester.class, new RenderRotationTester());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObjTester.class, new RendererObjTester());
	    //deco
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoPoleSatelliteReceiver.class, new RenderPoleSatelliteReceiver());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlock.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBroadcaster.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGeiger.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadioRec.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadiobox.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBomber.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSatDock.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAlt.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltG.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltW.class, new RenderDecoBlockAlt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlockAltF.class, new RenderDecoBlockAlt());
		//bombs
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeGadget.class, new RenderNukeGadget());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeBoy.class, new RenderNukeBoy());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeCustom.class, new RenderNukeCustom());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeSolinium.class, new RenderNukeSolinium());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeN2.class, new RenderNukeN2());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMan.class, new RenderNukeMan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeN45.class, new RenderNukeN45());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeBalefire.class, new RenderNukeFstbmb());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBombMulti.class, new RenderBombMulti());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMike.class, new RenderNukeMike());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeTsar.class, new RenderNukeTsar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeFleija.class, new RenderNukeFleija());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrashedBomb.class, new RenderCrashedBomb());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukePrototype.class, new RenderNukePrototype());
		//turrets
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHeavy.class, new RenderHeavyTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretRocket.class, new RenderRocketTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretLight.class, new RenderLightTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFlamer.class, new RenderFlamerTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTau.class, new RenderTauTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretSpitfire.class, new RenderSpitfireTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretCIWS.class, new RenderCIWSTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretCheapo.class, new RenderCheapoTurret());
		//mines
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLandmine.class, new RenderLandmine());
		//cel prime
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelPrime.class, new RenderCelPrimeTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelPrimeTerminal.class, new RenderCelPrimePart());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelPrimeBattery.class, new RenderCelPrimePart());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelPrimePort.class, new RenderCelPrimePart());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelPrimeTanks.class, new RenderCelPrimePart());
		//machines
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCentrifuge.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasCent.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUF6Tank.class, new RenderUF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePuF6Tank.class, new RenderPuF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineIGenerator.class, new RenderIGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCyclotron.class, new RenderCyclotron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineOilWell.class, new RenderDerrick());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasFlare.class, new RenderGasFlare());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningDrill.class, new RenderMiningDrill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningLaser.class, new RenderLaserMiner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new RenderAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChemplant.class, new RenderChemplant());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFluidTank.class, new RenderFluidTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRefinery.class, new RenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePumpjack.class, new RenderPumpjack());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineTurbofan.class, new RenderTurbofan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePress.class, new RenderPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineEPress.class, new RenderEPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadGen.class, new RenderRadGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSeleniumEngine.class, new RenderSelenium());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReactorSmall.class, new RenderSmallReactor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineShredderLarge.class, new RenderMachineShredder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTesla.class, new RenderTesla());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new RenderFluidBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCrystallizer.class, new RenderCrystallizer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicrowave.class, new RenderMicrowave());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRTG.class, new RenderRTG());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiniRTG.class, new RenderRTG());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFF.class, new RenderForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFENSU.class, new RenderFENSU());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineLargeTurbine.class, new RenderBigTurbine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReactor.class, new RenderBreeder());
		//AMS
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSBase.class, new RenderAMSBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSEmitter.class, new RenderAMSEmitter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSLimiter.class, new RenderAMSLimiter());
		//DFC
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreEmitter.class, new RenderCoreComponent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreReceiver.class, new RenderCoreComponent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreInjector.class, new RenderCoreComponent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreStabilizer.class, new RenderCoreComponent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCore.class, new RenderCore());
		//missile blocks
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunchPadTier1());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMissileAssembly.class, new RenderMissileAssembly());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompactLauncher.class, new RenderCompactLauncher());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchTable.class, new RenderLaunchTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoyuzLauncher.class, new RenderSoyuzLauncher());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoyuzCapsule.class, new RenderCapsule());
		//network
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilDuct.class, new RenderOilDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasDuct.class, new RenderGasDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidDuct.class, new RenderFluidDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRFDuct.class, new RenderRFCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylonRedWire.class, new RenderPylon());
		//multiblocks
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStructureMarker.class, new RenderStructureMaker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMultiblock.class, new RenderMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoyuzStruct.class, new RenderSoyuzMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityITERStruct.class, new RenderITERMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlasmaStruct.class, new RenderPlasmaMultiblock());
		//ITER
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityITER.class, new RenderITER());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePlasmaHeater.class, new RenderPlasmaHeater());
		//doors
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlastDoor.class, new RenderBlastDoor());
	}

	@Override
	public void registerItemRenderer() {
		
		ItemRenderLibrary.init();
		
		for(Entry<Item, ItemRenderBase> entry : ItemRenderLibrary.renderers.entrySet())
			MinecraftForgeClient.registerItemRenderer(entry.getKey(), entry.getValue());
		
		//test crap
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.test_container), new ItemRenderTestContainer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.test_bomb_advanced), new ItemRenderTestBombAdvanced());
		//templates
		MinecraftForgeClient.registerItemRenderer(ModItems.assembly_template, new ItemRenderTemplate());
		MinecraftForgeClient.registerItemRenderer(ModItems.chemistry_template, new ItemRenderTemplate());
		//swords and hammers
		MinecraftForgeClient.registerItemRenderer(ModItems.redstone_sword, new ItemRenderRedstoneSword());
		MinecraftForgeClient.registerItemRenderer(ModItems.big_sword, new ItemRenderBigSword());
		MinecraftForgeClient.registerItemRenderer(ModItems.shimmer_sledge, new ItemRenderShim());
		MinecraftForgeClient.registerItemRenderer(ModItems.shimmer_axe, new ItemRenderShim());
		MinecraftForgeClient.registerItemRenderer(ModItems.stopsign, new ItemRenderShim());
		MinecraftForgeClient.registerItemRenderer(ModItems.sopsign, new ItemRenderShim());
		MinecraftForgeClient.registerItemRenderer(ModItems.chernobylsign, new ItemRenderShim());
		MinecraftForgeClient.registerItemRenderer(ModItems.wood_gavel, new ItemRenderGavel());
		MinecraftForgeClient.registerItemRenderer(ModItems.lead_gavel, new ItemRenderGavel());
		MinecraftForgeClient.registerItemRenderer(ModItems.diamond_gavel, new ItemRenderGavel());
		MinecraftForgeClient.registerItemRenderer(ModItems.mese_gavel, new ItemRenderGavel());
		//guns
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_rpg, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_karl, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_panzerschreck, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stinger, new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_skystinger, new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver, new ItemRenderRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_saturnite, new ItemRenderRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_iron, new ItemRenderRevolverIron());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_gold, new ItemRenderRevolverGold());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_lead, new ItemRenderRevolverLead());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_schrabidium, new ItemRenderRevolverSchrabidium());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_cursed, new ItemRenderRevolverCursed());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare, new ItemRenderRevolverNightmare(ModItems.gun_revolver_nightmare));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare2, new ItemRenderRevolverNightmare(ModItems.gun_revolver_nightmare2));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman, new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_proto, new ItemRenderFatMan());
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
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_dash, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_twigun, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_pip, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nopip, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_blackjack, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_silver, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_red, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_dampfmaschine, new ItemRenderBullshit());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action_dark, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action_green, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action_sonata, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action_saturnite, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_b92, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_b93, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_silencer, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_saturnite, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_saturnite_silencer, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_calamity, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_calamity_dual, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_minigun, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_avenger, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lacunae, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_folly, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_brimstone, new ItemRenderObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_hk69, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_deagle, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_supershotgun, new ItemRenderWeaponShotty());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_ks23, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flechette, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_quadro, new ItemRenderWeaponQuadro());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_sauer, new ItemRenderWeaponSauer());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_vortex, new ItemRenderWeaponVortex());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_thompson, new ItemRenderWeaponThompson());
		//ammo
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman_ammo, new ItemRenderMiniNuke());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mirv_ammo, new ItemRenderMIRV());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bf_ammo, new ItemRenderBaleflare());
		//multitool
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
		//blocks
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_wall), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_corner), new ItemRenderDecoBlock());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.steel_roof), new ItemRenderDecoBlock());
	}

	@Override
	public void registerEntityRenderer() {
		//projectiles
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderSnowball(ModItems.man_core));
		RenderingRegistry.registerEntityRenderingHandler(EntitySchrab.class, new RenderFlare());
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderRocket());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletBase.class, new RenderBullet());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, new RenderMiniNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMiniMIRV.class, new RenderMiniMIRV());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBaleflare.class, new RenderBaleflare());
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbow.class, new RenderRainbow());
		RenderingRegistry.registerEntityRenderingHandler(EntityNightmareBlast.class, new RenderOminousBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityFire.class, new RenderFireball(ModItems.energy_ball));
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBeam.class, new RenderBeam());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeam.class, new RenderBeam2());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinerBeam.class, new RenderBeam3());
		RenderingRegistry.registerEntityRenderingHandler(EntitySparkBeam.class, new RenderBeam4());
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveBeam.class, new RenderBeam5());
		RenderingRegistry.registerEntityRenderingHandler(EntityModBeam.class, new RenderBeam6());
		RenderingRegistry.registerEntityRenderingHandler(EntityLN2.class, new RenderLN2(ModItems.energy_ball));
		RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBombletTheta.class, new RenderBombletTheta());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBombletZeta.class, new RenderBombletTheta());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBombletSelena.class, new RenderBombletSelena());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMeteor.class, new RenderMeteor());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBoxcar.class, new RenderBoxcar());
	    RenderingRegistry.registerEntityRenderingHandler(EntityDuchessGambit.class, new RenderBoxcar());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBuilding.class, new RenderBoxcar());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBomber.class, new RenderBomber());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBurningFOEQ.class, new RenderFOEQ());
	    RenderingRegistry.registerEntityRenderingHandler(EntityFallingNuke.class, new RenderFallingNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMinerRocket.class, new RenderMinerRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBobmazon.class, new RenderMinerRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTom.class, new RenderTom());
	    RenderingRegistry.registerEntityRenderingHandler(EntityAAShell.class, new RenderMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRocketHoming.class, new RenderSRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityChopperMine.class, new RenderChopperMine());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRubble.class, new RenderRubble());
	    RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel());
	    RenderingRegistry.registerEntityRenderingHandler(EntityOilSpill.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityWaterSplash.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBeamVortex.class, new RenderVortexBeam());
		//grenades
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
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeMk2.class, new RenderGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeZOMG.class, new RenderSnowball(ModItems.grenade_zomg));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeASchrab.class, new RenderGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadePulse.class, new RenderSnowball(ModItems.grenade_pulse));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeShrapnel.class, new RenderSnowball(ModItems.grenade_shrapnel));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBlackHole.class, new RenderSnowball(ModItems.grenade_black_hole));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGascan.class, new RenderSnowball(ModItems.grenade_gascan));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeCloud.class, new RenderSnowball(ModItems.grenade_cloud));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadePC.class, new RenderSnowball(ModItems.grenade_pink_cloud));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSmart.class, new RenderSnowball(ModItems.grenade_smart));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeMIRV.class, new RenderSnowball(ModItems.grenade_mirv));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBreach.class, new RenderSnowball(ModItems.grenade_breach));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBurst.class, new RenderSnowball(ModItems.grenade_burst));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFGeneric.class, new RenderSnowball(ModItems.grenade_if_generic));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFHE.class, new RenderSnowball(ModItems.grenade_if_he));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFBouncy.class, new RenderSnowball(ModItems.grenade_if_bouncy));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFSticky.class, new RenderSnowball(ModItems.grenade_if_sticky));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFImpact.class, new RenderSnowball(ModItems.grenade_if_impact));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFIncendiary.class, new RenderSnowball(ModItems.grenade_if_incendiary));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFToxic.class, new RenderSnowball(ModItems.grenade_if_toxic));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFConcussion.class, new RenderSnowball(ModItems.grenade_if_concussion));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFBrimstone.class, new RenderSnowball(ModItems.grenade_if_brimstone));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFMystery.class, new RenderSnowball(ModItems.grenade_if_mystery));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFSpark.class, new RenderSnowball(ModItems.grenade_if_spark));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFHopwire.class, new RenderSnowball(ModItems.grenade_if_hopwire));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeIFNull.class, new RenderSnowball(ModItems.grenade_if_null));
		//missiles
	    RenderingRegistry.registerEntityRenderingHandler(EntityTestMissile.class, new RenderTestMissile());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileCustom.class, new RenderMissileCustom());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileCluster.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBunkerBuster.class, new RenderMissileGeneric());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileClusterStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBusterStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMPStrong.class, new RenderMissileStrong());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileRain.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileDrill.class, new RenderMissileHuge());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileNuclear.class, new RenderMissileNuclear());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileMirv.class, new RenderMissileMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMIRV.class, new RenderMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileDoomsday.class, new RenderMissileDoomsday());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCarrier.class, new RenderCarrierMissile());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBooster.class, new RenderBoosterMissile());
	    RenderingRegistry.registerEntityRenderingHandler(EntitySoyuz.class, new RenderSoyuz());
	    RenderingRegistry.registerEntityRenderingHandler(EntitySoyuzCapsule.class, new RenderSoyuzCapsule());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileTaint.class, new RenderMissileTaint());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileMicro.class, new RenderMissileTaint());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileBHole.class, new RenderMissileTaint());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileSchrabidium.class, new RenderMissileTaint());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMP.class, new RenderMissileTaint());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileEndo.class, new RenderMissileThermo());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileExo.class, new RenderMissileThermo());
		//effects
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK4());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudBig.class, new RenderBigNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleija.class, new RenderCloudFleija());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleijaRainbow.class, new RenderCloudRainbow());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudSolinium.class, new RenderCloudSolinium());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudTom.class, new RenderCloudTom());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudNoShroom.class, new RenderNoCloud());
	    RenderingRegistry.registerEntityRenderingHandler(EntityFalloutRain.class, new RenderFallout());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityVortex.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRagingVortex.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityDeathBlast.class, new RenderDeathBlast());
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeExplosionAdvanced.class, new RenderSnowball(ModItems.energy_ball));
		//minecarts
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartTest.class, new RenderMinecartTest());
		//items
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingItem.class, new RenderMovingItem());
		//mobs
	    RenderingRegistry.registerEntityRenderingHandler(EntityNuclearCreeper.class, new RenderNuclearCreeper());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTaintedCreeper.class, new RenderTaintedCreeper());
	    RenderingRegistry.registerEntityRenderingHandler(EntityHunterChopper.class, new RenderHunterChopper());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCyberCrab.class, new RenderCyberCrab());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTeslaCrab.class, new RenderTeslaCrab());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTaintCrab.class, new RenderTaintCrab());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMaskMan.class, new RenderMaskMan());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBallsOTronSegment.class, new RenderBalls());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBOTPrimeHead.class, new RenderWormHead());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBOTPrimeBody.class, new RenderWormBody());
	    RenderingRegistry.registerEntityRenderingHandler(EntityDuck.class, new RenderDuck(new ModelChicken(), 0.3F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityQuackos.class, new RenderQuacc(new ModelChicken(), 0.3F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFBI.class, new RenderFBI());
	    //"particles"
	    RenderingRegistry.registerEntityRenderingHandler(EntitySmokeFX.class, new MultiCloudRenderer(new Item[] { ModItems.smoke1, ModItems.smoke2, ModItems.smoke3, ModItems.smoke4, ModItems.smoke5, ModItems.smoke6, ModItems.smoke7, ModItems.smoke8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBSmokeFX.class, new MultiCloudRenderer(new Item[] { ModItems.b_smoke1, ModItems.b_smoke2, ModItems.b_smoke3, ModItems.b_smoke4, ModItems.b_smoke5, ModItems.b_smoke6, ModItems.b_smoke7, ModItems.b_smoke8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDSmokeFX.class, new MultiCloudRenderer(new Item[] { ModItems.d_smoke1, ModItems.d_smoke2, ModItems.d_smoke3, ModItems.d_smoke4, ModItems.d_smoke5, ModItems.d_smoke6, ModItems.d_smoke7, ModItems.d_smoke8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityChlorineFX.class, new MultiCloudRenderer(new Item[] { ModItems.chlorine1, ModItems.chlorine2, ModItems.chlorine3, ModItems.chlorine4, ModItems.chlorine5, ModItems.chlorine6, ModItems.chlorine7, ModItems.chlorine8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityPinkCloudFX.class, new MultiCloudRenderer(new Item[] { ModItems.pc1, ModItems.pc2, ModItems.pc3, ModItems.pc4, ModItems.pc5, ModItems.pc6, ModItems.pc7, ModItems.pc8 }));
	    RenderingRegistry.registerEntityRenderingHandler(com.hbm.entity.particle.EntityCloudFX.class, new MultiCloudRenderer(new Item[] { ModItems.cloud1, ModItems.cloud2, ModItems.cloud3, ModItems.cloud4, ModItems.cloud5, ModItems.cloud6, ModItems.cloud7, ModItems.cloud8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityOrangeFX.class, new MultiCloudRenderer(new Item[] { ModItems.orange1, ModItems.orange2, ModItems.orange3, ModItems.orange4, ModItems.orange5, ModItems.orange6, ModItems.orange7, ModItems.orange8 }));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFogFX.class, new FogRenderer());
	    RenderingRegistry.registerEntityRenderingHandler(EntitySSmokeFX.class, new SSmokeRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityOilSpillFX.class, new SpillRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGasFX.class, new GasRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGasFlameFX.class, new GasFlameRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityCombineBall.class, new RenderSnowball(ModItems.energy_ball));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDischarge.class, new ElectricityRenderer(ModItems.discharge));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEMPBlast.class, new RenderEMPBlast());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTSmokeFX.class, new TSmokeRenderer(ModItems.nuclear_waste));
	}
	
	@Override
	public void registerBlockRenderer() {

		RenderingRegistry.registerBlockHandler(new RenderTaintBlock());
		RenderingRegistry.registerBlockHandler(new RenderScaffoldBlock());
		RenderingRegistry.registerBlockHandler(new RenderTapeBlock());
		RenderingRegistry.registerBlockHandler(new RenderSteelBeam());
		RenderingRegistry.registerBlockHandler(new RenderBarrel());
		RenderingRegistry.registerBlockHandler(new RenderFence());
		RenderingRegistry.registerBlockHandler(new RenderBarbedWire());
		RenderingRegistry.registerBlockHandler(new RenderAntennaTop());
		RenderingRegistry.registerBlockHandler(new RenderConserve());
		RenderingRegistry.registerBlockHandler(new RenderConveyor());
		RenderingRegistry.registerBlockHandler(new RenderRTGBlock());
		RenderingRegistry.registerBlockHandler(new RenderSpikeBlock());
	}
	
	@Override
	public void registerRenderInfo()
	{
		MinecraftForge.EVENT_BUS.register(new ModEventHandlerClient());

		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		
		registerTileEntitySpecialRenderer();
		registerItemRenderer();
		registerEntityRenderer();
		registerBlockRenderer();
	    
		RenderingRegistry.addNewArmourRendererPrefix("5");
		RenderingRegistry.addNewArmourRendererPrefix("6");
		RenderingRegistry.addNewArmourRendererPrefix("7");
		RenderingRegistry.addNewArmourRendererPrefix("8");
		RenderingRegistry.addNewArmourRendererPrefix("9");
	}
	
	@Override
	public void registerMissileItems() {
		
		MissilePart.registerAllParts();
		
		Iterator it = MissilePart.parts.entrySet().iterator();
		
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        MissilePart part = (MissilePart)pair.getValue();
			MinecraftForgeClient.registerItemRenderer(part.part, new ItemRenderMissilePart(part));
	    }
		
		MinecraftForgeClient.registerItemRenderer(ModItems.missile_custom, new ItemRenderMissile());
	}

	@Deprecated
	@Override
	public void particleControl(double x, double y, double z, int type) {

		
		World world = Minecraft.getMinecraft().theWorld;
		TextureManager man = Minecraft.getMinecraft().renderEngine;
		
		switch(type) {
		case 0:
			
			for(int i = 0; i < 10; i++) {
				EntityCloudFX smoke = new EntityCloudFX(world, x + world.rand.nextGaussian(), y + world.rand.nextGaussian(), z + world.rand.nextGaussian(), 0.0, 0.0, 0.0);
				Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			}
			break;
			
		case 1:
			
			EntityCloudFX smoke = new EntityCloudFX(world, x, y, z, 0.0, 0.1, 0.0);
			Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			break;
			
		case 2:
			
			ParticleContrail contrail = new ParticleContrail(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
			break;
			
		case 3:

			ParticleRadiationFog fog = new ParticleRadiationFog(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(fog);
			break;
		}
	}
	
	//version 2, now with strings!
	@Deprecated
	@Override
	public void spawnParticle(double x, double y, double z, String type, float args[]) {

		
		World world = Minecraft.getMinecraft().theWorld;
		TextureManager man = Minecraft.getMinecraft().renderEngine;
		
		if("launchsmoke".equals(type) && args.length == 3) {
			ParticleSmokePlume contrail = new ParticleSmokePlume(man, world, x, y, z);
			contrail.motionX = args[0];
			contrail.motionY = args[1];
			contrail.motionZ = args[2];
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
		if("exKerosene".equals(type)) {
			ParticleContrail contrail = new ParticleContrail(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
		if("exSolid".equals(type)) {
			ParticleContrail contrail = new ParticleContrail(man, world, x, y, z, 0.3F, 0.2F, 0.05F, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
		if("exHydrogen".equals(type)) {
			ParticleContrail contrail = new ParticleContrail(man, world, x, y, z, 0.7F, 0.7F, 0.7F, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
		if("exBalefire".equals(type)) {
			ParticleContrail contrail = new ParticleContrail(man, world, x, y, z, 0.2F, 0.7F, 0.2F, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
		if("radSmoke".equals(type)) {
			ParticleRadiationFog contrail = new ParticleRadiationFog(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
		}
	}
	
	//mk3, only use this one
	public void effectNT(NBTTagCompound data) {
		
		World world = Minecraft.getMinecraft().theWorld;
		TextureManager man = Minecraft.getMinecraft().renderEngine;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		Random rand = world.rand;
		String type = data.getString("type");
		double x = data.getDouble("posX");
		double y = data.getDouble("posY");
		double z = data.getDouble("posZ");
		
		if("smoke".equals(type)) {
			
			String mode = data.getString("mode");
			int count = Math.max(1, data.getInteger("count"));
			
			if("cloud".equals(mode)) {
				
				for(int i = 0; i < count; i++) {
					ParticleExSmoke fx = new ParticleExSmoke(man, world, x, y, z);
					fx.motionY = rand.nextGaussian() * (1 + (count / 100));
					fx.motionX = rand.nextGaussian() * (1 + (count / 150));
					fx.motionZ = rand.nextGaussian() * (1 + (count / 150));
					if(rand.nextBoolean()) fx.motionY = Math.abs(fx.motionY);
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}

			if("radial".equals(mode)) {

				for(int i = 0; i < count; i++) {
					ParticleExSmoke fx = new ParticleExSmoke(man, world, x, y, z);
					fx.motionY = rand.nextGaussian() * (1 + (count / 50));
					fx.motionX = rand.nextGaussian() * (1 + (count / 50));
					fx.motionZ = rand.nextGaussian() * (1 + (count / 50));
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
			
			if("shock".equals(mode)) {
				
				double strength = data.getDouble("strength");

				Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
				vec.rotateAroundY(rand.nextInt(360));
				
				for(int i = 0; i < count; i++) {
					ParticleExSmoke fx = new ParticleExSmoke(man, world, x, y, z);
					fx.motionY = 0;
					fx.motionX = vec.xCoord;
					fx.motionZ = vec.zCoord;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					
					vec.rotateAroundY(360 / count);
				}
			}
			
			if("shockRand".equals(mode)) {
				
				double strength = data.getDouble("strength");

				Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
				vec.rotateAroundY(rand.nextInt(360));
				double r;
				
				for(int i = 0; i < count; i++) {
					r = rand.nextDouble();
					ParticleExSmoke fx = new ParticleExSmoke(man, world, x, y, z);
					fx.motionY = 0;
					fx.motionX = vec.xCoord * r;
					fx.motionZ = vec.zCoord * r;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					
					vec.rotateAroundY(360 / count);
				}
			}
			
			if("wave".equals(mode)) {
				
				double strength = data.getDouble("range");

				Vec3 vec = Vec3.createVectorHelper(strength, 0, 0);
				
				for(int i = 0; i < count; i++) {
					
					vec.rotateAroundY((float) Math.toRadians(rand.nextFloat() * 360F));
					
					ParticleExSmoke fx = new ParticleExSmoke(man, world, x + vec.xCoord, y, z + vec.zCoord);
					fx.maxAge = 50;
					fx.motionY = 0;
					fx.motionX = 0;
					fx.motionZ = 0;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					
					vec.rotateAroundY(360 / count);
				}
			}
		}
		
		if("exhaust".equals(type)) {

			String mode = data.getString("mode");
			
			if("soyuz".equals(mode)) {
				
				if(Vec3.createVectorHelper(player.posX - x, player.posY - y, player.posZ - z).lengthVector() > 350)
					return;
	
				int count = Math.max(1, data.getInteger("count"));
				double width = data.getDouble("width");
				
				for(int i = 0; i < count; i++) {
					
					ParticleRocketFlame fx = new ParticleRocketFlame(man, world, x + rand.nextGaussian() * width, y, z + rand.nextGaussian() * width);
					fx.motionY = -0.75 + rand.nextDouble() * 0.5;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
			
			if("meteor".equals(mode)) {
				
				if(Vec3.createVectorHelper(player.posX - x, player.posY - y, player.posZ - z).lengthVector() > 350)
					return;
	
				int count = Math.max(1, data.getInteger("count"));
				double width = data.getDouble("width");
				
				for(int i = 0; i < count; i++) {
					
					ParticleRocketFlame fx = new ParticleRocketFlame(man, world, x + rand.nextGaussian() * width, y + rand.nextGaussian() * width, z + rand.nextGaussian() * width);
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
		}
		
		if("vanillaburst".equals(type)) {
			
			double motion = data.getDouble("motion");
			
			for(int i = 0; i < data.getInteger("count"); i++) {

				double mX = rand.nextGaussian() * motion;
				double mY = rand.nextGaussian() * motion;
				double mZ = rand.nextGaussian() * motion;
				
				EntityFX fx = null;

				if("flame".equals(data.getString("mode"))) {
					fx = new EntityFlameFX(world, x, y, z, mX, mY, mZ);
				}

				if("cloud".equals(data.getString("mode"))) {
					fx = new net.minecraft.client.particle.EntityCloudFX(world, x, y, z, mX, mY, mZ);
				}

				if("reddust".equals(data.getString("mode"))) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.0F, 0.0F, 0.0F);
					fx.motionX = mX;
					fx.motionY = mY;
					fx.motionZ = mZ;
				}

				if("bluedust".equals(data.getString("mode"))) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.01F, 0.01F, 1F);
				}

				if("greendust".equals(data.getString("mode"))) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.01F, 0.5F, 0.1F);
				}

				if("blockdust".equals(data.getString("mode"))) {
					
					Block b = Block.getBlockById(data.getInteger("block"));
					fx = new net.minecraft.client.particle.EntityBlockDustFX(world, x, y, z, mX, mY + 0.2, mZ, b, 0);
					ReflectionHelper.setPrivateValue(EntityFX.class, fx, 50 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
				}
				
				if(fx != null)
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
		}
		
		if("vanillaExt".equals(type)) {

			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");
			
			EntityFX fx = null;

			if("flame".equals(data.getString("mode"))) {
				fx = new EntityFlameFX(world, x, y, z, mX, mY, mZ);
			}

			if("smoke".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntitySmokeFX(world, x, y, z, mX, mY, mZ);
			}

			if("cloud".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntityCloudFX(world, x, y, z, mX, mY, mZ);
			}

			if("reddust".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, (float)mX, (float)mY, (float)mZ);
			}

			if("bluedust".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.01F, 0.01F, 1F);
			}

			if("greendust".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.01F, 0.5F, 0.1F);
			}
			
			if(fx != null)
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
		
		if("vanilla".equals(type)) {

			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");
			world.spawnParticle(data.getString("mode"), x, y, z, mX, mY, mZ);
		}
		
		if("hadron".equals(type)) {
			
			/*for(int i = 0; i < 30; i++) {
				
				EntityFX fx = null;

				if(i % 3 == 0) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.0F, 0.0F, 0.0F);
				}
				if(i % 3 == 1) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 0.01F, 0.01F, 1F);
				}
				if(i % 3 == 2) {
					fx = new net.minecraft.client.particle.EntityReddustFX(world, x, y, z, 1F, 1F, 0.1F);
				}
				
				if(fx != null) {
					fx.motionX = rand.nextGaussian() * 0.1;
					fx.motionY = rand.nextGaussian() * 0.1;
					fx.motionZ = rand.nextGaussian() * 0.1;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}*/
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleHadron(man, world, x, y, z));
		}
	}
	
	@Override
	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch) {
		
		AudioWrapperClient audio = new AudioWrapperClient(new ResourceLocation(sound));
		audio.updatePosition(x, y, z);
		return audio;
	}

	@Override
	public void playSound(String sound, Object data) {
		
	}

	@Override
	public void displayTooltip(String msg) {
		
		Minecraft.getMinecraft().ingameGUI.func_110326_a(msg, false);
	}
}

