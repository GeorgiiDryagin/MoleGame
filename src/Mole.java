import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Mole {

    static JFrame getJFrame(){
        JFrame jFrame = new JFrame() {};
        int Width = 500;
        int Height = 500;
        jFrame.setVisible(true);
        jFrame.setBackground(Color.LIGHT_GRAY);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - Width/2, dimension.height/2 - Height/2, Width, Height);
        jFrame.setTitle("Mole");

        return jFrame;
    }
    public static void main(String[] args){
        JFrame mainFrame = getJFrame();
        JPanel menuPanel = new JPanel();
        mainFrame.add(menuPanel);
        Menu menu = new Menu(menuPanel, 0);
        menuPanel.setVisible(true);

    }
}

/*

        String FileName = "level1.txt";
        JFrame GameFrame = getJFrame();
        GameFrame.setVisible(true);

        Field field = null;
        try {
            field = new Field(FileName);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        Field finalField = field;
        GameFrame.addKeyListener(new KeyListener() {
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
        GameplayGraphics canvas = new GameplayGraphics(field);
        //настройки игрового поля
        canvas.setBackground(Color.white);
        GameFrame.add(canvas);


        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalField.isShowingControls = !finalField.isShowingControls;
                if (finalField.isShowingControls){
                    finalField.label = menu.Labels[language][3];
                }
                else
                    finalField.label = "";
            }
        });
        canvas.add(help);


    JButton again = new JButton("Start again");
        again.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
        finalField.SetToDefault();
        }
        });
        canvas.add(again);

        JPanel levelPanel = new JPanel();
        GameFrame.add(levelPanel);

        int language = 0;
        JPanel menuPanel = new JPanel();
        Menu menu = new Menu(menuPanel, language, canvas);
        GameFrame.add(menuPanel);

        canvas.setVisible(true);

        */
