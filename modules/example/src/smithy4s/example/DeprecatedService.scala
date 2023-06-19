package smithy4s.example

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ServiceProduct
import smithy4s.ShapeId
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.Schema.unit

@deprecated(message = "N/A", since = "N/A")
trait DeprecatedServiceGen[F[_, _, _, _, _]] {
  self =>

  @deprecated(message = "N/A", since = "N/A")
  def deprecatedOperation(): F[Unit, Nothing, Unit, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[DeprecatedServiceGen[F]] = Transformation.of[DeprecatedServiceGen[F]](this)
}

trait DeprecatedServiceProductGen[F[_, _, _, _, _]] {
  self =>

  @deprecated(message = "N/A", since = "N/A")
  def deprecatedOperation: F[Unit, Nothing, Unit, Nothing, Nothing]
}

object DeprecatedServiceGen extends Service.Mixin[DeprecatedServiceGen, DeprecatedServiceOperation] with ServiceProduct.Mirror[DeprecatedServiceGen] {

  val id: ShapeId = ShapeId("smithy4s.example", "DeprecatedService")
  val version: String = ""

  val hints: Hints = Hints(
    smithy.api.Deprecated(message = None, since = None),
  )

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[DeprecatedServiceOperation, _, _, _, _, _]] = List(
    DeprecatedServiceOperation.DeprecatedOperation,
  )

  def endpoint[I, E, O, SI, SO](op: DeprecatedServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends DeprecatedServiceOperation.Transformed[DeprecatedServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: DeprecatedServiceGen[DeprecatedServiceOperation] = DeprecatedServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: DeprecatedServiceGen[P], f: PolyFunction5[P, P1]): DeprecatedServiceGen[P1] = new DeprecatedServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[DeprecatedServiceOperation, P]): DeprecatedServiceGen[P] = new DeprecatedServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: DeprecatedServiceGen[P]): PolyFunction5[DeprecatedServiceOperation, P] = DeprecatedServiceOperation.toPolyFunction(impl)

  type Prod[F[_, _, _, _, _]] = DeprecatedServiceProductGen[F]
  val serviceProduct: ServiceProduct.Aux[DeprecatedServiceProductGen, DeprecatedServiceGen] = DeprecatedServiceProductGen
}

object DeprecatedServiceProductGen extends ServiceProduct[DeprecatedServiceProductGen] {
  type Alg[F[_, _, _, _, _]] = DeprecatedServiceGen[F]
  val service: DeprecatedServiceGen.type = DeprecatedServiceGen

  def endpointsProduct: DeprecatedServiceProductGen[service.Endpoint] = new DeprecatedServiceProductGen[service.Endpoint] {
    def deprecatedOperation: service.Endpoint[Unit, Nothing, Unit, Nothing, Nothing] = DeprecatedServiceOperation.DeprecatedOperation
  }

  def toPolyFunction[P2[_, _, _, _, _]](algebra: DeprecatedServiceProductGen[P2]) = new PolyFunction5[service.Endpoint, P2] {
    def apply[I, E, O, SI, SO](fa: service.Endpoint[I, E, O, SI, SO]) = {
      fa.runWithProduct(algebra)
    }
  }

  def mapK5[F[_, _, _, _, _], G[_, _, _, _, _]](alg: DeprecatedServiceProductGen[F], f: PolyFunction5[F, G]): DeprecatedServiceProductGen[G] = {
    new DeprecatedServiceProductGen[G] {
      def deprecatedOperation: G[Unit, Nothing, Unit, Nothing, Nothing] = f[Unit, Nothing, Unit, Nothing, Nothing](alg.deprecatedOperation)
    }
  }
}

sealed trait DeprecatedServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: DeprecatedServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[DeprecatedServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object DeprecatedServiceOperation {

  object reified extends DeprecatedServiceGen[DeprecatedServiceOperation] {
    def deprecatedOperation() = DeprecatedOperation()
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: DeprecatedServiceGen[P], f: PolyFunction5[P, P1]) extends DeprecatedServiceGen[P1] {
    def deprecatedOperation() = f[Unit, Nothing, Unit, Nothing, Nothing](alg.deprecatedOperation())
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: DeprecatedServiceGen[P]): PolyFunction5[DeprecatedServiceOperation, P] = new PolyFunction5[DeprecatedServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: DeprecatedServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class DeprecatedOperation() extends DeprecatedServiceOperation[Unit, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: DeprecatedServiceGen[F]): F[Unit, Nothing, Unit, Nothing, Nothing] = impl.deprecatedOperation()
    def endpoint: (Unit, smithy4s.Endpoint[DeprecatedServiceOperation,Unit, Nothing, Unit, Nothing, Nothing]) = ((), DeprecatedOperation)
  }
  object DeprecatedOperation extends smithy4s.Endpoint[DeprecatedServiceOperation,Unit, Nothing, Unit, Nothing, Nothing] {
    val id: ShapeId = ShapeId("smithy4s.example", "DeprecatedOperation")
    val input: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints(
      smithy.api.Deprecated(message = None, since = None),
    )
    def wrap(input: Unit) = DeprecatedOperation()
    override val errorable: Option[Nothing] = None
    def runWithProduct[F[_, _, _, _, _]](impl: DeprecatedServiceProductGen[F]): F[Unit, Nothing, Unit, Nothing, Nothing] = impl.deprecatedOperation
  }
}

