package co.edu.unbosque.model.persistence;

import java.io.*;
import java.util.Properties;

public class ScheduleProperties {
    Properties properties = new Properties();

    public void writeProperties(String name, String career, int semester, int status, int plan, boolean scholarship, double average) throws IOException {
        File f = new File("MiHorario.xml");
        properties.setProperty("name",name);
        properties.setProperty("career",career);
        properties.setProperty("semester",String.valueOf(semester));
        properties.setProperty("status",String.valueOf(status));
        properties.setProperty("plan",String.valueOf(plan));
        properties.setProperty("scholarship",String.valueOf(scholarship));
        properties.setProperty("average",String.valueOf(average));
        properties.storeToXML(new FileOutputStream(f),"");
    }

    public String readProperties() throws IOException {
        String json = "";
        File f = new File("MiHorario.xml");
        properties.loadFromXML(new FileInputStream("MiHorario.xml"));
        json += properties.getProperty("name")+",";
        json += properties.getProperty("career")+",";
        json += properties.getProperty("semester")+",";
        json += properties.getProperty("status")+",";
        json += properties.getProperty("plan")+",";
        json += properties.getProperty("scholarship")+",";
        json += properties.getProperty("average")+",";
        return json;
    }
}
