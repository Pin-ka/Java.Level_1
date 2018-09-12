import java.util.Random;
import java.util.Scanner;
import static java.lang.Math.*;

public class Lesson3 {
    public static void main(String[] args) {
        Scanner task = new Scanner(System.in);
        int taskNumber;//Для ветвления по номеру задачи
        System.out.println("Введите номер задачи:\n1 - задача №1,\n2 - задача №2,\n3 - дополнительная задача");
        taskNumber=task.nextInt();
        switch (taskNumber) {
            case 1:
                System.out.println("1. Написать программу, которая загадывает случайное число от 0 до 9, и пользователю дается 3 попытки угадать это число. \nПри каждой попытке компьютер должен сообщить больше ли указанное пользователем число чем загаданное, или меньше.\nПосле победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).");
                System.out.println();//Для отступа между постановкой задачи и выводом результата
                guessNumber();
                System.out.println("-----------------------------------------------");//Для разделения между разными заданиями
                break;
            case 2:
                System.out.println("2 * Создать массив из слов:\napple, orange, lemon, banana, apricot, avocado, broccoli, carrot, cherry, garlic, grape, melon, leak,\nkiwi, mango, mushroom, nut, olive, pea, peanut, pear, pepper, pineapple, pumpkin, potato}\n" +
                        "При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя,\n" +
                        "сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь.\nЕсли слово не угадано, компьютер показывает буквы которые стоят на своих местах.\n");
                System.out.println();//Для отступа между постановкой задачи и выводом результата
                guessWord();
                System.out.println("-----------------------------------------------");//Для разделения между разными заданиями
                break;
            case 3:
                System.out.println("Дополнительное задание: Консольный калькулятор на действия +,-,*,/.\n" +
                        "Числа и действия вводим через пробел (н-р: 5 + 7). В идеале чисел и действий неограничено");
                System.out.println();//Для отступа между постановкой задачи и выводом результата
                calculator();
                System.out.println("-----------------------------------------------");//Для разделения между разными заданиями
                break;
            default:
                System.out.println("Задача была выбрана некорректно");
        }
    }

    /*1.Написать программу, которая загадывает случайное число от 0 до 9, и пользователю дается 3 попытки угадать это число.
    При каждой попытке компьютер должен сообщить больше ли указанное пользователем число чем загаданное, или меньше.
    После победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).
     */
    private static void guessNumber () {
        do {//бескончный цикл, прерывающийся по желанию пользователя выйти
            Scanner scanNumber = new Scanner(System.in);
            Random random=new Random();
            int hiddenNumber=random.nextInt(9);//получаем загаданное случайно число от 1 до 9
            int attempt=3;//Количество попыток, данное пользователю
            int answerNumber;//Переменная для записи ответа пользователя
            for (int i=1;i<=attempt;i++) {
                System.out.println("Угадайте число от 0 до 9. Попытка "+i+" из "+attempt);
                answerNumber=scanNumber.nextInt();
                if (hiddenNumber>answerNumber) {
                    System.out.println("Ваше число меньше загаданного");
                } else if (hiddenNumber<answerNumber) {
                    System.out.println("Ваше число больше загаданного");
                } else {
                    System.out.println("Поздравляем, вы угадали");
                    break;
                }
            }
        }while (isNoExit());
    }

    //Метод для ввода и проверки желания пользователя выйти
    private  static boolean isNoExit () {
        boolean result=true;//Переменная, возвращающая результат запроса на выход. Априори - не выходим
        Scanner scanYesOrNo = new Scanner(System.in);
        int answer=-1; //Переменная для записи ответа пользователя
        do {//Цикл повторения запроса, пока введённые данные не станут корректны
            System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
            answer=scanYesOrNo.nextInt();//Ничего, кроме числа, ввести не сможем
        }while((answer<0)||(answer>1));
        if (answer==0) result=false;
        return result;
    }

