package com.vertxKotlin.starter.models

open class User {
  var id: Int = 0
  var name: String = ""
  var credits: Int = 0
  var projects: ArrayList<Project> = ArrayList<Project>()
}
