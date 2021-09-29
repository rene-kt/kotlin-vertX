package com.vertxKotlin.starter.exceptions

class NullRequestBodyException(message: String = "The JSON body is missing some required attributes"): Exception(message)

