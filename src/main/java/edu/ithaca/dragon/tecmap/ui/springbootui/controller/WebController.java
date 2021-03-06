package edu.ithaca.dragon.tecmap.ui.springbootui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class WebController {

    public WebController() {
        super();
    }

    @GetMapping()
    public String viewLandingPage() {
        return "Landing";
    }

    @GetMapping("/structureTree/{courseId}")
    public String viewStructureTree(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "StructureGraph";
    }

    @GetMapping("/cohortTree/{courseId}")
    public String viewCohortTree(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "CohortGraph";
    }

    @GetMapping("/connectResources/{courseId}")
    public String viewConnectResources(@PathVariable("courseId") String courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "ConnectResources";
    }
}