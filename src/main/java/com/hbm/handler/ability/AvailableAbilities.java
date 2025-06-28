package com.hbm.handler.ability;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.EnumChatFormatting;

// All abilities available on a given tool
public class AvailableAbilities {
	// Insertion order matters
	private HashMap<IBaseAbility, Integer> abilities = new HashMap<IBaseAbility, Integer>();

	public AvailableAbilities() { }

	public AvailableAbilities addAbility(IBaseAbility ability, int level) {
		if(level < 0 || level >= ability.levels()) {
			MainRegistry.logger.warn("Illegal level " + level + " for ability " + ability.getName());
			level = ability.levels() - 1;
		}

		if(abilities.containsKey(ability)) {
			MainRegistry.logger.warn("Ability " + ability.getName() + " already had level " + abilities.get(ability) + ", overwriting with level " + level);
		}

		if(ability.isAllowed()) {
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
		return abilities.keySet().stream().filter(a -> a instanceof IWeaponAbility).collect(Collectors.toMap(a -> (IWeaponAbility) a, a -> abilities.get(a)));
	}

	public Map<IBaseAbility, Integer> getToolAbilities() {
		return abilities.keySet().stream().filter(a -> a instanceof IToolAreaAbility || a instanceof IToolHarvestAbility).collect(Collectors.toMap(a -> a, a -> abilities.get(a)));
	}

	public Map<IToolAreaAbility, Integer> getToolAreaAbilities() {
		return abilities.keySet().stream().filter(a -> a instanceof IToolAreaAbility).collect(Collectors.toMap(a -> (IToolAreaAbility) a, a -> abilities.get(a)));
	}

	public Map<IToolHarvestAbility, Integer> getToolHarvestAbilities() {
		return abilities.keySet().stream().filter(a -> a instanceof IToolHarvestAbility).collect(Collectors.toMap(a -> (IToolHarvestAbility) a, a -> abilities.get(a)));
	}

	public int size() {
		return abilities.size();
	}

	public boolean isEmpty() {
		return abilities.isEmpty();
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(List list) {
		List<Map.Entry<IBaseAbility, Integer>> toolAbilities = abilities.entrySet().stream()
				.filter(entry -> (entry.getKey() instanceof IToolAreaAbility && entry.getKey() != IToolAreaAbility.NONE)
						|| (entry.getKey() instanceof IToolHarvestAbility && entry.getKey() != IToolHarvestAbility.NONE))
				.sorted(Comparator.comparing(Map.Entry<IBaseAbility, Integer>::getKey).thenComparing(Map.Entry<IBaseAbility, Integer>::getValue)).collect(Collectors.toList());

		if(!toolAbilities.isEmpty()) {
			list.add("Abilities: ");

			toolAbilities.forEach(entry -> {
				IBaseAbility ability = entry.getKey();
				int level = entry.getValue();

				list.add("  " + EnumChatFormatting.GOLD + ability.getFullName(level));
			});

			list.add("Right click to cycle through presets!");
			list.add("Sneak-click to go to first preset!");
			list.add("Alt-click to open customization GUI!");
		}

		List<Map.Entry<IBaseAbility, Integer>> weaponAbilities = abilities.entrySet().stream().filter(entry -> (entry.getKey() instanceof IWeaponAbility && entry.getKey() != IWeaponAbility.NONE))
				.sorted(Comparator.comparing(Map.Entry<IBaseAbility, Integer>::getKey).thenComparing(Map.Entry<IBaseAbility, Integer>::getValue)).collect(Collectors.toList());

		if(!weaponAbilities.isEmpty()) {
			list.add("Weapon modifiers: ");

			weaponAbilities.forEach(entry -> {
				IBaseAbility ability = entry.getKey();
				int level = entry.getValue();

				list.add("  " + EnumChatFormatting.RED + ability.getFullName(level));
			});
		}
	}
}
