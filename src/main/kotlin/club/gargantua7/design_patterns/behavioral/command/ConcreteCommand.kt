package club.gargantua7.design_patterns.behavioral.command

/**
 * @author Gargantua丶
 **/
class ConcreteCommand : AbstractCommand {

    private val receiver = Receiver()

    override fun execute() {
        receiver.request()
    }
}