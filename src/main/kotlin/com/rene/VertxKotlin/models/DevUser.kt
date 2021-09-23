package com.rene.VertxKotlin.models

import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.Table
import javax.persistence.Entity


@Entity
@Table(name = "dev_users")
class DevUser{

	@Id
	@GeneratedValue(GenerationType.IDENTITY)
	val id: Int = 0
	var name: String = ""
	var email: String = ""


}

