package graalJS


import com.oracle.truffle.js.parser.GraalJSParserHelper
import com.oracle.truffle.js.runtime.{JSParserOptions, JSTruffleOptions}
import org.scalatest.{Matchers, PropSpec}


/**
  * Print a JSON representation of a JavaScript AST using GraalJSParserHelper
  * to build the script environment and pass to JSONWriter.
  */
class GraalJSParserHelperTest extends PropSpec with Matchers {

  val script: String =
    s"""
       |var a  = 0
       |var b = 1
       |
       |function add() {
       |  a += 1
       |}
     """.stripMargin



  val graalsJSParserOptions: JSParserOptions = new JSParserOptions()
    .putStrict(true)
    .putScripting(false)
    .putShebang(false)
    .putEcmaScriptVersion(8)
    .putSyntaxExtensions(false)
    .putConstAsVar(false)
    .putFunctionStatementError(true)
    .putAnnexB(JSTruffleOptions.AnnexB)
    .putAllowBigInt(true)


  val parserHelperJson: String = GraalJSParserHelper.parseToJSON(script, "test", true, graalsJSParserOptions)

  println(parserHelperJson)
}