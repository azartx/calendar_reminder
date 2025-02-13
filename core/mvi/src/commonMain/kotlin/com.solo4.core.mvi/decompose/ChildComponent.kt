package com.solo4.core.mvi.decompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ChildComponent {

    @Composable
    fun Content(modifier: Modifier)
}