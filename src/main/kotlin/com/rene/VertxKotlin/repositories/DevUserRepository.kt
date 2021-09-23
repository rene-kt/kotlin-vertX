package com.rene.VertxKotlin.repositories

import com.rene.VertxKotlin.models.DevUser
import org.springframework.data.jpa.repository.JpaRepository

interface DevUserRepository: JpaRepository<DevUser, Int>{
}