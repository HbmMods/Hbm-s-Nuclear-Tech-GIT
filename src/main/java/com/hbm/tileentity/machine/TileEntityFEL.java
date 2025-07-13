package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineSILEX;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.ContainerFEL;
import com.hbm.inventory.gui.GUIFEL;
import com.hbm.items.machine.ItemFELCrystal;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFEL extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int powerReq = 1250;
	public EnumWavelengths mode = EnumWavelengths.NULL;
	public boolean isOn;
	public boolean missingValidSilex = true	;
	public int distance;
	public List<EntityLivingBase> entities = new ArrayList();
	private int audioDuration = 0;
	private AudioWrapper audio;
	
	
	public TileEntityFEL() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.machineFEL";
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	@Spaghetti ("What the fuck were you thinking")
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.trySubscribe(worldObj, xCoord + dir.offsetX * -5, yCoord + 1, zCoord + dir.offsetZ  * -5, dir.getOpposite());
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(this.isOn && !(this.slots[1] == null)) {
				
				if(this.slots[1].getItem() instanceof ItemFELCrystal) {
					
					ItemFELCrystal crystal = (ItemFELCrystal) this.slots[1].getItem();
					this.mode = crystal.wavelength;
					
				} else { this.mode = EnumWavelengths.NULL; }
				
			} else { this.mode = EnumWavelengths.NULL; }
			
			int range = 24;
			boolean silexSpacing = false;
			
			int req = (int) (powerReq * ((mode.ordinal() == 0) ? 0 : Math.pow(3, mode.ordinal())));
			
			if(this.isOn && this.mode != EnumWavelengths.NULL && power < req) {
				this.power = 0;
			}
			
			if(this.isOn && power >= req && this.mode != EnumWavelengths.NULL) {
				
				int distance = this.distance-1;
				double blx = Math.min(xCoord, xCoord + dir.offsetX * distance) + 0.2;
				double bux = Math.max(xCoord, xCoord + dir.offsetX * distance) + 0.8;
				double bly = Math.min(yCoord, 1 + yCoord + dir.offsetY * distance) + 0.2;
				double buy = Math.max(yCoord, 1 + yCoord + dir.offsetY * distance) + 0.8;
				double blz = Math.min(zCoord, zCoord + dir.offsetZ * distance) + 0.2;
				double buz = Math.max(zCoord, zCoord + dir.offsetZ * distance) + 0.8;
				
				List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
				
				for(EntityLivingBase entity : list) {
					switch(this.mode) {
					case VISIBLE: entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 60 * 60 * 65536, 0));
					case IR:
					case UV: entity.setFire(10); break;
					case GAMMA: ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, 25); break;
					case DRX: ContaminationUtil.applyDigammaData(entity, 0.1F); break;
					}
				}
				
				power -= req;
				for(int i = 3; i < range; i++) {
				
					int x = xCoord + dir.offsetX * i;
					int y = yCoord + 1;
					int z = zCoord + dir.offsetZ * i;
					
					Block b = worldObj.getBlock(x, y, z);
					
					if(!(b.getMaterial().isOpaque()) && b != Blocks.tnt) {
						this.distance = range;
						silexSpacing = false;
						continue;
					}
					
					if(b == ModBlocks.machine_silex) {
					
						TileEntity te = worldObj.getTileEntity(x + dir.offsetX, yCoord, z + dir.offsetZ);
					
						if(te instanceof TileEntitySILEX) {
							TileEntitySILEX silex = (TileEntitySILEX) te;
							int meta = silex.getBlockMetadata() - BlockDummyable.offset;
							if(rotationIsValid(meta, this.getBlockMetadata() - BlockDummyable.offset) && i >= 5 && silexSpacing == false	) {
								if(silex.mode != this.mode) {
									silex.mode = this.mode;
									this.missingValidSilex = false;
									silexSpacing = true;
									continue;
								} 
							} else {
								MachineSILEX silexBlock = (MachineSILEX)silex.getBlockType();
								silexBlock.breakBlock(worldObj, silex.xCoord, silex.yCoord, silex.zCoord, silexBlock, 0);
								worldObj.spawnEntityInWorld(new EntityItem(worldObj, x + 0.5, y + 0.5, z + 0.5, new ItemStack(Item.getItemFromBlock(ModBlocks.machine_silex))));
							} 
						}
						
					} else if(b.getMaterial().isOpaque() || b == Blocks.tnt) {
						
						this.distance = i;
						
						if(b.getMaterial().isLiquid()) {
							worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
							worldObj.setBlockToAir(x, y, z);
							break;
						} 
						
						float hardness = b.getExplosionResistance(null);
						if(hardness < 75 && worldObj.rand.nextInt(5) == 0) {
							worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
							Block block = (this.mode != EnumWavelengths.DRX) ? Blocks.fire : (MainRegistry.polaroidID == 11) ? ModBlocks.digamma_matter : ModBlocks.fire_digamma;
							worldObj.setBlock(x, y, z, block);
							if(this.mode == EnumWavelengths.DRX)
								worldObj.setBlock(x, y-1, z, ModBlocks.ash_digamma);
						}
						break;
					}
				}
			}
			
			this.networkPackNT(250);
		} else {

			if(power > powerReq * Math.pow(2, mode.ordinal()) && isOn && !(mode == EnumWavelengths.NULL) && distance - 3 > 0) {
				audioDuration += 2;
			} else {
				audioDuration -= 3;
			}

			audioDuration = MathHelper.clamp_int(audioDuration, 0, 60);

			if(audioDuration > 10) {

				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.updateVolume(getVolume(2F));
				audio.updatePitch((audioDuration - 10) / 100F + 0.5F);

			} else {

				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	public boolean rotationIsValid(int silexMeta, int felMeta) {
		ForgeDirection silexDir = ForgeDirection.getOrientation(silexMeta);
		ForgeDirection felDir = ForgeDirection.getOrientation(felMeta);
		if(silexDir == felDir || silexDir == felDir.getOpposite()) {
			return true;
		}
		 
		return false;
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		BufferUtil.writeString(buf, mode.toString());
		buf.writeBoolean(isOn);
		buf.writeBoolean(missingValidSilex);
		buf.writeInt(distance);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		mode = EnumWavelengths.valueOf(BufferUtil.readString(buf));
		isOn = buf.readBoolean();
		missingValidSilex = buf.readBoolean();
		distance = buf.readInt();
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 2){
			this.isOn = !this.isOn;
		}
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		mode = EnumWavelengths.valueOf(nbt.getString("mode"));
		isOn = nbt.getBoolean("isOn");
		missingValidSilex = nbt.getBoolean("valid");
		distance = nbt.getInteger("distance");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setString("mode", mode.toString());
		nbt.setBoolean("isOn", isOn);
		nbt.setBoolean("valid", missingValidSilex);
		nbt.setInteger("distance", distance);
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.fel", xCoord, yCoord, zCoord, 2.0F, 10F, 2.0F);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFEL(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFEL(player.inventory, this);
	}
}