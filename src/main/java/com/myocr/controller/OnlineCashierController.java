package com.myocr.controller;

import com.myocr.controller.json.OnlineCashierReceipt;
import com.myocr.controller.json.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/online-cashier")
public class OnlineCashierController {
    private static final Logger log = LoggerFactory.getLogger(OnlineCashierController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OnlineCashierController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{login}/{password}")
    public OnlineCashierReceipt getReceipt(
            @PathVariable long login, @PathVariable long password) {

        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());
        return new OnlineCashierReceipt("hello world");
    }
}
