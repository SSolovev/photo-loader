package srg.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )


    {
        ApplicationContext ctx =SpringApplication.run(App.class,args);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
//        System.out.println( "Hello World!" );
    }
}
