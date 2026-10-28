# UNO — Java Card Game 🃏

A terminal-based implementation of the classic UNO card game, developed in Java using object-oriented programming principles and software design patterns.

## 📖 Project Overview

This project was developed as a final group assignment for **CSCI 4448: Object-Oriented Analysis and Design** at the University of Colorado Boulder.

The goal was to apply object-oriented programming concepts and design patterns learned throughout the semester to create a functioning UNO card game.

The game allows users to compete against computer-controlled opponents with three difficulty settings. It incorporates card management, player interactions, turn tracking, and different opponent strategies.

The project was built upon previous assignments completed throughout the semester, giving us experience working with an existing codebase, expanding functionality, and integrating different components into a larger application.

## 🎮 Features

- **Terminal-Based Gameplay:** Play UNO directly through the command line.
- **Computer Opponents:** Compete against 3–9 computer-controlled players.
- **Difficulty Settings:** Choose between Easy, Intermediate, and Expert opponent strategies.
- **Card Management:** Generate, shuffle, distribute, draw, and play UNO cards.
- **Special Cards:** Includes Skip, Reverse, Draw Two, Wild Draw Four, and custom Shuffle cards.
- **Player Customization:** Enter player information and compete against randomly generated opponents.
- **Turn Management:** Tracks player turns and handles special card interactions.
- **Game Tracking:** Includes functionality for tracking turns and recording game information.

## 💻 Technologies Used

| Technology | Purpose |
|---|---|
| Java | Primary programming language |
| Object-Oriented Programming | Organizing game components into classes and interfaces |
| Design Patterns | Strategy, Factory, Singleton, and Observer |
| ArrayList | Managing cards, player hands, and gameplay |
| Circular Doubly Linked List | Additional implementation for player turn management |
| CSV Files | Storing card information and generating opponent names |
| UML | Planning class relationships and software architecture |

## 🚀 Installation & How to Run

### Prerequisites

Before running the game, make sure you have the following installed:

- Java Development Kit (JDK)
- A terminal or command-line interface
- Git (optional, for cloning the repository)

### Installation

**1. Clone the repository**

```bash
git clone <repository-url>
```

**2. Navigate to the project directory**

```bash
cd <repository-folder>
```

**3. Compile the Java files**

```bash
javac *.java
```

**4. Run the game**

```bash
java Main
```

Make sure `cards.csv` and `names.csv` remain in the same directory as the compiled Java classes.

### Starting a Game

Once the program launches:

1. Select the number of computer-controlled opponents (3–9).
2. Choose the opponent difficulty:
   - **1 — Easy**
   - **2 — Intermediate**
   - **3 — Expert**
3. Follow the terminal prompts to enter your player information.
4. Begin playing UNO!

During gameplay, the player can select cards from their hand, draw additional cards, view opponents' card counts, and access other available commands.

## 🏗️ Object-Oriented Design & Design Patterns

One of the primary objectives of this project was to implement object-oriented programming principles and demonstrate how different software design patterns can be used within a larger application.

### 1. Strategy Pattern

**Classes:** `DifficultyBehavior`, `Easy`, `Intermediate`, `Expert`, `CPU`

The Strategy pattern is used to implement different decision-making algorithms for computer-controlled opponents.

The `DifficultyBehavior` interface defines a common `playCard()` method that each difficulty class implements differently.

- **Easy:** Selects a playable card based on matching colors or card values.
- **Intermediate:** Evaluates available matching cards to influence its selection.
- **Expert:** Uses an alternative strategy that prioritizes Wild cards.

The `CPU` class uses the selected strategy to determine which card to play.

**Purpose:** Separating opponent behavior into individual classes makes it easier to modify existing algorithms or introduce additional difficulty levels without significantly changing the CPU class.

### 2. Factory Pattern

**Classes:** `Factory`, `Cards`, `CPU`

The Factory pattern is used to centralize object creation.

The `Factory` class provides methods for generating different colored UNO cards and creating computer-controlled opponents based on the selected difficulty.

**Purpose:** Separating object creation from gameplay logic improves code organization and makes it easier to introduce additional object types.

### 3. Singleton Pattern

**Classes:** `Logger`, `Tracker`

The Singleton pattern is used to provide shared instances of the logging and tracking components.

- `Logger` demonstrates eager initialization by creating its shared instance when the class is initialized.
- `Tracker` demonstrates lazy initialization by creating its shared instance when first requested.

