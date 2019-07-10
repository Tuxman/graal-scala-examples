package graalJS

import com.oracle.js.parser.ir.visitor.NodeVisitor
import com.oracle.js.parser.ir.{FunctionNode, LexicalContext, Node, VarNode}
import com.oracle.js.parser.{ErrorManager, Parser, ScriptEnvironment, Source}
import org.scalatest.{Matchers, PropSpec}

/**
  * VarNode represents any variable declaration in JS using var, let, or const.
  */
class VarNodeTest extends PropSpec with Matchers {

  property("Collect all variable declarations") {

    val script: String =
      s"""
         |var a
         |b = 0
         |this.c = 1
         |var d = 2
         |global.e = 3
         |let f = 4
         |const g = 5
       """.stripMargin

    val scriptEnv: ScriptEnvironment = ScriptEnvironment.builder
      .ecmaScriptVersion(8)
      .constAsVar(false)
      .earlyLvalueError(true)
      .emptyStatements(false)
      .syntaxExtensions(true)
      .scripting(false)
      .shebang(false)
      .strict(true)
      .functionStatementBehavior(ScriptEnvironment.FunctionStatementBehavior.ERROR)
      .build()

    val errManager = new ErrorManager.ThrowErrorManager
    val src = Source.sourceFor("script", script)
    val parser: Parser = new Parser(scriptEnv, src, errManager)
    val parsed = parser.parse()

    def collectVars(node: FunctionNode): Node = {

      node.getBody.accept(new NodeVisitor[LexicalContext](new LexicalContext) {

        override def leaveVarNode(varNode: VarNode): VarNode = {
          println(s"getAssignmentSource: ${varNode.getAssignmentSource}")
          println(s"getStart: ${varNode.getStart}")
          println(s"getName: ${varNode.getName}")
          println(s"getInit: ${varNode.getInit}")
          println(s"varNode: ${varNode.toString()}")
          println(s"tokenType: ${varNode.tokenType.getName}")

          varNode
        }
      })
    }

    collectVars(parsed)
  }
}
