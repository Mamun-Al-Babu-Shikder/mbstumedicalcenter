package com.mcubes.controller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.Contact;
import com.mcubes.model.Medicine;
import com.mcubes.model.Subscriber;
import com.mcubes.service.SendEmail;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/13/2019.
 */
@RestController
public class HomeRestController {

    private static List<Medicine> medicineList;
    public static final Subscriber subscriber = new Subscriber();
    private static boolean b = false;



    /*
     Contact controller
     */
    @RequestMapping(value = "send-contact-message", method = RequestMethod.POST)
    public boolean sendContactMessage(@RequestParam String name, @RequestParam String email,
                                      @RequestParam String subject, @RequestParam String message){
        SessionService.sessionBuilder(session -> {
            try {
                Transaction t = session.beginTransaction();
                Contact contact = new Contact();
                contact.setDate(new Date().toLocaleString());
                contact.setName(name);
                contact.setEmail(email);
                contact.setSubject(subject);
                contact.setMessage(message);
                contact.setStatus(Contact.UNSEEN);
                session.save(contact);
                t.commit();
                b = true;

            }catch (Exception e){
                b = false;
            }
        });
        return b;
    }


    @RequestMapping(value = "send-subscribe-request", method = RequestMethod.POST)
    private boolean sendSubscribeRequest(@RequestParam String subscriberEmail)
    {
        b = false;
        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                subscriber.setEmailAddress(subscriberEmail);
                session.save(subscriber);
                t.commit();
                b = true;

                SendEmail.sendEmail(subscriberEmail,"Subscribe success !",
                        "Dear subscriber, you successfully subscribed to us. Now you will get update of our activity.",
                        SendEmail.TEXT_MAIL);


            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        return b;
    }




    @RequestMapping(value = "fetch-medicine", method = RequestMethod.POST)
    private List<Medicine> fetchMedicine(@RequestParam String opt) {
        SessionService.sessionBuilder(session -> {
            Transaction t = session.beginTransaction();
            if(opt.equals("#")){
                medicineList = session.createQuery("from Medicine where name like '1%' or name like '2%'" +
                        " or name like '3%' or name like '4%' or name like '5%' or name like '6%' or name like '7%'" +
                                "or name like '8%' or name like '9%' or name like '0%' "
                        , Medicine.class).list();
            }else {
                medicineList = session.createQuery("from Medicine where name like '" + opt + "%'", Medicine.class).list();
            }
            t.commit();
        });
        return medicineList;
    }




}
