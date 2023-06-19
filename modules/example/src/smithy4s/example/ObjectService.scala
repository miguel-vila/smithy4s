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

trait ObjectServiceGen[F[_, _, _, _, _]] {
  self =>

  def putObject(key: ObjectKey, bucketName: BucketName, data: String, foo: Option[LowHigh] = None, someValue: Option[SomeValue] = None): F[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing]
  /** @param key
    *   Sent in the URI label named "key".
    *   Key can also be seen as the filename
    *   It is always required for a GET operation
    * @param bucketName
    *   Sent in the URI label named "bucketName".
    */
  def getObject(key: ObjectKey, bucketName: BucketName): F[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[ObjectServiceGen[F]] = Transformation.of[ObjectServiceGen[F]](this)
}

trait ObjectServiceProductGen[F[_, _, _, _, _]] {
  self =>

  def putObject: F[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing]
  def getObject: F[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing]
}

object ObjectServiceGen extends Service.Mixin[ObjectServiceGen, ObjectServiceOperation] with ServiceProduct.Mirror[ObjectServiceGen] {

  val id: ShapeId = ShapeId("smithy4s.example", "ObjectService")
  val version: String = "1.0.0"

  val hints: Hints = Hints(
    alloy.SimpleRestJson(),
  )

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[ObjectServiceOperation, _, _, _, _, _]] = List(
    ObjectServiceOperation.PutObject,
    ObjectServiceOperation.GetObject,
  )

  def endpoint[I, E, O, SI, SO](op: ObjectServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends ObjectServiceOperation.Transformed[ObjectServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: ObjectServiceGen[ObjectServiceOperation] = ObjectServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: ObjectServiceGen[P], f: PolyFunction5[P, P1]): ObjectServiceGen[P1] = new ObjectServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[ObjectServiceOperation, P]): ObjectServiceGen[P] = new ObjectServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: ObjectServiceGen[P]): PolyFunction5[ObjectServiceOperation, P] = ObjectServiceOperation.toPolyFunction(impl)

  type PutObjectError = ObjectServiceOperation.PutObjectError
  val PutObjectError = ObjectServiceOperation.PutObjectError
  type GetObjectError = ObjectServiceOperation.GetObjectError
  val GetObjectError = ObjectServiceOperation.GetObjectError
  type Prod[F[_, _, _, _, _]] = ObjectServiceProductGen[F]
  val serviceProduct: ServiceProduct.Aux[ObjectServiceProductGen, ObjectServiceGen] = ObjectServiceProductGen
}

object ObjectServiceProductGen extends ServiceProduct[ObjectServiceProductGen] {
  type Alg[F[_, _, _, _, _]] = ObjectServiceGen[F]
  val service: ObjectServiceGen.type = ObjectServiceGen

  def endpointsProduct: ObjectServiceProductGen[service.Endpoint] = new ObjectServiceProductGen[service.Endpoint] {
    def putObject: service.Endpoint[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] = ObjectServiceOperation.PutObject
    def getObject: service.Endpoint[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] = ObjectServiceOperation.GetObject
  }

  def toPolyFunction[P2[_, _, _, _, _]](algebra: ObjectServiceProductGen[P2]) = new PolyFunction5[service.Endpoint, P2] {
    def apply[I, E, O, SI, SO](fa: service.Endpoint[I, E, O, SI, SO]) = {
      fa.runWithProduct(algebra)
    }
  }

  def mapK5[F[_, _, _, _, _], G[_, _, _, _, _]](alg: ObjectServiceProductGen[F], f: PolyFunction5[F, G]): ObjectServiceProductGen[G] = {
    new ObjectServiceProductGen[G] {
      def putObject: G[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] = f[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing](alg.putObject)
      def getObject: G[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] = f[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing](alg.getObject)
    }
  }
}

