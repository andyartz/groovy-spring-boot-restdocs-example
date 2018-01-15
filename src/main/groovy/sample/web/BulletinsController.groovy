package sample.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sample.model.Bulletin
import sample.model.SearchResult

import javax.validation.Valid

@RestController
@RequestMapping(BulletinsController.ENDPOINT)
class BulletinsController {

    public static final String ENDPOINT = '/kraken/bulletins'

    @PostMapping()
    ResponseEntity<Bulletin> createBulletin(@Valid @RequestBody Bulletin bulletin) {
        Bulletin createdBulletin = new Bulletin(id: '987915b-ec7e-488c-e5a6-a59b6aa1698f', title: bulletin.title)
        HttpStatus status = HttpStatus.CREATED
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>()
        headers.set('Deprecation-Warning', 'This method is going away in version 42.0.0.')
        new ResponseEntity<Bulletin>(createdBulletin, headers, status)
//        return bulletinsRepository.save(bulletin)
    }

    @GetMapping()
    SearchResult listAllBulletins() {
        Bulletin[] bulletins = []
        bulletins += new Bulletin(id: '6724915b-df7e-49dc-b9b6-a1b36a1a8a73', title: 'Bad Flux Capacitor')
        bulletins += new Bulletin(id: '9d3f3fe4-bdc2-4181-9d3e-c2259bb5f541', title: 'JohnnyCab AI Navigation Failure')
//        bulletins
        return new SearchResult(took: 3, total: 2, maxScore: 0.8675309, hits: bulletins)
//        return bulletinsRepository.findAll()
    }

    @GetMapping('/{id}')
    Bulletin getBulletinById(@PathVariable(value = 'id') String bulletinId) {
        Bulletin bulletin = new Bulletin(id: bulletinId, title: "Faulty Handbrake $bulletinId")
        bulletin
//        bulletinsRepository.findById(bulletinId)
    }
}
