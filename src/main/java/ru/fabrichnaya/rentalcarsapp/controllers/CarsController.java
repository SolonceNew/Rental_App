package ru.fabrichnaya.rentalcarsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fabrichnaya.rentalcarsapp.models.Car;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.services.impL.CarsServiceImpl;
import ru.fabrichnaya.rentalcarsapp.services.impL.PeopleServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarsServiceImpl carsService;
    private final PeopleServiceImpl peopleService;

    @Autowired
    public CarsController(CarsServiceImpl carsService, PeopleServiceImpl peopleService) {
        this.carsService = carsService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "cars_per_page", required = false) Integer carsPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || carsPerPage == null)
            model.addAttribute("cars", carsService.findAll(sortByYear));
        else
            model.addAttribute("cars", carsService.findWithPagination(page, carsPerPage, sortByYear));

        return "cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("car", carsService.findOne(id));

        Person carTenant = carsService.getCarTenant(id);

        if (carTenant != null)
            model.addAttribute("tenant", carTenant);
        else
            model.addAttribute("people", peopleService.findAll());

        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") Car car) {
        return "cars/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("car") @Valid Car car,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "cars/new";

        carsService.save(car);
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("car", carsService.findOne(id));
        return "cars/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "cars/edit";

        carsService.update(id, car);
        return "redirect:/cars";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        carsService.delete(id);
        return "redirect:/cars";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        carsService.release(id);
        return "redirect:/cars/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        carsService.assign(id, selectedPerson);
        return "redirect:/cars/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "cars/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("cars", carsService.searchByName(query));
        return "cars/search";
    }
}
