package com.vertxKotlin.starter.services

import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.ManagerUser
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class DevService: AbstractService() {

  fun createDevUser(user: JsonObject): DevUser {
    return DevUser(1, user["name"], 0, ManagerUser())
  }
}
