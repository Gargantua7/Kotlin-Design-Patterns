package club.gargantua7.design_patterns.builder.prototype

import java.io.*

/**
 * 深克隆
 * @author Gargantua丶
 **/
class Deep: Cloneable, Serializable {

    val flag = Flag()

    public override fun clone(): Deep {
        val out = ByteArrayOutputStream()
        ObjectOutputStream(out).use { it.writeObject(this) }
        return ObjectInputStream(ByteArrayInputStream(out.toByteArray())).use { it.readObject() } as Deep
    }
}