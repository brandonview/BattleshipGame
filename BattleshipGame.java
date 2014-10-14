import java.util.Scanner;


enum TileValue {
    HIT, MISS, BLANK, UNHIT
}

class Tile {

    private TileValue value;

    private int x;
    private int y;


    //method to display the game tile

    public void display() {
        switch (value) {
            case HIT:
                System.out.print("X ");
                break;
            case MISS:
                System.out.print("0 ");
                break;
            case BLANK:
                System.out.print("- ");
                break;
            case UNHIT:
                System.out.print("I ");
        }
    }


    //Method to show an UNHIT ship to the opponent as BLANK
    public void hideShips() {
        if (value == TileValue.UNHIT) {
            System.out.print("- ");
        }
        //if this method is called on a tile that is not UNHIT, then it will simply display
        else {
            this.display();
        }
    }


    //Setters and getters

    public void setValue(TileValue value) {
        if (value == TileValue.HIT || value == TileValue.MISS || value == TileValue.BLANK || value == TileValue.UNHIT) {
            this.value = value;
        }
    }

    public TileValue getValue()

    {
        return value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

}


enum BattleshipType {
    AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, DESTROYER, PATROL
}

enum BattleshipDirection {
    UP, DOWN, LEFT, RIGHT
}


class Battleship {

    private int startX;
    private int startY;
    private BattleshipType type;
    private int length;
    private BattleshipDirection direction;


    //Setters and getters

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getLength() {
        return length;
    }

    //This method should ONLY be called from within setType method, as the length is always determined by the type
    public void setLength(BattleshipType type) {
        switch (type) {
            case AIRCRAFT_CARRIER:
                length = 5;
                break;
            case BATTLESHIP:
                length = 4;
                break;
            case SUBMARINE:
                length = 3;
                break;
            case DESTROYER:
                length = 3;
                break;
            case PATROL:
                length = 2;
                break;
        }
    }

    public BattleshipType getType() {
        return type;
    }

    public void setType(BattleshipType type) {
        if (type == BattleshipType.AIRCRAFT_CARRIER || type == BattleshipType.BATTLESHIP || type == BattleshipType.SUBMARINE || type == BattleshipType.DESTROYER || type == BattleshipType.PATROL) {
            this.type = type;
            //sets the length of the Battleship immediately after setting the type
            setLength(type);
        }
    }

    public void setDirection(BattleshipDirection direction) {
        if (direction == BattleshipDirection.UP || direction == BattleshipDirection.DOWN || direction == BattleshipDirection.LEFT || direction == BattleshipDirection.RIGHT) {
            this.direction = direction;
        }
    }

    public BattleshipDirection getDirection() {
        return direction;
    }

    //Will print out the name of the type of ship
    public void displayType() {
        switch (type) {
            case AIRCRAFT_CARRIER:
                System.out.print("Aircraft Carrier");
                break;
            case BATTLESHIP:
                System.out.print("Battleship");
                break;
            case DESTROYER:
                System.out.print("Destroyer");
                break;
            case SUBMARINE:
                System.out.print("Submarine");
                break;
            case PATROL:
                System.out.print("Patrol Boat");
                break;
        }
    }


}


class GameBoard {

    private int width = 10;
    private int height = 10;
    private int numOfShips = 5;

    //Create 2-D array containing all of the game tiles
    private Tile[][] gameTiles = new Tile[width][height];

    //Array that contains the 4 Battleships
    private Battleship[] battleships = new Battleship[numOfShips];


