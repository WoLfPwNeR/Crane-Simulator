Instructions
type "make assign2" to compile
type "make run" to run
type "make all" to compile and run



Note:

You should be patient about the program because I made flexibility so insignificant that you have to align the magnet and block nearly perfect to pick it up.

Also, drag the main body(excluding wheels)to move in x direction, drag 5 arm joints to rotate them.
Click the green magnet to pick up a block, click again to release. Magnet will change to red if any block is attached to it, the block will change to yellow as well. In order for the block to stay after you release it, you have to place it either on the ground level or on top of another block



Enhancements:

- I added a functionality that allows user to reset the crane to original position, as well as returning all the blocks. Just right click to undo all the work!

- If user try to move the crane out of screen, my program will stop this by setting it back inside.

- User can also lay a block on top of another, but if more than half of the block is positioned over the edge of the bottom block, it will return to its original place.
For example:
+----+
|    |
+----+
|    |
+----+OK

+----+
|    |
+-+--+-+
  |    |
  +----+OK

+----+
|    |
+--+-+--+
   |    |
   +----+NOT OK

- User can also stack a block on top of 2 blocks of the same height.
For example:
  +----+
  |    |
+-+--+-+--+
|    |    |
+----+----+OK

    +----+
    |    |
+---++--++---+
|    |  |    |
+----+  +----+OK

- How can it be amazing without background? this game is themed with Super Mario background so you don't get bored!