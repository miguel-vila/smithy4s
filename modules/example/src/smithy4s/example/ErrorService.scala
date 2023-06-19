package smithy4s.example

import smithy4s.Endpoint
import smithy4s.Errorable
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ServiceProduct
import smithy4s.ShapeId
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.Schema.UnionSchema
import smithy4s.schema.Schema.union
import smithy4s.schema.Schema.unit

trait ErrorServiceGen[F[_, _, _, _, _]] {
  self =>

  def errorOp(): F[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[ErrorServiceGen[F]] = Transformation.of[ErrorServiceGen[F]](this)
}

trait ErrorServiceProductGen[F[_, _, _, _, _]] {
  self =>

  def errorOp: F[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing]
}

object ErrorServiceGen extends Service.Mixin[ErrorServiceGen, ErrorServiceOperation] with ServiceProduct.Mirror[ErrorServiceGen] {

  val id: ShapeId = ShapeId("smithy4s.example", "ErrorService")
  val version: String = "1.0.0"

  val hints: Hints = Hints.empty

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[ErrorServiceOperation, _, _, _, _, _]] = List(
    ErrorServiceOperation.ErrorOp,
  )

  def endpoint[I, E, O, SI, SO](op: ErrorServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends ErrorServiceOperation.Transformed[ErrorServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: ErrorServiceGen[ErrorServiceOperation] = ErrorServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: ErrorServiceGen[P], f: PolyFunction5[P, P1]): ErrorServiceGen[P1] = new ErrorServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[ErrorServiceOperation, P]): ErrorServiceGen[P] = new ErrorServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: ErrorServiceGen[P]): PolyFunction5[ErrorServiceOperation, P] = ErrorServiceOperation.toPolyFunction(impl)

  type ErrorOpError = ErrorServiceOperation.ErrorOpError
  val ErrorOpError = ErrorServiceOperation.ErrorOpError
  type Prod[F[_, _, _, _, _]] = ErrorServiceProductGen[F]
  val serviceProduct: ServiceProduct.Aux[ErrorServiceProductGen, ErrorServiceGen] = ErrorServiceProductGen
}

object ErrorServiceProductGen extends ServiceProduct[ErrorServiceProductGen] {
  type Alg[F[_, _, _, _, _]] = ErrorServiceGen[F]
  val service: ErrorServiceGen.type = ErrorServiceGen

  def endpointsProduct: ErrorServiceProductGen[service.Endpoint] = new ErrorServiceProductGen[service.Endpoint] {
    def errorOp: service.Endpoint[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] = ErrorServiceOperation.ErrorOp
  }

  def toPolyFunction[P2[_, _, _, _, _]](algebra: ErrorServiceProductGen[P2]) = new PolyFunction5[service.Endpoint, P2] {
    def apply[I, E, O, SI, SO](fa: service.Endpoint[I, E, O, SI, SO]) = {
      fa.runWithProduct(algebra)
    }
  }

  def mapK5[F[_, _, _, _, _], G[_, _, _, _, _]](alg: ErrorServiceProductGen[F], f: PolyFunction5[F, G]): ErrorServiceProductGen[G] = {
    new ErrorServiceProductGen[G] {
      def errorOp: G[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] = f[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing](alg.errorOp)
    }
  }
}

sealed trait ErrorServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: ErrorServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[ErrorServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object ErrorServiceOperation {

  object reified extends ErrorServiceGen[ErrorServiceOperation] {
    def errorOp() = ErrorOp()
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: ErrorServiceGen[P], f: PolyFunction5[P, P1]) extends ErrorServiceGen[P1] {
    def errorOp() = f[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing](alg.errorOp())
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: ErrorServiceGen[P]): PolyFunction5[ErrorServiceOperation, P] = new PolyFunction5[ErrorServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: ErrorServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class ErrorOp() extends ErrorServiceOperation[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: ErrorServiceGen[F]): F[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] = impl.errorOp()
    def endpoint: (Unit, smithy4s.Endpoint[ErrorServiceOperation,Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing]) = ((), ErrorOp)
  }
  object ErrorOp extends smithy4s.Endpoint[ErrorServiceOperation,Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] with Errorable[ErrorOpError] {
    val id: ShapeId = ShapeId("smithy4s.example", "ErrorOp")
    val input: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints.empty
    def wrap(input: Unit) = ErrorOp()
    override val errorable: Option[Errorable[ErrorOpError]] = Some(this)
    val error: UnionSchema[ErrorOpError] = ErrorOpError.schema
    def liftError(throwable: Throwable): Option[ErrorOpError] = throwable match {
      case e: ErrorOpError => Some(e)
      case _ => None
    }
    def unliftError(e: ErrorOpError): Throwable = e
    def runWithProduct[F[_, _, _, _, _]](impl: ErrorServiceProductGen[F]): F[Unit, ErrorServiceOperation.ErrorOpError, Unit, Nothing, Nothing] = impl.errorOp
  }
  type ErrorOpError = BadRequest | InternalServerError
  object ErrorOpError {
    val id: ShapeId = ShapeId("smithy4s.example", "ErrorOpError")

    val hints: Hints = Hints.empty

    val schema: UnionSchema[ErrorOpError] = {
      val badRequestAlt = BadRequest.schema.oneOf[ErrorOpError]("BadRequest")
      val internalServerErrorAlt = InternalServerError.schema.oneOf[ErrorOpError]("InternalServerError")
      union(badRequestAlt, internalServerErrorAlt) {
        case c: BadRequest => badRequestAlt(c)
        case c: InternalServerError => internalServerErrorAlt(c)
      }
    }
  }
}

