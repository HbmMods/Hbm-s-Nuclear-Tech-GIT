package com.hbm.handler.imc;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface ICompatNHNEI {
    /**First item on the list is the icon for the recipe in the NEI GUI, the rest are displayed on the sidebar
     * as other items that can be used for the same purpose**/
    ItemStack[] getMachinesForRecipe();

    String getRecipeID();

}
