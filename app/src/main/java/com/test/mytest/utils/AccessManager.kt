package com.test.mytest.utils


import com.test.mytest.model.User
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter

class AccessManager(private val user: User) {

    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    fun isInCoolingPeriod(): Boolean {
        val now = Instant.now()
        val coolingEnd = Instant.from(formatter.parse(user.coolingEndTime))
        return now.isBefore(coolingEnd)
    }

    fun getCoolingRemaining(): String {
        val now = Instant.now()
        val coolingEnd = Instant.from(formatter.parse(user.coolingEndTime))
        val remaining = Duration.between(now, coolingEnd).seconds.coerceAtLeast(0)

        val days = remaining / (24 * 3600)
        val hours = (remaining % (24 * 3600)) / 3600
        val minutes = (remaining % 3600) / 60
        val seconds = remaining % 60

        return when {
            days > 0 -> "Cooling ends in $days days $hours hrs %02d:%02d".format(minutes, seconds)
            hours > 0 -> "Cooling ends in $hours hrs %02d:%02d".format(minutes, seconds)
            else -> "Cooling ends in %02d:%02d".format(minutes, seconds)
        }
    }


    fun hasAccess(moduleId: String): Boolean {
        return user.accessibleModules.contains(moduleId)
    }
}
