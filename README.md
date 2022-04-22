# Pockball
Project made for TDT4240

## Project Structure
```
├── android                                             # Android-specific files
│   └── java
│       ├── com.pockball.pockball
│       │   ├── firebase
│       │   │   └── FirebaseService.java                # Handles communication with the server
│       │   └── AndroidLauncher.java                    # Launches the game on Android
│       └── assets                                      # Contains all textures, sounds and fonts
│
├── core                                                # Game code
│   └── java
│       └── com.pockball.pockball
│           ├── assets
│           │   ├── AssetsController.java               # Handles UI assets like skins and fonts
│           │   └── SoundController.java                # Handles the sound effects
│           ├── db_models
│           │   ├── BallTypeModel.java
│           │   ├── EventModel.java
│           │   ├── PlaceBallEvent.java
│           │   ├── PlayerModel.java
│           │   ├── RoomModel.java
│           │   └── ShotEvent.java
│           ├── ecs                                     # Entity-component-system
│           │   ├── components                          # Contains components storing data for entities
│           │   ├── entities                            # Contains factories for creating entities
│           │   ├── systems                             # Contains systems that act upon components
│           │   ├── types                               # Contains types used by the entities
│           │   └── Engine.java                         # The engine that runs the entity-component-system
│           ├── firebase
│           │   ├── FirebaseController.java             # Uses a FirebaseService to connect to server
│           │   └── FirebaseInterface.java              # Interface for communicating with server
│           ├── game_mode
│           │   ├── GameModeContext.java                # Contains the game mode state
│           │   ├── MultiPlayerGameMode.java            # State for multiplayer game mode
│           │   ├── SinglePlayerGameMode.java           # State for singleplayer game mode
│           │   └── GameMode.java                       # Interface for the game mode states
│           ├── screens
│           │   ├── create_game_room
│           │   │   ├── CreateGameRoomController.java   # MVC screen controller
│           │   │   └── CreateGameRoomView.java         # MVC screen view
│           │   ├── gameover
│           │   ├── join_game_room
│           │   ├── main_menu
│           │   ├── multiplayer
│           │   ├── settings
│           │   ├── singleplayer    
│           │   ├── tutorial
│           │   ├── won
│           │   ├── GameController.java                 # Handles local game state
│           │   ├── ScreenController.java               # Handles changing scenes
│           │   ├── ScreenModel.java                    # Contains enum of screen types
│           │   └── ScreenView.java                     # Abstract superclass for all screens
│           └── PockBall.java
│
└── desktop                                             # Desktop-specific code
    └── com.pockball.pockball.desktop
        ├── firebase
        │   └── FirebaseService.java                    # Dummy service
        └── DesktopLauncher.java                        # Launches the game on Android
```

## Compile & Run

These are the different ways to run the application:

### Compiling .apk
1. In Android Studio, click the *Build* tab at the top of the window.
2. Then click *Build Bundle(s) / APK(s) --> Build APK(s)*
3. Find the compiled .apk file in *\pockball\android\build\outputs\apk\debug*
4. Run it as described in the next section

### Running pockball.apk
1. Download the pockball.apk file on your android phone
2. Install the application
3. Open the application on your phone

### Emulated in Android Studio
1. Create an Android emulator
2. Select to run with *android* configuration
3. Run the game

If you want to try the multiplayer version on your local computer you need to create two different emulators, and click *Select Multiple Devices...*.

### Desktop version in Android Studio
1. Select to run with *desktop* configuration
2. Run the game

*__Note:__ The desktop version does not work with multiplayer, and will crash if you try to create a game room.*