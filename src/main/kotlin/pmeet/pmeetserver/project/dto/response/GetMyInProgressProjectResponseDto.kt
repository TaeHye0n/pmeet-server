package pmeet.pmeetserver.project.dto.response

import pmeet.pmeetserver.project.domain.Project

data class GetMyInProgressProjectResponseDto(
  val id: String,
  val title: String,
  val description: String,
  val thumbNailUrl: String?,
  val positionName: String?,
  val userInfos: List<ProjectMemberInfoDto>,
) {

  companion object {
    fun of(
      project: Project,
      positionName: String?,
      thumbNailDownloadUrl: String?,
      userInfos: List<ProjectMemberInfoDto>
    ): GetMyInProgressProjectResponseDto {
      return GetMyInProgressProjectResponseDto(
        id = project.id!!,
        title = project.title,
        description = project.description,
        thumbNailUrl = thumbNailDownloadUrl,
        positionName = positionName,
        userInfos = userInfos
      )
    }
  }
}
