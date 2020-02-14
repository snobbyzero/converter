package com.converter.controller;

import com.converter.entity.Conversion;
import com.converter.service.ConvService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final ConvService convService;

    public HistoryController(ConvService convService) {
        this.convService = convService;
    }

    @GetMapping
    public String history(Principal principal, Model model) {
        List<Conversion> conversions = convService.findUserByUsername(principal.getName()).getConversionList();
        model.addAttribute("conversions", conversions);
        return "history";
    }
}
