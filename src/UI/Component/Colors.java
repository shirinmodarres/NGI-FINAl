package UI.Component;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Colors {


        private static List<Color> colorList = new ArrayList<>();

        static {
            // Define your list of colors
            colorList.add(new Color(117, 70, 104));
            colorList.add(new Color(77, 170, 87));
            colorList.add(new Color(226, 133, 110));
            colorList.add(new Color(79, 71, 137));
            colorList.add(new Color(48, 115, 81));
            colorList.add(new Color(3, 63, 99));
            colorList.add(new Color(244, 144, 151));
            colorList.add(new Color(163, 22, 33));
            colorList.add(new Color(245, 93, 62));
            colorList.add(new Color(32, 191, 85));
            colorList.add(new Color(247, 108, 94));
            colorList.add(new Color(246, 142, 95));
            colorList.add(new Color(255, 178, 230));
            colorList.add(new Color(12, 124, 89));
            colorList.add(new Color(213, 255, 228));
            colorList.add(new Color(255, 158, 170));
            colorList.add(new Color(58, 166, 185));

            // Add more colors to the list as needed
        }

        public static Color getRandomColor() {
            Random random = new Random();
            int index = random.nextInt(colorList.size());
            return colorList.get(index);
        }
    }


