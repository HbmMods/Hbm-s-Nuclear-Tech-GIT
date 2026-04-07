package com.hbm.items.weapon.grenade;

import java.util.List;
import java.util.Locale;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.IAnimatedItem;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.grenade.ItemGrenadeExtra.EnumGrenadeExtra;
import com.hbm.items.weapon.grenade.ItemGrenadeFilling.EnumGrenadeFilling;
import com.hbm.items.weapon.grenade.ItemGrenadeFuze.EnumGrenadeFuze;
import com.hbm.items.weapon.grenade.ItemGrenadeShell.EnumGrenadeShell;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.NTMSounds;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.HbmAnimationPacket;
import com.hbm.render.anim.AnimationEnums.GunAnimation;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemGrenadeUniversal extends Item implements IEquipReceiver, IAnimatedItem {
	
	/*
	 *  __________
	 * | ________ | ______ SHELL - determines what filling can be used, various bonuses like throw distance and fragmentation, max stack size
	 * ||        ||
	 * ||       __________ FILLING - the bang - high explosive, fragmentation, incendiary, etc
	 * ||________||
	 *  \   /\   /
	 *   \_ || _/
	 *    | || |
	 *    | |_____________ FUZE - what triggers the explosive, timed, impact, or airburst
	 *    | || |
	 *    | || |
	 *    | || |
	 *    | || | _________ EXTRA - optional bonus like additional fuzes, special explosion effects, glue for sticky bombs, et cetera
	 *    | || |
	 *    | || |
	 *    / || \
	 *   |__||__|
	 *     {__}
	 */

	public static final String KEY_SHELL = "shell";
	public static final String KEY_FILLING = "filling";
	public static final String KEY_FUZE = "fuze";
	public static final String KEY_EXTRA = "extra";
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getShell(stack).getStackLimit();
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {
		HbmPlayerProps.getData(player).grenadeDeployment = 0;
		
		if(player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new HbmAnimationPacket(GunAnimation.EQUIP.ordinal(), 0), (EntityPlayerMP) player);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		EnumGrenadeShell shell = getShell(stack);
		if(HbmPlayerProps.getData(player).grenadeDeployment >= shell.getDrawDuration()) {
			if(!world.isRemote) {
				EntityGrenadeUniversal grenade = new EntityGrenadeUniversal(world, player, stack);
				world.spawnEntityInWorld(grenade);
			}
			if(!player.capabilities.isCreativeMode) stack.stackSize--;
			if(stack.stackSize > 0) this.onEquip(player, stack);
		}
		
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {

		if(!(entity instanceof EntityLivingBase)) return;
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;

		if(player != null) {
			boolean wasHeld = ItemGunBaseNT.getIsEquipped(stack);

			if(!wasHeld && isHeld) {
				this.onEquip(player, stack);
			} else if(isHeld) {
				HbmPlayerProps.getData(player).grenadeDeployment++;
				int deployment = HbmPlayerProps.getData(player).grenadeDeployment;
				EnumGrenadeShell shell = this.getShell(stack);
				if(shell == EnumGrenadeShell.FRAG && deployment == 18) {
					world.playSoundAtEntity(player, NTMSounds.GUN_REVOLVER_COCK, 1F, 1F);
				}
				if(shell == EnumGrenadeShell.STICK) {
					if(deployment == 16) world.playSoundAtEntity(player, NTMSounds.GUN_BOLT_OPEN, 1F, 1.25F);
					if(deployment == 25) world.playSoundAtEntity(player, NTMSounds.GUN_BOLT_OPEN, 1F, 1.25F);
				}
				if(shell == EnumGrenadeShell.TECH && deployment == 18) {
					world.playSoundAtEntity(player, NTMSounds.GRENADE_TECH, 1F, 1F);
				}
			}
		}

		ItemGunBaseNT.setIsEquipped(stack, isHeld);
	}
	
	public static EnumGrenadeShell getShell(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeShell.FRAG;
		int i = stack.stackTagCompound.getInteger(KEY_SHELL);
		return EnumUtil.grabEnumSafely(EnumGrenadeShell.class, i);
	}
	
	public static EnumGrenadeFilling getFilling(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeFilling.HE;
		int i = stack.stackTagCompound.getInteger(KEY_FILLING);
		return EnumUtil.grabEnumSafely(EnumGrenadeFilling.class, i);
	}
	
	public static EnumGrenadeFuze getFuze(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound()) return EnumGrenadeFuze.S3;
		int i = stack.stackTagCompound.getInteger(KEY_FUZE);
		return EnumUtil.grabEnumSafely(EnumGrenadeFuze.class, i);
	}
	
	public static EnumGrenadeExtra getExtra(ItemStack stack) {
		if(stack == null || !stack.hasTagCompound() || !stack.stackTagCompound.hasKey(KEY_EXTRA)) return null;
		int i = stack.stackTagCompound.getInteger(KEY_EXTRA);
		return EnumUtil.grabEnumSafely(EnumGrenadeExtra.class, i);
	}
	
	public static ItemStack make(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze) { return make(shell, filling, fuze, null, 1); }
	public static ItemStack make(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze, EnumGrenadeExtra extra) { return make(shell, filling, fuze, extra, 1); }
	
	public static ItemStack make(EnumGrenadeShell shell, EnumGrenadeFilling filling, EnumGrenadeFuze fuze, EnumGrenadeExtra extra, int amount) {
		ItemStack stack = new ItemStack(ModItems.grenade_universal, amount);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger(KEY_SHELL, shell.ordinal());
		stack.stackTagCompound.setInteger(KEY_FILLING, filling.ordinal());
		stack.stackTagCompound.setInteger(KEY_FUZE, fuze.ordinal());
		if(extra != null) stack.stackTagCompound.setInteger(KEY_EXTRA, extra.ordinal());
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(EnumGrenadeShell shell : EnumGrenadeShell.values()) for(EnumGrenadeFilling filling : EnumGrenadeFilling.values()) {
			if(filling.compatibleShells.contains(shell)) for(EnumGrenadeFuze fuze : EnumGrenadeFuze.values()) list.add(make(shell, filling, fuze));
		}
		
		for(EnumGrenadeExtra extra : EnumGrenadeExtra.values()) {
			list.add(make(EnumGrenadeShell.FRAG, EnumGrenadeFilling.HE, EnumGrenadeFuze.IMPACT, extra));
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add("item.grenade_universal.shell." + this.getShell(stack).name().toLowerCase(Locale.US));
		list.add("item.grenade_universal.filling." + this.getFilling(stack).name().toLowerCase(Locale.US));
		list.add("item.grenade_universal.fuze." + this.getFuze(stack).name().toLowerCase(Locale.US));
		EnumGrenadeExtra extra = this.getExtra(stack);
		if(extra != null) list.add("item.grenade_universal.extra." + extra.name().toLowerCase(Locale.US));
	}

	@Override
	public BusAnimation getAnimation(Enum type, ItemStack stack) {
		if(type != GunAnimation.EQUIP) return null;
		EnumGrenadeShell shell = this.getShell(stack);
		
		if(shell == EnumGrenadeShell.FRAG) {
			return new BusAnimation()
					.addBus("BODYMOVE", new BusAnimationSequence().setPos(0, -5, 0).addPos(0, -3, 0, 350).addPos(0, 0, 0, 350, IType.SIN_DOWN))
					.addBus("BODYTURN", new BusAnimationSequence().addPos(0, 0, 45, 350).addPos(0, 0, -15, 350, IType.SIN_DOWN).hold(200).addPos(0, 0, -20, 100, IType.SIN_DOWN).addPos(0, 0, 0, 500, IType.SIN_FULL))
					.addBus("RINGMOVE", new BusAnimationSequence().hold(900).addPos(0, 0, 1, 150).addPos(0, -3, 3, 300))
					.addBus("RINGTURN", new BusAnimationSequence().hold(900).addPos(0, 0, 45, 300))
					.addBus("RENDERRING", new BusAnimationSequence().setPos(1, 1, 1).hold(1350).setPos(0, 0, 0));
		}
		
		if(shell == EnumGrenadeShell.STICK) {
			return new BusAnimation()
					.addBus("BODYMOVE", new BusAnimationSequence().setPos(0, -7, 0).addPos(0, 3, 0, 750, IType.SIN_DOWN).holdUntil(1900).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("BODYTURN", new BusAnimationSequence().setPos(0, 0, 90).addPos(0, 0, -45, 750, IType.SIN_DOWN).holdUntil(1900).addPos(0, 0, 0, 250, IType.SIN_FULL))
					.addBus("CAPMOVE", new BusAnimationSequence().hold(800).addPos(0, -0.25, 0, 200, IType.SIN_FULL).hold(250).addPos(0, -0.5, 0, 200, IType.SIN_FULL).addPos(2, -5, 0, 350, IType.SIN_UP))
					.addBus("CAPTURN", new BusAnimationSequence().hold(800).addPos(0, 360, 0, 200, IType.SIN_FULL).hold(250).addPos(0, 360 * 2, 0, 200, IType.SIN_FULL))
					.addBus("RENDERCAP", new BusAnimationSequence().setPos(1, 1, 1).hold(2100).setPos(0, 0, 0));
		}
		
		if(shell == EnumGrenadeShell.TECH) {
			return new BusAnimation()
					.addBus("BODYMOVE", new BusAnimationSequence().setPos(0, -5, 0).addPos(0, -3, 0, 350).addPos(0, 0, 0, 350, IType.SIN_DOWN))
					.addBus("BODYTURN", new BusAnimationSequence().addPos(0, 0, 45, 350).addPos(0, 0, -15, 350, IType.SIN_DOWN).hold(200).addPos(0, 0, -20, 100, IType.SIN_DOWN).addPos(0, 0, 0, 500, IType.SIN_FULL))
					.addBus("RINGMOVE", new BusAnimationSequence().hold(900).addPos(0, 0, 1, 150).addPos(0, -3, 3, 300))
					.addBus("RINGTURN", new BusAnimationSequence().hold(900).addPos(0, 0, 45, 300))
					.addBus("RENDERRING", new BusAnimationSequence().setPos(1, 1, 1).hold(1350).setPos(0, 0, 0));
		}
		
		return null;
	}

	@Override public boolean shouldPlayerModelAim(ItemStack stack) { return false; }
	@Override public Class getEnum() { return GunAnimation.class; }
}
