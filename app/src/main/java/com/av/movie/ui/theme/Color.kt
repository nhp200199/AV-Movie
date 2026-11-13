package com.av.movie.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ==========================================
// Brand Primary Gradient (Cyan90 -> Blue90)
// Màu chủ đạo của ứng dụng
// ==========================================
val Cyan90 = Color(0xFF4AB9FF)
val Blue90 = Color(0xFF0084F3)
val BrandGradient = Brush.linearGradient(listOf(Cyan90, Blue90))

val BrandPrimary = Blue90
val BrandSecondary = Cyan90
val BrandLightTint = Color(0xFFE5F4FF)
val BrandShadow = Color(0x400084F3) // 25% opacity

// ==========================================
// Dark Mode Palette (Dựa trên Movix Dark Theme)
// ==========================================
val DarkBackground = Color(0xFF181A20)
val DarkSurface = Color(0xFF1F222A)
val DarkSurfaceSecondary = Color(0xFF35383F)
val DarkDivider = Color(0xFF35383F)
val DarkTextPrimary = Color(0xFFFFFFFF)
val DarkTextSecondary = Color(0xFFE0E0E0)
val DarkTextMuted = Color(0xFF9E9E9E)

// ==========================================
// Light Mode Palette (Dựa trên Movix Light Theme)
// ==========================================
val LightBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFFAFAFA)
val LightSurfaceSecondary = Color(0xFFF5F5F5)
val LightDivider = Color(0xFFEEEEEE)
val LightTextPrimary = Color(0xFF212121)
val LightTextSecondary = Color(0xFF424242)
val LightTextMuted = Color(0xFF616161)

// ==========================================
// Status & Feedback Colors
// ==========================================
val Success = Color(0xFF4CAF50)
val Warning = Color(0xFFFFC107)
val Error = Color(0xFFF75555)

// ==========================================
// Backward Compatibility
// ==========================================
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val White = Color(0xFFFFFFFF)
val Grey10 = Color(0xFF090E17)
val LightGrey30 = Color(0xFF303D4F)
val LightGrey50 = Color(0xFF4C5158)
val LightGrey10 = Color(0xFF5C6169)

val ColorScheme.AppTopBarBackgroundColor
    get() = DarkSurface
