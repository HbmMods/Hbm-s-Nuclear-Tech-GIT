package com.hbm.handler.guncfg;
import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.util.EnumChatFormatting;

public class GunMiniFELFactory {
    public static GunConfiguration getMiniFELConfig() {

        GunConfiguration config = new GunConfiguration();

        config.roundsPerCycle = 1;
        config.gunMode = GunConfiguration.MODE_NORMAL;
        config.firingMode = GunConfiguration.FIRE_AUTO;
        config.firingDuration = 0;
        config.durability = 2500;
        config.allowsInfinity = false;
        config.crosshair = Crosshair.BOX;
        config.firingSound = "hbm:weapon.zomgShoot";
        config.maxCharge = 1_000_000;
        config.chargeRate = 2500;

        config.name = "Handheld Miniaturised FEL";

        return config;
    }
}