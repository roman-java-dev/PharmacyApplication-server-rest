package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.request.CustomerLoginDto;
import com.example.serverrest.dto.request.CustomerRequestDto;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.example.serverrest.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.AuthenticationServiceThrift;
import communication.CustomerThrift;
import communication.InvalidOperationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {
    private final ConfigProperties config;
    private final TSocket socket;
    private final TProtocol protocol;
    private final TMultiplexedProtocol multiplexedProtocol;
    private final AuthenticationServiceThrift.Client client;
    private final ObjectMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(ConfigProperties config, ObjectMapper mapper, JwtTokenProvider jwtTokenProvider) throws TTransportException {
        this.config = config;
        this.mapper = mapper;
        this.jwtTokenProvider = jwtTokenProvider;
        socket =  new TSocket(config.host(), config.port());
        protocol = new TBinaryProtocol(socket);
        multiplexedProtocol = new TMultiplexedProtocol(protocol, config.service().auth());
        client = new AuthenticationServiceThrift.Client(multiplexedProtocol);
    }

    public CustomerResponseDto register(CustomerRequestDto dto) {
        try (socket) {
            socket.open();
            return mapper.convertValue(client.register(dto.firstName(), dto.lastName(),
                    Long.parseLong(dto.phoneNumber()), dto.email(),
                    dto.password()), CustomerResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> login(CustomerLoginDto dto){
        try (socket) {
            socket.open();
            CustomerThrift customer = client.login(dto.email(), dto.password());
            String token = jwtTokenProvider.createToken(customer.getEmail(),
                    List.of(customer.getRole().name()));
            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }
}
