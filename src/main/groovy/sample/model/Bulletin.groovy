package sample.model

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotBlank

@CompileStatic
@Document(collection='bulletins')
class Bulletin {

    @Id
    String id

    @NotBlank
    String title
}
