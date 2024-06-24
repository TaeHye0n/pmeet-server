package pmeet.pmeetserver.user.dto.resume.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import pmeet.pmeetserver.user.domain.enum.ExperienceYear
import pmeet.pmeetserver.user.domain.enum.Gender
import pmeet.pmeetserver.user.domain.job.Job
import pmeet.pmeetserver.user.domain.resume.JobExperience
import pmeet.pmeetserver.user.domain.resume.ProjectExperience
import pmeet.pmeetserver.user.domain.resume.Resume
import pmeet.pmeetserver.user.domain.techStack.TechStack
import java.time.LocalDate

data class ResumeJobRequestDto(
  val id: String,
  val name: String
) {
  fun toEntity(): Job {
    return Job(
      id = this.id,
      name = this.name
    )
  }
}

data class ResumeTechStackRequestDto(
  val id: String,
  val name: String
) {
  fun toEntity(): TechStack {
    return TechStack(
      id = this.id,
      name = this.name
    )
  }
}

data class ResumeJobExperienceRequestDto(
  val companyName: String,
  val experiencePeriod: ExperienceYear,
  val responsibilities: String
) {
  fun toEntity(): JobExperience {
    return JobExperience(
      companyName = this.companyName,
      experiencePeriod = this.experiencePeriod,
      responsibilities = this.responsibilities
    )
  }
}

data class ResumeProjectExperienceRequestDto(
  val projectName: String,
  val experiencePeriod: ExperienceYear,
  val responsibilities: String
) {
  fun toEntity(): ProjectExperience {
    return ProjectExperience(
      projectName = this.projectName,
      experiencePeriod = this.experiencePeriod,
      responsibilities = this.responsibilities
    )
  }
}

data class CreateResumeRequestDto(
  @field:NotBlank(message = "제목은 필수입니다.")
  @field:Size(min = 1, max = 30, message = "제목은 1글자에서 30글자 사이여야 합니다.")
  @field:Pattern(
    regexp = "^[가-힣a-zA-Z0-9\\p{Punct}]*$",
    message = "제목은 한글, 영어, 숫자, 문장부호만 입력 가능합니다."
  )
  val title: String,
  val isActive: Boolean,
  @field:NotBlank(message = "생성자 아이디는 필수입니다.")
  val userId: String,
  @field:NotBlank(message = "사용자 이름은 필수입니다.")
  val userName: String,
  @field:NotBlank(message = "사용자 성별은 필수입니다.")
  val userGender: Gender,
  @field:NotBlank(message = "사용자 생년월일은 필수입니다.")
  val userBirthDate: LocalDate,
  @field:NotBlank(message = "사용자 전화 번호는 필수입니다.")
  val userPhoneNumber: String,
  @field:NotBlank(message = "사용자 이메일은 필수입니다.")
  @field:Email(message = "유효한 이메일 주소를 입력해주세요.")
  val userEmail: String,
  val userProfileImageUrl: String?,
  @field:Size(max = 5, message = "최대 5개의 희망 직무만 입력 가능합니다.")
  val desiredJobs: List<ResumeJobRequestDto>,
  @field:Size(max = 5, message = "최대 5개의 기술 스택만 입력 가능합니다.")
  val techStacks: List<ResumeTechStackRequestDto>,
  val jobExperiences: List<ResumeJobExperienceRequestDto>,
  val projectExperiences: List<ResumeProjectExperienceRequestDto>,
  val portfolioFileUrl: String?,
  @field:Size(max = 3, message = "최대 3개의 포트폴리오 링크만 입력 가능합니다.")
  val portfolioUrl: List<String>,
  @field:Size(max = 500, message = "자기소개는 최대 500자까지 입력 가능합니다.")
  val selfDescription: String?
) {
  fun toEntity(): Resume {
    return Resume(
      title = this.title,
      isActive = this.isActive,
      userId = this.userId,
      userName = this.userName,
      userGender = this.userGender,
      userBirthDate = this.userBirthDate,
      userPhoneNumber = this.userPhoneNumber,
      userEmail = this.userEmail,
      userProfileImageUrl = this.userProfileImageUrl,
      desiredJobs = this.desiredJobs.map { it.toEntity() },
      techStacks = this.techStacks.map { it.toEntity() },
      jobExperiences = this.jobExperiences.map { it.toEntity() },
      projectExperiences = this.projectExperiences.map { it.toEntity() },
      portfolioFileUrl = this.portfolioFileUrl,
      portfolioUrl = this.portfolioUrl,
      selfDescription = this.selfDescription
    )
  }

}