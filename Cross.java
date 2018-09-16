package Lesson4;

import java.util.Random;
import java.util.Scanner;

public class Cross {

    static final int SIZE_X=3;//Размер поля по абсциссе
    static final int SIZE_Y=3;//Размер поля по ординате
    static final char PLAYER_DOT='X';//Метка игрока
    static final char AI_DOT='O';//Метка компьютера
    static final char EMPTY_DOT='.';//Обозначение пустой ячейки поля
    static final int WIN_SERIES=3;//Длина победной серии

    static char[][]field=new char[SIZE_Y][SIZE_X];//Создаем массив игрового поля

    static Scanner scanner=new Scanner(System.in);
    static Random random=new Random();

    //Метод для заполнения массива точками
    public static void initFields(){
        for (int i=0;i<SIZE_Y;i++){
            for (int j=0;j<SIZE_X;j++){
                field[i][j]=EMPTY_DOT;
            }
        }
    }

    //Метод для отображения массива
    public static void printField(){
        System.out.println("-------");
        for (int i=0;i<SIZE_Y;i++){
            System.out.print("|");
            for (int j=0;j<SIZE_X;j++){
                System.out.print(field[i][j]+"|");
            }
           System.out.println();
        }
        System.out.println("-------");
    }

    //Метод, позволяющий устанавливать символ в конкретной ячейке
    public static void setSym(int y,int x,char sym){
        field[y][x]=sym;
    }

    //Ход человека
    public static void playerStep(){
        int x;
        int y;
        do {
            System.out.println("Введите координаты: X Y");
            x=scanner.nextInt()-1;
            y=scanner.nextInt()-1;

        }while (!isCellValid(y,x));
        setSym(y,x,PLAYER_DOT);
    }

