package Lesson_8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel {

    public static final int MODE_H_V_A=0;
    public static final int MODE_H_V_H=1;
    public static final int PLAYER_X_DOT=1;//Метка игрока-крестика
    public static final int PLAYER_O_DOT=2;//Метка игрока-нолика (компьютера)


    int[][] field;
    int fieldSizeX;
    int fieldSizeY;
    int vinLenght;
    int mode;
    int counter=0;


    int cellHeight;
    int cellWidth;

    boolean isInitialized=false;

    Player playerX;
    Player playerO;

    Color lightBlueInt = new Color(152, 255, 252);
    Color grayInt = new Color(88, 88, 88);
    Color colorX=new Color(22, 243, 83);
    Color colorO=new Color(8,35,237);

    Map(){
        setBackground(lightBlueInt);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
    }

    void update(MouseEvent e){
        int cellX=e.getX()/cellWidth;
        int cellY=e.getY()/cellHeight;
        //System.out.println(cellY+" "+cellX);
        if (!isCellValid(cellY,cellX)){
            JOptionPane error=new JOptionPane();
            error.showMessageDialog(GameWindow.getField(),"Некорректный ход!");
        }else{
            Graphics sumbol=getGraphics();

            int summ=0;

            if(mode==0){
                playerX.step(cellX, cellY, sumbol);

                if (chekWin(PLAYER_X_DOT)) {
                    JOptionPane winX = new JOptionPane();
                    winX.showMessageDialog(GameWindow.getField(), "Победили крестики!");
                    cleanMap();
                }
                if (isFieldFull()) {
                    JOptionPane draw = new JOptionPane();
                    draw.showMessageDialog(GameWindow.getField(), "Ничья!");
                    cleanMap();
                }
                playerO.step(cellX, cellY, sumbol);

                if (chekWin(PLAYER_O_DOT)) {
                    JOptionPane winO = new JOptionPane();
                    winO.showMessageDialog(GameWindow.getField(), "Победили нолики!");
                    cleanMap();
                }
                if (isFieldFull()) {
                    JOptionPane draw = new JOptionPane();
                    draw.showMessageDialog(GameWindow.getField(), "Ничья!");
                    cleanMap();
                }
                counter++;

                for (int i=0;i<fieldSizeY;i++){
                    for (int j=0;j<fieldSizeX;j++){
                        summ+=field[i][j];
                    }
                    System.out.println();
                }
                if (summ==2&&counter==1){
                    for (int i=0;i<fieldSizeY;i++){
                        for (int j=0;j<fieldSizeX;j++){
                            field[i][j]=0;
                        }
                    }
                }
            }else {
                if (counter == 0) {
                    playerX.step(cellX, cellY, sumbol);

                    if (chekWin(PLAYER_X_DOT)) {
                        JOptionPane winX = new JOptionPane();
                        winX.showMessageDialog(GameWindow.getField(), "Победили крестики!");
                        cleanMap();
                    }
                    if (isFieldFull()) {
                        JOptionPane draw = new JOptionPane();
                        draw.showMessageDialog(GameWindow.getField(), "Ничья!");
                        cleanMap();
                    }
                    counter = 1;
                } else {
                    playerO.step(cellX, cellY, sumbol);

                    if (chekWin(PLAYER_O_DOT)) {
                        JOptionPane winO = new JOptionPane();
                        winO.showMessageDialog(GameWindow.getField(), "Победили нолики!");
                        cleanMap();
                    }
                    if (isFieldFull()) {
                        JOptionPane draw = new JOptionPane();
                        draw.showMessageDialog(GameWindow.getField(), "Ничья!");
                        cleanMap();
                    }
                    counter = 0;
                }
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }

    void startNewGame(int mode,int fieldSizeX,int fieldSizeY,int vinLen){
        this.fieldSizeX=fieldSizeX;
        this.fieldSizeY=fieldSizeY;
        this.vinLenght=vinLen;
        this.mode=mode;
        field=new int[fieldSizeY][fieldSizeX];//Создаём массив игрового поля
        //Создаём 2х игроков
        playerX=new Player(1,1);
        if (mode==0){
            playerO=new Player(0,0);
        }else {
         playerO=new Player(0,1);
        }
        isInitialized=true;
        repaint();
    }

    void render (Graphics g){
        if(!isInitialized) return;

        int panelWidth=getWidth();
        int panelHeight=getHeight();

        cellWidth=panelWidth/fieldSizeY;
        cellHeight=panelHeight/fieldSizeX;

        for(int i=0;i<fieldSizeY;i++){
            int y=i*cellHeight;
            g.setColor(grayInt);
            g.drawLine(0,y,panelWidth,y);
        }

        for(int i=0;i<fieldSizeX;i++){
            int x=i*cellWidth;
            g.drawLine(x,0,x,panelHeight);
        }


    }

    //метод рисования хулиганского крестика,адаптивно вписывающегося в любой размер ячейки
    public void setX(int x,int y,Graphics lineX){
        lineX.setColor(colorX);
        lineX.drawLine(x*cellWidth+cellWidth/20,y*cellHeight+(cellHeight/6),x*cellWidth+(cellWidth/3),y*cellHeight+cellHeight/20);
        lineX.drawLine(x*cellWidth+(cellWidth/3),y*cellHeight+cellHeight/20,x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/2-cellHeight/10);
        lineX.drawLine(x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/2-cellHeight/10,x*cellWidth+cellWidth-cellHeight/8,y*cellHeight+cellHeight/20);
        lineX.drawLine(x*cellWidth+cellWidth-cellHeight/8,y*cellHeight+cellHeight/20,x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight/5);
        lineX.drawLine(x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight/5,x*cellWidth+cellWidth/2+cellWidth/10,y*cellHeight+cellHeight/2);
        lineX.drawLine(x*cellWidth+cellWidth/2+cellWidth/10,y*cellHeight+cellHeight/2,x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight-cellHeight/20);
        lineX.drawLine(x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight-cellHeight/20,x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/2+cellHeight/10);
        lineX.drawLine(x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/2+cellHeight/10,x*cellWidth+cellWidth/10,y*cellHeight+cellHeight-cellHeight/20);
        lineX.drawLine(x*cellWidth+cellWidth/10,y*cellHeight+cellHeight-cellHeight/20,x*cellWidth+cellWidth/20,y*cellHeight+cellHeight-cellHeight/6);
        lineX.drawLine(x*cellWidth+cellWidth/20,y*cellHeight+cellHeight-cellHeight/6,x*cellWidth+cellWidth/2-cellWidth/10,y*cellHeight+cellHeight/2);
        lineX.drawLine(x*cellWidth+cellWidth/2-cellWidth/10,y*cellHeight+cellHeight/2,x*cellWidth+cellWidth/20,y*cellHeight+(cellHeight/6));
    }

    //метод рисования хулиганского нолика,адаптивно вписывающегося в любой размер ячейки
    public void setO(int x,int y,Graphics lineO) {
        lineO.setColor(colorO);
        lineO.drawLine(x*cellWidth+cellWidth/2-cellWidth/20,y*cellHeight+cellHeight/20,x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight/2+cellHeight/20);
        lineO.drawLine(x*cellWidth+cellWidth-cellHeight/20,y*cellHeight+cellHeight/2+cellHeight/20,x*cellWidth+cellWidth/2+cellWidth/20,y*cellHeight+cellHeight-cellHeight/20);
        lineO.drawLine(x*cellWidth+cellWidth/2+cellWidth/20,y*cellHeight+cellHeight-cellHeight/20,x*cellWidth+cellWidth/20,y*cellHeight+cellHeight/2-cellHeight/20);
        lineO.drawLine(x*cellWidth+cellWidth/20,y*cellHeight+cellHeight/2-cellHeight/20,x*cellWidth+cellWidth/2-cellWidth/20,y*cellHeight+cellHeight/20);
        lineO.drawLine(x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/4,x*cellWidth+cellWidth-cellHeight/4,y*cellHeight+cellHeight/2);
        lineO.drawLine(x*cellWidth+cellWidth-cellHeight/4,y*cellHeight+cellHeight/2,x*cellWidth+cellWidth/2,y*cellHeight+cellHeight*3/4);
        lineO.drawLine(x*cellWidth+cellWidth/2,y*cellHeight+cellHeight*3/4,x*cellWidth+cellWidth/4,y*cellHeight+cellHeight/2);
        lineO.drawLine(x*cellWidth+cellWidth/4,y*cellHeight+cellHeight/2,x*cellWidth+cellWidth/2,y*cellHeight+cellHeight/4);
    }

    //Проверка координат на валидность
    public boolean isCellValid(int y,int x){
        return field[y][x]==0;//Проверка на незанятость ячейки
    }

    public void setField(int x,int y,int symbol) {
        field [y][x] = symbol;
    }

    public int getField(int x,int y) {
        return field[y][x];
    }

    //Метод для проверки победы
    public boolean chekWin(int sym){
        /*В цикле проверяем каждую ячейку поля по 4м направлениям: диагональ вправо вверх, горизонталь вправо,
        диагональ вправо вниз, вертикаль вниз; - по длине победной серии. Если хотя бы по одному из направлений
         вернётся истина, значит победа состоялась
         */
        for (int i=0;i<fieldSizeY;i++){
            for (int j=0;j<fieldSizeX;j++){
                if(isWinUpperDiagonal(i,j,sym) || isWinHorizontal(i,j,sym) || isWinLowerDiagonal(i,j,sym) || isWinVertical(i,j,sym)) return true;
            }
        }
        return false;
    }

    //Проверка диагонали вправо вверх по длине победной серии
    boolean isWinUpperDiagonal (int x,int y,int sym) {
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<vinLenght;k++){
            if(x-k<0 ||y+k>=fieldSizeX) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x-k][y+k]==sym) counter++;
        }
        if (counter==vinLenght) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка диагонали вправо вниз по длине победной серии
    boolean isWinLowerDiagonal(int x,int y,int sym){
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<vinLenght;k++){
            if(x+k>=fieldSizeY || y+k>=fieldSizeX) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x+k][y+k]==sym) counter++;
        }
        if (counter==vinLenght) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка горизонтали вправо по длине победной серии
    boolean isWinHorizontal(int x,int y,int sym){
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<vinLenght;k++){
            if(y+k>=fieldSizeX) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x][y+k]==sym) counter++;
        }
        if (counter==vinLenght) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка вертикали вниз по длине победной серии
    boolean isWinVertical(int x,int y,int sym) {
        int counter = 0;//Счетчик победных ячеек
        for (int k = 0; k < vinLenght; k++) {
            if (x + k >= fieldSizeY)
                return false;//Сразу можно выходить. Победной серии не будет, так как не ххватает самой длины серии
            if (field[x + k][y] == sym) counter++;
        }
        if (counter == vinLenght) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        } else {
            return false;
        }
    }

    //окно очистки после победы или ничьей
    void cleanMap(){
        repaint();
        for (int i=0;i<fieldSizeY;i++){
            for (int j=0;j<fieldSizeX;j++){
                field[i][j]=0;
            }
        }
        counter=0;
    }

    //Метод проверки поля на заполненность
    boolean isFieldFull (){
        for (int i=0;i<fieldSizeY;i++){
            for (int j=0;j<fieldSizeX;j++){
                if(field[i][j]==0) return false;
            }
        }
        return true;
    }
}
