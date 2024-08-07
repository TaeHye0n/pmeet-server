package pmeet.pmeetserver.project.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import pmeet.pmeetserver.project.domain.ProjectTryout
import reactor.core.publisher.Mono

interface ProjectTryoutRepository : ReactiveMongoRepository<ProjectTryout, String> {
  fun deleteByProjectId(projectId: String): Mono<Void>
}
