package com.hbm.items.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.model.ModelArmorDNT;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorDNT extends ArmorFSBPowered {

	public ArmorDNT(ArmorMaterial material, int layer, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, layer, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorDNT[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorDNT[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorDNT(i);
		}

		return models[armorSlot];
	}
	
	private static final UUID speed = UUID.fromString("6ab858ba-d712-485c-bae9-e5e765fc555a");

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		super.onArmorTick(world, player, stack);
		
		if(this != ModItems.dns_plate)
			return;

		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		/// SPEED ///
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(speed, "DNT SPEED", 0.25, 0));
		player.getAttributeMap().removeAttributeModifiers(multimap);
		
		if(player.isSprinting()) {
			player.getAttributeMap().applyAttributeModifiers(multimap);
		}

		if(world.isRemote) {

			if(player == MainRegistry.proxy.me()) {

				boolean last = props.getKeyPressed(EnumKeybind.JETPACK);
				boolean current = MainRegistry.proxy.getIsKeyPressed(EnumKeybind.JETPACK);

				if(last != current) {
					PacketDispatcher.wrapper.sendToServer(new KeybindPacket(EnumKeybind.JETPACK, current));
					props.setKeyPressed(EnumKeybind.JETPACK, current);
				}
			}

		} else {
			
			/// JET ///
			if(this.hasFSBArmor(player) && props.getKeyPressed(EnumKeybind.JETPACK) || (!player.onGround && !player.isSneaking())) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack_dns");
				data.setInteger("player", player.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.dimensionId, player.posX, player.posY, player.posZ, 100));
			}
		}

		if(this.hasFSBArmor(player)) {

			if(props.getKeyPressed(EnumKeybind.JETPACK)) {

				if(player.motionY < 0.6D)
					player.motionY += 0.2D;

				player.fallDistance = 0;

				world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.immolatorShoot", 0.125F, 1.5F);

			} else if(!player.isSneaking() && !player.onGround) {
				player.fallDistance = 0;
				
				if(player.motionY < -1)
					player.motionY += 0.4D;
				else if(player.motionY < -0.1)
					player.motionY += 0.2D;
				else if(player.motionY < 0)
					player.motionY = 0;

				player.motionX *= 1.05D;
				player.motionZ *= 1.05D;

				world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:weapon.immolatorShoot", 0.125F, 1.5F);
			}
			
			if(player.isSneaking() && !player.onGround) {
				player.motionY -= 0.1D;
			}
		}
	}
	
	@Override
	public void handleAttack(LivingAttackEvent event) {

		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;

			if(ArmorFSB.hasFSBArmor(player)) {
				
				if(event.source.isExplosion()) {
					return;
				}

				e.worldObj.playSoundAtEntity(e, "random.break", 5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
				event.setCanceled(true);
			}
		}
	}

	@Override
	public void handleHurt(LivingHurtEvent event) {

		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;

			if(ArmorFSB.hasFSBArmor(player)) {
				
				if(event.source.isExplosion()) {
					event.ammount *= 0.001F;
					return;
				}
				
				event.ammount = 0;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add("Charge: " + Library.getShortNumber(getCharge(stack)) + " / " + Library.getShortNumber(maxPower));

		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("armor.fullSetBonus"));

		if(!effects.isEmpty()) {

			for(PotionEffect effect : effects) {
				list.add(EnumChatFormatting.AQUA + "  " + I18n.format(Potion.potionTypes[effect.getPotionID()].getName()));
			}
		}
		
		list.add(EnumChatFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.explosionImmune"));
		list.add(EnumChatFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.cap", 5));
		list.add(EnumChatFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.modifier", 0.001F));
		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.vats"));
		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.thermal"));
		list.add(EnumChatFormatting.RED + "  " + I18nUtil.resolveKey("armor.hardLanding"));
		list.add(EnumChatFormatting.DARK_RED + "  " + I18nUtil.resolveKey("armor.ignoreLimit"));
		list.add(EnumChatFormatting.AQUA + "  " + I18nUtil.resolveKey("armor.rocketBoots"));
		list.add(EnumChatFormatting.AQUA + "  " + I18nUtil.resolveKey("armor.fastFall"));
		list.add(EnumChatFormatting.AQUA + "  " + I18nUtil.resolveKey("armor.sprintBoost"));
	}
}
