package graal

import org.graalvm.polyglot.Context
import org.scalatest.FlatSpec

class MultiLangTest extends FlatSpec {

  val pyre = Context.newBuilder().allowAllAccess(true).build

  val script =
    s"""
       |import polyglot
       |print("Hello from Python")
       |polyglot.eval(language="js", string="console.log('Hello from JavaScript inside Python')")
       |polyglot.eval(language="js", string="Polyglot.eval("js", "console.log('Hello from R inside in Javascript inside Python'))")
       |""".stripMargin

  pyre.eval("python", script)
}
