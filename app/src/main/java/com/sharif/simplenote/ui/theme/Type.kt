package com.sharif.simplenote.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sharif.simplenote.R

val interFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold)
)


object AppTypography {
    val textXsRegular = TextStyle(
        fontSize = 10.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 10.sp // Auto (1:1 ratio)
    )

    val textXsMedium = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 10.sp
    )

    val textXsBold = TextStyle(
        fontSize = 10.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 10.sp
    )

    // Text 2XS
    val text2xsRegular = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 12.sp // Auto (1:1 ratio)
    )

    val text2xsMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 12.sp
    )

    val text2xsBold = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 12.sp
    )

    // Text SM
    val textSmRegular = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 19.6.sp // 140% of 14px
    )

    val textSmMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 19.6.sp
    )

    val textSmBold = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 19.6.sp
    )

    // Text Base
    val textBaseRegular = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.4.sp // 140% of 16px
    )

    val textBaseMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 22.4.sp
    )

    val textBaseBold = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.4.sp
    )

    // Text LG
    val textLgRegular = TextStyle(
        fontSize = 20.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp // 140% of 20px
    )

    val textLgMedium = TextStyle(
        fontSize = 20.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 28.sp
    )

    val textLgBold = TextStyle(
        fontSize = 20.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    )

    // Text XI
    val textXiRegular = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.8.sp // 120% of 24px
    )

    val textXiMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 28.8.sp
    )

    val textXiBold = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.8.sp
    )

    // Text 2XI
    val text2xiRegular = TextStyle(
        fontSize = 32.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 38.4.sp // 120% of 32px
    )

    val text2xiMedium = TextStyle(
        fontSize = 32.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 38.4.sp
    )

    val text2xiBold = TextStyle(
        fontSize = 32.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 38.4.sp
    )

    // Text 3XI
    val text3xiRegular = TextStyle(
        fontSize = 40.sp,
        fontFamily = interFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp // 110% of 40px
    )

    val text3xiMedium = TextStyle(
        fontFamily = interFamily,
        fontSize = 40.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 44.sp
    )

    val text3xiBold = TextStyle(
        fontFamily = interFamily,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp
    )
}