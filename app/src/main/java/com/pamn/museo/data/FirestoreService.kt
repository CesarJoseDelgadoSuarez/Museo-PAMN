package com.pamn.museo.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pamn.museo.model.DocumentResult
import kotlinx.coroutines.tasks.await

class FirestoreService<T> constructor(
    private val firestore: FirebaseFirestore,
    private val collectionName: String,
    private val dataClass: Class<T>
) {

    suspend fun insertDataWithDocumentID(documentId: String, data: Any, collectionName: String) {
        try {
            firestore.collection(collectionName).document(documentId).set(data).await()
            Log.d(TAG, "Datos insertados correctamente.")
        } catch (e: Exception) {
            Log.e(TAG, "Error al insertar datos: ${e.message}", e)
            throw e
        }
    }

    suspend fun insertData(data: Any, collectionName: String) {
        try {
            firestore.collection(collectionName).add(data).await()
            Log.d(TAG, "Datos insertados correctamente.")
        } catch (e: Exception) {
            Log.e(TAG, "Error al insertar datos: ${e.message}", e)
            throw e
        }
    }

    suspend fun getData(documentId: String): DocumentResult<T> {
        return try {
            val document = firestore.collection(collectionName).document(documentId).get().await()

            if (document.exists()) {
                val data = document.toObject(dataClass)
                DocumentResult.Success(data!!)
            } else {
                DocumentResult.Error("Documento no encontrado")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener datos: ${e.message}", e)
            DocumentResult.Error(e.message.toString())
        }
    }

    companion object {
        private const val TAG = "FirestoreService"
    }
}
