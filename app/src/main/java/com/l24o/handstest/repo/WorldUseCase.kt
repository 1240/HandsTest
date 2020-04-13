package com.l24o.handstest.repo

import com.l24o.handstest.model.World
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

// must be Singleton or live some in scope
// @Singleton class ...
object WorldUseCase {

    private val cells = mutableListOf<World>()

    fun getWorld(): Single<List<World>> {
        return Single.fromCallable { cells.toList() }
            .subscribeOn(Schedulers.io())
    }

    fun createLife(isAlive: Boolean): Completable {
        return Completable.fromCallable { cells.add(World.Cell(isAlive = isAlive)) }
            .andThen(transform())
            .subscribeOn(Schedulers.io())
    }

    private fun getState(): WorldState {
        return when {
            cells.size >= 3
                    && cells.subList(cells.size - MAGIC_NUMBER, cells.size)
                .all { it is World.Cell && it.isAlive } -> WorldState.REPLACE_TO_RESULT
            cells.size >= 4
                    && cells.last() == World.Result
                    && cells.subList(cells.size - MAGIC_NUMBER.dec(), cells.size.dec())
                .all { it is World.Cell && !it.isAlive } -> WorldState.REMOVE_RESULT
            cells.size >= 4
                    && cells[(cells.size - MAGIC_NUMBER).dec()] == World.Result
                    && cells.subList(cells.size - MAGIC_NUMBER, cells.size)
                .all { it is World.Cell && !it.isAlive } -> WorldState.REMOVE_RESULT
            else -> WorldState.NOTHING
        }
    }

    private fun transform(): Completable {
        return Completable.fromCallable {
            var hasChanges = true
            while (hasChanges) {
                when (getState()) {
                    WorldState.REPLACE_TO_RESULT -> {
                        val newCells = cells.dropLast(3)
                        cells.clear()
                        cells.addAll(newCells)
                        cells.add(World.Result)
                        hasChanges = true
                    }
                    WorldState.REMOVE_RESULT -> {
                        val newCells = cells.dropLast(4)
                        cells.clear()
                        cells.addAll(newCells)
                        hasChanges = true
                    }
                    WorldState.NOTHING -> {
                        hasChanges = false
                    }
                }
            }
        }
            .subscribeOn(Schedulers.computation())
    }

    private enum class WorldState {
        NOTHING,
        REPLACE_TO_RESULT,
        REMOVE_RESULT
    }

    private const val MAGIC_NUMBER = 3
}