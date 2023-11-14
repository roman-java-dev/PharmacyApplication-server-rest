namespace java communication

exception InvalidOperationException {
  1: string message,
  2: i32 errorCode
}

struct CustomerThrift {
    1: i64 id,
    2: string firstName,
    3: string lastName,
    4: i64 phoneNumber,
    5: string email,
    6: string password,
    7: i32 bonus,
    8: RoleThrift role
}

enum RoleThrift {
    ADMIN,
    CUSTOMER
}

service CustomerServiceThrift {
    CustomerThrift findByEmail(1: string email) throws (1:InvalidOperationException e),
    CustomerThrift findById(1: i64 id) throws (1:InvalidOperationException e),
    list<CustomerThrift> getAll() throws (1:InvalidOperationException e),
    CustomerThrift findByPhoneNumber(1: i64 phoneNumber) throws (1:InvalidOperationException e),
    CustomerThrift findByFirstNameAndLastName(1: string firstName, 2: string lastName) throws (1:InvalidOperationException e),
    CustomerThrift update(1: i64 id, 2: CustomerThrift customerThrift) throws (1:InvalidOperationException e),
    void delete(1: i64 id) throws (1:InvalidOperationException e)
}

service AuthenticationServiceThrift {
    CustomerThrift login(1: string email, 2: string password) throws (1:InvalidOperationException e),
    CustomerThrift register(1: string firstName, 2: string lastName, 3: i64 phoneNumber, 4: string email, 5: string password) throws (1:InvalidOperationException e)
}
