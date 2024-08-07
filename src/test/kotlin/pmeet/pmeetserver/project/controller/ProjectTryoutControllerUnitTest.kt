package pmeet.pmeetserver.project.controller

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import pmeet.pmeetserver.config.TestSecurityConfig
import pmeet.pmeetserver.project.domain.enum.ProjectTryoutStatus
import pmeet.pmeetserver.project.dto.request.tryout.CreateProjectTryoutRequestDto
import pmeet.pmeetserver.project.dto.request.tryout.ProjectTryoutResponseDto
import pmeet.pmeetserver.project.service.ProjectFacadeService
import pmeet.pmeetserver.user.resume.ResumeGenerator.generateResume
import java.time.LocalDateTime

@WebFluxTest(ProjectTryoutController::class)
@Import(TestSecurityConfig::class)
internal class ProjectTryoutControllerUnitTest : DescribeSpec() {

  @Autowired
  private lateinit var webTestClient: WebTestClient

  @MockkBean
  lateinit var projectFacadeService: ProjectFacadeService

  init {
    describe("createProjectTryout") {
      context("인증된 유저의 프로젝트에 대한 이력서 지원 요청이 들어오면") {
        val resume = generateResume()
        val userId = resume.userId
        val requestDto = CreateProjectTryoutRequestDto(
          projectId = "testProjectId",
          resumeId = resume.id!!,
          positionName = "positionName",
        )
        val createdAt = LocalDateTime.now()
        val responseDto = ProjectTryoutResponseDto(
          id = "testTryoutId",
          resumeId = resume.id!!,
          userId = userId,
          projectId = "testProjectId",
          userName = "userName",
          positionName = "positionName",
          tryoutStatus = ProjectTryoutStatus.INREVIEW,
          createdAt = createdAt
        )

        coEvery {
          projectFacadeService.createProjectTryout(
            userId,
            requestDto
          )
        } answers { responseDto }
        val mockAuthentication = UsernamePasswordAuthenticationToken(userId, null, null)
        val performRequest =
          webTestClient
            .mutateWith(mockAuthentication(mockAuthentication))
            .post()
            .uri("/api/v1/project-tryouts")
            .bodyValue(requestDto)
            .exchange()

        it("서비스를 통해 데이터를 생성한다") {
          coVerify(exactly = 1) { projectFacadeService.createProjectTryout(userId, requestDto) }
        }

        it("요청은 성공한다") {
          performRequest.expectStatus().isCreated
        }

        it("생성된 게시글과 지원서 정보를 반환한다") {
          performRequest.expectBody<ProjectTryoutResponseDto>().consumeWith { response ->
            response.responseBody?.id shouldBe responseDto.id
            response.responseBody?.projectId shouldBe responseDto.projectId
            response.responseBody?.userId shouldBe responseDto.userId
            response.responseBody?.userName shouldBe responseDto.userName
            response.responseBody?.tryoutStatus shouldBe responseDto.tryoutStatus
            response.responseBody?.createdAt shouldBe responseDto.createdAt
          }
        }
      }
    }
  }
}
