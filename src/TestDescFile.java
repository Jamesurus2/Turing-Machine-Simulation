import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Read variant,initialState,acceptState,rejectState,rules from file_path.
 *
 * @author Sen Wang
 * @version 1.0
 */
public class TestDescFile {
    private String file_path;
    private String variantType;
    private String initialState;
    private String acceptState;
    private String rejectState;
    private String rules;
    private int number;

    /**
     * read key value from file_path.
     *
     * @param file_path file_path.
     * @throws IOException incase of null file index
     */
    public TestDescFile(String file_path) throws IOException {
        Properties pp = new Properties();
        this.file_path = file_path;
        pp.load(new java.io.FileInputStream(file_path));
        variantType = pp.getProperty("variant");
        initialState = pp.getProperty("initialState");
        acceptState = pp.getProperty("acceptState");
        rejectState = pp.getProperty("rejectState", "qr");
        rules = pp.getProperty("rules");
        number = rules.split("<>").length;
    }

    public String getVariantType() {
        return variantType;
    }

    public int getRuleNumber() {
        return number;
    }

    public String getInitialState() {
        return initialState;
    }

    public String getAcceptState() {
        return acceptState;
    }

    public String getRejectedState() {
        return rejectState;
    }

    public String getRules() {
        return rules;
    }
}
