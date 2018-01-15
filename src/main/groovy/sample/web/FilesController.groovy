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

    public static final String ENDPOINT = '/kraken/files'

    @GetMapping('/{id}')
    ResponseEntity<byte[]> getCmsFileById(@PathVariable(value = 'id') String cmsFileId) {
        byte[] content
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>()

        if (cmsFileId == '42') {
            content = getClass().getResource('image.jpg').bytes
            headers.set('Content-Type', 'image/jpeg')
        } else {
            content = getClass().getResource('page.html').bytes
            headers.set('Content-Type', 'text/html')
        }
        HttpStatus status = HttpStatus.OK
        new ResponseEntity<byte[]>(content, headers, status)
    }
}
