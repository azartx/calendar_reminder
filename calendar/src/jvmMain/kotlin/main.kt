import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.solo4.calendarreminder.calendar.di.applyApplicationModules
import com.solo4.calendarreminder.calendar.presentation.root.RootComponent
import com.solo4.core.kmputils.MultiplatformContext
import org.koin.core.context.startKoin
import utils.runOnUiThread

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    startKoin {
        applyApplicationModules(createMultiplatformContext())
    }

    val lifecycle = LifecycleRegistry()
    val rootComponent = runOnUiThread {
        RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
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
            resizable = true,
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
