package co.edu.unbosque.model.persistence;

import java.io.*;

public class Binaries implements Serializable {
    public void write(Object[] assignments) throws IOException {
        String cAssignments = "";
        for (int i = 0; i < assignments.length; i++) {
            cAssignments += assignments[i]+"$\n";
        }
        File f = new File("./MiHorario.bin");
        FileOutputStream fos = new FileOutputStream(f);
        DataOutputStream dos = new DataOutputStream(fos);
        String json = "Assignments [,"+cAssignments+"]";
        byte[] data = json.getBytes();
        for (int i = 0; i < data.length; i++) {
            dos.writeInt(data[i]+30);
        }
        dos.flush();
        dos.close();
    }

    public String read() throws IOException {
        File f = new File("./MiHorario.bin");
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] data = dis.readAllBytes();
        String json = "";
        for (int i = 0; i < data.length; i++) {
            if(!(data[i] == 0)) {
                int bin = Byte.valueOf(data[i]) - 30;
                data[i] = (byte) bin;
                byte[] unencrypted = {data[i]};
                json += new String(unencrypted);
            }
        }
        return json;
    }
}
