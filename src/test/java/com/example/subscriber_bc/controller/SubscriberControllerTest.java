package com.example.subscriber_bc.controller;

import com.example.subscriber_bc.model.Subscriber;
import com.example.subscriber_bc.service.SubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class SubscriberControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private SubscriberController subscriberController;

    @Mock
    private SubscriberService subscriberService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = standaloneSetup(subscriberController).build();
    }

    @Test
    void createSubscriber_ShouldReturnCreatedSubscriber_WhenSuccessful() throws Exception {
        //Given
        Subscriber subscriber = new Subscriber();
        subscriber.setSubscriberId(1L);
        subscriber.setFname("Il");
        subscriber.setLname("Bill");
        subscriber.setMail("il.bill@gmail.com");
        subscriber.setPhone("00000000002");

        when(subscriberService.createSubscriber(any(Subscriber.class))).thenReturn(subscriber);

        // When & Then
        mockMvc.perform(post("/subscribers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"fname" : "Il",
                                	"lname" : "Bill",
                                	"mail" : "il.bill@gmail.com",
                                    "phone": "00000000002"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subscriberId").value(1L))
                .andExpect(jsonPath("$.fname").value("Il"))
                .andExpect(jsonPath("$.lname").value("Bill"))
                .andExpect(jsonPath("$.mail").value("il.bill@gmail.com"))
                .andExpect(jsonPath("$.phone").value("00000000002"));
    }

    @Test
    void createSubscriber_ShouldReturnInternalError_WhenSubscriberExists() throws Exception {

        //Given
        when(subscriberService.createSubscriber(any(Subscriber.class))).thenReturn(null);

        mockMvc.perform(post("/subscribers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                	"fname" : "Il",
                                	"lname" : "Bill",
                                	"mail" : "il.bill@gmail.com",
                                    "phone": "00000000002"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Un abonné existe déjà avec cette adresse mail et/ou ce numéro"));
    }

    @Test
    void getSubscriber_ShouldReturnSubscriber_WhenCriteriaMatch() throws Exception {
        //Given
        Subscriber subscriber = new Subscriber();
        subscriber.setSubscriberId(1L);
        subscriber.setFname("Il");
        subscriber.setLname("Bill");
        subscriber.setMail("il.bill@gmail.com");
        subscriber.setPhone("00000000002");

        when(subscriberService.getSubscribersByCriteria(anyMap())).thenReturn(Collections.singletonList(subscriber));

        //When & Then
        mockMvc.perform(get("/subscribers")
                        .param("mail", "il.bill@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subscriberId").value(1L))
                .andExpect(jsonPath("$[0].fname").value("Il"))
                .andExpect(jsonPath("$[0].lname").value("Bill"))
                .andExpect(jsonPath("$[0].mail").value("il.bill@gmail.com"))
                .andExpect(jsonPath("$[0].phone").value("00000000002"));
    }

    @Test
    void updateSubscriber_ShouldUpdateSubscriber_WhenSuccessful() throws Exception {
        //Given
        Subscriber updatedSubscriber = new Subscriber();
        updatedSubscriber.setSubscriberId(1L);
        updatedSubscriber.setFname("Ily");

        when(subscriberService.updateSubscriber(eq(1L), any(Subscriber.class))).thenReturn(updatedSubscriber);

        mockMvc.perform(put("/subscribers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "fname":"Ily"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subscriberId").value(1L))
                .andExpect(jsonPath("$.fname").value("Ily"));
    }

    @Test
    void updateSubscriber_ShouldReturnInternalError_WhenMailOrPhoneExists() throws Exception {
        //Given
        when(subscriberService.updateSubscriber(eq(1L), any(Subscriber.class))).thenReturn(null);

        mockMvc.perform(put("/subscribers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "mail" : "il.bill@gmail.com",
                                "fname":"Ily"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Un abonné existe déjà avec cette adresse mail et/ou ce numéro"));

    }

    @Test
    void deactivateSubscriber_ShouldDeactivateSubscriber_WhenSuccessful() throws Exception {
        doNothing().when(subscriberService).deactivateSubscriber(1L);

        mockMvc.perform(put("/subscribers/deactivate/1"))
                .andExpect(status().isOk());

        verify(subscriberService, times(1)).deactivateSubscriber(1L);
    }
}
