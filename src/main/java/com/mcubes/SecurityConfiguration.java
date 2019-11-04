package com.mcubes;

import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by A.A.MAMUN on 11/4/2019.
 */
public class SecurityConfiguration{

    /*
    public static void main(String[] args)
    {
        try {
            URL url = new URL("https://www.prothomalo.com/");
            InputStream is = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line=br.readLine())!=null){
                line = line.trim();
                if(line.contains("<span class=\"title\">")) {
                    line = line.replace("<span class=\"title\">","");
                    line = line.replace("</span>","");
                    line = line.replace("<span class=\"subtitle\">","");
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */


}
/*
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().anyRequest().requiresSecure();
    }
}
*/