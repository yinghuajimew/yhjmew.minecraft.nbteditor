## NBT Editor_1.0.1-Update Log

1. A custom directory, whether it is a parent directory or a direct archive directory, is allowed to be loaded. The parent directory will exclude non-archives. If the archive is missing part of it, loading will be refused, and the parent directory will not be displayed.
Example:
/storage/emulated/0/download/ allowed
/storage/emulated/0/download/{archive name}/ allowed
2. Added anti-fool and fool-proof functions. If the user moves the edited archive of Blocktopograph to an old directory, a new directory, or a custom directory, the player will not be loaded.
3. Update jar to stable version, 2019~2020
4. Replace some NBT labels and solve that 12, 11, 7 are arrays and the rest are single groups.
5. ? No one should click that fast to prevent users from clicking and reloading too quickly, causing errors.


## NBT Editor_1.0.2-Update Log

1. Add English
2. About page pop-up window
3. Add a description of this class (automatic matching of item IDs is not implemented)
4. Add night and day time
5. UI redesign
6. Big update! Replace the original iq80 library with HiveGamesOSS and solve the Blocktopograph lock problem. It can be seamlessly switched and modified with Blocktopograph and naro.
7. A large number of nbts in the editing world or players cause crashes
8. Add Blocktopograph similar to dendrogram


## NBT Editor_1.0.3-Update Log

New

1. Add other players NBT more operations and modifications
2. Added more operations and modifications to edit map NBT
3. Added more operations and modifications for editing village NBT


Repair

1. Added the ability to capture fatal errors that usually cause "APP has stopped running" and save the error information to a local file.
2. Multi-threaded sharded reading, db player archives in this directory
3. Solve the problem that new applications do not have permission to use old application folders from Huawei
4. Add a sidebar to prevent the page from running out of space
5. Whenever data is loaded, it must be updated accurately
6. Repair, whether it is a public directory or a private directory, as long as it crashes once, no matter where it is, both sides will be saved once.
7. Add map multi-threading to speed up image processing and prevent user input from being too large.
8. When other players in this category edit the map or even the village and there is nothing in it, they can click on it. Although it is empty, players can create it themselves.
9. When loading other classes, when I click full-screen editing, it will be loaded by the player. Although the content is not lost, regardless of whether it is saved or not, once you return and press reload, it will be the player data.
10. When the user clicks on this class, it can be automatically loaded without having to step back to the page to load.
11. After loading archive a last time, the world, village, etc. were still saved. It should be automatically reset after the user switches archive b.
12. If you have loaded the players, maps, villages, etc. in the sidebar, it should seem to be left in the memory without reloading, otherwise you will have to reload it every time.
13. "Load archive" should directly load the selected archive instead of popping up a pop-up window and re-select it.
14. When editing any nbt data, for some relatively large amounts of data, you can use the search function to search for the corresponding class or class translation.
15. When there are many files for the player, map, and village, you can choose to use search fuzzy to find them.
16. When there are many files for the player, map, and village, there are check boxes to select and delete as many as you want.
17. The main interface should be more compact
18. Add a description of this class (implementation of automatic matching of item IDs), which needs to be refactored
19. I should classify these IDs in more detail, they must be separated into a file each
20. Enter the level in normal view first, and then switch to tree view, which will cause a refresh. Then enter in normal view, and switch again, and the entry will be repeated.
21. The navigation stack (navigationStack) in list mode stores "the data of the previous layer" (parent node) instead of "the data of the current layer"
22. Duplicate paths for view synchronization
23. The long bar above the full screen needs to copy the functions of the main interface.
24. If the user's archive is damaged, we will try to repair it and inform the user if it needs to be repaired, because some data will be lost.
25. If you place an empty file named .nomedia under the Bridge folder, the system media service will directly ignore this folder and no longer try to scan and index, thus completely eradicating this crash. From OS2.0.203.0.VMLCNXM (Xiaomi Xiaomi 13T Pro/Redmi K60 Ultra)
26. Whether it is public or private, there will be .nomedia in the working directory and backup directory to prevent the system media service scan of the domestic system from being modified.
27. Put the search key value, original class, world class, and old world class, some counters, scoreboard, etc.
28. The key-value content cannot be found and is automatically created.
29. When loading into the archive, automatically change the homepage title and put some commonly used display map names and map seeds. You can long press the map seeds to copy the seeds.
30. Apply multi-threading to all reading and writing. When the device condition does not allow it, there is a check box set. If it is checked, it is enabled by default. If it is not checked, it will be single-threaded. The multi-threading here is not only archives, but also players, maps, villages, etc.
31. Players don’t know how to read and remember these random and complex map folder names. It is better to traverse all map names.
32. Change the application signature and no longer use mt signature. You need to create a signature belonging to the application.
33. When building an application, there must be two versions: one is the official release version, and the other is the debug version.
34. It is enough to translate the resources into Chinese and English. Wait for the sidebar to be changed (front-loaded). If you need to add it later, you can make a decision later.
35. Multi-task switching + automatic draft saving until you decide to click "Save" to write to disk, or change the archive
36. Fixed the problem that custom puzzles cannot be used after internationalization.
37. Fixed the main title of the homepage, which will not switch to the map and add seeds.
38. Anti-null pointer crash repair (for the case where null adapter is passed in the search list)