package Lesson_8;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player {
    int sumbol;//1-если крестик, 0-если нолик
    int isHuman;//1-если человек 0 - если компьютер
    Player(int sumbol,int isHuman){
        this.sumbol=sumbol;
        this.isHuman=isHuman;
    }
    //Ход
    public void step(int x,int y,Graphics sumbolG){
        if (isHuman==1){
            humanStep(x,y,sumbolG);
        }else {
            aiStep(sumbolG);
        }
    }
    //Прорисовка символа

    //Заполнение поля

    //Ход человека
    void humanStep(int x,int y,Graphics sumbolG){
        if(sumbol==1) {
            //Заполнение поля
            GameWindow.getField().setField(x, y, 1);
            //Прорисовка крестика
            GameWindow.getField().setX(x, y, sumbolG);
        }else {
            //Заполнение поля
            GameWindow.getField().setField(x, y, 2);
            //Прорисовка нолика
            GameWindow.getField().setO(x, y, sumbolG);
        }
    }

    //Ход компьютера
    /*У компьютера будет 4 приоритета:
            1 - Высший приоритет: Если на следующем ходу он выигрывает, то он ищет любую такую ячейку и делает ход в неё;
    2 - Если нет ячеек высшего приоритета, то он ищет, нет ли ячеек, при установке в которые человек выигрывает на
    следующем ходу. Если есть, то занимет эту ячейку;
    3 - Если нет ячеек приоритета 1 и 2, то он определяет, где вероятность выигрыша будет больше. То есть определяет рейтинг
    ячеек (фактически рейтинг равен количеству ячеек, заполненных символом компьютера, нашей вокруг ячейки). Находит
    максимальный рейтинг и в ту ячейку делает ход;
    4 - Если нет ячеек с приоритетами 1,2,3, то делает случайный ход.
            */
    void aiStep(Graphics sumbolG){
        int x = -1;//Присваиваем заведомо ложное начальное значение, чтобы по нему затем отслеживать срабатывание приоритетов
        int y=0;
        //Проверяем приоритет 1
        for (int i = 0; i < GameWindow.getField().fieldSizeY; i++) {
            for (int j = 0; j < GameWindow.getField().fieldSizeX; j++) {
                if (isAiWinNextStep(i, j)&&(GameWindow.getField().isCellValid(i, j))) {
                    x = j;
                    y = i;
                    //System.out.println("Приоритет 1 сработал");//Это только для контроля, при необходимости - раскомментировать
                    break;
                }
            }
            if (x != -1) break;
        }
        //Если приоритет 1 не сработал, проверяем приоритет 2
        if (x==-1) {
            for (int i = 0; i < GameWindow.getField().fieldSizeY; i++) {
                for (int j = 0; j < GameWindow.getField().fieldSizeX; j++) {
                    if (isPlayerWinNextStep(i, j)&&(GameWindow.getField().isCellValid(i, j))) {
                        x = j;
                        y = i;
                        //System.out.println("Приоритет 2 сработал");//Это только для контроля, при необходимости - раскомментировать
                        break;
                    }
                }
                if (x != -1) break;
            }
        }

        //Если приоритеты 1 и 2 не сработали, проверяем приоритет 3
        if (x==-1) {
            int maxRating=0;//Переменная для записи максимального рейтинга
            int maxX=0;//абцисса ячейки с максимальным рейтингом
            int maxY=0;//ордината ячейки с максимальным рейтингом
            for (int i = 0; i < GameWindow.getField().fieldSizeY; i++) {
                for (int j = 0; j < GameWindow.getField().fieldSizeX; j++) {
                    if (getRating(i, j)>maxRating && (GameWindow.getField().isCellValid(i, j))) {
                        maxRating=getRating(i, j);
                        maxX = j;
                        maxY = i;
                    }
                }
            }
            if(maxRating!=0) {//Если максимальный рейтинг стал ненулевым, то приоритет сработал
                x = maxX;
                y = maxY;
                //System.out.println("Приоритет 3 сработал");//Это только для контроля, при необходимости - раскомментировать
            }
        }

        //Если приоритеты 1, 2 и 3 не сработали, действуем по варианту 4 - случайный ход
        if (x == -1) {
            Random random = new Random();
            do {
                x = random.nextInt(GameWindow.getField().fieldSizeX - 1);
                y = random.nextInt(GameWindow.getField().fieldSizeY - 1);
            } while (!GameWindow.getField().isCellValid(y, x));
            //System.out.println("Приоритеты не сработали - случайный ход");//Это только для контроля, при необходимости - раскомментировать
        }
        //Заполнение поля
        GameWindow.getField().setField(x,y,2);
        //Прорисовка нолика
        GameWindow.getField().setO(x,y,sumbolG);
    }

    //Метод проверки - не выигрывает ли компьютер при ходе в текущую ячейку
    boolean isAiWinNextStep(int y,int x){
        int buffer=GameWindow.getField().getField(x,y);//Отправляем в буфер текущее значение ячейки
        GameWindow.getField().setField(x,y,Map.PLAYER_O_DOT);//присваиваем текущей ячейке метку копьютера
        if (GameWindow.getField().chekWin(Map.PLAYER_O_DOT)){//Если наступает ситуация победы
            GameWindow.getField().setField(x,y,buffer);
            return true;
        }else {
            GameWindow.getField().setField(x,y,buffer);
            return false;
        }
    }

    //Метод проверки - не выигрывает ли человек при ходе в текущую ячейку
    boolean isPlayerWinNextStep(int y,int x){
        int buffer=GameWindow.getField().getField(x,y);//Отправляем в буфер текущее значение ячейки
        GameWindow.getField().setField(x,y,Map.PLAYER_X_DOT);//присваиваем текущей ячейке метку игрока
        if (GameWindow.getField().chekWin(Map.PLAYER_X_DOT)){//Если наступает ситуация победы
            GameWindow.getField().setField(x,y,buffer);
            return true;
        }else {
            GameWindow.getField().setField(x,y,buffer);
            return false;
        }
    }

    //Метод для определения рейтинга текущей ячейки с точки зрения компьютера
    int getRating(int y,int x){
        //aMin, aMax,bMin, bMax - максимальный и минимальный номера строки и аналогично для столбца - для обхода вокруг текущей ячейки
        int aMin, aMax,bMin, bMax;
        //Выбор aMin и aMax
        if (y==0){//для первой строки
            aMin=0;
            aMax=1;
        } else if (y==GameWindow.getField().fieldSizeY-1) {//для последней строки
            aMin=y-1;
            aMax=y;
        } else {//не первая и не последняя сторка
            aMin=y-1;
            aMax=y+1;
        }

        //Выбор bMin и bMax
        if (x==0){//для первого столбца
            bMin=0;
            bMax=1;
        } else if (x==GameWindow.getField().fieldSizeX-1) {//для последнего столбца
            bMin=x-1;
            bMax=x;
        } else {//не первый и не последний столбец
            bMin=x-1;
            bMax=x+1;
        }

        int rating=0;
        for (int a=aMin;a<=aMax;a++) {
            for (int b=bMin; b<=bMax; b++) {
                if (GameWindow.getField().getField(b,a)==Map.PLAYER_O_DOT) rating++;
            }
        }
        return rating;

    }

}
