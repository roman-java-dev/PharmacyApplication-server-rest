namespace java communication

struct CustomerThrift {
    1: i64 id,
    2: string firstName,
    3: string lastName,
    4: i64 phoneNumber,
    5: i32 bonus
}

service CustomerServiceThrift {
    CustomerThrift add(1: CustomerThrift customerThrift),
    list<CustomerThrift> getAll(),
    CustomerThrift findByPhoneNumber(1: i64 phoneNumber),
    CustomerThrift findByFirstNameAndLastName(1: string firstName, 2: string lastName),
    CustomerThrift update(1: i64 id, 2: CustomerThrift customerThrift),
    void delete(1: i64 id)
}
