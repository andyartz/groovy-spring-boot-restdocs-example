package sample

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {
    @Bean
    ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
        mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper
    }
}
