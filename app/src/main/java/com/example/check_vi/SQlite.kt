package com.example.check_vi

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE usuarios (codigo INTEGER PRIMARY KEY, usuario TEXT, contraseña TEXT, edad INTEGER, sexo Text)")

        db?.execSQL("CREATE TABLE historial (codigo INTEGER PRIMARY KEY, usuario_codigo INTEGER, presion TEXT, temperatura TEXT, fecha TEXT, FOREIGN KEY(usuario_codigo) REFERENCES usuarios(codigo))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS historial")
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }
}
