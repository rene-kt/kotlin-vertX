package com.vertxKotlin.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.pgclient.PgConnectOptions
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions


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
      println(json)
      req.response().putHeader("content-type", "application/json").end(Json.encode(json))
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

    val connectOptions = PgConnectOptions()
      .setPort(5400)
      .setHost("rene")
      .setDatabase("vertx")
      .setUser("postgres")
      .setPassword("admin")
// Pool options
    val poolOptions = PoolOptions()
      .setMaxSize(5)

// Create the pooled client
    val client = PgPool.client(vertx, connectOptions, poolOptions)

    client.query("create table teste")
  }
}
