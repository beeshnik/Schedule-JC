package hph.app.schedulejc

import android.app.Application
import hph.app.data.local.db.DatabaseProvider

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.init(this)
    }
}