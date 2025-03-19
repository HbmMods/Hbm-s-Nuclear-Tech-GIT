package api.hbm.tile;

/** For anything that should be removed off networks when considered unloaded, only affects providers and receivers, not links. Must not necessarily be a tile. */
public interface ILoadedTile {
	
	public boolean isLoaded();
}
