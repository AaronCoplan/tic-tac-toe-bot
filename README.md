# Tic Tac Toe Bot

Project to create a **Tic Tac Toe Bot** which learns from past games and becomes unbeatable at Tic Tac Toe.  

Contributors: [@AaronCoplan](https://github.com/AaronCoplan), [@Arkidillo](https://github.com/Arkidillo), [@apadalian](https://github.com/apadalian)

This project is licensed under the MIT License.

## Installing and Running

**Dependencies:** 

To install and run, it is a requirement that the following dependencies are met:
  * Java 8 is installed

There are no other requirements at this time.

**Installation:** 

Once the dependencies are met, simply clone this repository.

**Building and Running:**

This project is built using **Gradle**.  To build and run, follow these instructions:

1. Ensure you are in the root directory of the project (the directory containing the **build.gradle** file and this **README** file)
2. Build the software into a runnable jar file:
  * On **Mac** or **Linux**, run the following command: `./gradlew clean build`
  * On **Windows**, run the following command: `gradlew.bat clean build`
3. Run the jar file we've just created: 
  * To play against the computer with a GUI: `java -jar build/libs/tictactoebot-v0.0.1.jar`
  * To train the bot with N games: `java -jar build/libs/tictactoebot-v0.0.1.jar train <num games>`
  * To use host a BotVsBot match: `java -jar build/libs/tictactoebot-v0.0.1.jar host <port>`
     * NOTE: To exit from host, type `exit` in console then press enter, or type `ctrl-c`
  * To be a client in a BotVsBot match: `java -jar build/libs/tictactoebot-v0.0.1.jar client <ip> <port> <num games>`

## Major Components

* Random Trainer Bot (to rapidly train the bot with N randomized games)
* Ability for the bot to train against itself
* Data Handler (representing moves, storing them, and fetching and filtering them efficiently)
* UI (for a human to play the bot)
* Move Selection Engine (the bot itself)
* Prediction Engine (predicting the game)
