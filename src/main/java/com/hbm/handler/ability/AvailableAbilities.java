package com.hbm.handler.ability;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumChatFormatting;

// All abilities available on a given tool
public class AvailableAbilities {
    private HashMap<IBaseAbility, Integer> abilities = new HashMap<IBaseAbility, Integer>();

    public AvailableAbilities() {}

    public AvailableAbilities addAbility(IBaseAbility ability, int level) {
        if (level < 0 || level >= ability.levels()) {
            throw new IllegalArgumentException("Illegal level " + level + " for ability " + ability.getName());
        }

        abilities.put(ability, level);
        return this;
    }

    public AvailableAbilities addToolAbilities() {
        addAbility(IToolAreaAbility.NONE, 0);
        addAbility(IToolHarvestAbility.NONE, 0);
        return this;
    }

    public AvailableAbilities removeAbility(IBaseAbility ability) {
        abilities.remove(ability);
        return this;
    }

    public boolean supportsAbility(IBaseAbility ability) {
        return abilities.containsKey(ability);
    }

    public int maxLevel(IBaseAbility ability) {
        return abilities.getOrDefault(ability, -1);
    }

    public Map<IBaseAbility, Integer> get() {
        return Collections.unmodifiableMap(abilities);
    }

    public int size() {
        return abilities.size();
    }

    public boolean isEmpty() {
        return abilities.isEmpty();
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(List list) {
        boolean hasToolAbilities = abilities.keySet().stream().anyMatch(a -> a instanceof IToolAreaAbility || a instanceof IToolHarvestAbility);
        
        if (hasToolAbilities) {
            list.add("Abilities: ");

            abilities.forEach((ability, level) -> {
                list.add("  " + EnumChatFormatting.GOLD + ability.getFullName(level));
            });

			list.add("Right click to cycle through abilities!");
			list.add("Sneak-click to turn ability off!");
			list.add("Alt-click to open ability selection GUI!");
        }
        
        boolean hasWeaponModifiers = abilities.keySet().stream().anyMatch(a -> a instanceof IWeaponAbility);
        
        if (hasWeaponModifiers) {
            list.add("Weapon modifiers: ");
            
            abilities.forEach((ability, level) -> {
                list.add("  " + EnumChatFormatting.RED + ability.getFullName(level));
            });
        }
    }
}
