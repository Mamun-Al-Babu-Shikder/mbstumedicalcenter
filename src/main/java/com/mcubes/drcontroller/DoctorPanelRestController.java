package com.mcubes.drcontroller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.*;
import com.mcubes.service.FileUploadService;
import com.mcubes.service.SendEmail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.print.Doc;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by A.A.MAMUN on 9/7/2019.
 */
@RestController
public class DoctorPanelRestController {

    private static boolean b = false;
    private static String status = "";
    private static Patient patient ;
    private static Doctor doctor ;
    private static HealthPost healthPost;
    private static HealthTipsBook healthTipsBook;
    private static List<Patient> patientList ;
    private static List<Prescription> historyList;
    private static List<Message> messageList ;
    private static List<Contact> contactList ;
    private static List<HealthPost> healthPostList;
    private static List<HealthTipsBook> healthTipsBookList;
    private static List<Medicine> medicineList;
    private static Map<String, String> mapData ;


    /*
    @RequestMapping(value = "is-login-success", method = RequestMethod.POST)
    private boolean isLoginSuccess(){

    }
    */

    public DoctorPanelRestController(){
        healthPost = new HealthPost();
        healthTipsBook = new HealthTipsBook();
    }


    @RequestMapping(value = "fetch-doctor-info", method = RequestMethod.POST)
    private Doctor getDoctorInfo(@RequestParam String email)
    {
        SessionService.sessionBuilder(session -> {
            doctor = session.get(Doctor.class, email);
        });
        return doctor;
    }

    @RequestMapping(value = "update-doctor-info", method = RequestMethod.POST)
    private boolean updateDoctorInfo(@RequestBody Doctor doctor){
        b = false;
        SessionService.sessionBuilder(session -> {
            Transaction t =session.beginTransaction();
            session.update(doctor);
            t.commit();
            b = true;
        });
        return b;
    }

    @RequestMapping(value = "fetch-patient-by-id", method = RequestMethod.POST)
    private Patient getSinglePatient(@RequestParam long id){
        SessionService.sessionBuilder(session -> {
            patient = session.get(Patient.class, id);
        });
        return patient;
    }

    @RequestMapping(value = "fetch-patient", method = RequestMethod.POST)
    private List<Patient> getAllPatient()
    {
        SessionService.sessionBuilder((session)-> {
            patientList = session.createQuery("from Patient", Patient.class).list();

        });
        return patientList;
    }

    @RequestMapping(value = "save-patient", method = RequestMethod.POST)
    private boolean savePatient(@RequestBody Patient patient){
        b = false;
        Date date = new Date();
        patient.setYyyy(date.getYear()+1900);
        patient.setMm(date.getMonth()+1);
        patient.setDd(date.getDate());
        SessionService.sessionBuilder(session ->{
            Transaction t = session.beginTransaction();
            session.save(patient);
            t.commit();
            b = true;
        });
        return b;
    }

    @RequestMapping(value = "update-patient", method = RequestMethod.POST)
    private boolean updatePatient(@RequestBody Patient p)
    {
        b = false;
        System.out.println("Update : "+p.toString());
        SessionService.sessionBuilder(session -> {
            Transaction t = session.beginTransaction();
            session.update(p);
            t.commit();
            b = true;
        });
        return b;
    }


    @RequestMapping(value = "delete-patient", method = RequestMethod.POST)
    private boolean deletePatient(@RequestParam long id)
    {
        b = false;
        SessionService.sessionBuilder((session)->{
            Transaction t = session.beginTransaction();
            Patient p = session.get(Patient.class, id);
            session.delete(p);
            t.commit();
            b = true;
        });
        return b;
    }


    @RequestMapping(value = "save-prescription", method = RequestMethod.POST)
    private String savePrescription(@RequestParam String dremail,
                                    @RequestParam String drname,
                                    @RequestParam String drdegree,
                                    @RequestParam String drphone,
                                    @RequestParam String pid,
                                    @RequestParam String pname,
                                    @RequestParam String psex,
                                    @RequestParam String page,
                                    @RequestParam String pbody)
    {

        final Prescription prescription = new Prescription();
        prescription.setDate((new  Date()).toLocaleString());
        prescription.setDremail(dremail);
        prescription.setDrname(drname);
        prescription.setDrDegree(drdegree);
        prescription.setDrPhone(drphone);
        prescription.setDrname(getDoctorInfo(dremail).getName());
        prescription.setPid(pid);
        prescription.setPname(pname);
        prescription.setPsex(psex);
        prescription.setPage(page);
        prescription.setBody(pbody);

       // System.out.println("Pres. : "+prescription.toString());

        status = "Can't save prescription, Please tra again.";
        SessionService.sessionBuilder(session -> {
            Transaction t = session.beginTransaction();
            session.save(prescription);
            t.commit();
            status = "Prescription successfully saved.";
        });
        return status;
    }


