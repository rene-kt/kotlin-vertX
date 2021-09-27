package com.vertxKotlin.starter.models

class DevUser {
    var id: Int = 0
    var name: String = ""
    var email: String = ""
    var credits: Int = 0
    // var projects: List<Project>
    var manager: ManagerUser = ManagerUser()

  constructor(id: Int, name: String, email: String, credits: Int){
    this.id = id
    this.name = name
    this.email = email
    this.credits = credits
  }


}
