# OpenComputers Floppy Disks

This directory is where the contents of floppy disks registered by `com.hbm.handler.CompatHandler` reside.

New floppy disks can be added by:
1. Adding a line inside the `init()` function in the `CompatHandler` class to add the floppy disk to the list of disks to register
   (actually registering the disks is done automatically by the handler.)
2. Adding the Lua (Preferably 5.3) files to the directory path based on the name you chose for your floppy disk.
   <br>Note: the names of drives are "sanitized", meaning the directory path will be the name you selected but all lowercase and stripped of any non-word character.
   (A-Z, a-z, 0-9, _)
3. Add a recipe to the disk at the end of the `init()` function in the `CompatHandler` class, though this step is not required.

After those steps are complete, a new floppy disk should be registered into OC with a recipe (if added).