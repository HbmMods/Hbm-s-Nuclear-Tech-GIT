# Hbm-s-Nuclear-Tech-GIT

https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod?gameCategorySlug=mc-mods&projectID=235439

For a 1.12 fork, check this link: https://github.com/Drillgon200/Hbm-s-Nuclear-Tech-GIT/releases

## Installation Instructions

Tired of waiting until the next version comes out? Here is a tutorial on how to compile the very newest version yourself:
1. Download minecraft forge 1.7.10 src
2. Unpack it somewhere
3. Go to the new folder and run `gradlew setupDecompWorkspace` on windows or `./gradlew setupDecompWorkspace` on linux. This process will most likely display errors which is due to some dependencies being no longer available. Don't worry, the repository should have all the files you need.
4. Download the source and insert it into the same folder. This will overwrite a couple of files, for example build.gradle
5. The `build.gradle` file will reference these following files:
```
compile files('lib/CodeChickenCore-1.7.10-1.0.4.29-dev.jar')
compile files('lib/CodeChickenLib-1.7.10-1.1.3.140-dev.jar')
compile files('lib/NotEnoughItems-1.7.10-1.0.3.74-dev.jar')
```
which means you need to do one of the following things:
1) Find these exact versions of the dependencies, download them and put them into a folder called `libs` in your project's root folder
2) Find other versions, change the `build.gradle` file to reflect this difference and fix any potential errors this could cause (unlikely but not impossible)
3) Remove these dependencies from `build.gradle` along with all the API code. Note that this will break NEI-integration.
I know that this approach is anything but desirable, but until I get around to fixing this, you can always ask me to send you the libraries.
7. Open up the CMD in the root directory and run `gradlew build` on windows or `./gradlew build` on linux
8. Head over to `build/libs` and get the jar (named modid.jar)
9. Open the jar file with an archieve manager of your choice and insert the mod's asset folder into the jar's main directory

If you want to do some changes in the code yourself, start here after 6. and continue with 7. once you are done:
1) Get the IDE of your choice and prepare the workspace (for eclipse, it's `gradlew eclipse` or `./gradlew eclipse`, then use the eclipse folder as workspace directory in eclipse)
2) Meddle with the code, you can always test the code in the IDE (eclipse has a convenient green play button)
3) Save changes, close the IDE and continue with 7. of the previous list

## Installation Instructions for a non-standard Workspace

I cannot offer any help on compiling the mod in a GregTech-workspace as I never managed to get it to work myself, here's a rough guide on how it might be possible:
1. Prepare the workspace
2. Slap this mod's `src` into the project's root directory
3. Pray

# License

This software is licensed under the GNU Public License version 3. In short: This software is free, you may run the software freely, create modified versions, distribute this software and distribute modified versions, as long as the modified software too has a free software license. You win this round, Stallman. The full license can be found in the `LICENSE` file.
