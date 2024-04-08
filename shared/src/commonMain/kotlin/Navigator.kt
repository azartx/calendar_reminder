package com.solo4.calendarreminder.shared

import androidx.compose.runtime.compositionLocalOf
import com.bumble.appyx.navigation.plugin.NodeReadyObserver
import com.solo4.calendarreminder.shared.nodes.root.RootNode
import kotlinx.coroutines.CoroutineScope

val LocalNavigator = compositionLocalOf { Navigator() }

class Navigator : NodeReadyObserver<RootNode> {

    private lateinit var rootNode: RootNode
    private lateinit var lifecycleScope: CoroutineScope

    override fun init(node: RootNode) {
        rootNode = node
        lifecycleScope = node.lifecycleScope
    }

    fun back() {
        rootNode.pop()
    }

    // example
    /*fun goToCake(cake: Cake) {
        lifecycleScope.launch {
            val main = rootNode.waitForMainAttached()
            main
                .onCakes()
                .goToCake(cake)
                .enterHeroMode(delay = 500)
            main
                .goToCakes()
        }
    }*/
}