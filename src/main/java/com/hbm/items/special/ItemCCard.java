package com.hbm.items.special;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemCCard extends Item {

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        li.cil.oc.api.Driver.add((li.cil.oc.api.driver.Item) new DriverItemCCard());
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
        list.add("Right-click to link RBMK.");
        if(itemstack.getTagCompound() == null) {
            list.add(EnumChatFormatting.RED + "No RBMK Linked!");
        } else {
            list.add(EnumChatFormatting.YELLOW + "Linked to RBMK at: " + itemstack.stackTagCompound.getInteger("x") + ", " + itemstack.stackTagCompound.getInteger("y") + ", " + itemstack.stackTagCompound.getInteger("z"));
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if(!world.isRemote) {

            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
            }
            Block block = world.getBlock(x, y, z);

            if (block instanceof RBMKBase) {

                RBMKBase rbmk = (RBMKBase) block;

                int[] pos = rbmk.findCore(world, x, y, z);

                if (pos != null) {

                    TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

                    if (te instanceof TileEntityRBMKBase) {
                        stack.stackTagCompound.setInteger("x", pos[0]);
                        stack.stackTagCompound.setInteger("y", pos[1]);
                        stack.stackTagCompound.setInteger("z", pos[2]);
                        player.addChatMessage(new ChatComponentText("Linked!"));
                        world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
                    }
                }
            }
        }
        return true;
    }

}
