package com.hbm.handler.ability;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Functions;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumChatFormatting;

// All abilities available on a given tool
public class AvailableAbilities {
    // Insertion order matters
    private LinkedHashMap<IBaseAbility, Integer> abilities = new LinkedHashMap<IBaseAbility, Integer>();

    public AvailableAbilities() {}

    public AvailableAbilities addAbility(IBaseAbility ability, int level) {
        if (level < 0 || level >= ability.levels()) {
            MainRegistry.logger.warn("Illegal level " + level + " for ability " + ability.getName());
            level = ability.levels() - 1;
        }

        if (abilities.containsKey(ability)) {
            MainRegistry.logger.warn("Ability " + ability.getName() + " already had level " + abilities.get(ability) + ", overwriting with level " + level);
        }

        if (ability.isAllowed()) {
            abilities.put(ability, level);
        }

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

    public Map<IWeaponAbility, Integer> getWeaponAbilities() {
        return abilities.keySet().stream().filter(a -> a instanceof IWeaponAbility).collect(Collectors.toMap(a -> (IWeaponAbility)a, a -> abilities.get(a)));
    }

    public Map<IBaseAbility, Integer> getToolAbilities() {
        return abilities.keySet().stream().filter(a -> a instanceof IToolAreaAbility || a instanceof IToolHarvestAbility).collect(Collectors.toMap(a -> a, a -> abilities.get(a), (x, y) -> y, LinkedHashMap::new));
    }

    public Map<IToolAreaAbility, Integer> getToolAreaAbilities() {
        return abilities.keySet().stream().filter(a -> a instanceof IToolAreaAbility).collect(Collectors.toMap(a -> (IToolAreaAbility)a, a -> abilities.get(a), (x, y) -> y, LinkedHashMap::new));
    }

    public Map<IToolHarvestAbility, Integer> getToolHarvestAbilities() {
        return abilities.keySet().stream().filter(a -> a instanceof IToolHarvestAbility).collect(Collectors.toMap(a -> (IToolHarvestAbility)a, a -> abilities.get(a), (x, y) -> y, LinkedHashMap::new));
    }

    public int size() {
        return abilities.size();
    }

    public boolean isEmpty() {
        return abilities.isEmpty();
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(List list) {
        Map<IBaseAbility, Integer> toolAbilities = getToolAbilities();
        
        if (!toolAbilities.isEmpty()) {
            list.add("Abilities: ");

            toolAbilities.forEach((ability, level) -> {
                String fullName = ability.getFullName(level);
                if (!fullName.isEmpty()) {
                    list.add("  " + EnumChatFormatting.GOLD + fullName);
                }
            });

			list.add("Right click to cycle through abilities!");
			list.add("Sneak-click to turn ability off!");
			list.add("Alt-click to open ability selection GUI!");
        }
        
        Map<IWeaponAbility, Integer> weaponAbilities = getWeaponAbilities();
        
        if (!weaponAbilities.isEmpty()) {
            list.add("Weapon modifiers: ");
            
            weaponAbilities.forEach((ability, level) -> {
                list.add("  " + EnumChatFormatting.RED + ability.getFullName(level));
            });
        }
    }
}
