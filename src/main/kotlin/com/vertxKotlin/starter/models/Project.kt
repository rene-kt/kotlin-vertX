package com.vertxKotlin.starter.models

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class Project {

  var id: Int = 0;
  var name: String = ""
  var language: String = ""


  constructor(){}
  constructor(name: String, language: String){
    this.name = name
    this.language = language
  }

   fun jsonToObject(json: JsonObject): Project{

     var project: Project = Project()
    project.name = json["name"]
    project.language = json["language"]

     return project
  }
}
