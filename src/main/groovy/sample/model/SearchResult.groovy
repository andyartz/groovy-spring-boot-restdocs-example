package sample.model

import groovy.transform.CompileStatic

import javax.validation.constraints.NotBlank

@CompileStatic
class SearchResult {

    @NotBlank
    int took
    int total
    float maxScore
    List<Object> hits
}
