package com.hbm.items.special;

import com.hbm.lib.RefStrings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemSlidingBlastDoorSkin extends ItemDoorSkin {
    public ItemSlidingBlastDoorSkin() {
        super(3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon(this.getIconString());

        this.icons[0] = reg.registerIcon(RefStrings.MODID + ":sliding_blast_door_default");
        this.icons[1] = reg.registerIcon(RefStrings.MODID + ":sliding_blast_door_variant1");
        this.icons[2] = reg.registerIcon(RefStrings.MODID + ":sliding_blast_door_variant2");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + stack.getItemDamage();
    }
}
