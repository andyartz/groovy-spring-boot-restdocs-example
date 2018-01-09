package sample.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sample.model.Bulletin
import sample.repository.BulletinRepository

import javax.validation.Valid

@RestController
@RequestMapping('/bulletins')
class BulletinsController {

    @Autowired
    BulletinRepository bulletinsRepository

    @PostMapping()
    Mono<Bulletin> createBulletin(@Valid @RequestBody Bulletin bulletin) {
        return bulletinsRepository.save(bulletin)
    }

    @GetMapping()
    Flux<Bulletin> listAllBulletins() {
        return bulletinsRepository.findAll()
    }

    @GetMapping('/{id}')
    Mono<Bulletin> getBulletinById(@PathVariable(value = 'id') String bulletinId) {
        bulletinsRepository.findById(bulletinId)
    }
}
