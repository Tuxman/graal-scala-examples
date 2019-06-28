package truffle.instrument

import java.util.Objects

import com.oracle.truffle.api.CompilerDirectives
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.{EventContext, ExecutionEventListener, SourceSectionFilter, StandardTags, TruffleInstrument}
import org.graalvm.options.OptionDescriptors

/**
  * This is a valid Truffle Instrument implementation. Scala does not support Java annotation processing,
  * and so cannot generate the OptionDescriptors class to enable the instrument for a Polyglot Context.
  
  */
@TruffleInstrument.Registration(id = InvalidEventListenerInstrument.ID, name = "Invalid Test Listener", services = Array(classOf[InvalidEventListenerInstrument]))
class InvalidEventListenerInstrument extends TruffleInstrument {

  override def onCreate(env: TruffleInstrument.Env): Unit = {

    env.registerService(this)
    env.getInstrumenter.attachExecutionEventListener(
      SourceSectionFilter.newBuilder.tagIs(classOf[StandardTags.CallTag]).build, new ExecutionEventListener {
        override def onEnter(context: EventContext, frame: VirtualFrame): Unit = ???

        override def onReturnValue(context: EventContext, frame: VirtualFrame, result: Any): Unit = {
          if (!Objects.equals(result, 42)) {
            CompilerDirectives.transferToInterpreter()
            throw context.createUnwind(42)
          }
        }

        override def onReturnExceptional(context: EventContext, frame: VirtualFrame, exception: Throwable): Unit = ???

        @Override def createUnwind(eventContext: EventContext, virtualFrame: VirtualFrame, info: Any): Any = {
          info
        }
      }
    )
  }

  /*override protected[instrument] def getOptionDescriptors: OptionDescriptors = {
    new InvalidListenerCLIOptionDescriptors()
  }*/
}

object InvalidEventListenerInstrument {

  final val ID = "invalidTestListener"
}