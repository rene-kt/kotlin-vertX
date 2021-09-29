package com.vertxKotlin.starter


import com.vertxKotlin.starter.models.DevUser
import com.vertxKotlin.starter.models.ManagerUser
import com.vertxKotlin.starter.verticles.ManagerVerticle
import com.vertxKotlin.starter.verticles.DevVerticle
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler


class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val router: Router = Router.router(vertx)
    router.route().handler(BodyHandler.create())

    var devLogged = DevUser()
    var managerLogged = ManagerUser()

    val managerVerticle = ManagerVerticle()
    val devVerticle = DevVerticle()

    // MANAGER ENDPOINTS

    // GET
    router.get("/manageruser").handler { managerVerticle.returnManager(managerLogged, it) }
    // POST
    router.post("/manageruser/devuser").handler { managerVerticle.createDevUser(managerLogged, it) }
    router.post("/manageruser").handler { managerLogged = managerVerticle.createManager(it) }
    router.post("/manageruser/project").handler { managerVerticle.createProject(managerLogged, it) }
    // PUT
    router.put("/manageruser/credits").handler { managerVerticle.changeCredits(managerLogged, it) }
    // DELETE
    router.delete("/manageruser/project/:projectId").handler { managerVerticle.deleteProject(managerLogged, it) }
    router.delete("/manageruser/devuser/:devId").handler { managerVerticle.deleteDevUser(managerLogged, it) }


    // DEV ENDPOINTS

    // GET
    router.get("/devuser").handler { devVerticle.returnDevUser(devLogged, it) }
    // POST
    router.post("/devuser").handler { devLogged = devVerticle.createDevUser(it) }
    router.post("/devuser/project").handler { devVerticle.createProject(devLogged, it) }
    // DELETE
    router.delete("/devuser/project/:projectId").handler { devVerticle.deleteProject(devLogged, it) }

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
