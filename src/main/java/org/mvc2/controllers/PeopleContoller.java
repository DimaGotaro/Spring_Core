package org.mvc2.controllers;

import javax.validation.Valid;
import org.mvc2.DAO.PersonDAO;
import org.mvc2.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people") // ссылки на методы в классе будут начинаться с /people/
public class PeopleContoller {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleContoller(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping() // если пусто, то адрес - /people
    public String index(Model model) { // метод для возвращения всех пользователей(списка)
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // с помощью @PathVariable получим id из адресной строки
    public String show(@PathVariable("id") int id, Model model) {
        // метод для возврата инф о конкретном пользователе
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

//    @GetMapping("/new")
//    public String newG() {
//        return "people/new";
//    }

//    @PostMapping() // POST должен быть определён отдельно, нельзя вызвать без отправки данных в POST
//    public String newP(@RequestParam("name") String name,
//                       @RequestParam("age") int age,
//                       @RequestParam("email") String email) {
//        personDAO.getPeople().add(new Person(personDAO.p_count(), name, age, email));
//        return "people/success";
//    }
//    @GetMapping("/new2") // то же самое
//    public String newG2(Model model) {
//        model.addAttribute("persModel", new Person());
//        return "people/new2";
//    }
    @GetMapping("/new2") // то же самое
    public String newG2(@ModelAttribute("persModel") Person person) { // когда отправляем из формы в html,
        // то посылается объект только с полями заданными в форме
        return "people/new2";
    }
    @PostMapping("/p") // два post запроса не должны указывать на один URL
    public String newP2(@ModelAttribute("persModel") @Valid Person person,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new2";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editG(Model model,
                        @PathVariable("id") int id) {
        model.addAttribute("editMg", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}") // тк в форме назначаем только name, то приходит объект только с полем name,
    // а id ловим из URL
    public String editP(@ModelAttribute("editMg") @Valid Person person, BindingResult bindingResult,
                        @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.edit(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deleteD(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

//    @ModelAttribute("modA") // добавляет ко всем моделям пару ключ(modA)-значение(Hello),
//    // доступен во всех методах контроллера
//    public String hey() {
//        return "Hello";
//    }
}
