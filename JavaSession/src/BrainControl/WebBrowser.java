package BrainControl;
import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebBrowser {

    static Robot ai;

    static {
        try {
            ai = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        String exampleSentence = "rick roll";
        String link = "http://www.youtube.com";

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(link));
        }

        Thread.sleep(3000);
        keyTyped(KeyEvent.VK_TAB);
        keyTyped(KeyEvent.VK_TAB);
        keyTyped(KeyEvent.VK_TAB);
        keyTyped(KeyEvent.VK_TAB);
        toType(exampleSentence);

        keyTyped(KeyEvent.VK_ENTER);

        Thread.sleep(1500);
        keyTyped(KeyEvent.VK_TAB);
        keyTyped(KeyEvent.VK_ENTER);


    }

    static public void keyTyped(int letter) throws InterruptedException {
        Thread.sleep(1);
        ai.keyPress(letter);
        Thread.sleep(1);
        ai.keyRelease(letter);
    }

    static public void toType(String str){
        for(int i = 0; i < str.length(); i++){
            keyTyped(str.charAt(i));
        }
    }

    public static void keyTyped(char letter){
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

    public static int charToVkDecoder(char letter){
        //vk letter a starts at 65
        //ascii letter a starts at 97
        System.out.println(letter);
        System.out.println(letter-32);
        return letter - 32;
    }


}
