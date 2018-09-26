import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lesson_7 {
    public static void main(String[] args) {
        Scanner task = new Scanner(System.in);
        int taskNumber;//Для ветвления по номеру задачи
        System.out.println("Введите номер задачи:\n1 - задача на взаимодействие классов в рамках ООП (о котах и мисках),\n2 - задача на построение графического интерфейса в рамках swing (введение и отображение ФИО).");
        taskNumber=task.nextInt();
        switch (taskNumber) {
            case 1:
                System.out.println("1. Расширить задачу про котов и тарелки с едой\n" +
                        "2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды (например, в миске 10 еды, а кот пытается покушать 15-20)\n" +
                        "3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны). Если коту удалось покушать (хватило еды), сытость = true\n" +
                        "4. Считаем, что если коту мало еды в тарелке, то он ее просто не трогает, то есть не может быть наполовину сыт (это сделано для упрощения логики программы)\n" +
                        "5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки и потом вывести информацию о сытости котов в консоль\n" +
                        "6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку");
                System.out.println();//Для отступа между постановкой задачи и выводом результата
                CatsAndPlate();
                break;
            case 2:
                System.out.println("1 Необходимо создать окно с 3мя полями - по обсуждкнию на лекции - (Фамилия, Имя, Отчество) и кнопку\n" +
                        "2 По нажатии кнопки открывается еще 1 окно с тремя полями и кнопкой, в поля вводишь ФИО и нажимаешь кнопку ОК.\n" +
                        "3 Дополнительное окно закрывается и в основном окне в поле оседает ФИО.\n");
                nameEntryWindow();
                break;
            default:
                System.out.println("Задача была выбрана некорректно");
        }
    }

    public static void CatsAndPlate(){//главный метод задачи 1
        Cat[] catsArray= {new Cat("Барсик", 5),   //Создаем массив котов
                    new Cat("Пушок",12),
                    new Cat("Бегемот",25),
                    new Cat("Рыжик",12),
                    new Cat("Том",14)
                    };
        Plate plate=new Plate(50);//создаём тарелку ёмкостью 100

        //Просим всех котов покушать из тарелки
        System.out.print("До начала общего кормления ");
        plate.info();
        for (Cat cat:catsArray) {
                System.out.println("Кормим кота "+cat.getName()+" с аппетитом "+cat.getAppetite()+":");
                cat.eat(plate);
                if (cat.getSatiety()){
                    System.out.print("   Кот "+cat.getName()+" сыт");
                } else {
                    System.out.println("   Кот "+cat.getName()+" всё ещё голоден");
                    System.out.println("Наполняем миску");
                    plate.fullPlate();
                    cat.eat(plate);
                    System.out.print("   Кот "+cat.getName()+" сыт");
                }
                System.out.print(", после чего ");
                plate.info();
                }



    }

    public static void nameEntryWindow() {//главный метод задачи 2
        MainWindow watchWindow=new MainWindow();
    }

}

class Cat {
    public String getName () {//геттер имени
        return name;
    }

    public int getAppetite () {//геттер уровня аппетита
        return appetite;
    }

    public boolean getSatiety (){
        return satiety;
    }

    private  String name;//Кошачье имя
    private int appetite;//Уровень кошачьего аппетита
    private boolean satiety=false;//Поле сытости, по умолчанию коты голодны

    public Cat (String name,int appetite) {//Конструктор
        this.name=name;
        this.appetite=appetite;
    }

    public void eat (Plate plate) {//метод принятия пищи котом
        if (plate.getFood()<appetite){
            System.out.println("В миске слишком мало еды для кота "+name);
        } else {
            plate.decreaseFood(appetite);
            satiety = true;
        }
    }
}


class Plate {
    public int getFood () {//геттер кол-ва еды
        return food;
    }
    private int food;//Кол-во еды
    private int volume;//Ёмкость миски

    public Plate (int volume) {//конструктор
        this.volume=volume;
        this.food=volume;//Изначально миска полная
    }

    public void decreaseFood (int appetite){//Метод, уменьшающий кол-во еды на величину аппетита кота
        if (food<appetite){
            food=0;//не может быть отрицательного количества еды; хотя в нашей реализации в эту ветку вхождений не будет,
                    //так как в методе eat класса Cat уже реализована проверка
        }else {
            food -= appetite;
        }
    }

    public void fullPlate (){//Метод наполнения миски
        food=volume;
    }

    public void info (){//Метод вывода информации
        System.out.println("в миске "+food+" единиц еды");
    }
}

