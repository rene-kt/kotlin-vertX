package com.vertxKotlin.starter

import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.services.DevService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.get

var codec = Json.CODEC as DatabindCodec


class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router: Router = Router.router(vertx)
    router.route().handler(BodyHandler.create());

    val devService: DevService = DevService()

    var devLogged: DevUser = DevUser()

    router.post("/user").handler { req ->
      devLogged = devService.createDevUser(req.bodyAsJson)
      req.response().putHeader("content-type", "application/json").end(Json.encodePrettily(devLogged))
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
