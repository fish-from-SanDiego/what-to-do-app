package com.fishfromsandiego.whattodo.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

interface BottomNavigationItem {
    val screen: WhatToDoAppScreen
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
}

@Composable
fun WhatToDoAppBottomBar(
    navItems: Array<BottomNavigationItem>,
    currentScreen: WhatToDoAppScreen,
    onClick: (WhatToDoAppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        navItems.forEach {

            NavigationBarItem(
                selected = it.screen == currentScreen,
                onClick = { onClick(it.screen) },
                icon = {
                    Icon(
                        imageVector = if (it.screen == currentScreen) it.selectedIcon else it.unselectedIcon,
                        contentDescription = it.screen.title,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 4.dp)
                    )
                },
            )
        }
    }
}