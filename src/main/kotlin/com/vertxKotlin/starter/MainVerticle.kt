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
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

var codec = Json.CODEC as DatabindCodec


class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router: Router = Router.router(vertx)
    router.route().handler(BodyHandler.create());

    val devService: DevService = DevService()
    val managerService: ManagerService = ManagerService()

    var devLogged: DevUser = DevUser()
    var managerLogged: ManagerUser = ManagerUser()

    val exceptionsResponseHandler: ExceptionsResponseHandler = ExceptionsResponseHandler()

    router.post("/manageruser").handler { req ->
      managerLogged = managerService.createManagerUser(req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "A new dev was created", JsonObject.mapFrom(managerLogged))))
    }

    router.post("/manageruser/devuser").handler { req ->
      managerService.createDevUser(managerLogged, req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(managerLogged))))
    }



    router.post("/devuser").handler { req ->
      devLogged = devService.createDevUser(req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json")
        .end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(devLogged))))
    }

    router.post("/devuser/project").handler { req ->
      var project: Project = Project()

      try {
        devService.createProject(devLogged, project.jsonToObject(req.bodyAsJson))
        req.response().putHeader("content-type", "application/json")
          .end(Json.encodePrettily(ResponseHandler(201, "Your project was created!", JsonObject.mapFrom(devLogged))))
      } catch (e: UserNotLoggedException) {
        exceptionsResponseHandler.userNotLoggedExceptionResponse(req, e)
      }
    }

    router.delete("/devuser/project/:projectId").handler { req ->

      var projectId: Int = req.request().getParam("projectId").toInt()

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
          startPromise.fail(http.cause());
        }
      }
  }
}
