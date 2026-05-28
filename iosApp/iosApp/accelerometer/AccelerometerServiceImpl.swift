import Foundation
import CoreMotion
import ComposeApp

class AccelerometerServiceImpl : AccelerometerService {

    private let motionManager = CMMotionManager()


    func start() {
        print(motionManager.isDeviceMotionAvailable)
        motionManager.deviceMotionUpdateInterval = 1.0 / 60.0
    }

    func stop() {

        motionManager.stopDeviceMotionUpdates()
    }

    func observeAccelerometer(
        result: @escaping (KotlinFloat, KotlinFloat) -> Void
    ) {

        guard motionManager.isDeviceMotionAvailable else {
            return
        }

        motionManager.startDeviceMotionUpdates(
            to: OperationQueue.main
        ) { motion, error in
            
            guard let motion = motion else {
                return
            }

            let gravityX =
                Float(motion.gravity.x)

            let gravityY =
                Float(-motion.gravity.y)

            result(
                KotlinFloat(value: gravityX),
                KotlinFloat(value: gravityY)
            )
        }
    }
}
