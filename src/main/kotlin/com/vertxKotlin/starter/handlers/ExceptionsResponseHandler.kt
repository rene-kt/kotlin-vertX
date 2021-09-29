package com.vertxKotlin.starter.handlers

import com.vertxKotlin.starter.exceptions.ObjectNotFoundException
import com.vertxKotlin.starter.exceptions.UserNotLoggedException
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

class ExceptionsResponseHandler {

  fun userNotLoggedExceptionResponse(req: RoutingContext, e: UserNotLoggedException){
    req.response().setStatusCode(403).putHeader("content-type", "application/json")
      .end(Json.encodePrettily(ResponseHandler(403, e.message, null)))
  }

  fun objectNotFoundExceptionResponse(req: RoutingContext, e: ObjectNotFoundException){
    req.response().putHeader("content-type", "application/json").setStatusCode(404)
      .end(Json.encodePrettily(ResponseHandler(404, e.message, null)))
  }

  fun nullRequestBodyResponse(req: RoutingContext, e: NullPointerException){
    req.response().putHeader("content-type", "application/json").setStatusCode(400)
      .end(Json.encodePrettily(ResponseHandler(400, "The JSON body is missing some required attributes", null)))
  }

}
