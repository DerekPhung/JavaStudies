package PC_Controls;

import java.awt.*;

public class MouseControl {

    public static void main(String[] args) throws Exception {
        Robot ai = new Robot();
        int x = 0;
        int y = 800;

        Thread.sleep(1000);

        //System.out.println(KeyEvent.VK_C);

//        ai.mousePress(500);
//        Thread.sleep(1000);
//        ai.mouseRelease(500);

//        ai.mousePress(0);

        for (x=0;x<1920;x+=2) {
            Thread.sleep(10);
            ai.mouseMove(x, y);
        }

//        ai.mousePress(MouseEvent.BUTTON1);
//        Thread.sleep(2000);
//        ai.mouseRelease(MouseEvent.BUTTON1);
    }
}
