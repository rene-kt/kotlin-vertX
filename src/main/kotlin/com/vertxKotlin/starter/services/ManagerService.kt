package com.vertxKotlin.starter.services

import com.vertxKotlin.starter.exceptions.ObjectNotFoundException
import com.vertxKotlin.starter.exceptions.UserNotLoggedException
import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.ManagerUser
import com.vertxKotlin.starter.models.Project
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.get

class ManagerService : AbstractService() {

  fun createManagerUser(user: JsonObject): ManagerUser {
    return ManagerUser(1, user["name"], 0)
  }

  fun createDevUser(manager: ManagerUser, user: JsonObject) {

    val projectsJson: JsonArray = user["projects"]
    var devUser: DevUser = DevUser()

    devUser.name = user["name"]
    devUser.id = returnTheIdOfDevs(manager)

    for (i in 0..projectsJson.size() - 1) {
      var project: Project = Project()
      project.id = returnTheIdOfProject(devUser)
      project.name = projectsJson.getJsonObject(i)["name"]
      project.language = projectsJson.getJsonObject(i)["language"]

      devUser.projects.add(project)
    }

    manager.devs.add(devUser)

  }

  fun deleteDev(manager: ManagerUser, devId: Int) {
    manager.devs.remove(findDevById(manager, devId))
  }

  fun addProjectsToADevUser(manager: ManagerUser, projects: JsonObject, devId: Int){
    var dev = findDevById(manager, devId)
    var project = Project(projects["name"], projects["language"])
    project.id = returnTheIdOfProject(dev)
    dev.projects.add(project)

  }

  fun changeCredits(manager: ManagerUser, devId: Int, credits: Int) {

    if (manager.id == 0) throw UserNotLoggedException("You need to create your account first")
    if (devId == 0) {
      manager.credits += credits
    } else {
      findDevById(manager, devId).credits += credits

    }

  }

  private fun findDevById(manager: ManagerUser, id: Int): DevUser {
    try {
      return manager.devs.single { it.id == id }
    } catch (e: NoSuchElementException) {
      throw ObjectNotFoundException("This object was not found")
    }
  }


  private fun returnTheIdOfDevs(user: ManagerUser): Int {
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
