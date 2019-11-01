package com.mcubes.controller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.Message;
import com.mcubes.model.Prescription;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/8/2019.
 */
@RestController
public class UserProfileRestController {

    private boolean b = false;
    private List<Message> messageList;
    private static List<Prescription> prescriptionList;

    @RequestMapping(value = "prescription-list-by-id", method = RequestMethod.POST)
    public List<Prescription> getPrescriptionById(@RequestParam String pid){
        SessionService.sessionBuilder(session -> {
            prescriptionList = session.createQuery("from Prescription where pid='"+pid+"' order by id desc", Prescription.class).list();
        });
        return prescriptionList;
    }

    /*
    * Fetch message from database by id
     */
    @RequestMapping(value = "fetch-message-by-id", method = RequestMethod.POST)
    public List<Message> fetchMessages(@RequestParam String id)
    {
        SessionService.sessionBuilder(session -> {
            messageList = session.createQuery("from Message where sender = '"+id+"' or receiver='"+id+"' order by id desc",
                    Message.class).list();
        });
        return messageList;
    }

   /*
   * Send message to doctor
   */
    @RequestMapping(value = "send-message-to-doctor", method = RequestMethod.POST)
    public boolean sendMessageToDoctor(@RequestParam String sender, @RequestParam String receiver,
                                       @RequestParam String doctor, @RequestParam String subject,
                                       @RequestParam String message, @RequestParam String patient)
    {
        Message mgsObj = new Message();
        mgsObj.setDate(new Date().toLocaleString());
        mgsObj.setSender(sender);
        mgsObj.setPatient(patient);
        mgsObj.setReceiver(receiver);
        mgsObj.setDoctor(doctor);
        mgsObj.setSubject(subject);
        mgsObj.setMessage(message);
        mgsObj.setStatus(Message.UNSEEN);

        SessionService.sessionBuilder(session -> {
            try {
                Transaction t = session.beginTransaction();
                session.save(mgsObj);
                t.commit();
                b = true;
            }catch (Exception e){

                b = false;
                e.printStackTrace();
            }
        });

        return b;
    }


    @RequestMapping(value = "message-status-to-seen", method = RequestMethod.POST)
    public boolean updateMessageStatusToSeen(@RequestParam Long id){

        SessionService.sessionBuilder(session -> {

            try{
                Transaction t = session.beginTransaction();
                Message message = session.get(Message.class, id);
                message.setStatus("seen");
                session.update(message);
                t.commit();
                b = true;
            }catch (Exception e){
                b = false;
            }
        });
        return b;
    }

}
