package activityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
/**
 * ActivityServerApplication class
 *
 * @Author wyx
 * @Date 2019.07.12
 */
public class ActivityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityServerApplication.class, args);
    }

}