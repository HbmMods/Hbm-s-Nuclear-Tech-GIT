package com.hbm.items.tool;

import java.util.List;
import java.util.Locale;

import com.hbm.entity.cart.*;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemModMinecart extends Item {

	private IIcon[] icons;
	private IIcon[] bases = new IIcon[4];
	
	public static final String CART_BASE_NBT = "cartBase";
	
	public static enum EnumCartBase {
		VANILLA,
		WOOD,
		STEEL,
		PAINTED
	}
	
	public static enum EnumMinecart {
		EMPTY		(EnumCartBase.WOOD, EnumCartBase.STEEL, EnumCartBase.PAINTED),
		CRATE		(EnumCartBase.VANILLA),
		DESTROYER	(EnumCartBase.STEEL, EnumCartBase.PAINTED),
		POWDER		(EnumCartBase.WOOD, EnumCartBase.STEEL, EnumCartBase.PAINTED),
		SEMTEX		(EnumCartBase.WOOD, EnumCartBase.STEEL, EnumCartBase.PAINTED);
		
		public int types;
		
		private EnumMinecart(EnumCartBase... types) {
			this.types = 0;
			for(EnumCartBase type : types) {
				this.types |= (1 << type.ordinal());
			}
		}
		
		private EnumMinecart(int types) {
			this.types = types;
		}
		
		public boolean supportsBase(int type) {
			return (this.types & (1 << type)) > 0;
		}
		
		public boolean supportsBase(EnumCartBase type) {
			return supportsBase(type.ordinal());
		}
		
		/*public boolean isVanilla() {
			return isType(0);
		}
		
		public boolean isSteel() {
			return isType(1);
		}
		
		public boolean isWood() {
			return isType(2);
		}
		
		public boolean isPainted() {
			return isType(3);
		}*/
	}

	public ItemModMinecart() {
		this.setMaxStackSize(4);
		this.setCreativeTab(CreativeTabs.tabTransport);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenseBehavior);
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		this.setTextureName(RefStrings.MODID + ":"+ unlocalizedName);
		return this;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		EnumMinecart cart = EnumUtil.grabEnumSafely(EnumMinecart.class, stack.getItemDamage());
		return super.getUnlocalizedName() + "." + cart.name().toLowerCase(Locale.US);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < EnumMinecart.values().length; i++) {
			EnumMinecart cart = EnumMinecart.values()[i];
			
			for(EnumCartBase base : EnumCartBase.values()) {
				if(cart.supportsBase(base)) {
					list.add(createCartItem(base, cart));
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		for(int i = 0; i < EnumCartBase.values().length; i++) {
			EnumCartBase base = EnumCartBase.values()[i];
			bases[i] = reg.registerIcon(this.getIconString() + "." + base.name().toLowerCase(Locale.US));
		}
		
		EnumMinecart[] enums = EnumMinecart.values();
		this.icons = new IIcon[enums.length];

		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_overlay." + num.name().toLowerCase(Locale.US));
		}
	}
	
	public static EnumCartBase getBaseType(ItemStack stack) {
		if(!stack.hasTagCompound())
			return EnumCartBase.VANILLA;
		
		int meta = stack.stackTagCompound.getInteger(CART_BASE_NBT);
		return EnumUtil.grabEnumSafely(EnumCartBase.class, meta);
	}
	
	public static ItemStack createCartItem(EnumCartBase base, EnumMinecart cart) {
		ItemStack stack = new ItemStack(ModItems.cart, 1, cart.ordinal());
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger(CART_BASE_NBT, base.ordinal());
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		
		if(pass == 0) {
			EnumCartBase base = getBaseType(stack);
			return this.bases[base.ordinal()];
		}
		
		return this.icons[stack.getItemDamage()];
	}
	
	private static final IBehaviorDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem() {
		private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

		@Override
		public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			EnumFacing enumfacing = BlockDispenser.func_149937_b(source.getBlockMetadata());
			World world = source.getWorld();
			double x = source.getX() + enumfacing.getFrontOffsetX() * 1.125D;
			double y = source.getY() + enumfacing.getFrontOffsetY() * 1.125D;
			double z = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125D;
			int iX = source.getXInt() + enumfacing.getFrontOffsetX();
			int iY = source.getYInt() + enumfacing.getFrontOffsetY();
			int iZ = source.getZInt() + enumfacing.getFrontOffsetZ();
			Block block = world.getBlock(iX, iY, iZ);
			double yOffset;

			if(BlockRailBase.func_150051_a(block)) {
				yOffset = 0.0D;
			} else {
				if(block.getMaterial() != Material.air || !BlockRailBase.func_150051_a(world.getBlock(iX, iY - 1, iZ))) {
					return this.behaviourDefaultDispenseItem.dispense(source, stack);
				}

				yOffset = -1.0D;
			}

			EntityMinecart entityminecart = createMinecart(world, x, y + yOffset, z, stack);

			if(stack.hasDisplayName()) {
				entityminecart.setMinecartName(stack.getDisplayName());
			}

			world.spawnEntityInWorld(entityminecart);
			stack.splitStack(1);
			return stack;
		}
		
		protected void playDispenseSound(IBlockSource source) {
			source.getWorld().playAuxSFX(1000, source.getXInt(), source.getYInt(), source.getZInt(), 0);
		}
	};

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer entity, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
			if(!world.isRemote) {
				
				EntityMinecart entityminecart = createMinecart(world, x + fx, y + fy, z + fz, stack);

				if(stack.hasDisplayName()) {
					entityminecart.setMinecartName(stack.getDisplayName());
				}

				world.spawnEntityInWorld(entityminecart);
			}

			--stack.stackSize;
			return true;
		} else {
			return false;
		}
	}
	
	public static EntityMinecart createMinecart(World world, double x, double y, double z, ItemStack stack) {
		EnumMinecart type = (EnumMinecart) EnumMinecart.values()[stack.getItemDamage()];
		EnumCartBase base = getBaseType(stack);
		switch(type) {
		case CRATE: return new EntityMinecartCrate(world, x, y, z, base, stack);
		case DESTROYER: return new EntityMinecartDestroyer(world, x, y, z, base);
		case EMPTY: return new EntityMinecartOre(world, x, y, z, base);
		case POWDER: return new EntityMinecartPowder(world, x, y, z, base);
		case SEMTEX: return new EntityMinecartSemtex(world, x, y, z, base);
		default: return new EntityMinecartEmpty(world, x, y, z);
		}
	}
}
