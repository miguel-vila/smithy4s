package smithy4s.example.guides.auth

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.Smithy4sThrowable
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class NotAuthorizedError(message: String) extends Smithy4sThrowable {
  override def getMessage(): String = message
}

object NotAuthorizedError extends ShapeTag.Companion[NotAuthorizedError] {
  val id: ShapeId = ShapeId("smithy4s.example.guides.auth", "NotAuthorizedError")

  val hints: Hints = Hints(
    smithy.api.Error.CLIENT.widen,
    smithy.api.HttpError(401),
  ).lazily

  implicit val schema: Schema[NotAuthorizedError] = struct(
    string.required[NotAuthorizedError]("message", _.message),
  ){
    NotAuthorizedError.apply
  }.withId(id).addHints(hints)
}
