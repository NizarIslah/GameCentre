package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ShogiScoreCalcTest {

    @Test
    public void testGenerateScore() {
        ShogiScore s = new ShogiScore();
        s.setBoard(new Board(makeTiles()));
        assertEquals(960, s.generateScore(13, 5));
        assertEquals(838, s.generateScore(16, 4));
        assertEquals(0, s.generateScore(800, 1));
    }

    @Test
    public void testGetPieceDiff() {
        ShogiBoardManager bm = new ShogiBoardManager(6);
        bm.setTileSelected(0);
        bm.touchMove(12);
        bm.setTileSelected(31);
        bm.touchMove(13);
        bm.setTileSelected(2);
        bm.touchMove(8);
        bm.setTileSelected(32);
        bm.touchMove(14);
        bm.setTileSelected(3);
        bm.touchMove(15);
        ShogiScore s = new ShogiScore();
        assertEquals(2, s.getPieceDiff(bm.getBoard()));
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


