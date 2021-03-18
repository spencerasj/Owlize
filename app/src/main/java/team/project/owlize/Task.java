package team.project.owlize;

public class Task implements Add, Delete, Clear, Remind{

    private String name;
    private double dueDate;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getDueDate() {
        return dueDate;
    }

    public void setDueDate(double dueDate) {
        this.dueDate = dueDate;
    }

    public void add() {

    }

    public void clear() {

    }

    public void delete() {

    }
    
    public void remind() {

    }
}
