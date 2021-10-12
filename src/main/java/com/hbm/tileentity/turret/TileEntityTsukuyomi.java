package com.hbm.tileentity.turret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.Level;

import com.hbm.config.MachineConfig;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
@Spaghetti("aaaaaa")
public class TileEntityTsukuyomi extends TileEntityMachineBase implements IConsumer, IControlReceiver
{
	private static int cooldown = 0;
	private int ammoCount = 0;
	public boolean isOn, isReady, gotTargets, hasTachyon;
	public byte tachyonCount = 0;
	public byte index = 0;
	private long power = 0;
	public static final long maxPower = 1000000000000L;
	private int bulletConf = 0;
	static List<Integer> configs = new ArrayList<Integer>();
	public EntityPlayer currPlayer = null;
	private List<String> targets = new ArrayList<String>(0);
	private List<EntityPlayer> readyTargets = new ArrayList<EntityPlayer>(0);
	private static HashMap<Item, Integer> configMap = new HashMap<>();
	static
	{
		configs.add(BulletConfigSyncingUtil.TWR_RAY);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_LARGE);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_SUPERHEATED);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_COUNTER_RESONANT);
		configMap.put(ModItems.singularity_micro, configs.get(0));
		configMap.put(ModItems.singularity, configs.get(1));
		configMap.put(ModItems.singularity_super_heated, configs.get(2));
		configMap.put(ModItems.singularity_counter_resonant, configs.get(3));
	}
	
	public TileEntityTsukuyomi()
	{
		super(3);
		isOn = false;
		isReady = false;
		gotTargets = false;
		hasTachyon = false;
	}

	public void updateConfig()
	{
		if (slots[1] != null && ammoCount == 0)
		{
			Item ammo = slots[1].getItem();
			if (configMap.containsKey(ammo))
				bulletConf = configMap.get(ammo);
			else
				bulletConf = 0;
		}
	}
	
	public String getAttackResult()
	{
		BulletConfiguration config = bulletConf == 0 ? null : BulletConfigSyncingUtil.pullConfig(bulletConf);
		if (config != null && currPlayer != null && isReady)
		{
			if (currPlayer.capabilities.isCreativeMode)
				return I18nUtil.resolveKey("twr.result.fail");
			final float pHealth = currPlayer.getHealth();
			return config.dmgMin > pHealth ? I18nUtil.resolveKey("twr.result.success") :
				config.dmgMax > pHealth ? I18nUtil.resolveKey("twr.result.partial") :
					I18nUtil.resolveKey("twr.result.fail");
		}
		else
			return "N/A";
	}
	
	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			cooldown = AuxSavedData.getDataPair(getWorldObj(), "tsukuyomiCooldown");
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			updateConfig();
			if (index > targets.size())
				index = 0;
			currPlayer = gotTargets ? readyTargets.get(index) : null;
			
			hasTachyon = tachyonCount > 0;
			
			if (ammoCount > 0 && bulletConf == 0)
				ammoCount = 0;
			
			if (cooldown > 0)
			{
				gotTargets = false;
				isReady = false;
				isOn = false;
			}
			
			if (worldObj.rand.nextInt(MachineConfig.twrTurretChance) == 0 && cooldown > 0)
				cooldown--;
			if (cooldown < 0)
				cooldown = 0;
			
			AuxSavedData.setDataPair(getWorldObj(), "tsukuyomiCooldown", cooldown);
