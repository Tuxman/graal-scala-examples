package graalJS

import com.oracle.js.parser.{Lexer, Source, Token, TokenStream}
import org.scalatest.{Matchers, PropSpec}

class TokenTest extends PropSpec with Matchers {

  val script: String =
    s"""
       |/**
       |* @param a Number
       |* @param b Number
       |**/
       |add = function(a,b) {
       |  return a + b
       |}
     """.stripMargin

  val src: Source = Source.sourceFor("script", script)

  property("Should print all tokens from script") {

    val tokenStream: TokenStream = new TokenStream

    val lexer = new Lexer(src, tokenStream, false, true, false, false, true)

    lexer.lexify()

    for (i <- 0 until tokenStream.last()) {
      println(s"tokenStream $i: ${Token.descType(tokenStream.get(i))}")
    }
  }
}
