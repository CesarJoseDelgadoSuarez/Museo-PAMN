package com.pamn.museo.ui.menuUser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pamn.museo.R


@Composable
fun MenuUserScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .padding(bottom = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
        ) {
            val image = painterResource(R.drawable.user_image)
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Nombre de usuario", color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(R.drawable.pencil_icon),
                contentDescription = "Editar",
                tint = Color.White,
                modifier = Modifier.size(16.dp) // Agregado para cambiar el tamaño del ícono
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 500.dp, bottom = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CustomButton(text = "Botón 1", modifier = Modifier)
        CustomButton(text = "Botón 2", modifier = Modifier)
        CustomButton(text = "Botón 3", modifier = Modifier)
        CustomButton(text = "Botón 4", modifier = Modifier)
    }
}


@Composable
fun CustomButton(text: String, modifier: Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp),
        colors = ButtonDefaults.buttonColors(Color.Blue) // Agregado para cambiar el color del botón
    ) {
        Text(text = text)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuUser(){
    MenuUserScreen()
}