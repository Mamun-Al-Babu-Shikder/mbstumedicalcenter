package com.mcubes.dbservice;

import com.mcubes.model.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by A.A.MAMUN on 9/7/2019.
 */
public class SessionService {

    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static Session session;

    private SessionService(){}

    public static void sessionBuilder(SessionService.ProcessSession processSession){

        System.out.println("Connection building............");
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            processSession.getSession(session);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("closing session................");
            if(session!=null) {
                sessionFactory.close();
                session.close();
            }
        }
    }

    public static Session getSession(){
        configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        return session;
    }

    public interface ProcessSession{
        public void getSession(Session session);
    }
}
