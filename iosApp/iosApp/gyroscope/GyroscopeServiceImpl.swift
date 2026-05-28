import ComposeApp
import Foundation
import CoreMotion

class GyroscopeServiceImpl : GyroScopeService {
    private let motionManager = CMMotionManager()
    
    func start() {
        if motionManager.isGyroAvailable {

                   motionManager.gyroUpdateInterval = 0.1

                   motionManager.startGyroUpdates()
               }
      }
    
    func stop() {
        motionManager.stopGyroUpdates()
      }
    
    func observeGyro(result: @escaping (KotlinFloat, KotlinFloat, KotlinFloat) -> Void) {
        guard motionManager.isGyroAvailable else {
                  return
              }

              motionManager.gyroUpdateInterval = 0.1

              motionManager.startGyroUpdates(
                  to: OperationQueue.main
              ) { data, error in

                  guard let data = data else {
                      return
                  }

                  let x = Float(data.rotationRate.x)
                  let y = Float(data.rotationRate.y)
                  let z = Float(data.rotationRate.z)

                  result(
                      KotlinFloat(value: x),
                      KotlinFloat(value: y),
                      KotlinFloat(value: z)
                  )
              }
      }
    
}
