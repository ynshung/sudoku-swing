Sudoku
======

This is a Sudoku game programmed using Java and Swing for the GUI. It currently supports 6x6, 9x9, and 12x12 puzzles and easy, medium and hard difficulty level.

Forked from [mattnenterprise/Sudoku](https://github.com/mattnenterprise/Sudoku).

## Improvement
- New Game Dialog: Add a dialog on new game and when user selects new game
- Sudoku Difficulty Levels: Allow users to select different difficulty levels to play
- Keyboard Number Input: Improve functionality by allowing input and movement using user’s keyboard
- Original Digit: Make the digit of the original value clearer so users can differentiate between the input values vs the original values
- Highlight on Invalid Input: The selected grid will be highlighted as red when an invalid value is inputted
- Board Completion Dialog: Prompt the congratulation message with the time used to complete after user finished the Sudoku game
- Highlight Row and Column of Selected Grid: Highlight the selected grid with the associated row and column. Highlight the selected digit that available in the Sudoku game
- Undo Function: Allow users to return to the previous step
- Clear and Clear All Functions: Functional buttons that allow the users to erase the selected grid input or all inputs made on the grid
- Timer: Make the time spent on each game visible to the users
- Desktop Icon: Update the desktop icon from the default to a specific icon
- GUI Enhancement: The colour and dimensions for the window, menu panel and grid are enhanced to improve user readability and to make it more appealing.

## Installation

Launch directly from JAR file: [sudoku.jar](https://github.com/ynshung/sudoku-swing/releases/download/2.0/sudoku.jar)

Make sure that JRE/JDKs is installed in the system.

Building from source with IntelliJ IDEA:

1. Launch IntelliJ IDEA and create a new project from version control
2. Paste the link: https://github.com/ynshung/sudoku-swing.git and create the project
3. Add a new Gradle build configuration and set the "Tasks and arguments" to run.
4. Click the run button.

## Usage

1. The program will prompt the user to select the size of the board which can be selected.
2. The program then asks for the difficulty which can be selected as well.
3. The user can select one of the empty slots to insert a number, either by pressing the number on the keyboard or selecting the number on the right panel.
4. Users can move around the grid by using arrow keys as well.
5. The game ends once the board is solved.
6. The user can create a new game by clicking the new game button on the top menu.

## Screenshots
### Original Game
![Original screenshot](https://raw.githubusercontent.com/mattnenterprise/Sudoku/master/screenshot.png)

### Improved Game

![image](https://user-images.githubusercontent.com/61302840/220392957-d2b85e32-fb35-4c34-b3fc-1259424fea4e.png)
![image](https://user-images.githubusercontent.com/61302840/220392991-0b840c70-4449-4845-805c-e12aa16d52a2.png)
![image](https://user-images.githubusercontent.com/61302840/220393059-550a1020-7461-4640-a095-3eaa8a1a0a16.png)
![image](https://user-images.githubusercontent.com/61302840/220393091-5e567f24-a7d5-4bc3-bff0-73e3d5a34071.png)
![image](https://user-images.githubusercontent.com/61302840/220393108-ece887eb-62bf-4f5e-9b16-83b827708307.png)
![image](https://user-images.githubusercontent.com/61302840/220393120-09cdd6d3-8bca-4eec-9be8-26c847806dad.png)
