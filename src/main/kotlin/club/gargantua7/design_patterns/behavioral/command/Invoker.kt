package club.gargantua7.design_patterns.behavioral.command

/**
 * @author Gargantua丶
 **/
class Invoker(private val command: AbstractCommand) {

    fun request() = command.execute()
}