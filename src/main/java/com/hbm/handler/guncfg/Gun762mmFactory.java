package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun762mmFactory
{

	public static GunConfiguration getUACDMRConfig()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 30;
		config.firingDuration = 8;
		config.ammoCap = 30;
		config.durability = 30000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.DMRMagInPB3";
		config.firingSound = "hbm:weapon.DMRShootPB3Alt";
//		config.firingPitch = 0.85F;
		config.reloadSoundEnd = true;
		
		config.name = "uacDMR";
		config.manufacturer = EnumGunManufacturer.UAC;
		
		config.config.addAll(HbmCollection.threeZeroEight);
		
		return config;
	}
	
	public static GunConfiguration getUACCarbineConfig()
	{
		GunConfiguration config = getUACDMRConfig();
		
		config.rateOfFire = 2;
		config.reloadDuration = 20;
		config.ammoCap = 40;
		config.durability = 40000;
		config.crosshair = Crosshair.SPLIT;
		config.reloadSound = "hbm:weapon.carbineMagInPB3";
		config.firingSound = "hbm:weapon.carbineShootPB3";
		
		config.name = "uacCarbine";
		
		return config;
	}

	public static GunConfiguration getUACLMGConfig()
	{
		GunConfiguration config = getUACCarbineConfig();
		
		config.ammoCap = 60;
		config.durability = 50000;
		config.crosshair = Crosshair.BOX;
		config.reloadSound = "hbm:weapon.LMGMagInPB3";
		config.firingSound = "hbm:weapon.LMGShootPB3Alt";
//		config.firingPitch = 0.9F;
		
		config.name = "uacLMG";
		
		return config;
	}

	public static GunConfiguration getM60Config()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.durability = 10000;
		config.roundsPerCycle = 1;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.ammoCap = 0;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_BOX;
		config.firingSound = "hbm:weapon.LMGShootPB3";
		
		config.name = "m60";
		config.manufacturer = EnumGunManufacturer.SACO;
		config.comment.add("\"Get some!\"");
		config.comment.add(" ~ Stuart Brown (aka Ahoy)");
		config.config.addAll(HbmCollection.threeZeroEight);
		
		config.advLore.add("The M60, officially the Machine Gun, Caliber 7.62 mm, M60, is a family of American");
		config.advLore.add("general-purpose machine guns firing 7.62×51mm NATO cartridges from a disintegrating belt of M13 links.");
		config.advLore.add("There are several types of ammunition approved for use in the M60, including ball, tracer, and");
		config.advLore.add("armor-piercing rounds.");
		config.advLore.add("It was adopted in 1957 and issued to units beginning in 1959. It has served with every branch of the");
		config.advLore.add("U.S. military and still serves with the armed forces of other states. Its manufacture and continued");
		config.advLore.add("upgrade for military and commercial purchase continues into the 21st century, although it has been");
		config.advLore.add("replaced or supplemented in most roles by other designs, most notably the M240 machine gun in U.S.");
		config.advLore.add("service.");
		
		config.advFuncLore.add("The M60 is a belt-fed machine gun that fires the 7.62×51mm NATO cartridge (similar to .308");
		config.advFuncLore.add("Winchester), which is commonly used in larger rifles, such as the M14. It is generally used as a crew-served");
		config.advFuncLore.add("weapon and operated by a team of two or three individuals. The team consists of the gunner, the assistant");
		config.advFuncLore.add("gunner (AG), and the ammunition bearer. The gun's weight and the amount of ammunition it can consume when fired");
		config.advFuncLore.add("make it difficult for a single soldier to carry and operate. The gunner carries the weapon and, depending on his");
		config.advFuncLore.add("strength and stamina, anywhere from 200 to 1,000 rounds of ammunition. The assistant carries a spare barrel and");
		config.advFuncLore.add("extra ammunition, and reloads and spots targets for the gunner. The ammunition bearer carries additional ammunition");
		config.advFuncLore.add("and the tripod with associated traversing and elevation mechanism, if issued, and fetches more ammunition as needed");
		config.advFuncLore.add("during firing.");
		
		return config;
	}
	static byte i = 0;
	public static BulletConfiguration get762NATOConfig()
	{
		BulletConfiguration bullet = Gun556mmFactory.get556Config();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_308, 1, i++);
		bullet.dmgMax *= 2;
		bullet.dmgMin *= 2;
		bullet.penetration *= 1.5;
		bullet.velocity *= 2.5;
		bullet.maxAge *= 2;
		bullet.spread /= 2;
		
		return bullet;
	}
	
	public static BulletConfiguration get762APConfig()
	{
		BulletConfiguration bullet = get762NATOConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_308, 1, i++);
		bullet.dmgMax *= 1.5;
		bullet.dmgMin *= 1.5;
		bullet.penetration *= 1.5;
		
		return bullet;
	}
	
	public static BulletConfiguration get762DUConfig()
	{
		BulletConfiguration bullet = get762NATOConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_308, 1, i++);
		bullet.dmgMax *= 2;
		bullet.dmgMin *= 2;
		bullet.penetration *= 2;
		
		return bullet;
	}
	
	public static BulletConfiguration get762TracerConfig()
	{
		BulletConfiguration bullet = get762NATOConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_308, 1, i++);
		bullet.vPFX = "reddust";
		
		return bullet;
	}
	
	public static BulletConfiguration get762BlankConfig()
	{
		BulletConfiguration bullet = get762NATOConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_308, 1, i++);
		bullet.dmgMax = 0;
		bullet.dmgMin = 0;
		bullet.penetration = 0;
		bullet.maxAge = 0;
		
		return bullet;
	}
}