    @RequestMapping(value = "history-by-pid", method = RequestMethod.POST)
    private List<Prescription> getHistory(@RequestParam String pid)
    {
        SessionService.sessionBuilder(session -> {
            historyList = session.createQuery("from Prescription where pid='"+pid+"' order by id desc",
                    Prescription.class).list();
        });
       // System.out.println("History : "+historyList.toString());
        return  historyList;
    }



    @RequestMapping(value = "fetch-message-by-doctor", method = RequestMethod.POST)
    public List<Message> fetchMessageByDoctor(@RequestParam String dremail)
    {
        SessionService.sessionBuilder(session -> {
            messageList = session.createQuery("from Message where sender = '"+dremail+"' or receiver = '"+dremail+"'" +
                            " order by id desc ",
                    Message.class).list();
        });
        return messageList;
    }

    @RequestMapping(value = "send-message-to-patient", method = RequestMethod.POST)
    public boolean sendMessageToPatient(@RequestParam String pid,
                                        @RequestParam String dremail,
                                        @RequestParam String drname,
                                        @RequestParam String subject,
                                        @RequestParam String message)
    {
        SessionService.sessionBuilder(session -> {

            try {

                Transaction t = session.beginTransaction();
                Patient p = session.createQuery("from Patient where pid='" + pid + "'", Patient.class).getSingleResult();
                Message mgs = new Message();
                mgs.setDate(new Date().toLocaleString());
                mgs.setPatient(p.getPname());
                mgs.setDoctor(drname);
                mgs.setStatus("unseen");
                mgs.setSubject(subject);
                mgs.setMessage(message);
                mgs.setReceiver(pid);
                mgs.setSender(dremail);
                session.save(mgs);
                t.commit();

                b =true;

            }catch (Exception e){
                b = false;
                e.printStackTrace();
            }

        });

        return b;
    }


    @RequestMapping(value = "fetch-contact-message", method = RequestMethod.POST)
    private List<Contact> fetchContactMessageList(){
        SessionService.sessionBuilder(session -> {
            contactList = session.createQuery("from Contact order by id desc", Contact.class).list();
        });
       // System.out.println("Contact List : \n"+contactList.toString());
        return contactList;
    }

