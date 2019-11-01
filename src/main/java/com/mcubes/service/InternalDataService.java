package com.mcubes.service;

import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by A.A.MAMUN on 10/26/2019.
 */
public class InternalDataService {


    public static void getFooterData(ModelMap modelMap){


        modelMap.addAttribute("devName_1","Md. Fahim Al Mamun");
        modelMap.addAttribute("devLink_1","https://www.facebook.com/raj.shikder.71");

        modelMap.addAttribute("devName_2","Mobarak Hossen");
        modelMap.addAttribute("devLink_2","https://www.facebook.com/ismayel.hossen.77");

        modelMap.addAttribute("devName_3","Abdullah-Al-Mamun");
        modelMap.addAttribute("devLink_3","https://www.facebook.com/wazidullah.murad");


        modelMap.addAttribute("devName_4","Abdullah-Al-Mamun");
        modelMap.addAttribute("devLink_4","https://www.facebook.com/raj.shikder.71");

//        modelMap.addAttribute("devName_2","Ismayel Hossen");
//        modelMap.addAttribute("devLink_2","https://www.facebook.com/ismayel.hossen.77");
//
//        modelMap.addAttribute("devName_3","Wazid Ullah Murad");
//        modelMap.addAttribute("devLink_3","https://www.facebook.com/wazidullah.murad");
//
//        modelMap.addAttribute("devName_4","Khaled Hassan Manna");
//        modelMap.addAttribute("devLink_4","https://www.facebook.com/khaledhasan.manna");

        modelMap.addAttribute("mcubesFbLink","https://www.facebook.com/mcubesit");
        modelMap.addAttribute("mcubesTwitterLink","https://www.facebook.com/mcubesit");
        modelMap.addAttribute("mcubesLinkedInLink","https://www.facebook.com/mcubesit");

        modelMap.addAttribute("contactNo1","+8801626311400");
        modelMap.addAttribute("contactNo2","+8801740436775");


    }


}
