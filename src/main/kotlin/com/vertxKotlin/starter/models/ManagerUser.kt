package com.vertxKotlin.starter.models

class ManagerUser {
  var id: Int = 0
  var name: String = ""
  var email: String = ""
  var credits: Int = 0
  var devs: ArrayList<DevUser> = ArrayList<DevUser>();

  constructor() {}

  constructor(id: Int, name: String, credits: Int, devs: Array<DevUser>) {
    this.id = id
    this.name = name
    this.credits = credits
    this.devs.addAll(devs)
  }

}