    /*Ход компьютера
    У компьютера будет 4 приоритета:
    1 - Высший приоритет: Если на следующем ходу он выигрывает, то он ищет любую такую ячейку и делает ход в неё;
    2 - Если нет ячеек высшего приоритета, то он ищет, нет ли ячеек, при установке в которые человек выигрывает на
    следующем ходу. Если есть, то занимет эту ячейку;
    3 - Если нет ячеек приоритета 1 и 2, то он определяет, где вероятность выигрыша будет больше. То есть определяет рейтинг
    ячеек (фактически рейтинг равен количеству ячеек, заполненных символом компьютера, нашей вокруг ячейки). Находит
    максимальный рейтинг и в ту ячейку делает ход;
    4 - Если нет ячеек с приоритетами 1,2,3, то делает случайный ход.
     */
    public static void aiStep() {
        int x = -1;//Присваиваем заведомо ложное начальное значение, чтобы по нему затем отслеживать срабатывание приоритетов
        int y=0;

        //Проверяем приоритет 1
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (isAiWinNextStep(i, j)&&(isCellValid(i, j))) {
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
            for (int i = 0; i < SIZE_Y; i++) {
                for (int j = 0; j < SIZE_X; j++) {
                    if (isPlayerWinNextStep(i, j)&&(isCellValid(i, j))) {
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
            for (int i = 0; i < SIZE_Y; i++) {
                for (int j = 0; j < SIZE_X; j++) {
                    if (getRating(i, j)>maxRating && (isCellValid(i, j))) {
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

        //Если приоритеты 1, 2 и 4 не сработали, действуем по варианту 4 - случайный ход
        if (x == -1) {
            do {
                x = random.nextInt(SIZE_X - 1);
                y = random.nextInt(SIZE_Y - 1);
            } while (!isCellValid(y, x));
            //System.out.println("Приоритеты не сработали - случайный ход");//Это только для контроля, при необходимости - раскомментировать
        }
        setSym(y,x,AI_DOT);
    }

    //Метод проверки - не выигрывает ли компьютер при ходе в текущую ячейку
    public static boolean isAiWinNextStep(int y,int x){
        char buffer=field[y][x];//Отправляем в буфер текущее значение ячейки
        field[y][x]=AI_DOT;//присваиваем текущей ячейке метку копьютера
        if (chekWin(AI_DOT)){//Если наступает ситуация победы
            field[y][x]=buffer;
            return true;
        }else {
            field[y][x]=buffer;
            return false;
        }
    }

    //Метод проверки - не выигрывает ли человек при ходе в текущую ячейку
    public static boolean isPlayerWinNextStep(int y,int x){
        char buffer=field[y][x];//Отправляем в буфер текущее значение ячейки
        field[y][x]=PLAYER_DOT;//присваиваем текущей ячейке метку копьютера
        if (chekWin(PLAYER_DOT)){//Если наступает ситуация победы
            field[y][x]=buffer;
            return true;
        }else {
            field[y][x]=buffer;
            return false;
        }
    }

    //Метод для определения рейтинга текущей ячейки с точки зрения компьютера
    public static int getRating(int y,int x){
        //aMin, aMax,bMin, bMax - максимальный и минимальный номера строки и аналогично для столбца - для обхода вокруг текущей ячейки
        int aMin, aMax,bMin, bMax;
        //Выбор aMin и aMax
        if (y==0){//для первой строки
            aMin=0;
            aMax=1;
        } else if (y==SIZE_Y-1) {//для последней строки
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
        } else if (x==SIZE_X-1) {//для последнего столбца
            bMin=x-1;
            bMax=x;
        } else {//не первый и не последний столбец
            bMin=x-1;
            bMax=x+1;
        }

        int rating=0;
        for (int a=aMin;a<=aMax;a++) {
            for (int b=bMin; b<=bMax; b++) {
                if (field[a][b]==AI_DOT) rating++;
            }
        }
        return rating;

    }

    //Метод проверки на валидность
    public static boolean isCellValid(int y,int x){
        if ( x<0 || y<0 || x>SIZE_X-1 ||y>SIZE_Y-1) return  false;//Проверка на невыход за пределы игрового поля
        return field[y][x]==EMPTY_DOT;//Проверка на незанятость ячейки
    }

    //Метод проверки поля на заполненность
    public static boolean isFieldFull (){
        for (int i=0;i<SIZE_Y;i++){
            for (int j=0;j<SIZE_X;j++){
                if(field[i][j]==EMPTY_DOT) return false;
            }
        }
        return true;
    }

    //Метод для проверки победы
    public static boolean chekWin(char sym){
        /*В цикле проверяем каждую ячейку поля по 4м направлениям: диагональ вправо вверх, горизонталь вправо,
        диагональ вправо вниз, вертикаль вниз; - по длине победной серии. Если хотя бы по одному из направлений
         вернётся истина, значит победа состоялась
         */
        for (int i=0;i<SIZE_Y;i++){
            for (int j=0;j<SIZE_X;j++){
                if(isWinUpperDiagonal(i,j,sym) || isWinHorizontal(i,j,sym) || isWinLowerDiagonal(i,j,sym) || isWinVertical(i,j,sym)) return true;
            }
        }
        return false;
    }

    //Проверка диагонали вправо вверх по длине победной серии
    public static boolean isWinUpperDiagonal (int x,int y,char sym) {
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<WIN_SERIES;k++){
            if(x-k<0 ||y+k>=SIZE_X) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x-k][y+k]==sym) counter++;
        }
        if (counter==WIN_SERIES) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка диагонали вправо вниз по длине победной серии
    public static boolean isWinLowerDiagonal(int x,int y,char sym){
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<WIN_SERIES;k++){
            if(x+k>=SIZE_Y || y+k>=SIZE_X) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x+k][y+k]==sym) counter++;
        }
        if (counter==WIN_SERIES) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка горизонтали вправо по длине победной серии
    public static boolean isWinHorizontal(int x,int y,char sym){
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<WIN_SERIES;k++){
            if(y+k>=SIZE_X) return false;//Сразу можно выходить. Победной серии не будет, так как не хватает самой длины серии
            if(field[x][y+k]==sym) counter++;
        }
        if (counter==WIN_SERIES) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    //Проверка вертикали вниз по длине победной серии
    public static boolean isWinVertical(int x,int y,char sym){
        int counter=0;//Счетчик победных ячеек
        for (int k=0;k<WIN_SERIES;k++){
            if(x+k>=SIZE_Y) return false;//Сразу можно выходить. Победной серии не будет, так как не ххватает самой длины серии
            if(field[x+k][y]==sym) counter++;
        }
        if (counter==WIN_SERIES) {//Если нужное кол-во ячеек заполнено, значит, победа
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        initFields();
        printField();

        while (true){
            playerStep();
            printField();
            if (chekWin(PLAYER_DOT)){
                System.out.println("PLAYER WIN!");
                break;
            }
            if (isFieldFull()){
                System.out.println("DRAW!");
                break;
            }

            aiStep();
            printField();
            if (chekWin(AI_DOT)) {
                System.out.println("SkyNet WIN!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW!");
                break;
            }
        }
    }
}
