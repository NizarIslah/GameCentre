package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BoardTest {

    @Test
    public void testNumReds() {
        // TWO REDS AND 6 BLACKS
        int[] fromMoves = new int[]{0, 31, 1, 32, 2, 33, 3, 34, 5};
        int[] toMoves = new int[]{12, 13, 0, 14, 1, 15, 2, 16, 17};
        ShogiBoardManager bm = makeBoard(fromMoves, toMoves);
        assertEquals(bm.getBoard().numReds(), 2);

        // 4 BLACKS AND 6 REDS
        int[] fromMoves2 = new int[]{1, 30, 3, 32, 5, 34};
        int[] toMoves2 = new int[]{13, 12, 15, 14, 29, 16};
        ShogiBoardManager bm2 = makeBoard(fromMoves2, toMoves2);
        assertEquals(bm2.getBoard().numReds(), 6);

        // A CONTINUATION OF ABOVE, 4 BLACKS AND 2 REDS
        ShogiBoardManager bm3 = makeBoard(new int[]{1, 30, 3, 32, 5, 34, 29, 33, 0, 12, 6}, new int[]{13, 12, 15, 14, 29, 16, 17, 15, 6, 13, 12});
        assertEquals(bm3.getBoard().numReds(), 2);

    }

    // ====================== HELPER FUNCTIONS ================================

    @Test
    public void testNumBlacks() {
        // TWO REDS AND 6 BLACKS
        int[] fromMoves = new int[]{0, 31, 1, 32, 2, 33, 3, 34, 5};
        int[] toMoves = new int[]{12, 13, 0, 14, 1, 15, 2, 16, 17};
        ShogiBoardManager bm = makeBoard(fromMoves, toMoves);
        assertEquals(bm.getBoard().numBlacks(), 6);

        // 4 BLACKS AND 6 REDS
        int[] fromMoves2 = new int[]{1, 30, 3, 32, 5, 34};
        int[] toMoves2 = new int[]{13, 12, 15, 14, 29, 16};
        ShogiBoardManager bm2 = makeBoard(fromMoves2, toMoves2);
        assertEquals(bm2.getBoard().numBlacks(), 4);

        // A CONTINUATION OF ABOVE, 4 BLACKS AND 2 REDS
        ShogiBoardManager bm3 = makeBoard(new int[]{1, 30, 3, 32, 5, 34, 29, 33, 0, 12, 6}, new int[]{13, 12, 15, 14, 29, 16, 17, 15, 6, 13, 12});
        assertEquals(bm3.getBoard().numBlacks(), 4);
    }

    private ShogiBoardManager makeBoard(int[] fromMoves, int[] toMoves) {
        ShogiBoardManager bm = new ShogiBoardManager(6);

        for (int i = 0; i < fromMoves.length; i++) {
            bm.setTileSelected(fromMoves[i]);
            bm.touchMove(toMoves[i]);
        }
        return bm;
    }


}
