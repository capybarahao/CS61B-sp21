
a MapGenerator class

pass seed to map generator class

overlap

Create a Room class and keep a list of all existing rooms during world generation.

Write an overlap method for the Room class, and reject any generated room that overlaps an existing one.

Making hallways that turn

For each room, randomly generate neighbor rooms that branch off of the current room.

Since a hallway is just a width 1 room, this algorithm is capable of generating turning hallways.

DO NOT

Do not share state between methods!
Anything complicated involving shared variables(set and accessed by multiple methods)

DO

If you need to return multiple things, make a class that has multiple fields.

For example, I made a Position class that just
stored x and y coordinates (and later found it useful to add methods to my Position class).