package sample.web

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document

import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class BulletinsControllerSpec extends BaseControllerSpec {

    @Shared
    String bulletinId

    void 'test and document creating a bulletin with a custom name'() {
        when:
        def result = this.webTestClient.post().uri('/bulletins')
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject('{"title": "Defective Flux Capacitor"}'))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath('$.id').isNotEmpty()
                .jsonPath('$.title').isEqualTo('Defective Flux Capacitor')
                .consumeWith(document('bulletins-post-example',
                    requestFields(
                        fieldWithPath('title').type(JsonFieldType.STRING).description("The bulletin's title"))))
        JsonSlurper slurper = new JsonSlurper()
        bulletinId = slurper.parseText(new String(result.returnResult().body)).id

        then:
        assert bulletinId
    }

    void 'test and document get of a list of bulletins'() {
        expect:
        this.webTestClient.get().uri('/bulletins').accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document('bulletins-list-example',
                   responseFields(bulletinList)))
    }

    void 'test and document getting a bulletin by id'() {
        expect:
        this.webTestClient.get().uri('/bulletins/{id}', bulletinId).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document('bulletins-get-by-id-example',
                pathParameters(parameterWithName('id').description("The bulletin's id")),
                responseFields(bulletin)
        ))
    }

    FieldDescriptor[] bulletin = new FieldDescriptor().with {
        [fieldWithPath('id').type(JsonFieldType.STRING)
                 .description("The bulletin's id"),
         fieldWithPath('title').type(JsonFieldType.STRING)
                 .description("The bulletin's title")]
    }

    FieldDescriptor[] bulletinList = new FieldDescriptor().with {
        [fieldWithPath('[].id').type(JsonFieldType.STRING).optional()
                 .description("The bulletin's id"),
         fieldWithPath('[].title').type(JsonFieldType.STRING)
                 .description("The bulletin's title")]
    }
}
