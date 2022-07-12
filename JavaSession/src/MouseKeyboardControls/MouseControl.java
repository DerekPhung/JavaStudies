package MouseKeyboardControls;

import java.awt.*;

public class MouseControl {

    public static void main(String[] args) throws Exception {
        Robot ai = new Robot();
        int x = 0;
        int y = 800;

        for (x=0;x<1920;x+=5) {
            Thread.sleep(10);
            ai.mouseMove(x, y);
        }



    }
}
