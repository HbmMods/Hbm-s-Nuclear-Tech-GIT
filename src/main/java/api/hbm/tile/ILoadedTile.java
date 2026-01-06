package api.hbm.tile;

import java.util.HashMap;
import java.util.Map;

import com.hbm.util.Compat;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** For anything that should be removed off networks when considered unloaded, only affects providers and receivers, not links. Must not necessarily be a tile. */
public interface ILoadedTile {
	
	public boolean isLoaded();
	
	// should we gunk this into the API? no, but i don't care
	public static class TileAccessCache {
		
		public static Map<Quartet, TileAccessCache> cache = new HashMap();

		public static int NULL_CACHE = 20;
		public static int NONNULL_CACHE = 60;
		
		public TileEntity tile;
		public long expiresOn;
		
		public TileAccessCache(TileEntity tile, long expiresOn) {
			this.tile = tile;
			this.expiresOn = expiresOn;
		}
		
		public boolean hasExpired(long worldTime) {
			if(tile != null && tile.isInvalid()) return true;
			if(worldTime >= expiresOn) return true;
			if(tile instanceof ILoadedTile && !((ILoadedTile) tile).isLoaded()) return true;
			return false;
		}
		
		public static Quartet publicCumRag = new Quartet(0, 0, 0, 0);
		public static TileEntity getTileOrCache(World world, int x, int y, int z) {
			publicCumRag.mangle(x, y, z, world.provider.dimensionId);
			TileAccessCache cache = TileAccessCache.cache.get(publicCumRag);
			
			if(cache == null || cache.hasExpired(world.getTotalWorldTime())) {
				TileEntity tile = Compat.getTileStandard(world, x, y, z);
				cache = new TileAccessCache(tile, world.getTotalWorldTime() + (tile == null ? NULL_CACHE : NONNULL_CACHE));
				TileAccessCache.cache.put(publicCumRag.clone(), cache);
				return tile;
			} else {
				return cache.tile;
			}
		}
	}
}
