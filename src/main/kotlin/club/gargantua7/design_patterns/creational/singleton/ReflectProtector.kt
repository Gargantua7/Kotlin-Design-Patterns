package club.gargantua7.design_patterns.creational.singleton

/**
 * 在多次实例化时直接抛出异常
 *
 * 但并没有什么用，因为反射也可以修改 flag，
 * 而且懒汉式第一次调用直接用反射的话会导致之后正常调用都出现异常
 * 反射是无法完全防御的，除非用枚举
 *
 * @author Gargantua丶
 **/
class ReflectProtector {

    companion object {

        private var flag = false

        val instance = ReflectProtector()
    }

    init {
        synchronized(ReflectProtector::class.java) {
            if (flag) throw RuntimeException("禁止破坏单例")
            flag = true
        }
    }

}