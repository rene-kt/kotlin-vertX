package com.rene.VertxKotlin

import com.rene.VertxKotlin.models.DevUser
import io.vertx.core.json.Json
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.core.Promise

class MainVerticle : AbstractVerticle() {


	val devUser1: DevUser = DevUser(1, "Rene", "renejr.arraes286@gmail.com")


	override fun start(startPromise: Promise<Void>) {

		var router: Router = Router.router(vertx)
	
				
		vertx
			.createHttpServer()
			.requestHandler { req ->
				req.response()
					.putHeader("content-type", "application/json")
					.end(Json.encodePrettily(devUser1))
			}
			.listen(8888) { http ->
				if (http.succeeded()) {
					startPromise.complete()
					println("HTTP server started on port 8888")
				} else {
					startPromise.fail(http.cause());
				}
			}



		router.get("/users").handler { req ->


			req.response().putHeader("content-type", "application/json").end(Json.encodePrettily(devUser1))
			
			

		}
	}
}
