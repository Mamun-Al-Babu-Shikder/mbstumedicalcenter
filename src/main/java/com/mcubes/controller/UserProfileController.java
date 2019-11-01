package com.mcubes.controller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.Doctor;
import com.mcubes.model.Patient;
import com.mcubes.service.InternalDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by A.A.MAMUN on 10/7/2019.
 */
@Controller
public class UserProfileController {

    boolean isSuccess = false;

    @RequestMapping("/login-page")
    private String userLoginPage(ModelMap modelMap){
        InternalDataService.getFooterData(modelMap);
        return "user/user-login";
    }

    @RequestMapping(value = "user-login", method = RequestMethod.POST)
    private String userLogin(@RequestParam String type, @RequestParam String pid, @RequestParam String dob,
                             ModelMap modelMap){

        SessionService.sessionBuilder(session -> {
            try {
                Patient patient = session.createQuery("from Patient where ptype = '" + type + "' and pid = '" + pid + "' and pdob = '" + dob + "'  ",
                        Patient.class).getSingleResult();
                isSuccess = true;
                List<Doctor> drList = session.createQuery("from Doctor", Doctor.class).list();
                modelMap.addAttribute("patient",patient);
                modelMap.addAttribute("drList", drList);
            }catch (Exception e){
                modelMap.addAttribute("message","Wrong info, Please check your information");
                isSuccess = false;
            }
        });
        InternalDataService.getFooterData(modelMap);
        return isSuccess ? "user/user-profile":"user/user-login";
    }

}
