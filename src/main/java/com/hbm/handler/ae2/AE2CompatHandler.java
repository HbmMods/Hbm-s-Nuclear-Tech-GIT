package com.hbm.handler.ae2;

import appeng.api.AEApi;
import cpw.mods.fml.common.Loader;

public class AE2CompatHandler {
    public static void init() {
        if (Loader.isModLoaded("appliedenergistics2")) {
            AEApi.instance().registries().externalStorage().addExternalStorageInterface(new MSUExternalStorageHandler());
        }
    }
}
