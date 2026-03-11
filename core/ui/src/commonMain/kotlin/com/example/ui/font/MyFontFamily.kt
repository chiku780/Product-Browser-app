package com.appynitty.ui.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import productbrowserapp.core.ui.generated.resources.Res
import productbrowserapp.core.ui.generated.resources.bebasneuebold
import productbrowserapp.core.ui.generated.resources.georgia
import productbrowserapp.core.ui.generated.resources.georgiab
import productbrowserapp.core.ui.generated.resources.nunito_sans
import productbrowserapp.core.ui.generated.resources.nunito_sans_black


@OptIn(ExperimentalResourceApi::class)
@Composable
fun appFont() = FontFamily(
    Font(Res.font.nunito_sans_black, FontWeight.ExtraBold),
    Font(Res.font.nunito_sans, FontWeight.Bold),
    Font(Res.font.bebasneuebold, FontWeight.Black),
    Font(Res.font.georgiab, FontWeight.SemiBold),
    Font(Res.font.nunito_sans, FontWeight.Normal),
    Font(Res.font.nunito_sans, FontWeight.Medium),
    Font(Res.font.georgia, FontWeight.Light),
    Font(Res.font.georgia, FontWeight.Thin)
)