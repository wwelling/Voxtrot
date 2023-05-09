# Voxtrot
Soon to be Voxel Game Engine.

## Backlog
1. ~~Render TrueType fonts using dynamic draw VBO without awt.~~
2. ~~Implement greedy mesh optimization.~~
3. ~~Skybox.~~
4. ~~Voxel textures.~~
5. Sections.
6. Dynamic chunk map loading/buffering, writing and disposal. 
  * Lower resolution textures, outer buffer
7. Dynamic active chunk buffering. 
  * Bring in voxel properties into memory in proximity of catalyst, inner buffer. 
8. Edit mode.
  * Voxel adding and removing.
  * Update chunk method.
9. Terrain generation.
10. World/section/chunk updating.
11. Hidden chunk culling.
12. Hidden chunk side culling.
13. Occlusion culling. 
  * Occlusion queries are slow. 
  * Maybe shader program ray tracing or software occlusion culling.
14. Collision detection.
15. Normals.
16. Lighting.
17. Physics.
18. Day/Night simulation.
19. Weather simulation.
20. Object generation.
  * Trees, buildings, paths/roads, etc.
  * Voxel type properties
21. Character creation.
  * Voxel based characters
  * Possibly 3ds mesh

## Sprint Schedule
| Card  | Points | Developer |
| :------------- | :-------------: | :------------- |
| 5. Sections |  |  |
| 6. Outer buffer |  |  |
| 7. Inner buffer |  |  |
| 8. Edit mode |  |  |
| 9. Terrain |  |  |


#### To build 
```
mvn clean package
```

> ***BROKEN*** Upgrade required.

Run from bash shell:
```
XMODIFIERS=@im=ibus
java -Djava.library.path="native" -cp target/Voxtrot-0.0.1-SNAPSHOT.jar voxel.game.Main
```
