namespace java communication

exception InvalidOperationException {
  1: string message,
  2: i32 errorCode
}

struct ProductThrift {
    1: i64 id,
    2: string name,
    3: double price,
    4: i32 quantity,
    5: string description
}

service ProductServiceThrift {
  ProductThrift add(1: ProductThrift productThrift) throws (1:InvalidOperationException e),
  ProductThrift findByName(1: string name) throws (1:InvalidOperationException e),
  list<ProductThrift> getAll() throws (1:InvalidOperationException e),
  ProductThrift update(1: i64 id, 2: ProductThrift productThrift) throws (1:InvalidOperationException e),
  void delete(1: i64 id) throws (1:InvalidOperationException e)
}
