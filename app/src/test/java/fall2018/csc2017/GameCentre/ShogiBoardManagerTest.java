package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShogiBoardManagerTest {

    private int[] tilesSelected;
    private int[] targetTiles;

    @Test
    public void testPuzzleSolved() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertFalse(bm.puzzleSolved());
        bm = makeMidGameBoard();
        assertFalse(bm.puzzleSolved());
        bm = makeOneMoveAwayforRed();
        assertFalse(bm.puzzleSolved());
        bm = makeRedIsWinner();
        assertTrue(bm.puzzleSolved());
        bm = makeOneMoveAwayforBlack();
        assertFalse(bm.puzzleSolved());
        bm = makeBlackIsWinner();
        assertTrue(bm.puzzleSolved());
    }

    @Test
    public void testIsValidTap() {
        ShogiBoardManager bm = makeOneMoveBoard();
        bm.setTileSelected(48);
        assertFalse(bm.isValidTap(6));//move red onto black
        bm.setTileSelected(42);
        assertTrue(bm.isValidTap(43));//move red onto another red
        bm.setTileSelected(43);
        assertFalse(bm.isValidTap(37));//red diagonal
        bm.setTileSelected(46);
        assertTrue(bm.isValidTap(39));//red moving up.
        bm = makeMidGameBoard();
        bm.setTileSelected(43);
        assertTrue(bm.isValidTap(36));
    }

    @Test
    public void testIsBlack() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertFalse(bm.isBlack(6, 0));//Red tile
        assertFalse(bm.isBlack(5, 3)); //Blank tile
        assertTrue(bm.isBlack(3, 1));//Moved black tile
        assertTrue(bm.isBlack(0, 0));//Black tile that did not move
    }

    @Test
    public void testIsRed() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertFalse(bm.isRed(3, 1));//Black tile
        assertFalse(bm.isRed(2, 0));//Blank tile
        assertTrue(bm.isRed(6, 6)); //Red tile

    }

    @Test
    public void testInSameRow() {
        ShogiBoardManager bm = makeMidGameBoard();
        assertFalse(bm.inSameRow(0, 7));
        assertFalse(bm.inSameRow(0, 48));
        assertTrue(bm.inSameRow(14, 16));
    }

    @Test
    public void testInSameCol() {
        ShogiBoardManager bm = makeOneMoveAwayforRed();
        assertFalse(bm.inSameCol(0, 8));
        assertTrue(bm.inSameCol(15, 22));
        assertFalse(bm.inSameCol(1, 2));
    }

    @Test
    public void testTileBlockingRow() {
        ShogiBoardManager bm = makeOneMoveBoard();
        assertFalse(bm.tileBlockingRow(22, 27));//row only has 1 tile
        assertTrue(bm.tileBlockingRow(42, 47));//row has multiple tiles
        bm.setTileSelected(44);
        bm.touchMove(23);
        assertTrue(bm.tileBlockingRow(22, 24));//Try to go on top of a tile
    }

    @Test
    public void testTileBlockingCol() {
        ShogiBoardManager bm = new ShogiBoardManager(makeOneMoveBoard().getBoard());
        assertFalse(bm.tileBlockingCol(42, 7));//way is clear
        assertTrue(bm.tileBlockingCol(43, 15));//Try to jump over another tile

    }

    @Test
    public void testGetChanged() {
        ShogiBoardManager bm = makeMidGameBoard();
        bm.setTileSelected(-1);
        bm.touchMove(30);
        assertFalse(bm.getChanged());
        bm.setTileSelected(43);
        bm.touchMove(35);
        assertFalse(bm.getChanged());
        bm = makeOneMoveBoard();
        setTilesSelected(new int[]{44, 3, 48, 24, 42, 6, 43, 1, 44, 5, 14});
        setTargetTiles(new int[]{23, 24, 20, 27, 14, 13, 44, 8, 23, 12, 21});
        makeMoves(bm);
        assertTrue(bm.getChanged());
        bm = makeOneMoveBoard();
        setTilesSelected(new int[]{48, 22, 47, 0, 33});
        setTargetTiles(new int[]{20, 27, 33, 7, 34});
        makeMoves(bm);
        assertTrue(bm.getChanged());
    }

    @Test
    public void testSetTileToMove() {
        ShogiBoardManager bm = makeOneMoveAwayforBlack();
        bm.setTileSelected(33);
        assertFalse(bm.setTileToMove(35, bm.getBoard().getTile(5, 5)));
    }

    private ShogiBoardManager makeOneMoveBoard() {
        ShogiBoardManager bm = new ShogiBoardManager(7);
        bm.setTileSelected(1);
        bm.touchMove(22);
        return bm;
    }

    private ShogiBoardManager makeMidGameBoard() {
        ShogiBoardManager bm = new ShogiBoardManager(7);
        setTilesSelected(new int[]{1, 46, 2, 42, 3, 48, 4});
        setTargetTiles(new int[]{22, 32, 23, 21, 24, 41, 25});
        return makeMoves(bm);
    }

    private ShogiBoardManager makeOneMoveAwayforRed() {
        ShogiBoardManager bm = new ShogiBoardManager(7);
        setTilesSelected(new int[]{1, 46, 2, 42, 3, 48, 4, 43, 5, 41, 6, 44, 1});
        setTargetTiles(new int[]{22, 32, 23, 21, 24, 41, 25, 36, 26, 27, 1, 43, 29});
        return makeMoves(bm);
        //bm.touchMove(21, 22); <-- Winning move for red
    }

    private ShogiBoardManager makeRedIsWinner() {
        ShogiBoardManager bm = makeOneMoveAwayforRed();
        bm.setTileSelected(21);
        bm.touchMove(22);
        return bm;
    }

    private ShogiBoardManager makeOneMoveAwayforBlack() {
        ShogiBoardManager bm = new ShogiBoardManager(6);
        setTilesSelected(new int[]{4, 33, 0, 31, 3, 32, 6, 34, 9, 30, 1, 24});
        setTargetTiles(new int[]{16, 15, 6, 13, 9, 14, 12, 22, 33, 24, 19, 28});
        return makeMoves(bm);
    }

    private ShogiBoardManager makeBlackIsWinner() {
        ShogiBoardManager bm = makeOneMoveAwayforBlack();
        bm.setTileSelected(33);
        bm.touchMove(34);
        return bm;
    }

    private ShogiBoardManager makeMoves(ShogiBoardManager bm) {
        for (int i = 0; i < tilesSelected.length; i++) {
            bm.setTileSelected(tilesSelected[i]);
            bm.touchMove(targetTiles[i]);
        }
        return bm;
    }

    private void setTilesSelected(int[] tilesSelected) {
        this.tilesSelected = tilesSelected;
    }

    private void setTargetTiles(int[] targetTiles) {
        this.targetTiles = targetTiles;
    }
}