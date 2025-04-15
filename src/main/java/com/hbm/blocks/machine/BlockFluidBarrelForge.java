package com.hbm.blocks.machine;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.storage.TileEntityBarrelForge;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.hbm.items.machine.IItemFluidIdentifier;

import java.util.List;

public class BlockFluidBarrelForge extends BlockContainer implements ITooltipProvider, IPersistentInfoProvider {

    int capacity;

    public BlockFluidBarrelForge(Material material, int capacity) {
        super(material);
        this.capacity = capacity;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityBarrelForge(capacity);
    }

    // Use the same render ID as BlockFluidBarrel for consistent rendering
    // This ensures that both barrels use the same renderer and avoids NEI rendering issues
    public static int renderID = BlockFluidBarrel.renderID;

    @Override
    public int getRenderType(){
        return renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("hbm:barrel_steel");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;

        } else if(!player.isSneaking()) {
            // Open the GUI - this will use the TileEntityBarrel's GUI since TileEntityBarrelForge extends it
            FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
            return true;

        } else if(player.isSneaking()){
            TileEntityBarrelForge barrel = (TileEntityBarrelForge) world.getTileEntity(x, y, z);

            if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IItemFluidIdentifier) {
                FluidType type = ((IItemFluidIdentifier) player.getHeldItem().getItem()).getType(world, x, y, z, player.getHeldItem());

                barrel.tank.setTankType(type);
                barrel.markDirty();
                player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation(type.getConditionalName())).appendSibling(new ChatComponentText("!")));
            }
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add(EnumChatFormatting.AQUA + "Capacity: " + capacity + "mB");
        list.add(EnumChatFormatting.GREEN + "Can store hot fluids");
        list.add(EnumChatFormatting.GREEN + "Can store highly corrosive fluids");
        list.add(EnumChatFormatting.GREEN + "Can store antimatter");
        list.add(EnumChatFormatting.YELLOW + "Compatible with HBM's Fluids System");
        list.add(EnumChatFormatting.YELLOW + "Compatible with Forge Fluid System");
        list.add(EnumChatFormatting.YELLOW + "(Buildcraft, Thermal Expansion, etc.)");
    }

    @Override
    public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
        if(persistentTag != null) {
            if(persistentTag.hasKey("type")) {
                FluidType type = Fluids.fromID(persistentTag.getInteger("type"));

                if(type != Fluids.NONE) {
                    list.add(EnumChatFormatting.YELLOW + "Contains: " + type.getLocalizedName());
                    list.add(EnumChatFormatting.YELLOW + "Amount: " + persistentTag.getInteger("fill") + "mB");
                }
            }
        }
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool, int x, int y, int z, World world) {
        TileEntity te = world.getTileEntity(x, y, z);

        if(te instanceof TileEntityBarrelForge) {
            TileEntityBarrelForge barrel = (TileEntityBarrelForge) te;

            if(barrel.tank.getTankType().getName() != "NONE") {
                list.add(EnumChatFormatting.GREEN + "Contains: " + barrel.tank.getTankType().getLocalizedName());
                list.add(EnumChatFormatting.GREEN + "Amount: " + barrel.tank.getFill() + " / " + barrel.tank.getMaxFill() + "mB");
            } else {
                list.add(EnumChatFormatting.GREEN + "Empty");
            }
        }
    }
}
