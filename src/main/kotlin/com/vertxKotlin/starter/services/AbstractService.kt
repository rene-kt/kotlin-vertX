package com.vertxKotlin.starter.services

import com.vertxKotlin.starter.models.Project
import com.vertxKotlin.starter.models.User

class AbstractService {



  fun createProject(user: User, project: Project){
      project.id = returnTheIdOfProject(user)
      user.projects.add(project)
  }

  fun deleteProject(user:User, projectId: Int) {

    try {
      var project: Project = user.projects.single { it.id == projectId }
      user.projects.remove(project)
    } catch (e: NoSuchElementException) {
      throw NoSuchElementException()
    }

  }

  private fun returnTheIdOfProject(user: User): Int {
    var id: Int = 1;

    for (i in 1..user.projects.size) {
      try {
        user.projects.single { it.id == i }
        id = i + 1;
      } catch (e: NoSuchElementException) {
        return i;
      }
    }
    return id;
  }

}
