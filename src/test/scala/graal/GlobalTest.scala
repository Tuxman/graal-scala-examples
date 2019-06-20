package graal

import org.graalvm.polyglot.{Context, Value}
import org.scalatest.{Matchers, PropSpec}

/**
  * GraalJS defines a global 'Graal' object
  */
class GlobalTest extends PropSpec with Matchers{

  val context: Context = Context.create("js")

  val script: String =
    s"""
       |if (typeof Graal != 'undefined') {
       |  print(Graal.versionJS);
       |  print(Graal.versionGraalVM);
       |  print(Graal.isGraalRuntime);
       |}
     """.stripMargin

  val graalOutput: Value = context.eval("js", script)

  println(graalOutput)

  val globalOutput: Value = context.eval("js", "this")

  println(globalOutput)
}
