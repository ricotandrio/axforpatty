# AXForPatty

This project was completed during my assistant recruitment training. The project describes a scenario where you, a full-time programmer from a small company who also takes on freelance gigs, are approached by a friend wanting to build a restaurant in your hometown. Knowing you're looking for new work, they offer you a lucrative opportunity to create a small simulation restaurant game using multithreading. This game would simulate customer behavior, including their patience levels, to help your friend understand restaurant operations.

## Quick Start
The project contains three packages:
* DB: This package stores all game progress.
* meal: This package contains all classes related to game properties, such as customer, food, and drink.
* recreate: This package contains all classes related to game logic.
* The main class is located at ./src/recreate/Main.java

## Start Menu
<img src="https://github.com/ricotandrio/axforpatty/assets/119276763/e7455f7c-21c1-4586-8a42-b057da5f26f1" width="300" />

## Main Menu
<img src="https://github.com/ricotandrio/axforpatty/assets/119276763/900b5f99-3f1b-4def-b919-9a039c67daff" width="300" />

## Game Preview 
* The game starts with a 60-second timer.
* Every second, the game will shows the current list of customers and generates a new customer with a set probability by increasing patience meter by 10%.
* The goal of the game is last for 60 seconds without your patience meter reaching 0%.
* Game result if you survive the 60 seconds, you win! Your score is recorded in a local text file. If your patience meter reaches 0% before the timer ends, you lose.
* Customers, foods, and drinks will appear in random order.

<img src="https://github.com/ricotandrio/axforpatty/assets/119276763/dfbfce07-8b0a-4cd3-af42-3e3ae0707240" width="300" />
<br/>
<img src="https://github.com/ricotandrio/axforpatty/assets/119276763/7b91de7c-c1af-4d7b-a928-f8678f736f2f" width="300" />

## Scoreboard Preview
<img src="https://github.com/ricotandrio/axforpatty/assets/119276763/00f22433-ff37-41d5-8e75-967673c23e5a =250x250" width="300" />

