package com.hbm.main;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityChopperMine;
import com.hbm.entity.EntityCloudFleija;
import com.hbm.entity.EntityCombineBall;
import com.hbm.entity.EntityGrenadeASchrab;
import com.hbm.entity.EntityGrenadeCluster;
import com.hbm.entity.EntityGrenadeElectric;
import com.hbm.entity.EntityGrenadeFire;
import com.hbm.entity.EntityGrenadeFlare;
import com.hbm.entity.EntityGrenadeFrag;
import com.hbm.entity.EntityGrenadeGas;
import com.hbm.entity.EntityGrenadeGeneric;
import com.hbm.entity.EntityGrenadeLemon;
import com.hbm.entity.EntityGrenadeMk2;
import com.hbm.entity.EntityGrenadeNuclear;
import com.hbm.entity.EntityGrenadeNuke;
import com.hbm.entity.EntityGrenadePlasma;
import com.hbm.entity.EntityGrenadePoison;
import com.hbm.entity.EntityGrenadeSchrabidium;
import com.hbm.entity.EntityGrenadeStrong;
import com.hbm.entity.EntityGrenadeTau;
import com.hbm.entity.EntityGrenadeZOMG;
import com.hbm.entity.EntityHunterChopper;
import com.hbm.entity.EntityMiniNuke;
import com.hbm.entity.EntityMirv;
import com.hbm.entity.EntityMissileAntiBallistic;
import com.hbm.entity.EntityMissileBunkerBuster;
import com.hbm.entity.EntityMissileBurst;
import com.hbm.entity.EntityMissileBusterStrong;
import com.hbm.entity.EntityMissileCluster;
import com.hbm.entity.EntityMissileClusterStrong;
import com.hbm.entity.EntityMissileDrill;
import com.hbm.entity.EntityMissileEndo;
import com.hbm.entity.EntityMissileExo;
import com.hbm.entity.EntityMissileGeneric;
import com.hbm.entity.EntityMissileIncendiary;
import com.hbm.entity.EntityMissileIncendiaryStrong;
import com.hbm.entity.EntityMissileInferno;
import com.hbm.entity.EntityMissileMirv;
import com.hbm.entity.EntityMissileNuclear;
import com.hbm.entity.EntityMissileRain;
import com.hbm.entity.EntityMissileStrong;
import com.hbm.entity.EntityNuclearCreeper;
import com.hbm.entity.EntityNukeCloudBig;
import com.hbm.entity.EntityNukeCloudNoShroom;
import com.hbm.entity.EntityNukeCloudSmall;
import com.hbm.entity.EntityRainbow;
import com.hbm.entity.EntityRocket;
import com.hbm.entity.EntitySchrab;
import com.hbm.entity.EntityTestMissile;
import com.hbm.items.ModItems;
import com.hbm.particles.EntityBSmokeFX;
import com.hbm.particles.EntitySmokeFX;
import com.hbm.render.ItemRenderRevolverCursed;
import com.hbm.render.ItemRenderRevolverIron;
import com.hbm.render.ItemRenderRevolverLead;
import com.hbm.render.BSmokeRenderer;
import com.hbm.render.ItemRenderBigSword;
import com.hbm.render.ItemRenderDecoBlock;
import com.hbm.render.ItemRenderFatMan;
import com.hbm.render.ItemRenderMP;
import com.hbm.render.ItemRenderMiniNuke;
import com.hbm.render.ItemRenderOSIPR;
import com.hbm.render.ItemRenderPoleTop;
import com.hbm.render.ItemRenderRedstoneSword;
import com.hbm.render.ItemRenderRevolver;
import com.hbm.render.ItemRenderRevolverGold;
import com.hbm.render.ItemRenderRevolverSchrabidium;
import com.hbm.render.ItemRenderRocket;
import com.hbm.render.ItemRenderRpg;
import com.hbm.render.ItemRenderSatelliteReceiver;
import com.hbm.render.ItemRenderSteelPoles;
import com.hbm.render.ItemRenderTapeRecorder;
import com.hbm.render.ItemRenderTestBombAdvanced;
import com.hbm.render.ItemRenderTestContainer;
import com.hbm.render.ItemRenderXVL1456;
import com.hbm.render.ItemRenderZOMG;
import com.hbm.render.ModEffectRenderer;
import com.hbm.render.RenderBigNuke;
import com.hbm.render.RenderBombMulti;
import com.hbm.render.RenderCable;
import com.hbm.render.RenderCentrifuge;
import com.hbm.render.RenderChopperMine;
import com.hbm.render.RenderCloudFleija;
import com.hbm.render.RenderCrashedBomb;
import com.hbm.render.RenderDecoBlock;
import com.hbm.render.RenderDecoBlockAlt;
import com.hbm.render.RenderFlare;
import com.hbm.render.RenderHunterChopper;
import com.hbm.render.RenderLaunchPadTier1;
import com.hbm.render.RenderMiniNuke;
import com.hbm.render.RenderMirv;
import com.hbm.render.RenderMissileGeneric;
import com.hbm.render.RenderMissileHuge;
import com.hbm.render.RenderMissileMirv;
import com.hbm.render.RenderMissileNuclear;
import com.hbm.render.RenderMissileStrong;
import com.hbm.render.RenderMissileThermo;
import com.hbm.render.RenderNoCloud;
import com.hbm.render.RenderNuclearCreeper;
import com.hbm.render.RenderNukeBoy;
import com.hbm.render.RenderNukeFleija;
import com.hbm.render.RenderNukeGadget;
import com.hbm.render.RenderNukeMan;
import com.hbm.render.RenderNukeMike;
import com.hbm.render.RenderNukePrototype;
import com.hbm.render.RenderNukeTsar;
import com.hbm.render.RenderPoleSatelliteReceiver;
import com.hbm.render.RenderPoleTop;
import com.hbm.render.RenderPuF6Tank;
import com.hbm.render.RenderRainbow;
import com.hbm.render.RenderRedBarrel;
import com.hbm.render.RenderRocket;
import com.hbm.render.RenderRotationTester;
import com.hbm.render.RenderSmallNukeAlt;
import com.hbm.render.RenderSteelPoles;
import com.hbm.render.RenderTapeRecorder;
import com.hbm.render.RenderTestBombAdvanced;
import com.hbm.render.RenderTestContainer;
import com.hbm.render.RenderTestMissile;
import com.hbm.render.RenderTestRender;
import com.hbm.render.RenderUF6Tank;
import com.hbm.render.RenderYellowBarrel;
import com.hbm.render.RendererObjTester;
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
import com.hbm.tileentity.TileEntityLaunchPad;
import com.hbm.tileentity.TileEntityMachineCentrifuge;
import com.hbm.tileentity.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.TileEntityMachineUF6Tank;
import com.hbm.tileentity.TileEntityNukeBoy;
import com.hbm.tileentity.TileEntityNukeFleija;
import com.hbm.tileentity.TileEntityNukeGadget;
import com.hbm.tileentity.TileEntityNukeMan;
import com.hbm.tileentity.TileEntityNukeMike;
import com.hbm.tileentity.TileEntityNukePrototype;
import com.hbm.tileentity.TileEntityNukeTsar;
import com.hbm.tileentity.TileEntityObjTester;
import com.hbm.tileentity.TileEntityRedBarrel;
import com.hbm.tileentity.TileEntityRotationTester;
import com.hbm.tileentity.TileEntityTestBombAdvanced;
import com.hbm.tileentity.TileEntityTestContainer;
import com.hbm.tileentity.TileEntityTestRender;
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeBoy.class, new RenderNukeBoy());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCentrifuge.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMan.class, new RenderNukeMan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUF6Tank.class, new RenderUF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePuF6Tank.class, new RenderPuF6Tank());

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

		RenderingRegistry.registerEntityRenderingHandler(EntitySchrab.class, new RenderFlare());

	    RenderingRegistry.registerEntityRenderingHandler(EntityTestMissile.class, new RenderTestMissile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeFleija.class, new RenderNukeFleija());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrashedBomb.class, new RenderCrashedBomb());
		
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeAlt());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudBig.class, new RenderBigNuke());
	    RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleija.class, new RenderCloudFleija());
	    RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudNoShroom.class, new RenderNoCloud());

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
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman, new ItemRenderFatMan());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_xvl1456, new ItemRenderXVL1456());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_zomg, new ItemRenderZOMG());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_osipr, new ItemRenderOSIPR());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mp, new ItemRenderMP());

		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderRocket());
	    RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, new RenderMiniNuke());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_fatman_ammo, new ItemRenderMiniNuke());
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbow.class, new RenderRainbow());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukePrototype.class, new RenderNukePrototype());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRedBarrel.class, new RenderRedBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityYellowBarrel.class, new RenderYellowBarrel());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunchPadTier1());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());

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
	    RenderingRegistry.registerEntityRenderingHandler(EntityMirv.class, new RenderMirv());

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

	    RenderingRegistry.registerEntityRenderingHandler(EntityChopperMine.class, new RenderChopperMine());

	    RenderingRegistry.registerEntityRenderingHandler(EntitySmokeFX.class, new ModEffectRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBSmokeFX.class, new BSmokeRenderer(ModItems.nuclear_waste));
	    RenderingRegistry.registerEntityRenderingHandler(EntityCombineBall.class, new RenderSnowball(ModItems.energy_ball));
	    
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

