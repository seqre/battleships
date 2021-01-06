# Battleships

You have to write an application for playing ships over the network.

The application connects to another application, and plays the game of the ship.

### Startup parameters
The application supports the following parameters:
* `-mode [server|client]` - indicates the mode of operation (as a server - accepts the connection, as a client - establishes a connection to the server)
* `-port N` - the port which the application uses to communicate.
* `-map map-file` - path to the file containing the map with the ship layout (format described in the Map section).

### Map
* The map is a 10x10 chart, containing a description of the position of the ships.
* `.` means water, `#` means ship.
* Rows are marked with numbers from 1 to 10 (top to bottom), columns with letters A to J (left to right). 
* The sheet should contain:
  * 4 ships of size 1, 
  * 3 ships of size 2,
  * 2 ships of size 3,
  * 1 ship size 4.
* Ships of size 3-4 can be "broken", but the individual segments must connect with at least one side.
* Two ships cannot contact each other (also diagonally).

Example map below:
```
..#.......
#......#..
#..#......
..##......
......##..
.##.......
.........#
..##...#..
.##....#.#
.......#..
```
Explanation: on this map, the 1-mast ships are in position: C1, H2, J7 and J9.

### Communication protocol
* Communication is done using TCP protocol, with UTF-8 encoding.
* The client and the server alternately send each other a _message_, which consists of 2 parts: _commands_ and _coordinates_, separated by a `;` character, and terminated by an end of line character `\n`.
  * The message format: `command;coordinates\n`.
  * Example of a message: `pudło;D6\n`.
* Commands and their meaning:
  * _start_
    * command to initiate the game. 
    * The client sends it only once, at the beginning.
    * Example: `start;A1\n`.
  * _pudło_
    * answer sent when there is no ship under the coordinates provided by the other side.
    * Example: `pudło;A1\n`.
  * _trafiony_
    * the message sent when there is a ship under the coordinates provided from the other side, and this is not its last missed segment so far.
    * Example: `trafiony;A1\n`.
  * _trafiony zatopiony_
    * the story sent when there is a ship under the coordinates received from the other side, and the last missed segment of that ship was hit.
    * Example: `trafiony zatopiony;A1\n`.
  * _ostatni zatopiony_
    * the story sent when there is a ship at the coordinates received from the other side, and the last missed ship segment of the entire fleet in this game was hit.
    * This is the last command in the game. The sending side loses it.
    * This command does not give the coordinates of the shot (there is no one to shoot anymore!). 
    * Example: `ostatni zatopiony\n`.
* It is possible (although strategically unreasonable) to shoot multiple times in the same place. You should then respond according to the current state of the board:
  * `pudło` in case of a box,
  * `trafiony`when the ship was already hit, but is not yet sunk,
  * `trafiony zatopiony` when the ship is already sunk.
* Error handling:
  * If you receive an incomprehensible command or after 1 second of waiting, you must resend your last message. 
  * After 3 unsuccessful attempts, you should display the message `Błąd komunikacji` and terminate the application.

### Application Operation
* When started (in any mode), the application should display its map.
* During operation, the application should display all sent and received messages.
* After the game, the application should display:
  * `Wygrana\n` in case of a win, or `Przegrana\n` in case of a loss,
  * In case of a win - full map of the opponent,
  * In case of a loss, the map of the opponent, with the replacement of unknown fields with the `?` character. _Note_: the fields adjacent to the sunken ship are to be considered uncovered (there must be no other ship on them).
  * Blank line
  * Your map, with additional markings: `~` - the opponent's boxes, `@` - the opponent's targeted shots.

An example of an opponent's map from a lost session:
```
..#..??.?.
#.????.#..
#....??...
..##....?.
?.....##..
??#??.....
..?......#
..##...#..
.##....#.#
.......#..
```

An example of your map after the game (winning - not all sunken ships):
```
~~@~~.~~~.
@..~.~.@.~
#.~#..~.~.
..##..~..~
..~.~.@@..
.#@~..~...
.~.~.~.~.@
~.##.~.#~~
.##~..~~~~
..~.~.~~~.
```
