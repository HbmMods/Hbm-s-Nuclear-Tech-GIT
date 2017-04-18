package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RailHighspeed extends BlockRailBase {

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public RailHighspeed()
    {
        super(true);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_2_ >= 6 ? this.icon : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        super.registerBlockIcons(p_149651_1_);
        this.icon = p_149651_1_.registerIcon(this.getTextureName());
    }
    
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z)
    {
        return 1.0f;
    }
}
