package WorkersBase;

public class lesson5 {
    public static void main(String[] args) {
        //Создать массив из 5 сотрудников
        Workers[] workersArray=new Workers[5];//// Вначале объявляем массив объектов

        // потом для каждой ячейки массива задаем объект:
        workersArray[0]=new Workers("Иван Иванов","директор","dir@mail.com","111-111-11-11",2000f,48);
        workersArray[1]=new Workers("Николай Петров","менеджер","manager@mail.com","222-222-22-22",1800f,35);
        workersArray[2]=new Workers("Геннадий Пупкин","инженер","ingener@mail.com","333-333-33-33",1200f,27);
        workersArray[3]=new Workers("Михаил Иванов","логист","logist@mail.com","444-444-44-44",1600f,24);
        workersArray[4]=new Workers("Олег Сидоров","специалист по закупкам","zakup@mail.com","555-555-55-55",900f,42);

        //С помощью цикла вывести информацию только о сотрудниках старше 40 лет
        System.out.println("Сотрудники старше 40 лет:");
        for (Workers worker:workersArray){
            if ( worker.getAge()>40) worker.printWorker();
        }
    }

}

//Создать класс «Сотрудник» с полями: ФИО, должность, email, телефон, зарплата, возраст;
class Workers {
    private String fullName;// Поле "ФИО"
    private String position;// Поле "должность"
    private String email;// Поле "email"
    private String phone;// Поле "телефон"
    private float salary;// Поле "зарплата"
    private int age;// Поле "возраст"

    //Конструктор класса должен заполнять эти поля при создании объекта;
    public Workers (String fullName,String position, String email,String phone,float salary,int age){
        this.fullName=fullName;
        this.position=position;
        this.email=email;
        this.phone=phone;
        this.salary=salary;
        this.age=age;
    }

    //Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль;
    public void printWorker () {
        System.out.println("ФИО: "+this.fullName+", должность: "+this.position+", email: "+email+", телефон: "+phone+", зарплата: "+salary+"$, возраст: "+age+";");
    }

    //Пишем геттер для доступа к возрасту
    public int getAge (){
        return  this.age;
    }
}