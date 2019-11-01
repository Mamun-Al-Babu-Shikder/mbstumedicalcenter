package com.mcubes.controller;

import com.mcubes.dbservice.SessionService;
import com.mcubes.model.HealthPost;
import com.mcubes.model.HealthTipsBook;
import com.mcubes.service.InternalDataService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/20/2019.
 */
@Controller
public class HealthController {

    private boolean canPrevious = true, canNext = false;
    private int totalData, pageCount, startPos = 0 , pageLimit = 5, dataLimit = 5;//,fp = 1, lp ;
    private List<HealthPost> healthPostList;


    @RequestMapping(value = "/health", method = RequestMethod.GET)
    private String healthPage(@RequestParam(defaultValue = "1") int fp, @RequestParam(defaultValue = "1") int pos, @RequestParam(defaultValue = "5") int lp, ModelMap modelMap){


        SessionService.sessionBuilder(session -> {

            try{
                totalData = Integer.parseInt(session.createNativeQuery("SELECT COUNT(id) FROM healthpost")
                        .getSingleResult().toString());

                pageCount = (int) Math.ceil((totalData*1.0)/dataLimit);
                System.out.println("#Total Data : "+totalData+", #Page count : "+pageCount);

                int selectedPos = pos<=0 ? 1 : pos>pageCount ? pageCount : pos;
                startPos = dataLimit*(selectedPos-1);

                //healthPostList = session.createNativeQuery("SELECT * FROM `healthpost` ORDER by id desc LIMIT " +startPos+","+dataLimit, HealthPost.class).list();
                healthPostList = session.createNativeQuery("SELECT * FROM healthpost ORDER by id desc LIMIT " +dataLimit, HealthPost.class).list();

                modelMap.addAttribute("healthPostList", healthPostList);
                modelMap.addAttribute("selectedPos", selectedPos);



                /*
                if(pageCount<=pageLimit){
                    lp = pageCount;
                    canPrevious = false;
                    canNext =false;
                }else{
                    canNext = true;
                    if(pos<=pageLimit){
                        canPrevious = false;
                        fp = 1;
                        lp = pageLimit;
                    }else{
                        canPrevious = true;
                    }
                    if(pos==pageCount){
                        canNext = false;
                    }
                }
                */

                //pageCount<pageLimit


                if(pos<fp){
                    modelMap.addAttribute("fp", fp-1);
                    modelMap.addAttribute("lp", lp-1);
                }else if(pos>lp){
                    modelMap.addAttribute("fp", fp+1);
                    modelMap.addAttribute("lp", lp+1);
                }else {
                    modelMap.addAttribute("fp", fp<1 ? 1 : fp);
                    modelMap.addAttribute("lp", pageCount<lp ? pageCount : lp);
                }
                modelMap.addAttribute("pageCount", pageCount);


                loadHealthTipsBooks(session, modelMap);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        InternalDataService.getFooterData(modelMap);
        return "health";
    }


    private void loadHealthTipsBooks(Session session, ModelMap modelMap)
    {
        try {

            int totalBook = Integer.parseInt(session.createNativeQuery("SELECT COUNT(id) FROM healthtipsbook")
                    .getSingleResult().toString());
            int randVal = 0;
            if (totalBook > 10) {
                randVal = (int) ((totalBook - 5) * Math.random());
            }
            System.out.println("#totalBook : " + totalBook + "  #Rand Value : " + randVal);
            List<HealthTipsBook> healthTipsBookList = session.createNativeQuery("SELECT * FROM healthtipsbook ORDER BY id LIMIT "+randVal+",5", HealthTipsBook.class).list();

            modelMap.addAttribute("healthTipsBookList", healthTipsBookList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /*
    @RequestMapping(value = "search-health-post", method = RequestMethod.GET)
    private void loadPreviousOrNextPost(@RequestParam int pos, ModelMap modelMap)
    {
        //System.out.println("#Search Value : "+pos);
        if(pos.equals("previous")){
            this.pos -= 1;
            if(this.pos>pageLimit){
                fp-=1;
                lp-=1;
            }
        }else if(pos.equals("next")){

            this.pos += 1;
            if(this.pos>pageLimit){
                fp+=1;
                lp+=1;
            }
        }else{
            this.pos = Integer.parseInt(pos);
        }
        return "redirect:/health";
    }
    */


    @RequestMapping(value = "read-more", method = RequestMethod.POST)
    private String readMoreDetails(@RequestParam Long id, ModelMap modelMap)
    {

        SessionService.sessionBuilder(session -> {
            try{
                Transaction t = session.beginTransaction();
                HealthPost healthPost1 = session.get(HealthPost.class, id);
                modelMap.addAttribute("healthPost", healthPost1);
                getHealthTipsImage(modelMap);
                healthPost1.setViews( healthPost1.getViews() + 1 );
                session.update(healthPost1);
                t.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        InternalDataService.getFooterData(modelMap);
        return "blog";
    }


    private void getHealthTipsImage(ModelMap modelMap){

        int i = (int)(6 * Math.random());
        modelMap.addAttribute("health_tips_image_1","health_tips_image/tips_img"+i+".jpg");
        modelMap.addAttribute("health_tips_image_2","health_tips_image/tips_img"+(i+1)+".jpg");
        modelMap.addAttribute("health_tips_image_3","health_tips_image/tips_img"+(i+2)+".jpg");
    }


}