    //getter for the number of ships
    public int getNumOfShips() {
        return numOfShips;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    //getter for the battleships[] array
    public Battleship[] getBattleships() {
        return battleships;
    }

    //getters for height and width
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    //method used to set a single game tile. Takes args of x-coordinate, y-coordinate, and a value for the tile
    public void setGameTile(int x, int y, TileValue value) {
        gameTiles[x][y].setX(x);
        gameTiles[x][y].setY(y);
        gameTiles[x][y].setValue(value);
    }

    public TileValue getTileValue(int x, int y) {
        return gameTiles[x][y].getValue();
    }

    public void setBlankBoard() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameTiles[x][y] = new Tile();
                gameTiles[x][y].setX(x);
                gameTiles[x][y].setY(y);
                gameTiles[x][y].setValue(TileValue.BLANK);
            }
        }
    }

    //Will create new battleship objects in the array battleships[]
    //These battleships only have the name set. Another method will be used to get user information for each battleship
    public void makeBattleships() {
        //Initialize all the elements of the battleships array
        for (int i = 0; i < numOfShips; i++) {
            battleships[i] = new Battleship();
        }

        //set the elements of battleships array to be one of each type
        //There might be a better way to do this
        battleships[0].setType(BattleshipType.AIRCRAFT_CARRIER);
        battleships[1].setType(BattleshipType.BATTLESHIP);
        battleships[2].setType(BattleshipType.SUBMARINE);
        battleships[3].setType(BattleshipType.DESTROYER);
        battleships[4].setType(BattleshipType.PATROL);

    }


    //Will assign UNHIT to appropriate tile values based on the battleship starting point and the direction they point in
    //Uses variable "i" as a counter to determine how many squares to change to UNHIT
    public void placeBattleship(Battleship battleship) {

        switch (battleship.getDirection()) {
            case UP:
                for (int i = 0; i < battleship.getLength(); i++) {
                    gameTiles[battleship.getStartX()][battleship.getStartY() - i].setValue(TileValue.UNHIT);
                }
                break;

            case DOWN:
                for (int i = 0; i < battleship.getLength(); i++) {
                    gameTiles[battleship.getStartX()][battleship.getStartY() + i].setValue(TileValue.UNHIT);
                }
                break;

            case RIGHT:
                for (int i = 0; i < battleship.getLength(); i++) {
                    gameTiles[battleship.getStartX() + i][battleship.getStartY()].setValue(TileValue.UNHIT);
                }
                break;

            case LEFT:
                for (int i = 0; i < battleship.getLength(); i++) {
                    gameTiles[battleship.getStartX() - i][battleship.getStartY()].setValue(TileValue.UNHIT);
                }
                break;
        }

    }


    //I needed this function to deal with Java throwing an exception if anything other than an int was given as user input for nextInt()
    public static int getUserInt() {
        Scanner userInput = new Scanner(System.in);

        if (userInput.hasNextInt()) {
            return userInput.nextInt();
        } else {
            return -1;
        }
    }


    //Method that prompts a user for information of all battleships in the battleships[] array and sets variables accordingly
    public void humanSetBattleships() {
        Scanner userInput = new Scanner(System.in);

        for (int i = 0; i < numOfShips; i++) {
            int userInputX = -1;
            int userInputY = -1;
            String userInputDirection = "default value";
            boolean hasValidData = false;

            do {
                hasValidData = true;
                System.out.print("\nSet information for your ");
                battleships[i].displayType();
                System.out.print("\n");

                System.out.print("Starting x-coordinate: ");
                userInputX = GameBoard.getUserInt();

                if (userInputX > width - 1 || userInputX < 0) {
                    System.out.println("Invalid input for x-coordinate. Please re-enter information\n");
                    hasValidData = false;
                    continue;
                }

                System.out.print("Starting y-coordinate: ");
                userInputY = GameBoard.getUserInt();

                if (userInputY > height - 1 || userInputY < 0) {
                    System.out.println("Invalid input for y-coordinate. Please re-enter information\n");
                    hasValidData = false;
                    continue;
                }

                System.out.print("Direction to place battleship: ");
                userInputDirection = userInput.next();

                if (!userInputDirection.equals("UP") && !userInputDirection.equals("DOWN") && !userInputDirection.equals("LEFT") && !userInputDirection.equals("RIGHT")) {
                    System.out.println("\nInvalid input for direction. The direction can be either UP, DOWN, LEFT, or RIGHT. Please re-enter information\n");
                    hasValidData = false;
                    continue;
                }


                //Don't allow users to put battleships in places that cause them to hang off the board
                //Also checks to see if there is anything already in the space that the user is trying to place the battleship
                switch (userInputDirection) {

                    case "UP":
                        if ((userInputY - battleships[i].getLength()) < 0) {
                            System.out.println("That won't fit there! Please re-enter information\n");
                            hasValidData = false;
                            continue;
                        }

                        for (int j = 0; j < battleships[i].getLength(); j++) {
                            if (gameTiles[userInputX][userInputY - j].getValue() != TileValue.BLANK) {
                                hasValidData = false;
                                System.out.println("This placement overlaps with another Battleship. Please re-enter information\n");
                                break;
                            }
                        }
                        break;

                    case "DOWN":
                        if ((userInputY + battleships[i].getLength()) > height) {
                            System.out.println("That won't fit there! Please re-enter information\n");
                            hasValidData = false;
                            continue;
                        }
                        for (int j = 0; j < battleships[i].getLength(); j++) {
                            if (gameTiles[userInputX][userInputY + j].getValue() != TileValue.BLANK) {
                                hasValidData = false;
                                System.out.println("This placement overlaps with another Battleship. Please re-enter information\n");
                                break;
                            }
                        }
                        break;

                    case "LEFT":
                        if ((userInputX - battleships[i].getLength()) < 0) {
                            System.out.println("That won't fit there! Please re-enter information\n");
                            hasValidData = false;
                            continue;
                        }
                        for (int j = 0; j < battleships[i].getLength(); j++) {
                            if (gameTiles[userInputX - j][userInputY].getValue() != TileValue.BLANK) {
                                hasValidData = false;
                                System.out.println("This placement overlaps with another Battleship. Please re-enter information\n");
                                break;
                            }
                        }
                        break;

                    case "RIGHT":
                        if ((userInputX + battleships[i].getLength()) > width) {
                            System.out.println("That won't fit there! Please re-enter information\n");
                            hasValidData = false;
                            continue;
                        }
                        for (int j = 0; j < battleships[i].getLength(); j++) {
                            if (gameTiles[userInputX + j][userInputY].getValue() != TileValue.BLANK) {
                                hasValidData = false;
                                System.out.println("This placement overlaps with another Battleship at tile. Please re-enter information");
                                break;
                            }
                        }
                        break;
                }

                //if the user has entered valid data, set the values for the ship in the array based on the direction that the user entered, and then place the ship on the board
                if (hasValidData) {
                    battleships[i].setStartX(userInputX);
                    battleships[i].setStartY(userInputY);

                    switch (userInputDirection) {
                        case "UP":
                            battleships[i].setDirection(BattleshipDirection.UP);
                            break;
                        case "DOWN":
                            battleships[i].setDirection(BattleshipDirection.DOWN);
                            break;
                        case "LEFT":
                            battleships[i].setDirection(BattleshipDirection.LEFT);
                            break;
                        case "RIGHT":
                            battleships[i].setDirection(BattleshipDirection.RIGHT);
                            break;
                    }

                    this.placeBattleship(battleships[i]);
                }

            } while (!hasValidData);
        }
    }


    //method to display the gameboard for the player that owns it. Will display by row according to for loop structure. Will show EVERYTHING
    public void displayForSelf() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9 \n");
        int rowCounter = 0;
        for (int y = 0; y < height; y++) {

            System.out.print(rowCounter + "  ");

            for (int x = 0; x < width; x++) {
                gameTiles[x][y].display();
            }
            //make a line break at the end of every row
            System.out.print("\n");

            rowCounter++;
        }
    }

    //Method to display gameboard for opponent. Will only show what he has hit or missed. Unhit tiles will appear as blank
    public void displayForOpponent() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9 \n");
        int rowCounter = 0;
        for (int y = 0; y < height; y++) {

            System.out.print(rowCounter + "  ");

            for (int x = 0; x < width; x++) {
                gameTiles[x][y].hideShips();
            }
            //make a line break at the end of every row
            System.out.print("\n");

            rowCounter++;
        }
    }

}


