public class AssociateProfessor extends Teacher {
    private double factor = 1.2;
    public AssociateProfessor(String name, int age, String post, double salary) {
        super(name, age, post, salary);
    }
    public void introduce() {
        System.out.println("姓名：" + getName() + ";年龄:" + getAge() +
                ";职称:" + getPost() + ";基本工资:" + factor * getSalary());
    }
}