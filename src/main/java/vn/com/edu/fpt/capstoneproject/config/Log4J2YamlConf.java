package vn.com.edu.fpt.capstoneproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4J2YamlConf {
    private static final Logger logger = LoggerFactory.getLogger(Log4J2YamlConf.class);
    public void performSomeTask(){

        logger.error("This is an info message");

    }
}
