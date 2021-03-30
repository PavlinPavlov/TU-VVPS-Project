package tu.vvps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Menu {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    private final ScannerWrapper scanner;

    public Menu(ScannerWrapper scanner) {
        this.scanner = scanner;
    }

    public ID getInputForData() {

        System.out.println("========= Choose data =========");
        System.out.println("\t= 0 User Id (default)");
        System.out.println("\t> 0 Page Id");
        System.out.println("\t< 0 Course Module Id");

        int choice = scanner.readInt();

        if (choice > 0) {
            return ID.PAGE;
        } else if (choice < 0) {
            return ID.COURSE_MODULE;
        } else {
            return ID.USER;
        }
    }
}
