package com.bms.notification_v1_api.service;


import com.bms.notification_v1_api.requestbody.TheaterRequestBody;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class TheaterMailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void notifyAdminForTheaterCreation(TheaterRequestBody theaterRequestBody) throws MessagingException {

        Context context = new Context(); //context class is used to set the values of variables used in the html code

        context.setVariable("adminName",theaterRequestBody.getAdmin().getName());
        context.setVariable("theaterName",theaterRequestBody.getTheater().getId());
        context.setVariable("theaterAddress",theaterRequestBody.getTheater().getAddress());
        context.setVariable("theaterState",theaterRequestBody.getTheater().getState());
        context.setVariable("pinCode",theaterRequestBody.getTheater().getPinCode());
        context.setVariable("ownerName",theaterRequestBody.getTheater().getOwner().getName());
        context.setVariable("ownerEmail",theaterRequestBody.getTheater().getOwner().getEmail());

        String htmlEmail = templateEngine.process("TheaterRequest",context); //templateEngine provides the final output in the form of string, replacing all the placeholder with actual data we have set in the context object

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        message.setSubject("A new theater %s has been registered and is awaiting for approval",theaterRequestBody.getTheater().getName());
        helper.setTo(theaterRequestBody.getAdmin().getEmail());
        helper.setText(htmlEmail,true); // true because it uses html content

        javaMailSender.send(message);

    }
}
