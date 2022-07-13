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
    public static void main(String[] args) throws AWTException, InterruptedException {

        KeyboardControl keyboard = new KeyboardControl();

        Thread.sleep(5000);


        System.out.println("done");


    }

    public void toType(String str){
        for(int i = 0; i < str.length(); i++){
            keyTyped(str.charAt(i));
        }
    }

    public void keyTyped(char letter){
        //space in ascii is 32
        try {
            if(letter < 91){
                Thread.sleep(10);
                ai.keyPress(KeyEvent.VK_SHIFT);
                ai.keyPress(letter);
                Thread.sleep(10);
                ai.keyRelease(letter);
                ai.keyRelease(KeyEvent.VK_SHIFT);
            }else {
                Thread.sleep(10);
                System.out.println(letter);
                ai.keyPress(charToVkDecoder(letter));
                Thread.sleep(10);
                ai.keyRelease(charToVkDecoder(letter));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int charToVkDecoder(char letter){
        //vk letter a starts at 65
        //ascii letter a starts at 97
        System.out.println(letter);
        System.out.println(letter-32);
        return letter - 32;
    }

    //tekken related
    static public void powerUp() throws InterruptedException{
        Thread.sleep(100);
        ai.keyPress(KeyEvent.VK_DELETE);
        Thread.sleep(100);
        ai.keyRelease(KeyEvent.VK_DELETE);
    }


    //tekken related
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


    //tekken related
    public void SampleCombo15MarshallLaw() throws InterruptedException, AWTException {

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
