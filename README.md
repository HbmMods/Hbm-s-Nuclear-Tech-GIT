# Hbm-s-Nuclear-Tech-GIT

https://minecraft.curseforge.com/projects/hbms-nuclear-tech-mod?gameCategorySlug=mc-mods&projectID=235439

For a 1.12 fork, check this link: https://github.com/Drillgon200/Hbm-s-Nuclear-Tech-GIT/releases

## Building from source

Tired of waiting until the next version comes out? Here is a tutorial on how to compile the very newest version yourself:
Please note that these installation instructions are assuming you're running Microsoft Windows operating system. Linux users should know what to do by looking at the same guide.

 1. Make sure you have JDK8 installed. If not, download it from [adoptium.net](https://adoptium.net/?variant=openjdk8&jvmVariant=hotspot)
 2. If you don't have git installed, download&install it from [here](https://git-scm.com/downloads).
 3. Open up "Git Bash":
    * Press Windows Button, type "Git Bash" and press ENTER
 4. Enter the directory where you would like the sources to be (advanced users can use any directory)
 ```bash
     cd $HOME/Downloads
 ```
 5. Download the source code:
 ```bash
     git clone https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT.git
 ```
 4. Enter the source code directory
 ```bash
     cd Hbm-s-Nuclear-Tech-GIT
 ```
 5. Build the mod
 ```bash
     ./gradlew build
 ```
 6. Locate the mod file.
    1. Open up your file explorer.
    2. Navigate to the location where you downloaded the sources.
       * If you exactly followed step 1, it should be `C:/Users/%USER%/Downloads`.
    3. Enter the downloaded source tree.
    4. Navigate to `build/libs`.
    5. Grab the "HBM-NTM-<version>.jar" one.
        * This is your mod file. You can install it like any other mod by putting it into your mods directory.

## Contributing
If you want to make some changes to the mod, follow this guide:
1. Follow steps 1-4 from *Building from source* section
2. Setup forge decompilation workspace
```bash
    ./gradlew setupDecompWorkspace
```
3. (OPTIONAL, but needed if you use eclipse) Generate eclipse files
```bash
    ./gradlew eclipse
```
4. Open up the project directory in eclipse using *Open existing project from file system*
5. Code!

# License
This software is licensed under the GNU Public License version 3. In short: This software is free, you may run the software freely, create modified versions, distribute this software and distribute modified versions, as long as the modified software too has a free software license. You win this round, Stallman. The full license can be found in the `LICENSE` file.
