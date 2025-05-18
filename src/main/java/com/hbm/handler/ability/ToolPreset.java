package com.hbm.handler.ability;

import com.hbm.util.ChatBuilder;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

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

    public ChatComponentText getMessage() {
        if (isNone())
            return ChatBuilder.start("[Tool ability deactivated]").color(EnumChatFormatting.GOLD).flush();
        
        String areaPart = areaAbility.getFullName(areaAbilityLevel);
        String harvestPart = harvestAbility.getFullName(harvestAbilityLevel);
        
        ChatBuilder builder = ChatBuilder.start("[Enabled ");

        if (!areaPart.isEmpty())
            builder.next(areaPart);

        if (!areaPart.isEmpty() && !harvestPart.isEmpty())
            builder.next(" + ");

        if (!harvestPart.isEmpty())
            builder.next(harvestPart);
        
        return builder.colorAll(EnumChatFormatting.YELLOW).flush();
    }

    public boolean isNone() {
        return areaAbility == IToolAreaAbility.NONE && harvestAbility == IToolHarvestAbility.NONE;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("area", areaAbility.getName());
        nbt.setInteger("areaLevel", areaAbilityLevel);
        nbt.setString("harvest", harvestAbility.getName());
        nbt.setInteger("harvestLevel", harvestAbilityLevel);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        areaAbility = IToolAreaAbility.getByName(nbt.getString("area"));
        areaAbilityLevel = nbt.getInteger("areaLevel");
        harvestAbility = IToolHarvestAbility.getByName(nbt.getString("harvest"));
        harvestAbilityLevel = nbt.getInteger("harvestLevel");

        areaAbilityLevel = Math.min(areaAbilityLevel, areaAbility.levels() - 1);
        harvestAbilityLevel = Math.min(harvestAbilityLevel, harvestAbility.levels() - 1);
    }

    public void restrictTo(AvailableAbilities availableAbilities) {
        int maxAreaLevel = availableAbilities.maxLevel(areaAbility);

        if (maxAreaLevel == -1) {
            areaAbility = IToolAreaAbility.NONE;
            areaAbilityLevel = 0;
        } else if (areaAbilityLevel > maxAreaLevel) {
            areaAbilityLevel = maxAreaLevel;
        } else if (areaAbilityLevel < 0) {
            areaAbilityLevel = 0;
        }

        if (!areaAbility.allowsHarvest(areaAbilityLevel)) {
            harvestAbility = IToolHarvestAbility.NONE;
            harvestAbilityLevel = 0;
        }

        int maxHarvestLevel = availableAbilities.maxLevel(harvestAbility);

        if (maxHarvestLevel == -1) {
            harvestAbility = IToolHarvestAbility.NONE;
            harvestAbilityLevel = 0;
        } else if (harvestAbilityLevel > maxHarvestLevel) {
            harvestAbilityLevel = maxHarvestLevel;
        } else if (harvestAbilityLevel < 0) {
            harvestAbilityLevel = 0;
        }
    }
}
