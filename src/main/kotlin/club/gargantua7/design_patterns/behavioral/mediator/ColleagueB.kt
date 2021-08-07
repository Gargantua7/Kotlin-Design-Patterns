package club.gargantua7.design_patterns.behavioral.mediator

/**
 * @author Gargantua丶
 **/
class ColleagueB(mediator: AbstractMediator) : AbstractColleague(mediator) {

    override fun self() {
        println("$this.self")
    }

    override fun dependent() {
        println("$this.dependent")
        mediator.transferB()
    }
}