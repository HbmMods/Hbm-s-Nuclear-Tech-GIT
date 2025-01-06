package com.hbm.handler.radiation;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.hbm.blocks.IRadResistantBlock;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * @author Drillgon200, hit with a hammer until it works in 1.7.10 by Bob
 *
 */
public class ChunkRadiationHandlerNT extends ChunkRadiationHandler {
	
	/* once i have to start debugging this, even my nightmares will start shitting themselves */
	
	private static HashMap<World, WorldRadiationData> worldMap = new HashMap();

	@Override
	public void clearSystem(World world) {
		WorldRadiationData radWorld = worldMap.get(world);
		
		if(radWorld != null) {
			radWorld.data.clear();
			radWorld.activePockets.clear();
			radWorld.dirtyChunks.clear();
			radWorld.dirtyChunks2.clear();
		}
	}

	@Override
	public void incrementRad(World world, int x, int y, int z, float rad) {
		if(!world.blockExists(x, y, z)) {
			return;
		}
		
		RadPocket p = getPocket(world, x, y, z);
		p.radiation += rad;
		
		if(rad > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.activePockets.add(p);
		}
	}

	@Override
	public void decrementRad(World world, int x, int y, int z, float rad) {
		if(y < 0 || y > 255 || !isSubChunkLoaded(world, x, y, z)) {
			return;
		}
		
		RadPocket p = getPocket(world, x, y, z);
		p.radiation -= Math.max(rad, 0);
		if(p.radiation < 0){
			p.radiation = 0;
		}
	}

	@Override
	public void setRadiation(World world, int x, int y, int z, float rad) {
		RadPocket p = getPocket(world, x, y, z);
		p.radiation = Math.max(rad, 0);
		
		if(rad > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.activePockets.add(p);
		}
	}

	@Override
	public float getRadiation(World world, int x, int y, int z) {
		if(!isSubChunkLoaded(world, x, y, z))
			return 0;
		return getPocket(world, x, y, z).radiation;
	}
	
	public static void jettisonData(World world){
		WorldRadiationData data = getWorldRadData(world);
		data.data.clear();
		data.activePockets.clear();
	}
	
	public static RadPocket getPocket(World world, int x, int y, int z){
		return getSubChunkStorage(world, x, y, z).getPocket(x, y, z);
	}
	
	public static Collection<RadPocket> getActiveCollection(World world){
		return getWorldRadData(world).activePockets;
	}
	
