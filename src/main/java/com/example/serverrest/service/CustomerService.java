package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.CustomerServiceThrift;
import communication.CustomerThrift;
import communication.InvalidOperationException;
import lombok.SneakyThrows;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final ConfigProperties config;
    private final TSocket socket;
    private final TProtocol protocol;
    private final TMultiplexedProtocol multiplexedProtocol;
    private final CustomerServiceThrift.Client client;
    private final ObjectMapper mapper;

    public CustomerService(ConfigProperties config, ObjectMapper mapper) throws TTransportException {
        this.config = config;
        this.mapper = mapper;
        socket =  new TSocket(config.host(), config.port());
        protocol = new TBinaryProtocol(socket);
        multiplexedProtocol = new TMultiplexedProtocol(protocol, config.service().customer());
        client = new CustomerServiceThrift.Client(multiplexedProtocol);
    }

    public List<CustomerResponseDto> getAll() {
        try (socket){
            socket.open();
            return client.getAll().stream()
                    .map(customerThrift -> mapper.convertValue(customerThrift, CustomerResponseDto.class))
                    .collect(Collectors.toList());
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try (socket){
            socket.open();
            client.delete(id);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public CustomerResponseDto update(String email, Map<String, Object> fields) {
        try (socket){
            socket.open();
            CustomerThrift customer = client.findByEmail(email);
            fields.forEach((key, value) ->{
                Field field = ReflectionUtils.findField(CustomerThrift.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, customer, value);
            });
            return mapper.convertValue(
                    client.update(customer.id, mapper.convertValue(customer,CustomerThrift.class)),
                    CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerResponseDto getByPhoneNumber(Long phoneNumber) {
        try (socket){
            socket.open();
            return mapper.convertValue(client.findByPhoneNumber(phoneNumber),
                    CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerResponseDto getByFirstNameAndLastName(String firstName, String lastName) {
        try (socket){
            socket.open();
            return mapper.convertValue(
                    client.findByFirstNameAndLastName(firstName, lastName), CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerThrift getByEmail(String email) {
        try (socket){
            socket.open();
            return client.findByEmail(email);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }
}
