package com.hbm.items.armor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.lib.Library;
import com.hbm.render.model.ModelNo9;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorNo9 extends ArmorModel implements IAttackHandler, IDamageHandler {
	
	protected ModelNo9 model;

	public ArmorNo9(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, armorType);
		this.setMaxDamage(0);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.BLUE + "+0.5 DT");
		list.add(EnumChatFormatting.YELLOW + "Lets you breathe coal, neat!");
	}

	@Override
	public void handleDamage(LivingHurtEvent event, ItemStack stack) {
		
		if(event.source.isUnblockable())
			return;
		
		event.ammount -= 0.5F;
		
		if(event.ammount < 0)
			event.ammount = 0;
	}

	@Override
	public void handleAttack(LivingAttackEvent event, ItemStack armor) {
		
		if(event.source.isUnblockable())
			return;
		
		if(event.ammount <= 0.5F) {
			event.setCanceled(true);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(model == null) {
			model = new ModelNo9(0);
		}
		
		return model;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		
		if(!world.isRemote) {
			if(!armor.hasTagCompound()) {
				armor.stackTagCompound = new NBTTagCompound();
			}
			
			boolean turnOn = HbmPlayerProps.getData(player).enableHUD;
			boolean wasOn = armor.getTagCompound().getBoolean("isOn");

			if(turnOn && !wasOn) world.playSoundAtEntity(player, "fire.ignite", 1F, 1.5F);
			if(!turnOn && wasOn) world.playSoundAtEntity(player, "random.fizz", 0.5F, 2F);
			armor.getTagCompound().setBoolean("isOn", turnOn); // a crude way of syncing the "enableHUD" prop to other players is just by piggybacking off the NBT sync
			
			if(HbmLivingProps.getBlackLung(player) > HbmLivingProps.maxBlacklung * 0.9) {
				HbmLivingProps.setBlackLung(player, (int) (HbmLivingProps.maxBlacklung * 0.9));
			}
			
			if(HbmLivingProps.getBlackLung(player) >= HbmLivingProps.maxBlacklung * 0.25) {
				HbmLivingProps.setBlackLung(player, HbmLivingProps.getBlackLung(player) - 1);
			}
		}
		
		if(world.isRemote && world.getTotalWorldTime() % 2 == 0 && armor.hasTagCompound() && armor.getTagCompound().getBoolean("isOn")) {

			//originally it would have just been a bright aura like a torch, but that's boring
			/*int x = (int) Math.floor(player.posX);
			int y = (int) Math.floor(player.posY + player.eyeHeight);
			int z = (int) Math.floor(player.posZ);*/
			
			checkLights(world, false);
			float range = 50F;
			MovingObjectPosition mop = Library.rayTrace(player, range, 0F, false, true, false);
			
			if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
				Vec3 look = Vec3.createVectorHelper(player.posX - mop.hitVec.xCoord, player.posY + player.getEyeHeight() - mop.hitVec.yCoord, player.posZ - mop.hitVec.zCoord);
				ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
				int level = Math.min(15, (int) (25 - (look.lengthVector() * 25 / range)));
				lightUpRecursively(world, mop.blockX + dir.offsetX, mop.blockY + dir.offsetY, mop.blockZ + dir.offsetZ, level);
				breadcrumb.clear();
			}
		}
	}
	
	public static Set<BlockPos> breadcrumb = new HashSet();
	public static void lightUpRecursively(World world, int x, int y, int z, int light) {
		if(light <= 0) return;
		BlockPos pos = new BlockPos(x, y, z);
		if(breadcrumb.contains(pos)) return;
		breadcrumb.add(pos);
		
		int existingLight = world.getSavedLightValue(EnumSkyBlock.Block, x, y, z);
		int occupancy = world.getBlockLightOpacity(x, y, z);
		
		if(occupancy >= 255) return; // only block if it's fully blocking, light goes through semi-translucent blocks like it's air
		
		int newLight = Math.min(15, Math.max(existingLight, light));
		world.setLightValue(EnumSkyBlock.Block, x, y, z, newLight);
		lightCheck.put(new Pair(world, pos), world.getTotalWorldTime() + 5);

		lightUpRecursively(world, x + 1, y, z, light - 1);
		lightUpRecursively(world, x - 1, y, z, light - 1);
		lightUpRecursively(world, x, y + 1, z, light - 1);
		lightUpRecursively(world, x, y - 1, z, light - 1);
		lightUpRecursively(world, x, y, z + 1, light - 1);
		lightUpRecursively(world, x, y, z - 1, light - 1);
	}
	
	public static HashMap<World, Long> lastChecks = new HashMap();
	public static HashMap<Pair<World, BlockPos>, Long> lightCheck = new HashMap();
	
	public static void checkLights(World world, boolean force) {
		Iterator it = lightCheck.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<Pair<World, BlockPos>, Long> entry = (Entry<Pair<World, BlockPos>, Long>) it.next();
			
			if(entry.getKey().getKey() == world && (world.getTotalWorldTime() > entry.getValue() || force)) {
				BlockPos pos = entry.getKey().getValue();
				world.updateLightByType(EnumSkyBlock.Block, pos.getX(), pos.getY(), pos.getZ());
				it.remove();
			}
		}
		
		lastChecks.put(world, world.getTotalWorldTime());
	}
	
	public static void updateWorldHook(World world) {
		if(world == null || !world.isRemote) return;
		Long last = lastChecks.get(world);
		
		if(last != null && last < world.getTotalWorldTime() + 15) {
			checkLights(world, false);
		}
	}
	
	// lighting data is saved by the server, not the client, so why would we need that??
	/*public static void unloadWorldHook(World world) {
		if(!world.isRemote) return;
		checkLights(world, true);
	}*/
}
