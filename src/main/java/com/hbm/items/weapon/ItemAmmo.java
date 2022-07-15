package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ItemAmmoEnums.AmmoRocket;
import com.hbm.items.ItemAmmoEnums.IAmmoItemEnum;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemAmmo extends ItemEnumMulti
{
	public enum AmmoItemTrait
	{
		CON_ACCURACY2,
		CON_DAMAGE,
		CON_HEAVY_WEAR,
		CON_LING_FIRE,
		CON_NN,
		CON_NO_DAMAGE,
		CON_NO_EXPLODE1,
		CON_NO_EXPLODE2,
		CON_NO_EXPLODE3,
		CON_NO_FIRE,
		CON_NO_MIRV,
		CON_PENETRATION,
		CON_RADIUS,
		CON_RANGE2,
		CON_SING_PROJECTILE,
		CON_SPEED,
		CON_SUPER_WEAR,
		CON_WEAR,
		NEU_40MM,
		NEU_BLANK,
		NEU_BOAT,
		NEU_BOXCAR,
		NEU_BUILDING,
		NEU_CHLOROPHYTE,
		NEU_ERASER,
		NEU_FUN,
		NEU_HEAVY_METAL,
		NEU_HOMING,
		NEU_JOLT,
		NEU_LESS_BOUNCY,
		NEU_MASKMAN_FLECHETTE,
		NEU_MASKMAN_METEORITE,
		NEU_MORE_BOUNCY,
		NEU_NO_BOUNCE,
		NEU_NO_CON,
		NEU_STARMETAL,
		NEU_TRACER,
		NEU_UHH,
		NEU_WARCRIME1,
		NEU_WARCRIME2,
		PRO_ACCURATE1,
		PRO_ACCURATE2,
		PRO_BALEFIRE,
		PRO_BOMB_COUNT,
		PRO_CAUSTIC,
		PRO_CHAINSAW,
		PRO_CHLORINE,
		PRO_DAMAGE,
		PRO_DAMAGE_SLIGHT,
		PRO_EMP,
		PRO_EXPLOSIVE,
		PRO_FALLOUT,
		PRO_FIT_357,
		PRO_FLAMES,
		PRO_GRAVITY,
		PRO_HEAVY_DAMAGE,
		PRO_INCENDIARY,
		PRO_LUNATIC,
		PRO_MARAUDER,
		PRO_MINING,
		PRO_NO_GRAVITY,
		PRO_NUCLEAR,
		PRO_PENETRATION,
		PRO_PHOSPHORUS,
		PRO_PHOSPHORUS_SPLASH,
		PRO_POISON_GAS,
		PRO_RADIUS,
		PRO_RADIUS_HIGH,
		PRO_RANGE,
		PRO_ROCKET,
		PRO_ROCKET_PROPELLED,
		PRO_SHRAPNEL,
		PRO_SPEED,
		PRO_STUNNING,
		PRO_TOXIC,
		PRO_WEAR,
		PRO_WITHERING;
		public String key = "desc.item.ammo.";
		private AmmoItemTrait()
		{
			key += this.toString().toLowerCase();
		}
	}
	private final String altName;
	public ItemAmmo(Class<? extends Enum<?>> clazz)
	{
		this(clazz, "");
	}
	
	public ItemAmmo(Class<? extends Enum<?>> clazz, String altName)
	{
		super(clazz, true, true);
		setCreativeTab(MainRegistry.weaponTab);
		this.altName = altName;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(stack, player, list, bool);
		if (!altName.isEmpty())
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey(altName));
		if (stack.getItem() == ModItems.ammo_rocket && stack.getItemDamage() == AmmoRocket.DIGAMMA.ordinal())
			list.add(player.worldObj.rand.nextInt(3) < 2 ? EnumChatFormatting.RED + "COVER YOURSELF IN OIL" : EnumChatFormatting.RED + "" + EnumChatFormatting.OBFUSCATED + "COVER YOURSELF IN OIL");
		
		final IAmmoItemEnum item = (IAmmoItemEnum) EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		final Set<AmmoItemTrait> ammoTraits = item.getTraits();
		
		if (bool)
		{
			final BulletConfiguration config = item.getConfig();
			list.add("Damage: " + config.dmgMin + '-' + config.dmgMax);
			list.add("Penetration value: " + config.penetration);
			list.add("Penetration value at 100 meters: ~" + Math.round(config.modFunction.orElse(BulletConfiguration.defaultModifier).mod(config.penetration, config.penetrationModifier, 100)));
		}
		
		if (ammoTraits.size() > 0)
		{
			//List traits = Arrays.asList(traitArray);
			final ArrayList<AmmoItemTrait> sortedTraits = new ArrayList<AmmoItemTrait>(ammoTraits);
			Collections.sort(sortedTraits, Collections.reverseOrder());
			for (AmmoItemTrait trait : sortedTraits)
			{
				final EnumChatFormatting color;
				switch(trait.toString().substring(0, 3))
				{
				case "PRO": color = EnumChatFormatting.BLUE; break;
				case "NEU": color = EnumChatFormatting.YELLOW; break;
				case "CON": color = EnumChatFormatting.RED; break;
				default: color = EnumChatFormatting.DARK_GRAY; break;
				}
				list.add(color + I18nUtil.resolveKey(trait.key));
			}
		}		
	}
	@Override
	public ItemEnumMulti setUnlocalizedName(String uloc)
	{
		setTextureName(RefStrings.MODID + ':' + uloc);
		return (ItemEnumMulti) super.setUnlocalizedName(uloc);
	}
}
