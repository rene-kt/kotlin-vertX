package com.vertxKotlin.starter.models

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class DevUser: User {


  constructor(){}
  constructor(id: Int, name: String, credits: Int) {
    this.id = id
    this.name = name
    this.credits = credits
  }

}
