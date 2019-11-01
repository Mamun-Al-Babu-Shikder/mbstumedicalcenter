package com.mcubes.controller;

import com.mcubes.service.InternalDataService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by A.A.MAMUN on 9/7/2019.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    private String homePage(ModelMap modelMap){
        InternalDataService.getFooterData(modelMap);
        return "home";
    }

    @RequestMapping("/medicine")
    private String medicinePage(ModelMap modelMap){
        InternalDataService.getFooterData(modelMap);
        return "medicine";
    }

    @RequestMapping(value = "/contact")
    private String contactPage(ModelMap modelMap)
    {
        InternalDataService.getFooterData(modelMap);
        return "contact";
    }


}
