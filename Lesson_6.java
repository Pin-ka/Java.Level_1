
public class Lesson_6 {

    public static void main(String[] args) {
     System.out.println("1 - Работа с абстрактными классами в ООП:");
     System.out.println();

        Animals[] arrayAnimals=new Animals[4];

        arrayAnimals[0]=new Cat("Мурзик");
        arrayAnimals[1]=new Dog("Барбос");
        arrayAnimals[2]=new LibertyCat ("Кот-бегемот",500,3);
        arrayAnimals[3]=new LibertyDog ("Палкан",700,2,50);

        for (Animals animal:arrayAnimals) {//Опрашиваем экземпляры в цикле
            animal.run(300);
            animal.jump(2);
            animal.swim(8);
        }
        System.out.println("_______________________________");

        System.out.println("2 - Работа со строками: привести строку к нормальному виду");
        System.out.println("Исходная строка:");
        String str1 = " Предложение один    Теперь предложение два     Предложение три";
        System.out.println(str1);

        String str2 = str1.replaceAll(" +", " ");

        StringBuilder stringBuilder = new StringBuilder(str2);

        for(int i = 1; i < str2.length(); i++) {
            if(str2.charAt(i) >= 'A' && str2.charAt(i) <= 'Я' && (i-1)!=0) {
                stringBuilder.setCharAt(i-1, '.');
            }
        }

        if (stringBuilder.charAt(stringBuilder.length()-1)!='.') stringBuilder.append(".");

        //Посчитаем кол-во точек, кол-во точек = кол-ву предложений
        int dotCounter=0;
        for(int i = 1;  i <stringBuilder.length(); i++) {
            if(stringBuilder.charAt(i) == '.') {
                dotCounter++;
            }
        }
        //Создадим строковый массив: одна строка - одно предложение вместе с точкой
        String [] arrayProposal=new String[dotCounter];
        int openIndex=0;//отмечает начало предложения
        int proposalIndex=0;//индекс предложения в массиве
        for (int i=1;i<stringBuilder.length();i++) {
            if(stringBuilder.charAt(i) == '.') {
                arrayProposal[proposalIndex]=stringBuilder.substring(openIndex,i+1);
                openIndex=i+1;
                proposalIndex++;
            }
        }

        stringBuilder.delete(0,stringBuilder.length());//Очистим нашу строку, чтобы не создавать новую
        //И перепишем её, но на этот раз с пробелами
        stringBuilder.append(arrayProposal[0]+" ");
        for (int i=1;i<proposalIndex;i++){
            if(i!=proposalIndex-1){stringBuilder.append(arrayProposal[i]+" ");
            }else {stringBuilder.append(arrayProposal[i]);}
        }
        if (stringBuilder.charAt(0)==' ') stringBuilder.delete(0,1);//Убираем пробел вначале 1го предложения, если он остался
        System.out.println("Обработанная строка:");
        System.out.println(stringBuilder.toString());
    }

    /*Животные могут выполнять действия:
    бежать, плыть, перепрыгивать препятствие.
    В качестве параметра каждому методу передается величина,
    означающая или длину препятствия (для бега и плавания), или высоту (для прыжков);
     */
    public static abstract class Animals {
        protected String name;

        protected int runLimit = 0;
        protected double jumpLimit = 0;
        protected int swimLimit = 0;

        //Конструктор с передачей имени
        public Animals(String name) {
            this.name = name;
        }

        public void run(int distance) {//Животному нужно бежать dictance м
            if (runLimit > 0 && distance <= runLimit) {
                System.out.println(this.name + " пробежал " + distance + " метров");
            } else {
                System.out.println(this.name + " не сможет пробежать столько!");
            }
        }

        public void swim(int distance) {//Животному нужно плыть distance м
            if (swimLimit > 0 && distance <= swimLimit) {
                System.out.println(this.name + " проплыл " + distance + " метров");
            } else {
                System.out.println(this.name + " не сможет проплыть столько!");
            }
        }

        public void jump(double height) {//Животному нужно подпрыгнуть на height м
            if (jumpLimit > 0 && height <= jumpLimit) {
                System.out.println(this.name + " подпрыгнул на " + height + " метров");
            } else {
                System.out.println(this.name + " не сможет прыгнуть так высоко!");
            }
        }
    }

    //Создать классы Собака и Кот с наследованием от класса Животное;
    public static class Cat extends Animals {

        // У каждого животного есть ограничения на действия (бег: кот – 200 м., прыжок: кот – 2 м., плавание: кот не умеет плавать);
        public Cat (String name) {//Переопределение конструктора с именем
            super("Кот " + name);
            this.runLimit=200;
            this.jumpLimit=2;
        }

        public void swim (int distance) {System.out.println("Кот "+name+" не умеет плавать!");}
    }

    public  static class Dog extends Animals {

        // У каждого животного есть ограничения на действия (бег: собака – 500 м.; прыжок: собака – 0.5 м.; плавание: собака – 10 м.);
        public Dog(String name) {//Переопределение конструктора с именем
            super("Собака " + name);
            this.runLimit = 500;
            this.jumpLimit = 0.5;
            this.swimLimit = 10;
        }
    }

    public static class LibertyCat extends Cat {
        //Переопределяем конструктор класса Cat для возможности ввода любых ограничений на действия
        public LibertyCat (String name,int runLimit,int jumpLimit) {
            super(name);
            this.jumpLimit=jumpLimit;
            this.runLimit=runLimit;
        }

    }

    public static class LibertyDog extends Dog {

        //Переопределяем конструктор класса Dog для возможности ввода любых ограничений на действия
        public LibertyDog (String name,int runLimit,int jumpLimit,int swimLimit) {
            super(name);
            this.jumpLimit=jumpLimit;
            this.runLimit=runLimit;
            this.swimLimit=swimLimit;
        }
    }
}
