import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.KeyEvent;

public class Field {
    public int StepsNum;
    public Cell[][] cells;
    private int size;
    public int getSize(){
        return size;
    }
    public boolean Win;

    public Field(String name) throws Exception {
        boolean moleIsFound = false;
        Win = false;
        StepsNum = 0;
        try {
            File myObj = new File(name);
            Scanner myReader = new Scanner(myObj);
            boolean first = true;
            int row = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (first) {
                    size = data.length() / 2;
                    cells = new Cell[size][];
                    for (int i = 0; i < size; i++) {
                        cells[i] = new Cell[size];
                        for (int j = 0; j < size; j++)
                            cells[i][j] = new Cell();
                    }
                    first = false;
                }

                for(int i = 0; i < data.length(); i++) {
                    if (i % 2 == 0){

                        char curChar = data.charAt(i);
                        //нахождение ячеек, куда нужно доставить зерно, они обозначены заглавными
                        if (curChar >= 'A' && curChar <= 'Z'){
                            curChar = Character.toLowerCase(curChar);
                            cells[row][i/2].target = true;
                        }

                        cells[row][i/2].state = curChar;

                        //если в файле больше одного крота
                        if(curChar == 'm') {
                            if (moleIsFound)
                                throw new Exception("More then one mole");

                            moleIsFound = true;   //запись координат ячейки с кротом
                        }
                    }
                    else{
                        setWalls(cells[row][(i - 1)/2], data.charAt(i));
                    }
                }
                row++;
            }
            EnsureCorrectness();
            if (!checkEquality())
                throw new Exception("Not equal numbers of targets, grains or bags");
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private boolean checkEquality(){
        int grainNum = 0;
        int bagNum = 0;
        int targetNum = 0;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)  {
                if (cells[i][j].target) targetNum++;
                if (cells[i][j].state == 'g') grainNum++;
                if (cells[i][j].state == 'b') bagNum++;
            }
        return (targetNum == grainNum && grainNum == bagNum);
    }

    //обеспечивает правильность расстановки стен
    private void EnsureCorrectness(){
        //постановка стен вокруг непроходимой ячейки
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (cells[i][j].state == 'c')
                    for (int k = 0; k < 4; k++)
                        cells[i][j].walls[k] = true;

        //установка ограничителей
        //сверху поля
        for (int i = 0; i < size; i++)
            cells[0][i].walls[2] = true;
        //снизу поля
        for (int i = 0; i < size; i++)
            cells[size - 1][i].walls[0] = true;
        //справа от поля
        for (int i = 0; i < size; i++)
            cells[i][size - 1].walls[3] = true;
        //слева от поля
        for (int i = 0; i < size; i++)
            cells[i][0].walls[1] = true;

        //наличиле стены для обоих ячеек
        //сосед находится сверху от данной
        //т.е. если у данной ячейки есть стена сверху, то ячейка выше должна иметь стену снизу
        for (int i = 1; i < size; i++)
            for(int j = 0; j < size; j++)
                if (cells[i][j].walls[2]) cells[i - 1][j].walls[0] = true;
        //снизу от данной
        for (int i = 0; i < size - 1; i++)
            for(int j = 0; j < size; j++)
                if (cells[i][j].walls[0]) cells[i + 1][j].walls[2] = true;
        //слева от данной
        for (int i = 0; i < size; i++)
            for(int j = 1; j < size; j++)
                if (cells[i][j].walls[1]) cells[i][j - 1].walls[3] = true;
        //справа от данной
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size - 1; j++)
                if (cells[i][j].walls[3]) cells[i][j + 1].walls[1] = true;

        //обратно удаляем стенки у непроходимых. для красоты
        for (int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if (cells[i][j].state == 'c')
                    for (int k = 0; k < 4; k++)
                        cells[i][j].walls[k] = false;
    }

