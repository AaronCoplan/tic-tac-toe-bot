# Tic Tac Toe Bot

Project to create a Tic Tac Toe Bot which learns from past games and becomes unbeatable at Tic Tac Toe.  

Contributors: [@AaronCoplan](https://github.com/AaronCoplan), [@Arkidillo](https://github.com/Arkidillo), [@apadalian](https://github.com/apadalian)

## Installing and Running

**Dependencies:** 

To install and run, it is a requirement that the following dependencies are met:
  * Java 8 is installed

There are no other requirements at this time.

**Installation:** 

Once the dependencies are met, simply clone this repository.

**Building and Running:**

This project is built using Gradle.  To build and run, follow these instructions:

1. Ensure you are in the root directory of the project (the directory containing the **build.gradle** file and this **README** file)
2. Build the software into a runnable jar file:
  * On Mac or Linux, run the following command: `./gradlew clean build`
  * On Windows, run the following command: `gradlew.bat clean build`
3. Run the jar file we've just created: `java -jar build/libs/tictactoebot-v0.0.1.jar`

## Major Components

* Random Trainer Bot (to rapidly train the bot with N randomized games)
* Ability for the bot to train against itself?
* Data Handler (representing moves, storing them, and fetching and filtering them efficiently) - Aaron
* UI (for a human to play the bot) - Devin
* Move Selection Engine (the bot itself)
* Prediction Engine (predicting the game) - Andrew

## JUnit Tests

JUnit Tests allow us to ensure our code is working correctly.  For instance, we might write a method called `int power(int base, int power)`, which returns the base to the power.  However, how do we know that this code is actually working correctly?

The way that we do this is by writing a JUnit test, all of which get executed during the `./gradlew clean build` phase.  If **any** JUnit tests fail, the software will fail to build, and you will not have a jar file to run.

To write a JUnit test for our method (`int power(int base, int power)`), we will add a method to a test class, found in the `src/main/test` directory.  Our method will look something like this:

```
@Test
public void testPower(){
    assertEquals(4, power(2,2));
    assertEquals(9, power(3,2));
    assertEquals(27, power(2,3));
}
```

Note how all test methods must contain the `@Test` annotation.  The `assertEquals()` method takes two parameters, the first is the expected value (aka what the method should return), and the second is the result of the method call.  If any of our assertions fail, our test will fail, and as described above, this will cause our build to fail as well.
