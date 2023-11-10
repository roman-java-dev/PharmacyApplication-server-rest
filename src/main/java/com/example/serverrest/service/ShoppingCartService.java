package com.example.serverrest.service;

import com.example.serverrest.config.ConfigProperties;
import com.example.serverrest.dto.response.ShoppingCartResponseDto;
import com.example.serverrest.exception.DataProcessingException;
import com.example.serverrest.mapper.ThriftMapper;
import communication.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private final ConfigProperties config;
    private final TSocket socket;
    private final TProtocol protocol;
    private final TMultiplexedProtocol multiplexedProtocol;
    private final ShoppingCartServiceThrift.Client client;
    private final ThriftMapper mapper;

    public ShoppingCartService(ConfigProperties config, ThriftMapper mapper) throws TTransportException {
        this.config = config;
        this.mapper = mapper;
        socket =  new TSocket(config.host(), config.port());
        protocol = new TBinaryProtocol(socket);
        multiplexedProtocol = new TMultiplexedProtocol(protocol, config.service().shoppingCart());
        client = new ShoppingCartServiceThrift.Client(multiplexedProtocol);
    }

    public void clear(Long customerId) {
        try (socket){
            socket.open();
            client.clear(customerId);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public ShoppingCartResponseDto findByCustomer(Long customerId) {
        try (socket){
            socket.open();
            return mapper.mapToShoppingCartDto(client.findByCustomer(customerId));
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductToShoppingCart(Long productId, Long customerId) {
        try (socket){
            socket.open();
            client.addProductToShoppingCart(productId, customerId);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProductFromShoppingCart(Long productId, Long customerId) {
        try (socket){
            socket.open();
            client.deleteProductFromShoppingCart(productId, customerId);
        } catch (InvalidOperationException e) {
            throw new DataProcessingException(e.getMessage(), e);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }
}
