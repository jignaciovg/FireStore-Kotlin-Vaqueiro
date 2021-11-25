package com.vaqueiro.appfirebase_intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TAG = "FireBaseDebug"

        val db = FirebaseFirestore.getInstance()
        val dbCollection = "books"

        //GET ALL
        db.collection(dbCollection)
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    Log.d(TAG,"------------------------")
                    Log.d(TAG,"METHOD: GET ALL")
                    Log.d(TAG,"NAME:${document.getString("name")}")
                    Log.d(TAG,"DESCRIPTION:${document.getString("description")}")
                }
            }
            .addOnFailureListener{

            }

        //GET BY ID
        var busquedaID = "001"
        db.collection(dbCollection)
            .document(busquedaID)
            .get()
            .addOnSuccessListener { document ->
                document.let {
                    Log.d(TAG,"------------------------")
                    Log.d(TAG,"METHOD: GET BY ID")
                    Log.d(TAG,"ID:${busquedaID}")
                    Log.d(TAG,"RESULT NAME:${document.getString("name")}")
                }
            }
            .addOnFailureListener{

            }

        //GET LIKE
        //NO FUNCIONO NO ENCUENTRA NADA, NO SE MUESTRA EN EL DEBUG
        var busquedaName = "Naruto"
        db.collection(dbCollection)
            .whereEqualTo("name",busquedaName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    Log.d(TAG,"------------------------")
                    Log.d(TAG,"METHOD: GET LIKE")
                    Log.d(TAG,"SEARCH:${busquedaName}")
                    Log.d(TAG,"NAME:${document.getString("name")}")
                    Log.d(TAG,"DESCRIPTION:${document.getString("description")}")
                }
            }
            .addOnFailureListener{

            }

        //INSERT
        /*db.collection(dbCollection)
            .document()
            .set(Book("LEARN KOTLIN","VOLUMEN 2"))
            .addOnSuccessListener {
                Log.d(TAG,"------------------------")
                Log.d(TAG,"METHOD: POST")
                Log.d(TAG,"OK:200")
                Log.d(TAG,"LIBRO AGREGADO CORRECTAMENTE")
            }
            .addOnFailureListener {
                exception ->
                Log.d(TAG,"------------------------")
                Log.d(TAG,"METHOD: POST")
                Log.d(TAG,"ERROR:401")
                Log.d(TAG,"EL LIBRO NO FUE AGREGADO")
            }
        */

        //VER CAMBIOS EN TIEMPO REAL DE UN REGISTRO ESPECIFICO
        db.collection(dbCollection)
            .document(busquedaID)
            .addSnapshotListener{
                value, error->
                value.let { document ->
                    document.let{
                        Log.d(TAG,"------------------------")
                        Log.d(TAG,"METHOD: REALTIME DOCUMENT")
                        Log.d(TAG,"ID:${busquedaID}")
                        Log.d(TAG,"NAME:${document?.getString("name")}")
                        Log.d(TAG,"DESCRIPTION:${document?.getString("description")}")
                    }
                }
            }

        //VER CAMBIOS EN TIEMPO REAL DE TODOS LOS REGISTROS
        db.collection(dbCollection)
            .addSnapshotListener{value, error ->
                value.let {
                    for(document in value?.documents!!){
                        Log.d(TAG,"------------------------")
                        Log.d(TAG,"METHOD: REALTIME COLLECTION")
                        Log.d(TAG,"NAME:${document?.getString("name")}")
                        Log.d(TAG,"DESCRIPTION:${document?.getString("description")}")
                    }
                }
            }
    }

    data class Book(var name:String, var description:String)
}