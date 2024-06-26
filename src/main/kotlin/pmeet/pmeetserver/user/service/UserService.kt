package pmeet.pmeetserver.user.service

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pmeet.pmeetserver.common.ErrorCode
import pmeet.pmeetserver.common.exception.BadRequestException
import pmeet.pmeetserver.common.exception.EntityDuplicateException
import pmeet.pmeetserver.common.exception.EntityNotFoundException
import pmeet.pmeetserver.user.domain.User
import pmeet.pmeetserver.user.repository.UserRepository

@Service
class UserService(
  private val userRepository: UserRepository
) {
  @Transactional
  suspend fun save(user: User): User {
    userRepository.findByEmailAndIsDeletedFalse(user.email).awaitSingleOrNull()?.let {
      throw EntityDuplicateException(ErrorCode.USER_DUPLICATE_BY_EMAIL)
    }

    userRepository.findByNicknameAndIsDeletedFalse(user.nickname).awaitSingleOrNull()?.let {
      throw EntityDuplicateException(ErrorCode.USER_DUPLICATE_BY_NICKNAME)
    }

    return userRepository.save(user).awaitSingle()
  }

  @Transactional(readOnly = true)
  suspend fun getUserByNickname(nickname: String): User {
    return userRepository.findByNicknameAndIsDeletedFalse(nickname).awaitSingleOrNull()
      ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOUND_BY_NICKNAME)
  }

  @Transactional(readOnly = true)
  suspend fun getUserByEmail(email: String): User {
    return userRepository.findByEmailAndIsDeletedFalse(email).awaitSingleOrNull()
      ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOUND_BY_EMAIL)
  }

  @Transactional(readOnly = true)
  suspend fun getUserById(userId: String): User {
    return userRepository.findById(userId).awaitSingleOrNull()
      ?.apply { if (isDeleted) throw BadRequestException(ErrorCode.IS_DELETED_USER) }
      ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOUND_BY_ID)
  }

  @Transactional(readOnly = true)
  suspend fun findUserByNickname(nickname: String): User? {
    return userRepository.findByNicknameAndIsDeletedFalse(nickname).awaitSingleOrNull()
  }

  @Transactional(readOnly = true)
  suspend fun findUserByEmail(email: String): User? {
    return userRepository.findByEmailAndIsDeletedFalse(email).awaitSingleOrNull()
  }

  @Transactional
  suspend fun update(user: User): User {
    return userRepository.save(user).awaitSingle()
  }

  @Transactional(readOnly = true)
  suspend fun findUserWithHighestNicknameNumber(): User? {
    return userRepository.findFirstByIsDeletedFalseOrderByNicknameNumberDesc().awaitSingleOrNull()
  }
}

