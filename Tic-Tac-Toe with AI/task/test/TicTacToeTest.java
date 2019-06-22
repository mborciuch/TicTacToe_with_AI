import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.hyperskill.hstest.v4.testcase.TestCase;
import tictactoe.Main;

import java.util.List;

public class TicTacToeTest extends MainMethodTest {
    public TicTacToeTest() throws Exception {
        super(Main.class);
    }


    @Override
    public List<TestCase> generateTestCases() {
        return List.of(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {

        reply = reply.replaceAll("\\s+", "");

        if (reply.length() > 9) {
            return new CheckResult(false,
                "You need to output no more than 9 symbols");
        }

        for (char c : reply.toCharArray()) {
            if (c != 'X' && c != 'O') {
                return new CheckResult(false,
                    "You need to output X and O " +
                        "symbols only not counting spaces");
            }
        }

        return CheckResult.TRUE;
    }
}