    /*2 * Создать массив из слов String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
    "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut",
    "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
    При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя,
    сравнивает его с загаданным словом и сообщает правильно ли ответил пользователь.
    Если слово не угадано, компьютер показывает буквы которые стоят на своих местах.
    apple – загаданное
    apricot - ответ игрока
    ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
    Для сравнения двух слов посимвольно, можно пользоваться:
    String str = "apple";
    str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции
    Играем до тех пор, пока игрок не отгадает слово
    Используем только маленькие буквы
    */
    private static void guessWord(){
        Scanner scanWord = new Scanner(System.in);
        int lattices=15;//Количество символов, отображаемых, чтобы пользователь не знал длину слова
        //Создаём требуемый массив
        String [] arrayWords={"apple", "orange", "lemon", "banana", "apricot", "avocado",
                "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut",
                "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        //компьютер загадывает слово из массива
        Random random=new Random();
        String hiddenWord=arrayWords[random.nextInt(arrayWords.length-1)];
        do {//Зацикливаем до тех пор, пока пользователь не отгадает число
                //Спрашиваем ответ у пользователя
                System.out.println("Отгадайте слово:");
                String answerWord = scanWord.nextLine();
                answerWord = answerWord.toLowerCase();//Используем только маленькие буквы
                if (answerWord.equals(hiddenWord)) {
                    System.out.println("Слово угадано!");
                    break;
                } else {
                    System.out.println("Слово не угадано.");
                    //Цикл для сравнения загаданного слова и ответа игрока и отображения строки в 15 символов
                    for (int i = 0; i < lattices; i++) {
                        if ((i > (answerWord.length() - 1)) || (i > (hiddenWord.length() - 1))) {//Если мы вышли за пределы одного из слов, то сразу рисуем #
                            System.out.print("#");
                        } else if (answerWord.charAt(i) == hiddenWord.charAt(i)) {
                            System.out.print(answerWord.charAt(i));
                        } else {
                            System.out.print("#");
                        }
                    }
                    System.out.print("\n");
                }
        }while (true);
    }

    /*Дополнительное задание: Консольный калькулятор на действия +,-,*,/.
    Числа и действия вводим через пробел (н-р: 5 + 7).
     */
    private static void calculator () {
        Scanner scanLine = new Scanner(System.in);
        String inputLine;
        double arg1=0;//переменная для хранения аргумента 1
        double arg2=0;//переменная для хранения аргумента 2
        char action='$';//переменная для хранения символа действия
        boolean correctArgs=true;//Помогает проверять, корректны ли введённые аргументы
        boolean correctAction = false;//Помогает проверять, корректно ли введённое действие
        int numSum=0;//считает значимые символы: 1 - 1ый аргумент есть, 2 - действие есть, 3 - 2ой аргумент есть

        do {//Цикл ввода данных и проверки их на корректность
            System.out.println("Введите, что нужно посчитать (через пробел. Например: '5 + 7')");
            inputLine = scanLine.nextLine();

            //Теперь делим строку
            for (int i = 1; i < inputLine.length(); i++) { //С 1,  так как реперы у нас - пробелы, а первый символ не должен быть пробелом
                if ((inputLine.charAt(i) == ' ') && (numSum == 0)) {
                    for (int j = 0; j < i; j++) {//Получаем первый аргумент
                        arg1 += sumbolToNum(inputLine.charAt(j)) * pow(10, i - j - 1);
                        if (sumbolToNum(inputLine.charAt(j)) == 10) correctArgs = false;
                    }
                    numSum++;
                } else if ((inputLine.charAt(i) == ' ') && (numSum == 1)) {
                    //Получаем символ действия
                    action = inputLine.charAt(i - 1);
                    numSum++;
                    for (int j = i + 1; j < inputLine.length(); j++) {//Получаем второй аргумент
                        arg2 += sumbolToNum(inputLine.charAt(j)) * pow(10, inputLine.length() - j - 1);
                        if (sumbolToNum(inputLine.charAt(j)) == 10) correctArgs = false;
                    }
                }
            }

            //проверяем на корректность

            switch (action) {
                case '+':
                case '-':
                case '*':
                case '/':
                    correctAction = true;
                    break;
            }
           if(correctArgs==false||numSum!=2||correctAction==false) System.out.println("Введенные данные некорректны");

        }while (correctArgs==false||numSum!=2||correctAction==false);
        
         //Считаем и выводим значение
        double result = 0;
        switch (action) {
            case '+':
                result=arg1+arg2;
                break;
            case '-':
                result=arg1-arg2;
                break;
            case '*':
                result=arg1*arg2;
                break;
            case '/':
                result=arg1/arg2;
                break;
        }

        if(result%10==0){//Если число целое, то дробную часть отбрасываем
            System.out.printf("= %.0f",result);
        } else {
            //Иначе сделаем 2 символа после запятой
            System.out.printf("= %.2f",result);
        }

        System.out.println();
    }

    //Метод, преобразующий символ в число
    private static int sumbolToNum(char sumbol) {
        int num;
        switch (sumbol) {
            case '0':
                num=0;
                break;
            case '1':
                num=1;
                break;
            case '2':
                num=2;
                break;
            case '3':
                num=3;
                break;
            case '4':
                num=4;
                break;
            case '5':
                num=5;
                break;
            case '6':
                num=6;
                break;
            case '7':
                num=7;
                break;
            case '8':
                num=8;
                break;
            case '9':
                num=9;
                break;
            default:
                num=10;//признак, что символ был некорректным
                break;
        }
        return num;
    }

}
