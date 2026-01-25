# Packet System MK2

## Introduction
This system revolves around one key premise: **Don't send players packets if we don't need to.**

This sounds simple, but in reality it comes with a lot of edgecases that this system solves.

## Force-sending
To navigate around some of the edgecases, this system introduces a process for force-sending packets to players.
There are 2 cases where a packet is force-sent:
- When a **client** loads a new chunk, OR
- When a **client** goes within a chunk radius of a TileEntity.

### Loading
Every time a client loads a chunk (whether that be from disk or newly generated), the client will send a packet
to the server, effectively asking for every TEs' data in that chunk. This is done via the `TEDataRequestPacket`.

### Radius
When a client enters a chunk, a `TEDataRequestPacket` will be sent for the newly entered chunk, _as well as the adjacent chunks_.
This does come with some optimizations, however; the server will only send 6 chunks worth of data. The previous chunk that the client was in,
as well as the 2 chunks adjacent to that chunk (dependant on the axis the player was moving on) will be skipped, as the client already
has the data for those chunks.

### Packet handling
When the server receives a `TEDataRequestPacket`, the `playerNeedsData` function (inside `IBufPacketReceiver`) is called for every tile inside the
requested chunk. This function must internally place the player in some form of special queue where they, upon the next tick (when packets would normally be sent),
should be sent the TE's data. This step should ignore all caching and should "forcefully" send the TE data (hence the name).

## Data-Change System
This system also includes an experimental data-change system, where TEs can designate their data as having "changed." This allows only
_**compiling**_, as well as sending packets _only when needed_.

Normally, when a TE's data changes (or when a check is made to see if it has changed), it requires recompiling the entire packet buffer. This, sadly,
is an expensive process. This system aims to solve this by only sending packets when the TE's data _actually_ changes, as determined by the TE itself.

This is done by using the `networkPackMK2` function, rather than the `networkPackNT` function in the `TileEntityLoadedBase`.
However, (and this is what makes it hard to implement), any time the data changes, the tile should _always_ call the `dataChanged()` function (in `TileEntityLoadedBase`) whenever
any data that it may be sending has changed. This is to actually notify the system that the data has changed at all.

## And thats it!

Ask me (BallOfEnergy01) if you have any questions.
