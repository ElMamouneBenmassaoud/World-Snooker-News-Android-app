package g58112.mobg5.snookernews
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom application class for initializing Firebase and Dagger-Hilt.
 *
 * This class extends [Application] and is annotated with [HiltAndroidApp],
 * which triggers Hilt's code generation and adds an application-level dependency container.
 * This setup is necessary for integrating Firebase services and Dagger-Hilt dependency injection framework.
 */
@HiltAndroidApp
class ApplicationFirebaseAuth:Application()