package pmeet.pmeetserver.project.dto.response

import pmeet.pmeetserver.project.domain.Project
import pmeet.pmeetserver.user.domain.User
import java.time.LocalDateTime

data class MyProjectUserInfoDto(
  val userId: String,
  val userName: String,
  val userProfileImageDownloadUrl: String?
) {
  companion object {
    fun of(user: User, profileImageDownloadUrl: String): MyProjectUserInfoDto {
      return MyProjectUserInfoDto(
        userId = user.id!!,
        userName = user.name,
        userProfileImageDownloadUrl = profileImageDownloadUrl
      )
    }
  }
}

data class GetMyProjectResponseDto(
  val projectId: String,
  val title: String,
  val description: String,
  val thumbNailDownloadUrl: String,
  val projectStartDate: LocalDateTime,
  val projectEndDate: LocalDateTime,
  val projectUsers: List<MyProjectUserInfoDto>,
  val jobName: String,
) {
  companion object {
    fun of(
      project: Project,
      thumbNailDownloadUrl: String,
      jobName: String,
      projectUsers: List<MyProjectUserInfoDto>
    ): GetMyProjectResponseDto {
      return GetMyProjectResponseDto(
        projectId = project.id!!,
        title = project.title,
        description = project.description,
        thumbNailDownloadUrl = thumbNailDownloadUrl,
        projectStartDate = project.startDate,
        projectEndDate = project.endDate,
        projectUsers = projectUsers,
        jobName = jobName
      )
    }
  }
}
