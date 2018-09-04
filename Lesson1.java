import java.util.Scanner;
public class Lesson1 {
    //1. Создать пустой проект в IntelliJ IDEA и прописать метод main()
    public static void main (String [] args) {

        //2. Создать переменные всех пройденных типов данных, и инициализировать их значения
        System.out.println("2. Создать переменные всех пройденных типов данных, и инициализировать их значения:");
        typeData ();

        System.out.println("3. Написать метод, вычисляющий выражение a * (b + (c / d)) и возвращающий результат,\n где a, b, c, d – входные параметры этого метода");
        double method= evaluate (3, 2, 35, 56);
        System.out.println("Результат работы метода a * (b + (c / d)): "+method);

        System.out.println("4. Написать метод, принимающий на вход два числа, и проверяющий, что их сумма лежит в пределах от 10 до 20 (включительно), \n если да – вернуть true, в противном случае – false;");
        boolean rezultCompare=compare(12,7);
        System.out.println("Результат работы метода сравнения суммы чисел с диапазоном [10;20]; "+rezultCompare);

        System.out.println("5. Написать метод, которому в качестве параметра передается целое число,\n метод должен напечатать в консоль положительное число передали или отрицательное;\nЗамечание: ноль считаем положительным числом.");
        System.out.println(plusMinus(8));

        System.out.println("6.Написать метод, которому в качестве параметра передается целое число,\nметод должен вернуть true, если число отрицательное");
        boolean rezultMinusTrue=minusTrue(-5);
        System.out.println("Результат работы метода возврата истины в случае отрицательного значения: "+rezultMinusTrue);

        System.out.println("7. Написать метод, которому в качестве параметра передается строка, обозначающая имя,\nметод должен вывести в консоль сообщение «Привет, указанное_имя!»;");
        Scanner inputName=new Scanner(System.in);
        System.out.println("Введите имя (установите курсор в консоль): ");
        String inName=inputName.nextLine();
        hello(inName);

        System.out.println("8. Написать метод, который определяет, является ли год високосным, и выводит сообщение в консоль.\nКаждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.");
        Scanner inputYear=new Scanner(System.in);
        System.out.println("Введите год (установите курсор в консоль): ");
        double inYear=inputYear.nextDouble();
        leapYear(inYear);
         }

    //2. Создать переменные всех пройденных типов данных, и инициализировать их значения;
   static void typeData () {
       byte varByte=4;
       short varShort=7;
       char varChar='k';
       int varInt=354;
       long varLong=79432856L;
       float varFloat=32.54f;
       double varDouble=3421.34;
       boolean varBoolean=true;
       String varString="Мама мыла раму";
       System.out.println(varByte+", "+varShort+", "+varChar+", "+varInt+", "+varLong+", "+varFloat+", "+varDouble+", "+varBoolean+", "+varString);
   }

   /*3. Написать метод, вычисляющий выражение a * (b + (c / d)) и возвращающий результат,
    где a, b, c, d – входные параметры этого метода;
     */
    static double evaluate (int a, int b, int c, double d) {
        return a * (b + (c / d));
    }

    /*4. Написать метод, принимающий на вход два числа, и проверяющий,
    что их сумма лежит в пределах от 10 до 20 (включительно),
    если да – вернуть true, в противном случае – false;
     */
    static boolean compare (int a, int b) {
        return (((a+b)>=10)&&((a+b)<=20));
    }

    /*5. Написать метод, которому в качестве параметра передается целое число,
    метод должен напечатать в консоль положительное число передали или отрицательное;
    Замечание: ноль считаем положительным числом.;
     */
    static String plusMinus (int a) {
        return (a>=0) ? "Число положительное" : "Число отрицательное";
    }

    /*6. Написать метод, которому в качестве параметра передается целое число,
    метод должен вернуть true, если число отрицательное;
     */
    static boolean minusTrue (int a) {
        return (a<0);
    }

    /*7. Написать метод, которому в качестве параметра передается строка, обозначающая имя,
    метод должен вывести в консоль сообщение «Привет, указанное_имя!»;
     */
    static void hello (String name){
        System.out.println("Привет, "+name+"!");
    }
    /*Написать метод, который определяет, является ли год високосным, и выводит сообщение в консоль.
    Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
     */
    static void leapYear (double year) {
        if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
            System.out.println("Год високосный");
        } else {
            System.out.println("Год не високосный");
        }
    }
}
