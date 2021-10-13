package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.hbm.handler.indexing.AmmoIndex;
import com.hbm.handler.indexing.AmmoIndex.AmmoTrait;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.items.special.ItemStarterKit.KitType;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import scala.actors.threadpool.Arrays;

public class ItemAmmo extends ItemCustomLore
{
	private AmmoItemTrait[] traitArray = new AmmoItemTrait[] {};
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
	
	public ItemAmmo addTraits(AmmoItemTrait... traitsIn)
	{
		traitArray = traitsIn;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(stack, player, list, bool);
		if (traitArray != null || traitArray.length > 0)
		{
			//List traits = Arrays.asList(traitArray);
			for (AmmoItemTrait trait : traitArray)
			{
				EnumChatFormatting color;
				switch(trait.toString().substring(0, 3))
				{
				case "PRO":
					color = EnumChatFormatting.BLUE;
					break;
				case "NEU":
					color = EnumChatFormatting.YELLOW;
					break;
				case "CON":
					color = EnumChatFormatting.RED;
					break;
				default:
					color = EnumChatFormatting.DARK_GRAY;
					break;
				}
				list.add(color + I18nUtil.resolveKey(trait.key));
			}
		}		
	}

}
