import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init(){
        GyroScopeServiceKt.gyroScopeService = GyroscopeServiceImpl()
        AccelerometerServiceKt.accelerometerService = AccelerometerServiceImpl()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
