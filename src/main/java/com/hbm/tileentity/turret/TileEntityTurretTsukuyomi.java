package com.hbm.tileentity.turret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.guncfg.GunEnergyFactory;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.I18nUtil;

import cofh.api.energy.EnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTurretTsukuyomi extends TileEntityMachineBase implements IConsumer, IControlReceiver
{
	private int ammoCount = 0;
	private boolean isOn = false;
	private boolean isReady = false;
	public boolean gotTargets = false;
	public byte index = 0;
	private long power = 0;
	public static final long maxPower = 10000000000L;
	private int bulletConf = 0;
	static List<Integer> configs = new ArrayList<Integer>();
	public EntityPlayer currPlayer = null;
	private List<String> targets = new ArrayList<String>(0);
	private List<EntityPlayer> readyTargets = new ArrayList<EntityPlayer>(0);
	private static HashMap<BulletConfiguration, Integer> configMap = new HashMap<>();
	static
	{
		configs.add(BulletConfigSyncingUtil.TWR_RAY);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_LARGE);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_SUPERHEATED);
		configs.add(BulletConfigSyncingUtil.TWR_RAY_COUNTER_RESONANT);
		configMap.put(GunEnergyFactory.getSingConfig(), configs.get(0));
		configMap.put(GunEnergyFactory.getRegSingConfig(), configs.get(1));
		configMap.put(GunEnergyFactory.getSuperheatedSingConfig(), configs.get(2));
		configMap.put(GunEnergyFactory.getCounterResonantSingConfig(), configs.get(3));
	}
	
	public TileEntityTurretTsukuyomi()
	{
		super(2);
	}

	public void updateConfig()
	{
		if (slots[1] != null && ammoCount == 0)
		{
			Item ammo = slots[1].getItem();
//			System.out.println(ammo.getUnlocalizedName());
			if (ammo == ModItems.singularity_micro)
				bulletConf = configs.get(0);
			else if (ammo == ModItems.singularity)
				bulletConf = configs.get(1);
			else if (ammo == ModItems.singularity_counter_resonant)
				bulletConf = configs.get(3);
			else if (ammo == ModItems.singularity_super_heated)
				bulletConf = configs.get(2);
			else
				bulletConf = 0;
		}
	}
	
	public String getAttackResult()
	{
		BulletConfiguration config = bulletConf == 0 ? null : BulletConfigSyncingUtil.pullConfig(bulletConf);
		if (config != null && currPlayer != null)
		{
			float pHealth = currPlayer.getHealth();
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
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			updateConfig();
			currPlayer = gotTargets ? readyTargets.get(index) : null;
			if (index > targets.size())
				index = 0;
//			System.out.println(gotTargets);
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("ammoCount", ammoCount);
		data.setByte("index", index);
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
		super.networkUnpack(nbt);
		power = nbt.getLong("power");
		ammoCount = nbt.getInteger("ammoCount");
		index = nbt.getByte("index");
		if (nbt.hasKey("currPlayer"))
			currPlayer = worldObj.getPlayerEntityByName(nbt.getString("currPlayer"));
		if (nbt.hasKey("bullConfig"))
			bulletConf = nbt.getInteger("bullConfig");
		targets.clear();
		for (int i = 0; i < 16; i++)
		{
			if (!nbt.hasKey("player_" + i))
				break;
			else
				targets.add(nbt.getString("player_" + i));
		}
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		switch (value)
		{
		case 0:
			if (constructTargetList() > 0 && !gotTargets)
			{
				gotTargets = true;
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
			}
			else
			{
				gotTargets = false;
				readyTargets.clear();
			}
			break;
		case 2:
			if (!readyTargets.isEmpty())
			{
				purify();
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:alarm.defconstage", 1.0F, 1.0F);
			}
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
		for (int i = 0; i < 16; i++)
		{
			if (!nbt.hasKey("player_" + i))
				break;
			else
				targets.add(nbt.getString("player_" + i));
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("ammoCount", ammoCount);
		nbt.setLong("power", power);
		if (!targets.isEmpty())
			for (int i = 0; i < targets.size(); i++)
				nbt.setString("player_" + i, targets.get(i));
	}
	
	private void purify()
	{
		BulletConfiguration config = BulletConfigSyncingUtil.pullConfig(bulletConf);
		for (int i = 0; i < readyTargets.size() - 1; i++)
			EntityDamageUtil.attackEntityFromIgnoreIFrame(readyTargets.get(i), ModDamageSource.twr, worldObj.rand.nextInt((int) config.dmgMax) + config.dmgMin);
		ammoCount -= readyTargets.size();
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
		if (targets.size() == 15)
			return false;
		else
			return targets.add(name);
	}
	
	private int constructTargetList()
	{
		if (!targets.isEmpty())
		{
			readyTargets.clear();
			for (int i = 0; i < targets.size() - 1; i++)
			{
				if (isPlayerNameValid(targets.get(i)) && isPlayerValid(worldObj.getPlayerEntityByName(targets.get(i))) != -1)
					readyTargets.add(worldObj.getPlayerEntityByName(targets.get(i)));
				else
				{
					targets.remove(i);
					i -= 2;
				}
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
				MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
				byte[] bytes = sha256.digest(Integer.toHexString((int) (7 * x * 13 * z * health * dim * hash * super.hashCode() * 31 * exp + worldObj.rand.nextInt())).getBytes());
				for (byte b : bytes)
					s += Integer.toString((b & 0xFF) + 256, 16).substring(1);
				
				return s;
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
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
			System.out.println(addPlayer(data.getString("name")));
	}
}
