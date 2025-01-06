/**
 * 
 */
/**
 * @author hbm
 *
 */
package api.hbm.energymk2;

// i have snorted two lines of pure caffeine and taken one large paracetamol laced with even more caffine, let's fucking go

//most of the new classes are just copy pasted mashed up shit from yesteryear, what a productive segment that was

/*

before my caffine high ends entirely and i black out, here's the gist:
* diodes are handled like energy receiver and simply chain-load the power net they output into in a recursive function, this might be a bit laggy compared to the rest of the system, but it's still way less laggy than the old one
* instead of power nets being bound to tile entities directly, tiles spawn ethereal "nodes" similar to the drone waypoints which can be saved using world data, meaning that breaking cables will delete nodes, but unloading them will keep them alive in "node space" which is what's actually used to check for connections
* power nets may cache some positional info in order to limit the amount of nodes, this should prevent horrific freezes in the unlikely event that some retard makes a superflat world out of cables
* general energy transmission will work in a similar fashion as martin explained his, but somewhat simplified; the system will determine supply and demand and then split those evenly if possible, retrying within one operation is only necessary for minor restrictions like priority, any leftovers from rounding don't have to be re-tried because the next tick will already take care of that
* invest funds in more coal mare nudes
* battery "fair share" transfer will most likely no longer work, but that's not really as relevant these days considering there's capacitors and because batteries have transfer speed limits anyway
* most of the machine's functions will be repurposed, the "sendPower" method will no longer send power directly but register the machine to the network as a power source
* if all else fails and martin still hasn't surrendered his code, i will beg greg for his wisdom (but without loss or tiering because fuck that)
* 
*  |  | || 
* ____|____
*     |
*  || | |_ 
* 
* ...i said WITHOUT loss

*/