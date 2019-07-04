package pl.mbierut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.services.WeatherSearch;

@Controller
public class CityController {
    private WeatherSearch weatherSearch;

    private CityController(WeatherSearch weatherSearch){
        this.weatherSearch = weatherSearch;
    }

    @GetMapping("/")
    public String sendHome(Model model) {
        model.addAttribute("city", "");
        return "home";
    }

    @GetMapping("/results")
    public String sendBack(){
        return "error";
    }

    @PostMapping("/results")
    public String showResults(@RequestParam String city, Model model) {
        model.addAttribute("city", city);
        String data = weatherSearch.getOutputData(city);
        model.addAttribute("data", data);
        return "results";
    }
}
