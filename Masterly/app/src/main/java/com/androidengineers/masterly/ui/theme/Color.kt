package com.androidengineers.masterly.ui.theme

import androidx.compose.ui.graphics.Color

// Light theme colors
val LightBackground = Color(0xFF18181b) // --background: hsl(0, 0%, 100%)
val LightForeground = Color(0x0E0E15FF) // --foreground: hsl(222.2, 84%, 4.9%)

val LightCard = Color(0xFFFFFFFF)
val LightCardForeground = LightForeground

val LightPopover = Color(0xFFFFFFFF)
val LightPopoverForeground = LightForeground

val LightPrimary = Color(0x1D1F30FF) // hsl(222.2, 47.4%, 11.2%)
val LightPrimaryForeground = Color(0xF1F5F9FF) // hsl(210, 40%, 98%)

val LightSecondary = Color(0xF1F5F9FF) // hsl(210, 40%, 96.1%)
val LightSecondaryForeground = LightPrimary

val LightMuted = Color(0xF1F5F9FF) // hsl(210, 40%, 96.1%)
val LightMutedForeground = Color(0x74869CFF) // hsl(215.4, 16.3%, 46.9%)

val LightAccent = LightMuted
val LightAccentForeground = LightPrimary

val LightDestructive = Color(0xF87171FF) // hsl(0, 84.2%, 60.2%)
val LightDestructiveForeground = LightPrimaryForeground

val LightBorder = Color(0xE5E7EBFF) // hsl(214.3, 31.8%, 91.4%)
val LightInput = LightBorder
val LightRing = LightForeground

// Dark theme colors
val DarkBackground = Color(0xFF18181b) // hsl(240, 6%, 10%)
val DarkForeground = Color(0xF1F5F9FF) // hsl(210, 40%, 98%)

val DarkCard = Color(0x1E1E1EFF) // hsl(240, 6%, 12%)
val DarkCardForeground = DarkForeground

val DarkPopover = DarkCard
val DarkPopoverForeground = DarkForeground

val DarkPrimary = Color(0x7C3AEDFF) // hsl(260, 100%, 65%)
val DarkPrimaryForeground = DarkBackground

val DarkSecondary = Color(0x2A2A2AFF) // hsl(240, 6%, 16%)
val DarkSecondaryForeground = DarkForeground

val DarkMuted = DarkSecondary
val DarkMutedForeground = Color(0xA0AEC0FF) // hsl(215, 20.2%, 65.1%)

val DarkAccent = DarkSecondary
val DarkAccentForeground = DarkForeground

val DarkDestructive = Color(0x991B1BFF) // hsl(0, 62.8%, 30.6%)
val DarkDestructiveForeground = DarkForeground

val DarkBorder = DarkSecondary
val DarkInput = DarkSecondary
val DarkRing = DarkPrimary

// Brand Colors
val MasterlyIndigo = Color(0x7C3AEDFF) // hsl(260, 100%, 65%)
val MasterlyCyan = Color(0x00C4C4FF)    // hsl(190, 100%, 65%)
val MasterlyDark = DarkBackground

val TrackColor = Color(0xFF2A2A30)        // progress track
val ProgressFill = Color(0xFF7C3AED)      // purple fill
val CardBackground = Color(0xFF1A1A1F)    // matches card bg
val MutedGray = Color(0xFFA0AEC0)         // subtitle color

val PremiumFeatureCardBackground = Color(0xFF1F1F1F).copy(alpha = 0.5f) // bg-card/50
val PremiumFeatureBorder = Color(0xFF2A2A2A).copy(alpha = 0.5f)           // border-border/50
val PremiumFeatureGradientStart = Color(0xFF6366F1) // Indigo
val PremiumFeatureGradientMiddle = Color(0xFF8B5CF6) // Violet
val PremiumFeatureGradientEnd = Color(0xFF06B6D4) // Cyan
val PremiumFeatureSubtitle = Color(0xFF9CA3AF) // text-gray-400

val Purple500 = Color(0xFF073042)
val Teal200 = Color(0xFF3ddc84)
val Indicator = Color(0xFF4285f4)