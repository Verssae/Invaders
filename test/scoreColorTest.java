import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.time.LocalTime;

public class scoreColorTest {

    // ------------------------------ ORIGINAL METHODS ------------------------------ //

    private static Color blinkingColor(String color) {
        if (color == "HIGH_SCORES") {
            int R = (int) (Math.pow(Math.random() * (15 - 0), 2));
            int G = (int) (Math.random() * (255 - 0));
            int B = (int) 3.3 * LocalTime.now().getSecond();
            Color title = new Color(R, G, B);
            return title;
        }
        if (color == "GREEN") {
            Color green = new Color(0, (int) (Math.random() * (255 - 155) + 155), 0);
            return green;
        }
        if (color == "WHITE") {
            int RGB = (int) (Math.random() * (255 - 155) + 155);
            Color white = new Color(RGB, RGB, RGB);
            return white;
        }
        if (color == "GRAY") {
            int RGB = (int) (Math.random() * (160 - 100) + 100);
            Color gray = new Color(RGB, RGB, RGB);
            return gray;
        }
        return Color.WHITE;
    }

    private Color scoreColor(final int score) {
        if (score < 800) return Color.WHITE;
        if (score >= 800 && score < 1600) return new Color(206, 255, 210);
        if (score >= 1600 && score < 2400) return new Color(151, 255, 158);
        if (score >= 2400 && score < 3200) return new Color(88, 255, 99);
        if (score >= 3200 && score < 4000) return new Color(50, 255, 64);
        if (score >= 4000 && score < 4800) return new Color(0, 255, 17);
        else return blinkingColor("HIGH_SCORES");
    }



    // ---------- LIBRARY TEST ---------- //

    @Test
    public void library_test() {
        assertEquals(1 + 1, 2);
    }



    // ------------------------------ BLINKING COLOR TEST  ------------------------------ //

    @Test
    public void high_score_test() {
        if (blinkingColor("HIGH_SCORES").getRed() > 255 ||
                blinkingColor("HIGH_SCORES").getRed() < 0 ) { assertEquals(1, 2); }
        else { assertEquals(1, 1); }
    }

    @Test
    public void green_color_test() {
        if (blinkingColor("GREEN").getGreen() > 255 ||
                blinkingColor("GREEN").getGreen() < 155 ) { assertEquals(1, 2); }
        else { assertEquals(1, 1); }
    }

    @Test
    public void white_color_test() {
        if (blinkingColor("WHITE").getBlue() > 255 ||
                blinkingColor("WHITE").getBlue() < 155 ) { assertEquals(1, 2); }
        else { assertEquals(1, 1); }
    }

    @Test
    public void gray_color_test() {
        if (blinkingColor("GRAY").getBlue() >= 160 ||
                blinkingColor("GRAY").getBlue() < 100 ) { assertEquals(1, 2); }
        else { assertEquals(1, 1); }
    }



    // ------------------------------ SCORE COLOR TEST  ------------------------------ //

    @Test
    public void score_color_test_0() {
        assertEquals(scoreColor(500), Color.WHITE);
    }
    @Test
    public void score_color_test_1() {
        assertEquals(scoreColor(1000), new Color(206, 255, 210));
    }
    @Test
    public void score_color_test_2() {
        assertEquals(scoreColor(1800), new Color(151, 255, 158));
    }
    @Test
    public void score_color_test_3() {
        assertEquals(scoreColor(2600), new Color(88, 255, 99));
    }
    @Test
    public void score_color_test_4() {
        assertEquals(scoreColor(3400), new Color(50, 255, 64));
    }
    @Test
    public void score_color_test_5() {
        assertEquals(scoreColor(4500), new Color(0, 255, 17));
    }
}