package sample.web

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import groovy.transform.CompileStatic
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@CompileStatic
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseControllerSpec extends Specification {

    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

    @Autowired
    ApplicationContext context

    protected WebTestClient webTestClient

    void setup() {

        this.webTestClient = WebTestClient.bindToApplicationContext(this.context)
                .configureClient()
                .defaultHeader(HttpHeaders.AUTHORIZATION, 'Bearer pxqltWnuedMzqw4ISNFIZQ...')
                .filter(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint()))
                .build()
    }
}
