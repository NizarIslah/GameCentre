package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ConnectFourScoreTest {

    @Test
    public void testCalculateUserScore() {
        ConnectFourScore cs = new ConnectFourScore();
        cs.setBoard(new Board(makeTiles()));
        assertEquals(1288, cs.calculateUserScore(10, 6));
    }

    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        return tiles;
    }
}
