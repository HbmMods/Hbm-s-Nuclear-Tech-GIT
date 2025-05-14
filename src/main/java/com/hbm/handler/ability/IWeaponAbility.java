package com.hbm.handler.ability;

import javax.swing.text.html.parser.Entity;

import com.hbm.items.tool.IItemWithAbility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IWeaponAbility extends IBaseAbility {
    void onHit(int level, World world, EntityPlayer player, Entity victim, IItemWithAbility tool);
}
