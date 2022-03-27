package com.services.syslogin.model.utilities;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WriteLog {

    private final Logger logger = Logger.getLogger(WriteLog.class.getName());

    public WriteLog() {
    }

    public void writeLogDebug(String text, String nameLog) {
        SimpleDateFormat format = new SimpleDateFormat("M_d");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String separator = System.lineSeparator();
        String StackTrace = Arrays.toString(Thread.currentThread().getStackTrace()).replace( ',', '\n' );;
        String name = nameLog + "_" + format.format(Calendar.getInstance().getTime()) + ".txt";
        String logPath = "C:/JavaPrj/Logs/" + name;
        try {
            File log = new File(logPath);
            if (log.createNewFile()) {
                FileWriter fwlog = new FileWriter(logPath);
                fwlog.write(
                        dtf.format(now) + separator + text + separator + separator
                );
                fwlog.flush();
                fwlog.close();
            } else {
                FileWriter fwlog = new FileWriter(logPath, true);
                fwlog.write(separator + dtf.format(now) + separator + text + separator +  separator
                );
                fwlog.flush();
                fwlog.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void writeLog(String nameLog, String text){
//        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmm");
//        FileHandler fh = null;
//        try {
//            fh = new FileHandler("C:/JavaPrj/Logs/"+ nameLog +
//                    format.format(Calendar.getInstance().getTime()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assert fh != null;
//        fh.setFormatter(new SimpleFormatter());
//        logger.addHandler(fh);
//        logger.info(text);
//        logger.setUseParentHandlers(false);
//    }
}
