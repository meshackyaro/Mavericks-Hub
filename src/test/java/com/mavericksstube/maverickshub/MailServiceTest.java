package com.mavericksstube.maverickshub;

import com.mavericksstube.maverickshub.services.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail(){
        String email = "darhyor2050@gmail.com";
        String response = mailService.sendMail(email);

        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }
}