//			System.out.println(gotTargets);
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("ammoCount", ammoCount);
		data.setBoolean("gotTargets", gotTargets);
		data.setBoolean("isReady", isReady);
		data.setBoolean("hasTachyon", hasTachyon);
		data.setByte("tachyonCount", tachyonCount);
		data.setBoolean("isOn", isOn);
		data.setByte("index", index);
		data.setInteger("cooldown", cooldown);
		if (currPlayer != null)
			data.setString("currPlayer", currPlayer.getDisplayName());
		if (bulletConf != 0)
			data.setInteger("bullConfig", bulletConf);
		if (!targets.isEmpty())
			for (int i = 0; i < targets.size(); i++)
				data.setString("player_" + i, targets.get(i));
		networkPack(data, 50);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		ammoCount = nbt.getInteger("ammoCount");
		gotTargets = nbt.getBoolean("gotTargets");
		isReady = nbt.getBoolean("isReady");
		hasTachyon = nbt.getBoolean("hasTachyon");
		tachyonCount = nbt.getByte("tachyonCount");
		isOn = nbt.getBoolean("isOn");
		index = nbt.getByte("index");
		cooldown = nbt.getInteger("cooldown");
		if (nbt.hasKey("currPlayer"))
			currPlayer = worldObj.getPlayerEntityByName(nbt.getString("currPlayer"));
		if (nbt.hasKey("bullConfig"))
			bulletConf = nbt.getInteger("bullConfig");
		targets.clear();
		byte i = 0;
		while (nbt.hasKey("player_" + i))
		{
			targets.add(nbt.getString("player_" + i));
			i++;
		}
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		if (cooldown > 0)
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
			return;
		}
		switch (value)
		{
		case 0:
			if (constructTargetList() > 0 && !gotTargets)
			{
				gotTargets = true;
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
				MainRegistry.logger.info("A TWR (Tsukuyomi) platform has entered the first stage");
			}
			else
			{
				gotTargets = false;
				isReady = false;
				if (readyTargets.isEmpty())
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
				readyTargets.clear();
				currPlayer = null;
				MainRegistry.logger.info("A TWR (Tsukuyomi) platform has halted the first stage");
			}
			break;
		case 1:
			if (gotTargets && !isReady && bulletConf != 0)
			{
				isReady = true;
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonYes", 1.0F, 1.0F);
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
				if (ammoCount == 0)
				{
					ammoCount = BulletConfigSyncingUtil.pullConfig(bulletConf).ammoCount;
					decrStackSize(1, 1);
				}
				MainRegistry.logger.info("A TWR (Tsukuyomi) platform has entered the second stage using the configuration: [" + BulletConfigSyncingUtil.pullConfig(bulletConf).ammo.getUnlocalizedName() + "] and is ready to purify");
			}
			else
			{
				if (!gotTargets || bulletConf == 0)
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
				isReady = false;
			}
			break;
		case 2:
			if (!readyTargets.isEmpty() && isReady && gotTargets && bulletConf != 0 && ammoCount > 0 && power == maxPower)
			{
				isOn = true;
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
				currPlayer = null;
				MainRegistry.logger.info("A TWR (Tsukuyomi) platform has commenced purification");
				purify();
			}
			else
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
			break;
		case 3:
			if (meta == 0)
			{
				if (!targets.isEmpty() && index > 0)
					index--;
			}
			else
			{
				if (!targets.isEmpty() && !(index == targets.size() - 1))
					index++;
			}
			if (index > 14)
				index = 14;
			if (index < 0)
				index = 0;
			break;
		case 4:
			boolean flag = false;
			if (targets.isEmpty())
				break;
			if (index == targets.size() - 1 && index != 0)
				flag = true;
			targets.remove(index);
			if (flag)
				index--;
			break;
		default:
			break;
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		ammoCount = nbt.getInteger("ammoCount");
		power = nbt.getLong("power");
		tachyonCount = nbt.getByte("tachyonCount");
		bulletConf = nbt.getInteger("bullConf");
		cooldown = AuxSavedData.getDataPair(getWorldObj(), "tsukuyomiCooldown");
		byte i = 0;
		while (nbt.hasKey("player_" + i))
		{
			targets.add(nbt.getString("player_" + i));
			i++;
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("ammoCount", ammoCount);
		nbt.setLong("power", power);
		nbt.setByte("tachyonCount", tachyonCount);
		nbt.setInteger("bullConf", bulletConf);
		AuxSavedData.setDataPair(getWorldObj(), "tsukuyomiCooldown", cooldown);
		if (!targets.isEmpty())
			for (int i = 0; i < targets.size(); i++)
				nbt.setString("player_" + i, targets.get(i));
	}
	
	private void purify()
	{
		// TODO Finish
		BulletConfiguration config = BulletConfigSyncingUtil.pullConfig(bulletConf);
		for (int i = 0; i < readyTargets.size(); i++)
			EntityDamageUtil.attackEntityFromIgnoreIFrame(readyTargets.get(i), ModDamageSource.causeTWRDamage(readyTargets.get(i)), worldObj.rand.nextInt((int) config.dmgMax) + config.dmgMin);
		gotTargets = false;
		isOn = false;
		isReady = false;
		power = 0;
		cooldown = MachineConfig.twrTurretCooldown;
		ammoCount -= readyTargets.size();
		if (ammoCount == 0)
			bulletConf = 0;
		MainRegistry.logger.info("A TWR (Tsukuyomi) platform has completed purification, global cooldown is now: " + cooldown);
	}

	private boolean isPlayerNameValid(String name)
	{
		return worldObj.getPlayerEntityByName(name) != null ? true : false;
	}
	/**
	 * Check if a player is valid
	 * @param playerIn - The player object in question
	 * @return -1 if null; 0 if valid; 1 if outside current dimension
	 */
	private byte isPlayerValid(EntityPlayer playerIn)
	{
		return (byte) (playerIn == null ? -1 : (playerIn.dimension == 0 ? 0 : 1));
	}
	/**
	 * Add a new player to the targeting list
	 * @param name - The player's name
	 * @return If the action was successful
	 */
	private boolean addPlayer(String name)
	{
		if (targets.size() == 15 || cooldown > 0)
			return false;
		else
			return targets.add(name);
	}
	
	private int constructTargetList()
	{
		if (!targets.isEmpty())
		{
			readyTargets.clear();
			index = 0;
			for (String t : new ArrayList<>(targets))
			{
				if (isPlayerNameValid(t) && isPlayerValid(worldObj.getPlayerEntityByName(t)) == worldObj.provider.dimensionId)
					readyTargets.add(worldObj.getPlayerEntityByName(t));
				else
					targets.remove(t);
			}
		}
		return readyTargets.size();
	}
	
	@Override
	public long getPower()
	{
		return power;
	}
	
	@Override
	public String getName()
	{
		return "container.turretTWR";
	}

	@Override
	public void setPower(long i)
	{
		power = i;
	}

	@Override
	public long getMaxPower()
	{
		return maxPower;
	}
	
	public int getAmmoCount()
	{
		return ammoCount;
	}

	private class PlayerStats
	{
		private double x;
		private double y;
		private double z;
		private float health;
		private float maxHealth;
		private byte dim;
		private UUID id;
		private int hash;
		private float exp;
		public PlayerStats(EntityPlayer playerIn)
		{
			x = playerIn.posX;
			y = playerIn.posY;
			z = playerIn.posZ;
			health = playerIn.getHealth();
			maxHealth = playerIn.getMaxHealth();
			dim = (byte) playerIn.dimension;
			id = playerIn.getPersistentID();
			hash = playerIn.hashCode();
			exp = playerIn.experience;
		}
		
		public String hashed()
		{
			try
			{
				String s = "";
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] bytes = md5.digest(Integer.toHexString((int) (7 * x * 13 * z * health * dim * hash * super.hashCode() - y * 31 * exp + worldObj.rand.nextInt()) - (int) maxHealth).getBytes());
				for (byte b : bytes)
					s += Integer.toString((b & 0xFF) + 256, 16).substring(1);
				
				return s;
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				MainRegistry.logger.catching(Level.ERROR, e);
			}
			return "N/A";
		}
	}

	public String getPlayerHash()
	{
		return new PlayerStats(currPlayer).hashed();
	}
	
	public String getPlayerHash(EntityPlayer p)
	{
		return new PlayerStats(p).hashed();
	}
	
	public List<String> getTargets()
	{
		return targets;
	}
	
	public static int getCooldown()
	{
		return cooldown;
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player)
	{
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
//		System.out.println("Got commands");
		if (data.hasKey("name") && targets.size() <= 15 && !targets.contains(data.getString("name")) && !gotTargets)
		{
			if (addPlayer(data.getString("name")))
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonYes", 1.0F, 1.0F);
			else
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:gui.buttonNo", 1.0F, 1.0F);
		}
	}
}
