package cynofsin.minesweeper;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MinefieldTest {

    @Test
    public void testHints()
    {
        boolean[][] testMines = new boolean[][] {
            {true, false, false, false, false},
            {false, true, false, true, false},
            {false, false, false, false, true},
            {false, false, false, false, true},
            {false, true, true, false, false}
        };

        Integer[][] hints = Minefield.generateHints(testMines);

        assertArrayEquals("Calculated hints were incorrect",
        new Integer[][] {
            {null, 2,      2,      1,      1},
            {2,    null,   2,      null,   2},
            {1,    1,      2,      3,      null},
            {1,    2,      2,      3,      null},
            {1,    null,   null,   2,      1}
        },
        hints);
        
    }
}
