import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Mole {
    public static void main(String[] args){
        Field field = null;
        try {
            field = new Field("level1.txt");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        JFrame jFrame = new JFrame() {};

        Field finalField = field;
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                finalField.Move(e);
            }
        });

        jFrame.setVisible(true);
        jFrame.setBackground(Color.LIGHT_GRAY);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 500, dimension.height/2 - 400, 1000, 800);
        jFrame.setTitle("Mole");

        if (field != null){
            MoleGraphics canvas = new MoleGraphics(field);
            canvas.setBackground(Color.white);
            jFrame.add(canvas);
        }

    }
}
