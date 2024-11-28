package com.example.server.user

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/user")
class UserController(
    private val userRepository: UserRepository,
) {
    @GetMapping
    fun getUser(authentication: Authentication): UserRecord? {
        if (authentication.principal is OidcUser) {
            val oidcUser = authentication.principal as OidcUser
            val userRecord = userRepository.findBySubject(oidcUser.subject)

            if (userRecord == null) {
                return userRepository.save(UserRecord(
                    username = oidcUser.getClaimAsString("name"),
                    subject = oidcUser.subject,
                ))
            } else {
                return userRecord
            }
        }
        return null
    }
}