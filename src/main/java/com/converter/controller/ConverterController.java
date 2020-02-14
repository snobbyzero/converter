package com.converter.controller;

import com.converter.entity.Conversion;
import com.converter.entity.Currency;
import com.converter.service.ConvService;
import com.converter.service.CurrencyRepositoryService;
import com.converter.service.XmlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/converter")
@SessionAttributes({"conversion", "result", "currencyList"})
public class ConverterController {
    private final ConvService convService;
    private final CurrencyRepositoryService currencyRepositoryService;

    public ConverterController(ConvService convService, CurrencyRepositoryService currencyRepositoryService) {
        this.convService = convService;
        this.currencyRepositoryService = currencyRepositoryService;
    }

    @ModelAttribute
    public Conversion conversion() {
        return new Conversion();
    }

    @GetMapping
    public String getConverter(Model model) {
        List<Currency> currencyList = currencyRepositoryService.loadCurrencies();
        model.addAttribute("currencyList", currencyList);
        return "converter";
    }

    @PostMapping
    public String showResult(@Valid Conversion conversion, Errors errors, Model model, Principal principal) {
        if (errors.hasErrors()) {
            return "converter";
        }
        model.addAttribute("result", convService.calculate(conversion, principal.getName()));
        return "redirect:/converter";
    }
}
