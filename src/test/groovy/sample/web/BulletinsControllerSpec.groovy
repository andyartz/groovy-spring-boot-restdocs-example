package sample.web

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class BulletinsControllerSpec extends BaseControllerSpec {

    @Shared
    String bulletinId

    void 'test and document creating a bulletin with a custom name'() {
        when:
        def result = this.webTestClient.post().uri(BulletinsController.ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject('{"title": "Defective Flux Capacitor"}'))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath('$.id').isNotEmpty()
                .jsonPath('$.title').isEqualTo('Defective Flux Capacitor')
                .consumeWith(document('bulletins-post-example',
                    requestFields(
                        fieldWithPath('title').type(JsonFieldType.STRING).description("The bulletin's title"))))
        JsonSlurper slurper = new JsonSlurper()
        bulletinId = slurper.parseText(new String(result.returnResult().body)).id

        then:
        bulletinId
    }

    void 'test and document get of a list of bulletins'() {
        expect:
        this.webTestClient.get().uri("${BulletinsController.ENDPOINT}?year={year}&make={make}&model={model}&engine={engine}&submodel={submodel}&keywords={keywords}",
            '1997', 'Ford', 'Taurus', 'V6', 'Special Edition', 'fictional problems')
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document('bulletins-list-example',
                    requestParameters(bulletinSearchFields),
                    responseFields(bulletinsResponseFields)))
    }

    void 'test and document getting a bulletin by id'() {
        expect:
        this.webTestClient.get().uri("$BulletinsController.ENDPOINT/{id}", bulletinId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document('bulletins-get-by-id-example',
                pathParameters(parameterWithName('id').description("The bulletin's id")),
                responseFields(bulletin)
        ))
    }

    ParameterDescriptor[] bulletinSearchFields = new ParameterDescriptor().with {
        [   parameterWithName('year').optional().description('The vehicle year the bulletin applies to'),
            parameterWithName('make').optional().description('The vehicle make the bulletin applies to'),
            parameterWithName('model').optional().description('The vehicle model the bulletin applies to'),
            parameterWithName('engine').optional().description('The vehicle engine the bulletin applies to'),
            parameterWithName('submodel').optional().description('The vehicle submodel the bulletin applies to'),
            parameterWithName('keywords').optional().description('The keywords to search against')]
    }

    FieldDescriptor[] bulletin = new FieldDescriptor().with {
        [fieldWithPath('id').type(JsonFieldType.STRING)
                 .description("The bulletin's id"),
         fieldWithPath('title').type(JsonFieldType.STRING)
                 .description("The bulletin's title")]
    }

    FieldDescriptor[] bulletinsResponseFields = new FieldDescriptor().with {
        [fieldWithPath('took').type(JsonFieldType.NUMBER)
                 .description('The duration of the search'),
         fieldWithPath('total').type(JsonFieldType.NUMBER)
                 .description('The total number of hits'),
         fieldWithPath('max_score').type(JsonFieldType.NUMBER)
                 .description('The maximum score across all hits'),
         fieldWithPath('hits[].id').type(JsonFieldType.STRING)
                 .description("The bulletin's id"),
         fieldWithPath('hits[].title').type(JsonFieldType.STRING)
                 .description("The bulletin's title")]
    }
}
