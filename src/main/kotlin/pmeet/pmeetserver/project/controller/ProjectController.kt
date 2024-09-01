package pmeet.pmeetserver.project.controller

import jakarta.validation.Valid
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pmeet.pmeetserver.project.dto.comment.response.GetProjectCommentWithChildResponseDto
import pmeet.pmeetserver.project.dto.request.CreateProjectRequestDto
import pmeet.pmeetserver.project.dto.request.SearchProjectRequestDto
import pmeet.pmeetserver.project.dto.request.UpdateProjectRequestDto
import pmeet.pmeetserver.project.dto.response.GetOwnedProjectResponseDto
import pmeet.pmeetserver.project.dto.response.ProjectResponseDto
import pmeet.pmeetserver.project.dto.response.ProjectWithUserResponseDto
import pmeet.pmeetserver.project.dto.response.SearchProjectResponseDto
import pmeet.pmeetserver.project.enums.ProjectFilterType
import pmeet.pmeetserver.project.enums.ProjectSortProperty
import pmeet.pmeetserver.project.service.ProjectFacadeService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/projects")
class ProjectController(
  private val projectFacadeService: ProjectFacadeService
) {

  @GetMapping("/{projectId}")
  @ResponseStatus(HttpStatus.OK)
  suspend fun getProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @PathVariable projectId: String
  ): ProjectWithUserResponseDto {
    return projectFacadeService.getProjectByProjectId(userId.awaitSingle(), projectId)
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  suspend fun createProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @RequestBody @Valid requestDto: CreateProjectRequestDto
  ): ProjectResponseDto {
    return projectFacadeService.createProject(userId.awaitSingle(), requestDto)
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  suspend fun updateProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @RequestBody @Valid requestDto: UpdateProjectRequestDto
  ): ProjectResponseDto {
    return projectFacadeService.updateProject(userId.awaitSingle(), requestDto)
  }

  @DeleteMapping("/{projectId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  suspend fun deleteProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @PathVariable projectId: String
  ) {
    projectFacadeService.deleteProject(userId.awaitSingle(), projectId)
  }

  @GetMapping("/{projectId}/comments")
  @ResponseStatus(HttpStatus.OK)
  suspend fun getProjectCommentList(
    @PathVariable projectId: String
  ): List<GetProjectCommentWithChildResponseDto> {
    return projectFacadeService.getProjectCommentList(projectId)
  }

  @GetMapping("/search-slice")
  @ResponseStatus(HttpStatus.OK)
  suspend fun searchProjectSlice(
    @AuthenticationPrincipal userId: Mono<String>,
    @RequestParam(defaultValue = "false") isCompleted: Boolean,
    @RequestParam(required = false) filterType: ProjectFilterType?,
    @RequestParam(required = false) filterValue: String?,
    @RequestParam(defaultValue = "0") page: Int,
    @RequestParam(defaultValue = "8") size: Int,
    @RequestParam(defaultValue = "BOOK_MARKERS") sortBy: ProjectSortProperty,
    @RequestParam(defaultValue = "DESC") direction: Direction
  ): Slice<SearchProjectResponseDto> {
    val requestDto = SearchProjectRequestDto.of(isCompleted, filterType, filterValue, page, size, sortBy, direction)
    return projectFacadeService.searchProjectSlice(userId.awaitSingle(), requestDto)
  }

  @PutMapping("/{projectId}/bookmark")
  @ResponseStatus(HttpStatus.OK)
  suspend fun addBookmarkProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @PathVariable projectId: String
  ) {
    projectFacadeService.addBookmark(userId.awaitSingle(), projectId)
  }

  @DeleteMapping("/{projectId}/bookmark")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  suspend fun deleteBookmarkProject(
    @AuthenticationPrincipal userId: Mono<String>,
    @PathVariable projectId: String
  ) {
    projectFacadeService.deleteBookmark(userId.awaitSingle(), projectId)
  }

  @GetMapping("/owned")
  @ResponseStatus(HttpStatus.OK)
  suspend fun getOwnedProjectSlice(
    @AuthenticationPrincipal userId: Mono<String>,
    @RequestParam(defaultValue = "0") page: Int,
    @RequestParam(defaultValue = "6") size: Int
  ): Slice<GetOwnedProjectResponseDto> {
    return projectFacadeService.getOwnedProjectSlice(userId.awaitSingle(), PageRequest.of(page, size))
  }
//
//  @GetMapping("/my/im-progress")
//  @ResponseStatus(HttpStatus.OK)
//  suspend fun getMyProjectSlice(
//    @AuthenticationPrincipal userId: Mono<String>,
//    @RequestParam(defaultValue = "0") page: Int,
//    @RequestParam(defaultValue = "6") size: Int
//  ): Slice<GetMyProjectResponseDto> {
////    return projectFacadeService.getMyProjectSlice(userId.awaitSingle(), PageRequest.of(page, size))
//  }
}
