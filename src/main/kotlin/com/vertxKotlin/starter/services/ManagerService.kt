package com.vertxKotlin.starter.services

import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.ManagerUser
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class ManagerService {

  fun createManagerUser(user: JsonObject): ManagerUser {
    return ManagerUser(1, user["name"], 0)
  }

  fun returnTheIdOfDevs(user: ManagerUser): Int {
    var id: Int = 1;

    for (i in 1..user.devs.size) {
      try {
        user.devs.single { it.id == i }
        id = i + 1;
      } catch (e: NoSuchElementException) {
        return i;
      }
    }
    return id;
  }
}
