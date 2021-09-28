package com.vertxKotlin.starter.models

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class DevUser: User {

    var manager: ManagerUser = ManagerUser()

  constructor(){}
  constructor(id: Int, name: String, credits: Int, manager: ManagerUser) {
    this.id = id
    this.name = name
    this.credits = credits
    this.manager = manager
  }

}
