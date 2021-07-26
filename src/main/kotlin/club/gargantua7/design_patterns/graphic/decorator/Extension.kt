package club.gargantua7.design_patterns.graphic.decorator

/**
 * @author Gargantua丶
 **/
fun AbstractComponent.extensionRequest() {
    println("Extension Decorator is requested")
    request()
}