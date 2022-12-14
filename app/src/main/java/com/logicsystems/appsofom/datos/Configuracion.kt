package com.logicsystems.appsofom.datos

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.logicsystems.appsofom.datos.bd.Configuracion
import kotlin.properties.Delegates

open class ClsConfiguracion : ClsGenerica() {
    lateinit var c: Cursor
    var Id by Delegates.notNull<Int>()
    var cEntorno: String
    var cEmpresa: String
    var nMinUpdateGPS by Delegates.notNull<Int>()
    var nMinUpdateInfo by Delegates.notNull<Int>()
    var cLoginUser: String
    var cLoginPass: String
    var cOperador: String
    var cInfoTicket: String
    init {
        this.Limpiar()
        this.Id = 0
        this.cEntorno = ""
        this.cEmpresa = ""
        this.nMinUpdateGPS = 0
        this.nMinUpdateInfo = 0
        this.cLoginUser = ""
        this.cLoginPass = ""
        this.cOperador = ""
        this.cInfoTicket = ""
    }

    fun validacion(ODB: SQLiteDatabase): Boolean {
        var BlnReturn = false
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    override fun Guardar(OCom: SQLiteDatabase): Boolean{
        var BlnReturn: Boolean = false
        try {
            if (this.Id == 0) {
                val values = ContentValues().apply {
                    put(Configuracion.cEntorno, cEntorno)
                    put(Configuracion.cEmpresa, cEmpresa)
                    put(Configuracion.nMinUpdateGPS, nMinUpdateGPS)
                    put(Configuracion.nMinUpdateInfo, nMinUpdateInfo)
                    put(Configuracion.cLoginUser, cLoginUser)
                    put(Configuracion.cLoginPass, cLoginPass)
                    put(Configuracion.cOperador, cOperador)
                    put(Configuracion.cInfoTicket, cInfoTicket)
                }
                val LastId: Long = OCom.insert(Configuracion.TABLE_NAME, null, values)
                this.Id = LastId.toInt()
            } else {
                OCom.execSQL(
                    "Update Configuracion Set " +
                            "cEntorno= '" + this.cEntorno + "'," +
                            "cEmpresa='" + this.cEmpresa + "'," +
                            "nMinUpdateGPS= " + this.nMinUpdateGPS + "," +
                            "nMinUpdateInfo= " + this.nMinUpdateInfo + "," +
                            "cLoginUser= '" + this.cLoginUser + "'," +
                            "cLoginPass= '" + this.cLoginPass + "'," +
                            "cOperador= '" + this.cOperador + "'," +
                            "cInfoTicket= '" + this.cInfoTicket + "' " +
                            "Where Id=" + this.Id.toString()
                )
            }
        } catch (ex: Exception) {
            this.StrProblema = "Problema: " + ex.message
        }
        if (this.StrProblema == "") {
            BlnReturn = true
        }
        return BlnReturn
    }

    override fun LoadAll (OCom: SQLiteDatabase): Boolean{
        val columnas = arrayOf(Configuracion.Id, Configuracion.cEntorno, Configuracion.cEmpresa, Configuracion.nMinUpdateGPS, Configuracion.nMinUpdateInfo, Configuracion.cLoginUser, Configuracion.cLoginPass, Configuracion.cOperador, Configuracion.cInfoTicket)
        try {
            this.c = OCom.query(Configuracion.TABLE_NAME, columnas, null, null, null, null, null)
        }
        catch (ex: SQLiteException){
            this.StrProblema = "ZX: ${ex.message}"
        }
        return (this.StrProblema == "")
    }
}