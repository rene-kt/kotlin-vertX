package com.vertxKotlin.starter

import com.vertxKotlin.starter.handlers.ResponseHandler
import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.Project
import com.vertxKotlin.starter.services.DevService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

var codec = Json.CODEC as DatabindCodec


class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router: Router = Router.router(vertx)
    router.route().handler(BodyHandler.create());

    val devService: DevService = DevService()

    var devLogged: DevUser = DevUser()

    router.post("/devuser").handler { req ->
      devLogged = devService.createDevUser(req.bodyAsJson)
      req.response().setStatusCode(201).putHeader("content-type", "application/json").end(Json.encodePrettily(ResponseHandler(201, "Your account was created", JsonObject.mapFrom(devLogged))))
    }

    router.post("/devuser/project").handler { req ->
      var project: Project = Project()

      try {
        devService.createProject(devLogged, project.jsonToObject(req.bodyAsJson))
        req.response().putHeader("content-type", "application/json").end(Json.encodePrettily(ResponseHandler(201, "Your project was created!", JsonObject.mapFrom(project))))
      }catch(e: NoSuchElementException){
        req.response().setStatusCode(403).putHeader("content-type", "application/json").end(Json.encodePrettily(ResponseHandler(403, "You need to create an account first", null)))
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
