package pmeet.pmeetserver.project.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pmeet.pmeetserver.common.ErrorCode
import pmeet.pmeetserver.common.exception.ForbiddenRequestException
import pmeet.pmeetserver.project.domain.enum.ProjectTryoutStatus
import java.time.LocalDateTime

@Document
class ProjectTryout(
    @Id
    var id: String? = null,
    val resumeId: String,
    val userId: String,
    val userName: String,
    val userSelfDescription: String,
    val positionName: String,
    var tryoutStatus: ProjectTryoutStatus,
    val projectId: String,
    val createdAt: LocalDateTime,
) {
    fun updateStatus(tryoutStatus: ProjectTryoutStatus) {
        if (this.tryoutStatus === ProjectTryoutStatus.INREVIEW) {
            this.tryoutStatus = tryoutStatus
        } else {
            throw ForbiddenRequestException(ErrorCode.PROJECT_TRYOUT_STATUS_UPDATE_FAIL)
        }
    }

    fun createProjectMember(): ProjectMember {
        return ProjectMember(
            resumeId = resumeId,
            userId = userId,
            userName = userName,
            userSelfDescription = userSelfDescription,
            projectId = projectId,
            createdAt = LocalDateTime.now()
        )
    }
}