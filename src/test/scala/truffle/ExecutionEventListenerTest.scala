package truffle

import com.oracle.truffle.api.CompilerDirectives
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.StandardTags.CallTag
import com.oracle.truffle.api.instrumentation.{AllocationEventFilter, AllocationListener, ContextsListener, EventBinding, EventContext, ExecuteSourceListener, ExecutionEventListener, ExecutionEventNode, ExecutionEventNodeFactory, Instrumenter, LoadSourceListener, LoadSourceSectionListener, SourceFilter, SourceSectionFilter, ThreadsListener, TruffleInstrument}
import org.graalvm.polyglot.Context
import org.scalatest.{Matchers, PropSpec}

import scala.language.existentials

/**
  * Attach a Truffle ExecutionEventListener to a JS Context and replace the value of the return statement
  */
class ExecutionEventListenerTest extends PropSpec with Matchers {

  val script =
    s"""
       |function add(a) {
       |  return a + 1
       |}
     """.stripMargin




    val truffleListener: ExecutionEventListener = new ExecutionEventListener {
      override def onEnter(context: EventContext, frame: VirtualFrame): Unit = ???

      override def onReturnValue(context: EventContext, frame: VirtualFrame, result: Object): Unit = {
        val source: String = context.getInstrumentedSourceSection.getCharacters.toString

        source match {
          case "a" => CompilerDirectives.transferToInterpreter(); throw context.createUnwind(null)
        }
      }

      override def onReturnExceptional(context: EventContext, frame: VirtualFrame, exception: Throwable): Unit = ???

      override def onUnwind(context: EventContext, frame: VirtualFrame, info: Object): Object = {
        info
      }
    }

    @TruffleInstrument.Registration(id = "test", services = Array(classOf[testListener]))
    case class testListener() extends TruffleInstrument {
      override def onCreate(env: TruffleInstrument.Env): Unit = {
        env.registerService(this)
        env.getInstrumenter.attachExecutionEventListener(SourceSectionFilter.newBuilder().tagIs(classOf[CallTag]).build(), truffleListener)
        println(s"options: ${env.getOptions}")
      }
    }

  property("Should always return 42") {

    val context: Context = Context.newBuilder("js").build()

    context.getEngine.getInstruments.get("test").lookup(classOf[Object])

    context.eval("js", script)

    val result = context.eval("js", "add(1)")

    println(s"result: ${result}")

    result.asInt shouldEqual 42
  }
}
