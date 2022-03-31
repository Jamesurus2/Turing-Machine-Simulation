import utm.TuringMachine;
import utm.UniversalTuringMachine;
import utmeditor.*;

import java.io.IOException;

/**
 * This calss:   1. extends MyClass: 3 key public methods.
 *              2. override loadTuringMchine and runTM methods, which can invoke the 3 key public methods.
 * @author  Sen Wang
 * @version 1.1 change display() from run to load.
 */
public class ImplementController extends MyClass implements UTMController {
    private TuringMachine tm;

    public ImplementController() {
        super();
    }

    @Override
    public void loadTuringMachineFrom(String s) {
        try {
            tm = this.createTMFromFile(s);
            this.displayWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void runUTM(String s) {
        if (this.getVariant().equals("BUSY_BEAVER")) {
            s = "00000000000000000000";
        } else if (s.length() >= 20) {
            s = s.substring(0, 19) + "*";
        } else {
            while (s.length() < 20) {
                s = s + "*";
            }
        }
        this.executeTM(tm, s, true);
    }
}
