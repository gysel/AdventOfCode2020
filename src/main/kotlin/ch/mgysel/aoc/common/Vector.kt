package ch.mgysel.aoc.common

import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.roundToLong
import kotlin.math.sin

data class Vector(val x: Long, val y: Long) {

    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)

    /**
     * Calculates the manhattan distance
     */
    fun distanceTo(other: Vector): Long {
        return (x - other.x).absoluteValue + (y - other.y).absoluteValue
    }

    operator fun times(scalar: Long): Vector {
        return Vector(x * scalar, y * scalar)
    }

    fun rotateLeft(degrees: Long): Vector {
        val phi = degrees.toDouble() * kotlin.math.PI / 180
        val newX = x * cos(phi) - y * sin(phi)
        val newY = x * sin(phi) + y * cos(phi)
        return Vector(newX.roundToLong(), newY.roundToLong())
    }

    fun rotateRight(degrees: Long): Vector {
        return rotateLeft(-degrees)
    }

}