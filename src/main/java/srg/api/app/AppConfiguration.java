package srg.api.app;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergey on 16.10.2016.
 */
@Configuration
@PropertySource("classpath:/app.properties")
public class AppConfiguration {

//    public List<HttpMessageConverter<?>> getConverters() {
//        AtomFeedHttpMessageConverter atomConverter = new AtomFeedHttpMessageConverter();
//        atomConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_ATOM_XML));
//
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        jsonConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//
//                Jaxb2Marshaller marshaller= new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(Entry.class);
//        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter(marshaller);
//        xmlConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_XML));
//        return Arrays.asList(xmlConverter);
////        return Arrays.asList(atomConverter,jsonConverter,xmlConverter);
//    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setMessageConverters(getConverters());
        return restTemplate;
    }
}
