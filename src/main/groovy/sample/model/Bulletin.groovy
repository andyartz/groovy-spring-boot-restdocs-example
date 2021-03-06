package sample.model

import groovy.transform.CompileStatic

import javax.validation.constraints.NotBlank

@CompileStatic
class Bulletin {

    String id

    @NotBlank
    String title
}
