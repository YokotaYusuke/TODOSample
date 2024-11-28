package com.example.server.user

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
@Transactional
interface UserRepository: JpaRepository<UserRecord, UUID> {
    fun findBySubject(subject: String): UserRecord?
}