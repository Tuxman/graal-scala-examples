package truffle

import org.graalvm.polyglot.Context
import org.scalatest.{Matchers, PropSpec}

import scala.language.existentials

/**
  * Attach a Truffle ExecutionEventListener to a JS Context and replace the value of the return statement
  */
class ExecutionEventListenerTest extends PropSpec with Matchers {

  val script: String =
    s"""
       |function add(a) {
       |  return a + 1
       |}
     """.stripMargin

  property("Should always return 42") {

    val context: Context = Context.newBuilder("js")
      .option("testListener", "true")
      .build()

    context.getEngine.getInstruments.get("testListener").lookup(classOf[Object])



    context.eval("js", script)

    val result = context.eval("js", "add(1)")

    println(s"result: ${result}")

    result.asInt shouldEqual 42
  }
}
