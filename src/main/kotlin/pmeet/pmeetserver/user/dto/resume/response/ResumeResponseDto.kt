package pmeet.pmeetserver.user.dto.resume.response

import pmeet.pmeetserver.user.domain.enum.ExperienceYear
import pmeet.pmeetserver.user.domain.enum.Gender
import pmeet.pmeetserver.user.domain.resume.JobExperience
import pmeet.pmeetserver.user.domain.resume.ProjectExperience
import pmeet.pmeetserver.user.domain.resume.Resume
import pmeet.pmeetserver.user.dto.job.response.JobResponseDto
import pmeet.pmeetserver.user.dto.techStack.response.TechStackResponseDto
import java.time.LocalDate

data class ResumeJobExperienceResponseDto(
  val companyName: String,
  val experiencePeriod: ExperienceYear,
  val responsibilities: String
) {
  companion object {
    fun from(jobExperience: JobExperience): ResumeJobExperienceResponseDto {
      return ResumeJobExperienceResponseDto(
        companyName = jobExperience.companyName,
        experiencePeriod = jobExperience.experiencePeriod,
        responsibilities = jobExperience.responsibilities
      )
    }
  }
}

data class ResumeProjectExperienceResponseDto(
  val companyName: String,
  val experiencePeriod: ExperienceYear,
  val responsibilities: String
) {
  companion object {
    fun from(projectExperience: ProjectExperience): ResumeProjectExperienceResponseDto {
      return ResumeProjectExperienceResponseDto(
        companyName = projectExperience.projectName,
        experiencePeriod = projectExperience.experiencePeriod,
        responsibilities = projectExperience.responsibilities
      )
    }
  }
}

data class ResumeResponseDto(
  val id: String,
  val title: String,
  val userName: String,
  val userGender: Gender,
  val userBirthDate: LocalDate,
  val userPhoneNumber: String,
  val userEmail: String,
  val userProfileImageUrl: String,
  val desiredJobs: List<JobResponseDto>,
  val techStacks: List<TechStackResponseDto>,
  val jobExperiences: List<ResumeJobExperienceResponseDto>,
  val projectExperiences: List<ResumeProjectExperienceResponseDto>,
  val portfolioFileUrl: String,
  val portfolioUrl: List<String>,
  val selfDescription: String
) {
  companion object {
    fun from(resume: Resume): ResumeResponseDto {
      return ResumeResponseDto(
        id = resume.id!!,
        title = resume.title,
        userName = resume.userName,
        userGender = resume.userGender,
        userBirthDate = resume.userBirthDate,
        userPhoneNumber = resume.userPhoneNumber,
        userEmail = resume.userEmail,
        userProfileImageUrl = resume.userProfileImageUrl ?: "",
        desiredJobs = resume.desiredJobs.map { JobResponseDto.from(it) },
        techStacks = resume.techStacks.map { TechStackResponseDto.from(it) },
        jobExperiences = resume.jobExperiences.map { ResumeJobExperienceResponseDto.from(it) },
        projectExperiences = resume.projectExperiences.map { ResumeProjectExperienceResponseDto.from(it) },
        portfolioFileUrl = resume.portfolioFileUrl ?: "",
        portfolioUrl = resume.portfolioUrl,
        selfDescription = resume.selfDescription ?: ""
      )
    }
  }
}