**Purpose:** Providing shared instances allows different components to access centralized logging and tracking functionality.

### 4. Observer Pattern

**Classes:** `Logger`, `Menu`

The Observer pattern is demonstrated using Java's `Observable` and `Observer` APIs.

The `Logger` class contains functionality for notifying registered observers when logging information changes.

**Purpose:** The Observer pattern allows objects to communicate changes without requiring tightly coupled dependencies.

*Note: The Observer functionality is partially implemented and uses Java APIs that have since been deprecated.*

### 5. Inheritance & Polymorphism

In addition to these design patterns, the project demonstrates inheritance and polymorphism through several class relationships.

**Player Hierarchy**

The `Players` class serves as the base class for:

- `MainPlayer` — Handles human player interactions.
- `CPU` — Handles computer-controlled opponents.

Both classes share common player functionality, including managing hands, drawing cards, and tracking player information.

**Card Hierarchy**

The `Cards` class serves as the base class for different UNO card types.

Subclasses represent the four standard card colors and Wild cards.

**Polymorphism**

The `DifficultyBehavior` interface allows different opponent strategies to be used through a common method, demonstrating how multiple implementations can share the same interface.

## 📊 Data Structures

The project uses several data structures to manage gameplay.

### ArrayList

Java's `ArrayList` is used throughout the application to manage:

- The UNO deck
- Player hands
- Computer-controlled opponents
- Turn order and gameplay information

### Circular Doubly Linked List

The `AllPlayers` class implements a circular doubly linked list to represent player relationships.

This implementation includes functionality for:

- Adding players
- Advancing to the next player
- Skipping players
- Reversing player order

The linked list was developed as an alternative approach to managing turn order. The main gameplay implementation currently uses an `ArrayList`.

## 📐 UML Class Diagram

The following UML diagram illustrates the original design of the application, including its class relationships, interfaces, and design patterns.

![UNO UML Class Diagram](images/uno-uml.png)

*The UML diagram represents the project's planned architecture. Some components differ from the final implementation.*

## 🔮 Future Improvements

Although the project demonstrates the core functionality of UNO, there are several areas I would like to improve as I continue developing my programming skills.

### 1. Improved Computer Opponents

- Refine the decision-making algorithms for each difficulty level.
- Develop more intelligent card-selection strategies.
- Improve how opponents select colors when playing Wild cards.
- Allow opponents to consider the number of cards remaining in other players' hands.
- Create more meaningful differences between Easy, Intermediate, and Expert difficulties.

### 2. Graphical User Interface (GUI)

- Transition from terminal-based gameplay to a graphical interface.
- Design an interactive game board displaying cards, player hands, and opponents.
- Implement drag-and-drop functionality for playing cards.
- Add animations and visual feedback for special card interactions.
- Improve the overall user experience and accessibility.

### 3. Gameplay Improvements

- Improve the implementation of special card interactions and UNO rules.
- Introduce customizable house rules.
- Implement a scoring system to track results across multiple rounds.
- Add multiplayer functionality.
- Allow players to save and resume games.

### 4. Code Improvements

- Refactor repeated code into reusable methods.
- Improve input validation and error handling.
- Develop automated tests for gameplay logic and opponent strategies.
- Resolve existing bugs and incomplete functionality.
- Replace deprecated Java APIs with modern alternatives.
- Improve documentation and overall code maintainability.

## 👥 Development & Contributions

This project was developed as a team assignment for CSCI 4448: Object-Oriented Analysis and Design at the University of Colorado Boulder.

**Team Members:**
- Chandler Phillips
- Mathew Kim
- Suyogya Poudel

Mathew and I worked closely together throughout the semester, collaborating on multiple assignments that eventually contributed to the development of this UNO game.

My primary responsibility was developing the player classes and their associated functionality, while Mathew focused on the card classes and deck implementation.

Beyond these individual responsibilities, we collaborated on the game's overall architecture, design patterns, gameplay logic, and integration of the different components.

## 💡 Project Takeaways

This project helped strengthen my understanding of object-oriented programming and how software design patterns can be applied to a larger application.

One of my biggest takeaways was learning how to organize different components of a program so they could work together while maintaining their own responsibilities.

Working alongside my teammates also gave me experience collaborating on an existing codebase, dividing responsibilities, and integrating code developed by different people.

Looking back at this project, I can identify several areas where the code could be improved, particularly the opponent algorithms, overall code organization, and user experience.

These are improvements I would like to revisit as I continue growing as a software developer.
