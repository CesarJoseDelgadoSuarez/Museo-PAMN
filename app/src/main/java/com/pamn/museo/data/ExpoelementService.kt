package com.pamn.museo.data

import com.pamn.museo.model.ExpoElement
import javax.inject.Inject

class ExpoelementService @Inject constructor(
    private val firestoreService: FirestoreService<ExpoElement>,
) {

    suspend fun getElement(id: String) = firestoreService.getData(collection, id)

    companion object {
        private const val collection = "elementosExposicion"
    }
}