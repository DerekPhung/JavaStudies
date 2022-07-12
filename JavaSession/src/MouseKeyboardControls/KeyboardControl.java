package MouseKeyboardControls;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardControl {

    static Robot ai;
    static int up = KeyEvent.VK_W;
    static int down = KeyEvent.VK_S;
    static int left = KeyEvent.VK_A;
    static int right = KeyEvent.VK_D;

    static int btn1 = KeyEvent.VK_U;
    static int btn2 = KeyEvent.VK_I;
    static int btn3 = KeyEvent.VK_J;
    static int btn4 = KeyEvent.VK_K;

    static {
        try {
            ai = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public KeyboardControl() throws AWTException {
    }

    // this is entirely hardcoded
    // going to figure out a way to not hardcode this
    public static void main(String[] args) throws Exception{

        //punch u i
        //kick j k
        var up = KeyEvent.VK_W;
        var down = KeyEvent.VK_S;
        var left = KeyEvent.VK_A;
        var right = KeyEvent.VK_D;

        var btn1 = KeyEvent.VK_U;
        var btn2 = KeyEvent.VK_I;
        var btn3 = KeyEvent.VK_J;
        var btn4 = KeyEvent.VK_K;

        KeyboardControl key = new KeyboardControl();

        Thread.sleep(2000);

        for(int i = 0; i < 10; i++) {
            electrics();
        }

        //ending taunt
        Thread.sleep(1000);
        ai.keyPress(KeyEvent.VK_DELETE);
        Thread.sleep(100);
        ai.keyRelease(KeyEvent.VK_DELETE);

        System.out.println("done");


    }

    static public void electrics() throws InterruptedException {
        //eletric
        //f
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(100);

        //df+2
        ai.keyPress(down);
        ai.keyPress(right);
        ai.keyPress(btn2);

        Thread.sleep(100);

        ai.keyRelease(btn2);
        ai.keyRelease(down);
        ai.keyRelease(right);

        Thread.sleep(500);
    }

    public void SampleCombo15() throws InterruptedException, AWTException {
        //punch u i
        //kick j k
        var up = KeyEvent.VK_W;
        var down = KeyEvent.VK_S;
        var left = KeyEvent.VK_A;
        var right = KeyEvent.VK_D;

        var btn1 = KeyEvent.VK_U;
        var btn2 = KeyEvent.VK_I;
        var btn3 = KeyEvent.VK_J;
        var btn4 = KeyEvent.VK_K;

        KeyboardControl key = new KeyboardControl();



        Thread.sleep(0);

        //for jokes I used Law sample combo 15


        // -> -> 3+4
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(100);

        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(100);

        Thread.sleep(50);
        ai.keyPress(right);
        ai.keyPress(btn3);
        ai.keyPress(btn4);

        Thread.sleep(50);
        ai.keyRelease(btn3);
        ai.keyRelease(btn4);
        ai.keyRelease(right);

        Thread.sleep(1000);

        //4 up 3
        Thread.sleep(100);
        ai.keyPress(btn4);
        Thread.sleep(100);
        ai.keyRelease(btn4);

        Thread.sleep(50);
        ai.keyPress(up);
        ai.keyPress(btn3);

        Thread.sleep(50);
        ai.keyRelease(btn3);
        ai.keyRelease(up);
        Thread.sleep(800);

        //<-2, 1
        ai.keyPress(left);
        ai.keyPress(btn2);
        Thread.sleep(100);
        ai.keyRelease(btn2);
        ai.keyRelease(left);

        Thread.sleep(100);
        ai.keyPress(btn1);
        Thread.sleep(50);
        ai.keyRelease(btn1);
        Thread.sleep(800);

        //moving foward
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(50);

        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(50);

        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        Thread.sleep(100);


        //4,3 <- ->
        Thread.sleep(100);
        ai.keyPress(btn4);
        Thread.sleep(50);
        ai.keyRelease(btn4);

        Thread.sleep(50);
        ai.keyPress(btn3);
        Thread.sleep(100);
        ai.keyPress(left);
        Thread.sleep(100);
        ai.keyRelease(left);
        ai.keyRelease(btn3);

        Thread.sleep(100);
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);

        Thread.sleep(50);
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);
        System.out.println("fowards");

        //-> 3
        Thread.sleep(100);
        ai.keyPress(right);
        Thread.sleep(50);
        ai.keyRelease(right);

        Thread.sleep(50);
        ai.keyPress(right);
        ai.keyPress(btn3);

        Thread.sleep(100);

        ai.keyRelease(right);
        ai.keyRelease(btn3);

        //ending taunt
        Thread.sleep(1000);
        ai.keyPress(KeyEvent.VK_DELETE);
        Thread.sleep(100);
        ai.keyRelease(KeyEvent.VK_DELETE);

        System.out.println("done");
    }


}
