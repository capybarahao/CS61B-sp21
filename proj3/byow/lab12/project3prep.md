# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: Very similar. Both made it easy to expand (by simply changing parameters)
        Even code lines are similar. Also neither creates new class. All jobs done in HexWorld.java

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: may be get some random rooms at first without thinking about how to place them,
        place rooms on map so they don't overlap.
        generate hallways to connect them.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: single room generation,
        get the "start position" of hallways on room wall
        how many hallways max can a room have?


-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: the shorter side of hallway is max two tiles"a width of 1 or 2 tiles and a random length"
        room 
        similarity: rectangular.
