package smithy4s.example

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class InternalServerError(stackTrace: String) extends Throwable {
}
object InternalServerError extends ShapeTag.Companion[InternalServerError] {
  val id: ShapeId = ShapeId("smithy4s.example", "InternalServerError")

  val hints: Hints = Hints(
    smithy.api.Error.SERVER.widen,
  )

  implicit val schema: Schema[InternalServerError] = struct(
    string.required[InternalServerError]("stackTrace", _.stackTrace).addHints(smithy.api.Required()),
  ){
    InternalServerError.apply
  }.withId(id).addHints(hints)
}
