package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void addStateToNewlyCreatedUserGameStack() {
        User u = new User("John", "Tavares", "What is your city of birth?", "Mississauga");
        int stateSize = u.getGameStack(1).size();
        ConnectFourBoardManager bm = new ConnectFourBoardManager(6);
        u.addState(bm.getBoard(), 1);
        assertEquals(stateSize + 1, u.getGameStack(1).size());
    }
}