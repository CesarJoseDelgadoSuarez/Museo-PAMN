
package com.pamn.museo.navigation

import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pamn.museo.model.AppScreens
import com.pamn.museo.model.BottomNavigationItem


@Composable
fun BottomNavigation(
    selectedIndex: Int,
    items: List<BottomNavigationItem>,
    onItemSelected: (Int) -> Unit,
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {

                        onItemSelected(index)
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(badge = {
                        if (item.hasNews) {
                            Badge()
                        }
                    }) {
                        Icon(
                            imageVector = if (index == selectedIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon, contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}
