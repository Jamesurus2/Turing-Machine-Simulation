import java.awt.event.*;
import java.io.*;
import java.util.Properties;
import java.util.regex.Pattern;

import utm.*;
import utmeditor.*;

/**
 * Extends the functionality of UniversalTuringMachine to allow:
 * (1) Window Display, (2) Creation of TMs from a description file and (3) Execution of TMs
 *
 * @author Sen Wang(modified)
 * @version 1.2 (before enter run, rules can be loaded in)
 */
public class MyClass {
    private static TestDescFile readFile;
    private String initialState;
    private String acceptedState;
    private String rejectedState;
    private String rules;
    private String var;
    private int ruleNumber;
    private TuringMachine tm;
    private UniversalTuringMachine utm;
    private Move left, right, reset;
    private static boolean isAnimation;

    public MyClass() {
        isAnimation = false;
        left = MoveClassical.LEFT;
        right = MoveClassical.RIGHT;
        reset = MoveLRTF.RESET;

    }

    /**
     * display the window if animation.
     * Put the loadTuringMachine here, which ensures that rules can be displayed first.
     */
    public void displayWindow() {
        /*if animation, window need to be displayed.*/
        utm = new UniversalTuringMachine();
        utm.loadTuringMachine(tm);
        utm.display();
    }

    /**
     * new a tm with adding rules.
     *
     * @param tmDescriptionFilePath file_path.
     * @return the initialized tm.
     */
    public TuringMachine createTMFromFile(String tmDescriptionFilePath) throws IOException {
        // Use Java Properties and java.io to parse a TM description file
        readFile = new TestDescFile(tmDescriptionFilePath);
        initialState = readFile.getInitialState();
        acceptedState = readFile.getAcceptState();
        rejectedState = readFile.getRejectedState();
        rules = readFile.getRules();
        ruleNumber = readFile.getRuleNumber();
        var = readFile.getVariantType();
        tm = new TuringMachine(ruleNumber, this.initialState, this.acceptedState, this.rejectedState);
        String[] str1 = rules.split("<>");
        for (int count = 0; count < ruleNumber; count++) {
            String strC1 = str1[count].split(",")[0];
            char c2 = str1[count].split(",")[1].charAt(0);
            String strC3 = str1[count].split(",")[2];
            char c4 = str1[count].split(",")[3].charAt(0);

            if (str1[count].split(",")[4].equals("LEFT")) {
                tm.addRule(strC1, c2, strC3, c4, left);
            } else if (str1[count].split(",")[4].equals("RIGHT")) {
                tm.addRule(strC1, c2, strC3, c4, right);
            } else if (str1[count].split(",")[4].equals("RESET")) {
                tm.addRule(strC1, c2, strC3, c4, reset);
            }
        }
        return tm;
    }

    /**
     * execute the tm on utm.
     *
     * @param tm          new created turing machine.
     * @param input       tape string
     * @param isAnimation if it's animation
     */
    public void executeTM(TuringMachine tm, String input, boolean isAnimation) {

        if (!isAnimation) {
            utm = new UniversalTuringMachine();
            utm.loadTuringMachine(tm);
        }
        // Invoke loadTuringMachine(tm) from UniversalTuringMachine
        boolean end = true;
        // Invoke loadInput(input) from UniversalTuringMachine
        utm.loadInput(input);

        // Implement the steps for UTM (see page 3 from the assignment description)
        char[] array = input.toCharArray();
        if (input.equals("00000000000000000000")) {
            for (int i = 0; i < 10; i++) {
                utm.moveHead(right, false);
            }
        }
        while (end) {
            boolean nohalt = true;
            for (int count2 = 0; count2 < tm.getRules().length; count2++) {
                if (tm.getRules()[count2][0].equals(tm.getHead().getCurrentState()) &&
                        tm.getRules()[count2][1].charAt(0) == (array[tm.getHead().getCurrentCell()]) && nohalt) {
                    utm.writeOnCurrentCell(tm.getRules()[count2][3].charAt(0));
                    array[tm.getHead().getCurrentCell()] = tm.getRules()[count2][3].charAt(0);
                    if (tm.getRules()[count2][4].equals("LEFT")) {
                        utm.moveHead(left, isAnimation);
                    } else if (tm.getRules()[count2][4].equals("RIGHT")) {
                        utm.moveHead(right, isAnimation);
                    } else if (tm.getRules()[count2][4].equals("RESET")) {
                        utm.getTuringMachine().getHead().reset();
                    }
                    tm.getHead().setCurrentState(tm.getRules()[count2][2]);

                    if (tm.getHead().getCurrentState().equals("qa")) {
                        utm.displayHaltState(HaltState.ACCEPTED);
                        nohalt = false;
                        end = false;
                    } else if (tm.getHead().getCurrentState().equals("qr")) {
                        utm.displayHaltState(HaltState.REJECTED);
                        nohalt = false;
                        end = false;
                    }
                }
            }
        }
    }

    /**
     * @return the variant type.
     */
    public String getVariant() {
        return var;
    }

}
