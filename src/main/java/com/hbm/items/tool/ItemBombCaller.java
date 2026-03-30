package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.lib.Library;
import com.hbm.main.NTMSounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.hbm.world.WorldUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBombCaller extends Item {

	public ItemBombCaller() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Aim & click to call an airstrike!");

		switch (stack.getItemDamage()) {
			case 0: list.add("Type: Carpet bombing"); break;
			case 1: list.add("Type: Napalm"); break;
			case 2: list.add("Type: Poison gas"); break;
			case 3: list.add("Type: Agent orange"); break;
			case 4: list.add("Type: Atomic bomb"); break;
			case 5: list.add("Type: VT stinger rockets"); break;
			case 6: list.add("Type: PIP OH GOD"); break;
			case 7: list.add("Type: Cloud the cloud oh god the cloud"); break;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;

		if(!world.isRemote) {
			EntityBomber bomber;
			switch(stack.getItemDamage()) {

				case 1: bomber = EntityBomber.statFacNapalm(world, x, y, z); break;
				case 2: bomber = EntityBomber.statFacChlorine(world, x, y, z); break;
				case 3: bomber = EntityBomber.statFacOrange(world, x, y, z); break;
				case 4: bomber = EntityBomber.statFacABomb(world, x, y, z); break;
				case 5: bomber = EntityBomber.statFacStinger(world, x, y, z); break;
				case 6: bomber = EntityBomber.statFacBoxcar(world, x, y, z); break;
				case 7: bomber = EntityBomber.statFacPC(world, x, y, z); break;
				default: bomber = EntityBomber.statFacCarpet(world, x, y, z);

			}
			WorldUtil.loadAndSpawnEntityInWorld(bomber);
			player.addChatMessage(new ChatComponentText("Called in airstrike!"));
			world.playSoundAtEntity(player, NTMSounds.TECH_BLEEP, 1.0F, 1.0F);

		}

		stack.stackSize -= 1;

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.getItemDamage() >= 4;
	}
}
