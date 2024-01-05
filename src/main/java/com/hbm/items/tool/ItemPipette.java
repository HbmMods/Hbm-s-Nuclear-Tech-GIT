package com.hbm.items.tool;

import api.hbm.fluid.IFillableItem;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemPipette extends Item implements IFillableItem {

    public ItemPipette() {
        this.setMaxDamage(0);
    }

    @SideOnly(Side.CLIENT) protected IIcon overlayIcon;

    public int amount = this.getMaxFill();

    public FluidType type = Fluids.NONE;

    public int getMaxFill() {
        if(this == ModItems.pipette_laboratory)
            return 50;
        else
            return 1_000;
    }

    public void initNBT(ItemStack stack) {

        stack.stackTagCompound = new NBTTagCompound();

        this.setFill(stack, type, 0);
    }

    public void setFill(ItemStack stack, FluidType type, int fill) {
        if(!stack.hasTagCompound()) {
            initNBT(stack);
        }

        this.type = type;
        stack.stackTagCompound.setInteger(type.getName(), fill);
    }

    public int getFill(ItemStack stack, FluidType type) {
        if(!stack.hasTagCompound()) {
            initNBT(stack);
        }

        return stack.stackTagCompound.getInteger(type.getName());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        if(!stack.hasTagCompound()) {
            initNBT(stack);
        }

        if(!world.isRemote) {
            if (this.getFill(stack, type) == 0) {

                if(this != ModItems.pipette_laboratory)
                    this.amount = player.isSneaking() ? Math.min(this.amount + 50, 1_000) : Math.max(this.amount - 50, 50);
                else
                    this.amount = player.isSneaking() ? Math.min(this.amount + 1, 50) : Math.max(this.amount - 1, 1);


                player.addChatMessage(new ChatComponentText(this.amount + "/" + this.getMaxFill() + "mB"));
            } else {
                player.addChatMessage(new ChatComponentText("Pipette not empty!"));
            }
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if(this == ModItems.pipette_laboratory)
            list.add("Now with 50x more precision!");
        list.add("Fluid: " + type.getLocalizedName());
        list.add("Amount: " + this.getFill(stack, type) + "/" + amount + "mB (" + this.getMaxFill() + "mB)");
    }

    @Override
    public boolean acceptsFluid(FluidType type, ItemStack stack) {
        if(this == ModItems.pipette_boron || this == ModItems.pipette_laboratory)
            return (type == this.type || this.getFill(stack, type) == 0 && !type.isAntimatter());
        return (type == this.type || this.getFill(stack, type) == 0) && (!type.isCorrosive() && !type.isAntimatter());
    }

    @Override
    public int tryFill(FluidType type, int amount, ItemStack stack) {

        if(!acceptsFluid(type, stack))
            return amount;

        if(this.getFill(stack, type) == 0)
            this.setFill(stack, type, 0);

        int req = this.amount - this.getFill(stack, type);
        int toFill = Math.min(req, amount);

        this.setFill(stack, type, this.getFill(stack, type) + toFill);

        return amount - toFill;
    }

    @Override
    public boolean providesFluid(FluidType type, ItemStack stack) {
        return this.type == type;
    }

    @Override
    public int tryEmpty(FluidType type, int amount, ItemStack stack) {
        if(providesFluid(type, stack)) {
            int toUnload = Math.min(amount, this.getFill(stack, type));
            this.setFill(stack, type,this.getFill(stack, type) - toUnload);
            return toUnload;
        }
        return amount;
    }

    //this took me way too long to figure out

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister icon) {
        super.registerIcons(icon);
        this.overlayIcon = icon.registerIcon("hbm:pipette_overlay");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
        return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if(pass == 0) {
            return 0xffffff;
        } else {
            int j = Fluids.fromID(stack.getItemDamage()).getColor();

            if(j < 0) {
                j = 0xffffff;
            }

            return j;
        }
    }

}
