package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.request.CustomerRequestDto;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.CustomerServiceThrift;
import communication.CustomerThrift;
import communication.InvalidOperationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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

    public CustomerResponseDto add(@RequestBody CustomerRequestDto dto) {
        try (socket){
            socket.open();
            return mapper.convertValue(
                    client.add(mapper.convertValue(dto,CustomerThrift.class)),
                    CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(@PathVariable Long id) {
        try (socket){
            socket.open();
            client.delete(id);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerResponseDto update(@PathVariable Long id,
                                      @RequestBody CustomerRequestDto customerRequestDto) {
        try (socket){
            socket.open();
            return mapper.convertValue(
                    client.update(id, mapper.convertValue(customerRequestDto,CustomerThrift.class)),
                    CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerResponseDto getByPhoneNumber(@PathVariable Long phoneNumber) {
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

    public CustomerResponseDto getByFirstNameAndLastName(@RequestParam String firstName,
                                                         @RequestParam String lastName) {
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
}
