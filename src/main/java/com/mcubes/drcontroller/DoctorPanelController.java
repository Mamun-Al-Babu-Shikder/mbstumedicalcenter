package com.mcubes.drcontroller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.Doctor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by A.A.MAMUN on 9/7/2019.
 */
@Controller
public class DoctorPanelController {

    private static String url = "";

    @RequestMapping("/doctor-login-page")
    private String drLoginPage() {
        return "doctor/drlogin";
    }

    @RequestMapping("/doctor-sign-up-page")
    private String drSignUpPage(){
        return "doctor/drsignup";
    }

    @RequestMapping("/doctor-logout")
    private String drLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("dr-email", null);
        session.invalidate();
        return "redirect:/doctor-login-page";
    }

    @RequestMapping("/doctor")
    private String initDoctor(HttpServletRequest request, ModelMap modelMap)
    {
        //modelMap.addAttribute("mgs","Email or password is wrong. Please try gain with valid one.");
        HttpSession session = request.getSession();
        if(session.getAttribute("dr-email")==null){
            return "doctor/drlogin";
        }else {
            modelMap.addAttribute("email", session.getAttribute("dr-email") );
            return "doctor/index";
        }
    }

    @RequestMapping(value = "doctor-login", method = RequestMethod.POST)
    private String drLogin(ModelMap modelMap, HttpServletRequest request, @RequestParam String email, @RequestParam String pass)
    {
        SessionService.sessionBuilder(session -> {
            Doctor doctor = session.get(Doctor.class,email);

            if(doctor!=null && (doctor.getEmail().equals(email) && doctor.getPassword().equals(pass))){
                HttpSession s = request.getSession();
                s.setAttribute("dr-email", email);
                s.setMaxInactiveInterval(7*24*60*60);
                url = "redirect:/doctor";
            }else{
                modelMap.addAttribute("mgs","Email or password is wrong. Please try gain with valid one.");
                url = "doctor/drlogin";
            }
        });

        System.out.println(modelMap.get("mgs"));
        //return "redirect:/doctor";
        return url;
    }


    @RequestMapping(value = "doctor-sign-up", method = RequestMethod.POST)
    private String drSignUp(ModelMap modelMap, HttpServletRequest request, @RequestParam String email,
                            @RequestParam String pass, @RequestParam String name, @RequestParam String phone,
                            @RequestParam String degree)
    {

        SessionService.sessionBuilder(session -> {

            Doctor doctor = new Doctor();
            doctor.setEmail(email);
            doctor.setPassword(pass);
            doctor.setName(name);
            doctor.setPhone(phone);
            doctor.setDegree(degree);

            try {
                Transaction t= session.beginTransaction();
                session.save(doctor);
                t.commit();
                url ="redirect:/doctor-login-page";
            }catch (Exception ex){
                url ="doctor/drsignup";
                modelMap.addAttribute("mgs","Email address maybe already exist. Please try again with another one.");
                //ex.printStackTrace();
            }

        });

        return url;
    }


    @RequestMapping("/prescription")
    private String prescriptionForm(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        if(session.getAttribute("dr-email")==null){
            return "redirect:/doctor-login-page";
        }else {
            return "doctor/prescription";
        }
    }


}
