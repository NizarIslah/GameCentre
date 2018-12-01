package fall2018.csc2017.GameCentre;

import org.junit.Test;


import static org.junit.Assert.*;

public class ConnectFourBoardManagerTest {

    @Test
    public void testGetBoard() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertNotNull(bm.getBoard());
    }

    @Test
    public void testSwitchPlayer() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        int currPlayer = bm.getCurrentPlayer(18);
        bm.touchMove(12);
        int newPlayer = bm.getCurrentPlayer(12);
        assertNotEquals(currPlayer, newPlayer);
        bm.touchMove(6);
        int newnewPlayer = bm.getCurrentPlayer(6);
        assertEquals(currPlayer, newnewPlayer);
    }

    @Test
    public void testCheckDiagonals() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        bm.touchMove(12);
        assertFalse(bm.checkDiagonals(12));
        bm = makeRedWinDiagonalBoard();
        assertTrue(bm.checkDiagonals(15));
        bm = makeRedWinVerticalBoard();
        assertTrue(bm.checkDiagonals(13));
    }

    @Test
    public void testCheckSides() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertFalse(bm.checkSides(18));//Empty on right side
        assertFalse(bm.checkSides(26));//Various colors on the right
        bm = makeRedWinVerticalBoard();
        assertFalse(bm.checkSides(13));//Blanks on either side
        bm = makeRedWinHorizontalBoard();
        assertTrue(bm.checkSides(33));
    }

    @Test
    public void testCheckUnder() {
        ConnectFourBoardManager bm = makeRedWinVerticalBoard();
        assertTrue(bm.checkUnder(13));
        assertFalse(bm.checkUnder(14));
    }

    @Test
    public void testIsValidTap() {
        ConnectFourBoardManager bm = makeEarlyBoard();
        assertFalse(bm.isValidTap(30));//Already occupied position
        assertFalse(bm.isValidTap(0));//Too high
        assertTrue(bm.isValidTap(12));//Just right
    }

    @Test
    public void testCheckEmptyTile() {
        ConnectFourBoardManager bm = makeRedWinVerticalBoard();
        assertTrue(bm.checkEmptyTile(12));//Empty tile directly above another
        assertTrue(bm.checkEmptyTile(5));//Empty tile in middle of nowhere
        assertFalse(bm.checkEmptyTile(20));//Red tile
        assertFalse(bm.checkEmptyTile(32));//Black tile
    }

    @Test
    public void testCheckUnderneath() {
        ConnectFourBoardManager bm = makeRedOneMoveAwayBoard();
        assertFalse(bm.checkUnderneath(0));//Not underneath
        assertTrue(bm.checkUnderneath(12));//Is underneath
    }

    @Test
    public void testGameDrawn() {
        ConnectFourBoardManager bm = makeRedWinDiagonalBoard();
        assertFalse(bm.gameDrawn());
        bm = makeEarlyBoard();
        assertFalse(bm.gameDrawn());
    }

    private ConnectFourBoardManager makeEarlyBoard() {
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        return makeMoves(bm, new int[]{30, 24, 31, 18});
    }

    private ConnectFourBoardManager makeRedOneMoveAwayBoard() {
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        return makeMoves(bm, new int[]{30, 24, 31, 18, 25, 32, 34, 26, 20, 33, 27, 21, 19});
    }

    private ConnectFourBoardManager makeRedWinDiagonalBoard() {
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        return makeMoves(bm, new int[]{30, 24, 31, 18, 25, 32, 34, 26, 20, 33, 27, 21, 19, 35, 15});
    }

    private ConnectFourBoardManager makeRedWinVerticalBoard() {
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        return makeMoves(bm, new int[]{30, 24, 31, 18, 25, 32, 34, 26, 20, 33, 27, 21, 19, 35, 13});
    }

    private ConnectFourBoardManager makeRedWinHorizontalBoard() {
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        return makeMoves(bm, new int[]{30, 24, 31, 25, 32, 26, 33});
    }

    private ConnectFourBoardManager makeMoves(ConnectFourBoardManager bm, int[] moves) {
        for (int move : moves) {
            bm.touchMove(move);
        }
        return bm;
    }
}