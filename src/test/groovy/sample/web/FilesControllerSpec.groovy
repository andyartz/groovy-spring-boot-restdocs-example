package sample.web

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.http.MediaType
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class FilesControllerSpec extends BaseControllerSpec {

    @Shared
    String fileId = '4c8d6cc5-9a08-42ba-ad1d-2217a968d324'

    void 'test and document getting a file by id'() {
        expect:
        this.webTestClient.get().uri("$FilesController.ENDPOINT/{id}", fileId)
                .accept(MediaType.ALL)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document('files-get-by-id-example',
                pathParameters(parameterWithName('id').description("The file's id"))
        ))
    }
}
