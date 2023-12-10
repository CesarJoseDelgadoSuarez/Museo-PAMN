package com.pamn.museo.ui.expoelement

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pamn.museo.model.ExpoElement
import kotlinx.coroutines.coroutineScope

@Composable
fun ExpoElementScreen(
    viewModel: ExpoElementViewModel = hiltViewModel(),
    id: String
) {
    val expoElement: ExpoElement? by viewModel.expoElement.observeAsState()

    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = true)

    LaunchedEffect(key1 = true) {
        coroutineScope {
            Log.d("ExpoElementScreen", "id: .$id.")
            viewModel.setExpoElement(id)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (isLoading){
            CircularProgressIndicator()
        }else if (expoElement == null){
            Text(text = "No se pudo cargar la información")
        }
        else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                tituloExpo(expoElement!!.titulo)
                Spacer(modifier = Modifier.padding(16.dp))
                imageExpo(expoElement!!.imagen)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = expoElement!!.artista,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = expoElement!!.añoCreacion,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = expoElement!!.descripcion,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }


    }
}
@Composable
fun imageExpo(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Imagen del elemento en exposición",
    )

}

@Composable
fun tituloExpo(titulo: String) {
    Text(
        text = titulo,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium
    )
}
