package sample.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import sample.model.Bulletin

@Repository
interface BulletinRepository extends ReactiveCrudRepository<Bulletin, String> {
}
