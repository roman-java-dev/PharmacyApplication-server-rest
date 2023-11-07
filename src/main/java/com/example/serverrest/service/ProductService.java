package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.request.ProductRequestDto;
import com.example.serverrest.dto.response.CustomerResponseDto;
import com.example.serverrest.dto.response.ProductResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.InvalidOperationException;
import communication.ProductServiceThrift;
import communication.ProductThrift;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ConfigProperties config;
    private final TSocket socket;
    private final TProtocol protocol;
    private final TMultiplexedProtocol multiplexedProtocol;
    private final ProductServiceThrift.Client client;
    private final ObjectMapper mapper;

    public ProductService(ConfigProperties config, ObjectMapper mapper) throws TTransportException {
        this.config = config;
        this.mapper = mapper;
        socket =  new TSocket(config.host(), config.port());
        protocol = new TBinaryProtocol(socket);
        multiplexedProtocol = new TMultiplexedProtocol(protocol, config.service().product());
        client = new ProductServiceThrift.Client(multiplexedProtocol);
    }

    public ProductResponseDto add(@RequestBody ProductRequestDto dto) {
        try (socket){
            socket.open();
            socket.open();
            return mapper.convertValue(
                    client.add(mapper.convertValue(dto, ProductThrift.class)),
                    ProductResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto getByName(@RequestParam String name) {
        try (socket){
            socket.open();
            return mapper.convertValue(client.findByName(name), ProductResponseDto.class);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductResponseDto> getAll() {
        try (socket){
            socket.open();
            return client.getAll().stream()
                    .map(productThrift -> mapper.convertValue(productThrift, ProductResponseDto.class))
                    .collect(Collectors.toList());
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto productRequestDto) {
        try (socket){
            socket.open();
            return mapper.convertValue(
                    client.update(id, mapper.convertValue(productRequestDto, ProductThrift.class)),
                    ProductResponseDto.class);
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
}