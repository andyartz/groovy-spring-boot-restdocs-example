package sample.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(FilesController.ENDPOINT)
class FilesController {

    public static final String ENDPOINT = '/files'

    @GetMapping('/{id}')
    ResponseEntity<byte[]> getCmsFileById(@PathVariable(value = 'id') String cmsFileId) {
        cmsFileId // this does nothing but keep codenarc from barking until implementation
        byte[] content = getClass().getResource('image.gif').bytes
        HttpStatus status = HttpStatus.OK
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>()
        headers.set('Content-Type', 'image/gif')
        new ResponseEntity<byte[]>(content, headers, status)
    }
}
