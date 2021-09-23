package com.rene.VertxKotlin.models

class DevUser {

	var id: Int = 0
	var name: String = ""
	var email: String = ""


	constructor() {}
	constructor(name: String, email: String) {

		this.name = name
		this.email = email

	}
}

