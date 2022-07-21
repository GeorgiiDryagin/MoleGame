import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu {
    public int language;
    public String[][] Labels;
    JPanel jPanel;
    JPanel Levels;
    JButton jbStart;
    JButton jbHelp;
    JButton jbLanguage;
    boolean isShowingControls;
    JButton jbExit;
    JButton jbLevel1;
    JButton jbLevel2;
    JButton jbLevel3;
    JButton jbLevelMenu;

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

    public Menu(JPanel jp, int l)  {
        jPanel = jp;
        language = l;
        setLabels();
        ShowMenu();
    }

    private void setLabels(){
        int languageCount = 2;
        int wordCount = 6;
        Labels = new String[languageCount][];
        for (int i = 0; i < languageCount; i++)
            Labels[i] = new String[wordCount];
        Labels[0][0] = "Начать";
        Labels[0][1] = "Помощь";
        Labels[0][2] = "Выход";
        Labels[0][3] = "Используйте стрелки для перемещения, пробел для отмены хода";
        Labels[0][4] = "Сменить язык";
        Labels[0][5] = "Вы уверены?";

        Labels[1][0] = "Start";
        Labels[1][1] = "Help";
        Labels[1][2] = "Exit";
        Labels[1][3] = "Use Arrows to move, spacebar to undo";
        Labels[1][4] = "Change language";
        Labels[1][5] = "Are you sure?";
    }

    public void ShowMenu(){

        JLabel jLabel = new JLabel("");

        jbStart = new JButton(Labels[language][0]);
        jPanel.add(jbStart);
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbStart.setVisible(false);
                jbHelp.setVisible(false);
                jbLanguage.setVisible(false);
                jbExit.setVisible(false);
                jLabel.setText("");

                jbLevel1.setVisible(true);
                jbLevel2.setVisible(true);
                jbLevel3.setVisible(true);
                jbLevelMenu.setVisible(true);
            }
        });

        jbHelp = new JButton(Labels[language][1]);
        jPanel.add(jbHelp);
        jbHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isShowingControls = !isShowingControls;
                if (isShowingControls){
                    jLabel.setText(Labels[language][3]);
                }
                else
                    jLabel.setText("");
            }
        });

        jbLanguage = new JButton(Labels[language][4]);
        jPanel.add(jbLanguage);
        jbLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (language == 0)
                    language = 1;
                else
                    language = 0;
                jbStart.setText(Labels[language][0]);
                jbHelp.setText(Labels[language][1]);
                jbLanguage.setText(Labels[language][4]);
                jbExit.setText(Labels[language][2]);
                if (isShowingControls)
                    jLabel.setText(Labels[language][3]);
            }
        });

        jbExit = new JButton(Labels[language][2]);
        jPanel.add(jbExit);
        jbExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame(Labels[language][2]);
                if(JOptionPane.showConfirmDialog(jFrame, Labels[language][5], Labels[language][2], JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_NO_OPTION)
                        System.exit(0);
            }
        });

        jPanel.add(jLabel);
        jLabel.setLocation(100, 200);


        jbLevel1 = new JButton("#1");
        jPanel.add(jbLevel1);
        jbLevel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame("level1.txt");
            }
        });
        jbLevel1.setVisible(false);

        jbLevel1 = new JButton("#1");
        jPanel.add(jbLevel1);
        jbLevel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame("level1.txt");
            }
        });
        jbLevel1.setVisible(false);

        jbLevel1 = new JButton("#1");
        jPanel.add(jbLevel1);
        jbLevel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame("level1.txt");
            }
        });

        jbLevel2 = new JButton("#2");
        jPanel.add(jbLevel2);
        jbLevel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame("level1.txt");           /////////НУЖЕН ЕЩЁ УРОВЕНЬ
            }
        });

        jbLevel3 = new JButton("#3");
        jPanel.add(jbLevel3);
        jbLevel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaunchGame("level1.txt");           /////////НУЖЕН ЕЩЁ УРОВЕНЬ
            }
        });

        jbLevelMenu = new JButton("Menu");
        jPanel.add(jbLevelMenu);
        jbLevelMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbStart.setVisible(true);
                jbHelp.setVisible(true);
                jbLanguage.setVisible(true);
                jbExit.setVisible(true);
                jLabel.setText("");

                jbLevel1.setVisible(false);
                jbLevel2.setVisible(false);
                jbLevel3.setVisible(false);
                jbLevelMenu.setVisible(false);
            }
        });

        jbLevel1.setVisible(false);
        jbLevel2.setVisible(false);
        jbLevel3.setVisible(false);
        jbLevelMenu.setVisible(false);


    }

    private void LaunchGame(String name){
        JFrame GameFrame = getJFrame();
        GameFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GameFrame.setVisible(true);

        Field field = null;
        try {
            field = new Field(name);
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
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == 'r')
                    finalField.SetToDefault();
                finalField.Move(e);
            }
        });


        GameplayGraphics canvas = new GameplayGraphics(field);
        //настройки игрового поля
        canvas.setBackground(Color.white);
        GameFrame.add(canvas);
        GameFrame.setVisible(true);

  /*      JButton again = new JButton("Start again");
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalField.SetToDefault();
            }
        });
        canvas.add(again);
        */

    }
}
