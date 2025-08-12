package io.github.com.ice909.android.todo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val shape: Shape = RoundedCornerShape(4.dp)

    // 颜色定义
    val borderColor by animateColorAsState(
        targetValue = when {
            checked -> MaterialTheme.colorScheme.primary
            isError -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        }, label = "border"
    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            checked -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        }, label = "bg"
    )

    // focus/hover 态光环
    val ringColor = when {
        isError -> MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
        isFocused || isPressed -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else -> Color.Transparent
    }

    val checkmarkColor = MaterialTheme.colorScheme.onPrimary

    val boxModifier = modifier
        .size(20.dp)
        .background(color = backgroundColor, shape = shape)
        .border(width = 1.dp, color = borderColor, shape = shape)
        // focus ring 用外阴影模拟
        .shadow(
            elevation = if (isFocused || isPressed) 6.dp else 0.dp,
            shape = shape,
            ambientColor = ringColor,
            spotColor = ringColor,
            clip = false
        )
        .alpha(if (enabled) 1f else 0.5f)
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null, // 不要原生涟漪
            role = Role.Checkbox,
            onClick = { onCheckedChange() }
        )
        .focusable(enabled, interactionSource = interactionSource) // 支持键盘 focus
    // 可加更多无障碍属性

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = checkmarkColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}