package com.hbm.extprop;

import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.armor.ItemModShield;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmPlayerProps implements IExtendedEntityProperties {

	public static final String key = "NTM_EXT_PLAYER";
	public EntityPlayer player;

	public boolean hasReceivedBook = false;

	public boolean enableHUD = true;
	public boolean enableBackpack = true;
	public boolean enableMagnet = true;

	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];

	public boolean dashActivated = true;

	public static final int dashCooldownLength = 5;
	public int dashCooldown = 0;

	public int totalDashCount = 0;
	public int stamina = 0;

	public static final int plinkCooldownLength = 10;
	public int plinkCooldown = 0;

	public float shield = 0;
	public float maxShield = 0;
	public int lastDamage = 0;
	public static final float shieldCap = 100;

	public int reputation;

	public boolean isOnLadder = false;

	public HbmPlayerProps(EntityPlayer player) {
		this.player = player;
	}

	public static HbmPlayerProps registerData(EntityPlayer player) {
		player.registerExtendedProperties(key, new HbmPlayerProps(player));
		return (HbmPlayerProps) player.getExtendedProperties(key);
	}

	public static HbmPlayerProps getData(EntityPlayer player) {
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(key);
		return props != null ? props : registerData(player);
	}

	public boolean getKeyPressed(EnumKeybind key) {
		return keysPressed[key.ordinal()];
	}

	public boolean isJetpackActive() {
		return this.enableBackpack && getKeyPressed(EnumKeybind.JETPACK);
	}

	public boolean isMagnetActive(){
		return this.enableMagnet;
	}

	public void setKeyPressed(EnumKeybind key, boolean pressed) {

		if(!getKeyPressed(key) && pressed) {

			if(key == EnumKeybind.TOGGLE_JETPACK) {

				if(!player.worldObj.isRemote) {
					this.enableBackpack = !this.enableBackpack;

					if(this.enableBackpack)
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "Jetpack ON", MainRegistry.proxy.ID_JETPACK);
					else
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Jetpack OFF", MainRegistry.proxy.ID_JETPACK);
				}
			}
			if (key == EnumKeybind.TOGGLE_MAGNET){
				if (!player.worldObj.isRemote){
					this.enableMagnet = !this.enableMagnet;

					if(this.enableMagnet)
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "Magnet ON", MainRegistry.proxy.ID_MAGNET);
					else
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Magnet OFF", MainRegistry.proxy.ID_MAGNET);
				}
			}
			if(key == EnumKeybind.TOGGLE_HEAD) {

				if(!player.worldObj.isRemote) {
					this.enableHUD = !this.enableHUD;

					if(this.enableHUD)
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "HUD ON", MainRegistry.proxy.ID_HUD);
					else
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "HUD OFF", MainRegistry.proxy.ID_HUD);
				}
			}

			if(key == EnumKeybind.TRAIN) {

				if(!this.player.worldObj.isRemote) {

					if(player.ridingEntity != null && player.ridingEntity instanceof EntityRailCarBase && player.ridingEntity instanceof IGUIProvider) {
						FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, player.worldObj, player.ridingEntity.getEntityId(), 0, 0);
					}
				}
			}
		}

		keysPressed[key.ordinal()] = pressed;
	}

	public void setDashCooldown(int cooldown) {
		this.dashCooldown = cooldown;
		return;
	}

	public int getDashCooldown() {
		return this.dashCooldown;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
		return;
	}

	public int getStamina() {
		return this.stamina;
	}

	public void setDashCount(int count) {
		this.totalDashCount = count;
		return;
	}

	public int getDashCount() {
		return this.totalDashCount;
	}

	public static void plink(EntityPlayer player, String sound, float volume, float pitch) {
		HbmPlayerProps props = HbmPlayerProps.getData(player);

		if(props.plinkCooldown <= 0) {
			player.worldObj.playSoundAtEntity(player, sound, volume, pitch);
			props.plinkCooldown = props.plinkCooldownLength;
		}
	}

	public float getEffectiveMaxShield() {

		float max = this.maxShield;

		if(player.getCurrentArmor(2) != null) {
			ItemStack[] mods = ArmorModHandler.pryMods(player.getCurrentArmor(2));
			if(mods[ArmorModHandler.kevlar] != null && mods[ArmorModHandler.kevlar].getItem() instanceof ItemModShield) {
				ItemModShield mod = (ItemModShield) mods[ArmorModHandler.kevlar].getItem();
				max += mod.shield;
			}
		}

		return max;
	}

	@Override
	public void init(Entity entity, World world) { }

	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.hasReceivedBook);
		buf.writeFloat(this.shield);
		buf.writeFloat(this.maxShield);
		buf.writeBoolean(this.enableBackpack);
		buf.writeBoolean(this.enableHUD);
		buf.writeInt(this.reputation);
		buf.writeBoolean(this.isOnLadder);
		buf.writeBoolean(this.enableMagnet);
	}

	public void deserialize(ByteBuf buf) {
		if(buf.readableBytes() > 0) {
			this.hasReceivedBook = buf.readBoolean();
			this.shield = buf.readFloat();
			this.maxShield = buf.readFloat();
			this.enableBackpack = buf.readBoolean();
			this.enableHUD = buf.readBoolean();
			this.reputation = buf.readInt();
			this.isOnLadder = buf.readBoolean();
			this.enableMagnet = buf.readBoolean();
		}
	}

	@Deprecated
	@Override
	public void saveNBTData(NBTTagCompound nbt) {

		NBTTagCompound props = new NBTTagCompound();

		props.setBoolean("hasReceivedBook", hasReceivedBook);
		props.setFloat("shield", shield);
		props.setFloat("maxShield", maxShield);
		props.setBoolean("enableBackpack", enableBackpack);
		props.setBoolean("enableMagnet", enableMagnet);
		props.setBoolean("enableHUD", enableHUD);
		props.setInteger("reputation", reputation);
		props.setBoolean("isOnLadder", isOnLadder);

		nbt.setTag("HbmPlayerProps", props);
	}

	@Deprecated
	@Override
	public void loadNBTData(NBTTagCompound nbt) {

		NBTTagCompound props = (NBTTagCompound) nbt.getTag("HbmPlayerProps");

		if(props != null) {
			this.hasReceivedBook = props.getBoolean("hasReceivedBook");
			this.shield = props.getFloat("shield");
			this.maxShield = props.getFloat("maxShield");
			this.enableBackpack = props.getBoolean("enableBackpack");
			this.enableMagnet = props.getBoolean("enableMagnet");
			this.enableHUD = props.getBoolean("enableHUD");
			this.reputation = props.getInteger("reputation");
			this.isOnLadder = props.getBoolean("isOnLadder");
		}
	}
}
