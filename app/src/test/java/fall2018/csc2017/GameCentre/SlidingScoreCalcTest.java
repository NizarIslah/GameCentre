package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class SlidingScoreCalcTest {

    @Test
    public void testCalculateUserScore() {
        SlidingScore s = new SlidingScore();
        s.setBoard(new Board(makeTiles()));
        assertEquals(350, s.calculateUserScore(10, 3));
        assertEquals(230, s.calculateUserScore(65, 4));
        assertEquals(260, s.calculateUserScore(200, 5));
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
