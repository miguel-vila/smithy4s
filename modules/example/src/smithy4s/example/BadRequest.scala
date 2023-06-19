package smithy4s.example

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class BadRequest(reason: String) extends Throwable {
}
object BadRequest extends ShapeTag.Companion[BadRequest] {
  val id: ShapeId = ShapeId("smithy4s.example", "BadRequest")

  val hints: Hints = Hints(
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[BadRequest] = struct(
    string.required[BadRequest]("reason", _.reason).addHints(smithy.api.Required()),
  ){
    BadRequest.apply
  }.withId(id).addHints(hints)
}
