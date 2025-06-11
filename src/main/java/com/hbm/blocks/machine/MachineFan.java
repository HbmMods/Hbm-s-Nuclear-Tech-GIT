package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.ChatBuilder;

import api.hbm.block.IBlowable;
import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineFan extends BlockContainer implements IToolable, ITooltipProvider {

	public MachineFan() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFan();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		int meta = world.getBlockMetadata(x, y, z);

		if(side == ForgeDirection.UP || side == ForgeDirection.DOWN) return meta != 0 && meta != 1;
		if(side == ForgeDirection.NORTH || side == ForgeDirection.SOUTH) return meta != 2 && meta != 3;
		if(side == ForgeDirection.EAST || side == ForgeDirection.WEST) return meta != 4 && meta != 5;
		
		return false;
	}
	
	public static class TileEntityFan extends TileEntityLoadedBase {

		public float spin;
		public float prevSpin;
		public boolean falloff = true;

		@Override
		public void updateEntity() {
			
			this.prevSpin = this.spin;
			
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				
				int range = 10;
				int effRange = 0;
				double push = 0.1;
				
				for(int i = 1; i <= range; i++) {
					Block block = worldObj.getBlock(xCoord + dir.offsetX * i, yCoord + dir.offsetY * i, zCoord + dir.offsetZ * i);
					boolean blowable = block instanceof IBlowable;
					
					if(block.isNormalCube() || blowable) {
						if(!worldObj.isRemote && blowable)
							((IBlowable) block).applyFan(worldObj, xCoord + dir.offsetX * i, yCoord + dir.offsetY * i, zCoord + dir.offsetZ * i, dir, i);
						
						break;
					}
					
					effRange = i;
				}

				int x = dir.offsetX * effRange;
				int y = dir.offsetY * effRange;
				int z = dir.offsetZ * effRange;
				
				List<Entity> affected = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5 + Math.min(x, 0), yCoord + 0.5 + Math.min(y, 0), zCoord + 0.5 + Math.min(z, 0), xCoord + 0.5 + Math.max(x, 0), yCoord + 0.5 + Math.max(y, 0), zCoord + 0.5 + Math.max(z, 0)).expand(0.5, 0.5, 0.5));
				
				for(Entity e : affected) {

					double coeff = push;

					if(falloff) {
						double dist = e.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
						coeff *= 1.5 * (1 - dist / range / 2);
					}
					
					e.motionX += dir.offsetX * coeff;
					e.motionY += dir.offsetY * coeff;
					e.motionZ += dir.offsetZ * coeff;
				}
				
				if(worldObj.isRemote && worldObj.rand.nextInt(30) == 0) {
					double speed = 0.2;
					worldObj.spawnParticle("cloud", xCoord + 0.5 + dir.offsetX * 0.5, yCoord + 0.5 + dir.offsetY * 0.5, zCoord + 0.5 + dir.offsetZ * 0.5, dir.offsetX * speed, dir.offsetY * speed, dir.offsetZ * speed);
				}
				
				this.spin += 30;
			}
			
			if(this.spin >= 360) {
				this.prevSpin -= 360;
				this.spin -= 360;
			}

			if(!worldObj.isRemote) {
				networkPackNT(150);
			}
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.falloff = nbt.getBoolean("falloff");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setBoolean("falloff", falloff);
		}

		@Override
		public void serialize(ByteBuf buf) {
			buf.writeBoolean(falloff);
		}

		@Override
		public void deserialize(ByteBuf buf) {
			falloff = buf.readBoolean();
		}
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool == ToolType.SCREWDRIVER) {
			int meta = world.getBlockMetadata(x, y, z);

			if(meta == 0) world.setBlockMetadataWithNotify(x, y, z, 1, 3);
			if(meta == 1) world.setBlockMetadataWithNotify(x, y, z, 0, 3);
			if(meta == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 3);
			if(meta == 3) world.setBlockMetadataWithNotify(x, y, z, 2, 3);
			if(meta == 4) world.setBlockMetadataWithNotify(x, y, z, 5, 3);
			if(meta == 5) world.setBlockMetadataWithNotify(x, y, z, 4, 3);
			
			return true;
		}

		if(tool == ToolType.HAND_DRILL) {
			TileEntityFan tile = (TileEntityFan) world.getTileEntity(x, y, z);

			if(tile != null) {
				tile.falloff = !tile.falloff;
				tile.markDirty();

				if(!world.isRemote) {
					PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation(this.getUnlocalizedName() + (tile.falloff ? ".falloffOn" : ".falloffOff")).color(EnumChatFormatting.GOLD).flush(), MainRegistry.proxy.ID_FAN_MODE), (EntityPlayerMP) player);

					world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.click", 0.5F, 0.5F);
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}