class MainWindow extends JFrame {//класс, формирующий главное окно

    public MainWindow() {//Конструктор, формирующий главное окно по умолчанию
        setTitle("Окно отображения ФИО");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 350, 120);

        //Добавим кнопку внизу, используя компоновщик для главного окна
        JButton enterButton=new JButton("Ввести ФИО");
        setLayout(new BorderLayout());
        add(enterButton,BorderLayout.SOUTH);

        //Добавим лейблы в центре, используя GridLayout на отдельной панели: 3 лейбла на имена строк и ещё три: для отображения ФИО
        JPanel labelPanel=new JPanel(new GridLayout(3,3));
        JLabel nameLabel=new JLabel("Имя");
        JLabel patronymicLabel=new JLabel("Отчество");
        JLabel surnameLabel =new JLabel("Фамилия");
        JLabel name=new JLabel("_____________");
        JLabel patronymic=new JLabel("_____________");
        JLabel surname=new JLabel("_____________");
        labelPanel.add(nameLabel);
        labelPanel.add(name);
        labelPanel.add(patronymicLabel);
        labelPanel.add(patronymic);
        labelPanel.add(surnameLabel);
        labelPanel.add(surname);

        add(labelPanel,BorderLayout.CENTER);

        //Добавим слушатель на кнопку
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnterWindow enterWindow=new EnterWindow();//Вызываем новое окно - для ввода данных


                setVisible(false);
            }
        });
        setVisible(true);
    }

    public MainWindow(String inputName, String inputPatronymic,String inputSurname) {//Конструктор, формирующий главное окно после ввода ФИО
        setTitle("Окно отображения ФИО");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 350, 120);

        //Добавим кнопку внизу, используя компоновщик для главного окна
        JButton enterButton=new JButton("Ввести ФИО");
        setLayout(new BorderLayout());
        add(enterButton,BorderLayout.SOUTH);

        //Добавим лейблы в центре, используя GridLayout на отдельной панели: 3 лейбла на имена строк и ещё три: для отображения ФИО
        JPanel labelPanel=new JPanel(new GridLayout(3,3));
        JLabel nameLabel=new JLabel("Имя");
        JLabel patronymicLabel=new JLabel("Отчество");
        JLabel surnameLabel =new JLabel("Фамилия");
        JLabel name=new JLabel(inputName);
        JLabel patronymic=new JLabel(inputPatronymic);
        JLabel surname=new JLabel(inputSurname);
        labelPanel.add(nameLabel);
        labelPanel.add(name);
        labelPanel.add(patronymicLabel);
        labelPanel.add(patronymic);
        labelPanel.add(surnameLabel);
        labelPanel.add(surname);

        add(labelPanel,BorderLayout.CENTER);

        //Добавим слушатель на кнопку
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnterWindow enterWindow=new EnterWindow();//Вызываем новое окно - для ввода данных
                //Убираем окно отображения
                setVisible(false);
            }
        });
        setVisible(true);
    }

}

class EnterWindow extends JFrame {//класс, формирующий окно ввода данных
    public EnterWindow () {


        setTitle("Окно ввода ФИО");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 350, 120);

        //Добавим кнопку внизу, используя компоновщик для главного окна
        JButton okButton=new JButton("OK");
        setLayout(new BorderLayout());
        add(okButton,BorderLayout.SOUTH);

        //Добавим поля ввода текста в центре, используя GridLayout на отдельной панели: 3 лейбла на имена строк, ещё три текстовых поля: для ввода ФИО
        JPanel labelPanel=new JPanel(new GridLayout(3,3));
        JLabel nameLabel=new JLabel("Имя");
        JLabel patronymicLabel=new JLabel("Отчество");
        JLabel surnameLabel =new JLabel("Фамилия");
        JTextField name=new JTextField();
        JTextField patronymic=new JTextField();
        JTextField surname=new JTextField();
        labelPanel.add(nameLabel);
        labelPanel.add(name);
        labelPanel.add(patronymicLabel);
        labelPanel.add(patronymic);
        labelPanel.add(surnameLabel);
        labelPanel.add(surname);

        add(labelPanel,BorderLayout.CENTER);

        //Добавим слушатель на кнопку подтверждения ввода
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Нужно взять данные из текстовых полей, передать их в лейблы главного окна и вызвать главное окно
                MainWindow watchWindow=new MainWindow(name.getText(),patronymic.getText(),surname.getText());
                //Убираем окно ввода данных
                setVisible(false);
            }
        });

        setVisible(true);
    }
}