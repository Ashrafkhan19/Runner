package com.example.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.database.dao.RunPendingSyncDao
import com.example.core.domain.run.RemoteRunDataSource

class DeleteRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val pendingSyncDao: RunPendingSyncDao,
    private val remoteRunDataSource: RemoteRunDataSource
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val id = params.inputData.getString(RUN_ID) ?: return Result.failure()


        return when (val result = remoteRunDataSource.deleteRun(id)) {
            is com.example.core.domain.util.Result.Error -> result.error.toWorkerResult()
            is com.example.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteDeletedRunSyncEntity(id)
                Result.success()
            }
        }


    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }

}