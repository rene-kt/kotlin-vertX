package com.rene.VertxKotlin

import com.rene.VertxKotlin.models.DevUser
import io.vertx.core.json.Json
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.core.Promise

class MainVerticle : AbstractVerticle() {


	val devUser1: DevUser = DevUser(1, "Rene", "renejr.arraes286@gmail.com")


	override fun start(startPromise: Promise<Void>) {

		val router: Router = Router.router(vertx)
		router.route().handler(BodyHandler.create());

		
        router.get("/users").handler{req ->
		
			req.response().putHeader("content-type", "application/json").end(Json.encode(devUser1))
		
		}
		
		
		vertx.createHttpServer().requestHandler(router).listen(8888)
		
	}
}
