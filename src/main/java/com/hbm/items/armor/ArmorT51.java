package com.hbm.items.armor;

import net.minecraft.item.ItemArmor.ArmorMaterial;

/**
 * Legacy compatibility wrapper for old armor class name.
 */
public class ArmorT51 extends ArmorFSB {
    public ArmorT51(ArmorMaterial material, int slot, String texture, long maxCharge, long drain, long transfer, int armor) {
        super(material, slot, texture);
    }
}

