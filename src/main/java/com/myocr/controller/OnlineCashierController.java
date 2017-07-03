package com.myocr.controller;

import com.myocr.controller.json.onlinecashier.OnlineCashierReceipt;
import com.myocr.controller.json.onlinecashier.OnlineCashierReceiptsLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/online-cashier")
public class OnlineCashierController {

    private static final String ROOT_URL = "http://proverkacheka.nalog.ru:8888";
    private final static String GET_URLS = "/v1/extract?sendToEmail=0&fileType=json";

    private static final Logger log = LoggerFactory.getLogger(OnlineCashierController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public OnlineCashierController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{login}/{password}")
    public OnlineCashierReceipt[] getReceipt(
            @PathVariable String login, @PathVariable String password) {

        log.info(String.format("login %s password %s", login, password));

        // basic authorization for next requests
        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor(login, password));

        // get link with receipts
        OnlineCashierReceiptsLink receiptsLink = restTemplate.getForObject(
                ROOT_URL + GET_URLS, OnlineCashierReceiptsLink.class);
        log.info("Received link " + receiptsLink.getUrl());

        // get receipts
        return restTemplate.getForObject(
                ROOT_URL + receiptsLink.getUrl(), OnlineCashierReceipt[].class);
    }
}
