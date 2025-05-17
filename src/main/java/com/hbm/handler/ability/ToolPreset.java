package com.hbm.handler.ability;

public class ToolPreset {
    public IToolAreaAbility areaAbility = IToolAreaAbility.NONE;
    public int areaAbilityLevel = 0;
    public IToolHarvestAbility harvestAbility = IToolHarvestAbility.NONE;
    public int harvestAbilityLevel = 0;

    public ToolPreset() {}

    public ToolPreset(IToolAreaAbility areaAbility, IToolHarvestAbility harvestAbility) {
        this.areaAbility = areaAbility;
        this.harvestAbility = harvestAbility;
    }

    public ToolPreset(IToolAreaAbility areaAbility, int areaAbilityLevel, IToolHarvestAbility harvestAbility, int harvestAbilityLevel) {
        this.areaAbility = areaAbility;
        this.areaAbilityLevel = areaAbilityLevel;
        this.harvestAbility = harvestAbility;
        this.harvestAbilityLevel = harvestAbilityLevel;
    }

    public String getMessage() {
        String areaPart = areaAbility.getFullName(areaAbilityLevel);
        String harvestPart = harvestAbility.getFullName(harvestAbilityLevel);

        if (harvestPart.isEmpty() && areaPart.isEmpty())
            return "[Tool ability deactivated]";
        
        if (harvestPart.isEmpty())
            return "[Enabled " + areaPart + "]";
        
        if (areaPart.isEmpty())
            return "[Enabled " + harvestPart + "]";

        return "[Enabled " + areaPart + " + " + harvestPart + "]";
    }
}
