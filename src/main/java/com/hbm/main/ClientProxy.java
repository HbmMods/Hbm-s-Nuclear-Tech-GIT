 package com.hbm.main;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityStun;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeLunatic;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeStunning;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.grenade.EntityWastePearl;
import com.hbm.entity.item.EntityFireworks;
import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.entity.missile.EntitySoyuzCapsule;
import com.hbm.entity.missile.EntityTestMissile;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.mob.sodtekhnologiyah.EntityBallsOTronSegment;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBeamVortex;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.entity.projectile.EntityNightmareBlast;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.entity.projectile.EntityWaterSplash;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.ModItems;
import com.hbm.particle.ParticleContrail;
import com.hbm.particle.ParticleExSmoke;
import com.hbm.particle.ParticleHadron;
import com.hbm.particle.ParticleLetter;
import com.hbm.particle.ParticleMukeCloud;
import com.hbm.particle.ParticleMukeFlash;
import com.hbm.particle.ParticleMukeWave;
import com.hbm.particle.ParticleRadiationFog;
import com.hbm.particle.ParticleRift;
import com.hbm.particle.ParticleRocketFlame;
import com.hbm.particle.ParticleSmokePlume;
import com.hbm.particle.ParticleSpark;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.block.RenderAntennaTop;
import com.hbm.render.block.RenderBarbedWire;
import com.hbm.render.block.RenderBarrel;
import com.hbm.render.block.RenderChain;
import com.hbm.render.block.RenderConserve;
import com.hbm.render.block.RenderConveyor;
import com.hbm.render.block.RenderFence;
import com.hbm.render.block.RenderMirror;
import com.hbm.render.block.RenderRTGBlock;
import com.hbm.render.block.RenderScaffoldBlock;
import com.hbm.render.block.RenderSpikeBlock;
import com.hbm.render.block.RenderSteelBeam;
import com.hbm.render.block.RenderTaintBlock;
import com.hbm.render.block.RenderTapeBlock;
import com.hbm.render.entity.RenderEmpty;
import com.hbm.render.entity.effect.ElectricityRenderer;
import com.hbm.render.entity.effect.FogRenderer;
import com.hbm.render.entity.effect.GasFlameRenderer;
import com.hbm.render.entity.effect.GasRenderer;
import com.hbm.render.entity.effect.MultiCloudRenderer;
import com.hbm.render.entity.effect.RenderBigNuke;
import com.hbm.render.entity.effect.RenderBlackHole;
import com.hbm.render.entity.effect.RenderCloudFleija;
import com.hbm.render.entity.effect.RenderCloudRainbow;
import com.hbm.render.entity.effect.RenderCloudSolinium;
import com.hbm.render.entity.effect.RenderCloudTom;
import com.hbm.render.entity.effect.RenderDeathBlast;
import com.hbm.render.entity.effect.RenderEMPBlast;
import com.hbm.render.entity.effect.RenderFallout;
import com.hbm.render.entity.effect.RenderFireball;
import com.hbm.render.entity.effect.RenderFlare;
import com.hbm.render.entity.effect.RenderNoCloud;
import com.hbm.render.entity.effect.RenderSmallNukeMK4;
import com.hbm.render.entity.effect.RenderStun;
import com.hbm.render.entity.effect.SSmokeRenderer;
import com.hbm.render.entity.effect.SpillRenderer;
import com.hbm.render.entity.effect.TSmokeRenderer;
import com.hbm.render.entity.item.RenderBomber;
import com.hbm.render.entity.item.RenderMinecartTest;
import com.hbm.render.entity.item.RenderMovingItem;
import com.hbm.render.entity.mob.RenderBalls;
import com.hbm.render.entity.mob.RenderCyberCrab;
import com.hbm.render.entity.mob.RenderDuck;
import com.hbm.render.entity.mob.RenderFBI;
import com.hbm.render.entity.mob.RenderHunterChopper;
import com.hbm.render.entity.mob.RenderMaskMan;
import com.hbm.render.entity.mob.RenderNuclearCreeper;
import com.hbm.render.entity.mob.RenderQuacc;
import com.hbm.render.entity.mob.RenderRADBeast;
import com.hbm.render.entity.mob.RenderTaintCrab;
import com.hbm.render.entity.mob.RenderTaintedCreeper;
import com.hbm.render.entity.mob.RenderTeslaCrab;
import com.hbm.render.entity.mob.RenderWormBody;
import com.hbm.render.entity.mob.RenderWormHead;
import com.hbm.render.entity.projectile.RenderBeam;
import com.hbm.render.entity.projectile.RenderBeam2;
import com.hbm.render.entity.projectile.RenderBeam3;
import com.hbm.render.entity.projectile.RenderBeam4;
import com.hbm.render.entity.projectile.RenderBeam5;
import com.hbm.render.entity.projectile.RenderBeam6;
import com.hbm.render.entity.projectile.RenderBombletSelena;
import com.hbm.render.entity.projectile.RenderBombletTheta;
import com.hbm.render.entity.projectile.RenderBoxcar;
import com.hbm.render.entity.projectile.RenderBullet;
import com.hbm.render.entity.projectile.RenderChopperMine;
import com.hbm.render.entity.projectile.RenderFOEQ;
import com.hbm.render.entity.projectile.RenderFallingNuke;
import com.hbm.render.entity.projectile.RenderGrenade;
import com.hbm.render.entity.projectile.RenderLN2;
import com.hbm.render.entity.projectile.RenderLaser;
import com.hbm.render.entity.projectile.RenderMeteor;
import com.hbm.render.entity.projectile.RenderMirv;
import com.hbm.render.entity.projectile.RenderOminousBullet;
import com.hbm.render.entity.projectile.RenderRainbow;
import com.hbm.render.entity.projectile.RenderRocket;
import com.hbm.render.entity.projectile.RenderRubble;
import com.hbm.render.entity.projectile.RenderSRocket;
import com.hbm.render.entity.projectile.RenderShrapnel;
import com.hbm.render.entity.projectile.RenderTom;
import com.hbm.render.entity.projectile.RenderVortexBeam;
import com.hbm.render.entity.rocket.RenderBoosterMissile;
import com.hbm.render.entity.rocket.RenderCarrierMissile;
import com.hbm.render.entity.rocket.RenderMinerRocket;
import com.hbm.render.entity.rocket.RenderMissileCustom;
import com.hbm.render.entity.rocket.RenderMissileDoomsday;
import com.hbm.render.entity.rocket.RenderMissileGeneric;
import com.hbm.render.entity.rocket.RenderMissileHuge;
import com.hbm.render.entity.rocket.RenderMissileMirv;
import com.hbm.render.entity.rocket.RenderMissileNuclear;
import com.hbm.render.entity.rocket.RenderMissileStrong;
import com.hbm.render.entity.rocket.RenderMissileTaint;
import com.hbm.render.entity.rocket.RenderMissileThermo;
import com.hbm.render.entity.rocket.RenderSoyuz;
import com.hbm.render.entity.rocket.RenderSoyuzCapsule;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.item.ItemRenderLibrary;
import com.hbm.render.item.ItemRenderMissile;
import com.hbm.render.item.ItemRenderMissilePart;
import com.hbm.render.item.ItemRenderMultitool;
import com.hbm.render.item.ItemRenderSatoriEye;
import com.hbm.render.item.ItemRenderTemplate;
import com.hbm.render.item.ItemRendererHot;
import com.hbm.render.item.ItemRendererMeteorSword;
import com.hbm.render.item.block.ItemRenderDecoBlock;
import com.hbm.render.item.block.ItemRenderTestContainer;
import com.hbm.render.item.weapon.ItemRenderBFLauncher;
import com.hbm.render.item.weapon.ItemRenderBenelli;
import com.hbm.render.item.weapon.ItemRenderBigSword;
import com.hbm.render.item.weapon.ItemRenderBullshit;
import com.hbm.render.item.weapon.ItemRenderCrucible;
import com.hbm.render.item.weapon.ItemRenderCryolator;
import com.hbm.render.item.weapon.ItemRenderEMPRay;
import com.hbm.render.item.weapon.ItemRenderFatMan;
import com.hbm.render.item.weapon.ItemRenderGavel;
import com.hbm.render.item.weapon.ItemRenderGunAnim;
import com.hbm.render.item.weapon.ItemRenderHLR;
import com.hbm.render.item.weapon.ItemRenderImmolator;
import com.hbm.render.item.weapon.ItemRenderLunaticSniper;
import com.hbm.render.item.weapon.ItemRenderMIRVLauncher;
import com.hbm.render.item.weapon.ItemRenderMLR;
import com.hbm.render.item.weapon.ItemRenderMP;
import com.hbm.render.item.weapon.ItemRenderMP40;
import com.hbm.render.item.weapon.ItemRenderOSIPR;
import com.hbm.render.item.weapon.ItemRenderObj;
import com.hbm.render.item.weapon.ItemRenderOverkill;
import com.hbm.render.item.weapon.ItemRenderRedstoneSword;
import com.hbm.render.item.weapon.ItemRenderRevolver;
import com.hbm.render.item.weapon.ItemRenderRevolverCursed;
import com.hbm.render.item.weapon.ItemRenderRevolverGold;
import com.hbm.render.item.weapon.ItemRenderRevolverInverted;
import com.hbm.render.item.weapon.ItemRenderRevolverIron;
import com.hbm.render.item.weapon.ItemRenderRevolverLead;
import com.hbm.render.item.weapon.ItemRenderRevolverNightmare;
import com.hbm.render.item.weapon.ItemRenderRevolverSchrabidium;
import com.hbm.render.item.weapon.ItemRenderRpg;
import com.hbm.render.item.weapon.ItemRenderShim;
import com.hbm.render.item.weapon.ItemRenderStinger;
import com.hbm.render.item.weapon.ItemRenderTWR;
import com.hbm.render.item.weapon.ItemRenderTestBombAdvanced;
import com.hbm.render.item.weapon.ItemRenderUZI;
import com.hbm.render.item.weapon.ItemRenderUboinik;
import com.hbm.render.item.weapon.ItemRenderWeaponBolter;
import com.hbm.render.item.weapon.ItemRenderWeaponObj;
import com.hbm.render.item.weapon.ItemRenderWeaponQuadro;
import com.hbm.render.item.weapon.ItemRenderWeaponSauer;
import com.hbm.render.item.weapon.ItemRenderWeaponShotty;
import com.hbm.render.item.weapon.ItemRenderWeaponThompson;
import com.hbm.render.item.weapon.ItemRenderWeaponVortex;
import com.hbm.render.item.weapon.ItemRenderXVL1456;
import com.hbm.render.item.weapon.ItemRenderZOMG;
import com.hbm.render.loader.HmfModelLoader;
import com.hbm.render.tileentity.*;
import com.hbm.render.util.MissilePart;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.AudioWrapperClient;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityCelPrime;
import com.hbm.tileentity.bomb.TileEntityCelPrimeBattery;
import com.hbm.tileentity.bomb.TileEntityCelPrimePort;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTanks;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTerminal;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;
import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityTestBombAdvanced;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;
import com.hbm.tileentity.conductor.TileEntityGasDuct;
import com.hbm.tileentity.conductor.TileEntityOilDuct;
import com.hbm.tileentity.conductor.TileEntityPylonRedWire;
import com.hbm.tileentity.conductor.TileEntityRFDuct;
import com.hbm.tileentity.deco.TileEntityBomber;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityDecoBlockAlt;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltF;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltG;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltW;
import com.hbm.tileentity.deco.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.tileentity.deco.TileEntityObjTester;
import com.hbm.tileentity.deco.TileEntityRotationTester;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityBarrel;
import com.hbm.tileentity.machine.TileEntityBlastDoor;
import com.hbm.tileentity.machine.TileEntityBroadcaster;
import com.hbm.tileentity.machine.TileEntityCore;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.hbm.tileentity.machine.TileEntityFEL;
import com.hbm.tileentity.machine.TileEntityFF;
import com.hbm.tileentity.machine.TileEntityForceField;
import com.hbm.tileentity.machine.TileEntityGeiger;
import com.hbm.tileentity.machine.TileEntityITER;
import com.hbm.tileentity.machine.TileEntityITERStruct;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.tileentity.machine.TileEntityMachineEPress;
import com.hbm.tileentity.machine.TileEntityMachineFENSU;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineGasFlare;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;
import com.hbm.tileentity.machine.TileEntityMachineLargeTurbine;
import com.hbm.tileentity.machine.TileEntityMachineMiniRTG;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.hbm.tileentity.machine.TileEntityMachineRadar;
import com.hbm.tileentity.machine.TileEntityMachineReactor;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineRefinery;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.tileentity.machine.TileEntityMachineShredderLarge;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;
import com.hbm.tileentity.machine.TileEntityMicrowave;
import com.hbm.tileentity.machine.TileEntityMultiblock;
import com.hbm.tileentity.machine.TileEntityPlasmaStruct;
import com.hbm.tileentity.machine.TileEntityRadioRec;
import com.hbm.tileentity.machine.TileEntityRadiobox;
import com.hbm.tileentity.machine.TileEntitySILEX;
import com.hbm.tileentity.machine.TileEntitySolarBoiler;
import com.hbm.tileentity.machine.TileEntitySoyuzCapsule;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;
import com.hbm.tileentity.machine.TileEntitySoyuzStruct;
import com.hbm.tileentity.machine.TileEntityStructureMarker;
import com.hbm.tileentity.machine.TileEntityTesla;
import com.hbm.tileentity.machine.TileEntityVaultDoor;
import com.hbm.tileentity.turret.TileEntityTurretCIWS;
import com.hbm.tileentity.turret.TileEntityTurretCheapo;
import com.hbm.tileentity.turret.TileEntityTurretChekhov;
import com.hbm.tileentity.turret.TileEntityTurretFlamer;
import com.hbm.tileentity.turret.TileEntityTurretFriendly;
import com.hbm.tileentity.turret.TileEntityTurretHeavy;
import com.hbm.tileentity.turret.TileEntityTurretHoward;
import com.hbm.tileentity.turret.TileEntityTurretJeremy;
import com.hbm.tileentity.turret.TileEntityTurretLight;
import com.hbm.tileentity.turret.TileEntityTurretRichard;
import com.hbm.tileentity.turret.TileEntityTurretRocket;
import com.hbm.tileentity.turret.TileEntityTurretSpitfire;
import com.hbm.tileentity.turret.TileEntityTurretTau;
import com.hbm.tileentity.turret.TileEntityTurretTauon;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
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
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretChekhov.class, new RenderTurretChekhov());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFriendly.class, new RenderTurretFriendly());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretJeremy.class, new RenderTurretJeremy());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTauon.class, new RenderTurretTauon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretRichard.class, new RenderTurretRichard());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHoward.class, new RenderTurretHoward());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTsukuyomi.class, new RenderTurretTsukuyomi());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarBoiler.class, new RenderSolarBoiler());
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
		//hot stuff
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_steel_dusted, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_chainsteel, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_meteorite, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_meteorite_forged, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.blade_meteorite, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_dineutronium_forged, new ItemRendererHot());
		MinecraftForgeClient.registerItemRenderer(ModItems.ingot_desh, new ItemRendererHot());
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
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolter, new ItemRenderWeaponBolter());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_bolter_digamma, new ItemRenderWeaponBolter());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_twr, new ItemRenderTWR());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_hlr, new ItemRenderHLR());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_benelli, new ItemRenderBenelli());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_lunatic_marksman, new ItemRenderLunaticSniper());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_mlr, new ItemRenderMLR());
		MinecraftForgeClient.registerItemRenderer(ModItems.test_gun, new ItemRenderUboinik());
		
		//Satori
		MinecraftForgeClient.registerItemRenderer(ModItems.eye, new ItemRenderSatoriEye());
		
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
	    RenderingRegistry.registerEntityRenderingHandler(EntityFireworks.class, new RenderShrapnel());
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
		RenderingRegistry.registerEntityRenderingHandler(EntityWastePearl.class, new RenderSnowball(ModItems.nuclear_waste_pearl));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeLunatic.class, new RenderSnowball(ModItems.grenade_lunatic));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeStunning.class, new RenderSnowball(ModItems.grenade_stunning));
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
	    RenderingRegistry.registerEntityRenderingHandler(EntityQuackos.class, new RenderQuacc(new ModelChicken(), 7.5F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFBI.class, new RenderFBI());
	    RenderingRegistry.registerEntityRenderingHandler(EntityRADBeast.class, new RenderRADBeast());
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
	    RenderingRegistry.registerEntityRenderingHandler(EntityStun.class, new RenderStun());
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
		RenderingRegistry.registerBlockHandler(new RenderChain());
		RenderingRegistry.registerBlockHandler(new RenderMirror());
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
			
			if(fx != null)
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
		
		if("vanilla".equals(type)) {

			double mX = data.getDouble("mX");
			double mY = data.getDouble("mY");
			double mZ = data.getDouble("mZ");
			world.spawnParticle(data.getString("mode"), x, y, z, mX, mY, mZ);
		}
		
		if("jetpack".equals(type)) {
			
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

				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix + ox, iy, iz + oz, p.motionX + moX * 2, p.motionY + moY * 2, p.motionZ + moZ * 2));
				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityFlameFX(world, ix - ox, iy, iz - oz, p.motionX + moX * 2, p.motionY + moY * 2, p.motionZ + moZ * 2));
				Minecraft.getMinecraft().effectRenderer.addEffect(new net.minecraft.client.particle.EntitySmokeFX(world, ix + ox, iy, iz + oz, p.motionX + moX * 3, p.motionY + moY * 3, p.motionZ + moZ * 3));
				Minecraft.getMinecraft().effectRenderer.addEffect(new net.minecraft.client.particle.EntitySmokeFX(world, ix - ox, iy, iz - oz, p.motionX + moX * 3, p.motionY + moY * 3, p.motionZ + moZ * 3));
			}
		}
		
		if("jetpack_bj".equals(type)) {
			
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

				EntityReddustFX dust1 = new EntityReddustFX(world, ix + ox, iy, iz + oz, 0.8F, 0.5F, 1.0F);
				EntityReddustFX dust2 = new EntityReddustFX(world, ix - ox, iy, iz - oz, 0.8F, 0.5F, 1.0F);
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
			//vic's immersive swing: 	HT 100, MHT 50
			
			if(player.getDisplayName().equals("Vic4Games")) {
				player.hurtTime = 100;
				player.maxHurtTime = 50;
			} else {
				player.hurtTime = 15;
				player.maxHurtTime = 15;
			}
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
			if(player.getDisplayName().equals("Vic4Games")) {
				player.hurtTime = 100;
				player.maxHurtTime = 50;
			} else {
				player.hurtTime = 15;
				player.maxHurtTime = 15;
			}
			player.attackedAtYaw = 0F;
		}
		
		if("justTilt".equals(type)) {
			
			player.hurtTime = player.maxHurtTime = data.getInteger("time");
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
			
			if(e instanceof EntityLivingBase) {

				double ix = e.posX;
				double iy = e.posY - e.getYOffset() + e.getEyeHeight() + 1;
				double iz = e.posZ;
				
				Vec3 vec = e.getLookVec();
				
				for(int i = 0; i < 25; i++) {
					EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(world, ix, iy, iz, (vec.xCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.yCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.zCoord + rand.nextGaussian() * 0.2) * 0.2, Blocks.stained_hardened_clay, 13);
					ReflectionHelper.setPrivateValue(EntityFX.class, fx, 150 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
				}
			}
		}
		
		if("bloodvomit".equals(type)) {
			
			Entity e = world.getEntityByID(data.getInteger("entity"));
			
			if(e instanceof EntityLivingBase) {

				double ix = e.posX;
				double iy = e.posY - e.getYOffset() + e.getEyeHeight() + 1;
				double iz = e.posZ;
				
				Vec3 vec = e.getLookVec();
				
				for(int i = 0; i < 25; i++) {
					EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(world, ix, iy, iz, (vec.xCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.yCoord + rand.nextGaussian() * 0.2) * 0.2, (vec.zCoord + rand.nextGaussian() * 0.2) * 0.2, Blocks.redstone_block, 0);
					ReflectionHelper.setPrivateValue(EntityFX.class, fx, 150 + rand.nextInt(50), "particleMaxAge", "field_70547_e");
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
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
		
		if("anim".equals(type)) {
			
			if("crucible".equals(data.getString("mode")) && player.getHeldItem() != null) {
				
				BusAnimation animation = new BusAnimation()
						.addBus("GUARD_ROT", new BusAnimationSequence()
								.addKeyframe(new BusAnimationKeyframe(90, 0, 1, 0))
								.addKeyframe(new BusAnimationKeyframe(90, 0, 1, 800))
								.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 50)));
				
				HbmAnimations.hotbar[player.inventory.currentItem] = new Animation(player.getHeldItem().getItem().getUnlocalizedName(), System.currentTimeMillis(), animation);
			}
			
			if("cSwing".equals(data.getString("mode"))) {
				
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
		}
		
		if("tau".equals(type)) {
			
			for(int i = 0; i < data.getByte("count"); i++)
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpark(world, x, y, z, rand.nextGaussian() * 0.05, 0.05, rand.nextGaussian() * 0.05));
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

	@Override
	public boolean getIsKeyPressed(EnumKeybind key) {
		
		if(key == EnumKeybind.JETPACK)
			return Minecraft.getMinecraft().gameSettings.keyBindJump.getIsKeyPressed();
		
		return false;
	}

	@Override
	public EntityPlayer me() {
		return Minecraft.getMinecraft().thePlayer;
	}
}

