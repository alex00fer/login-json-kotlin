package com.example.tarealoginalejandrofernandez.controller

import com.example.tarealoginalejandrofernandez.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class UserManager {

    lateinit  var activeUser : UserModel
    var users : HashMap<String, UserModel> = HashMap();
    lateinit var usersFile : File
    var gson = Gson()

    fun load(path: File) {
        usersFile = File(path, "users.json")
        usersFile.createNewFile()
        val json = usersFile.readText(Charsets.UTF_8)
        val type = object : TypeToken<HashMap<String, UserModel>?>() {}.type
        users = gson.fromJson(json, type)
    }
    fun save() {
        usersFile.writeText(gson.toJson(users))
    }
    fun count() : Int {
        return users.size
    }
    fun updateUser(user: UserModel) { // the username doesn't change
        activeUser = user
        users[user.username] = user
    }
    fun registerUser(user: UserModel) {
        users[user.username] = user
    }
    fun login(username: String, password: String) : Boolean {
        if (users.containsKey(username)) {
            if (users[username]!!.password == password) {
                activeUser = users[username]!!
                return true;
            }
        }
        return false
    }

}