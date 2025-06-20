package com.sanaa.tudee_assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sanaa.tudee_assistant.domain.ThemeManager
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.route.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.TasksScreen
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeManager: ThemeManager= koinInject()
            val isDark by themeManager.isDarkTheme.collectAsState(initial = false)

            TudeeTheme (isDark){
//                TudeeApp()
                TasksScreen(screenRoute = TasksScreenRoute(TaskUiStatus.IN_PROGRESS))
            }
        }
    }
}
