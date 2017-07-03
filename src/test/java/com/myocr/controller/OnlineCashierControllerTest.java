package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class OnlineCashierControllerTest extends AbstractSpringTest {
    @Test
    public void getReceipt() throws Exception {
        final String login = "+79523507654";
        final String password = "948346";

        mockMvc.perform(get("/online-cashier/" + login + "/" + password))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}