class Player {
    private GameBoard gameBoard = new GameBoard();

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setupGameBoard() {
        gameBoard.setBlankBoard();
        gameBoard.displayForSelf();
        System.out.println("\n\n\n\n");
        gameBoard.makeBattleships();
        gameBoard.humanSetBattleships();
        System.out.println("\n\n\n\n");
        gameBoard.displayForSelf();
    }

    public static void hideLastMove() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Seriously, don't be a jerk");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Stop scrolling up");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static void fire(GameBoard attackBoard) {
        int xCoordinate = -1;
        int yCoordinate = -1;
        boolean hasValidData = true;


        //get user input for the x and y coordinate of the tile to fire upon
        do {
            hasValidData = true;
            System.out.println("Specify an x-coordinate to fire upon!");
            xCoordinate = GameBoard.getUserInt();

            if (xCoordinate > attackBoard.getWidth() - 1 || xCoordinate < 0) {
                System.out.println("Invalid input for x-coordinate. Please re-enter information\n");
                hasValidData = false;
                continue;
            }

            System.out.println("Specify a y-coordinate to fire upon!");
            yCoordinate = GameBoard.getUserInt();

            if (yCoordinate > attackBoard.getHeight() - 1 || yCoordinate < 0) {
                System.out.println("Invalid input for y-coordinate. Please re-enter information\n");
                hasValidData = false;
                continue;
            }
        } while (!hasValidData);

