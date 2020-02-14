package com.converter;

import com.converter.service.XmlService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpListener {
    private final XmlService xmlService;

    public StartUpListener(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        xmlService.getCurrencies("http://www.cbr.ru/scripts/XML_daily.asp");
    }
}
