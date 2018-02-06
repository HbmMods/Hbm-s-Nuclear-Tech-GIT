# Hbm-s-Nuclear-Tech-GIT

https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod?gameCategorySlug=mc-mods&projectID=235439

## Installation Instructions
Tired of waiting until the next version comes out? Here is a tutorial on how to compile the very newest version yourself:
1. Download minecraft forge 1.7.10 src
2. Unpack it somewhere
3. Go to the new folder and run `gradlew setupDecompWorkspace` on windows or `./gradlew setupDecompWorkspace` on linux
4. Download the source and insert it into `src/main/java/`
5. Open build.gradle with a text editor of your choice and write this into the dependencies brackets:
```
compile files('lib/CodeChickenCore-1.7.10-1.0.4.29-dev.jar')
compile files('lib/CodeChickenLib-1.7.10-1.1.3.140-dev.jar')
compile files('lib/NotEnoughItems-1.7.10-1.0.3.74-dev.jar')
```
6. Download these exact versions of NEI and it's core mods and insert them into the lib folder (if there is none, create one in the dev environment's main directory, I forgot)
7. Open up the CMD in the main directory and run `gradlew build` on windows or `./gradlew build` on linux
8. Head to `build/libs` and get the jar
9. Open the jar with an archieve manager of your choice and insert the mod's asset folder into the jar's main directory
10. The jar is now done, ready for use!

If you want to do some changes in the code yourself, start here after 6. and continue with 7. once you are done:
1) Get the IDE of your choice and prepare the workspace (for eclipse, it's `gradlew eclipse` or `./gradlew eclipse`, then use the eclipse folder as workspace directory in eclipse)
2) Meddle with the code, you can run the code in the IDE (eclipse has a convenient green play button)
3) Save changes, close the IDE and continue with 7. of the previous list

========
DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE Version 2, December 2004 Everyone is permitted to copy and distribute verbatim or modified copies of this license document, and changing it is allowed as long as the name is changed. DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 0. You just DO WHAT THE FUCK YOU WANT TO.
========