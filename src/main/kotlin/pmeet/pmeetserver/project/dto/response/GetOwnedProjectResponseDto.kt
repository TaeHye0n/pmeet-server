package pmeet.pmeetserver.project.dto.response

import pmeet.pmeetserver.project.domain.Project
import java.time.LocalDateTime

data class GetOwnedProjectResponseDto(
  val id: String,
  val title: String,
  val startDate: LocalDateTime,
  val thumbNailUrl: String?,
  val description: String,
  val isCompleted: Boolean,
  val createdAt: LocalDateTime,
) {
  companion object {
    fun of(project: Project, thumbNailDownloadUrl: String?): GetOwnedProjectResponseDto {
      return GetOwnedProjectResponseDto(
        id = project.id!!,
        title = project.title,
        startDate = project.startDate,
        thumbNailUrl = thumbNailDownloadUrl,
        description = project.description,
        isCompleted = project.isCompleted,
        createdAt = project.createdAt
      )
    }
  }
}