    @RequestMapping(value = "update-contact-message-status", method = RequestMethod.POST)
    private boolean updateContactMessageStatus(@RequestParam long id){
        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                Contact contact = session.get(Contact.class, id);
                contact.setStatus("seen");
                session.update(contact);
                t.commit();
                b = true;
            }catch (Exception e){
                b = false;
            }
        });

        return b;
    }

    @RequestMapping(value = "delete-contact-message", method = RequestMethod.POST)
    private boolean deleteContactMessage(@RequestParam Long id){

         b = false;
        SessionService.sessionBuilder(new SessionService.ProcessSession() {
            @Override
            public void getSession(Session session) {
                try{
                    Transaction t = session.beginTransaction();
                    Contact contact = session.get(Contact.class, id);
                    //System.out.println("Delete Contact : "+contact.toString());
                    session.delete(contact);
                    t.commit();
                    b = true;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return b;
    }


    @RequestMapping(value = "reply-message-by-email", method = RequestMethod.POST)
    private boolean replyMessageByEmail(@RequestParam String email, @RequestParam String subject,
                                        @RequestParam String message)
    {
        b = false;
        try{
            SendEmail.sendEmail(email, subject, message, SendEmail.TEXT_MAIL);
            b = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return b;
    }




    /*
    Health Tips Section
     */
    @RequestMapping(value = "fetch-health-tips", method = RequestMethod.POST)
    private List<HealthPost> fetchHealthTips() {
        SessionService.sessionBuilder(session -> {
            healthPostList = session.createQuery("from HealthPost order by id desc", HealthPost.class).list();
        });
        return healthPostList;
    }

    @RequestMapping(value = "delete-health-tips", method = RequestMethod.POST)
    private boolean deleteHealthTip(@RequestParam Long id){
        b = false;
        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                session.delete(session.get(HealthPost.class,id));
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return b;
    }


    @RequestMapping(value = "upload-health-tips", method = RequestMethod.POST)
    private boolean uploadHealthTips(@RequestParam MultipartFile imageFile, @RequestParam String publisher,
                                     @RequestParam String title, @RequestParam String description)
    {
        b = false;


        try {

            String imageUrl = FileUploadService.uploadFile(imageFile.getInputStream(),FileUploadService.getFileExtension(imageFile.getOriginalFilename()));
            Date date = new Date();
            healthPost.setDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900));
            healthPost.setViews(0l);
            healthPost.setImage(imageUrl);
            healthPost.setPoster(publisher);
            healthPost.setTitle(title);
            healthPost.setBody(description);

            SessionService.sessionBuilder(session -> {
                Transaction t = session.beginTransaction();
                session.save(healthPost);
                t.commit();
                b = true;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }




    @RequestMapping(value = "upload-health-tips2", method = RequestMethod.POST)
    private boolean uploadHealthTips2(@RequestParam String imageUrl, @RequestParam String publisher,
                                      @RequestParam String title, @RequestParam String description)
    {
        b = false;

        Date date = new Date();
        healthPost.setDate(date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900));
        healthPost.setViews(0l);
        healthPost.setImage(imageUrl);
        healthPost.setPoster(publisher);
        healthPost.setTitle(title);
        healthPost.setBody(description);

        SessionService.sessionBuilder(session -> {

            try{
                Transaction t = session.beginTransaction();
                session.save(healthPost);
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }

        });

        return b;
    }



    /*
    Send mail to know about current uploaded
    heath tips.
     */
    private void sendEmailToSubscriberForHealthTips() {



    }



    /*
   Health Tips Book Section
    */
    @RequestMapping(value = "fetch-health-tips-book", method = RequestMethod.POST)
    private List<HealthTipsBook> fetchHealthTipsBook() {
        SessionService.sessionBuilder(session -> {
            healthTipsBookList = session.createQuery("from HealthTipsBook order by id desc", HealthTipsBook.class).list();
        });
        return healthTipsBookList;
    }


    @RequestMapping(value = "delete-health-tips-book", method = RequestMethod.POST)
    private boolean deleteHealthTipsBook(@RequestParam Long id){
        b = false;
        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                session.delete(session.get(HealthTipsBook.class,id));
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return b;
    }





    @RequestMapping(value = "upload-health-tips-book", method = RequestMethod.POST)
    private boolean uploadHealthTipsBook(@RequestParam MultipartFile imageFile, @RequestParam String link,
                                         @RequestParam String description)
    {
        b = false;


        try {

            String imageUrl = FileUploadService.uploadFile(imageFile.getInputStream(),FileUploadService.getFileExtension(imageFile.getOriginalFilename()));
            Date date = new Date();

            healthTipsBook.setImage(imageUrl);
            healthTipsBook.setLink(link);
            healthTipsBook.setDescription(description);

            SessionService.sessionBuilder(session -> {
                Transaction t = session.beginTransaction();
                session.save(healthTipsBook);
                t.commit();
                b = true;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }



    @RequestMapping(value = "upload-health-tips-book2", method = RequestMethod.POST)
    private boolean uploadHealthTipsBook2(@RequestParam String imageUrl, @RequestParam String link,
                                          @RequestParam String description)
    {
        b = false;

        healthTipsBook.setImage(imageUrl);
        healthTipsBook.setLink(link);
        healthTipsBook.setDescription(description);

        SessionService.sessionBuilder(session -> {

            try{
                Transaction t = session.beginTransaction();
                session.save(healthTipsBook);
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }

        });

        return b;
    }



    @PostMapping("fetch-medicine-by-abc")
    private List<Medicine> fetchMedicineByAbcOpt(@RequestParam String abcOpt){
        SessionService.sessionBuilder(session -> {
            Transaction t = session.beginTransaction();
            if(abcOpt.equals("#")){
                medicineList = session.createQuery("from Medicine where name like '1%' or name like '2%'" +
                                " or name like '3%' or name like '4%' or name like '5%' or name like '6%' or name like '7%'" +
                                "or name like '8%' or name like '9%' or name like '0%' "
                        , Medicine.class).list();
            }else {
                medicineList = session.createNativeQuery("select * from Medicine where name like '" + abcOpt + "%'", Medicine.class).list();
            }
            t.commit();
        });
        return medicineList;
    }


    @PostMapping("save-or-remove-medicine-suggestion")
    private boolean saveOrRemoveMedicineSuggestion(@RequestParam Integer id, @RequestParam int sugg){
        b = false;
        SessionService.sessionBuilder(session -> {
            try {
                Transaction t = session.beginTransaction();
                Medicine medicine = session.createQuery("from Medicine where id=" + id, Medicine.class).getSingleResult();
                medicine.setSuggestion(sugg);
                session.update(medicine);
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return b;
    }


    @PostMapping("fetch-suggestion-medicine")
    private List<Medicine> fetchSuggestionMedicine() {
        SessionService.sessionBuilder(session -> {
            Transaction t = session.beginTransaction();
            medicineList = session.createQuery("from Medicine where suggestion=1", Medicine.class).list();
            t.commit();
        });
        return medicineList;
    }


    @PostMapping("update-medicine-details")
    private boolean updateMedicineInfo( @RequestParam Long id, @RequestParam String name, @RequestParam String category,
                                        @RequestParam String percentage, String element, @RequestParam String company,
                                        @RequestParam String price, @RequestParam Integer suggestion)
    {
        b = false;

        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setName(name);
        medicine.setCategory(category);
        medicine.setPercentage(percentage);
        medicine.setElement(element);
        medicine.setCompany(company);
        medicine.setPrice(price);
        medicine.setSuggestion(suggestion);

        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                session.update(medicine);
                t.commit();
                b = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return b;
    }



}
