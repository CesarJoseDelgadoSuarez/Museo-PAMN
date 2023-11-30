package com.pamn.museo.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pamn.museo.model.FirestoreResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService<T> @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataClass: Class<T>
) {

    suspend fun insertDataWithDocumentID(collectionName: String, documentId: String, data: Any):FirestoreResult<String> {
        try {
            val response = firestore.collection(collectionName).document(documentId).set(data).await()
            Log.d(TAG, "Datos insertados correctamente: $response.")
            return FirestoreResult.Success("")
        } catch (e: Exception) {
            Log.e(TAG, "Error al insertar datos en la colección $collectionName: ${e.message}", e)
            throw e
        }
    }

    suspend fun insertData(collectionName: String, data: Any) {
        try {
            firestore.collection(collectionName).add(data).await()
            Log.d(TAG, "Datos insertados correctamente en la colección $collectionName.")
        } catch (e: Exception) {
            Log.e(TAG, "Error al insertar datos en la colección $collectionName: ${e.message}", e)
            throw e
        }
    }

    suspend fun getData(collectionName: String, documentId: String): FirestoreResult<T> {
        return try {
            val document = firestore.collection(collectionName).document(documentId).get().await()

            if (document.exists()) {
                val data = document.toObject(dataClass)
                FirestoreResult.Success(data!!)
            } else {
                FirestoreResult.Error("Documento no encontrado en la colección $collectionName")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener datos de la colección $collectionName: ${e.message}", e)
            FirestoreResult.Error(e.message.toString())
        }
    }

    companion object {
        private const val TAG = "FirestoreService"
    }
}
