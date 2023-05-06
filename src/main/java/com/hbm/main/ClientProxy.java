 package com.hbm.main;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.blocks.machine.MachineFan.TileEntityFan;
import com.hbm.blocks.machine.WatzPump.TileEntityWatzPump;
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
import com.hbm.entity.train.*;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.HbmKeybinds;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.IAnimatedItem;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.particle.*;
import com.hbm.particle.psys.engine.EventHandlerParticleEngine;
import com.hbm.render.anim.*;
import com.hbm.render.anim.HbmAnimations.Animation;
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
import com.hbm.render.util.RenderInfoSystem;
import com.hbm.render.util.RenderInfoSystem.InfoEntry;
import com.hbm.render.util.RenderOverhead;
import com.hbm.render.util.RenderOverhead.Marker;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.AudioWrapperClient;
import com.hbm.sound.AudioWrapperClientStartStop;
import com.hbm.tileentity.TileEntityDoorGeneric;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.storage.*;
import com.hbm.tileentity.network.*;
import com.hbm.tileentity.turret.*;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.wiaj.cannery.Jars;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientProxy extends ServerProxy {
	
	public RenderInfoSystem theInfoSystem = new RenderInfoSystem();
	
	@Override
	public void registerRenderInfo() {

		registerClientEventHandler(new ModEventHandlerClient());
		registerClientEventHandler(new ModEventHandlerRenderer());
		registerClientEventHandler(new EventHandlerParticleEngine());
		registerClientEventHandler(theInfoSystem);

		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		ResourceManager.loadAnimatedModels();

		registerTileEntitySpecialRenderer();
		registerItemRenderer();
		registerEntityRenderer();
		registerBlockRenderer();
		
		Jars.initJars();

		//SoundUtil.addSoundCategory("ntmMachines");
	}
	
	private void registerClientEventHandler(Object handler) {
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() {
		//test crap
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestRender.class, new RenderTestRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestBombAdvanced.class, new RenderTestBombAdvanced());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDemonLamp.class, new RenderDemonLamp());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLoot.class, new RenderLoot());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBobble.class, new RenderBobble());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySnowglobe.class, new RenderSnowglobe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEmitter.class, new RenderEmitter());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCharge.class, new RenderExplosiveCharge());
		//turrets
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretChekhov.class, new RenderTurretChekhov());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFriendly.class, new RenderTurretFriendly());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretJeremy.class, new RenderTurretJeremy());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTauon.class, new RenderTurretTauon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretRichard.class, new RenderTurretRichard());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHoward.class, new RenderTurretHoward());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHowardDamaged.class, new RenderTurretHowardDamaged());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretMaxwell.class, new RenderTurretMaxwell());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFritz.class, new RenderTurretFritz());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretBrandon.class, new RenderTurretBrandon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretArty.class, new RenderTurretArty());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHIMARS.class, new RenderTurretHIMARS());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretSentry.class, new RenderTurretSentry());
		//mines
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLandmine.class, new RenderLandmine());
		//machines
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCentrifuge.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasCent.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFEL.class, new RenderFEL());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySILEX.class, new RenderSILEX());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUF6Tank.class, new RenderUF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePuF6Tank.class, new RenderPuF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineIGenerator.class, new RenderIGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCyclotron.class, new RenderCyclotron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineOilWell.class, new RenderDerrick());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasFlare.class, new RenderGasFlare());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningDrill.class, new RenderMiningDrill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningLaser.class, new RenderLaserMiner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new RenderAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssemfac.class, new RenderAssemfac());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChemplant.class, new RenderChemplant());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChemfac.class, new RenderChemfac());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFluidTank.class, new RenderFluidTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineBAT9000.class, new RenderBAT9000());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineOrbus.class, new RenderOrbus());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRefinery.class, new RenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFractionTower.class, new RenderFractionTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpacer.class, new RenderSpacer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePumpjack.class, new RenderPumpjack());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFrackingTower.class, new RenderFrackingTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineTurbofan.class, new RenderTurbofan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineTurbineGas.class, new RenderTurbineGas());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePress.class, new RenderPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineEPress.class, new RenderEPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadGen.class, new RenderRadGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSeleniumEngine.class, new RenderSelenium());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactorResearch.class, new RenderSmallReactor());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReactorBreeding.class, new RenderBreeder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarBoiler.class, new RenderSolarBoiler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageDrum.class, new RenderStorageDrum());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChungus.class, new RenderChungus());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTowerLarge.class, new RenderLargeTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTowerSmall.class, new RenderSmallTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDeuteriumTower.class, new RenderDeuteriumTower());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCatalyticCracker.class, new RenderCatalyticCracker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineLiquefactor.class, new RenderLiquefactor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSolidifier.class, new RenderSolidifier());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadiolysis.class, new RenderRadiolysis());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityElectrolyser.class, new RenderElectrolyser());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceIron.class, new RenderFurnaceIron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceSteel.class, new RenderFurnaceSteel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceCombination.class, new RenderFurnaceCombination());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeaterFirebox.class, new RenderFirebox());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeaterOven.class, new RenderHeatingOven());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeaterOilburner.class, new RenderOilburner());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeaterElectric.class, new RenderElectricHeater());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeaterHeatex.class, new RenderHeaterHeatex());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStirling.class, new RenderStirling());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySawmill.class, new RenderSawmill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucible.class, new RenderCrucible());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHeatBoiler.class, new RenderBoiler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySteamEngine.class, new RenderSteamEngine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineDiesel.class, new RenderDieselGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCombustionEngine.class, new RenderCombustionEngine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineExcavator.class, new RenderExcavator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMixer.class, new RenderMixer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineHephaestus.class, new RenderHephaestus());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAutosaw.class, new RenderAutosaw());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineVacuumDistill.class, new RenderVacuumDistill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCatalyticReformer.class, new RenderCatalyticReformer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCoker.class, new RenderCoker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFan.class, new RenderFan());
		//Foundry
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFoundryBasin.class, new RenderFoundry());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFoundryMold.class, new RenderFoundry());
		//AMS
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSBase.class, new RenderAMSBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSEmitter.class, new RenderAMSEmitter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAMSLimiter.class, new RenderAMSLimiter());
		//ZIRNOX
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReactorZirnox.class, new RenderZirnox());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityZirnoxDestroyed.class, new RenderZirnoxDestroyed());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilDuct.class, new RenderOilDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasDuct.class, new RenderGasDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidDuct.class, new RenderFluidDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRFDuct.class, new RenderRFCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylon.class, new RenderPylon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConnector.class, new RenderConnector());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylonLarge.class, new RenderPylonLarge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySubstation.class, new RenderSubstation());
		//chargers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCharger.class, new RenderCharger());
		//DecoContainer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFileCabinet.class, new RenderFileCabinet());
		//multiblocks
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStructureMarker.class, new RenderStructureMaker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMultiblock.class, new RenderMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySoyuzStruct.class, new RenderSoyuzMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityITERStruct.class, new RenderITERMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlasmaStruct.class, new RenderPlasmaMultiblock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWatzStruct.class, new RenderWatzMultiblock());
		//RBMK
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKControlManual.class, new RenderRBMKControlRod());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKControlAuto.class, new RenderRBMKControlRod());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCraneConsole.class, new RenderCraneConsole());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKConsole.class, new RenderRBMKConsole());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKAbsorber.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKBlank.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKBoiler.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKModerator.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKOutgasser.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKReflector.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKRod.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKRodReaSim.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKCooler.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKStorage.class, new RenderRBMKLid());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRBMKHeater.class, new RenderRBMKLid());
		//ITER
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityITER.class, new RenderITER());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePlasmaHeater.class, new RenderPlasmaHeater());
		//Watz
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWatz.class, new RenderWatz());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWatzPump.class, new RenderWatzPump());
		//doors
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlastDoor.class, new RenderBlastDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoorGeneric.class, new RenderDoorGeneric());
	}

	@Override
	public void registerItemRenderer() {
		
		ItemRenderLibrary.init();
		
		for(Entry<Item, ItemRenderBase> entry : ItemRenderLibrary.renderers.entrySet())
			MinecraftForgeClient.registerItemRenderer(entry.getKey(), entry.getValue());
		
		//this bit registers an item renderer for every existing tile entity renderer that implements IItemRendererProvider
		Iterator iterator = TileEntityRendererDispatcher.instance.mapSpecialRenderers.values().iterator();
		while(iterator.hasNext()) {
			Object renderer = iterator.next();
			if(renderer instanceof IItemRendererProvider) {
				IItemRendererProvider prov = (IItemRendererProvider) renderer;
				for(Item item : prov.getItemsForRenderer()) {
					MinecraftForgeClient.registerItemRenderer(item, prov.getRenderer());
				}
			}
		}
		
		//universal JSON translated items
		double[] rtp = new double[] {0, 180, -90};
		double[] ttp_high = new double[] {0.125, 0.625, 0};
		double[] ttp_low = new double[] {0, 0.75, 0};
		double[] stp = new double[] {1.7, 1.7, 0.85};
		double[] rfp = new double[] {0, 180, -90};
		double[] tfp = new double[] {1.13, 5.2, -0.26};
		double[] sfp = new double[] {1.36, 1.36, 0.68};
		double[] rir = new double[] {0, 0, 0};
		double[] tir = new double[] {0, 0, 0};
		double[] sir = new double[] {1.1, 1.1, 1.1};

		MinecraftForgeClient.registerItemRenderer(ModItems.titanium_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.alloy_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.desh_sword, new ItemRenderTransformer(rtp, ttp_low, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.cobalt_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.cobalt_decorated_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.starmetal_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.schrabidium_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.cmb_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		MinecraftForgeClient.registerItemRenderer(ModItems.dnt_sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		
		/*for(ItemSwordMeteorite sword : ItemSwordMeteorite.swords) {
			MinecraftForgeClient.registerItemRenderer(sword, new ItemRenderTransformer(rtp, ttp_high, stp, rfp, tfp, sfp, rir, tir, sir));
		}*/
		
		
		//test crap
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.test_bomb_advanced), new ItemRenderTestBombAdvanced());
		//templates
		MinecraftForgeClient.registerItemRenderer(ModItems.assembly_template, new ItemRenderTemplate());
		MinecraftForgeClient.registerItemRenderer(ModItems.chemistry_template, new ItemRenderTemplate());
		MinecraftForgeClient.registerItemRenderer(ModItems.crucible_template, new ItemRenderTemplate());
		//hot stuff
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_steel_dusted, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_chainsteel, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_meteorite, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_meteorite_forged, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.blade_meteorite, new ItemRendererHot());
		//meteorite swords
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_seared, new ItemRendererMeteorSword(1.0F, 0.5F, 0.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_reforged, new ItemRendererMeteorSword(0.5F, 1.0F, 1.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_hardened, new ItemRendererMeteorSword(0.25F, 0.25F, 0.25F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_alloyed, new ItemRendererMeteorSword(0.0F, 0.5F, 1.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_machined, new ItemRendererMeteorSword(1.0F, 1.0F, 0.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_treated, new ItemRendererMeteorSword(0.5F, 1.0F, 0.5F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_etched, new ItemRendererMeteorSword(1.0F, 1.0F, 0.5F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_bred, new ItemRendererMeteorSword(0.5F, 0.5F, 0.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_irradiated, new ItemRendererMeteorSword(0.75F, 1.0F, 0.0F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_fused, new ItemRendererMeteorSword(1.0F, 0.0F, 0.5F));
		MinecraftForgeClient.registerItemRenderer(ModItems.meteorite_sword_baleful, new ItemRendererMeteorSword(0.0F, 1.0F, 0.0F));
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
		MinecraftForgeClient.registerItemRenderer(ModItems.crucible, new ItemRenderCrucible());
		MinecraftForgeClient.registerItemRenderer(ModItems.chainsaw, new ItemRenderChainsaw());
		MinecraftForgeClient.registerItemRenderer(ModItems.boltgun, new ItemRenderBoltgun());
		//guns
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_rpg, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_karl, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_panzerschreck, new ItemRenderRpg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_stinger, new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_skystinger, new ItemRenderStinger());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver, new ItemRenderWeaponFFColt(ResourceManager.ff_gun_bright, ResourceManager.ff_iron, ResourceManager.ff_wood));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_saturnite, new ItemRenderWeaponFFColt(ResourceManager.ff_saturnite, ResourceManager.ff_iron, ResourceManager.ff_wood));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_gold, new ItemRenderWeaponFFColt(ResourceManager.ff_gold, ResourceManager.ff_gold, ResourceManager.ff_gun_dark));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_schrabidium, new ItemRenderWeaponFFColt(ResourceManager.ff_schrabidium, ResourceManager.ff_schrabidium, ResourceManager.ff_gun_dark));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_cursed, new ItemRenderWeaponFFCursed());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare, new ItemRenderWeaponFFNightmare());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nightmare2, new ItemRenderWeaponFFNightmareDark());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman, new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_proto, new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mirv, new ItemRenderMIRVLauncher());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bf, new ItemRenderBFLauncher());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_xvl1456, new ItemRenderWeaponTau());
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
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_dampfmaschine, new ItemRenderBullshit());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action, new ItemRenderWeaponFFMaresLeg(ResourceManager.ff_gun_bright, ResourceManager.ff_wood));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action, new ItemRenderWeaponFFBolt(ResourceManager.rem700, ResourceManager.rem700_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action_dark, new ItemRenderWeaponFFMaresLeg(ResourceManager.ff_gun_normal, ResourceManager.ff_wood_red));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action_green, new ItemRenderWeaponFFBolt(ResourceManager.rem700poly, ResourceManager.rem700poly_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lever_action_sonata, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action_saturnite, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolt_action_saturnite, new ItemRenderWeaponFFBolt(ResourceManager.rem700sat, ResourceManager.rem700sat_tex));
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_b92, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_b93, new ItemRenderGunAnim());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_silencer, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_saturnite, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_uzi_saturnite_silencer, new ItemRenderUZI());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_calamity, new ItemRenderWeaponFFMG42());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_minigun, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_avenger, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lacunae, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_folly, new ItemRenderOverkill());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_hk69, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bio_revolver, new ItemRenderBioRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_deagle, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_supershotgun, new ItemRenderWeaponShotty());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_ks23, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flamer, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flechette, new ItemRenderWeaponObj());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_quadro, new ItemRenderWeaponQuadro());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_sauer, new ItemRenderWeaponSauer());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_vortex, new ItemRenderWeaponVortex());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_thompson, new ItemRenderWeaponThompson());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolter, new ItemRenderWeaponBolter());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolter_digamma, new ItemRenderWeaponBolter());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fireext, new ItemRenderFireExt());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_ar15, new ItemRenderWeaponAR15());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_detonator, new ItemRenderDetonatorLaser());
		MinecraftForgeClient.registerItemRenderer(ModItems.detonator_laser, new ItemRenderDetonatorLaser());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_remington, new ItemRenderWeaponRemington());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_spas12, new ItemRenderWeaponSpas12());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_glass_cannon, new ItemRenderWeaponGlass());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_chemthrower, new ItemRenderWeaponChemthrower());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_m2, new ItemRenderM2());	
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_nopip, new ItemRenderWeaponNovac());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_pip, new ItemRenderWeaponNovac());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_blackjack, new ItemRenderWeaponNovac());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_silver, new ItemRenderWeaponNovac());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_revolver_red, new ItemRenderWeaponNovac());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lunatic_marksman, new ItemRenderLunaticSniper());
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
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbow.class, new RenderRainbow());
		RenderingRegistry.registerEntityRenderingHandler(EntityNightmareBlast.class, new RenderOminousBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityFire.class, new RenderFireball(ModItems.energy_ball));
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBeam.class, new RenderBeam());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeam.class, new RenderBeam2());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinerBeam.class, new RenderBeam3());
		RenderingRegistry.registerEntityRenderingHandler(EntitySparkBeam.class, new RenderBeam4());
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveBeam.class, new RenderBeam5());
		RenderingRegistry.registerEntityRenderingHandler(EntityModBeam.class, new RenderBeam6());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeLaser.class, new RenderSiegeLaser());
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
	    RenderingRegistry.registerEntityRenderingHandler(EntitySiegeDropship.class, new RenderMinerRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityTom.class, new RenderTom());
	    RenderingRegistry.registerEntityRenderingHandler(EntityAAShell.class, new RenderMirv());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRocketHoming.class, new RenderSRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityChopperMine.class, new RenderChopperMine());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRubble.class, new RenderRubble());
	    RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new RenderShrapnel());
	    RenderingRegistry.registerEntityRenderingHandler(EntityFireworks.class, new RenderShrapnel());
	    RenderingRegistry.registerEntityRenderingHandler(EntityOilSpill.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityWaterSplash.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBeamVortex.class, new RenderVortexBeam());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRBMKDebris.class, new RenderRBMKDebris());
	    RenderingRegistry.registerEntityRenderingHandler(EntityZirnoxDebris.class, new RenderZirnoxDebris());
	    RenderingRegistry.registerEntityRenderingHandler(EntityArtilleryShell.class, new RenderArtilleryShell());
	    RenderingRegistry.registerEntityRenderingHandler(EntityArtilleryRocket.class, new RenderArtilleryRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCog.class, new RenderCog());
	    RenderingRegistry.registerEntityRenderingHandler(EntitySawblade.class, new RenderSawblade());
	    RenderingRegistry.registerEntityRenderingHandler(EntityChemical.class, new RenderChemical());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMist.class, new RenderMist());
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
		RenderingRegistry.registerEntityRenderingHandler(EntityWastePearl.class, new RenderSnowball(ModItems.nuclear_waste_pearl));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeDynamite.class, new RenderSnowball(ModItems.stick_dynamite));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBouncyGeneric.class, new RenderGenericGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeImpactGeneric.class, new RenderGenericGrenade());
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
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileVolcano.class, new RenderMissileNuclear());
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
	    RenderingRegistry.registerEntityRenderingHandler(EntityMissileShuttle.class, new RenderMissileShuttle());
		//effects
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK4());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudBig.class, new RenderBigNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleija.class, new RenderCloudFleija());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleijaRainbow.class, new RenderCloudRainbow());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudSolinium.class, new RenderCloudSolinium());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudTom.class, new RenderCloudTom());
	    RenderingRegistry.registerEntityRenderingHandler(EntityFalloutRain.class, new RenderFallout());
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityVortex.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRagingVortex.class, new RenderBlackHole());
	    RenderingRegistry.registerEntityRenderingHandler(EntityQuasar.class, new RenderQuasar());
	    RenderingRegistry.registerEntityRenderingHandler(EntityDeathBlast.class, new RenderDeathBlast());
	    RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpear());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeTorex.class, new RenderTorex());
		//minecarts
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartTest.class, new RenderMinecartTest());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartCrate.class, new RenderMinecart());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartNTM.class, new RenderNeoCart());
		RenderingRegistry.registerEntityRenderingHandler(EntityMagnusCartus.class, new RenderMagnusCartus());
		RenderingRegistry.registerEntityRenderingHandler(TrainCargoTram.class, new RenderTrainCargoTram());
		//items
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingItem.class, new RenderMovingItem());
		RenderingRegistry.registerEntityRenderingHandler(EntityMovingPackage.class, new RenderMovingPackage());
		RenderingRegistry.registerEntityRenderingHandler(EntityTNTPrimedBase.class, new RenderTNTPrimedBase());
		//mobs
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperNuclear.class, new RenderCreeperUniversal(RefStrings.MODID + ":" + "textures/entity/creeper.png", RefStrings.MODID + ":" + "textures/entity/creeper_armor.png").setSwellMod(5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperTainted.class, new RenderCreeperUniversal(RefStrings.MODID + ":" + "textures/entity/creeper_tainted.png", RefStrings.MODID + ":" + "textures/entity/creeper_armor_taint.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperPhosgene.class, new RenderCreeperUniversal(RefStrings.MODID + ":" + "textures/entity/creeper_phosgene.png", "textures/entity/creeper/creeper_armor.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperVolatile.class, new RenderCreeperUniversal(RefStrings.MODID + ":" + "textures/entity/creeper_volatile.png", "textures/entity/creeper/creeper_armor.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperGold.class, new RenderCreeperUniversal(RefStrings.MODID + ":" + "textures/entity/creeper_gold.png", "textures/entity/creeper/creeper_armor.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntityHunterChopper.class, new RenderHunterChopper());
		RenderingRegistry.registerEntityRenderingHandler(EntityCyberCrab.class, new RenderCyberCrab());
		RenderingRegistry.registerEntityRenderingHandler(EntityTeslaCrab.class, new RenderTeslaCrab());
		RenderingRegistry.registerEntityRenderingHandler(EntityTaintCrab.class, new RenderTaintCrab());
		RenderingRegistry.registerEntityRenderingHandler(EntityMaskMan.class, new RenderMaskMan());
		RenderingRegistry.registerEntityRenderingHandler(EntityBOTPrimeHead.class, new RenderWormHead());
		RenderingRegistry.registerEntityRenderingHandler(EntityBOTPrimeBody.class, new RenderWormBody());
		RenderingRegistry.registerEntityRenderingHandler(EntityDuck.class, new RenderDuck(new ModelChicken(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityQuackos.class, new RenderQuacc(new ModelChicken(), 7.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFBI.class, new RenderFBI());
		RenderingRegistry.registerEntityRenderingHandler(EntityRADBeast.class, new RenderRADBeast());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockSpider.class, new RenderBlockSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityUFO.class, new RenderUFO());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeZombie.class, new RenderSiegeZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeUFO.class, new RenderSiegeUFO());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeCraft.class, new RenderSiegeCraft());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeSkeleton.class, new RenderSiegeSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiegeTunneler.class, new RenderSiegeTunneler());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new RenderGhost());
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
		RenderingRegistry.registerBlockHandler(new RenderConveyorChute());
		RenderingRegistry.registerBlockHandler(new RenderConveyorLift());
		RenderingRegistry.registerBlockHandler(new RenderRTGBlock());
		RenderingRegistry.registerBlockHandler(new RenderSpikeBlock());
		RenderingRegistry.registerBlockHandler(new RenderChain());
		RenderingRegistry.registerBlockHandler(new RenderMirror());
		RenderingRegistry.registerBlockHandler(new RenderGrate());
		RenderingRegistry.registerBlockHandler(new RenderPipe());
		RenderingRegistry.registerBlockHandler(new RenderBattery());
		RenderingRegistry.registerBlockHandler(new RenderAnvil());
		RenderingRegistry.registerBlockHandler(new RenderCrystal());
		RenderingRegistry.registerBlockHandler(new RenderCable());
		RenderingRegistry.registerBlockHandler(new RenderCableClassic());
		RenderingRegistry.registerBlockHandler(new RenderTestPipe());
		RenderingRegistry.registerBlockHandler(new RenderBlockCT());
		RenderingRegistry.registerBlockHandler(new RenderDetCord());
		RenderingRegistry.registerBlockHandler(new RenderBlockMultipass());
		RenderingRegistry.registerBlockHandler(new RenderBlockSideRotation());
		RenderingRegistry.registerBlockHandler(new RenderDiode());
		RenderingRegistry.registerBlockHandler(new RenderBoxDuct());
		RenderingRegistry.registerBlockHandler(new RenderBlockDecoModel(ModBlocks.deco_computer.getRenderType(), ResourceManager.deco_computer));
		RenderingRegistry.registerBlockHandler(new RenderReeds());
		RenderingRegistry.registerBlockHandler(new RenderRTTY());
		RenderingRegistry.registerBlockHandler(new RenderDiFurnaceExtension());
		RenderingRegistry.registerBlockHandler(new RenderSplitter());

		RenderingRegistry.registerBlockHandler(new RenderFoundryBasin());
		RenderingRegistry.registerBlockHandler(new RenderFoundryMold());
		RenderingRegistry.registerBlockHandler(new RenderFoundryChannel());
		RenderingRegistry.registerBlockHandler(new RenderFoundryTank());
		RenderingRegistry.registerBlockHandler(new RenderFoundryOutlet());
		
		RenderingRegistry.registerBlockHandler(new RenderBlockRotated(ModBlocks.charge_dynamite.getRenderType(), ResourceManager.charge_dynamite));
		RenderingRegistry.registerBlockHandler(new RenderBlockRotated(ModBlocks.charge_c4.getRenderType(), ResourceManager.charge_c4));

		RenderingRegistry.registerBlockHandler(new RenderRBMKRod());
		RenderingRegistry.registerBlockHandler(new RenderRBMKReflector());
		RenderingRegistry.registerBlockHandler(new RenderRBMKControl());
		RenderingRegistry.registerBlockHandler(new RenderPribris());
	}
	
	@Override
	public void registerMissileItems() {
		
		MissilePart.registerAllParts();
		
		Iterator it = MissilePart.parts.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			MissilePart part = (MissilePart) pair.getValue();
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
		
		if(world == null) //might i ask why?
			return;
		
		TextureManager man = Minecraft.getMinecraft().renderEngine;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		int particleSetting = Minecraft.getMinecraft().gameSettings.particleSetting;
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

			if("radialDigamma".equals(mode)) {

				Vec3 vec = Vec3.createVectorHelper(2, 0, 0);
				vec.rotateAroundY(rand.nextFloat() * (float)Math.PI * 2F);
				
				for(int i = 0; i < count; i++) {
					ParticleDigammaSmoke fx = new ParticleDigammaSmoke(man, world, x, y, z);
					fx.motionY = 0;
					fx.motionX = vec.xCoord;
					fx.motionZ = vec.zCoord;
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					
					vec.rotateAroundY((float)Math.PI * 2F / (float)count);
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

					vec.rotateAroundY((float)Math.PI * 2F / (float)count);
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
		
		if("fireworks".equals(type)) {
			int color = data.getInteger("color");
			char c = (char)data.getInteger("char");
			
			ParticleLetter fx = new ParticleLetter(world, x, y, z, color, c);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			
			for(int i = 0; i < 50; i++) {
				EntityFireworkSparkFX blast = new EntityFireworkSparkFX(world, x, y, z,
						0.4 * world.rand.nextGaussian(),
						0.4 * world.rand.nextGaussian(),
						0.4 * world.rand.nextGaussian(), Minecraft.getMinecraft().effectRenderer);
				blast.setColour(color);
				Minecraft.getMinecraft().effectRenderer.addEffect(blast);
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

			if("volcano".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntitySmokeFX(world, x, y, z, mX, mY, mZ);
				float scale = 100;
				ReflectionHelper.setPrivateValue(net.minecraft.client.particle.EntitySmokeFX.class, (net.minecraft.client.particle.EntitySmokeFX)fx, scale, "smokeParticleScale", "field_70587_a");
				ReflectionHelper.setPrivateValue(EntityFX.class, fx, 200 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
				fx.noClip = true;
				fx.motionY = 2.5 + rand.nextDouble();
				fx.motionX = rand.nextGaussian() * 0.2;
				fx.motionZ = rand.nextGaussian() * 0.2;
			}

			if("cloud".equals(data.getString("mode"))) {
				fx = new net.minecraft.client.particle.EntityCloudFX(world, x, y, z, mX, mY, mZ);
				
				if(data.hasKey("r")) {
					float rng = rand.nextFloat() * 0.1F;
					fx.setRBGColorF(data.getFloat("r") + rng, data.getFloat("g") + rng, data.getFloat("b") + rng);
					ReflectionHelper.setPrivateValue(net.minecraft.client.particle.EntityCloudFX.class, (EntityCloudFX)fx, 7.5F, "field_70569_a");
					fx.motionX = 0;
					fx.motionY = 0;
					fx.motionZ = 0;
				}
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

			if("largeexplode".equals(data.getString("mode"))) {
				
				
				fx = new net.minecraft.client.particle.EntityLargeExplodeFX(man, world, x, y, z, data.getFloat("size"), 0.0F, 0.0F);
				float r = 1.0F - rand.nextFloat() * 0.2F;
				fx.setRBGColorF(1F * r, 0.9F * r, 0.5F * r);
				
				for(int i = 0; i < data.getByte("count"); i++) {
					net.minecraft.client.particle.EntityExplodeFX sec = new net.minecraft.client.particle.EntityExplodeFX(world, x, y, z, 0.0F, 0.0F, 0.0F);
					float r2 = 1.0F - rand.nextFloat() * 0.5F;
					sec.setRBGColorF(0.5F * r2, 0.5F * r2, 0.5F * r2);
					sec.multipleParticleScaleBy(i + 1);
					Minecraft.getMinecraft().effectRenderer.addEffect(sec);
				}
			}

			if("townaura".equals(data.getString("mode"))) {
				fx = new EntityAuraFX(world, x, y, z, 0, 0, 0);
				float color = 0.5F + rand.nextFloat() * 0.5F;
				fx.setRBGColorF(0.8F * color, 0.9F * color, 1.0F * color);
				fx.setVelocity(mX, mY, mZ);
			}

			if("blockdust".equals(data.getString("mode"))) {
				
				Block b = Block.getBlockById(data.getInteger("block"));
				fx = new net.minecraft.client.particle.EntityBlockDustFX(world, x, y, z, mX, mY + 0.2, mZ, b, 0);
				ReflectionHelper.setPrivateValue(EntityFX.class, fx, 10 + rand.nextInt(20), "particleMaxAge", "field_70547_e");
			}

			if("colordust".equals(data.getString("mode"))) {
				
				Block b = Blocks.wool;
				fx = new net.minecraft.client.particle.EntityBlockDustFX(world, x, y, z, mX, mY + 0.2, mZ, b, 0);
				fx.setRBGColorF(data.getFloat("r"), data.getFloat("g"), data.getFloat("b"));
				ReflectionHelper.setPrivateValue(EntityFX.class, fx, 10 + rand.nextInt(20), "particleMaxAge", "field_70547_e");
			}
			
			if(fx != null) {
				
				if(data.getBoolean("noclip")) {
					fx.noClip = true;
				}
				
				if(data.getInteger("overrideAge") > 0) {
					ReflectionHelper.setPrivateValue(EntityFX.class, fx, data.getInteger("overrideAge"), "particleMaxAge", "field_70547_e");
				}
				
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
		}
		
		if("vanilla".equals(type)) {

			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");
			world.spawnParticle(data.getString("mode"), x, y, z, mX, mY, mZ);
		}
		
		if("jetpack".equals(type)) {
			
			if(particleSetting == 2)
				return;
			
			Entity ent = world.getEntityByID(data.getInteger("player"));
			
			if(ent instanceof EntityPlayer) {
				
				EntityPlayer p = (EntityPlayer)ent;
				
				Vec3 vec = Vec3.createVectorHelper(0, 0, -0.25);
				Vec3 offset = Vec3.createVectorHelper(0.125, 0, 0);
				float angle = (float) -Math.toRadians(p.rotationYawHead - (p.rotationYawHead - p.renderYawOffset));

				vec.rotateAroundY(angle);
				offset.rotateAroundY(angle);
				
				double ix = p.posX + vec.xCoord;
				double iy = p.posY + p.eyeHeight - 1;
				double iz = p.posZ + vec.zCoord;
				double ox = offset.xCoord;
				double oz = offset.zCoord;
				
				double moX = 0;
				double moY = 0;
				double moZ = 0;
				
				int mode = data.getInteger("mode");
				
				if(mode == 0) {
					moY -= 0.2;
				}
				
				if(mode == 1) {
					Vec3 look = p.getLookVec();

					moX -= look.xCoord * 0.1D;
					moY -= look.yCoord * 0.1D;
					moZ -= look.zCoord * 0.1D;
				}

				if(particleSetting == 0) {
					Vec3 pos = Vec3.createVectorHelper(ix, iy, iz);
					Vec3 thrust = Vec3.createVectorHelper(moX, moY, moZ);
					thrust = thrust.normalize();
					Vec3 target = pos.addVector(thrust.xCoord * 10, thrust.yCoord * 10, thrust.zCoord * 10);
					MovingObjectPosition mop = player.worldObj.func_147447_a(pos, target, false, false, true);
					
					if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK && mop.sideHit == 1) {
						
						Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
						
						Vec3 delta = Vec3.createVectorHelper(ix - mop.hitVec.xCoord, iy - mop.hitVec.yCoord, iz - mop.hitVec.zCoord);
						Vec3 vel = Vec3.createVectorHelper(0.75 - delta.lengthVector() * 0.075, 0, 0);
						
						for(int i = 0; i < (10 - delta.lengthVector()); i++) {
							vel.rotateAroundY(world.rand.nextFloat() * (float)Math.PI * 2F);
							Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBlockDustFX(world, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.1, mop.hitVec.zCoord, vel.xCoord, 0.1, vel.zCoord, b, meta));
						}
					}
				}

				double mX2 = BobMathUtil.safeClamp(p.motionX + moX * 2, -5, 5);
				double mY2 = BobMathUtil.safeClamp(p.motionY + moY * 2, -5, 5);
				double mZ2 = BobMathUtil.safeClamp(p.motionZ + moZ * 2, -5, 5);
				double mX3 = BobMathUtil.safeClamp(p.motionX + moX * 2, -10, 10);
				double mY3 = BobMathUtil.safeClamp(p.motionY + moY * 2, -10, 10);
				double mZ3 = BobMathUtil.safeClamp(p.motionZ + moZ * 2, -10, 10);

				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix + ox, iy, iz + oz, mX2, mY2, mZ2));
				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix - ox, iy, iz - oz, mX2, mY2, mZ2));
				
				if(particleSetting == 0) {
					Minecraft.getMinecraft().effectRenderer.addEffect(new net.minecraft.client.particle.EntitySmokeFX(world, ix + ox, iy, iz + oz, mX3, mY3, mZ3));
					Minecraft.getMinecraft().effectRenderer.addEffect(new net.minecraft.client.particle.EntitySmokeFX(world, ix - ox, iy, iz - oz, mX3, mY3, mZ3));
				}
			}
		}
		
		if("bnuuy".equals(type)) {
			
			if(particleSetting == 2)
				return;
			
			Entity ent = world.getEntityByID(data.getInteger("player"));
			
			if(ent instanceof EntityPlayer) {
				
				EntityPlayer p = (EntityPlayer)ent;
				
				Vec3 vec = Vec3.createVectorHelper(0, 0, -0.6);
				Vec3 offset = Vec3.createVectorHelper(0.275, 0, 0);
				float angle = (float) -Math.toRadians(p.rotationYawHead - (p.rotationYawHead - p.renderYawOffset));

				vec.rotateAroundY(angle);
				offset.rotateAroundY(angle);
				
				double ix = p.posX + vec.xCoord;
				double iy = p.posY + p.eyeHeight - 1 + 0.4;
				double iz = p.posZ + vec.zCoord;
				double ox = offset.xCoord;
				double oz = offset.zCoord;
				
				vec = vec.normalize();
				double mult = 0.025D;
				double mX = vec.xCoord * mult;
				double mZ = vec.zCoord * mult;
				
				//Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix + ox, iy, iz + oz, 0, 0, 0));
				//Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix - ox, iy, iz - oz, 0, 0, 0));
				
				for(int i = 0; i < 2; i++) {
					net.minecraft.client.particle.EntitySmokeFX fx = new net.minecraft.client.particle.EntitySmokeFX(world, ix + ox * (i == 0 ? -1 : 1), iy, iz + oz * (i == 0 ? -1 : 1), mX, 0, mZ);
					float scale = 0.5F;
					ReflectionHelper.setPrivateValue(net.minecraft.client.particle.EntitySmokeFX.class, (net.minecraft.client.particle.EntitySmokeFX)fx, scale, "smokeParticleScale", "field_70587_a");
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
		}
		
		if("jetpack_bj".equals(type)) {
			
			if(particleSetting == 2)
				return;
			
			Entity ent = world.getEntityByID(data.getInteger("player"));
			
			if(ent instanceof EntityPlayer) {
				
				EntityPlayer p = (EntityPlayer)ent;
				
				Vec3 vec = Vec3.createVectorHelper(0, 0, -0.3125);
				Vec3 offset = Vec3.createVectorHelper(0.125, 0, 0);
				float angle = (float) -Math.toRadians(p.rotationYawHead - (p.rotationYawHead - p.renderYawOffset));

				vec.rotateAroundY(angle);
				offset.rotateAroundY(angle);
				
				double ix = p.posX + vec.xCoord;
				double iy = p.posY + p.eyeHeight - 0.9375;
				double iz = p.posZ + vec.zCoord;
				double ox = offset.xCoord;
				double oz = offset.zCoord;

				if(particleSetting == 0) {
					Vec3 pos = Vec3.createVectorHelper(ix, iy, iz);
					Vec3 thrust = Vec3.createVectorHelper(0, -1, 0);
					Vec3 target = pos.addVector(thrust.xCoord * 10, thrust.yCoord * 10, thrust.zCoord * 10);
					MovingObjectPosition mop = player.worldObj.func_147447_a(pos, target, false, false, true);
					
					if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK && mop.sideHit == 1) {
						
						Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
						
						Vec3 delta = Vec3.createVectorHelper(ix - mop.hitVec.xCoord, iy - mop.hitVec.yCoord, iz - mop.hitVec.zCoord);
						Vec3 vel = Vec3.createVectorHelper(0.75 - delta.lengthVector() * 0.075, 0, 0);
						
						for(int i = 0; i < (10 - delta.lengthVector()); i++) {
							vel.rotateAroundY(world.rand.nextFloat() * (float)Math.PI * 2F);
							Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBlockDustFX(world, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.1, mop.hitVec.zCoord, vel.xCoord, 0.1, vel.zCoord, b, meta));
						}
					}
				}

				EntityReddustFX dust1 = new EntityReddustFX(world, ix + ox, iy, iz + oz, 0.8F, 0.5F, 1.0F);
				EntityReddustFX dust2 = new EntityReddustFX(world, ix - ox, iy, iz - oz, 0.8F, 0.5F, 1.0F);
				dust1.setVelocity(p.motionX, p.motionY, p.motionZ);
				dust2.setVelocity(p.motionX, p.motionY, p.motionZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(dust1);
				Minecraft.getMinecraft().effectRenderer.addEffect(dust2);
			}
		}
		
		if("jetpack_dns".equals(type)) {
			
			if(particleSetting == 2)
				return;
			
			Entity ent = world.getEntityByID(data.getInteger("player"));
			
			if(ent instanceof EntityPlayer) {
				
				EntityPlayer p = (EntityPlayer)ent;
				
				Vec3 offset = Vec3.createVectorHelper(0.125, 0, 0);
				float angle = (float) -Math.toRadians(p.rotationYawHead - (p.rotationYawHead - p.renderYawOffset));

				offset.rotateAroundY(angle);
				
				double ix = p.posX;
				double iy = p.posY - p.getYOffset() - 0.5D;
				double iz = p.posZ;
				double ox = offset.xCoord;
				double oz = offset.zCoord;

				if(particleSetting == 0) {
					Vec3 pos = Vec3.createVectorHelper(ix, iy, iz);
					Vec3 thrust = Vec3.createVectorHelper(0, -1, 0);
					Vec3 target = pos.addVector(thrust.xCoord * 10, thrust.yCoord * 10, thrust.zCoord * 10);
					MovingObjectPosition mop = player.worldObj.func_147447_a(pos, target, false, false, true);
					
					if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK && mop.sideHit == 1) {
						
						Block b = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
						
						Vec3 delta = Vec3.createVectorHelper(ix - mop.hitVec.xCoord, iy - mop.hitVec.yCoord, iz - mop.hitVec.zCoord);
						Vec3 vel = Vec3.createVectorHelper(0.75 - delta.lengthVector() * 0.075, 0, 0);
						
						for(int i = 0; i < (10 - delta.lengthVector()); i++) {
							vel.rotateAroundY(world.rand.nextFloat() * (float)Math.PI * 2F);
							Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBlockDustFX(world, mop.hitVec.xCoord, mop.hitVec.yCoord + 0.1, mop.hitVec.zCoord, vel.xCoord, 0.1, vel.zCoord, b, meta));
						}
					}
				}

				EntityReddustFX dust1 = new EntityReddustFX(world, ix + ox, iy, iz + oz, 0.01F, 1.0F, 1.0F);
				EntityReddustFX dust2 = new EntityReddustFX(world, ix - ox, iy, iz - oz, 0.01F, 1.0F, 1.0F);
				dust1.setVelocity(p.motionX, p.motionY, p.motionZ);
				dust2.setVelocity(p.motionX, p.motionY, p.motionZ);
				Minecraft.getMinecraft().effectRenderer.addEffect(dust1);
				Minecraft.getMinecraft().effectRenderer.addEffect(dust2);
			}
		}
		
		if("muke".equals(type)) {

			ParticleMukeWave wave = new ParticleMukeWave(man, world, x, y, z);
			ParticleMukeFlash flash = new ParticleMukeFlash(man, world, x, y, z, data.getBoolean("balefire"));

			Minecraft.getMinecraft().effectRenderer.addEffect(wave);
			Minecraft.getMinecraft().effectRenderer.addEffect(flash);

			//single swing: 			HT 15,  MHT 15
			//double swing: 			HT 60,  MHT 50

			player.hurtTime = 15;
			player.maxHurtTime = 15;
			player.attackedAtYaw = 0F;
		}
		
		if("tinytot".equals(type)) {

			ParticleMukeWave wave = new ParticleMukeWave(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(wave);
			
    		for(double d = 0.0D; d <= 1.6D; d += 0.1) {
	    		ParticleMukeCloud cloud = new ParticleMukeCloud(man, world, x, y, z, rand.nextGaussian() * 0.05, d + rand.nextGaussian() * 0.02, rand.nextGaussian() * 0.05);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
    		for(int i = 0; i < 50; i++) {
	    		ParticleMukeCloud cloud = new ParticleMukeCloud(man, world, x, y + 0.5, z, rand.nextGaussian() * 0.5, rand.nextInt(5) == 0 ? 0.02 : 0, rand.nextGaussian() * 0.5);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
    		for(int i = 0; i < 15; i++) {
    			double ix = rand.nextGaussian() * 0.2;
    			double iz = rand.nextGaussian() * 0.2;
    			
    			if(ix * ix + iz * iz > 0.75) {
    				ix *= 0.5;
    				iz *= 0.5;
    			}
    			
    			double iy = 1.6 + (rand.nextDouble() * 2 - 1) * (0.75 - (ix * ix + iz * iz)) * 0.5;
    			
	    		ParticleMukeCloud cloud = new ParticleMukeCloud(man, world, x, y, z, ix, iy + rand.nextGaussian() * 0.02, iz);
	    		Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
    		}
			player.hurtTime = 15;
			player.maxHurtTime = 15;
			player.attackedAtYaw = 0F;
		}
		
		if("ufo".equals(type)) {
			double motion = data.getDouble("motion");
			ParticleMukeCloud cloud = new ParticleMukeCloud(man, world, x, y, z, rand.nextGaussian() * motion, 0, rand.nextGaussian() * motion);
			Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
		}
		
		if("haze".equals(type)) {

			ParticleHaze fog = new ParticleHaze(man, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(fog);
		}
		
		if("plasmablast".equals(type)) {
			
			ParticlePlasmaBlast cloud = new ParticlePlasmaBlast(man, world, x, y, z, data.getFloat("r"), data.getFloat("g"), data.getFloat("b"), data.getFloat("pitch"), data.getFloat("yaw"));
			cloud.setScale(data.getFloat("scale"));
			Minecraft.getMinecraft().effectRenderer.addEffect(cloud);
		}
		
		if("justTilt".equals(type)) {
			
			player.hurtTime = player.maxHurtTime = data.getInteger("time");
			player.attackedAtYaw = 0F;
		}
		
		if("properJolt".equals(type)) {
			
			player.hurtTime = data.getInteger("time");
			player.maxHurtTime = data.getInteger("maxTime");
			player.attackedAtYaw = 0F;
		}
		
		if("sweat".equals(type)) {
			
			Entity e = world.getEntityByID(data.getInteger("entity"));
			Block b = Block.getBlockById(data.getInteger("block"));
			int meta = data.getInteger("meta");
			
			if(e instanceof EntityLivingBase) {
				
				for(int i = 0; i < data.getInteger("count"); i++) {
	
					double ix = e.boundingBox.minX - 0.2 + (e.boundingBox.maxX - e.boundingBox.minX + 0.4) * rand.nextDouble();
					double iy = e.boundingBox.minY + (e.boundingBox.maxY - e.boundingBox.minY + 0.2) * rand.nextDouble();
					double iz = e.boundingBox.minZ - 0.2 + (e.boundingBox.maxZ - e.boundingBox.minZ + 0.4) * rand.nextDouble();
					
					
					EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(world, ix, iy, iz, 0, 0, 0, b, meta);
					ReflectionHelper.setPrivateValue(EntityFX.class, fx, 150 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
					
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
		}
		
		if("vomit".equals(type)) {
			
			Entity e = world.getEntityByID(data.getInteger("entity"));
			int count = data.getInteger("count") / (particleSetting + 1);
			
			if(e instanceof EntityLivingBase) {

				double ix = e.posX;
				double iy = e.posY - e.getYOffset() + e.getEyeHeight() + (e instanceof EntityPlayer ? 1 : 0);
				double iz = e.posZ;
				
				Vec3 vec = e.getLookVec();
				
				for(int i = 0; i < count; i++) {
					
					if("normal".equals(data.getString("mode"))) {
						EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(world, ix, iy, iz, (vec.xCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.yCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.zCoord + rand.nextGaussian() * 0.2) * 0.2, Blocks.stained_hardened_clay, (rand.nextBoolean() ? 5 : 13));
						ReflectionHelper.setPrivateValue(EntityFX.class, fx, 150 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
						Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					}
					
					if("blood".equals(data.getString("mode"))) {
						EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(world, ix, iy, iz, (vec.xCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.yCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.zCoord + rand.nextGaussian() * 0.2) * 0.2, Blocks.redstone_block, 0);
						ReflectionHelper.setPrivateValue(EntityFX.class, fx, 150 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
						Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					}
					
					if("smoke".equals(data.getString("mode"))) {
						EntityFX fx = new net.minecraft.client.particle.EntitySmokeFX(world, ix, iy, iz, (vec.xCoord + rand.nextGaussian() * 0.1) * 0.05, (vec.yCoord + rand.nextGaussian() * 0.1) * 0.05, (vec.zCoord + rand.nextGaussian() * 0.1) * 0.05, 0.2F);
						ReflectionHelper.setPrivateValue(EntityFX.class, fx, 10 + rand.nextInt(10), "particleMaxAge", "field_70547_e");
						Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					}
				}
			}
		}
		
		if("radiation".equals(type)) {
			
			for(int i = 0; i < data.getInteger("count"); i++) {
				
				EntityAuraFX flash = new EntityAuraFX(world,
						player.posX + rand.nextGaussian() * 4,
						player.posY + rand.nextGaussian() * 2,
						player.posZ + rand.nextGaussian() * 4,
						0, 0, 0);
				
				flash.setRBGColorF(0F, 0.75F, 1F);
				flash.setVelocity(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
				Minecraft.getMinecraft().effectRenderer.addEffect(flash);
			}
		}
		
		if("schrabfog".equals(type)) {
				
			EntityAuraFX flash = new EntityAuraFX(world, x, y, z, 0, 0, 0);
			flash.setRBGColorF(0F, 1F, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(flash);
		}
		
		if("hadron".equals(type)) {
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleHadron(man, world, x, y, z));
		}
		
		if("rift".equals(type)) {
			
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleRift(man, world, x, y, z));
		}
		
		if("rbmkflame".equals(type)) {
			int maxAge = data.getInteger("maxAge");
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleRBMKFlame(man, world, x, y, z, maxAge));
		}
		
		if("rbmkmush".equals(type)) {
			float scale = data.getFloat("scale");
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleRBMKMush(man, world, x, y, z, scale));
		}
		
		if("tower".equals(type)) {
			if(particleSetting == 0 || (particleSetting == 1 && rand.nextBoolean())) {
				ParticleCoolingTower fx = new ParticleCoolingTower(man, world, x, y, z);
				fx.setLift(data.getFloat("lift"));
				fx.setBaseScale(data.getFloat("base"));
				fx.setMaxScale(data.getFloat("max"));
				fx.setLife(data.getInteger("life") / (particleSetting + 1));
				
				if(data.hasKey("color")) {
					Color color = new Color(data.getInteger("color"));
					fx.setRBGColorF(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
				}
				
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
		}
		
		if("deadleaf".equals(type)) {
			if(particleSetting == 0 || (particleSetting == 1 && rand.nextBoolean()))
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleDeadLeaf(man, world, x, y, z));
		}
		
		if("anim".equals(type)) {
			
			String mode = data.getString("mode");
			
			/* crucible deploy */
			if("crucible".equals(mode) && player.getHeldItem() != null) {
				
				BusAnimation animation = new BusAnimation()
						.addBus("GUARD_ROT", new BusAnimationSequence()
								.addKeyframe(new BusAnimationKeyframe(90, 0, 1, 0))
								.addKeyframe(new BusAnimationKeyframe(90, 0, 1, 800))
								.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 50)));
				
				HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
			}
			
			/* crucible swing */
			if("cSwing".equals(mode)) {
				
				if(HbmAnimations.getRelevantTransformation("SWING_ROT")[0] == 0) {
					
					int offset = rand.nextInt(80) - 20;
					
					BusAnimation animation = new BusAnimation()
							.addBus("SWING_ROT", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(90 - offset, 90 - offset, 35, 75))
									.addKeyframe(new BusAnimationKeyframe(90 + offset, 90 - offset, -45, 150))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500)))
							.addBus("SWING_TRANS", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(-3, 0, 0, 75))
									.addKeyframe(new BusAnimationKeyframe(8, 0, 0, 150))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500)));

					Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:weapon.cSwing"), 0.8F + player.getRNG().nextFloat() * 0.2F));
					
					HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
				}
			}
			
			/* chainsaw swing */
			if("sSwing".equals(mode) || "lSwing".equals(mode)) { //temp for lance

				int forward = 150;
				int sideways = 100;
				int retire = 200;
				
				if(HbmAnimations.getRelevantAnim() == null) {
					
					BusAnimation animation = new BusAnimation()
							.addBus("SWING_ROT", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(0, 0, 90, forward))
									.addKeyframe(new BusAnimationKeyframe(45, 0, 90, sideways))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, retire)))
							.addBus("SWING_TRANS", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(0, 0, 3, forward))
									.addKeyframe(new BusAnimationKeyframe(2, 0, 2, sideways))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, retire)));

					
					HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
					
				} else {

					double[] rot = HbmAnimations.getRelevantTransformation("SWING_ROT");
					double[] trans = HbmAnimations.getRelevantTransformation("SWING_TRANS");
					
					if(System.currentTimeMillis() - HbmAnimations.getRelevantAnim().startMillis < 50) return;
					
					BusAnimation animation = new BusAnimation()
							.addBus("SWING_ROT", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(rot[0], rot[1], rot[2], 0))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 90, forward))
									.addKeyframe(new BusAnimationKeyframe(45, 0, 90, sideways))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, retire)))
							.addBus("SWING_TRANS", new BusAnimationSequence()
									.addKeyframe(new BusAnimationKeyframe(trans[0], trans[1], trans[2], 0))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 3, forward))
									.addKeyframe(new BusAnimationKeyframe(2, 0, 2, sideways))
									.addKeyframe(new BusAnimationKeyframe(0, 0, 0, retire)));
					
					HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
				}
			}
			
			if("generic".equals(mode)) {
				ItemStack stack = player.getHeldItem();
				
				if(stack != null && stack.getItem() instanceof IAnimatedItem) {
					IAnimatedItem item = (IAnimatedItem) stack.getItem();
					BusAnimation anim = item.getAnimation(data, stack);
					
					if(anim != null) {
						HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), anim);
					}
				}
			}
		}
		
		if("tau".equals(type)) {
			
			for(int i = 0; i < data.getByte("count"); i++)
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpark(world, x, y, z, rand.nextGaussian() * 0.05, 0.05, rand.nextGaussian() * 0.05));
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleHadron(man, world, x, y, z));
		}
		
		if("vanish".equals(type)) {
			int ent = data.getInteger("ent");
			this.vanish(ent);
		}
		
		if("giblets".equals(type)) {
			int ent = data.getInteger("ent");
			this.vanish(ent);
			Entity e = world.getEntityByID(ent);
			
			if(e == null)
				return;
			
			float width = e.width;
			float height = e.height;
			int gW = (int)(width / 0.25F);
			int gH = (int)(height / 0.25F);
			
			int count = (int) (gW * 1.5 * gH);
			
			if(data.hasKey("cDiv"))
				count = (int) Math.ceil(count / (double)data.getInteger("cDiv"));
			
			boolean blowMeIntoTheGodDamnStratosphere = rand.nextInt(15) == 0;
			double mult = 1D;
			
			if(blowMeIntoTheGodDamnStratosphere)
				mult *= 10;
			
			for(int i = 0; i < count; i++) {
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGiblet(man, world, x, y, z, rand.nextGaussian() * 0.25 * mult, rand.nextDouble() * mult, rand.nextGaussian() * 0.25 * mult));
			}
		}
		
		if("amat".equals(type)) {
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleAmatFlash(world, x, y, z, data.getFloat("scale")));
		}
		
		if("debug".equals(type)) {
			String t = data.getString("text");
			int color = data.getInteger("color");
			float scale = data.getFloat("scale");
			ParticleText text = new ParticleText(world, x, y, z, color, t);
			text.multipleParticleScaleBy(scale);
			Minecraft.getMinecraft().effectRenderer.addEffect(text);
		}
		
		if("network".equals(type)) {
			ParticleDebug debug = null;
			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");

			if("power".equals(data.getString("mode"))) {
				debug = new ParticleDebug(man, world, x, y, z, mX, mY, mZ);
			}
			if("fluid".equals(data.getString("mode"))) {
				int color = data.getInteger("color");
				debug = new ParticleDebug(man, world, x, y, z, mX, mY, mZ, color);
			}
			Minecraft.getMinecraft().effectRenderer.addEffect(debug);
		}
		
		if("gasfire".equals(type)) {
			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");
			float scale = data.getFloat("scale");
			ParticleGasFlame text = new ParticleGasFlame(world, x, y, z, mX, mY, mZ, scale > 0 ? scale : 6.5F);
			Minecraft.getMinecraft().effectRenderer.addEffect(text);
		}
		
		if("marker".equals(type)) {
			int color = data.getInteger("color");
			String label = data.getString("label");
			int expires = data.getInteger("expires");
			double dist = data.getDouble("dist");
			
			RenderOverhead.queuedMarkers.put(new BlockPos(x, y, z),  new Marker(color).setDist(dist).setExpire(expires > 0 ? System.currentTimeMillis() + expires : 0).withLabel(label.isEmpty() ? null : label));
		}
		
		if("casing".equals(type)) {
			CasingEjector ejector = CasingEjector.fromId(data.getInteger("ej"));
			if(ejector == null) return;
			SpentCasing casingConfig = SpentCasing.fromName((data.getString("name")));
			if(casingConfig == null) return;
			
			for(int i = 0; i < ejector.getAmount(); i++) {
				ejector.spawnCasing(man, casingConfig, world, x, y, z, data.getFloat("pitch"), data.getFloat("yaw"), data.getBoolean("crouched"));
			}
		}
		
		if("foundry".equals(type)) {
			int color = data.getInteger("color");
			byte dir = data.getByte("dir");
			float length = data.getFloat("len");
			float base = data.getFloat("base");
			float offset = data.getFloat("off");
			
			ParticleFoundry sploosh = new ParticleFoundry(man, world, x, y, z, color, dir, length, base, offset);
			Minecraft.getMinecraft().effectRenderer.addEffect(sploosh);
		}
	}
	
	private HashMap<Integer, Long> vanished = new HashMap();
	
	public void vanish(int ent) {
		vanished.put(ent, System.currentTimeMillis() + 2000);
	}
	
	@Override
	public boolean isVanished(Entity e) {
		
		if(e == null)
			return false;
		
		if(!this.vanished.containsKey(e.getEntityId()))
			return false;
		
		return this.vanished.get(e.getEntityId()) > System.currentTimeMillis();
	}
	
	@Override
	public AudioWrapper getLoopedSound(String sound, float x, float y, float z, float volume, float pitch) {
		
		AudioWrapperClient audio = new AudioWrapperClient(new ResourceLocation(sound));
		audio.updatePosition(x, y, z);
		return audio;
	}
	
	@Override
	public AudioWrapper getLoopedSoundStartStop(World world, String sound, String start, String stop, float x, float y, float z, float volume, float pitch) {
		AudioWrapperClientStartStop audio = new AudioWrapperClientStartStop(world, sound == null ? null : new ResourceLocation(sound), start, stop, volume * 5);
		audio.updatePosition(x, y, z);
		return audio;
	}

	@Override
	public void playSound(String sound, Object data) { }
	
	@Override
	public void displayTooltip(String msg, int time, int id) {
		
		if(id != 0)
			this.theInfoSystem.push(new InfoEntry(msg, time), id);
		else
			this.theInfoSystem.push(new InfoEntry(msg, time));
	}

	@Override
	public boolean getIsKeyPressed(EnumKeybind key) {

		switch(key){
		case JETPACK:			return Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed();
		case TOGGLE_JETPACK:	return HbmKeybinds.jetpackKey.getIsKeyPressed();
		case TOGGLE_HEAD:		return HbmKeybinds.hudKey.getIsKeyPressed();
		case RELOAD:			return HbmKeybinds.reloadKey.getIsKeyPressed();
		case DASH:				return HbmKeybinds.dashKey.getIsKeyPressed();
		case CRANE_UP:			return HbmKeybinds.craneUpKey.getIsKeyPressed();
		case CRANE_DOWN:		return HbmKeybinds.craneDownKey.getIsKeyPressed();
		case CRANE_LEFT:		return HbmKeybinds.craneLeftKey.getIsKeyPressed();
		case CRANE_RIGHT:		return HbmKeybinds.craneRightKey.getIsKeyPressed();
		case CRANE_LOAD:		return HbmKeybinds.craneLoadKey.getIsKeyPressed();
		}
		
		return false;
	}

	@Override
	public EntityPlayer me() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	@Override
	public void openLink(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) { }
	}
	
	@Override
	public List<ItemStack> getSubItems(ItemStack stack) {
		
		List<ItemStack> list = new ArrayList();
		stack.getItem().getSubItems(stack.getItem(), stack.getItem().getCreativeTab(), list);
		for(ItemStack sta : list) {
			sta.stackSize = stack.stackSize;
		}
		return list;
	}

	@Override
	public float getImpactDust(World world) {
		return ImpactWorldHandler.getDustForClient(world);
	}

	@Override
	public float getImpactFire(World world) {
		return ImpactWorldHandler.getFireForClient(world);
	}

	@Override
	public boolean getImpact(World world) {
		return ImpactWorldHandler.getImpactForClient(world);
	}
}

