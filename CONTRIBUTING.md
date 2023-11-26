# NTM Contribution Guidelines, Version 1

## Keep it concise

The best PRs are the ones that are small and to the point. The entire PR should focus on the thing you're trying to do, whether it's a fix or a feature PR. If your PR adds the Super Weldtronic 9000, there's no reason to include changes and tweaks to other things that have nothing to do with the Super Weldtronic 9000. If you think those changes are still necessary, open a new PR.

## Keep it clean

While admittedly my own code isn't the cleanest on earth, please try to keep terrible practices at a minimum. Also avoid things like unused variables and imports, mixed indentation styles or changes that have a high likelihood of breaking things.

Things you should also avoid include:
* new libraries (unless your PR absolutely needs it like for special mod compat)
* duplicate util functions (just use what we have, man)
* unused or half finished util functions (for obvious reasons)
* half finished or obviously broken features (à la "bob will fix it, i'm sure of it", please don't do that)

## Test your code

This should go without saying, but please don't PR code that was never actually tested or has obvious compiler errors in it.

## Communication

If you're planning on adding some new thing or doing a grand change, it's best to ask whether that's a good idea before spending 50 hours on a project that won't end up getting merged, due to issues that could have been entirely avoidable with communication.

## No guarantees

This ties together with the previous point - there's no guarantees that your PR gets merged no matter how hard or long you've worked on it. However, if you follow these guidelines, there's a good chance that your PR will be accepted.

## I want to help but don't know where to start

If you want to help the project, consider getting involved with the [wiki](ntm.fandom.com) first. Writing an article is the easiest and quickest way of helping, and requires no programming knowledge. If you do know Java and want to help, consider these places first:

* Localization, i.e. translations in different language are always accepted.
* `IConfigurableMachine`, an interface that allows machines to be added to the `hbmMachines.json` config, is still not used by many machines.
* F1 Presentations, also known as "Stare" or "Jar Presentations", is a neat system of creating a short movie explaining functionality. All the relevant code can be found in `com.hbm.wiaj`.
* Some guns still use the old "bow-style" systems and not `ItemGunBase`.
* Many guns don't have any animations whatsoever, just adding a minor recoil would already improve them by a lot.
* Adding tooltips to more machines, explaining some of the basics.
