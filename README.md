# GameCentre
An Android App containing three playable board games: Sliding tiles puzzle, Hasami Shogi, and Connect4.

##### Setting up the repository
Follow these steps to set up the repository:
* Open Android Studio. Close any open projects by clicking on File -> Close Project, if applicable.
* On the welcome page of Android Studio, click "Checkout from Version Control."
* Select "Git" then enter in the URL found above. Click "clone."
* Select "Yes" when prompted to create an Android Studio project.
* On the following screen, select "Create Project From Existing Sources." Click "Next".
* Continue to click "Next" then click "Finish".
* The project should now clone.
* Should you encounter a dialog which alludes to a VCS root not being set, expand the dialog and click "Add Root."
* Should you encounter enourmous blocks of red text, go to File -> Invalidate Caches and Restart -> Invalidate Caches and restart.
* You will now be able to run the app by clicking the "run" button (green arrow) near the top right corner of your screen.
* Should you encounter issues when running the application, specifically when setting up configurations, close the project (File -> Close Project) and open the subdirectory labelled "SlidingTiles" by clicking on it. The app will now run.

#### Using the Application

* When the application is started, a login/sign-up screen will appear. Use the bottom three fields to specify a new Username and Password. 
* From the dropdown menu below, select a security question and provide the answer in the text field below. This will help you to recover your password should you ever forget it.
* In the event you should forget your password, please simply answer your security question and click on "Forgot Password?". Your password will then be displayed to you on the screen. 
* Click the sign-up button below. You will be taken to the game list where you can select a game to play.

##### Sliding Tiles
Sliding tiles is a game where one tries to arrange numbered tiles in order in the east amount of time. Players click tiles to shuffle them around in order to produce the correct order.
There are several features on the Sliding tiles start screen.
* The dropdown menu on the left will allow you to select the number of times that you can "undo" a move. Your score will be adjusted accordingly. The defualt number of allowed undos per game is 3.
* The dropdown menu on the right will allow you to select a board size. The default size is 3x3.
* The Scoreboard button will allow you to see the global high scores of several users.
* The Load Game button will allow you to load the previous in-progress game, if applicable.
* The New Game button will allow you to begin a new game with your chosen board size and undo limit.

##### Hasami Shogi 
Hasami Shogi is a Japanese board game requiring players to use strategy to capture the opponent's players. You can capture the opponent's players by surrounding the player on both sides by one of your pieces. 
There are several features on the Hasami Shogi start screen.
* The dropdown menu on the left will allow you to select the number of times that you can "undo" a move. Your score will be adjusted accordingly. The defualt number of allowed undos per game is 3.
* The dropdown menu on the right will allow you to select a board size. The default size is 6x6.
* The Player 2 username and password fields will allow a second player (opponent) to log in with their account (support for multiplayer has been implemented)=

##### Connect 4 
Connect 4 is a board game where a player must get 4 or more tiles in any consecutive order to win the game. 
There are several features on the Connect 4 start screen.
* The dropdown menu on the right will allow you to select a board size. The default size is 6x6.
* The Player 2 username and password fields will allow a second player (opponent) to log in with their account (support for multiplayer has been implemented)

Happy Playing!


##### Citations and Credits 
The following website was used for help in devising an algorithm to make sure the Board is always solvable (for SlidingTiles): 
https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html

The following StackOverflow post was used for help when attempting to create a GlobalApplication class to resolve some major issues involving Context: 
https://stackoverflow.com/questions/7144177/getting-the-application-context
