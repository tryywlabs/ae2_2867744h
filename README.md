_CONNECT 4_

This file outlines:

- The extension feature,
- Methods of each class (excluding getters and setters, except for setSym() and setRow())
- Main method logic flow

---

EXTENSION FEATURE: CONFIRM TURN confTurn(Disc disc, Scanner scanner)
As part of the 5th Criteria, I've implemented the Confirm Turn method for each player turn.

When a player makes a move, the move is denoted in the grid with the character '#' instead of 'O' or 'X'. The original symbol is stored temporarily in the variable 'oriSym'.

The temporary replacement allows the player to recognise more easily which disc they have just inserted.

It then prompts the user to confirm if they would like to proceed with their move by typing 'Y', or "Yes", 'N', or "No" case-insensitively.

The ArrayList, apart from the 'currentDisc' variable in the main method, was implemented for later scalability and keep a better track of the most recent disc, where the most recent insertion would be ArrayList[n-1].

When the response is no/n, the most recent disc is deleted from the ArrayList of inserted discs and the grid. It would skip the 'takeTurn()' condition for both players, resulting in a repeated turn for the same player.

When the response is yes/y, the disc object's symbol is set to that of the player ('O' or 'X'), the player turn is alternates and the game moves on for the next player to take their turn.

The method confTurn() is included in the grid class, since it manipulates the disc insertion and deletion of the grid object.

---

PLAYER CLASS METHODS:

endTurn(): alternates the initial isTurn value for player (true -> false || false -> true).

takeTurn(Scanner scanner): Initialises and returns a disc object for the player turn. Symbol is set to player's symbol value, column is set to user input.

---

DISC CLASS METHDOS:

setSym(char x): sets symbol value for disc. Used as part of the extension feature (temporarily sets symbol to '#)

setRow(int row): sets row value of disc when dropDisc() is called.

---

GRID CLASS METHODS:

dropDisc(Disc disc): takes in a disc object, gets column value, inserts disc into the "lowest" possible row in grid by looping until current index is null or row == 0. Begins loop from bottom of grid and proceeds up (row--)

winHorizontal(Disc disc): Checks horizontal winning condition by iterating through the adjacent columns. First loops left side of most recently dropped disc, stops when character value is different or counter >= 4 (winning condition achieved). Second loop does the same for the right side, adding onto the counter value from the left side - 1.

winVertical(Disc disc): Checks vertical winning condition by iterating downwards from most recent disc.

winDiagonal(Disc disc): Checks win conditions for \ diagonal and / diagonal. Each checks the left-hand side of the diagonal first (iterating left up for \ and left down for /), then the right-hand side (iterating right down for \ and right up for /). Each diagonal type is treated separately by re-setting the counter value back to 0.

checkFull(): Iterates through entire grid and returns false immediately when index is null. Otherwise returns true. An ending condition for the game.

toString(): Overrides the toString method, prints the grid and returns StringBuilder.toString of

---

SIMPLIFIED LOGIC FLOW OF MAIN METHOD

1. Initialised Grid, Scanner and 2 Player objects

--START OF MAIN LOOP--

2. Sets currentPlayer as player object where isTurn is true.

3. Call takeTurn() to initialise a Disc object, call dropDisc() to drop disc into grid. To progress, both takeTurn() and dropDisc() must be completed successfully.

4. After each turn, check for winning conditions. If any one of the conditions returns true print out the winner, break out of the main loop.

5. Check for full grid. If checkFull() returns true, print a tie message. Main loop will be exited automatically, as it is the condition for the loop.

6. ADDITIONAL FEATURE: Check if player wishes to redo their move by calling confTurn() at the end. Note that this will not be called if checkFull() == true, or any of the winning conditions are met.

7. If player wishes to redo their move, don't switch turns. If player didn't redo, switch turns

--END OF MAIN LOOP--

8. Scanner object is closed, programme ends.
