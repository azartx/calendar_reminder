import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.solo4.calendarreminder.calendar.di.applyApplicationModules
import com.solo4.calendarreminder.calendar.domain.RestoreRemindersUseCase
import com.solo4.calendarreminder.calendar.presentation.root.RootComponent
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.ic_app
import com.solo4.core.kmputils.MultiplatformContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import utils.runOnUiThread

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    startKoin {
        applyApplicationModules(createMultiplatformContext())
    }

    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        runCatching {
            getKoin().get<RestoreRemindersUseCase>().invoke()
        }
    }

    val lifecycle = LifecycleRegistry()
    val backDispatcher = BackDispatcher()
    val rootComponent = runOnUiThread {
        RootComponent(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                backHandler = backDispatcher,
            ),
        )
    }

    application {
        val windowState = rememberWindowState(
            width = 480.dp,
            height = 860.dp,
        )

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Calendar Reminder",
            icon = painterResource(Res.drawable.ic_app),
            resizable = true,
            onKeyEvent = { event ->
                if (event.key == Key.Escape && event.type == KeyEventType.KeyUp) {
                    backDispatcher.back()
                } else {
                    false
                }
            },
        ) {
            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = windowState,
                windowInfo = LocalWindowInfo.current,
            )
            rootComponent.Content(Modifier)
        }
    }
}

private fun createMultiplatformContext(): MultiplatformContext {
    return object : MultiplatformContext {

        private var context: Any? = null

        override fun getContext(): Any? {
            return context
        }

        override fun setContext(context: Any?) {
            this.context = context
        }

        override fun dispose() {
            context = null
        }
    }
}
