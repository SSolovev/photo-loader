package srg.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class UploaderMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(UploaderMain.class, args);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