sealed trait ObjectServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: ObjectServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[ObjectServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object ObjectServiceOperation {

  object reified extends ObjectServiceGen[ObjectServiceOperation] {
    def putObject(key: ObjectKey, bucketName: BucketName, data: String, foo: Option[LowHigh] = None, someValue: Option[SomeValue] = None) = PutObject(PutObjectInput(key, bucketName, data, foo, someValue))
    def getObject(key: ObjectKey, bucketName: BucketName) = GetObject(GetObjectInput(key, bucketName))
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: ObjectServiceGen[P], f: PolyFunction5[P, P1]) extends ObjectServiceGen[P1] {
    def putObject(key: ObjectKey, bucketName: BucketName, data: String, foo: Option[LowHigh] = None, someValue: Option[SomeValue] = None) = f[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing](alg.putObject(key, bucketName, data, foo, someValue))
    def getObject(key: ObjectKey, bucketName: BucketName) = f[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing](alg.getObject(key, bucketName))
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: ObjectServiceGen[P]): PolyFunction5[ObjectServiceOperation, P] = new PolyFunction5[ObjectServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: ObjectServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class PutObject(input: PutObjectInput) extends ObjectServiceOperation[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: ObjectServiceGen[F]): F[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] = impl.putObject(input.key, input.bucketName, input.data, input.foo, input.someValue)
    def endpoint: (PutObjectInput, smithy4s.Endpoint[ObjectServiceOperation,PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing]) = (input, PutObject)
  }
  object PutObject extends smithy4s.Endpoint[ObjectServiceOperation,PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] with Errorable[PutObjectError] {
    val id: ShapeId = ShapeId("smithy4s.example", "PutObject")
    val input: Schema[PutObjectInput] = PutObjectInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("PUT"), uri = smithy.api.NonEmptyString("/{bucketName}/{key}"), code = 200),
      smithy.api.Idempotent(),
    )
    def wrap(input: PutObjectInput) = PutObject(input)
    override val errorable: Option[Errorable[PutObjectError]] = Some(this)
    val error: UnionSchema[PutObjectError] = PutObjectError.schema
    def liftError(throwable: Throwable): Option[PutObjectError] = throwable match {
      case e: PutObjectError => Some(e)
      case _ => None
    }
    def unliftError(e: PutObjectError): Throwable = e
    def runWithProduct[F[_, _, _, _, _]](impl: ObjectServiceProductGen[F]): F[PutObjectInput, ObjectServiceOperation.PutObjectError, Unit, Nothing, Nothing] = impl.putObject
  }
  type PutObjectError = ServerError | NoMoreSpace
  object PutObjectError {
    val id: ShapeId = ShapeId("smithy4s.example", "PutObjectError")

    val hints: Hints = Hints.empty

    val schema: UnionSchema[PutObjectError] = {
      val serverErrorAlt = ServerError.schema.oneOf[PutObjectError]("ServerError")
      val noMoreSpaceAlt = NoMoreSpace.schema.oneOf[PutObjectError]("NoMoreSpace")
      union(serverErrorAlt, noMoreSpaceAlt) {
        case c: ServerError => serverErrorAlt(c)
        case c: NoMoreSpace => noMoreSpaceAlt(c)
      }
    }
  }
  final case class GetObject(input: GetObjectInput) extends ObjectServiceOperation[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: ObjectServiceGen[F]): F[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] = impl.getObject(input.key, input.bucketName)
    def endpoint: (GetObjectInput, smithy4s.Endpoint[ObjectServiceOperation,GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing]) = (input, GetObject)
  }
  object GetObject extends smithy4s.Endpoint[ObjectServiceOperation,GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] with Errorable[GetObjectError] {
    val id: ShapeId = ShapeId("smithy4s.example", "GetObject")
    val input: Schema[GetObjectInput] = GetObjectInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[GetObjectOutput] = GetObjectOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("GET"), uri = smithy.api.NonEmptyString("/{bucketName}/{key}"), code = 200),
      smithy.api.Readonly(),
    )
    def wrap(input: GetObjectInput) = GetObject(input)
    override val errorable: Option[Errorable[GetObjectError]] = Some(this)
    val error: UnionSchema[GetObjectError] = GetObjectError.schema
    def liftError(throwable: Throwable): Option[GetObjectError] = throwable match {
      case e: GetObjectError => Some(e)
      case _ => None
    }
    def unliftError(e: GetObjectError): Throwable = e
    def runWithProduct[F[_, _, _, _, _]](impl: ObjectServiceProductGen[F]): F[GetObjectInput, ObjectServiceOperation.GetObjectError, GetObjectOutput, Nothing, Nothing] = impl.getObject
  }
  type GetObjectError = ServerError
  object GetObjectError {
    val id: ShapeId = ShapeId("smithy4s.example", "GetObjectError")

    val hints: Hints = Hints.empty

    val schema: UnionSchema[GetObjectError] = {
      val serverErrorAlt = ServerError.schema.oneOf[GetObjectError]("ServerError")
      union(serverErrorAlt) {
        case c: ServerError => serverErrorAlt(c)
      }
    }
  }
}

