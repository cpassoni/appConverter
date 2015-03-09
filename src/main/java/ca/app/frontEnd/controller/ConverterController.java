package ca.app.frontEnd.controller;


import ca.app.user.service.ISVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConverterController {


    @Autowired
    private ISVService isvService;


    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/converter")
    public String converter(Model model) {
        model.addAttribute("principal", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "converter";
    }
}
