# Hbm's Nuclear Tech Mod

https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod

## Installation Instructions
Tired of waiting until the next version comes out? Here is a tutorial on how to compile the very newest version yourself:
1. Download the source.
2. Open up the Command Prompt (CMD) in the main directory and run `gradlew build` on Windows or `./gradlew build` on Mac/Linux.
3. Head to `build/libs` and get the jar.
4. The jar is now done, ready for use!

If you want to do some changes in the code yourself, start here after 1. and continue with 2. once you are done:
1. Run `gradlew setupDecompWorkspace` on Windows or `./gradlew setupDecompWorkspace` on Mac/Linux.
2. Get the IDE of your choice and prepare the workspace.
   - For Eclipse:
     1. Run `gradlew eclipse` or `./gradlew eclipse`
     2. Use the `eclipse` folder as the workspace directory in Eclipse.
   - For IntelliJ IDEA:
     1. Run `gradlew idea` or `./gradlew idea`
     2. Use the main directory as the project folder in IntelliJ.
3. Make your changes to the code. You can run the code in the IDE. (Eclipse and IntelliJ have a convenient, green play button.)
4. Save changes, close the IDE, and continue with 2. of the previous list.

# License
````
DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
        Version 2, December 2004

Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>

Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.

DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

0. You just DO WHAT THE FUCK YOU WANT TO.
````
