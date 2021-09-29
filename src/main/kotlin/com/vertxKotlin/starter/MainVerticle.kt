package com.vertxKotlin.starter

import com.vertxKotlin.starter.exceptions.ObjectNotFoundException
import com.vertxKotlin.starter.exceptions.UserNotLoggedException
import com.vertxKotlin.starter.handlers.ExceptionsResponseHandler
import com.vertxKotlin.starter.handlers.ResponseHandler
import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.ManagerUser
import com.vertxKotlin.starter.models.Project
import com.vertxKotlin.starter.services.DevService
import com.vertxKotlin.starter.services.ManagerService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get


class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router: Router = Router.router(vertx)
    router.route().handler(BodyHandler.create())

    val devService = DevService()
    val managerService = ManagerService()

    var devLogged = DevUser()
    var managerLogged = ManagerUser()

    val exceptionsResponseHandler = ExceptionsResponseHandler()

    router.get("/manageruser").handler { req ->
      if (managerLogged.id == 0) exceptionsResponseHandler.userNotLoggedExceptionResponse(req, UserNotLoggedException())

      req.response().setStatusCode(200).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(200, "Successful search", JsonObject.mapFrom(managerLogged))))
    }

    router.post("/manageruser").handler { req ->

      try {
        managerLogged = managerService.createManagerUser(req.bodyAsJson)
        req.response().setStatusCode(201).putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "A new dev was created", JsonObject.mapFrom(managerLogged))))
      } catch (e: NullPointerException) {
        exceptionsResponseHandler.nullRequestBodyResponse(req, e)
      }

    }

    router.post("/manageruser/devuser").handler { req ->

      try {
        managerService.createDevUser(managerLogged, req.bodyAsJson)
        req.response().setStatusCode(201).putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(managerLogged))))
      } catch (e: NullPointerException) {
        exceptionsResponseHandler.nullRequestBodyResponse(req, e)
      } catch (e: UserNotLoggedException) {
        exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
      }

    }

    router.post("/manageruser/credits").handler { req ->

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

    router.post("/manageruser/project").handler { req ->
      var project = Project()

      try {
        managerService.createProject(managerLogged, project.jsonToObject(req.bodyAsJson))
        req.response().putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "Your project was created!", JsonObject.mapFrom(managerLogged))))
      }
      catch(e: NullPointerException){
        exceptionsResponseHandler.nullRequestBodyResponse(req, e)
      }
      catch (e: UserNotLoggedException) {
        exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
      }
    }


    router.get("/devuser").handler { req ->
      if (devLogged.id == 0) exceptionsResponseHandler.userNotLoggedExceptionResponse(req, UserNotLoggedException())

      req.response().setStatusCode(200).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(200, "Successful search", JsonObject.mapFrom(devLogged))))
    }

    router.post("/devuser").handler { req ->
      try {
        devLogged = devService.createDevUser(req.bodyAsJson)
        req.response().setStatusCode(201).putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(devLogged))))
      } catch (e: NullPointerException) {
        exceptionsResponseHandler.nullRequestBodyResponse(req, e)
      }
    }

    router.post("/devuser/project").handler { req ->
      var project = Project()

      try {
        devService.createProject(devLogged, project.jsonToObject(req.bodyAsJson))
        req.response().putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "Your project was created!", JsonObject.mapFrom(devLogged))))
      }
      catch(e: NullPointerException){
        exceptionsResponseHandler.nullRequestBodyResponse(req, e)
      }
      catch (e: UserNotLoggedException) {
        exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
      }
    }

    router.delete("/devuser/project/:projectId").handler { req ->

      val projectId: Int = req.request().getParam("projectId").toInt()

      try {
        devService.deleteProject(devLogged, projectId)
        req.response().putHeader("content-type", "application/json").setStatusCode(204).end()
      } catch (e: UserNotLoggedException) {
        exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
      } catch (e: ObjectNotFoundException) {
        exceptionsResponseHandler.objectNotFoundExceptionResponse(req, e)
      }
    }

    vertx.createHttpServer().requestHandler(router)
      .listen(8888) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause())
        }
      }
  }
}
