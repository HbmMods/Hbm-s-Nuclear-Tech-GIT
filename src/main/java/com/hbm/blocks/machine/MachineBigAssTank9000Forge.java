package com.hbm.blocks.machine;

import api.ntm1of90.compat.ProxyForgeAdapter;
import com.hbm.tileentity.machine.storage.TileEntityMachineBAT9000Forge;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * A Big Ass Tank that supports both HBM's Fluids System and Forge's fluid system (for compatibility with mods like Thermal Dynamics)
 *
 * This class extends MachineBigAssTank9000 and overrides the createNewTileEntity method to use TileEntityMachineBAT9000Forge.
 */
public class MachineBigAssTank9000Forge extends MachineBigAssTank9000 {

    public MachineBigAssTank9000Forge(Material mat) {
        super(mat);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if(meta >= 12) return new TileEntityMachineBAT9000Forge();
        if(meta >= 6) return new ProxyForgeAdapter(false, false, true);
        return null;
    }

    public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
        super.addInformation(stack, persistentTag, player, list, ext);
        list.add(EnumChatFormatting.YELLOW + "Compatible with Forge Fluid System");
        list.add(EnumChatFormatting.YELLOW + "(Buildcraft, Thermal Expansion, etc.)");
    }
}
