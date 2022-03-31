import utm.TuringMachine;
import utmeditor.UTMEditor;

import java.io.IOException;

/**
 * 1. After you choose the description file, utm window will display.
 * 2. input the tape string, tm begins running.
 *
 * @author Sen Wang
 * @version 1.1 changed
 */
public class Main {
    private static TestDescFile readFile;


    private static boolean isAnimation;

    /**
     * If no parameter, execute GUI.
     *
     * @param args args[0]-file_path,args[1]-tape string, args[2]-isAnimation.
     * @throws IOException catch the IO exception.
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            UTMEditor a = new UTMEditor();
            ImplementController ic = new ImplementController();
            a.setUTMController(ic);
        } else if (args.length == 3) {
            MyClass extendedUtm = new MyClass();
            readFile = new TestDescFile(args[0]);
            // Creates a TM from a description file
            TuringMachine tm = extendedUtm.createTMFromFile(args[0]);

            if (args[2].equals("--animation")) {
                extendedUtm.displayWindow();
                isAnimation = true;
            } else {
                isAnimation = false;
            }
            // Executes the created TM on the given input
            if (readFile.getVariantType().equals("BUSY_BEAVER")) {
                args[1] = "00000000000000000000";
            } else if (args[1].length() >= 20) {
                args[1] = args[1].substring(0, 19) + "*";
            } else {
                while (args[1].length() < 20) {
                    args[1] = args[1] + "*";
                }
            }
            extendedUtm.executeTM(tm, args[1], isAnimation);
        } else {
            //if user input wrong things, execution will give tips.
            //now there is 0 changed file
            System.out.println("Please input 3 or 0 parameters: (1.file_path, 2.tape string, 3.isAnimation)");
        }
    }
}
