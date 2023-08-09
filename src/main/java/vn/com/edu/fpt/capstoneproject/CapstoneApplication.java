package vn.com.edu.fpt.capstoneproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import vn.com.edu.fpt.capstoneproject.config.oauth2.AppProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EntityScan(basePackageClasses = {
        CapstoneApplication.class,
        Jsr310JpaConverters.class
})
@Slf4j
public class CapstoneApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CapstoneApplication.class, args);
        log.info("Started polling application...........");
    }
}