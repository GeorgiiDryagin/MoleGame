import javax.swing.*;
import java.awt.*;

public class GameplayGraphics extends JPanel {
    public Field field;
    private int cellSize = 50;
    private int gap = 0;
    Image Krtek;
    Image Grain;
    Image Bag;
    Image BagGrain;
    Image Target;
    int language;
    String[][] Labels;

    GameplayGraphics(Field f, int lang){
        field = f;
        language = lang;

        Krtek = new ImageIcon("Krtek.png").getImage();
        Bag = new ImageIcon("Bag.png").getImage();
        BagGrain = new ImageIcon("BagGrain.png").getImage();
        Grain = new ImageIcon("Grain.png").getImage();
        Target = new ImageIcon("Target.png").getImage();

        int languageCount = 2;
        int wordCount = 3;
        Labels = new String[languageCount][];
        for (int i = 0; i < languageCount; i++)
            Labels[i] = new String[wordCount];
        Labels[0][0] = "Нажмите R чтобы начать сначала";
        Labels[0][1] = "Нажмите H чатобы показать/спрятать управление";
        Labels[0][2] = "Используйте стрелки для перемещения, пробел для отмены хода";

        Labels[1][0] = "Press R to restart";
        Labels[1][1] = "Ptrss H to show/hide controls";
        Labels[1][2] = "Use Arrows to move, spacebar to undo";
    }



    public void paint(Graphics g){
        super.paint(g);
        g.drawString(Labels[language][0], 100, 10);
        g.drawString(Labels[language][1], 100, 20);

        String helpLabel = "";
        int size = field.getSize();
        int UpperSpace = 50;
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(field.StepsNum), 0, 10);
        if (field.Win)
            g.drawString("Win!", 0, 20);

        if (field.isShowingControls)
            helpLabel = Labels[language][2];
        else
            helpLabel = "";



        g.drawString(helpLabel, 0, 30);

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                int x = cellSize * j;
                int y = cellSize * i + UpperSpace;
                Cell cell = field.cells[i][j];
                if (cell.state == 'c')
                    g.setColor(Color.darkGray);
                else
                    g.setColor(Color.gray);
                g.fillRect(x , y, cellSize, cellSize);

                //отрисовка стен
                g.setColor(Color.BLACK);
                if (cell.walls[0]) //нижняя стена
                    g.fillRect(x + gap, y + cellSize - gap - 4, cellSize - gap * 2, 4);
                if (cell.walls[1]) //левая стена
                    g.fillRect(x + gap, y + gap, 4, cellSize - gap * 2);
                if (cell.walls[2]) //верхняя стена
                    g.fillRect(x + gap, y + gap, cellSize - gap * 2, 4);
                if (cell.walls[3]) //правая стена
                    g.fillRect(x + cellSize - gap - 3, y + gap, 4, cellSize - gap * 2);

                //отрисовка объектов
                //крот
                if (cell.state == 'm')
                    g.drawImage(Krtek, x,y,null);
                //пустой мешок
                if (cell.state == 'b')
                    g.drawImage(Bag, x,y,null);
                //мешок с зерном
                if (cell.state == 'w')
                    g.drawImage(BagGrain, x,y,null);
                //зерно
                if (cell.state == 'g')
                    g.drawImage(Grain, x,y,null);
                //зерно
                if (cell.target)
                    g.drawImage(Target, x,y,null);
            }

        }

        repaint();
    }
}
