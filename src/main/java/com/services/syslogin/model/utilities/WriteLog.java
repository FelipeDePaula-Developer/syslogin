package com.services.syslogin.model.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WriteLog {

    private final Logger logger = Logger.getLogger(WriteLog.class.getName());

    public WriteLog() {
    }

    public void writeLog(String nameLog, String text) {
        String logPath = "C:/JavaPrj/Logs/" + nameLog + ".txt";
        String name = nameLog + ".txt";
        try {
            File log = new File(logPath);
            if (log.createNewFile()){
                FileWriter fwlog = new FileWriter(logPath);
                fwlog.write(text);
                fwlog.flush();
                fwlog.close();
            }else{
                FileWriter fwlog = new FileWriter(logPath, true);
                fwlog.write(System.lineSeparator());
                fwlog.write(text);
                fwlog.flush();
                fwlog.close();
            };
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
