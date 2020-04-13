package com.l24o.handstest.model

sealed class World {
    data class Cell(
        val isAlive: Boolean
    ) : World()

    object Result : World()
}