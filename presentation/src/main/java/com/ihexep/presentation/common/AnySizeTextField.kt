package com.ihexep.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ihexep.presentation.utils.clearFocusOnKeyboardDismiss

@Composable
fun AnySizeTextField(
    modifier: Modifier = Modifier,
    value: String,
    valueStyle: TextStyle = TextStyle.Default,
    hint: String = "",
    hintStyle: TextStyle = TextStyle.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        singleLine = true,
        onValueChange = { onValueChange(it) },
        modifier = modifier.clearFocusOnKeyboardDismiss(),
        textStyle = valueStyle,
        cursorBrush = SolidColor(LocalContentColor.current),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1.0f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = hintStyle
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    )
}