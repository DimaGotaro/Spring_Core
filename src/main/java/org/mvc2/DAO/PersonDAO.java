package org.mvc2.DAO;

//import com.sun.tools.javac.code.Attribute;
import org.mvc2.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import java.util.Objects;

@Component
public class PersonDAO {

    // с использованием базы данных

    // через jdbcTemplate
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // без jdbcTemplate
//    private static final String URL = "jdbc:postgresql://localhost:5432/spring1_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "vbyfnj56";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver"); // проверяем загружен ли драйвер
//            // в оператиную память
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // подключение к базе данных
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public List<Person> index() {

        // через jdbcTemplate
        // с собственным маппером:
//        return jdbcTemplate.query("select * from Person ORDER BY id", new PersonMapper());
        // можно со встроенным маппером, но названия полей в классе Person и столбцов в таблице должны быть одинаковыми
        return jdbcTemplate.query("select * from Person ORDER BY id", new BeanPropertyRowMapper<>(Person.class));
        // PersonMapper не нужен)


        // через jdbc api
//        List<Person> people = new ArrayList<>();
//
//        try {
//            Statement statement = connection.createStatement(); // создали запрос
//            String SQL = "select * from Person ORDER BY id";
//            ResultSet resultSet = statement.executeQuery(SQL); // отправляет запрос и возвращает данные по запросу,
//            // не изменяет данные
//
//            while (resultSet.next()) {
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//
//                people.add(person);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return people;
    }

    public Person show(int id) {

        // через jdbcTemplate
        // query - что бы получить данные
        return jdbcTemplate.query("select * from Person where id = ?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);


        // через jdbc api
//        Person person = null;
//
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("select * from Person where id = ?");
//
//            preparedStatement.setInt(1, id);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next(); // если строка одна, цикл не нужен
//
//            person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            person.setEmail(resultSet.getString("email"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return person;
    }

    public void save(Person person) {

        // через jdbcTemplate
        // query - что бы получить данные
        // получим максимальный id из базы данных
        int m = 0;
        Person n = jdbcTemplate.query("select * from Person where id = (select max(id) from Person)",
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        if (n != null) {
            m += n.getId();
        }
        m += 1;

        // update - отправляет запрос, ничего в ответ не получает
        jdbcTemplate.update("insert into person values (?, ?, ?, ?)",
                m, person.getName(), person.getAge(), person.getEmail());


        // через jdbc api
//        try {
//            Statement statement1 = connection.createStatement();
//            String SQL1 = "select max(id) from Person";
//            ResultSet resultSet1 = statement1.executeQuery(SQL1);
//            int m = 0;
//            resultSet1.next()
//            m += resultSet1.getInt("max") + 1;
//
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("insert into person values (?, ?, ?, ?)");
//
//            preparedStatement.setInt(1, m);
//            preparedStatement.setString(2, person.getName()); // 2 - второй вопрос
//            preparedStatement.setInt(3, person.getAge());
//            preparedStatement.setString(4, person.getEmail());
//
//            preparedStatement.executeUpdate();
//
//            // Опасность SQL-инъекции
////            Statement statement2 = connection.createStatement();
////            String SQL2 = "insert into person values (" + m + ", '" + person.getName() + "', " + person.getAge() +
////                    ", '" + person.getEmail() + "')";
////            statement2.executeUpdate(SQL2); // отправляет запрос, изменяет данные, ничего не возвращает
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


    public void edit(int id, Person person) {

        // через jdbcTemplate
        jdbcTemplate.update("update Person set name = ?, age = ?, email = ? where id = ?",
                person.getName(), person.getAge(), person.getEmail(), id);


        // через jdbc api
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("update Person set name = ?, age = ?, email = ? where id = ?");
//            preparedStatement.setString(1, person.getName());
//            preparedStatement.setInt(2, person.getAge());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.setInt(4, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void delete(int id) {

        // через jdbcTemplate
        jdbcTemplate.update("delete from Person where id = ?", id);


        // через jdbc api
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("delete from Person where id = ?");
//            preparedStatement.setInt(1, id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }




    // только с использованием коллекции
//    private static int PEOPLE_COUNT;
//    private List<Person> people;

//    {
//        people = new ArrayList<>();
//
//        people.add(new Person(++PEOPLE_COUNT, "Tom", 23, "asd@tut.by"));
//        people.add(new Person(++PEOPLE_COUNT, "Bob", 56, "sldk@gmail.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Maik", 27, "oekf@gmail.com"));
//        people.add(new Person(++PEOPLE_COUNT, "Greta", 35, "owdm@tut.by"));
//        people.add(new Person(++PEOPLE_COUNT, "Rox", 61, "ndjlk@tut.by"));
//    }

//    public int p_count() {
//        return ++PEOPLE_COUNT;
//    }

//    public List<Person> index() {
//        return people;
//    }

//    public Person show(int id) {
//        return people.stream().filter(d -> d.getId() == id).findAny().orElse(null);
         // из списка ищем элемент у котороко id равен принимаемому id, находим его,
         // если нет возвращаем null
//        return null;
//    }

//    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
//    }

//    public void edit(int id,Person person) {
//        if (0 < person.getId() & person.getId() <= PEOPLE_COUNT & person.getName() != null) {
//            Objects.requireNonNull(people.stream().filter(d -> d.getId() == person.getId()).
//                    findAny().orElse(null)).setName(person.getName());

//            show(id).setName(person.getName());
//            show(id).setAge(person.getAge());
//            show(id).setEmail(person.getEmail());
//        }
//    }

//    public void delete(int id) {
//            people.remove(show(id));

//            people.removeIf(p -> p.getId() == id); // можно так
//    }
}
