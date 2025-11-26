package com.androidengineers.masterly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.R
import com.androidengineers.masterly.ui.theme.MasterlyTheme

@Composable
fun HomeFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF8B5CF6),
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        elevation = FloatingActionButtonDefaults.elevation(6.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = "Add",
            modifier = Modifier.size(20.dp)
                .clearAndSetSemantics(
                  properties = {
                        this.contentDescription = "Add Button"
                    }
                )
                .semantics(
                    // treat this whole group of UI elements as ONE accessibility node
                mergeDescendants = true, properties = {
                    this.contentDescription = "Add Button"
                }
            )
        )
    }
}