        //check to see if you hit something
        switch (attackBoard.getGameTiles()[xCoordinate][yCoordinate].getValue()) {
            case UNHIT:
                System.out.print("Direct hit!\n");
                attackBoard.setGameTile(xCoordinate, yCoordinate, TileValue.HIT);
                break;
            case HIT:
                System.out.println("You already hit that spot! Way to waste your turn, idiot\n");
                break;
            case MISS:
                System.out.println("You have now missed firing on this spot multiple times. What a stupid\n");
                break;
            case BLANK:
                System.out.println("Miss!\n");
                attackBoard.setGameTile(xCoordinate, yCoordinate, TileValue.MISS);
                break;
        }
    }


    public void takeTurn(GameBoard opponentBoard) {
        System.out.println("\n\n\n\n YOUR BOARD");
        gameBoard.displayForSelf();
        System.out.println("\n\n\n\n OPPONENT'S BOARD");
        opponentBoard.displayForOpponent();
        System.out.println("\n\n\n\n");
        Player.fire(opponentBoard);

    }
}


public class BattleshipGame {
    public static boolean checkForVictory(GameBoard opponentBoard) {
        for (int y = 0; y < opponentBoard.getHeight(); y++) {
            for (int x = 0; x < opponentBoard.getWidth(); x++) {
                if (opponentBoard.getTileValue(x, y) == TileValue.UNHIT) ;
                {
                    return false;
                }
            }

        }

        return true;
    }

    public static void confirmNextTurn() {
        Scanner nextTurnInput = new Scanner(System.in);

        System.out.println("\n\n\n\nPress ENTER to end your turn and continue to opponents turn\n\n\n\n");
        nextTurnInput.nextLine();
        Player.hideLastMove();
        System.out.println("\n\n\n\nPress ENTER to begin your turn\n\n\n\n");
        nextTurnInput.nextLine();
    }

    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();

        boolean gameHasBeenWon = false;

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        player1.setupGameBoard();
        BattleshipGame.confirmNextTurn();
        player2.setupGameBoard();
        BattleshipGame.confirmNextTurn();

        while (!gameHasBeenWon) {

            player1.takeTurn(player2.getGameBoard());

            if (BattleshipGame.checkForVictory(player2.getGameBoard())) {
                gameHasBeenWon = true;
                System.out.println("\n\nPLAYER 1 HAS WON THE GAME!! CONGRATULATIONS PLAYER 1\n\n");
            }

            BattleshipGame.confirmNextTurn();

            player2.takeTurn(player1.getGameBoard());

            if (BattleshipGame.checkForVictory(player1.getGameBoard())) {
                gameHasBeenWon = true;
                System.out.println("\n\nPLAYER 2 HAS WON THE GAME!! CONGRATULATIONS PLAYER 2\n\n");
            }

            BattleshipGame.confirmNextTurn();
        }
    }

}
