/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;

/**
 *
 * @author Enamul Karim
 */
public class BackUpDatabase {
    
    public void backup() throws IOException{
            String path = "McafeBackup.backup";
            Runtime r = Runtime.getRuntime();
            //PostgreSQL variables    
            String host = "localhost";
            String user = "postgres";
            String dbase = "umaiNewDatabase";
            String password = "postgres";
            Process p;
            ProcessBuilder pb;

            r = Runtime.getRuntime();
            pb = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\9.6\\bin\\pg_dump.exe", "-v", "-h", host, "-f", path, "-U", user, dbase);
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);
            p = pb.start();
            System.out.println("end of backup");
        
    }

    
}
