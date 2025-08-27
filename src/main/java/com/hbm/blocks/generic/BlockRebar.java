package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.ClientConfig;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemRebarPlacer;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ServerProxy;
import com.hbm.render.block.ISBRHUniversal;
import com.hbm.render.util.RenderBlocksNT;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.RebarNetwork;
import com.hbm.uninos.networkproviders.RebarNetworkProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Compat;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.IFluidReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRebar extends BlockContainer implements ISBRHUniversal {

	@SideOnly(Side.CLIENT) protected IIcon concrete;
	
	public BlockRebar() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.concrete = reg.registerIcon(RefStrings.MODID + ":concrete_liquid");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRebar();
	}

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!(tile instanceof TileEntityRebar)) return;
		
		TileEntityRebar rebar = (TileEntityRebar) tile;
		rebar.hasConnection = false;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity neighbor = Compat.getTileStandard(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if(neighbor instanceof TileEntityPipeBaseNT) {
				rebar.hasConnection = true;
				return;
			}
		}
	}
	
	public static class TileEntityRebar extends TileEntityLoadedBase implements IFluidReceiverMK2, IBufPacketReceiver {

		public Block concrete;
		public int concreteMeta;
		public int progress;
		public int prevProgress;
		protected RebarNode node;
		public boolean hasConnection = false;
		
		public TileEntityRebar setup(Block b, int m) {
			this.concrete = b;
			this.concreteMeta = m;
			return this;
		}

		@Override
		public void updateEntity() {

			long time = worldObj.getTotalWorldTime();

			if(!worldObj.isRemote) {

				if(prevProgress != progress) {
					worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
					prevProgress = progress;
				}
				
				if(this.progress >= 1_000) {
					if(concrete != null && ItemRebarPlacer.isValidConk(Item.getItemFromBlock(concrete), concreteMeta)) {
						worldObj.setBlock(xCoord, yCoord, zCoord, concrete, concreteMeta, 3);
					} else {
						worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.concrete_rebar);
					}
					return;
				}
				
				if(time % 60 == 0) {
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						this.trySubscribe(Fluids.CONCRETE, worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
					}
				}

				if(this.node == null || this.node.expired) {

					this.node = (RebarNode) UniNodespace.getNode(worldObj, xCoord, yCoord, zCoord, RebarNetworkProvider.THE_PROVIDER);

					if(this.node == null || this.node.expired) {
						this.node = this.createNode();
						UniNodespace.createNode(worldObj, this.node);
					}
				}
				
				this.networkPackNT(100);
			}
		}
		
		@Override
		public void invalidate() {
			super.invalidate();

			if(!worldObj.isRemote) {
				if(this.node != null) {
					UniNodespace.destroyNode(worldObj, xCoord, yCoord, zCoord, RebarNetworkProvider.THE_PROVIDER);
				}
			}
		}

		@Override public void serialize(ByteBuf buf) { buf.writeInt(progress); }
		@Override public void deserialize(ByteBuf buf) { this.progress = buf.readInt(); }

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.progress = nbt.getInteger("progress");
			this.hasConnection = nbt.getBoolean("hasConnection");
			
			if(nbt.hasKey("block")) {
				this.concrete = Block.getBlockById(nbt.getInteger("block"));
				this.concreteMeta = nbt.getInteger("meta");
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("progress", this.progress);
			nbt.setBoolean("hasConnection", this.hasConnection);
			
			if(this.concrete != null) {
				nbt.setInteger("block", Block.getIdFromBlock(this.concrete));
				nbt.setInteger("meta", this.concreteMeta);
			}
		}
		
		public RebarNode createNode() {
			TileEntity tile = (TileEntity) this;
			return new RebarNode(RebarNetworkProvider.THE_PROVIDER, new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord)).setConnections(
					new DirPos(tile.xCoord + 1, tile.yCoord, tile.zCoord, Library.POS_X),
					new DirPos(tile.xCoord - 1, tile.yCoord, tile.zCoord, Library.NEG_X),
					new DirPos(tile.xCoord, tile.yCoord + 1, tile.zCoord, Library.POS_Y),
					new DirPos(tile.xCoord, tile.yCoord - 1, tile.zCoord, Library.NEG_Y),
					new DirPos(tile.xCoord, tile.yCoord, tile.zCoord + 1, Library.POS_Z),
					new DirPos(tile.xCoord, tile.yCoord, tile.zCoord - 1, Library.NEG_Z)
					);
		}

		@Override
		public FluidTank[] getAllTanks() {
			FluidTank tank = new FluidTank(Fluids.CONCRETE, 1_000);
			tank.setFill(progress);
			return new FluidTank[] {tank};
		}

		@Override
		public long transferFluid(FluidType type, int pressure, long amount) {
			if(type != Fluids.CONCRETE) return amount;
			if(this.node == null || this.node.expired || !this.node.hasValidNet()) return amount;
			
			List<TileEntityRebar> lowestLinks = new ArrayList();
			int lowestY = 256;
			int progress = 0;
			int capacity = 0;
			
			for(Object o : this.node.net.links) {
				RebarNode node = (RebarNode) o;
				int y = node.positions[0].getY(); //rebar can only have one pos, there's no multiblock rebar
				
				if(y < lowestY) {
					lowestY = y;
					progress = 0;
					capacity = 0;
					lowestLinks.clear();
				}
				
				if(y == lowestY) {
					TileEntity tile = worldObj.getTileEntity(node.positions[0].getX(), y, node.positions[0].getZ());
					if(!(tile instanceof TileEntityRebar)) continue;
					
					TileEntityRebar rebar = (TileEntityRebar) tile;
					
					progress += rebar.progress;
					capacity += 1_000;
					lowestLinks.add(rebar);
				}
			}
			
			if(capacity > 0 && !lowestLinks.isEmpty()) {
				int maxSpeed = 50;
				int maxAccept = (int) BobMathUtil.min(capacity - progress, amount, maxSpeed * lowestLinks.size());
				int target = Math.min((progress + maxAccept) / lowestLinks.size(), 1_000);
				
				for(TileEntityRebar rebar : lowestLinks) {
					if(rebar.progress >= target) continue;
					int delta = target - rebar.progress;
					if(delta > amount) continue;
					
					rebar.progress += delta;
					amount -= delta;
				}
			}
			
			return amount;
		}

		@Override
		public long getDemand(FluidType type, int pressure) {
			return 10_000;
		}
	}
	
	public static class RebarNode extends GenNode<RebarNetwork> {

		public RebarNode(INetworkProvider<RebarNetwork> provider, BlockPos... positions) {
			super(provider, positions);
		}
		
		@Override
		public RebarNode setConnections(DirPos... connections) {
			super.setConnections(connections);
			return this;
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {

		GL11.glPushMatrix();
		RenderBlocks renderer = (RenderBlocks) renderBlocks;
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		double o = 0.25D;
		renderer.setRenderBounds(0.4375D - o, 0D, 0.4375D - o, 0.5625D - o, 1D, 0.5625D - o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D - o, 0D, 0.4375D + o, 0.5625D - o, 1D, 0.5625D + o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D + o, 0D, 0.4375D - o, 0.5625D + o, 1D, 0.5625D - o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D + o, 0D, 0.4375D + o, 0.5625D + o, 1D, 0.5625D + o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);

		renderer.setRenderBounds(0D, 0.4375D - o, 0.4375D - o, 1D, 0.5625D - o, 0.5625D - o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0D, 0.4375D - o, 0.4375D + o, 1D, 0.5625D - o, 0.5625D + o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0D, 0.4375D + o, 0.4375D - o, 1D, 0.5625D + o, 0.5625D - o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0D, 0.4375D + o, 0.4375D + o, 1D, 0.5625D + o, 0.5625D + o); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);

		renderer.setRenderBounds(0.4375D - o, 0.4375D - o, 0D, 0.5625D - o, 0.5625D - o, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D - o, 0.4375D + o, 0D, 0.5625D - o, 0.5625D + o, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D + o, 0.4375D - o, 0D, 0.5625D + o, 0.5625D - o, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		renderer.setRenderBounds(0.4375D + o, 0.4375D + o, 0D, 0.5625D + o, 0.5625D + o, 1D); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {

		RenderBlocksNT renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		double o = 0.25D;
		double min = -0.001;
		double max = 1.001;
		
		if(ClientConfig.RENDER_REBAR_SIMPLE.get()) {
			renderer.setRenderBounds(0.4375D, min, 0.4375D, 0.5625D, max, 0.5625D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(min, 0.4375D, 0.4375D, max, 0.5625D, 0.5625D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D, 0.4375D, min, 0.5625D, 0.5625D, max); renderer.renderStandardBlock(block, x, y, z);
		} else {
			renderer.setRenderBounds(0.4375D - o, min, 0.4375D - o, 0.5625D - o, max, 0.5625D - o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D - o, min, 0.4375D + o, 0.5625D - o, max, 0.5625D + o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D + o, min, 0.4375D - o, 0.5625D + o, max, 0.5625D - o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D + o, min, 0.4375D + o, 0.5625D + o, max, 0.5625D + o); renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(min, 0.4375D - o, 0.4375D - o, max, 0.5625D - o, 0.5625D - o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(min, 0.4375D - o, 0.4375D + o, max, 0.5625D - o, 0.5625D + o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(min, 0.4375D + o, 0.4375D - o, max, 0.5625D + o, 0.5625D - o); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(min, 0.4375D + o, 0.4375D + o, max, 0.5625D + o, 0.5625D + o); renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.4375D - o, 0.4375D - o, min, 0.5625D - o, 0.5625D - o, max); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D - o, 0.4375D + o, min, 0.5625D - o, 0.5625D + o, max); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D + o, 0.4375D - o, min, 0.5625D + o, 0.5625D - o, max); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.4375D + o, 0.4375D + o, min, 0.5625D + o, 0.5625D + o, max); renderer.renderStandardBlock(block, x, y, z);
		}
		
		/*TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntityRebar) {
			TileEntityRebar rebar = (TileEntityRebar) tile;
			if(rebar.progress > 0) {
				double height = rebar.progress / 1000D;
				renderer.setOverrideBlockTexture(concrete);
				renderer.setRenderBounds(0, 0, 0, 1, height, 1); renderer.renderStandardBlock(block, x, y, z);
				renderer.clearOverrideBlockTexture();
			}
		}*/
		
		return true;
	}

	// from ModEventHandlerClient.onRenderWorldLastEvent
	@SideOnly(Side.CLIENT)
	public static void renderRebar(List tiles, float interp) {
		
		List<TileEntityRebar> rebars = new ArrayList();
		for(Object o : tiles) {
			if(!(o instanceof TileEntityRebar)) continue;
			TileEntityRebar rebar = (TileEntityRebar) o;
			if(rebar.progress > 0) rebars.add(rebar);
		}

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		World world = mc.theWorld;

		double dx = player.prevPosX + (player.posX - player.prevPosX) * interp;
		double dy = player.prevPosY + (player.posY - player.prevPosY) * interp;
		double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * interp;
		
		if(!rebars.isEmpty()) {
			
			GL11.glPushMatrix();
			GL11.glShadeModel(GL11.GL_SMOOTH);
			//RenderHelper.enableStandardItemLighting();

			EntityRenderer entityRenderer = mc.entityRenderer;
			entityRenderer.enableLightmap(interp);

			RenderBlocksNT renderer = RenderBlocksNT.INSTANCE.setWorld(world);
			renderer.setOverrideBlockTexture(((BlockRebar) ModBlocks.rebar).concrete);
			mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();

			for(TileEntityRebar rebar : rebars) {
				tess.setTranslation(-dx, -dy, -dz);
				tess.setColorRGBA_F(1F, 1F, 1F, 1F);
				renderer.setRenderBounds(0, 0, 0, 1, rebar.progress / 1000D, 1);
				renderer.renderStandardBlock(ModBlocks.rebar, rebar.xCoord, rebar.yCoord, rebar.zCoord);
			}
			
			tess.draw();
			tess.setTranslation(0, 0, 0);
			renderer.clearOverrideBlockTexture();
			entityRenderer.disableLightmap(interp);

			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glPopMatrix();
		}
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.rebar_placer && player.getHeldItem().hasTagCompound() &&
				player.getHeldItem().stackTagCompound.hasKey("pos") && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {

			int[] pos = player.getHeldItem().stackTagCompound.getIntArray("pos");
			MovingObjectPosition mop = mc.objectMouseOver;
			ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			int iX = mop.blockX + dir.offsetX;
			int iY = mop.blockY + dir.offsetY;
			int iZ = mop.blockZ + dir.offsetZ;

			double minX = Math.min(pos[0], iX) + 0.125;
			double maxX = Math.max(pos[0], iX) + 0.875;
			double minY = Math.min(pos[1], iY) + 0.125;
			double maxY = Math.max(pos[1], iY) + 0.875;
			double minZ = Math.min(pos[2], iZ) + 0.125;
			double maxZ = Math.max(pos[2], iZ) + 0.875;
			
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1F, 1F, 1F);
			
			Tessellator tess = Tessellator.instance;
			tess.setTranslation(-dx, -dy, -dz);
			tess.startDrawing(GL11.GL_LINES);
			tess.setBrightness(240);
			tess.setColorRGBA_F(1F, 1F, 1F, 1F);
			
			// top
			tess.addVertex(minX, maxY, minZ);
			tess.addVertex(minX, maxY, maxZ);
			
			tess.addVertex(minX, maxY, maxZ);
			tess.addVertex(maxX, maxY, maxZ);
			
			tess.addVertex(maxX, maxY, maxZ);
			tess.addVertex(maxX, maxY, minZ);

			tess.addVertex(maxX, maxY, minZ);
			tess.addVertex(minX, maxY, minZ);
			
			// bottom
			tess.addVertex(minX, minY, minZ);
			tess.addVertex(minX, minY, maxZ);
			
			tess.addVertex(minX, minY, maxZ);
			tess.addVertex(maxX, minY, maxZ);
			
			tess.addVertex(maxX, minY, maxZ);
			tess.addVertex(maxX, minY, minZ);

			tess.addVertex(maxX, minY, minZ);
			tess.addVertex(minX, minY, minZ);

			// sides
			tess.addVertex(minX, minY, minZ);
			tess.addVertex(minX, maxY, minZ);

			tess.addVertex(maxX, minY, minZ);
			tess.addVertex(maxX, maxY, minZ);

			tess.addVertex(maxX, minY, maxZ);
			tess.addVertex(maxX, maxY, maxZ);

			tess.addVertex(minX, minY, maxZ);
			tess.addVertex(minX, maxY, maxZ);
			
			tess.draw();
			tess.setTranslation(0, 0, 0);
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			
			int rebarLeft = InventoryUtil.countAStackMatches(player, new ComparableStack(ModBlocks.rebar), true);
			int rebarRequired = (Math.max(pos[0], iX) - Math.min(pos[0], iX) + 1) * (Math.max(pos[1], iY) - Math.min(pos[1], iY) + 1) * (Math.max(pos[2], iZ) - Math.min(pos[2], iZ) + 1);
			MainRegistry.proxy.displayTooltip((rebarRequired > rebarLeft ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + (rebarLeft + " / " + rebarRequired), 1_000, ServerProxy.ID_CABLE);
		}
	}
}
