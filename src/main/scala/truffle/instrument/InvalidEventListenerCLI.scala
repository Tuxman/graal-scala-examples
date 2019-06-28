package truffle.instrument

import com.oracle.truffle.api.Option
import org.graalvm.options.{OptionCategory, OptionKey, OptionStability}

@Option.Group(Array(InvalidEventListenerInstrument.ID))
class InvalidEventListenerCLI extends ProfilerCLI {

}

object InvalidEventListenerCLI {

  @Option(name = "", help = "Enable the Invalid Test Listener (default: false).", category = OptionCategory.USER, stability = OptionStability.STABLE)
  /*private[truffle]*/  val ENABLED: OptionKey[Boolean] = new OptionKey[Boolean](false)

  @Option(name = "TraceRoots", help = "Capture roots when tracing (default:true).", category = OptionCategory.USER, stability = OptionStability.STABLE)
  /*private[truffle]*/ val TRACE_ROOTS = new OptionKey[Boolean](true)
}
