package com.converter.service;

import com.converter.entity.Currency;
import com.converter.entity.ExchangeRate;
import com.converter.repository.CurrencyRepository;
import com.converter.repository.ExchangeRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class XmlService {

    private Document document;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public XmlService(CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    private void parseXml(String URL) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(URL);

        document.getDocumentElement().normalize();

        this.document = document;
    }

    private NodeList getNodeList() {
        return this.document.getElementsByTagName("Valute");
    }

    public Date getDate() {
        Element valCursElement = (Element) this.document.getElementsByTagName("ValCurs").item(0);

        return reformatDate(valCursElement.getAttribute("Date"));
    }

    @Transactional
    public List<Currency> getCurrencies(String URL) {
        List<Currency> currencyList = new ArrayList<>();
        try {
            parseXml(URL);
            NodeList nodeList = getNodeList();
            Date date = getDate();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element element = (Element) node;
                String charcode = element.getElementsByTagName("CharCode").item(0).getTextContent();

                if (exchangeRateRepository.findByDateAndCurrencyCharCode(date, charcode).isEmpty()) {
                    ExchangeRate exchangeRate = new ExchangeRate(
                            Float.parseFloat(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")),
                            date
                    );
                    Currency currency = currencyRepository.findByCharCode(charcode).orElse(
                            new Currency(
                                    element.getAttribute("ID"),
                                    Integer.parseInt(element.getElementsByTagName("NumCode").item(0).getTextContent()),
                                    element.getElementsByTagName("CharCode").item(0).getTextContent(),
                                    Float.parseFloat(element.getElementsByTagName("Nominal").item(0).getTextContent()),
                                    element.getElementsByTagName("Name").item(0).getTextContent()
                            ));
                    currency.addExchangeRate(exchangeRate);
                    exchangeRate.setCurrency(currency);
                    currencyList.add(currency);
                    currencyRepository.save(currency);
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.getMessage();
        }
        return currencyList;
    }

    public Date reformatDate(String date) {
        java.util.Date initDate = null;
        try {
            initDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return Date.valueOf(formatter.format(initDate));
    }

}
