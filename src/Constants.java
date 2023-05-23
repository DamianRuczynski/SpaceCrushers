public class Constants {
    //    PANEL CONFIG

    public static final int BOARD_HEIGHT = 600;
    public static final int BOARD_WIDTH = 800;

    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 5;

    public static final int GROUND_Y = 290;


    //    GAME CONFIG
    public static final int LINES_OF_ENEMIES = 3;
    public static int COLUMNS_OF_ENEMIES = 3;
    public static int NUMBER_OF_ALIENS_TO_DESTROY = COLUMNS_OF_ENEMIES * LINES_OF_ENEMIES;
    public static int GO_DOWN = 15;
    public static final int CHANCE = 5; // chance to drop the bomb by the alien
    public static final int DELAY = 17;

    public static final String FILE_PATH = "topten.txt";
    public static final int MAX_SCORES = 10;

//    ICONS CONFIG

    public static final int ALIEN_HEIGHT = 12;
    public static final int ALIEN_WIDTH = 12;
    public static final int ALIEN_INIT_X = 150;
    public static final int ALIEN_INIT_Y = 105;

    public static final int BOMB_HEIGHT = 5;
    public static final int PLAYER_WIDTH = 15;
    public static final int PLAYER_HEIGHT = 10;

    public static final String EXPLOSION_ICON = "src/images/explosion.png";

    public static final String PLAYER_ICON = "src/images/player.png";
    public static final String SHOT_ICON = "src/images/shot.png";
    public static final String BOMB_ICON = "src/images/bomb.png";
    public static final String ALIEN_ICON = "src/images/alien.png";
    public static final int PLAYER_START_X = 270;
    public static final int PLAYER_START_Y = 280;
    public static int SCORE_THRESHOLD = NUMBER_OF_ALIENS_TO_DESTROY;
    public static int DIFFICULTY_LEVEL = 1; // Default difficulty level

    public static final String GAME_RULES = "Basic gameplay: The player controls the cannon at the bottom of the screen, which can move only horizontally.\n" +
            "Alien behavior: The aliens are aligned in a rectangular formation, floating slowly in horizontal direction.\n" +
            "Scores: Each eliminated alien is worth 1 pts.\n" +
            "Completing a level: When all aliens are eliminated, the level is completed and a congratulation screen is displayed.\n" +
            "Game over: When all lives have been lost, or the aliens have invaded the planet, the game ends and a game over screen is shown.";

    public static int getDifficultyLevel() {
        return DIFFICULTY_LEVEL;
    }

    public static void setDifficultyLevel(int level) {
        DIFFICULTY_LEVEL = level;
        setAliensCols(getDifficultyLevel());
    }

    private static void setAliensCols(int level) {
        COLUMNS_OF_ENEMIES *= level;
        NUMBER_OF_ALIENS_TO_DESTROY *= level;
        GO_DOWN *= level;
        SCORE_THRESHOLD = NUMBER_OF_ALIENS_TO_DESTROY;
    }
}
