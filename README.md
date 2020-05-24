# Hbm-s-Nuclear-Tech-GIT

https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod?gameCategorySlug=mc-mods&projectID=235439

## Installation Instructions

### Important note: Forge has since dropped support for 1.7.10, quite a few things are not going to work from the getgo. THIS IS NOT MY FAULT, so please, instead of opening an issue on this repository and making it my problem too, use this handy thing called "google" for instructions on how to fix 1.7.10 workspaces. The dependencies are also no longer available on the official forge site, you might have to resort to different versions of the CodeChicken mods, simply change the build.gradle file accordingly and check the code for any errors.

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
6. Download these exact versions of NEI and it's core mods, and then create a folder named "lib" inside the root of the project (IE wheverer you downloaded forge), and place them inside that folder
7. Open up the CMD in the main directory and run `gradlew build` on windows or `./gradlew build` on linux
8. Head to `build/libs` and get the jar
9. Open the jar with an archieve manager of your choice and insert the mod's asset folder into the jar's main directory
10. Due to a *tiny* fuckup on my side (i.e. only using the `/main/java` folder in this repository and omitting `/main/resources`) the code in this repo does not include a `mcmod.info` file *which is mandatory, not including it will cause funny things to happen.* In order to fix this, either insert the file from one of my releases into your jar or write your own file, the most important part is including the `modid` kay-value pair (or at least from what i can tell).
11. The jar is now done, ready for use!

If you want to do some changes in the code yourself, start here after 6. and continue with 7. once you are done:
1) Get the IDE of your choice and prepare the workspace (for eclipse, it's `gradlew eclipse` or `./gradlew eclipse`, then use the eclipse folder as workspace directory in eclipse)
2) Meddle with the code, you can run the code in the IDE (eclipse has a convenient green play button)
3) Save changes, close the IDE and continue with 7. of the previous list

# License
DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE Version 2, December 2004 Everyone is permitted to copy and distribute verbatim or modified copies of this license document, and changing it is allowed as long as the name is changed. DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 0. You just DO WHAT THE FUCK YOU WANT TO.
