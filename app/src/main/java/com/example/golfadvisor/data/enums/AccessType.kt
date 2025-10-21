package com.example.golfadvisor.data.enums

import com.example.golfadvisor.R

enum class AccessType(
    val screenNameResourceId: Int,
    val scoreMultiplier: Int
) {
    HalfRound(R.string.half_round_access_type_screen_name, 2),
    FullRound(R.string.full_round_access_type_screen_name, 1),
    DrivingRange(R.string.driving_range_access_type_screen_name, 0),
    Restaurant(R.string.restaurant_access_type_screen_name, 0)
}