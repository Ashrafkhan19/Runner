package com.example.run.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.example.core.database.dao.RunPendingSyncDao
import com.example.core.database.entity.DeletedRunSyncEntity
import com.example.core.database.entity.RunPendingSyncEntity
import com.example.core.database.mappers.toRunEntity
import com.example.core.domain.run.Run
import com.example.core.domain.run.SyncRunScheduler
import com.example.core.domain.session.SessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncRunWorkerScheduler(
    context: Context,
    private val pendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope

) : SyncRunScheduler {

    private val workerManager = WorkManager.getInstance(context)


    override suspend fun scheduleSync(type: SyncRunScheduler.SyncType) {
        when (type) {
            is SyncRunScheduler.SyncType.CreateRun -> scheduleCreateRunWorker(
                type.run,
                type.mapPictureBytes
            )

            is SyncRunScheduler.SyncType.DeleteRun -> scheduleDeleteRunWorker(type.runId)
            is SyncRunScheduler.SyncType.FetchRuns -> scheduleFetchRunWorker(type.interval)
        }
    }


    private suspend fun scheduleDeleteRunWorker(runId: String) {
        val userId = sessionStorage.get()?.userId ?: return
        val deletedRunSyncEntity = DeletedRunSyncEntity(runId, userId)
        pendingSyncDao.upsertDeletedRunSyncEntity(deletedRunSyncEntity)

        val workRequest =
            OneTimeWorkRequestBuilder<DeleteRunWorker>().addTag("delete_work").setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = MILLISECONDS
            ).setInputData(
                Data.Builder().putString(CreateRunWorker.RUN_ID, deletedRunSyncEntity.runId)
                    .build()
            ).build()

        applicationScope.launch {
            workerManager.enqueue(workRequest).await()
        }.join()

    }

    private suspend fun scheduleCreateRunWorker(run: Run, mapPictureByte: ByteArray) {
        val userId = sessionStorage.get()?.userId ?: return

        val pendingRunEntity = RunPendingSyncEntity(
            run = run.toRunEntity(), mapPictureBytes = mapPictureByte, userId = userId
        )
        pendingSyncDao.upsertRunPendingSyncEntity(pendingRunEntity)

        val workRequest =
            OneTimeWorkRequestBuilder<CreateRunWorker>().addTag("create_work").setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = MILLISECONDS
            ).setInputData(
                Data.Builder().putString(CreateRunWorker.RUN_ID, pendingRunEntity.runId).build()
            ).build()

        applicationScope.launch {
            workerManager.enqueue(workRequest).await()
        }.join()

    }

    private suspend fun scheduleFetchRunWorker(interval: Duration) {
//        val isSyncedSchedule = workerManager.getWorkInfosByTag("sync_work").await().isNotEmpty()
//
//        if (isSyncedSchedule) {
//            return
//        }

        val workRequest = PeriodicWorkRequestBuilder<FetchRunWorker>(
            repeatInterval = interval.toJavaDuration()
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL, backoffDelay = 2L, timeUnit = SECONDS
            ).setInitialDelay(
                duration = 30L, timeUnit = SECONDS
            ).addTag("sync_work").build()

        workerManager.enqueueUniquePeriodicWork(
            "sync_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        ).await()

    }

    override suspend fun cancelAllSyncs() {
        workerManager.cancelAllWork().await()
    }
}