# Battleship
## Made by Matt Marshall, Austin Ayers, and Julien Fournell

## Game Objective: (Taken from http://www.cs.nmsu.edu/~bdu/TA/487/brules.htm)

The object of Battleship is to try and sink all of the other player's before they sink all of your ships. All of the other player's ships are somewhere on his/her board.  You try and hit them by calling out the coordinates of one of the squares on the board.  The other player also tries to hit your ships by calling out coordinates.  Neither you nor the other player can see the other's board so you must try to guess where they are.  Each board in the physical game has two grids:  the lower (horizontal) section for the player's ships and the upper part (vertical during play) for recording the player's guesses. 

## To Start the game:
Before beginning, you need to change line #286 in MainFrame.java from "localhost" to the IP address of the host computer. After doing that, start BattleshipServer.java on the host computer and Mainframe.java on the two client computers. Follow the prompted instructions on the right side of the clients to play a regular game of Battleship! (All .java files must be compiled before running, obviously)

## Thoughts and Reflections:
First and foremost, we would like to express how well-scoped the project we chose was. It had a clear and easily achievable goal with direct relations to this class. Namely, sending an ArrayList's information over the network and communication between clients and host. As well, it was within reason and had a concise scope. The only challenges we faced while completing this project were learning to use the Java GUI and getting the communications between clients and server right. Overall, the project is complete and we learned many things from it.
