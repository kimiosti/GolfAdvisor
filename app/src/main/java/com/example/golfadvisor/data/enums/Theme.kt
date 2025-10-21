package com.example.golfadvisor.data.enums

import com.example.golfadvisor.R

enum class Theme(val screenNameResourceId: Int) {
    Light(R.string.light_theme_screen_name),
    Dark(R.string.dark_theme_screen_name),
    System(R.string.system_theme_screen_name)
}