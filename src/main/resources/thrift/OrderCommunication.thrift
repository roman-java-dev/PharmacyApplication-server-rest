namespace java communication

exception InvalidOperationException {
  1: string message,
  2: i32 errorCode
}

struct OrderThrift {
    1: i64 id,
    2: list<ProductThrift> productThrifts,
    3: string orderDate,
    4: CustomerThrift customerThrift
}

struct ProductThrift {
    1: i64 id,
    2: string name,
    3: double price,
    4: i32 quantity,
    5: string description
}

struct CustomerThrift {
    1: i64 id,
    2: string firstName,
    3: string lastName,
    4: i64 phoneNumber,
    5: i32 bonus
}

service OrderServiceThrift {
  OrderThrift completeOrder(1: i64 customerThriftId) throws (1:InvalidOperationException e),
  list<OrderThrift> getOrdersHistory(1: i64 customerThriftId) throws (1:InvalidOperationException e)
}
