package com.vertxKotlin.starter.verticles

import com.vertxKotlin.starter.exceptions.ObjectNotFoundException
import com.vertxKotlin.starter.exceptions.UserNotLoggedException
import com.vertxKotlin.starter.handlers.ExceptionsResponseHandler
import com.vertxKotlin.starter.handlers.ResponseHandler
import com.vertxKotlin.starter.models.ManagerUser
import com.vertxKotlin.starter.models.Project
import com.vertxKotlin.starter.services.ManagerService
import io.vertx.core.AbstractVerticle
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.get

class ManagerVerticle : AbstractVerticle() {

  private val router: Router = Router.router(vertx)
  private val exceptionsResponseHandler = ExceptionsResponseHandler()
  private val managerService = ManagerService()

  fun returnManager(managerLogged: ManagerUser, req: RoutingContext) {
    if (managerLogged.id == 0) exceptionsResponseHandler.userNotLoggedExceptionResponse(req, UserNotLoggedException())

    req.response().setStatusCode(200).putHeader("content-type", "application/json")
      .end(Json.encodePrettily(ResponseHandler(200, "Successful search", JsonObject.mapFrom(managerLogged))))

  }

  fun createManager(req: RoutingContext): ManagerUser {
    var managerLogged = ManagerUser()
    try {
      managerLogged = managerService.createManagerUser(req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(managerLogged))))
      return managerLogged
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    }
    return managerLogged
  }

  fun createDevUser(managerLogged: ManagerUser, req: RoutingContext) {

    try {
      managerService.createDevUser(managerLogged, req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "A new dev was created", JsonObject.mapFrom(managerLogged))))
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    }

  }

  fun deleteDevUser(managerLogged: ManagerUser, req: RoutingContext) {
    val devId: Int = req.request().getParam("devId").toInt()

    try {
      managerService.deleteDev(managerLogged, devId)
      req.response().putHeader("content-type", "application/json").setStatusCode(200).end(Json.encodePrettily(ResponseHandler(204, "The dev was deleted", JsonObject.mapFrom(managerLogged))))
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    } catch (e: ObjectNotFoundException) {
      exceptionsResponseHandler.objectNotFoundExceptionResponse(req, e)
    }
  }

  fun changeCredits(managerLogged: ManagerUser, req: RoutingContext) {

    try {

      managerService.changeCredits(managerLogged, req.bodyAsJson["devId"], req.bodyAsJson["credits"])
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(managerLogged))))
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    } catch (e: ObjectNotFoundException) {
      exceptionsResponseHandler.objectNotFoundExceptionResponse(req, e)
    }
  }

  fun createProject(managerLogged: ManagerUser, req: RoutingContext) {
    val project = Project()

    try {
      managerService.createProject(managerLogged, project.jsonToObject(req.bodyAsJson))
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(
          Json.encodePrettily(
            ResponseHandler(
              201,
              "Your project was created!",
              JsonObject.mapFrom(managerLogged)
            )
          )
        )
    } catch (e: NullPointerException) {
      exceptionsResponseHandler.nullRequestBodyResponse(req, e)
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    }
  }

  fun deleteProject(managerLogged: ManagerUser, req: RoutingContext){

    val projectId: Int = req.request().getParam("projectId").toInt()

    try {
      managerService.deleteProject(managerLogged, projectId)
      req.response().putHeader("content-type", "application/json").setStatusCode(200).end(Json.encodePrettily(ResponseHandler(204, "The project was deleted", JsonObject.mapFrom(managerLogged))))
    } catch (e: UserNotLoggedException) {
      exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
    } catch (e: ObjectNotFoundException) {
      exceptionsResponseHandler.objectNotFoundExceptionResponse(req, e)
    }
  }

}
