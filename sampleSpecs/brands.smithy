metadata smithy4sErrorsAsScala3Unions = true


namespace smithy4s.example

use smithy4s.example.common#BrandList

service ErrorService {
  version: "1.0.0",
  operations: [ErrorOp]
}

operation ErrorOp {
  input: Unit,
  output: Unit,
  errors: [BadRequest, InternalServerError]
}

@error("client")
structure BadRequest {
  @required
  reason: String
}

@error("server")
structure InternalServerError {
  @required
  stackTrace: String
}


service BrandService {
  version: "1",
  operations: [AddBrands]
}

@http(method: "POST", uri: "/brands", code: 200)
operation AddBrands {
  input: AddBrandsInput
}

structure AddBrandsInput {
  brands: BrandList
}
