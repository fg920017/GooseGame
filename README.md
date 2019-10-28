# Installation

* Make sure you have installed java 8 and maven 3+ on your machine. 

* Make sure the corresponding environment variables are set. 

* Once the project is downloaded, place youself inside the "TheGooseGame" folder, which contains the pom.xml and execute the following command to compile: <b>mvn clean package</b>. 
<br/> The "target" folder will be created and the "TheGooseGame" jar will be present inside it.

# Run it! 

* Once compiled the project, place yourself in the "target" folder, which contains the newly created jar, and execute the following command:
<b>java -jar TheGooseGame.jar </b>. <br/>

The commands are: 

* add player <PLAYER_NAME>: add player to game
* move <PLAYER_NAME>: rolls two dice and moves the player
* move <PLAYER_NAME> <DICE> <DICE> : moves the player, passing the dice
* exit: exit the game. If one of the players reaches box 63 the game will end automatically


 
