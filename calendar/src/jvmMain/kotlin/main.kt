import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.solo4.calendarreminder.calendar.di.applyApplicationModules
import com.solo4.calendarreminder.calendar.presentation.root.RootComponent
import com.solo4.core.kmputils.MultiplatformContext
import org.koin.core.context.startKoin
import utils.runOnUiThread

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
        Window(
            onCloseRequest = ::exitApplication,
            title = "Calendar reminder",
            resizable = false,
        ) {
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

        override fun setContext(context: Any?) {}

        override fun dispose() {
            context = null
        }
    }
}