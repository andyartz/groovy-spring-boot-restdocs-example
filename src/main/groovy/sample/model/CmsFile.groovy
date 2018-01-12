package sample.model

import groovy.transform.CompileStatic

import javax.validation.constraints.NotBlank

@CompileStatic
class CmsFile {

    String id

    @NotBlank
    String filename
}
