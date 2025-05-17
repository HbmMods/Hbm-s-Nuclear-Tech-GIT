package com.hbm.handler.ability;

import java.util.HashMap;

// All abilities available on a given tool
public class AvailableAbilities {
    private HashMap<IBaseAbility, Integer> abilities = new HashMap<IBaseAbility, Integer>();

    AvailableAbilities() {}

    AvailableAbilities addAbility(IBaseAbility ability, int level) {
        if (level < 0 || level >= ability.levels()) {
            throw new IllegalArgumentException("Illegal level " + level + " for ability " + ability.getName());
        }

        abilities.put(ability, level);
        return this;
    }

    AvailableAbilities removeAbility(IBaseAbility ability) {
        abilities.remove(ability);
        return this;
    }

    boolean supportsAbility(IBaseAbility ability) {
        return abilities.containsKey(ability);
    }

    int maxLevel(IBaseAbility ability) {
        return abilities.getOrDefault(ability, -1);
    }
}
