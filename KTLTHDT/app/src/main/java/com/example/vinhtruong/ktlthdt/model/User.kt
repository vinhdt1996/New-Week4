package com.example.vinhtruong.ktlthdt.model

data class User(val id: String = "",
                val name: String = "",
                val email: String = "",
                val password: String = "",
                val image: String = "",
                var userType: String = "",
                val token: String = "")