package com.vertxKotlin.starter

import com.vertxKotlin.starter.models.DevUser
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

    router.get("/users").handler { req ->
      req.response().putHeader("content-type", "text/plain").end("Teste")
    }

    router.get("/message").handler { req ->
      req.response().putHeader("content-type", "text/plain").end("Some message")
    }


    router.post("/user").handler { req ->

      var json: JsonObject = req.bodyAsJson

      var devUser: DevUser = DevUser(json["id"], json["name"], json["email"], json["credits"])
      req.response().putHeader("content-type", "application/json").end(Json.encodePrettily(devUser))
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
