package com.vertxKotlin.starter.models

class ManagerUser: User {

  var devs: ArrayList<DevUser> = ArrayList<DevUser>();

  constructor(){}

  constructor(id: Int, name: String, credits: Int) {
    this.id = id
    this.name = name
    this.credits = credits
  }


}