	public static boolean isSubChunkLoaded(World world, int x, int y, int z){
		
		if(y < 0 || y > 255)
			return false;
		
		WorldRadiationData worldRadData = worldMap.get(world);
		if(worldRadData == null){
			return false;
		}
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkCoordIntPair(x >> 4, z >> 4)); // !!!
		if(st == null){
			return false;
		}
		SubChunkRadiationStorage sc = st.getForYLevel(y);
		if(sc == null){
			return false;
		}
		return true;
	}
	
	public static SubChunkRadiationStorage getSubChunkStorage(World world, int x, int y, int z){
		ChunkRadiationStorage st = getChunkStorage(world, x, y, z);
		SubChunkRadiationStorage sc = st.getForYLevel(y);
		//If the sub chunk doesn't exist, bring it into existence by rebuilding the sub chunk, then get it.
		if(sc == null){
			rebuildChunkPockets(world.getChunkFromBlockCoords(x, z), y >> 4);
		}
		sc = st.getForYLevel(y);
		return sc;
	}
	
	public static ChunkRadiationStorage getChunkStorage(World world, int x, int y, int z){
		WorldRadiationData worldRadData = getWorldRadData(world);
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkCoordIntPair(x >> 4, z >> 4));
		//If it doesn't currently exist, create it
		if(st == null){
			st = new ChunkRadiationStorage(worldRadData, world.getChunkFromBlockCoords(x, z));
			worldRadData.data.put(new ChunkCoordIntPair(x >> 4,z >> 4), st);
		}
		return st;
	}
	
	/**
	 * Gets the world radiation data for the world
	 * @param world - the world to get the radiation data from
	 * @return the radiation data for the world
	 */
	private static WorldRadiationData getWorldRadData(World world){
		WorldRadiationData worldRadData = worldMap.get(world);
		//If we don't have one, make a new one
		if(worldRadData == null){
			worldRadData = new WorldRadiationData(world);
			worldMap.put(world, worldRadData);
		}
		return worldRadData;
	}

	@Override
	public void updateSystem() {
		updateRadiation();
	}

	@Override
	public void receiveWorldTick(TickEvent.ServerTickEvent event) {
		rebuildDirty();
	}

	@Override
	public void receiveChunkUnload(ChunkEvent.Unload event) {
		
		if(!event.world.isRemote){
			
			WorldRadiationData data = getWorldRadData(event.world);
			if(data.data.containsKey(event.getChunk().getChunkCoordIntPair())){
				data.data.get(event.getChunk().getChunkCoordIntPair()).unload();
				data.data.remove(event.getChunk().getChunkCoordIntPair());
			}
		}
	}

	@Override
	public void receiveChunkLoad(ChunkDataEvent.Load event) {
		if(!event.world.isRemote){
			if(event.getData().hasKey("hbmRadDataNT")){
				WorldRadiationData data = getWorldRadData(event.world);
				ChunkRadiationStorage cData = new ChunkRadiationStorage(data, event.getChunk());
				cData.readFromNBT(event.getData().getCompoundTag("hbmRadDataNT"));
				data.data.put(event.getChunk().getChunkCoordIntPair(), cData);
			}
		}
	}

	@Override
	public void receiveChunkSave(ChunkDataEvent.Save event) {
		if(!event.world.isRemote){
			WorldRadiationData data = getWorldRadData(event.world);
			if(data.data.containsKey(event.getChunk().getChunkCoordIntPair())){
				NBTTagCompound tag = new NBTTagCompound();
				data.data.get(event.getChunk().getChunkCoordIntPair()).writeToNBT(tag);
				event.getData().setTag("hbmRadDataNT", tag);
			}
		}
	}

	@Override
	public void receiveWorldLoad(WorldEvent.Load event){
		if(!event.world.isRemote){
			worldMap.put(event.world, new WorldRadiationData(event.world));
		}
	}

	@Override
	public void receiveWorldUnload(WorldEvent.Unload event){
		if(!event.world.isRemote){
			worldMap.remove(event.world);
		}
	}

	public static void updateRadiation() {
		long time = System.currentTimeMillis();
		//long lTime = System.nanoTime();
		for(WorldRadiationData w : worldMap.values()){
			//Avoid concurrent modification
			List<RadPocket> itrActive = new ArrayList<>(w.activePockets);
			Iterator<RadPocket> itr = itrActive.iterator();
			while(itr.hasNext()){
				RadPocket p = itr.next();
				BlockPos pos = p.parent.parent.getWorldPos(p.parent.yLevel);
				
				/*PlayerChunkMapEntry entry = ((WorldServer)w.world).getPlayerManager().getEntry(p.parent.parent.chunk.x, p.parent.parent.chunk.z);
				if(entry == null || entry.getWatchingPlayers().isEmpty()){
					//I shouldn't have to do this, but I ran into some issues with chunks not getting unloaded?
					//In any case, marking it for unload myself shouldn't cause any problems
					((WorldServer)w.world).getChunkProvider().queueUnload(p.parent.parent.chunk);
				}*/ // !!!
				
				//Lower the radiation a bit, and mark the parent chunk as dirty so the radiation gets saved
				p.radiation *= 0.999F;
				p.radiation -= 0.05F;
				p.parent.parent.chunk.isModified = true;
				if(p.radiation <= 0) {
					//If there's no more radiation, set it to 0 and remove
					p.radiation = 0;
					p.accumulatedRads = 0;
					itr.remove();
					p.parent.parent.chunk.isModified = true;
					continue;
				}
				
				/*if(p.radiation > RadiationConfig.fogRad && w.world != null && w.world.rand.nextInt(RadiationConfig.fogCh) == 0) {
					//Fog calculation works slightly differently here to account for the 3d nature of the system
					//We just try 10 random coordinates of the sub chunk
					//If the coordinate is inside this pocket and the block at the coordinate is air, 
					//use it to spawn a rad particle at that block and break
					//Also only spawn it if it's close to the ground, otherwise you get a giant fart when nukes go off.
					for(int i = 0; i < 10; i ++){
						BlockPos randPos = new BlockPos(w.world.rand.nextInt(16), w.world.rand.nextInt(16), w.world.rand.nextInt(16));
						if(p.parent.pocketsByBlock == null || p.parent.pocketsByBlock[randPos.getX()*16*16+randPos.getY()*16+randPos.getZ()] == p){
							randPos = randPos.add(p.parent.parent.getWorldPos(p.parent.yLevel));
							IBlockState state = w.world.getBlockState(randPos);
							Vec3d rPos = new Vec3d(randPos.getX()+0.5, randPos.getY()+0.5, randPos.getZ()+0.5);
							RayTraceResult trace = w.world.rayTraceBlocks(rPos, rPos.addVector(0, -6, 0));
							if(state.getBlock().isAir(state, w.world, randPos) && trace != null && trace.typeOfHit == Type.BLOCK){
								PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(randPos.getX()+0.5F, randPos.getY()+0.5F, randPos.getZ()+0.5F, 3), new TargetPoint(w.world.provider.getDimension(), randPos.getX(), randPos.getY(), randPos.getZ(), 100));
								break;
							}
						}
					}
				}*/
				
				//Count the number of connections to other pockets we have
				float count = 0;
				for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
					count += p.connectionIndices[e.ordinal()].size();
				}
				float amountPer = 0.7F/count;
				if(count == 0 || p.radiation < 1){
					//Don't update if we have no connections or our own radiation is less than 1. Prevents micro radiation bleeding.
					amountPer = 0;
				}
				if(p.radiation > 0 && amountPer > 0){
					//Only update other values if this one has radiation to update with
					for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
						//For every direction, get the block pos for the next sub chunk in that direction.
						//If it's not loaded or it's out of bounds, do nothhing
						BlockPos nPos = pos.offset(e, 16);
						if(!p.parent.parent.chunk.worldObj.blockExists(nPos.getX(), nPos.getY(), nPos.getZ()) || nPos.getY() < 0 || nPos.getY() > 255)
							continue;
						if(p.connectionIndices[e.ordinal()].size() == 1 && p.connectionIndices[e.ordinal()].get(0) == -1){
							//If the chunk in this direction isn't loaded, load it
							rebuildChunkPockets(p.parent.parent.chunk.worldObj.getChunkFromBlockCoords(nPos.getX(), nPos.getZ()), nPos.getY() >> 4);
						} else {
							//Else, For every pocket this chunk is connected to in this direction, add radiation to it
							//Also add those pockets to the active pockets set
							SubChunkRadiationStorage sc2 = getSubChunkStorage(p.parent.parent.chunk.worldObj, nPos.getX(), nPos.getY(), nPos.getZ());
							for(int idx : p.connectionIndices[e.ordinal()]){
								//Only accumulated rads get updated so the system doesn't interfere with itself while working
								sc2.pockets[idx].accumulatedRads += p.radiation*amountPer;
								w.activePockets.add(sc2.pockets[idx]);
							}
						}
					}
				}
				if(amountPer != 0){
					p.accumulatedRads += p.radiation * 0.3F;
				}
				//Make sure we only use around 20 ms max per tick, to help reduce lag.
				//The lag should die down by itself after a few minutes when all radioactive chunks get built.
				if(System.currentTimeMillis()-time > 20){
					break;
				}
			}
			//Remove the ones that reached 0 and set the actual radiation values to the accumulated values
			itr = w.activePockets.iterator();
			while(itr.hasNext()){
				RadPocket p = itr.next();
				p.radiation = p.accumulatedRads;
				p.accumulatedRads = 0;
				if(p.radiation <= 0){
					itr.remove();
				}
			}
		}
		//System.out.println(System.nanoTime()-lTime);
		//Should ideally never happen because of the 20 ms limit, 
		//but who knows, maybe it will, and it's nice to have debug output if it does
		if(System.currentTimeMillis()-time > 50){
			System.out.println("Rads took too long: " + (System.currentTimeMillis()-time));
		}
	}
	
	public static void markChunkForRebuild(World world, int x, int y, int z){
		BlockPos chunkPos = new BlockPos(x >> 4, y >> 4, z >> 4);
		WorldRadiationData r = getWorldRadData(world);
		
		if(r.iteratingDirty){
			r.dirtyChunks2.add(chunkPos);
		} else {
			r.dirtyChunks.add(chunkPos);
		}
	}
	
	private static void rebuildDirty(){
		for(WorldRadiationData r : worldMap.values()){
			r.iteratingDirty = true;
			for(BlockPos b : r.dirtyChunks){
				rebuildChunkPockets(r.world.getChunkFromChunkCoords(b.getX(), b.getZ()), b.getY());
			}
			r.iteratingDirty = false;
			r.dirtyChunks.clear();
			r.dirtyChunks.addAll(r.dirtyChunks2);
			r.dirtyChunks2.clear();
		}
	}
	
	private static RadPocket[] pocketsByBlock = null;
	
	private static void rebuildChunkPockets(Chunk chunk, int yIndex){
		
		BlockPos subChunkPos = new BlockPos(chunk.getChunkCoordIntPair().chunkXPos << 4, yIndex << 4, chunk.getChunkCoordIntPair().chunkZPos << 4);
		List<RadPocket> pockets = new ArrayList();
		ExtendedBlockStorage blocks = chunk.getBlockStorageArray()[yIndex];
		if(pocketsByBlock == null) {
			pocketsByBlock = new RadPocket[16*16*16];
		} else {
			Arrays.fill(pocketsByBlock, null);
		}
		ChunkRadiationStorage st = getChunkStorage(chunk.worldObj, subChunkPos.getX(), subChunkPos.getY(), subChunkPos.getZ());
		SubChunkRadiationStorage subChunk = new SubChunkRadiationStorage(st, subChunkPos.getY(), null, null);
		
		if(blocks != null){
			for(int x = 0; x < 16; x ++){
				for(int y = 0; y < 16; y ++){
					for(int z = 0; z < 16; z ++){
						if(pocketsByBlock[x*16*16+y*16+z] != null)
							continue;
						Block block = blocks.getBlockByExtId(x, y, z); // !!!
						if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
							pockets.add(buildPocket(subChunk, chunk.worldObj, new BlockPos(x, y, z), subChunkPos, blocks, pocketsByBlock, pockets.size()));
						}
					}
				}
			}
		} else {
			RadPocket pocket = new RadPocket(subChunk, 0);
			for(int x = 0; x < 16; x ++){
				for(int y = 0; y < 16; y ++){
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, 0, y), pocket, ForgeDirection.DOWN);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, 15, y), pocket, ForgeDirection.UP);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, y, 0), pocket, ForgeDirection.NORTH);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, y, 15), pocket, ForgeDirection.SOUTH);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(0, y, x), pocket, ForgeDirection.WEST);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(15, y, x), pocket, ForgeDirection.EAST);
				}
			}
			pockets.add(pocket);
		}
		subChunk.pocketsByBlock = pockets.size() == 1 ? null : pocketsByBlock;
		if(subChunk.pocketsByBlock != null)
			pocketsByBlock = null;
		subChunk.pockets = pockets.toArray(new RadPocket[pockets.size()]);
		st.setForYLevel(yIndex << 4, subChunk);
	}
	
	private static void doEmptyChunk(Chunk chunk, BlockPos subChunkPos, BlockPos pos, RadPocket pocket, ForgeDirection facing){
		BlockPos newPos = pos.offset(facing);
		BlockPos outPos = newPos.add(subChunkPos);
		Block block = chunk.worldObj.getBlock(outPos.getX(), outPos.getY(), outPos.getZ());
		if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
			if(!isSubChunkLoaded(chunk.worldObj, outPos.getX(), outPos.getY(), outPos.getZ())){
				if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
					pocket.connectionIndices[facing.ordinal()].add(-1);
				}
			} else {
				
				RadPocket outPocket = getPocket(chunk.worldObj, outPos.getX(), outPos.getY(), outPos.getZ());
				if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
					pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
			}
		}
	}
	
	private static Queue<BlockPos> stack = new ArrayDeque(1024);
	
	private static RadPocket buildPocket(SubChunkRadiationStorage subChunk, World world, BlockPos start, BlockPos subChunkWorldPos, ExtendedBlockStorage chunk, RadPocket[] pocketsByBlock, int index){
		RadPocket pocket = new RadPocket(subChunk, index);
		stack.clear();
		stack.add(start);
		while(!stack.isEmpty()){
			BlockPos pos = stack.poll();
			Block block = chunk.getBlockByExtId(pos.getX(), pos.getY(), pos.getZ()); // !!!
			if(pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] != null || (block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
				continue;
			}
			pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] = pocket;
			for(ForgeDirection facing : ForgeDirection.VALID_DIRECTIONS){
				BlockPos newPos = pos.offset(facing);
				if(Math.max(Math.max(newPos.getX(), newPos.getY()), newPos.getZ()) > 15 || Math.min(Math.min(newPos.getX(), newPos.getY()), newPos.getZ()) < 0){
					BlockPos outPos = newPos.add(subChunkWorldPos);
					if(outPos.getY() < 0 || outPos.getY() > 255)
						continue;
					block = world.getBlock(outPos.getX(),  outPos.getY(),  outPos.getZ()); // !!!
					if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
						if(!isSubChunkLoaded(world, outPos.getX(), outPos.getY(), outPos.getZ())){
							if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
								pocket.connectionIndices[facing.ordinal()].add(-1);
							}
						} else {
							RadPocket outPocket = getPocket(world, outPos.getX(), outPos.getY(), outPos.getZ());
							if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
								pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
						}
					}
					continue;
				}
				stack.add(newPos);
			}
		}
		return pocket;
	}
	
	public static class RadPocket {
		public SubChunkRadiationStorage parent;
		public int index;
		public float radiation;
		private float accumulatedRads = 0;
		@SuppressWarnings("unchecked")
		public List<Integer>[] connectionIndices = new List[ForgeDirection.VALID_DIRECTIONS.length];
		
		public RadPocket(SubChunkRadiationStorage parent, int index) {
			this.parent = parent;
			this.index = index;
			for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i ++){
				connectionIndices[i] = new ArrayList<>(1);
			}
		}
		
		protected void remove(World world, BlockPos pos){
			for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
				connectionIndices[e.ordinal()].clear();
			}
			parent.parent.parent.activePockets.remove(this);
		}

		public BlockPos getSubChunkPos() {
			return parent.parent.getWorldPos(parent.yLevel);
		}
	}
	
	public static class SubChunkRadiationStorage {
		public ChunkRadiationStorage parent;
		public int yLevel;
		public RadPocket[] pocketsByBlock;
		public RadPocket[] pockets;
		
		public SubChunkRadiationStorage(ChunkRadiationStorage parent, int yLevel, RadPocket[] pocketsByBlock, RadPocket[] pockets) {
			this.parent = parent;
			this.yLevel = yLevel;
			this.pocketsByBlock = pocketsByBlock;
			this.pockets = pockets;
		}
				
		public RadPocket getPocket(int x, int y, int z){
			if(pocketsByBlock == null){
				return pockets[0];
			} else {
				x &= 15;
				y &= 15;
				z &= 15;
				
				RadPocket p = pocketsByBlock[x * 16 * 16 + y *16 + z];
				return p == null ? pockets[0] : p;
			}
		}
		
		public void setRad(SubChunkRadiationStorage other){
			float total = 0;
			for(RadPocket p : other.pockets){
				total += p.radiation;
			}
			float radPer = total/pockets.length;
			for(RadPocket p : pockets){
				p.radiation = radPer;
				if(radPer > 0){
					p.parent.parent.parent.activePockets.add(p);
				}
			}
		}
		
		public void remove(World world, BlockPos pos){
			for(RadPocket p : pockets){
				p.remove(world, pos);
			}
			for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
				//world.getBlockState(pos.offset(e, 16));
				
				IChunkProvider provider = world.getChunkProvider();
				provider.loadChunk((pos.getX() + 16) >> 4, (pos.getZ() + 16) >> 4); // !!!
				
				BlockPos offPos = pos.offset(e, 16);
				if(isSubChunkLoaded(world, offPos.getX(), offPos.getY(), offPos.getZ())){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, offPos.getX(), offPos.getY(), offPos.getZ());
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
				}
			}
		}
		
		public void add(World world, BlockPos pos){
			for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
				//world.getBlockState(pos.offset(e, 16));
				
				IChunkProvider provider = world.getChunkProvider();
				provider.loadChunk((pos.getX() + 16) >> 4, (pos.getZ() + 16) >> 4); // !!!

				BlockPos offPos = pos.offset(e, 16);
				if(isSubChunkLoaded(world, offPos.getX(), offPos.getY(), offPos.getZ())){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, offPos.getX(), offPos.getY(), offPos.getZ());
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
					for(RadPocket p : pockets){
						List<Integer> indc = p.connectionIndices[e.ordinal()];
						for(int idx : indc){
							sc.pockets[idx].connectionIndices[e.getOpposite().ordinal()].add(p.index);
						}
					}
				}
			}
		}
	}
	
	public static class ChunkRadiationStorage {
		private static ByteBuffer buf = ByteBuffer.allocate(524288);
		
		public WorldRadiationData parent;
		private Chunk chunk;
		private SubChunkRadiationStorage[] chunks = new SubChunkRadiationStorage[16];
		
		public ChunkRadiationStorage(WorldRadiationData parent, Chunk chunk) {
			this.parent = parent;
			this.chunk = chunk;
		}
		
		public SubChunkRadiationStorage getForYLevel(int y){
			int idx = y >> 4;
			if(idx < 0 || idx > chunks.length){
				return null;
			}
			return chunks[y >> 4];
		}
		
		public BlockPos getWorldPos(int y){
			return new BlockPos(chunk.getChunkCoordIntPair().chunkXPos << 4, y, chunk.getChunkCoordIntPair().chunkZPos << 4);
		}
		
		public void setForYLevel(int y, SubChunkRadiationStorage sc){
			if(chunks[y >> 4] != null){
				chunks[y >> 4].remove(chunk.worldObj, getWorldPos(y));
				if(sc != null)
					sc.setRad(chunks[y >> 4]);
			}
			if(sc != null){
				sc.add(chunk.worldObj, getWorldPos(y));
			}
			chunks[y >> 4] = sc;
		}
		
		public void unload(){
			for(int y = 0; y < chunks.length; y ++){
				if(chunks[y] == null)
					continue;
				for(RadPocket p : chunks[y].pockets){
					parent.activePockets.remove(p);
				}
				chunks[y] = null;
			}
		}
		
		public NBTTagCompound writeToNBT(NBTTagCompound tag){
			for(SubChunkRadiationStorage st : chunks){
				if(st == null){
					buf.put((byte) 0);
				} else {
					buf.put((byte) 1);
					buf.putShort((short) st.yLevel);
					buf.putShort((short)st.pockets.length);
					for(RadPocket p : st.pockets){
						writePocket(buf, p);
					}
					if(st.pocketsByBlock == null){
						buf.put((byte) 0);
					} else {
						buf.put((byte) 1);
						for(RadPocket p : st.pocketsByBlock){
							buf.putShort(arrayIndex(p, st.pockets));
						}
					}
				}
			}
			buf.flip();
			byte[] data = new byte[buf.limit()];
			buf.get(data);
			tag.setByteArray("chunkRadData", data);
			buf.clear();
			return tag;
		}
		
		public short arrayIndex(RadPocket p, RadPocket[] pockets){
			for(short i = 0; i < pockets.length; i ++){
				if(p == pockets[i])
					return i;
			}
			return -1;
		}
		
		public void writePocket(ByteBuffer buf, RadPocket p){
			buf.putInt(p.index);
			buf.putFloat(p.radiation);
			for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				buf.putShort((short) indc.size());
				for(int idx : indc){
					buf.putShort((short) idx);
				}
			}
		}
		
		public void readFromNBT(NBTTagCompound tag){
			ByteBuffer data = ByteBuffer.wrap(tag.getByteArray("chunkRadData"));
			for(int i = 0; i < chunks.length; i ++){
				boolean subChunkExists = data.get() == 1 ? true : false;
				if(subChunkExists){
					int yLevel = data.getShort();
					SubChunkRadiationStorage st = new SubChunkRadiationStorage(this, yLevel, null, null);
					int pocketsLength = data.getShort();
					st.pockets = new RadPocket[pocketsLength];
					for(int j = 0; j < pocketsLength; j ++){
						st.pockets[j] = readPocket(data, st);
						if(st.pockets[j].radiation > 0){
							parent.activePockets.add(st.pockets[j]);
						}
					}
					boolean perBlockDataExists = data.get() == 1 ? true : false;
					if(perBlockDataExists){
						st.pocketsByBlock = new RadPocket[16*16*16];
						for(int j = 0; j < 16*16*16; j ++){
							int idx = data.getShort();
							if(idx >= 0)
								st.pocketsByBlock[j] = st.pockets[idx];
						}
					}
					chunks[i] = st;
				} else {
					chunks[i] = null;
				}
			}
		}
		
		public RadPocket readPocket(ByteBuffer buf, SubChunkRadiationStorage parent){
			int index = buf.getInt();
			RadPocket p = new RadPocket(parent, index);
			p.radiation = buf.getFloat();
			for(ForgeDirection e : ForgeDirection.VALID_DIRECTIONS){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				int size = buf.getShort();
				for(int i = 0; i < size; i ++){
					indc.add((int) buf.getShort());
				}
			}
			return p;
		}
	}
	
	public static class WorldRadiationData {
		public World world;
		private Set<BlockPos> dirtyChunks = new HashSet();
		private Set<BlockPos> dirtyChunks2 = new HashSet();
		private boolean iteratingDirty = false;
		
		public Set<RadPocket> activePockets = new HashSet();
		public HashMap<ChunkCoordIntPair, ChunkRadiationStorage> data = new HashMap();
		
		public WorldRadiationData(World world) {
			this.world = world;
		}
	}
}
