package com.example.f1widget.worker

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.f1widget.data.F1Database
import com.example.f1widget.glance.F1Widget
import com.example.f1widget.repository.F1Repository
import com.example.f1widget.network.RetrofitInstance

class F1WidgetWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // 1. SETUP (Manual Dependency Injection)
            // Since we are in a background worker, we build the tools from scratch
            val database = F1Database.getDatabase(context)
            val repository = F1Repository(database.f1Dao())

            // 2. FETCH DATA (This saves it to DB automatically via Repository logic)
            repository.getDrivers()
            // Optional: repository.getConstructors() if you want to sync teams too

            // 3. UPDATE WIDGET
            // This forces the F1Widget to read the new DB data and redraw
            F1Widget().updateAll(context)

            // Success!
            Result.success()
        } catch (e: Exception) {
            // If it fails (no internet), we return 'retry' or 'failure'
            e.printStackTrace()
            Result.retry()
        }
    }
}