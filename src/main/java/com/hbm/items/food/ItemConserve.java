package com.hbm.items.food;

import java.util.List;
import java.util.Locale;

import com.hbm.entity.effect.EntityVortex;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

//tfw no multiple inheritance
public class ItemConserve extends ItemEnumMulti {
	
	public ItemConserve() {
		super(EnumFoodType.class, true, true);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		EnumFoodType num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		
		stack.stackSize--;
		player.getFoodStats().addStats(num.foodLevel, num.saturation);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		this.onFoodEaten(stack, world, player);
		return stack;
	}
	
	//the fancy enum lambdas and method references and whatever can come later if necessary
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		player.inventory.addItemStackToInventory(new ItemStack(ModItems.can_key));
		EnumFoodType num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		
		if(num == EnumFoodType.BHOLE && !world.isRemote) {
			EntityVortex vortex = new EntityVortex(world, 0.5F);
    		vortex.posX = player.posX;
    		vortex.posY = player.posY;
    		vortex.posZ = player.posZ;
    		world.spawnEntityInWorld(vortex);
    		
		} else if(num == EnumFoodType.RECURSION && world.rand.nextInt(10) > 0) {
			
			player.inventory.addItemStackToInventory(stackFromEnum(EnumFoodType.RECURSION));
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.eat;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.canEat(false))
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		String unloc = this.getUnlocalizedName(itemstack) + ".desc";
		String loc = I18nUtil.resolveKey(unloc);
		
		if(!unloc.equals(loc)) {
			String[] locs = loc.split("\\$");
			
			for(String s : locs) {
				list.add(s);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];
		
		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase(Locale.US));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		return "item.canned_" + num.name().toLowerCase(Locale.US);
	}
	
	public static enum EnumFoodType {
		BEEF(8, 5F),
		TUNA(4, 5F),
		MYSTERY(6, 5F),
		PASHTET(4, 5F),
		CHEESE(3, 5F),
		JIZZ(15, 5F), // :3
		MILK(5, 5F),
		ASS(6, 5F), // :3
		PIZZA(8, 5F),
		TUBE(2, 5F),
		TOMATO(4, 5F),
		ASBESTOS(7, 5F),
		BHOLE(10, 5F),
		HOTDOGS(5, 5F),
		LEFTOVERS(1, 5F),
		YOGURT(3, 5F),
		STEW(5, 5F),
		CHINESE(6, 5F),
		OIL(3, 5F),
		FIST(6, 5F),
		SPAM(8, 5F),
		FRIED(10, 5F),
		NAPALM(6, 5F),
		DIESEL(6, 5F),
		KEROSENE(6, 4F),
		RECURSION(1, 5F),
		BARK(2, 5F);
		
		protected int foodLevel;
		protected float saturation;
		
		private EnumFoodType(int level, float sat) {
			this.foodLevel = level;
			this.saturation = sat;
		}
	}
}
