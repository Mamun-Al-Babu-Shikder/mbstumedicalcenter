package com.mcubes.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by A.A.MAMUN on 10/23/2019.
 */
public class FileUploadService {

    private static String UPLOAD_DIR = "G:/Project/My Project/IdeaProjects/mbstumedicalcenter/src/main/resources/static/upload/";

    private FileUploadService(){}

    public static String uploadFile(InputStream inputStream, String file_extension){

        String file_url = null;
        String file_name = "file_"+System.currentTimeMillis()+"_"+((int) (99999999*Math.random()+1000000))+"."+file_extension;

        try{

            OutputStream outputStream = new FileOutputStream(UPLOAD_DIR+file_name);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            //file_url = UPLOAD_DIR+file_name;
            file_url = "upload/"+file_name;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return file_url;
    }


    public static String getFileExtension(String file_name){

        String reverse_extension="", extension = "";

        for(int i=file_name.length()-1;i>=0;i--){
            char c = file_name.charAt(i);
            if(c=='.')
                break;
            reverse_extension += c;
        }

        for(int i=reverse_extension.length()-1;i>=0;i--){
            extension += reverse_extension.charAt(i);
        }

        return extension;
    }




}
