package com.vertxKotlin.starter.models

class Project {

  var id: Int = 0;
  var name: String = ""
  var language: String = ""


  constructor(){}
  constructor(name: String, language: String){
    this.name = name
    this.language = language
  }
}
