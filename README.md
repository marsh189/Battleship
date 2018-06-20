# Battleship
## Made by Matt Marshall, Austin Ayers, and Julien Fournell

## Game Objective: (Taken from http://www.cs.nmsu.edu/~bdu/TA/487/brules.htm)

The object of Battleship is to try and sink all of the other player's before they sink all of your ships. All of the other player's ships are somewhere on his/her board.  You try and hit them by calling out the coordinates of one of the squares on the board.  The other player also tries to hit your ships by calling out coordinates.  Neither you nor the other player can see the other's board so you must try to guess where they are.  Each board in the physical game has two grids:  the lower (horizontal) section for the player's ships and the upper part (vertical during play) for recording the player's guesses. 

## To Start the game:
Before beginning, you need to change line #18 in MainFrame.java from "localhost" to the IP address of the host computer. (To find this, in the host computer Command Prompt, type ipconfig. It will be the IPv4 Address). After doing that, in Command Prompt, run 'javac \*.java'. Then start BattleshipServer by typing 'java BattleshipServer'. To start the clients, run 'javac \*.java' on the computers you want to use. (Clients can be on the same computer as the Server). Then run 'java MainFrame' to start the client. Follow the prompted instructions on the right side of the clients to play a regular game of Battleship! 
(Java Jdk must be downloaded to the devices being used)

## Thoughts and Reflections:
First and foremost, we would like to express how well-scoped the project we chose was. It had a clear and easily achievable goal with direct relations to this class. Namely, sending an ArrayList's information over the network and communication between clients and host. As well, it was within reason and had a concise scope. The only challenges we faced while completing this project were learning to use the Java GUI and getting the communications between clients and server right. Because of this, we were not able to have our program check for wrongly placed ships (so be careful while selecting where your ships go). Overall, the project has a fully running battleship game and we learned many things from it.

##  Note:
When placing your ships, make sure you select your second location exactly as far away from the first one as it says to. The program will not run correctly if this is not done correctly. If the prompt in the command panel does not change to the next direction, close the client and start it again. Also, The program DOES NOT check for diagonally placed ships or ships going through other ships. If you accidentally click a wrong button, again, close the client and start it again. 