    private void setWalls(Cell cell, char c){
        //предствление шестнадцатиричного числа в двоичном виде
        int num = Integer.parseInt(Character.toString(c), 16);
        String bin = Integer.toString(num, 2);

        while (bin.length() < 4){
            bin = "0" + bin;
        }

        for (int i = 0; i < 4; i++)
            cell.walls[i] = bin.charAt(i) == '1';
    }

    private boolean checkWin(){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)  {
                //если найдена цель без заполненного зерна
                if (cells[i][j].target && cells[i][j].state != 'w') return false;
            }
        return true;
    }

    public void Move(KeyEvent event){
        StepsNum++;
        int moleRaw = 0;  //координаты ячейки с кротом
        int moleCol = 0;
        int direction = 0;
        boolean found = false;
        for (int i = 0; i < size && !found; i++)
            for (int j = 0; j < size && !found; j++)
                if (cells[i][j].state == 'm'){
                    moleRaw = i;
                    moleCol = j;
                    found = true;
                }

        Cell nextCell = null;       //ячейка, куда крот собирается пойти
        Cell afterNextCell = null;  //ячейка, куда может быть вытеснено содержимое

        //вверх
        if (event.getKeyChar() == 'w'){
            if (moleRaw == 0) return;  //если над кротом нет ячеек
            if (cells[moleRaw][moleCol].walls[2]) return; //если перед кротом стена

            //если над кротом больше одной ячейки
            if (moleRaw > 1) afterNextCell = cells[moleRaw - 2][moleCol];
            nextCell = cells[moleRaw - 1][moleCol];
            direction = 2;
        }
        //вниз
        if (event.getKeyChar() == 's'){
            if (moleRaw == size - 1) return;  //если под кротом нет ячеек
            if (cells[moleRaw][moleCol].walls[0]) return;

            //если под кротом больше одной ячейки
            if (moleRaw + 2 < size) afterNextCell = cells[moleRaw + 2][moleCol];
            nextCell = cells[moleRaw + 1][moleCol];
            direction = 0;
        }
        //влево
        if (event.getKeyChar() == 'a'){
            if (moleCol == 0) return;
            if (cells[moleRaw][moleCol].walls[1]) return;

            if (moleCol > 1) afterNextCell = cells[moleRaw][moleCol - 2];
            nextCell = cells[moleRaw][moleCol - 1];
            direction = 1;
        }
        //вправо
        if (event.getKeyChar() == 'd'){
            if (moleCol == size - 1) return;
            if (cells[moleRaw][moleCol].walls[3]) return;

            if (moleCol + 2 < size) afterNextCell = cells[moleRaw][moleCol + 2];
            nextCell = cells[moleRaw][moleCol + 1];
            direction = 3;
        }

        if (nextCell == null) return;
        if (nextCell.state == 'g' || nextCell.state == 'c' ) return;

        //перед кротом свободная ячейка
        if (nextCell.state == 'f'){
            nextCell.state = 'm';
            cells[moleRaw][moleCol].state = 'f';
            return;
        }

        if (nextCell.walls[direction]) return; //если нельзя вытолкнуть предмет с соседней клетки
        //перед кротом мешок (любой)
        if (nextCell.state == 'b' || nextCell.state == 'w' ){
            if (afterNextCell == null) return;//предотвращение выталкивания за границу
            //перемещение любого мешка на пустую клетку
            if (afterNextCell.state == 'f'){
                afterNextCell.state = nextCell.state;
                nextCell.state = 'm';
                cells[moleRaw][moleCol].state = 'f';
            }
            //если крот толкает пустой мешок на зерно
            if (afterNextCell.state == 'g' && nextCell.state == 'b'){
                afterNextCell.state = 'w';
                nextCell.state = 'm';
                cells[moleRaw][moleCol].state = 'f';
            }
        }
        Win = checkWin();
    }

    public void Delete(){
        if (size == 0) return;

        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                cells[i][j].walls = null;
            cells[i] = null;
        }
        cells = null;
        System.gc();    //сборщик мусора
    }
}
