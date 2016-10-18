package srg.api.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


@Configuration
@PropertySource("classpath:app.properties")
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
