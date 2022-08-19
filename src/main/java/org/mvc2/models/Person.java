package org.mvc2.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Name must not be empty!") // если поле назначается пустым, выводится сообщение
    @Size(min = 2, max = 30, message = "Name must be between 2 and 10 characters!") // размер строки
    private String name;
    @Min(value = 1, message = "Must be greater than 1!") // минимально возможное значение
    private int age;
    @NotEmpty(message = "Name must not be empty!")
    @Email(message = "Enter email by example(vjkfd@tut.by)") // df@tut.by - тип вводимой строки
    private String email;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
